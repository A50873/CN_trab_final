package cn2026labels.server.services;

import cn2026labels.server.labels.LabelsApp;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.protobuf.Timestamp;
import cn2026labels.pubsub.PubSubPublisher;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import servicestubs.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FunctionalServiceImpl extends  SFServiceGrpc.SFServiceImplBase{

    private static final Storage storage = StorageOptions.getDefaultInstance().getService();
    private static final String bucketName = "cn2526-t3-g07-trab-final";
    private static final String FIRESTORE_COLLECTION = "image_processing";
    private static final String REQUEST_ID_METADATA_KEY = "requestId";

    private static Firestore firestore;
    private static PubSubPublisher publisher;

    // Construct the functional gRPC service implementation.
    public FunctionalServiceImpl(Firestore firestore, PubSubPublisher publisher){
        FunctionalServiceImpl.firestore = firestore;
        FunctionalServiceImpl.publisher = publisher;
    }

    // Accept an image upload stream and return the generated request identifier.
    @Override
    public StreamObserver<ImageChunk> submitImage(StreamObserver<SubmitImageResponse> responseObserver) {

        // Receive one image upload as a client stream and answer when the upload finishes.
        return new StreamObserver<>() {

            final ByteArrayOutputStream bytesBuffer = new ByteArrayOutputStream();
            String imageName;
            String contentType;

            @Override
            public void onNext(ImageChunk imageChunk) {

                if(imageChunk == null){
                   responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("image chunk can't be null").asRuntimeException());
                   return;
                }

                if(imageName == null || imageName.isBlank()){
                    imageName = imageChunk.getImageName();
                }

                if(contentType == null || contentType.isBlank()){
                    contentType = imageChunk.getContentType();
                }

                try {
                    imageChunk.getData().writeTo(bytesBuffer);
                } catch (IOException e) {
                    responseObserver.onError(Status.INTERNAL.withDescription("Failed to process image data").withCause(e).asRuntimeException());
                }

            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("submitImage stream failed:. " + throwable.getMessage());
                bytesBuffer.reset();
            }

            @Override
            public void onCompleted() {
                // Validate that the upload contains both file metadata and content.
                if(bytesBuffer.size() == 0 || imageName == null || imageName.isBlank()){
                    responseObserver.onError(
                            Status.INVALID_ARGUMENT.withDescription("No image data was received").asRuntimeException()
                    );
                    return;
                }

                String requestId = UUID.randomUUID().toString();

                try {
                    // Check if the image already exists in Cloud Storage.
                    BlobId blobId = BlobId.of(bucketName, imageName);
                    if(storage.get(blobId) != null) {
                        responseObserver.onError(Status.ALREADY_EXISTS.withDescription("Image with name '" + imageName + "' already exists in bucket").asRuntimeException());
                        return;
                    }

                    // Persist the uploaded image to Cloud Storage.
                    byte[] imageBytes = bytesBuffer.toByteArray();
                    BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                            .setContentType(contentType)
                            .setMetadata(Map.of(REQUEST_ID_METADATA_KEY, requestId))
                            .build();
                    storage.create(blobInfo, imageBytes);

                    try {
                        LabelsApp.saveUploadMetadata(firestore, requestId, imageName, contentType, imageBytes.length, bucketName, FIRESTORE_COLLECTION);
                    } catch (Exception firestoreError) {
                        storage.delete(blobId);
                        throw firestoreError;
                    }

                    // publish a processing request to Pub/Sub
                    if (publisher != null) {
                        try {
                            String gsUri = "gs://" + bucketName + "/" + imageName;
                            String json = "{\"requestId\":\"" + requestId + "\",\"gsUri\":\"" + gsUri + "\",\"imageName\":\"" + imageName + "\",\"bucket\":\"" + bucketName + "\"}";
                            publisher.publishJson(json, Map.of("requestId", requestId));
                        } catch (Exception ex) {
                            System.err.println("Warning: failed to publish Pub/Sub message: " + ex.getMessage());
                        }
                    }
                } catch (Exception e) {
                    responseObserver.onError(Status.INTERNAL.withDescription("Failed to persist image to Cloud Storage: " + e.getMessage()).withCause(e).asRuntimeException());
                    return;
                }

                // Return the request identifier that the client will use in later queries.
                SubmitImageResponse response = SubmitImageResponse.newBuilder().setRequestId(requestId).build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        };
    }

    // Return the processed metadata and labels for a previously submitted image.
    @Override
    public void getImageData(ImageIdRequest request, StreamObserver<GetImageDataResponse> responseObserver) {
        try {

            String imageName = request.getImageName();

            if(imageName.isBlank()){
                responseObserver.
                        onError(Status.INVALID_ARGUMENT.withDescription("image name is required").
                                asRuntimeException());
                return;
            }

            System.out.println("getImageData: Looking for image: " + imageName);

            // Check if the blob exists in Cloud Storage
            BlobId blobId = BlobId.of(bucketName, imageName);
            com.google.cloud.storage.Blob blob = storage.get(blobId);

            if(blob == null) {
                System.out.println("getImageData: Blob not found: " + imageName);
                responseObserver.onError(Status.NOT_FOUND.withDescription("No image found with name: " + imageName).asRuntimeException());
                return;
            }

            System.out.println("getImageData: Blob found, detecting labels...");

            // Get the requestId from blob metadata
            String requestId = blob.getMetadata() != null ? blob.getMetadata().get(REQUEST_ID_METADATA_KEY) : null;

            // Detect labels in the image from Cloud Storage
            String gsURI = "gs://" + bucketName + "/" + imageName;
            System.out.println("getImageData: Calling detectLabels with URI: " + gsURI);
            List<LabelsApp.DetectedLabel> detectedLabels = LabelsApp.detectLabels(gsURI);
            List<String> labelsEng = new ArrayList<>();
            for (LabelsApp.DetectedLabel detectedLabel : detectedLabels) {
                labelsEng.add(detectedLabel.description);
            }
            System.out.println("getImageData: Labels detected: " + labelsEng.size());

            // Translate labels from English to Portuguese
            List<String> labelsPt = LabelsApp.translateLabels(labelsEng);
            System.out.println("getImageData: Labels translated: " + labelsPt.size());

            try {
                LabelsApp.saveProcessingMetadata(firestore, requestId, imageName, detectedLabels, labelsPt, bucketName, FIRESTORE_COLLECTION);
            } catch (Exception firestoreError) {
                System.out.println("getImageData: warning - failed to persist processing metadata in Firestore: " + firestoreError.getMessage());
            }

            // Build and send response
            GetImageDataResponse.Builder responseBuilder = GetImageDataResponse.newBuilder();
            if(requestId != null) {
                responseBuilder.setRequestId(requestId);
            }
            responseBuilder.setImageName(imageName);
            responseBuilder.setProcessedAt(Timestamp.newBuilder().setSeconds(System.currentTimeMillis() / 1000).build());

            int count = Math.min(labelsEng.size(), labelsPt.size());
            for(int i = 0; i < count; i++) {
                LabelData labelData = LabelData.newBuilder()
                        .setLabelEng(labelsEng.get(i))
                        .setLabelPt(labelsPt.get(i))
                        .setConfidenceScore(detectedLabels.get(i).confidence)
                        .build();
                responseBuilder.addLabels(labelData);
            }

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
            System.out.println("getImageData: Response sent successfully");
        } catch (Throwable e) {
            System.out.println("getImageData: Error - " + e.getClass().getName() + ": " + e.getMessage());
            responseObserver.onError(Status.INTERNAL.withDescription("Failed to process image: " + e.getMessage()).withCause(e).asRuntimeException());
        }
    }

    // Return the names of images that match a date range and label filter.
    @Override
    public void searchImages(SearchImagesRequest request, StreamObserver<SearchImagesResponse> responseObserver) {

        String label = request.getLabel();
        boolean hasStart = request.hasDateStart();
        boolean hasEnd = request.hasDateEnd();

        if(!hasStart){
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("start date is required").asRuntimeException());
            return;
        }

        if(!hasEnd){
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("end date is required").asRuntimeException());
            return;
        }

        if(label.isBlank()){
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("label is required").asRuntimeException());
            return;
        }

        Instant start = Instant.ofEpochSecond(request.getDateStart().getSeconds(),
                request.getDateStart().getNanos());

        Instant end = Instant.ofEpochSecond(request.getDateEnd().getSeconds(),
                request.getDateEnd().getNanos());

        if(start.isAfter(end)){
            responseObserver.onError(
                    Status.INVALID_ARGUMENT.withDescription("starting date must be before or equal to end date").asRuntimeException()
            );
            return;
        }

        // Search images by date range and label using Firestore metadata.
        try {
            System.out.println("searchImages: start date = " + start + ", end date = " + end + ", label = " + label);
            java.util.List<String> matches = new java.util.ArrayList<>();
            int docsChecked = 0;

            ApiFuture<QuerySnapshot> future = firestore.collection(FIRESTORE_COLLECTION)
                    .whereGreaterThanOrEqualTo("processed_at", Date.from(start))
                    .whereLessThanOrEqualTo("processed_at", Date.from(end))
                    .get();

            for (QueryDocumentSnapshot doc : future.get().getDocuments()) {
                docsChecked++;

                String imageName = doc.getString("image_name");
                if (imageName == null || imageName.isBlank()) {
                    continue;
                }

                String wanted = label.trim().toLowerCase();
                boolean matched = LabelsApp.matchesLabel(doc.get("labels_eng"), wanted)
                        || LabelsApp.matchesLabel(doc.get("labels_pt"), wanted);

                if (matched) {
                    System.out.println("searchImages: adding " + imageName + " to results");
                    matches.add(imageName);
                }

                if (matches.size() >= 100) {
                    break;
                }
            }

            System.out.println("searchImages: checked " + docsChecked + " firestore docs, found " + matches.size() + " matches");
            SearchImagesResponse.Builder resp = SearchImagesResponse.newBuilder();
            resp.addAllImageNames(matches);
            responseObserver.onNext(resp.build());
            responseObserver.onCompleted();
        } catch (Throwable t) {
            System.out.println("searchImages: Error - " + t.getClass().getName() + ": " + t.getMessage());
            System.err.println("searchImages: unexpected error");
            responseObserver.onError(Status.INTERNAL.withDescription("Failed to perform search: " + t.getMessage()).withCause(t).asRuntimeException());
        }
    }

    // Return list of all images in the bucket.
    @Override
    public void listImages(ListImagesRequest request, StreamObserver<ListImagesResponse> responseObserver) {
        try {
            System.out.println("listImages: listing all images from Firestore");
            java.util.List<String> imageNames = new java.util.ArrayList<>();

            ApiFuture<QuerySnapshot> future = firestore.collection(FIRESTORE_COLLECTION).get();
            for (DocumentSnapshot doc : future.get().getDocuments()) {
                String imageName = doc.getString("image_name");
                if (imageName != null && !imageName.isBlank()) {
                    imageNames.add(imageName);
                }
            }

            System.out.println("listImages: found " + imageNames.size() + " images");
            ListImagesResponse.Builder resp = ListImagesResponse.newBuilder();
            resp.addAllImageNames(imageNames);
            responseObserver.onNext(resp.build());
            responseObserver.onCompleted();
        } catch (Throwable t) {
            System.out.println("listImages: Error - " + t.getClass().getName() + ": " + t.getMessage());
            responseObserver.onError(Status.INTERNAL.withDescription("Failed to list images: " + t.getMessage()).withCause(t).asRuntimeException());
        }
    }

    // Download an image from the bucket as a stream of chunks.
    @Override
    public void downloadImage(DownloadImageRequest request, StreamObserver<ImageChunk> responseObserver) {
        String imageName = request.getImageName();

        if (imageName.isBlank()) {
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("image name is required").asRuntimeException());
            return;
        }

        try {
            System.out.println("downloadImage: downloading image: " + imageName);

            // Check if the blob exists in Cloud Storage
            BlobId blobId = BlobId.of(bucketName, imageName);
            com.google.cloud.storage.Blob blob = storage.get(blobId);

            if (blob == null) {
                System.out.println("downloadImage: Blob not found: " + imageName);
                responseObserver.onError(Status.NOT_FOUND.withDescription("No image found with name: " + imageName).asRuntimeException());
                return;
            }

            // Get content type from blob metadata
            String contentType = blob.getContentType();
            if (contentType == null || contentType.isBlank()) {
                contentType = "application/octet-stream";
            }

            System.out.println("downloadImage: downloading " + imageName + " (size: " + blob.getSize() + " bytes)");

            // Download the blob in chunks
            byte[] buffer = new byte[4096];
            int bytesRead;
            try (java.io.InputStream input = Channels.newInputStream(blob.reader())) {
                while ((bytesRead = input.read(buffer)) > 0) {
                    ImageChunk chunk = ImageChunk.newBuilder()
                            .setImageName(imageName)
                            .setContentType(contentType)
                            .setData(com.google.protobuf.ByteString.copyFrom(buffer, 0, bytesRead))
                            .build();
                    responseObserver.onNext(chunk);
                }
            }

            responseObserver.onCompleted();
            System.out.println("downloadImage: download completed for " + imageName);
        } catch (Throwable t) {
            System.out.println("downloadImage: Error - " + t.getClass().getName() + ": " + t.getMessage());
            System.err.println("downloadImage: unexpected error");
            responseObserver.onError(Status.INTERNAL.withDescription("Failed to download image: " + t.getMessage()).withCause(t).asRuntimeException());
        }
    }
}
