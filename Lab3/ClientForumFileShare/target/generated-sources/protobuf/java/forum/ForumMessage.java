// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: forum.proto

package forum;

/**
 * Protobuf type {@code forum.ForumMessage}
 */
public  final class ForumMessage extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:forum.ForumMessage)
    ForumMessageOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ForumMessage.newBuilder() to construct.
  private ForumMessage(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ForumMessage() {
    fromUser_ = "";
    topicName_ = "";
    txtMsg_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new ForumMessage();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private ForumMessage(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            fromUser_ = s;
            break;
          }
          case 18: {
            java.lang.String s = input.readStringRequireUtf8();

            topicName_ = s;
            break;
          }
          case 26: {
            java.lang.String s = input.readStringRequireUtf8();

            txtMsg_ = s;
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return forum.ForumOuterClass.internal_static_forum_ForumMessage_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return forum.ForumOuterClass.internal_static_forum_ForumMessage_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            forum.ForumMessage.class, forum.ForumMessage.Builder.class);
  }

  public static final int FROMUSER_FIELD_NUMBER = 1;
  private volatile java.lang.Object fromUser_;
  /**
   * <code>string fromUser = 1;</code>
   * @return The fromUser.
   */
  public java.lang.String getFromUser() {
    java.lang.Object ref = fromUser_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      fromUser_ = s;
      return s;
    }
  }
  /**
   * <code>string fromUser = 1;</code>
   * @return The bytes for fromUser.
   */
  public com.google.protobuf.ByteString
      getFromUserBytes() {
    java.lang.Object ref = fromUser_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      fromUser_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int TOPICNAME_FIELD_NUMBER = 2;
  private volatile java.lang.Object topicName_;
  /**
   * <code>string topicName = 2;</code>
   * @return The topicName.
   */
  public java.lang.String getTopicName() {
    java.lang.Object ref = topicName_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      topicName_ = s;
      return s;
    }
  }
  /**
   * <code>string topicName = 2;</code>
   * @return The bytes for topicName.
   */
  public com.google.protobuf.ByteString
      getTopicNameBytes() {
    java.lang.Object ref = topicName_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      topicName_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int TXTMSG_FIELD_NUMBER = 3;
  private volatile java.lang.Object txtMsg_;
  /**
   * <code>string txtMsg = 3;</code>
   * @return The txtMsg.
   */
  public java.lang.String getTxtMsg() {
    java.lang.Object ref = txtMsg_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      txtMsg_ = s;
      return s;
    }
  }
  /**
   * <code>string txtMsg = 3;</code>
   * @return The bytes for txtMsg.
   */
  public com.google.protobuf.ByteString
      getTxtMsgBytes() {
    java.lang.Object ref = txtMsg_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      txtMsg_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!getFromUserBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, fromUser_);
    }
    if (!getTopicNameBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, topicName_);
    }
    if (!getTxtMsgBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, txtMsg_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getFromUserBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, fromUser_);
    }
    if (!getTopicNameBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, topicName_);
    }
    if (!getTxtMsgBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, txtMsg_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof forum.ForumMessage)) {
      return super.equals(obj);
    }
    forum.ForumMessage other = (forum.ForumMessage) obj;

    if (!getFromUser()
        .equals(other.getFromUser())) return false;
    if (!getTopicName()
        .equals(other.getTopicName())) return false;
    if (!getTxtMsg()
        .equals(other.getTxtMsg())) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + FROMUSER_FIELD_NUMBER;
    hash = (53 * hash) + getFromUser().hashCode();
    hash = (37 * hash) + TOPICNAME_FIELD_NUMBER;
    hash = (53 * hash) + getTopicName().hashCode();
    hash = (37 * hash) + TXTMSG_FIELD_NUMBER;
    hash = (53 * hash) + getTxtMsg().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static forum.ForumMessage parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static forum.ForumMessage parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static forum.ForumMessage parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static forum.ForumMessage parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static forum.ForumMessage parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static forum.ForumMessage parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static forum.ForumMessage parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static forum.ForumMessage parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static forum.ForumMessage parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static forum.ForumMessage parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static forum.ForumMessage parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static forum.ForumMessage parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(forum.ForumMessage prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code forum.ForumMessage}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:forum.ForumMessage)
      forum.ForumMessageOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return forum.ForumOuterClass.internal_static_forum_ForumMessage_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return forum.ForumOuterClass.internal_static_forum_ForumMessage_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              forum.ForumMessage.class, forum.ForumMessage.Builder.class);
    }

    // Construct using forum.ForumMessage.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      fromUser_ = "";

      topicName_ = "";

      txtMsg_ = "";

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return forum.ForumOuterClass.internal_static_forum_ForumMessage_descriptor;
    }

    @java.lang.Override
    public forum.ForumMessage getDefaultInstanceForType() {
      return forum.ForumMessage.getDefaultInstance();
    }

    @java.lang.Override
    public forum.ForumMessage build() {
      forum.ForumMessage result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public forum.ForumMessage buildPartial() {
      forum.ForumMessage result = new forum.ForumMessage(this);
      result.fromUser_ = fromUser_;
      result.topicName_ = topicName_;
      result.txtMsg_ = txtMsg_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof forum.ForumMessage) {
        return mergeFrom((forum.ForumMessage)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(forum.ForumMessage other) {
      if (other == forum.ForumMessage.getDefaultInstance()) return this;
      if (!other.getFromUser().isEmpty()) {
        fromUser_ = other.fromUser_;
        onChanged();
      }
      if (!other.getTopicName().isEmpty()) {
        topicName_ = other.topicName_;
        onChanged();
      }
      if (!other.getTxtMsg().isEmpty()) {
        txtMsg_ = other.txtMsg_;
        onChanged();
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      forum.ForumMessage parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (forum.ForumMessage) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object fromUser_ = "";
    /**
     * <code>string fromUser = 1;</code>
     * @return The fromUser.
     */
    public java.lang.String getFromUser() {
      java.lang.Object ref = fromUser_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        fromUser_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string fromUser = 1;</code>
     * @return The bytes for fromUser.
     */
    public com.google.protobuf.ByteString
        getFromUserBytes() {
      java.lang.Object ref = fromUser_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        fromUser_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string fromUser = 1;</code>
     * @param value The fromUser to set.
     * @return This builder for chaining.
     */
    public Builder setFromUser(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      fromUser_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string fromUser = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearFromUser() {
      
      fromUser_ = getDefaultInstance().getFromUser();
      onChanged();
      return this;
    }
    /**
     * <code>string fromUser = 1;</code>
     * @param value The bytes for fromUser to set.
     * @return This builder for chaining.
     */
    public Builder setFromUserBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      fromUser_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object topicName_ = "";
    /**
     * <code>string topicName = 2;</code>
     * @return The topicName.
     */
    public java.lang.String getTopicName() {
      java.lang.Object ref = topicName_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        topicName_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string topicName = 2;</code>
     * @return The bytes for topicName.
     */
    public com.google.protobuf.ByteString
        getTopicNameBytes() {
      java.lang.Object ref = topicName_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        topicName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string topicName = 2;</code>
     * @param value The topicName to set.
     * @return This builder for chaining.
     */
    public Builder setTopicName(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      topicName_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string topicName = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearTopicName() {
      
      topicName_ = getDefaultInstance().getTopicName();
      onChanged();
      return this;
    }
    /**
     * <code>string topicName = 2;</code>
     * @param value The bytes for topicName to set.
     * @return This builder for chaining.
     */
    public Builder setTopicNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      topicName_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object txtMsg_ = "";
    /**
     * <code>string txtMsg = 3;</code>
     * @return The txtMsg.
     */
    public java.lang.String getTxtMsg() {
      java.lang.Object ref = txtMsg_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        txtMsg_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string txtMsg = 3;</code>
     * @return The bytes for txtMsg.
     */
    public com.google.protobuf.ByteString
        getTxtMsgBytes() {
      java.lang.Object ref = txtMsg_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        txtMsg_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string txtMsg = 3;</code>
     * @param value The txtMsg to set.
     * @return This builder for chaining.
     */
    public Builder setTxtMsg(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      txtMsg_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string txtMsg = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearTxtMsg() {
      
      txtMsg_ = getDefaultInstance().getTxtMsg();
      onChanged();
      return this;
    }
    /**
     * <code>string txtMsg = 3;</code>
     * @param value The bytes for txtMsg to set.
     * @return This builder for chaining.
     */
    public Builder setTxtMsgBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      txtMsg_ = value;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:forum.ForumMessage)
  }

  // @@protoc_insertion_point(class_scope:forum.ForumMessage)
  private static final forum.ForumMessage DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new forum.ForumMessage();
  }

  public static forum.ForumMessage getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ForumMessage>
      PARSER = new com.google.protobuf.AbstractParser<ForumMessage>() {
    @java.lang.Override
    public ForumMessage parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new ForumMessage(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<ForumMessage> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ForumMessage> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public forum.ForumMessage getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

