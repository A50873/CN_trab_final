package cn2026server;

import cn2026server.compute.GcpInstanceGroupScaler;
import cn2026labels.pubsub.PubSubPublisher;
import com.google.cloud.firestore.Firestore;

public class ShutdownHook extends Thread {
    io.grpc.Server svc;
    Firestore firestore;
    PubSubPublisher publisher;
    GcpInstanceGroupScaler grpcServerScaler;
    GcpInstanceGroupScaler processingInstanceScaler;

    public ShutdownHook(io.grpc.Server svc, Firestore firestore, PubSubPublisher publisher,
                       GcpInstanceGroupScaler grpcServerScaler, GcpInstanceGroupScaler processingInstanceScaler) {
        this.svc = svc;
        this.firestore = firestore;
        this.publisher = publisher;
        this.grpcServerScaler = grpcServerScaler;
        this.processingInstanceScaler = processingInstanceScaler;
    }

    @Override
    public void run() {
        System.err.println("*shutdown gRPC server, because JVM is shutting down");
        try {
            // Initiates an orderly shutdown in which preexisting calls continue
            // but new calls are rejected. So we can clean and finish work
            svc.shutdown();
            svc.awaitTermination();
            try {
                if (publisher != null) publisher.shutdown();
            } catch (Exception ex) {
                System.err.println("Error shutting down PubSub publisher: " + ex.getMessage());
            }
            try {
                if (grpcServerScaler != null) grpcServerScaler.close();
            } catch (Exception ex) {
                System.err.println("Error closing gRPC Server scaler: " + ex.getMessage());
            }
            try {
                if (processingInstanceScaler != null) processingInstanceScaler.close();
            } catch (Exception ex) {
                System.err.println("Error closing Processing Instance scaler: " + ex.getMessage());
            }
            firestore.close();
        } catch (Exception e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            e.printStackTrace(System.err);
        }
        System.err.println("*** server shut down");
    }
}
