package CnText;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.18.0)",
    comments = "Source: rpcService.proto")
public final class CnTextGrpc {

  private CnTextGrpc() {}

  public static final String SERVICE_NAME = "CnText.CnText";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<CnText.Login,
      CnText.Session> getStartMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "start",
      requestType = CnText.Login.class,
      responseType = CnText.Session.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<CnText.Login,
      CnText.Session> getStartMethod() {
    io.grpc.MethodDescriptor<CnText.Login, CnText.Session> getStartMethod;
    if ((getStartMethod = CnTextGrpc.getStartMethod) == null) {
      synchronized (CnTextGrpc.class) {
        if ((getStartMethod = CnTextGrpc.getStartMethod) == null) {
          CnTextGrpc.getStartMethod = getStartMethod = 
              io.grpc.MethodDescriptor.<CnText.Login, CnText.Session>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "CnText.CnText", "start"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  CnText.Login.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  CnText.Session.getDefaultInstance()))
                  .setSchemaDescriptor(new CnTextMethodDescriptorSupplier("start"))
                  .build();
          }
        }
     }
     return getStartMethod;
  }

  private static volatile io.grpc.MethodDescriptor<CnText.Session,
      CnText.CloseResponse> getCloseMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "close",
      requestType = CnText.Session.class,
      responseType = CnText.CloseResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<CnText.Session,
      CnText.CloseResponse> getCloseMethod() {
    io.grpc.MethodDescriptor<CnText.Session, CnText.CloseResponse> getCloseMethod;
    if ((getCloseMethod = CnTextGrpc.getCloseMethod) == null) {
      synchronized (CnTextGrpc.class) {
        if ((getCloseMethod = CnTextGrpc.getCloseMethod) == null) {
          CnTextGrpc.getCloseMethod = getCloseMethod = 
              io.grpc.MethodDescriptor.<CnText.Session, CnText.CloseResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "CnText.CnText", "close"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  CnText.Session.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  CnText.CloseResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new CnTextMethodDescriptorSupplier("close"))
                  .build();
          }
        }
     }
     return getCloseMethod;
  }

  private static volatile io.grpc.MethodDescriptor<CnText.UploadRequest,
      CnText.UploadRequestResponse> getUploadMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "upload",
      requestType = CnText.UploadRequest.class,
      responseType = CnText.UploadRequestResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<CnText.UploadRequest,
      CnText.UploadRequestResponse> getUploadMethod() {
    io.grpc.MethodDescriptor<CnText.UploadRequest, CnText.UploadRequestResponse> getUploadMethod;
    if ((getUploadMethod = CnTextGrpc.getUploadMethod) == null) {
      synchronized (CnTextGrpc.class) {
        if ((getUploadMethod = CnTextGrpc.getUploadMethod) == null) {
          CnTextGrpc.getUploadMethod = getUploadMethod = 
              io.grpc.MethodDescriptor.<CnText.UploadRequest, CnText.UploadRequestResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "CnText.CnText", "upload"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  CnText.UploadRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  CnText.UploadRequestResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new CnTextMethodDescriptorSupplier("upload"))
                  .build();
          }
        }
     }
     return getUploadMethod;
  }

  private static volatile io.grpc.MethodDescriptor<CnText.TranslateRequest,
      CnText.TranslateResponse> getTranslateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "translate",
      requestType = CnText.TranslateRequest.class,
      responseType = CnText.TranslateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<CnText.TranslateRequest,
      CnText.TranslateResponse> getTranslateMethod() {
    io.grpc.MethodDescriptor<CnText.TranslateRequest, CnText.TranslateResponse> getTranslateMethod;
    if ((getTranslateMethod = CnTextGrpc.getTranslateMethod) == null) {
      synchronized (CnTextGrpc.class) {
        if ((getTranslateMethod = CnTextGrpc.getTranslateMethod) == null) {
          CnTextGrpc.getTranslateMethod = getTranslateMethod = 
              io.grpc.MethodDescriptor.<CnText.TranslateRequest, CnText.TranslateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "CnText.CnText", "translate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  CnText.TranslateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  CnText.TranslateResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new CnTextMethodDescriptorSupplier("translate"))
                  .build();
          }
        }
     }
     return getTranslateMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CnTextStub newStub(io.grpc.Channel channel) {
    return new CnTextStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CnTextBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new CnTextBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CnTextFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new CnTextFutureStub(channel);
  }

  /**
   */
  public static abstract class CnTextImplBase implements io.grpc.BindableService {

    /**
     */
    public void start(CnText.Login request,
        io.grpc.stub.StreamObserver<CnText.Session> responseObserver) {
      asyncUnimplementedUnaryCall(getStartMethod(), responseObserver);
    }

    /**
     */
    public void close(CnText.Session request,
        io.grpc.stub.StreamObserver<CnText.CloseResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getCloseMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<CnText.UploadRequest> upload(
        io.grpc.stub.StreamObserver<CnText.UploadRequestResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(getUploadMethod(), responseObserver);
    }

    /**
     */
    public void translate(CnText.TranslateRequest request,
        io.grpc.stub.StreamObserver<CnText.TranslateResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getTranslateMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getStartMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                CnText.Login,
                CnText.Session>(
                  this, METHODID_START)))
          .addMethod(
            getCloseMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                CnText.Session,
                CnText.CloseResponse>(
                  this, METHODID_CLOSE)))
          .addMethod(
            getUploadMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                CnText.UploadRequest,
                CnText.UploadRequestResponse>(
                  this, METHODID_UPLOAD)))
          .addMethod(
            getTranslateMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                CnText.TranslateRequest,
                CnText.TranslateResponse>(
                  this, METHODID_TRANSLATE)))
          .build();
    }
  }

  /**
   */
  public static final class CnTextStub extends io.grpc.stub.AbstractStub<CnTextStub> {
    private CnTextStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CnTextStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CnTextStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CnTextStub(channel, callOptions);
    }

    /**
     */
    public void start(CnText.Login request,
        io.grpc.stub.StreamObserver<CnText.Session> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getStartMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void close(CnText.Session request,
        io.grpc.stub.StreamObserver<CnText.CloseResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCloseMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<CnText.UploadRequest> upload(
        io.grpc.stub.StreamObserver<CnText.UploadRequestResponse> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getUploadMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public void translate(CnText.TranslateRequest request,
        io.grpc.stub.StreamObserver<CnText.TranslateResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getTranslateMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class CnTextBlockingStub extends io.grpc.stub.AbstractStub<CnTextBlockingStub> {
    private CnTextBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CnTextBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CnTextBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CnTextBlockingStub(channel, callOptions);
    }

    /**
     */
    public CnText.Session start(CnText.Login request) {
      return blockingUnaryCall(
          getChannel(), getStartMethod(), getCallOptions(), request);
    }

    /**
     */
    public CnText.CloseResponse close(CnText.Session request) {
      return blockingUnaryCall(
          getChannel(), getCloseMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<CnText.TranslateResponse> translate(
        CnText.TranslateRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getTranslateMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class CnTextFutureStub extends io.grpc.stub.AbstractStub<CnTextFutureStub> {
    private CnTextFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CnTextFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CnTextFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CnTextFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<CnText.Session> start(
        CnText.Login request) {
      return futureUnaryCall(
          getChannel().newCall(getStartMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<CnText.CloseResponse> close(
        CnText.Session request) {
      return futureUnaryCall(
          getChannel().newCall(getCloseMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_START = 0;
  private static final int METHODID_CLOSE = 1;
  private static final int METHODID_TRANSLATE = 2;
  private static final int METHODID_UPLOAD = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CnTextImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CnTextImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_START:
          serviceImpl.start((CnText.Login) request,
              (io.grpc.stub.StreamObserver<CnText.Session>) responseObserver);
          break;
        case METHODID_CLOSE:
          serviceImpl.close((CnText.Session) request,
              (io.grpc.stub.StreamObserver<CnText.CloseResponse>) responseObserver);
          break;
        case METHODID_TRANSLATE:
          serviceImpl.translate((CnText.TranslateRequest) request,
              (io.grpc.stub.StreamObserver<CnText.TranslateResponse>) responseObserver);
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
        case METHODID_UPLOAD:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.upload(
              (io.grpc.stub.StreamObserver<CnText.UploadRequestResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class CnTextBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CnTextBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return CnText.RpcService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CnText");
    }
  }

  private static final class CnTextFileDescriptorSupplier
      extends CnTextBaseDescriptorSupplier {
    CnTextFileDescriptorSupplier() {}
  }

  private static final class CnTextMethodDescriptorSupplier
      extends CnTextBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    CnTextMethodDescriptorSupplier(String methodName) {
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
      synchronized (CnTextGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CnTextFileDescriptorSupplier())
              .addMethod(getStartMethod())
              .addMethod(getCloseMethod())
              .addMethod(getUploadMethod())
              .addMethod(getTranslateMethod())
              .build();
        }
      }
    }
    return result;
  }
}
