package cn2026labels.labels;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.vision.v1.*;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LabelsApp {

    public static final class DetectedLabel {
        public final String description;
        public final float confidence;

        public DetectedLabel(String description, float confidence) {
            this.description = description;
            this.confidence = confidence;
        }
    }

    public static List<DetectedLabel> detectLabels(String gsURI) throws IOException {
        List<AnnotateImageRequest> requests = new ArrayList<>();
        List<DetectedLabel> labels = new ArrayList<>();

        Image img = Image.newBuilder()
                .setSource(ImageSource.newBuilder().setImageUri(gsURI).build())
                .build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();
            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.out.format("Error: %s%n", res.getError().getMessage());
                } else {
                    for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
                        labels.add(new DetectedLabel(annotation.getDescription(), annotation.getScore()));
                    }
                }
            }
        }

        return labels;
    }

    public static List<String> translateLabels(List<String> labels) {
        List<String> labelsTranslated = new ArrayList<>();
        try {
            Translate translateService = TranslateOptions.getDefaultInstance().getService();
            for (String label : labels) {
                Translation translation = translateService.translate(
                        label,
                        Translate.TranslateOption.sourceLanguage("en"),
                        Translate.TranslateOption.targetLanguage("pt"));
                labelsTranslated.add(translation.getTranslatedText());
            }
        } catch (Exception ex) {
            System.err.println("TranslateLabels error: " + ex.getMessage());
        }
        return labelsTranslated;
    }

    private static String documentIdFor(String requestId, String imageName) {
        if (requestId != null && !requestId.isBlank()) {
            return requestId;
        }
        return imageName;
    }

    public static void saveProcessingMetadata(Firestore firestoreClient,
                                              String requestId,
                                              String imageName,
                                              List<DetectedLabel> detectedLabels,
                                              List<String> labelsPt,
                                              String bucketName,
                                              String collectionName) throws Exception {

        String documentId = documentIdFor(requestId, imageName);
        Map<String, Object> record = new HashMap<>();
        record.put("request_id", requestId);
        record.put("image_name", imageName);
        record.put("bucket_name", bucketName);
        record.put("status", "PROCESSED");
        record.put("processed_at", Date.from(Instant.now()));

        List<String> labelsEng = new ArrayList<>();
        List<Float> confidences = new ArrayList<>();
        for (DetectedLabel detectedLabel : detectedLabels) {
            labelsEng.add(detectedLabel.description);
            confidences.add(detectedLabel.confidence);
        }
        record.put("labels_eng", labelsEng);
        record.put("labels_pt", new ArrayList<>(labelsPt));
        record.put("labels_confidence", confidences);

        List<Map<String, Object>> labels = new ArrayList<>();
        int count = Math.min(Math.min(labelsEng.size(), labelsPt.size()), confidences.size());
        for (int i = 0; i < count; i++) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("label_eng", labelsEng.get(i));
            entry.put("label_pt", labelsPt.get(i));
            entry.put("confidence_score", confidences.get(i));
            labels.add(entry);
        }
        record.put("labels", labels);
        record.put("labels_count", count);

        firestoreClient.collection(collectionName)
                .document(documentId)
                .set(record, SetOptions.merge())
                .get();
    }

    public static void saveUploadMetadata(Firestore firestoreClient,
                                          String requestId,
                                          String imageName,
                                          String contentType,
                                          long sizeBytes,
                                          String bucketName,
                                          String collectionName) throws Exception {
        String documentId = documentIdFor(requestId, imageName);
        Map<String, Object> record = new HashMap<>();
        record.put("request_id", requestId);
        record.put("image_name", imageName);
        record.put("content_type", contentType);
        record.put("bucket_name", bucketName);
        record.put("size_bytes", sizeBytes);
        record.put("status", "UPLOADED");
        record.put("uploaded_at", Date.from(Instant.now()));

        firestoreClient.collection(collectionName)
                .document(documentId)
                .set(record, SetOptions.merge())
                .get();
    }

    public static boolean matchesLabel(Object fieldValue, String wanted) {
        if (!(fieldValue instanceof List<?> values)) {
            return false;
        }

        for (Object value : values) {
            if (value != null && value.toString().trim().equalsIgnoreCase(wanted)) {
                return true;
            }
        }

        return false;
    }
}



