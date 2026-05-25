package cn2026server.services.functional.functions;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.storage.BlobId;

import java.util.Date;
import java.util.List;

public final class FunctionalServiceSupport {

    public static final String REQUEST_ID_METADATA_KEY = "requestId";

    private FunctionalServiceSupport() {
    }

    public static String blobNameFor(String requestId, String imageName) {
        return requestId + "/" + imageName;
    }

    static String imageNameFromBlobName(String blobName) {
        int separator = blobName.lastIndexOf('/');
        if (separator < 0 || separator == blobName.length() - 1) {
            return blobName;
        }
        return blobName.substring(separator + 1);
    }

    public record ResolvedImageStorage(BlobId blobId, String requestId, String imageName) {
    }

    static ResolvedImageStorage resolveFromDocuments(String bucketName, List<QueryDocumentSnapshot> documents) {
        if (documents.isEmpty()) {
            return null;
        }

        QueryDocumentSnapshot selected = documents.getFirst();
        Date selectedUploadedAt = selected.getDate("uploaded_at");

        for (QueryDocumentSnapshot candidate : documents) {
            Date candidateUploadedAt = candidate.getDate("uploaded_at");
            if (selectedUploadedAt == null || (candidateUploadedAt != null && candidateUploadedAt.after(selectedUploadedAt))) {
                selected = candidate;
                selectedUploadedAt = candidateUploadedAt;
            }
        }

        String requestId = selected.getString("request_id");
        String imageName = selected.getString("image_name");
        if (imageName == null || imageName.isBlank()) {
            return null;
        }

        if (requestId == null || requestId.isBlank()) {
            return new ResolvedImageStorage(BlobId.of(bucketName, imageName), null, imageName);
        }

        return new ResolvedImageStorage(BlobId.of(bucketName, blobNameFor(requestId, imageName)), requestId, imageName);
    }

    public static ResolvedImageStorage resolveImageStorage(Firestore firestore,
                                                           String collectionName,
                                                           String bucketName,
                                                           String identifier) throws Exception {
        if (identifier == null || identifier.isBlank()) {
            return null;
        }

        ApiFuture<QuerySnapshot> byRequestIdFuture = firestore.collection(collectionName)
                .whereEqualTo("request_id", identifier)
                .get();
        ResolvedImageStorage resolved = resolveFromDocuments(bucketName, byRequestIdFuture.get().getDocuments());
        if (resolved != null) {
            return resolved;
        }

        ApiFuture<QuerySnapshot> byImageNameFuture = firestore.collection(collectionName)
                .whereEqualTo("image_name", identifier)
                .get();
        return resolveFromDocuments(bucketName, byImageNameFuture.get().getDocuments());
    }
}

