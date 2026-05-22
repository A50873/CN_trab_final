package cn2026labels.server.services;

import cn2026labels.server.compute.GcpInstanceGroupScaler;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import servicestubs.SGServiceGrpc;
import servicestubs.ScaleRequest;
import servicestubs.ScaleResponse;

// Hosts the SG RPCs for changing gRPC-server and processing-instance capacity.
public class ScalingServiceImpl extends SGServiceGrpc.SGServiceImplBase{

    // Compute Engine scalers for managing instance groups
    private final GcpInstanceGroupScaler grpcServerScaler;
    private final GcpInstanceGroupScaler processingInstanceScaler;

    // In-memory tracking of current instance counts (in a real system, these would be managed by an orchestrator)
    private static int grpcServerCount = 1;
    private static int processingInstanceCount = 1;
    private static final Object lock = new Object();

    // Constructor that accepts gRPC server and processing instance scalers
    public ScalingServiceImpl(GcpInstanceGroupScaler grpcServerScaler, GcpInstanceGroupScaler processingInstanceScaler) {
        this.grpcServerScaler = grpcServerScaler;
        this.processingInstanceScaler = processingInstanceScaler;
    }

    // Increase the number of gRPC servers
    @Override
    public void increaseGrpcServers(ScaleRequest request, StreamObserver<ScaleResponse> responseObserver) {
        try {
            int amount = request.getAmount();

            if(amount <= 0) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                        .withDescription("Amount must be greater than 0")
                        .asRuntimeException());
                return;
            }

            if (grpcServerScaler == null) {
                responseObserver.onError(Status.UNAVAILABLE
                        .withDescription("gRPC Server scaler not configured. Set GCP_GRPC_SERVERS_MIG environment variable.")
                        .asRuntimeException());
                return;
            }

            synchronized(lock) {
                try {
                    int previousSize = grpcServerScaler.getCurrentSize();
                    int newSize = previousSize + amount;

                    grpcServerScaler.resize(newSize);

                    System.out.println("increaseGrpcServers: increased from " + previousSize + " to " + newSize);

                    ScaleResponse response = ScaleResponse.newBuilder()
                            .setSuccess(true)
                            .setPreviousSize(previousSize)
                            .setNewSize(newSize)
                            .setMessage("Successfully increased gRPC servers from " + previousSize + " to " + newSize)
                            .build();

                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            System.err.println("increaseGrpcServers: Error - " + e.getMessage());
            e.printStackTrace();
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Failed to increase gRPC servers: " + e.getMessage())
                    .withCause(e)
                    .asRuntimeException());
        }
    }

    // Decrease the number of gRPC servers
    @Override
    public void decreaseGrpcServers(ScaleRequest request, StreamObserver<ScaleResponse> responseObserver) {
        try {
            int amount = request.getAmount();

            if(amount <= 0) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                        .withDescription("Amount must be greater than 0")
                        .asRuntimeException());
                return;
            }

            if (grpcServerScaler == null) {
                responseObserver.onError(Status.UNAVAILABLE
                        .withDescription("gRPC Server scaler not configured. Set GCP_GRPC_SERVERS_MIG environment variable.")
                        .asRuntimeException());
                return;
            }

            synchronized(lock) {
                try {
                    int previousSize = grpcServerScaler.getCurrentSize();

                    if(previousSize - amount < 1) {
                        responseObserver.onError(Status.INVALID_ARGUMENT
                                .withDescription("Cannot decrease below 1 server. Current: " + previousSize + ", requested reduction: " + amount)
                                .asRuntimeException());
                        return;
                    }

                    int newSize = previousSize - amount;
                    grpcServerScaler.resize(newSize);

                    System.out.println("decreaseGrpcServers: decreased from " + previousSize + " to " + newSize);

                    ScaleResponse response = ScaleResponse.newBuilder()
                            .setSuccess(true)
                            .setPreviousSize(previousSize)
                            .setNewSize(newSize)
                            .setMessage("Successfully decreased gRPC servers from " + previousSize + " to " + newSize)
                            .build();

                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            System.err.println("decreaseGrpcServers: Error - " + e.getMessage());
            e.printStackTrace();
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Failed to decrease gRPC servers: " + e.getMessage())
                    .withCause(e)
                    .asRuntimeException());
        }
    }

    // Increase the number of processing instances
    @Override
    public void increaseProcessingInstances(ScaleRequest request, StreamObserver<ScaleResponse> responseObserver) {
        try {
            int amount = request.getAmount();

            if(amount <= 0) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                        .withDescription("Amount must be greater than 0")
                        .asRuntimeException());
                return;
            }

            if (processingInstanceScaler == null) {
                responseObserver.onError(Status.UNAVAILABLE
                        .withDescription("Processing Instance scaler not configured. Set GCP_PROCESSING_INSTANCES_MIG environment variable.")
                        .asRuntimeException());
                return;
            }

            synchronized(lock) {
                try {
                    int previousSize = processingInstanceScaler.getCurrentSize();
                    int newSize = previousSize + amount;

                    processingInstanceScaler.resize(newSize);

                    System.out.println("increaseProcessingInstances: increased from " + previousSize + " to " + newSize);

                    ScaleResponse response = ScaleResponse.newBuilder()
                            .setSuccess(true)
                            .setPreviousSize(previousSize)
                            .setNewSize(newSize)
                            .setMessage("Successfully increased processing instances from " + previousSize + " to " + newSize)
                            .build();

                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            System.err.println("increaseProcessingInstances: Error - " + e.getMessage());
            e.printStackTrace();
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Failed to increase processing instances: " + e.getMessage())
                    .withCause(e)
                    .asRuntimeException());
        }
    }

    // Decrease the number of processing instances
    @Override
    public void decreaseProcessingInstances(ScaleRequest request, StreamObserver<ScaleResponse> responseObserver) {
        try {
            int amount = request.getAmount();

            if(amount <= 0) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                        .withDescription("Amount must be greater than 0")
                        .asRuntimeException());
                return;
            }

            if (processingInstanceScaler == null) {
                responseObserver.onError(Status.UNAVAILABLE
                        .withDescription("Processing Instance scaler not configured. Set GCP_PROCESSING_INSTANCES_MIG environment variable.")
                        .asRuntimeException());
                return;
            }

            synchronized(lock) {
                try {
                    int previousSize = processingInstanceScaler.getCurrentSize();

                    if(previousSize - amount < 1) {
                        responseObserver.onError(Status.INVALID_ARGUMENT
                                .withDescription("Cannot decrease below 1 instance. Current: " + previousSize + ", requested reduction: " + amount)
                                .asRuntimeException());
                        return;
                    }

                    int newSize = previousSize - amount;
                    processingInstanceScaler.resize(newSize);

                    System.out.println("decreaseProcessingInstances: decreased from " + previousSize + " to " + newSize);

                    ScaleResponse response = ScaleResponse.newBuilder()
                            .setSuccess(true)
                            .setPreviousSize(previousSize)
                            .setNewSize(newSize)
                            .setMessage("Successfully decreased processing instances from " + previousSize + " to " + newSize)
                            .build();

                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Exception e) {
            System.err.println("decreaseProcessingInstances: Error - " + e.getMessage());
            e.printStackTrace();
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Failed to decrease processing instances: " + e.getMessage())
                    .withCause(e)
                    .asRuntimeException());
        }
    }
}
