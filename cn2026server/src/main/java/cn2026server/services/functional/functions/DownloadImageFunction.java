package cn2026server.services.functional.functions;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.Storage;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import servicestubs.DownloadImageRequest;
import servicestubs.ImageChunk;

import java.nio.channels.Channels;

public final class DownloadImageFunction {

    private DownloadImageFunction() {
    }

    public static void downloadImage(Firestore firestore,
                                     Storage storage,
                                     String bucketName,
                                     String collectionName,
                                     DownloadImageRequest request,
                                     StreamObserver<ImageChunk> responseObserver) {
        String identifier = request.getImageName();

        if (identifier.isBlank()) {
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("image name is required").asRuntimeException());
            return;
        }

        try {
            System.out.println("downloadImage: downloading image/request: " + identifier);

            FunctionalServiceSupport.ResolvedImageStorage resolvedImageStorage = FunctionalServiceSupport.resolveImageStorage(firestore, collectionName, bucketName, identifier);
            if (resolvedImageStorage == null) {
                responseObserver.onError(Status.NOT_FOUND.withDescription("No image found for: " + identifier).asRuntimeException());
                return;
            }

            com.google.cloud.storage.Blob blob = storage.get(resolvedImageStorage.blobId());

            if (blob == null) {
                System.out.println("downloadImage: Blob not found: " + resolvedImageStorage.blobId().getName());
                responseObserver.onError(Status.NOT_FOUND.withDescription("No image found for: " + identifier).asRuntimeException());
                return;
            }

            String contentType = blob.getContentType();
            if (contentType == null || contentType.isBlank()) {
                contentType = "application/octet-stream";
            }

            String imageName = resolvedImageStorage.imageName() != null && !resolvedImageStorage.imageName().isBlank()
                    ? resolvedImageStorage.imageName()
                    : FunctionalServiceSupport.imageNameFromBlobName(resolvedImageStorage.blobId().getName());

            System.out.println("downloadImage: downloading " + imageName + " (size: " + blob.getSize() + " bytes)");

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

