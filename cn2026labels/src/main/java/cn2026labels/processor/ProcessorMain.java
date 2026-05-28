package cn2026labels.processor;

import cn2026labels.labels.LabelsApp;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.cloud.pubsub.v1.Subscriber;
import com.google.pubsub.v1.ProjectSubscriptionName;
import com.google.pubsub.v1.PubsubMessage;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

/**
 * Simple Pub/Sub consumer that receives processing requests, runs label detection/translation
 * and persists results in Firestore via LabelsApp. Designed to run as a separate process.
 */
public class ProcessorMain {

    public static void main(String[] args) throws Exception {
        String projectId = "cn2526-t3-g07";
        String subscriptionId = "cn2026labels-processing-sub";

        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();

        System.out.println(credentials.getClass().getName());
        System.out.println(System.getenv("GOOGLE_APPLICATION_CREDENTIALS"));

        Firestore firestore = FirestoreOptions.newBuilder()
                .setDatabaseId("trab-final-db")
                .setCredentials(GoogleCredentials.getApplicationDefault())
                .build()
                .getService();

        ProjectSubscriptionName subscriptionName = ProjectSubscriptionName.of(projectId, subscriptionId);
        ObjectMapper mapper = new ObjectMapper();

        MessageReceiver receiver = new MessageReceiver() {
            @Override
            public void receiveMessage(PubsubMessage message, AckReplyConsumer consumer) {
                String data = message.getData().toStringUtf8();
                try {
                    Map<String,Object> payload = mapper.readValue(data, new TypeReference<Map<String,Object>>(){});
                    String requestId = payload.getOrDefault("requestId", "").toString();
                    String gsUri = payload.getOrDefault("gsUri", "").toString();
                    String imageName = payload.getOrDefault("imageName", "").toString();

                    String documentId = (requestId != null && !requestId.isBlank()) ? requestId : imageName;

                    // Check if already processed
                    DocumentSnapshot doc = firestore.collection("image_processing").document(documentId).get().get();
                    if (doc.exists() && "PROCESSED".equalsIgnoreCase(doc.getString("status"))) {
                        System.out.println("Processor: already processed: " + documentId);
                        consumer.ack();
                        return;
                    }

                    // Detect and translate
                    System.out.println("Processor: processing " + imageName + " (" + gsUri + ")");
                    List<LabelsApp.DetectedLabel> labels = LabelsApp.detectLabels(gsUri);
                    List<String> labelsEng = new ArrayList<>();
                    for (LabelsApp.DetectedLabel dl : labels) {
                        labelsEng.add(dl.description);
                    }
                    List<String> labelsPt = LabelsApp.translateLabels(labelsEng);

                    // Save processing metadata
                    LabelsApp.saveProcessingMetadata(firestore, requestId, imageName, labels, labelsPt,
                            "cn2526-t3-g07-trab-final", "image_processing");

                    consumer.ack();
                    System.out.println("Processor: processing completed for " + documentId);
                } catch (Exception e) {
                    e.printStackTrace();
                    consumer.nack();
                }
            }
        };

        Subscriber subscriber = Subscriber.newBuilder(subscriptionName, receiver).build();
        subscriber.startAsync().awaitRunning();
        System.out.println("Processor: subscriber running for subscription: " + subscriptionName);

        // keep running until terminated
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("Processor shutting down");
            try {
                subscriber.stopAsync().awaitTerminated();
                firestore.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }));

        // block main thread while subscriber runs
        subscriber.awaitTerminated();
    }

    // bucket is constant for this project; no helper required
}

