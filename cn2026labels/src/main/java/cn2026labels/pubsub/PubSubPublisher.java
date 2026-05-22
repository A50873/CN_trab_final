package cn2026labels.pubsub;

import com.google.api.core.ApiFuture;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Simple Pub/Sub publisher helper used by the gRPC server to publish processing requests.
 */
public class PubSubPublisher {

    private final TopicName topicName;
    private final Publisher publisher;

    public PubSubPublisher(String projectId, String topicId) throws IOException {
        this.topicName = TopicName.of(projectId, topicId);
        this.publisher = Publisher.newBuilder(topicName).build();
    }

    public String publishJson(String jsonPayload, Map<String,String> attributes) throws Exception {
        PubsubMessage.Builder builder = PubsubMessage.newBuilder().setData(ByteString.copyFromUtf8(jsonPayload));
        if (attributes != null) {
            attributes.forEach(builder::putAttributes);
        }
        ApiFuture<String> future = publisher.publish(builder.build());
        return future.get();
    }

    public void shutdown() throws Exception {
        if (publisher != null) {
            publisher.shutdown();
            publisher.awaitTermination(1, TimeUnit.MINUTES);
        }
    }
}

