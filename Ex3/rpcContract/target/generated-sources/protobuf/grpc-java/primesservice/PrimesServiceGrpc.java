package primesservice;

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
 * <pre>
 * The greeting service definition.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.18.0)",
    comments = "Source: rpcService.proto")
public final class PrimesServiceGrpc {

  private PrimesServiceGrpc() {}

  public static final String SERVICE_NAME = "primesservice.PrimesService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<primesservice.NumOfPrimes,
      primesservice.Prime> getFindPrimesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "findPrimes",
      requestType = primesservice.NumOfPrimes.class,
      responseType = primesservice.Prime.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<primesservice.NumOfPrimes,
      primesservice.Prime> getFindPrimesMethod() {
    io.grpc.MethodDescriptor<primesservice.NumOfPrimes, primesservice.Prime> getFindPrimesMethod;
    if ((getFindPrimesMethod = PrimesServiceGrpc.getFindPrimesMethod) == null) {
      synchronized (PrimesServiceGrpc.class) {
        if ((getFindPrimesMethod = PrimesServiceGrpc.getFindPrimesMethod) == null) {
          PrimesServiceGrpc.getFindPrimesMethod = getFindPrimesMethod = 
              io.grpc.MethodDescriptor.<primesservice.NumOfPrimes, primesservice.Prime>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "primesservice.PrimesService", "findPrimes"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  primesservice.NumOfPrimes.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  primesservice.Prime.getDefaultInstance()))
                  .setSchemaDescriptor(new PrimesServiceMethodDescriptorSupplier("findPrimes"))
                  .build();
          }
        }
     }
     return getFindPrimesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<primesservice.Number,
      primesservice.SumResult> getAddNumbersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "addNumbers",
      requestType = primesservice.Number.class,
      responseType = primesservice.SumResult.class,
      methodType = io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
  public static io.grpc.MethodDescriptor<primesservice.Number,
      primesservice.SumResult> getAddNumbersMethod() {
    io.grpc.MethodDescriptor<primesservice.Number, primesservice.SumResult> getAddNumbersMethod;
    if ((getAddNumbersMethod = PrimesServiceGrpc.getAddNumbersMethod) == null) {
      synchronized (PrimesServiceGrpc.class) {
        if ((getAddNumbersMethod = PrimesServiceGrpc.getAddNumbersMethod) == null) {
          PrimesServiceGrpc.getAddNumbersMethod = getAddNumbersMethod = 
              io.grpc.MethodDescriptor.<primesservice.Number, primesservice.SumResult>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "primesservice.PrimesService", "addNumbers"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  primesservice.Number.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  primesservice.SumResult.getDefaultInstance()))
                  .setSchemaDescriptor(new PrimesServiceMethodDescriptorSupplier("addNumbers"))
                  .build();
          }
        }
     }
     return getAddNumbersMethod;
  }

  private static volatile io.grpc.MethodDescriptor<primesservice.OperationRequest,
      primesservice.OperationReply> getAddNumbersContMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "addNumbersCont",
      requestType = primesservice.OperationRequest.class,
      responseType = primesservice.OperationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<primesservice.OperationRequest,
      primesservice.OperationReply> getAddNumbersContMethod() {
    io.grpc.MethodDescriptor<primesservice.OperationRequest, primesservice.OperationReply> getAddNumbersContMethod;
    if ((getAddNumbersContMethod = PrimesServiceGrpc.getAddNumbersContMethod) == null) {
      synchronized (PrimesServiceGrpc.class) {
        if ((getAddNumbersContMethod = PrimesServiceGrpc.getAddNumbersContMethod) == null) {
          PrimesServiceGrpc.getAddNumbersContMethod = getAddNumbersContMethod = 
              io.grpc.MethodDescriptor.<primesservice.OperationRequest, primesservice.OperationReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "primesservice.PrimesService", "addNumbersCont"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  primesservice.OperationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  primesservice.OperationReply.getDefaultInstance()))
                  .setSchemaDescriptor(new PrimesServiceMethodDescriptorSupplier("addNumbersCont"))
                  .build();
          }
        }
     }
     return getAddNumbersContMethod;
  }

  private static volatile io.grpc.MethodDescriptor<primesservice.PrimesInterval,
      primesservice.Prime> getFindPrimesIntervalMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "findPrimesInterval",
      requestType = primesservice.PrimesInterval.class,
      responseType = primesservice.Prime.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<primesservice.PrimesInterval,
      primesservice.Prime> getFindPrimesIntervalMethod() {
    io.grpc.MethodDescriptor<primesservice.PrimesInterval, primesservice.Prime> getFindPrimesIntervalMethod;
    if ((getFindPrimesIntervalMethod = PrimesServiceGrpc.getFindPrimesIntervalMethod) == null) {
      synchronized (PrimesServiceGrpc.class) {
        if ((getFindPrimesIntervalMethod = PrimesServiceGrpc.getFindPrimesIntervalMethod) == null) {
          PrimesServiceGrpc.getFindPrimesIntervalMethod = getFindPrimesIntervalMethod = 
              io.grpc.MethodDescriptor.<primesservice.PrimesInterval, primesservice.Prime>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "primesservice.PrimesService", "findPrimesInterval"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  primesservice.PrimesInterval.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  primesservice.Prime.getDefaultInstance()))
                  .setSchemaDescriptor(new PrimesServiceMethodDescriptorSupplier("findPrimesInterval"))
                  .build();
          }
        }
     }
     return getFindPrimesIntervalMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PrimesServiceStub newStub(io.grpc.Channel channel) {
    return new PrimesServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PrimesServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new PrimesServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PrimesServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new PrimesServiceFutureStub(channel);
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static abstract class PrimesServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void findPrimes(primesservice.NumOfPrimes request,
        io.grpc.stub.StreamObserver<primesservice.Prime> responseObserver) {
      asyncUnimplementedUnaryCall(getFindPrimesMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<primesservice.Number> addNumbers(
        io.grpc.stub.StreamObserver<primesservice.SumResult> responseObserver) {
      return asyncUnimplementedStreamingCall(getAddNumbersMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<primesservice.OperationRequest> addNumbersCont(
        io.grpc.stub.StreamObserver<primesservice.OperationReply> responseObserver) {
      return asyncUnimplementedStreamingCall(getAddNumbersContMethod(), responseObserver);
    }

    /**
     */
    public void findPrimesInterval(primesservice.PrimesInterval request,
        io.grpc.stub.StreamObserver<primesservice.Prime> responseObserver) {
      asyncUnimplementedUnaryCall(getFindPrimesIntervalMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getFindPrimesMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                primesservice.NumOfPrimes,
                primesservice.Prime>(
                  this, METHODID_FIND_PRIMES)))
          .addMethod(
            getAddNumbersMethod(),
            asyncClientStreamingCall(
              new MethodHandlers<
                primesservice.Number,
                primesservice.SumResult>(
                  this, METHODID_ADD_NUMBERS)))
          .addMethod(
            getAddNumbersContMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                primesservice.OperationRequest,
                primesservice.OperationReply>(
                  this, METHODID_ADD_NUMBERS_CONT)))
          .addMethod(
            getFindPrimesIntervalMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                primesservice.PrimesInterval,
                primesservice.Prime>(
                  this, METHODID_FIND_PRIMES_INTERVAL)))
          .build();
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class PrimesServiceStub extends io.grpc.stub.AbstractStub<PrimesServiceStub> {
    private PrimesServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PrimesServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PrimesServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PrimesServiceStub(channel, callOptions);
    }

    /**
     */
    public void findPrimes(primesservice.NumOfPrimes request,
        io.grpc.stub.StreamObserver<primesservice.Prime> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getFindPrimesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<primesservice.Number> addNumbers(
        io.grpc.stub.StreamObserver<primesservice.SumResult> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(getAddNumbersMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<primesservice.OperationRequest> addNumbersCont(
        io.grpc.stub.StreamObserver<primesservice.OperationReply> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getAddNumbersContMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public void findPrimesInterval(primesservice.PrimesInterval request,
        io.grpc.stub.StreamObserver<primesservice.Prime> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getFindPrimesIntervalMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class PrimesServiceBlockingStub extends io.grpc.stub.AbstractStub<PrimesServiceBlockingStub> {
    private PrimesServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PrimesServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PrimesServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PrimesServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<primesservice.Prime> findPrimes(
        primesservice.NumOfPrimes request) {
      return blockingServerStreamingCall(
          getChannel(), getFindPrimesMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<primesservice.Prime> findPrimesInterval(
        primesservice.PrimesInterval request) {
      return blockingServerStreamingCall(
          getChannel(), getFindPrimesIntervalMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class PrimesServiceFutureStub extends io.grpc.stub.AbstractStub<PrimesServiceFutureStub> {
    private PrimesServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PrimesServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PrimesServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PrimesServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_FIND_PRIMES = 0;
  private static final int METHODID_FIND_PRIMES_INTERVAL = 1;
  private static final int METHODID_ADD_NUMBERS = 2;
  private static final int METHODID_ADD_NUMBERS_CONT = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PrimesServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PrimesServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_FIND_PRIMES:
          serviceImpl.findPrimes((primesservice.NumOfPrimes) request,
              (io.grpc.stub.StreamObserver<primesservice.Prime>) responseObserver);
          break;
        case METHODID_FIND_PRIMES_INTERVAL:
          serviceImpl.findPrimesInterval((primesservice.PrimesInterval) request,
              (io.grpc.stub.StreamObserver<primesservice.Prime>) responseObserver);
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
        case METHODID_ADD_NUMBERS:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.addNumbers(
              (io.grpc.stub.StreamObserver<primesservice.SumResult>) responseObserver);
        case METHODID_ADD_NUMBERS_CONT:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.addNumbersCont(
              (io.grpc.stub.StreamObserver<primesservice.OperationReply>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class PrimesServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PrimesServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return primesservice.RpcService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PrimesService");
    }
  }

  private static final class PrimesServiceFileDescriptorSupplier
      extends PrimesServiceBaseDescriptorSupplier {
    PrimesServiceFileDescriptorSupplier() {}
  }

  private static final class PrimesServiceMethodDescriptorSupplier
      extends PrimesServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PrimesServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (PrimesServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PrimesServiceFileDescriptorSupplier())
              .addMethod(getFindPrimesMethod())
              .addMethod(getAddNumbersMethod())
              .addMethod(getAddNumbersContMethod())
              .addMethod(getFindPrimesIntervalMethod())
              .build();
        }
      }
    }
    return result;
  }
}
