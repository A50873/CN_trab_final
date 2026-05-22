package servicestubs;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class SFServiceGrpc {

  private SFServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "grpcservice.SFService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<servicestubs.ImageChunk,
      servicestubs.SubmitImageResponse> getSubmitImageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "submitImage",
      requestType = servicestubs.ImageChunk.class,
      responseType = servicestubs.SubmitImageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<servicestubs.ImageChunk,
      servicestubs.SubmitImageResponse> getSubmitImageMethod() {
    io.grpc.MethodDescriptor<servicestubs.ImageChunk, servicestubs.SubmitImageResponse> getSubmitImageMethod;
    if ((getSubmitImageMethod = SFServiceGrpc.getSubmitImageMethod) == null) {
      synchronized (SFServiceGrpc.class) {
        if ((getSubmitImageMethod = SFServiceGrpc.getSubmitImageMethod) == null) {
          SFServiceGrpc.getSubmitImageMethod = getSubmitImageMethod =
              io.grpc.MethodDescriptor.<servicestubs.ImageChunk, servicestubs.SubmitImageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "submitImage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  servicestubs.ImageChunk.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  servicestubs.SubmitImageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SFServiceMethodDescriptorSupplier("submitImage"))
              .build();
        }
      }
    }
    return getSubmitImageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<servicestubs.ImageIdRequest,
      servicestubs.GetImageDataResponse> getGetImageDataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getImageData",
      requestType = servicestubs.ImageIdRequest.class,
      responseType = servicestubs.GetImageDataResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<servicestubs.ImageIdRequest,
      servicestubs.GetImageDataResponse> getGetImageDataMethod() {
    io.grpc.MethodDescriptor<servicestubs.ImageIdRequest, servicestubs.GetImageDataResponse> getGetImageDataMethod;
    if ((getGetImageDataMethod = SFServiceGrpc.getGetImageDataMethod) == null) {
      synchronized (SFServiceGrpc.class) {
        if ((getGetImageDataMethod = SFServiceGrpc.getGetImageDataMethod) == null) {
          SFServiceGrpc.getGetImageDataMethod = getGetImageDataMethod =
              io.grpc.MethodDescriptor.<servicestubs.ImageIdRequest, servicestubs.GetImageDataResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getImageData"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  servicestubs.ImageIdRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  servicestubs.GetImageDataResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SFServiceMethodDescriptorSupplier("getImageData"))
              .build();
        }
      }
    }
    return getGetImageDataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<servicestubs.SearchImagesRequest,
      servicestubs.SearchImagesResponse> getSearchImagesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "searchImages",
      requestType = servicestubs.SearchImagesRequest.class,
      responseType = servicestubs.SearchImagesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<servicestubs.SearchImagesRequest,
      servicestubs.SearchImagesResponse> getSearchImagesMethod() {
    io.grpc.MethodDescriptor<servicestubs.SearchImagesRequest, servicestubs.SearchImagesResponse> getSearchImagesMethod;
    if ((getSearchImagesMethod = SFServiceGrpc.getSearchImagesMethod) == null) {
      synchronized (SFServiceGrpc.class) {
        if ((getSearchImagesMethod = SFServiceGrpc.getSearchImagesMethod) == null) {
          SFServiceGrpc.getSearchImagesMethod = getSearchImagesMethod =
              io.grpc.MethodDescriptor.<servicestubs.SearchImagesRequest, servicestubs.SearchImagesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "searchImages"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  servicestubs.SearchImagesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  servicestubs.SearchImagesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SFServiceMethodDescriptorSupplier("searchImages"))
              .build();
        }
      }
    }
    return getSearchImagesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<servicestubs.ListImagesRequest,
      servicestubs.ListImagesResponse> getListImagesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "listImages",
      requestType = servicestubs.ListImagesRequest.class,
      responseType = servicestubs.ListImagesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<servicestubs.ListImagesRequest,
      servicestubs.ListImagesResponse> getListImagesMethod() {
    io.grpc.MethodDescriptor<servicestubs.ListImagesRequest, servicestubs.ListImagesResponse> getListImagesMethod;
    if ((getListImagesMethod = SFServiceGrpc.getListImagesMethod) == null) {
      synchronized (SFServiceGrpc.class) {
        if ((getListImagesMethod = SFServiceGrpc.getListImagesMethod) == null) {
          SFServiceGrpc.getListImagesMethod = getListImagesMethod =
              io.grpc.MethodDescriptor.<servicestubs.ListImagesRequest, servicestubs.ListImagesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "listImages"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  servicestubs.ListImagesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  servicestubs.ListImagesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SFServiceMethodDescriptorSupplier("listImages"))
              .build();
        }
      }
    }
    return getListImagesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<servicestubs.DownloadImageRequest,
      servicestubs.ImageChunk> getDownloadImageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "downloadImage",
      requestType = servicestubs.DownloadImageRequest.class,
      responseType = servicestubs.ImageChunk.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<servicestubs.DownloadImageRequest,
      servicestubs.ImageChunk> getDownloadImageMethod() {
    io.grpc.MethodDescriptor<servicestubs.DownloadImageRequest, servicestubs.ImageChunk> getDownloadImageMethod;
    if ((getDownloadImageMethod = SFServiceGrpc.getDownloadImageMethod) == null) {
      synchronized (SFServiceGrpc.class) {
        if ((getDownloadImageMethod = SFServiceGrpc.getDownloadImageMethod) == null) {
          SFServiceGrpc.getDownloadImageMethod = getDownloadImageMethod =
              io.grpc.MethodDescriptor.<servicestubs.DownloadImageRequest, servicestubs.ImageChunk>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "downloadImage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  servicestubs.DownloadImageRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  servicestubs.ImageChunk.getDefaultInstance()))
              .setSchemaDescriptor(new SFServiceMethodDescriptorSupplier("downloadImage"))
              .build();
        }
      }
    }
    return getDownloadImageMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SFServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SFServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SFServiceStub>() {
        @java.lang.Override
        public SFServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SFServiceStub(channel, callOptions);
        }
      };
    return SFServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static SFServiceBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SFServiceBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SFServiceBlockingV2Stub>() {
        @java.lang.Override
        public SFServiceBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SFServiceBlockingV2Stub(channel, callOptions);
        }
      };
    return SFServiceBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SFServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SFServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SFServiceBlockingStub>() {
        @java.lang.Override
        public SFServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SFServiceBlockingStub(channel, callOptions);
        }
      };
    return SFServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SFServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SFServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SFServiceFutureStub>() {
        @java.lang.Override
        public SFServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SFServiceFutureStub(channel, callOptions);
        }
      };
    return SFServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default io.grpc.stub.StreamObserver<servicestubs.ImageChunk> submitImage(
        io.grpc.stub.StreamObserver<servicestubs.SubmitImageResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getSubmitImageMethod(), responseObserver);
    }

    /**
     */
    default void getImageData(servicestubs.ImageIdRequest request,
        io.grpc.stub.StreamObserver<servicestubs.GetImageDataResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetImageDataMethod(), responseObserver);
    }

    /**
     */
    default void searchImages(servicestubs.SearchImagesRequest request,
        io.grpc.stub.StreamObserver<servicestubs.SearchImagesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSearchImagesMethod(), responseObserver);
    }

    /**
     */
    default void listImages(servicestubs.ListImagesRequest request,
        io.grpc.stub.StreamObserver<servicestubs.ListImagesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListImagesMethod(), responseObserver);
    }

    /**
     */
    default void downloadImage(servicestubs.DownloadImageRequest request,
        io.grpc.stub.StreamObserver<servicestubs.ImageChunk> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDownloadImageMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service SFService.
   */
  public static abstract class SFServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return SFServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service SFService.
   */
  public static final class SFServiceStub
      extends io.grpc.stub.AbstractAsyncStub<SFServiceStub> {
    private SFServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SFServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SFServiceStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<servicestubs.ImageChunk> submitImage(
        io.grpc.stub.StreamObserver<servicestubs.SubmitImageResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncClientStreamingCall(
          getChannel().newCall(getSubmitImageMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public void getImageData(servicestubs.ImageIdRequest request,
        io.grpc.stub.StreamObserver<servicestubs.GetImageDataResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetImageDataMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void searchImages(servicestubs.SearchImagesRequest request,
        io.grpc.stub.StreamObserver<servicestubs.SearchImagesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSearchImagesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listImages(servicestubs.ListImagesRequest request,
        io.grpc.stub.StreamObserver<servicestubs.ListImagesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListImagesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void downloadImage(servicestubs.DownloadImageRequest request,
        io.grpc.stub.StreamObserver<servicestubs.ImageChunk> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getDownloadImageMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service SFService.
   */
  public static final class SFServiceBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<SFServiceBlockingV2Stub> {
    private SFServiceBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SFServiceBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SFServiceBlockingV2Stub(channel, callOptions);
    }

    /**
     */
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/10918")
    public io.grpc.stub.BlockingClientCall<servicestubs.ImageChunk, servicestubs.SubmitImageResponse>
        submitImage() {
      return io.grpc.stub.ClientCalls.blockingClientStreamingCall(
          getChannel(), getSubmitImageMethod(), getCallOptions());
    }

    /**
     */
    public servicestubs.GetImageDataResponse getImageData(servicestubs.ImageIdRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getGetImageDataMethod(), getCallOptions(), request);
    }

    /**
     */
    public servicestubs.SearchImagesResponse searchImages(servicestubs.SearchImagesRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getSearchImagesMethod(), getCallOptions(), request);
    }

    /**
     */
    public servicestubs.ListImagesResponse listImages(servicestubs.ListImagesRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getListImagesMethod(), getCallOptions(), request);
    }

    /**
     */
    @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/10918")
    public io.grpc.stub.BlockingClientCall<?, servicestubs.ImageChunk>
        downloadImage(servicestubs.DownloadImageRequest request) {
      return io.grpc.stub.ClientCalls.blockingV2ServerStreamingCall(
          getChannel(), getDownloadImageMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service SFService.
   */
  public static final class SFServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<SFServiceBlockingStub> {
    private SFServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SFServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SFServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public servicestubs.GetImageDataResponse getImageData(servicestubs.ImageIdRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetImageDataMethod(), getCallOptions(), request);
    }

    /**
     */
    public servicestubs.SearchImagesResponse searchImages(servicestubs.SearchImagesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSearchImagesMethod(), getCallOptions(), request);
    }

    /**
     */
    public servicestubs.ListImagesResponse listImages(servicestubs.ListImagesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListImagesMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<servicestubs.ImageChunk> downloadImage(
        servicestubs.DownloadImageRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getDownloadImageMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service SFService.
   */
  public static final class SFServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<SFServiceFutureStub> {
    private SFServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SFServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SFServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<servicestubs.GetImageDataResponse> getImageData(
        servicestubs.ImageIdRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetImageDataMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<servicestubs.SearchImagesResponse> searchImages(
        servicestubs.SearchImagesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSearchImagesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<servicestubs.ListImagesResponse> listImages(
        servicestubs.ListImagesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListImagesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_IMAGE_DATA = 0;
  private static final int METHODID_SEARCH_IMAGES = 1;
  private static final int METHODID_LIST_IMAGES = 2;
  private static final int METHODID_DOWNLOAD_IMAGE = 3;
  private static final int METHODID_SUBMIT_IMAGE = 4;

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
        case METHODID_GET_IMAGE_DATA:
          serviceImpl.getImageData((servicestubs.ImageIdRequest) request,
              (io.grpc.stub.StreamObserver<servicestubs.GetImageDataResponse>) responseObserver);
          break;
        case METHODID_SEARCH_IMAGES:
          serviceImpl.searchImages((servicestubs.SearchImagesRequest) request,
              (io.grpc.stub.StreamObserver<servicestubs.SearchImagesResponse>) responseObserver);
          break;
        case METHODID_LIST_IMAGES:
          serviceImpl.listImages((servicestubs.ListImagesRequest) request,
              (io.grpc.stub.StreamObserver<servicestubs.ListImagesResponse>) responseObserver);
          break;
        case METHODID_DOWNLOAD_IMAGE:
          serviceImpl.downloadImage((servicestubs.DownloadImageRequest) request,
              (io.grpc.stub.StreamObserver<servicestubs.ImageChunk>) responseObserver);
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
        case METHODID_SUBMIT_IMAGE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.submitImage(
              (io.grpc.stub.StreamObserver<servicestubs.SubmitImageResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getSubmitImageMethod(),
          io.grpc.stub.ServerCalls.asyncClientStreamingCall(
            new MethodHandlers<
              servicestubs.ImageChunk,
              servicestubs.SubmitImageResponse>(
                service, METHODID_SUBMIT_IMAGE)))
        .addMethod(
          getGetImageDataMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              servicestubs.ImageIdRequest,
              servicestubs.GetImageDataResponse>(
                service, METHODID_GET_IMAGE_DATA)))
        .addMethod(
          getSearchImagesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              servicestubs.SearchImagesRequest,
              servicestubs.SearchImagesResponse>(
                service, METHODID_SEARCH_IMAGES)))
        .addMethod(
          getListImagesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              servicestubs.ListImagesRequest,
              servicestubs.ListImagesResponse>(
                service, METHODID_LIST_IMAGES)))
        .addMethod(
          getDownloadImageMethod(),
          io.grpc.stub.ServerCalls.asyncServerStreamingCall(
            new MethodHandlers<
              servicestubs.DownloadImageRequest,
              servicestubs.ImageChunk>(
                service, METHODID_DOWNLOAD_IMAGE)))
        .build();
  }

  private static abstract class SFServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SFServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return servicestubs.Cn2026Labels.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SFService");
    }
  }

  private static final class SFServiceFileDescriptorSupplier
      extends SFServiceBaseDescriptorSupplier {
    SFServiceFileDescriptorSupplier() {}
  }

  private static final class SFServiceMethodDescriptorSupplier
      extends SFServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    SFServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (SFServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SFServiceFileDescriptorSupplier())
              .addMethod(getSubmitImageMethod())
              .addMethod(getGetImageDataMethod())
              .addMethod(getSearchImagesMethod())
              .addMethod(getListImagesMethod())
              .addMethod(getDownloadImageMethod())
              .build();
        }
      }
    }
    return result;
  }
}
