// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: rpcService.proto

package primesservice;

/**
 * <pre>
 * input message
 * </pre>
 *
 * Protobuf type {@code primesservice.NumOfPrimes}
 */
public  final class NumOfPrimes extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:primesservice.NumOfPrimes)
    NumOfPrimesOrBuilder {
private static final long serialVersionUID = 0L;
  // Use NumOfPrimes.newBuilder() to construct.
  private NumOfPrimes(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private NumOfPrimes() {
    numOfPrimes_ = 0;
    startNum_ = 0;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private NumOfPrimes(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
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
          default: {
            if (!parseUnknownFieldProto3(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
          case 8: {

            numOfPrimes_ = input.readInt32();
            break;
          }
          case 16: {

            startNum_ = input.readInt32();
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
    return primesservice.RpcService.internal_static_primesservice_NumOfPrimes_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return primesservice.RpcService.internal_static_primesservice_NumOfPrimes_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            primesservice.NumOfPrimes.class, primesservice.NumOfPrimes.Builder.class);
  }

  public static final int NUMOFPRIMES_FIELD_NUMBER = 1;
  private int numOfPrimes_;
  /**
   * <code>int32 numOfPrimes = 1;</code>
   */
  public int getNumOfPrimes() {
    return numOfPrimes_;
  }

  public static final int STARTNUM_FIELD_NUMBER = 2;
  private int startNum_;
  /**
   * <code>int32 startNum = 2;</code>
   */
  public int getStartNum() {
    return startNum_;
  }

  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (numOfPrimes_ != 0) {
      output.writeInt32(1, numOfPrimes_);
    }
    if (startNum_ != 0) {
      output.writeInt32(2, startNum_);
    }
    unknownFields.writeTo(output);
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (numOfPrimes_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, numOfPrimes_);
    }
    if (startNum_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(2, startNum_);
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
    if (!(obj instanceof primesservice.NumOfPrimes)) {
      return super.equals(obj);
    }
    primesservice.NumOfPrimes other = (primesservice.NumOfPrimes) obj;

    boolean result = true;
    result = result && (getNumOfPrimes()
        == other.getNumOfPrimes());
    result = result && (getStartNum()
        == other.getStartNum());
    result = result && unknownFields.equals(other.unknownFields);
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + NUMOFPRIMES_FIELD_NUMBER;
    hash = (53 * hash) + getNumOfPrimes();
    hash = (37 * hash) + STARTNUM_FIELD_NUMBER;
    hash = (53 * hash) + getStartNum();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static primesservice.NumOfPrimes parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static primesservice.NumOfPrimes parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static primesservice.NumOfPrimes parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static primesservice.NumOfPrimes parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static primesservice.NumOfPrimes parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static primesservice.NumOfPrimes parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static primesservice.NumOfPrimes parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static primesservice.NumOfPrimes parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static primesservice.NumOfPrimes parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static primesservice.NumOfPrimes parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static primesservice.NumOfPrimes parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static primesservice.NumOfPrimes parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(primesservice.NumOfPrimes prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
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
   * <pre>
   * input message
   * </pre>
   *
   * Protobuf type {@code primesservice.NumOfPrimes}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:primesservice.NumOfPrimes)
      primesservice.NumOfPrimesOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return primesservice.RpcService.internal_static_primesservice_NumOfPrimes_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return primesservice.RpcService.internal_static_primesservice_NumOfPrimes_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              primesservice.NumOfPrimes.class, primesservice.NumOfPrimes.Builder.class);
    }

    // Construct using primesservice.NumOfPrimes.newBuilder()
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
    public Builder clear() {
      super.clear();
      numOfPrimes_ = 0;

      startNum_ = 0;

      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return primesservice.RpcService.internal_static_primesservice_NumOfPrimes_descriptor;
    }

    public primesservice.NumOfPrimes getDefaultInstanceForType() {
      return primesservice.NumOfPrimes.getDefaultInstance();
    }

    public primesservice.NumOfPrimes build() {
      primesservice.NumOfPrimes result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public primesservice.NumOfPrimes buildPartial() {
      primesservice.NumOfPrimes result = new primesservice.NumOfPrimes(this);
      result.numOfPrimes_ = numOfPrimes_;
      result.startNum_ = startNum_;
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.setField(field, value);
    }
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof primesservice.NumOfPrimes) {
        return mergeFrom((primesservice.NumOfPrimes)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(primesservice.NumOfPrimes other) {
      if (other == primesservice.NumOfPrimes.getDefaultInstance()) return this;
      if (other.getNumOfPrimes() != 0) {
        setNumOfPrimes(other.getNumOfPrimes());
      }
      if (other.getStartNum() != 0) {
        setStartNum(other.getStartNum());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    public final boolean isInitialized() {
      return true;
    }

    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      primesservice.NumOfPrimes parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (primesservice.NumOfPrimes) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int numOfPrimes_ ;
    /**
     * <code>int32 numOfPrimes = 1;</code>
     */
    public int getNumOfPrimes() {
      return numOfPrimes_;
    }
    /**
     * <code>int32 numOfPrimes = 1;</code>
     */
    public Builder setNumOfPrimes(int value) {
      
      numOfPrimes_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 numOfPrimes = 1;</code>
     */
    public Builder clearNumOfPrimes() {
      
      numOfPrimes_ = 0;
      onChanged();
      return this;
    }

    private int startNum_ ;
    /**
     * <code>int32 startNum = 2;</code>
     */
    public int getStartNum() {
      return startNum_;
    }
    /**
     * <code>int32 startNum = 2;</code>
     */
    public Builder setStartNum(int value) {
      
      startNum_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 startNum = 2;</code>
     */
    public Builder clearStartNum() {
      
      startNum_ = 0;
      onChanged();
      return this;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFieldsProto3(unknownFields);
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:primesservice.NumOfPrimes)
  }

  // @@protoc_insertion_point(class_scope:primesservice.NumOfPrimes)
  private static final primesservice.NumOfPrimes DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new primesservice.NumOfPrimes();
  }

  public static primesservice.NumOfPrimes getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<NumOfPrimes>
      PARSER = new com.google.protobuf.AbstractParser<NumOfPrimes>() {
    public NumOfPrimes parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new NumOfPrimes(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<NumOfPrimes> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<NumOfPrimes> getParserForType() {
    return PARSER;
  }

  public primesservice.NumOfPrimes getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
