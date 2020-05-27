package forum;

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
 * The Forum service definition.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.28.1)",
    comments = "Source: forum.proto")
public final class ForumGrpc {

  private ForumGrpc() {}

  public static final String SERVICE_NAME = "forum.Forum";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<forum.SubscribeUnSubscribe,
      forum.ForumMessage> getTopicSubscribeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "TopicSubscribe",
      requestType = forum.SubscribeUnSubscribe.class,
      responseType = forum.ForumMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<forum.SubscribeUnSubscribe,
      forum.ForumMessage> getTopicSubscribeMethod() {
    io.grpc.MethodDescriptor<forum.SubscribeUnSubscribe, forum.ForumMessage> getTopicSubscribeMethod;
    if ((getTopicSubscribeMethod = ForumGrpc.getTopicSubscribeMethod) == null) {
      synchronized (ForumGrpc.class) {
        if ((getTopicSubscribeMethod = ForumGrpc.getTopicSubscribeMethod) == null) {
          ForumGrpc.getTopicSubscribeMethod = getTopicSubscribeMethod =
              io.grpc.MethodDescriptor.<forum.SubscribeUnSubscribe, forum.ForumMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "TopicSubscribe"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  forum.SubscribeUnSubscribe.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  forum.ForumMessage.getDefaultInstance()))
              .setSchemaDescriptor(new ForumMethodDescriptorSupplier("TopicSubscribe"))
              .build();
        }
      }
    }
    return getTopicSubscribeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<forum.SubscribeUnSubscribe,
      com.google.protobuf.Empty> getTopicUnSubscribeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "TopicUnSubscribe",
      requestType = forum.SubscribeUnSubscribe.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<forum.SubscribeUnSubscribe,
      com.google.protobuf.Empty> getTopicUnSubscribeMethod() {
    io.grpc.MethodDescriptor<forum.SubscribeUnSubscribe, com.google.protobuf.Empty> getTopicUnSubscribeMethod;
    if ((getTopicUnSubscribeMethod = ForumGrpc.getTopicUnSubscribeMethod) == null) {
      synchronized (ForumGrpc.class) {
        if ((getTopicUnSubscribeMethod = ForumGrpc.getTopicUnSubscribeMethod) == null) {
          ForumGrpc.getTopicUnSubscribeMethod = getTopicUnSubscribeMethod =
              io.grpc.MethodDescriptor.<forum.SubscribeUnSubscribe, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "TopicUnSubscribe"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  forum.SubscribeUnSubscribe.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ForumMethodDescriptorSupplier("TopicUnSubscribe"))
              .build();
        }
      }
    }
    return getTopicUnSubscribeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      forum.ExistingTopics> getGetAllTopicsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getAllTopics",
      requestType = com.google.protobuf.Empty.class,
      responseType = forum.ExistingTopics.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      forum.ExistingTopics> getGetAllTopicsMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, forum.ExistingTopics> getGetAllTopicsMethod;
    if ((getGetAllTopicsMethod = ForumGrpc.getGetAllTopicsMethod) == null) {
      synchronized (ForumGrpc.class) {
        if ((getGetAllTopicsMethod = ForumGrpc.getGetAllTopicsMethod) == null) {
          ForumGrpc.getGetAllTopicsMethod = getGetAllTopicsMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, forum.ExistingTopics>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getAllTopics"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  forum.ExistingTopics.getDefaultInstance()))
              .setSchemaDescriptor(new ForumMethodDescriptorSupplier("getAllTopics"))
              .build();
        }
      }
    }
    return getGetAllTopicsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<forum.ForumMessage,
      com.google.protobuf.Empty> getMessagePublishMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "MessagePublish",
      requestType = forum.ForumMessage.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<forum.ForumMessage,
      com.google.protobuf.Empty> getMessagePublishMethod() {
    io.grpc.MethodDescriptor<forum.ForumMessage, com.google.protobuf.Empty> getMessagePublishMethod;
    if ((getMessagePublishMethod = ForumGrpc.getMessagePublishMethod) == null) {
      synchronized (ForumGrpc.class) {
        if ((getMessagePublishMethod = ForumGrpc.getMessagePublishMethod) == null) {
          ForumGrpc.getMessagePublishMethod = getMessagePublishMethod =
              io.grpc.MethodDescriptor.<forum.ForumMessage, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "MessagePublish"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  forum.ForumMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ForumMethodDescriptorSupplier("MessagePublish"))
              .build();
        }
      }
    }
    return getMessagePublishMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ForumStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ForumStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ForumStub>() {
        @java.lang.Override
        public ForumStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ForumStub(channel, callOptions);
        }
      };
    return ForumStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ForumBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ForumBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ForumBlockingStub>() {
        @java.lang.Override
        public ForumBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ForumBlockingStub(channel, callOptions);
        }
      };
    return ForumBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ForumFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ForumFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ForumFutureStub>() {
        @java.lang.Override
        public ForumFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ForumFutureStub(channel, callOptions);
        }
      };
    return ForumFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * The Forum service definition.
   * </pre>
   */
  public static abstract class ForumImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * subscribe a topic
     * </pre>
     */
    public void topicSubscribe(forum.SubscribeUnSubscribe request,
        io.grpc.stub.StreamObserver<forum.ForumMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getTopicSubscribeMethod(), responseObserver);
    }

    /**
     * <pre>
     *Unsubscrive a topic
     * </pre>
     */
    public void topicUnSubscribe(forum.SubscribeUnSubscribe request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getTopicUnSubscribeMethod(), responseObserver);
    }

    /**
     * <pre>
     * get all topics in server
     * </pre>
     */
    public void getAllTopics(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<forum.ExistingTopics> responseObserver) {
      asyncUnimplementedUnaryCall(getGetAllTopicsMethod(), responseObserver);
    }

    /**
     * <pre>
     * Send a message to a topic
     * </pre>
     */
    public void messagePublish(forum.ForumMessage request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getMessagePublishMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getTopicSubscribeMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                forum.SubscribeUnSubscribe,
                forum.ForumMessage>(
                  this, METHODID_TOPIC_SUBSCRIBE)))
          .addMethod(
            getTopicUnSubscribeMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                forum.SubscribeUnSubscribe,
                com.google.protobuf.Empty>(
                  this, METHODID_TOPIC_UN_SUBSCRIBE)))
          .addMethod(
            getGetAllTopicsMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                forum.ExistingTopics>(
                  this, METHODID_GET_ALL_TOPICS)))
          .addMethod(
            getMessagePublishMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                forum.ForumMessage,
                com.google.protobuf.Empty>(
                  this, METHODID_MESSAGE_PUBLISH)))
          .build();
    }
  }

  /**
   * <pre>
   * The Forum service definition.
   * </pre>
   */
  public static final class ForumStub extends io.grpc.stub.AbstractAsyncStub<ForumStub> {
    private ForumStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ForumStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ForumStub(channel, callOptions);
    }

    /**
     * <pre>
     * subscribe a topic
     * </pre>
     */
    public void topicSubscribe(forum.SubscribeUnSubscribe request,
        io.grpc.stub.StreamObserver<forum.ForumMessage> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getTopicSubscribeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     *Unsubscrive a topic
     * </pre>
     */
    public void topicUnSubscribe(forum.SubscribeUnSubscribe request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getTopicUnSubscribeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * get all topics in server
     * </pre>
     */
    public void getAllTopics(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<forum.ExistingTopics> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetAllTopicsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Send a message to a topic
     * </pre>
     */
    public void messagePublish(forum.ForumMessage request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getMessagePublishMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * The Forum service definition.
   * </pre>
   */
  public static final class ForumBlockingStub extends io.grpc.stub.AbstractBlockingStub<ForumBlockingStub> {
    private ForumBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ForumBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ForumBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * subscribe a topic
     * </pre>
     */
    public java.util.Iterator<forum.ForumMessage> topicSubscribe(
        forum.SubscribeUnSubscribe request) {
      return blockingServerStreamingCall(
          getChannel(), getTopicSubscribeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     *Unsubscrive a topic
     * </pre>
     */
    public com.google.protobuf.Empty topicUnSubscribe(forum.SubscribeUnSubscribe request) {
      return blockingUnaryCall(
          getChannel(), getTopicUnSubscribeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * get all topics in server
     * </pre>
     */
    public forum.ExistingTopics getAllTopics(com.google.protobuf.Empty request) {
      return blockingUnaryCall(
          getChannel(), getGetAllTopicsMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Send a message to a topic
     * </pre>
     */
    public com.google.protobuf.Empty messagePublish(forum.ForumMessage request) {
      return blockingUnaryCall(
          getChannel(), getMessagePublishMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The Forum service definition.
   * </pre>
   */
  public static final class ForumFutureStub extends io.grpc.stub.AbstractFutureStub<ForumFutureStub> {
    private ForumFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ForumFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ForumFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *Unsubscrive a topic
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> topicUnSubscribe(
        forum.SubscribeUnSubscribe request) {
      return futureUnaryCall(
          getChannel().newCall(getTopicUnSubscribeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * get all topics in server
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<forum.ExistingTopics> getAllTopics(
        com.google.protobuf.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getGetAllTopicsMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Send a message to a topic
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> messagePublish(
        forum.ForumMessage request) {
      return futureUnaryCall(
          getChannel().newCall(getMessagePublishMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_TOPIC_SUBSCRIBE = 0;
  private static final int METHODID_TOPIC_UN_SUBSCRIBE = 1;
  private static final int METHODID_GET_ALL_TOPICS = 2;
  private static final int METHODID_MESSAGE_PUBLISH = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ForumImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ForumImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_TOPIC_SUBSCRIBE:
          serviceImpl.topicSubscribe((forum.SubscribeUnSubscribe) request,
              (io.grpc.stub.StreamObserver<forum.ForumMessage>) responseObserver);
          break;
        case METHODID_TOPIC_UN_SUBSCRIBE:
          serviceImpl.topicUnSubscribe((forum.SubscribeUnSubscribe) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        case METHODID_GET_ALL_TOPICS:
          serviceImpl.getAllTopics((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<forum.ExistingTopics>) responseObserver);
          break;
        case METHODID_MESSAGE_PUBLISH:
          serviceImpl.messagePublish((forum.ForumMessage) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
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

  private static abstract class ForumBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ForumBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return forum.ForumOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Forum");
    }
  }

  private static final class ForumFileDescriptorSupplier
      extends ForumBaseDescriptorSupplier {
    ForumFileDescriptorSupplier() {}
  }

  private static final class ForumMethodDescriptorSupplier
      extends ForumBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ForumMethodDescriptorSupplier(String methodName) {
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
      synchronized (ForumGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ForumFileDescriptorSupplier())
              .addMethod(getTopicSubscribeMethod())
              .addMethod(getTopicUnSubscribeMethod())
              .addMethod(getGetAllTopicsMethod())
              .addMethod(getMessagePublishMethod())
              .build();
        }
      }
    }
    return result;
  }
}
