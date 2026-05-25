package cn2026server.services.functional;

import cn2026server.services.functional.functions.*;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import cn2026labels.pubsub.PubSubPublisher;
import io.grpc.stub.StreamObserver;
import servicestubs.*;

public class FunctionalServiceImpl extends  SFServiceGrpc.SFServiceImplBase{

    private static final Storage storage = StorageOptions.getDefaultInstance().getService();
    private static final String bucketName = "cn2526-t3-g07-trab-final";
    private static final String FIRESTORE_COLLECTION = "image_processing";

    private final Firestore firestore;
    private final PubSubPublisher publisher;

    // Construct the functional gRPC service implementation.
    public FunctionalServiceImpl(Firestore firestore, PubSubPublisher publisher){
        this.firestore = firestore;
        this.publisher = publisher;
    }

    // Accept an image upload stream and return the generated request identifier.
    @Override
    public StreamObserver<ImageChunk> submitImage(StreamObserver<SubmitImageResponse> responseObserver) {
        return SubmitImageFunction.submitImage(firestore, publisher, storage, bucketName, FIRESTORE_COLLECTION, responseObserver);
    }

    // Return the processed metadata and labels for a previously submitted image.
    @Override
    public void getImageData(ImageIdRequest request, StreamObserver<GetImageDataResponse> responseObserver) {
        getImageDataFunction.getImageData(firestore, storage, bucketName, FIRESTORE_COLLECTION, request, responseObserver);
    }

    // Return the names of images that match a date range and label filter.
    @Override
    public void searchImages(SearchImagesRequest request, StreamObserver<SearchImagesResponse> responseObserver) {
        SearchImagesFunction.searchImages(firestore, FIRESTORE_COLLECTION, request, responseObserver);
    }

    // Return list of all images in the bucket.
    @Override
    public void listImages(ListImagesRequest request, StreamObserver<ListImagesResponse> responseObserver) {
        ListImagesFunction.listImages(firestore, FIRESTORE_COLLECTION, responseObserver);
    }

    // Download an image from the bucket as a stream of chunks.
    @Override
    public void downloadImage(DownloadImageRequest request, StreamObserver<ImageChunk> responseObserver) {
        DownloadImageFunction.downloadImage(firestore, storage, bucketName, FIRESTORE_COLLECTION, request, responseObserver);
    }
}
