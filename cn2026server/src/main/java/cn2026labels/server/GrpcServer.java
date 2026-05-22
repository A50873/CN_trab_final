package cn2026labels.server;

import cn2026labels.server.services.FunctionalServiceImpl;
import cn2026labels.server.services.ScalingServiceImpl;
import cn2026labels.server.compute.GcpInstanceGroupScaler;
import cn2026labels.pubsub.PubSubPublisher;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import io.grpc.ServerBuilder;
import java.io.IOException;

public class GrpcServer {

    private static int svcPort = 8000;

    static void main(String[] args) throws InterruptedException, IOException {

        if( args.length > 0 ) svcPort = Integer.parseInt(args[0]);

        Firestore firestore = FirestoreOptions.newBuilder()
                .setDatabaseId("trab-final-db")
                .setCredentials(GoogleCredentials.getApplicationDefault())
                .build()
                .getService();

        // Create a Pub/Sub publisher and pass it to the FunctionalServiceImpl so uploads can publish processing requests.
        String projectId = "cn2526-t3-g07";
        PubSubPublisher publisher = null;
        try {
            publisher = new PubSubPublisher(projectId, "cn2026labels-processing");
        } catch (Exception ex) {
            System.err.println("Failed to initialize Pub/Sub publisher: " + ex.getMessage());
            publisher = null;
        }

        GcpInstanceGroupScaler grpcServerScaler = null;
        GcpInstanceGroupScaler processingInstanceScaler = null;

        try {
            String zone = "europe-west1-b";

            // Create gRPC server scaler if MIG name is provided
            String grpcMigName = "cn2026-server-mig";
            try {
                grpcServerScaler = new GcpInstanceGroupScaler(projectId, zone, grpcMigName);
                System.out.println("Initialized gRPC Server scaler for MIG: " + grpcMigName);
            } catch (Exception e) {
                System.err.println("Failed to initialize gRPC Server scaler: " + e.getMessage());
            }

            // Create processing instance scaler if MIG name is provided
            String processingMigName = "cn2026-processing-mig";
            try {
                processingInstanceScaler = new GcpInstanceGroupScaler(projectId, zone, processingMigName);
                System.out.println("Initialized Processing Instance scaler for MIG: " + processingMigName);
            } catch (Exception e) {
                System.err.println("Failed to initialize Processing Instance scaler: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Error initializing scalers: " + e.getMessage());
        }

        // Register the gRPC services that should be exposed by this server process.
        io.grpc.Server svc = ServerBuilder.
                                forPort(svcPort).
                                addService(
                                        new FunctionalServiceImpl(firestore, publisher)
                                ).
                                addService(
                                        new ScalingServiceImpl(grpcServerScaler, processingInstanceScaler)
                                ).
                                build();

        svc.start();

        System.out.println("Server started on port " + svcPort);

        // Shut the server down gracefully when the JVM exits.
        Runtime.getRuntime().addShutdownHook(new ShutdownHook(svc, firestore, publisher, grpcServerScaler, processingInstanceScaler));

        svc.awaitTermination();
    }
}

