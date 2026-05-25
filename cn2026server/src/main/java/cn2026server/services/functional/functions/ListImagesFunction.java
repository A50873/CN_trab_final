package cn2026server.services.functional.functions;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import servicestubs.ListImagesResponse;

import java.util.ArrayList;
import java.util.List;

public final class ListImagesFunction {

    private ListImagesFunction() {
    }

    public static void listImages(Firestore firestore,
                                  String collectionName,
                                  StreamObserver<ListImagesResponse> responseObserver) {
        try {
            System.out.println("listImages: listing all images from Firestore");
            List<String> imageNames = new ArrayList<>();

            ApiFuture<QuerySnapshot> future = firestore.collection(collectionName).get();
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
}

