// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: rpcService.proto

package CnText;

/**
 * Protobuf enum {@code CnText.UploadStatus}
 */
public enum UploadStatus
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>UPLOAD_STARTING = 0;</code>
   */
  UPLOAD_STARTING(0),
  /**
   * <code>UPLOADING = 1;</code>
   */
  UPLOADING(1),
  /**
   * <code>UPLOAD_SUCCESS = 2;</code>
   */
  UPLOAD_SUCCESS(2),
  /**
   * <code>UPLOAD_ERROR = 3;</code>
   */
  UPLOAD_ERROR(3),
  /**
   * <code>UPLOAD_INVALID_SESSION = 4;</code>
   */
  UPLOAD_INVALID_SESSION(4),
  /**
   * <code>UPLOAD_IMAGE_TOO_BIG = 5;</code>
   */
  UPLOAD_IMAGE_TOO_BIG(5),
  /**
   * <code>UPLOAD_UNSUPPORTED_FORMAT = 6;</code>
   */
  UPLOAD_UNSUPPORTED_FORMAT(6),
  /**
   * <code>UPLOAD_USER_ERROR = 7;</code>
   */
  UPLOAD_USER_ERROR(7),
  UNRECOGNIZED(-1),
  ;

  /**
   * <code>UPLOAD_STARTING = 0;</code>
   */
  public static final int UPLOAD_STARTING_VALUE = 0;
  /**
   * <code>UPLOADING = 1;</code>
   */
  public static final int UPLOADING_VALUE = 1;
  /**
   * <code>UPLOAD_SUCCESS = 2;</code>
   */
  public static final int UPLOAD_SUCCESS_VALUE = 2;
  /**
   * <code>UPLOAD_ERROR = 3;</code>
   */
  public static final int UPLOAD_ERROR_VALUE = 3;
  /**
   * <code>UPLOAD_INVALID_SESSION = 4;</code>
   */
  public static final int UPLOAD_INVALID_SESSION_VALUE = 4;
  /**
   * <code>UPLOAD_IMAGE_TOO_BIG = 5;</code>
   */
  public static final int UPLOAD_IMAGE_TOO_BIG_VALUE = 5;
  /**
   * <code>UPLOAD_UNSUPPORTED_FORMAT = 6;</code>
   */
  public static final int UPLOAD_UNSUPPORTED_FORMAT_VALUE = 6;
  /**
   * <code>UPLOAD_USER_ERROR = 7;</code>
   */
  public static final int UPLOAD_USER_ERROR_VALUE = 7;


  public final int getNumber() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalArgumentException(
          "Can't get the number of an unknown enum value.");
    }
    return value;
  }

  /**
   * @deprecated Use {@link #forNumber(int)} instead.
   */
  @java.lang.Deprecated
  public static UploadStatus valueOf(int value) {
    return forNumber(value);
  }

  public static UploadStatus forNumber(int value) {
    switch (value) {
      case 0: return UPLOAD_STARTING;
      case 1: return UPLOADING;
      case 2: return UPLOAD_SUCCESS;
      case 3: return UPLOAD_ERROR;
      case 4: return UPLOAD_INVALID_SESSION;
      case 5: return UPLOAD_IMAGE_TOO_BIG;
      case 6: return UPLOAD_UNSUPPORTED_FORMAT;
      case 7: return UPLOAD_USER_ERROR;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<UploadStatus>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      UploadStatus> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<UploadStatus>() {
          public UploadStatus findValueByNumber(int number) {
            return UploadStatus.forNumber(number);
          }
        };

  public final com.google.protobuf.Descriptors.EnumValueDescriptor
      getValueDescriptor() {
    return getDescriptor().getValues().get(ordinal());
  }
  public final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptorForType() {
    return getDescriptor();
  }
  public static final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptor() {
    return CnText.RpcService.getDescriptor().getEnumTypes().get(1);
  }

  private static final UploadStatus[] VALUES = values();

  public static UploadStatus valueOf(
      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "EnumValueDescriptor is not for this type.");
    }
    if (desc.getIndex() == -1) {
      return UNRECOGNIZED;
    }
    return VALUES[desc.getIndex()];
  }

  private final int value;

  private UploadStatus(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:CnText.UploadStatus)
}

