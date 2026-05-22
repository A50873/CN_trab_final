package servicestubs;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class SGServiceGrpc {

  private SGServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "grpcservice.SGService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<servicestubs.ScaleRequest,
      servicestubs.ScaleResponse> getIncreaseGrpcServersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "increaseGrpcServers",
      requestType = servicestubs.ScaleRequest.class,
      responseType = servicestubs.ScaleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<servicestubs.ScaleRequest,
      servicestubs.ScaleResponse> getIncreaseGrpcServersMethod() {
    io.grpc.MethodDescriptor<servicestubs.ScaleRequest, servicestubs.ScaleResponse> getIncreaseGrpcServersMethod;
    if ((getIncreaseGrpcServersMethod = SGServiceGrpc.getIncreaseGrpcServersMethod) == null) {
      synchronized (SGServiceGrpc.class) {
        if ((getIncreaseGrpcServersMethod = SGServiceGrpc.getIncreaseGrpcServersMethod) == null) {
          SGServiceGrpc.getIncreaseGrpcServersMethod = getIncreaseGrpcServersMethod =
              io.grpc.MethodDescriptor.<servicestubs.ScaleRequest, servicestubs.ScaleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "increaseGrpcServers"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  servicestubs.ScaleRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  servicestubs.ScaleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SGServiceMethodDescriptorSupplier("increaseGrpcServers"))
              .build();
        }
      }
    }
    return getIncreaseGrpcServersMethod;
  }

  private static volatile io.grpc.MethodDescriptor<servicestubs.ScaleRequest,
      servicestubs.ScaleResponse> getDecreaseGrpcServersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "decreaseGrpcServers",
      requestType = servicestubs.ScaleRequest.class,
      responseType = servicestubs.ScaleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<servicestubs.ScaleRequest,
      servicestubs.ScaleResponse> getDecreaseGrpcServersMethod() {
    io.grpc.MethodDescriptor<servicestubs.ScaleRequest, servicestubs.ScaleResponse> getDecreaseGrpcServersMethod;
    if ((getDecreaseGrpcServersMethod = SGServiceGrpc.getDecreaseGrpcServersMethod) == null) {
      synchronized (SGServiceGrpc.class) {
        if ((getDecreaseGrpcServersMethod = SGServiceGrpc.getDecreaseGrpcServersMethod) == null) {
          SGServiceGrpc.getDecreaseGrpcServersMethod = getDecreaseGrpcServersMethod =
              io.grpc.MethodDescriptor.<servicestubs.ScaleRequest, servicestubs.ScaleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "decreaseGrpcServers"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  servicestubs.ScaleRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  servicestubs.ScaleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SGServiceMethodDescriptorSupplier("decreaseGrpcServers"))
              .build();
        }
      }
    }
    return getDecreaseGrpcServersMethod;
  }

  private static volatile io.grpc.MethodDescriptor<servicestubs.ScaleRequest,
      servicestubs.ScaleResponse> getIncreaseProcessingInstancesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "increaseProcessingInstances",
      requestType = servicestubs.ScaleRequest.class,
      responseType = servicestubs.ScaleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<servicestubs.ScaleRequest,
      servicestubs.ScaleResponse> getIncreaseProcessingInstancesMethod() {
    io.grpc.MethodDescriptor<servicestubs.ScaleRequest, servicestubs.ScaleResponse> getIncreaseProcessingInstancesMethod;
    if ((getIncreaseProcessingInstancesMethod = SGServiceGrpc.getIncreaseProcessingInstancesMethod) == null) {
      synchronized (SGServiceGrpc.class) {
        if ((getIncreaseProcessingInstancesMethod = SGServiceGrpc.getIncreaseProcessingInstancesMethod) == null) {
          SGServiceGrpc.getIncreaseProcessingInstancesMethod = getIncreaseProcessingInstancesMethod =
              io.grpc.MethodDescriptor.<servicestubs.ScaleRequest, servicestubs.ScaleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "increaseProcessingInstances"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  servicestubs.ScaleRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  servicestubs.ScaleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SGServiceMethodDescriptorSupplier("increaseProcessingInstances"))
              .build();
        }
      }
    }
    return getIncreaseProcessingInstancesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<servicestubs.ScaleRequest,
      servicestubs.ScaleResponse> getDecreaseProcessingInstancesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "decreaseProcessingInstances",
      requestType = servicestubs.ScaleRequest.class,
      responseType = servicestubs.ScaleResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<servicestubs.ScaleRequest,
      servicestubs.ScaleResponse> getDecreaseProcessingInstancesMethod() {
    io.grpc.MethodDescriptor<servicestubs.ScaleRequest, servicestubs.ScaleResponse> getDecreaseProcessingInstancesMethod;
    if ((getDecreaseProcessingInstancesMethod = SGServiceGrpc.getDecreaseProcessingInstancesMethod) == null) {
      synchronized (SGServiceGrpc.class) {
        if ((getDecreaseProcessingInstancesMethod = SGServiceGrpc.getDecreaseProcessingInstancesMethod) == null) {
          SGServiceGrpc.getDecreaseProcessingInstancesMethod = getDecreaseProcessingInstancesMethod =
              io.grpc.MethodDescriptor.<servicestubs.ScaleRequest, servicestubs.ScaleResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "decreaseProcessingInstances"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  servicestubs.ScaleRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  servicestubs.ScaleResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SGServiceMethodDescriptorSupplier("decreaseProcessingInstances"))
              .build();
        }
      }
    }
    return getDecreaseProcessingInstancesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SGServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SGServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SGServiceStub>() {
        @java.lang.Override
        public SGServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SGServiceStub(channel, callOptions);
        }
      };
    return SGServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static SGServiceBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SGServiceBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SGServiceBlockingV2Stub>() {
        @java.lang.Override
        public SGServiceBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SGServiceBlockingV2Stub(channel, callOptions);
        }
      };
    return SGServiceBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SGServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SGServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SGServiceBlockingStub>() {
        @java.lang.Override
        public SGServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SGServiceBlockingStub(channel, callOptions);
        }
      };
    return SGServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SGServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SGServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SGServiceFutureStub>() {
        @java.lang.Override
        public SGServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SGServiceFutureStub(channel, callOptions);
        }
      };
    return SGServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void increaseGrpcServers(servicestubs.ScaleRequest request,
        io.grpc.stub.StreamObserver<servicestubs.ScaleResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getIncreaseGrpcServersMethod(), responseObserver);
    }

    /**
     */
    default void decreaseGrpcServers(servicestubs.ScaleRequest request,
        io.grpc.stub.StreamObserver<servicestubs.ScaleResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDecreaseGrpcServersMethod(), responseObserver);
    }

    /**
     */
    default void increaseProcessingInstances(servicestubs.ScaleRequest request,
        io.grpc.stub.StreamObserver<servicestubs.ScaleResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getIncreaseProcessingInstancesMethod(), responseObserver);
    }

    /**
     */
    default void decreaseProcessingInstances(servicestubs.ScaleRequest request,
        io.grpc.stub.StreamObserver<servicestubs.ScaleResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDecreaseProcessingInstancesMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service SGService.
   */
  public static abstract class SGServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return SGServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service SGService.
   */
  public static final class SGServiceStub
      extends io.grpc.stub.AbstractAsyncStub<SGServiceStub> {
    private SGServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SGServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SGServiceStub(channel, callOptions);
    }

    /**
     */
    public void increaseGrpcServers(servicestubs.ScaleRequest request,
        io.grpc.stub.StreamObserver<servicestubs.ScaleResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getIncreaseGrpcServersMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void decreaseGrpcServers(servicestubs.ScaleRequest request,
        io.grpc.stub.StreamObserver<servicestubs.ScaleResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDecreaseGrpcServersMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void increaseProcessingInstances(servicestubs.ScaleRequest request,
        io.grpc.stub.StreamObserver<servicestubs.ScaleResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getIncreaseProcessingInstancesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void decreaseProcessingInstances(servicestubs.ScaleRequest request,
        io.grpc.stub.StreamObserver<servicestubs.ScaleResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDecreaseProcessingInstancesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service SGService.
   */
  public static final class SGServiceBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<SGServiceBlockingV2Stub> {
    private SGServiceBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SGServiceBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SGServiceBlockingV2Stub(channel, callOptions);
    }

    /**
     */
    public servicestubs.ScaleResponse increaseGrpcServers(servicestubs.ScaleRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getIncreaseGrpcServersMethod(), getCallOptions(), request);
    }

    /**
     */
    public servicestubs.ScaleResponse decreaseGrpcServers(servicestubs.ScaleRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getDecreaseGrpcServersMethod(), getCallOptions(), request);
    }

    /**
     */
    public servicestubs.ScaleResponse increaseProcessingInstances(servicestubs.ScaleRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getIncreaseProcessingInstancesMethod(), getCallOptions(), request);
    }

    /**
     */
    public servicestubs.ScaleResponse decreaseProcessingInstances(servicestubs.ScaleRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getDecreaseProcessingInstancesMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service SGService.
   */
  public static final class SGServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<SGServiceBlockingStub> {
    private SGServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SGServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SGServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public servicestubs.ScaleResponse increaseGrpcServers(servicestubs.ScaleRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getIncreaseGrpcServersMethod(), getCallOptions(), request);
    }

    /**
     */
    public servicestubs.ScaleResponse decreaseGrpcServers(servicestubs.ScaleRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDecreaseGrpcServersMethod(), getCallOptions(), request);
    }

    /**
     */
    public servicestubs.ScaleResponse increaseProcessingInstances(servicestubs.ScaleRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getIncreaseProcessingInstancesMethod(), getCallOptions(), request);
    }

    /**
     */
    public servicestubs.ScaleResponse decreaseProcessingInstances(servicestubs.ScaleRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDecreaseProcessingInstancesMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service SGService.
   */
  public static final class SGServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<SGServiceFutureStub> {
    private SGServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SGServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SGServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<servicestubs.ScaleResponse> increaseGrpcServers(
        servicestubs.ScaleRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getIncreaseGrpcServersMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<servicestubs.ScaleResponse> decreaseGrpcServers(
        servicestubs.ScaleRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDecreaseGrpcServersMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<servicestubs.ScaleResponse> increaseProcessingInstances(
        servicestubs.ScaleRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getIncreaseProcessingInstancesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<servicestubs.ScaleResponse> decreaseProcessingInstances(
        servicestubs.ScaleRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDecreaseProcessingInstancesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_INCREASE_GRPC_SERVERS = 0;
  private static final int METHODID_DECREASE_GRPC_SERVERS = 1;
  private static final int METHODID_INCREASE_PROCESSING_INSTANCES = 2;
  private static final int METHODID_DECREASE_PROCESSING_INSTANCES = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_INCREASE_GRPC_SERVERS:
          serviceImpl.increaseGrpcServers((servicestubs.ScaleRequest) request,
              (io.grpc.stub.StreamObserver<servicestubs.ScaleResponse>) responseObserver);
          break;
        case METHODID_DECREASE_GRPC_SERVERS:
          serviceImpl.decreaseGrpcServers((servicestubs.ScaleRequest) request,
              (io.grpc.stub.StreamObserver<servicestubs.ScaleResponse>) responseObserver);
          break;
        case METHODID_INCREASE_PROCESSING_INSTANCES:
          serviceImpl.increaseProcessingInstances((servicestubs.ScaleRequest) request,
              (io.grpc.stub.StreamObserver<servicestubs.ScaleResponse>) responseObserver);
          break;
        case METHODID_DECREASE_PROCESSING_INSTANCES:
          serviceImpl.decreaseProcessingInstances((servicestubs.ScaleRequest) request,
              (io.grpc.stub.StreamObserver<servicestubs.ScaleResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getIncreaseGrpcServersMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              servicestubs.ScaleRequest,
              servicestubs.ScaleResponse>(
                service, METHODID_INCREASE_GRPC_SERVERS)))
        .addMethod(
          getDecreaseGrpcServersMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              servicestubs.ScaleRequest,
              servicestubs.ScaleResponse>(
                service, METHODID_DECREASE_GRPC_SERVERS)))
        .addMethod(
          getIncreaseProcessingInstancesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              servicestubs.ScaleRequest,
              servicestubs.ScaleResponse>(
                service, METHODID_INCREASE_PROCESSING_INSTANCES)))
        .addMethod(
          getDecreaseProcessingInstancesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              servicestubs.ScaleRequest,
              servicestubs.ScaleResponse>(
                service, METHODID_DECREASE_PROCESSING_INSTANCES)))
        .build();
  }

  private static abstract class SGServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SGServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return servicestubs.Cn2026Labels.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SGService");
    }
  }

  private static final class SGServiceFileDescriptorSupplier
      extends SGServiceBaseDescriptorSupplier {
    SGServiceFileDescriptorSupplier() {}
  }

  private static final class SGServiceMethodDescriptorSupplier
      extends SGServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    SGServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SGServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SGServiceFileDescriptorSupplier())
              .addMethod(getIncreaseGrpcServersMethod())
              .addMethod(getDecreaseGrpcServersMethod())
              .addMethod(getIncreaseProcessingInstancesMethod())
              .addMethod(getDecreaseProcessingInstancesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
