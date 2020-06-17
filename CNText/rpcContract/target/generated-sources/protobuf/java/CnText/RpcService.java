// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: rpcService.proto

package CnText;

public final class RpcService {
  private RpcService() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_CnText_Login_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CnText_Login_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_CnText_Session_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CnText_Session_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_CnText_CloseResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CnText_CloseResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_CnText_UploadRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CnText_UploadRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_CnText_UploadRequestResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CnText_UploadRequestResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_CnText_TranslateRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CnText_TranslateRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_CnText_TranslateResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CnText_TranslateResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\020rpcService.proto\022\006CnText\"\'\n\005Login\022\014\n\004u" +
      "ser\030\001 \001(\t\022\020\n\010password\030\002 \001(\t\"O\n\007Session\022\021" +
      "\n\tsessionId\030\001 \001(\t\022\014\n\004user\030\002 \001(\t\022#\n\006statu" +
      "s\030\003 \001(\0162\023.CnText.LoginStatus\"5\n\rCloseRes" +
      "ponse\022$\n\006status\030\001 \001(\0162\024.CnText.LogoutSta" +
      "tus\"R\n\rUploadRequest\022\021\n\tsessionId\030\001 \001(\t\022" +
      "\r\n\005image\030\002 \001(\014\022\014\n\004mime\030\003 \001(\t\022\021\n\textensio" +
      "n\030\004 \001(\t\"R\n\025UploadRequestResponse\022\023\n\013uplo" +
      "adToken\030\001 \001(\t\022$\n\006status\030\002 \001(\0162\024.CnText.U" +
      "ploadStatus\"L\n\020TranslateRequest\022\021\n\tsessi" +
      "onId\030\001 \001(\t\022\023\n\013uploadToken\030\002 \001(\t\022\020\n\010langu" +
      "age\030\003 \001(\t\"_\n\021TranslateResponse\022\014\n\004text\030\001" +
      " \001(\t\022\023\n\013translation\030\002 \001(\t\022\'\n\006status\030\003 \001(" +
      "\0162\027.CnText.TranslateStatus*q\n\013LoginStatu" +
      "s\022\021\n\rLOGIN_SUCCESS\020\000\022\035\n\031LOGIN_COMMUNICAT" +
      "ION_ERROR\020\001\022\026\n\022LOGIN_UNKNOWN_USER\020\002\022\030\n\024L" +
      "OGIN_WRONG_PASSWORD\020\003*\304\001\n\014UploadStatus\022\023" +
      "\n\017UPLOAD_STARTING\020\000\022\r\n\tUPLOADING\020\001\022\022\n\016UP" +
      "LOAD_SUCCESS\020\002\022\020\n\014UPLOAD_ERROR\020\003\022\032\n\026UPLO" +
      "AD_INVALID_SESSION\020\004\022\030\n\024UPLOAD_IMAGE_TOO" +
      "_BIG\020\005\022\035\n\031UPLOAD_UNSUPPORTED_FORMAT\020\006\022\025\n" +
      "\021UPLOAD_USER_ERROR\020\007*\322\001\n\017TranslateStatus" +
      "\022\020\n\014READING_TEXT\020\000\022\023\n\017READING_SUCCESS\020\001\022" +
      "\017\n\013TRANSLATING\020\002\022\025\n\021TRANSLATE_SUCCESS\020\003\022" +
      "\030\n\024UNSUPPORTED_LANGUAGE\020\006\022\023\n\017TRANSLATE_E" +
      "RROR\020\004\022\035\n\031TRANSLATE_INVALID_SESSION\020\005\022\"\n" +
      "\036TRANSLATE_INVALID_UPLOAD_TOKEN\020\007*^\n\014Log" +
      "outStatus\022\022\n\016LOGOUT_SUCCESS\020\000\022\036\n\032LOGOUT_" +
      "COMMUNICATION_ERROR\020\001\022\032\n\026LOGOUT_INVALID_" +
      "SESSION\020\0022\352\001\n\006CnText\022\'\n\005start\022\r.CnText.L" +
      "ogin\032\017.CnText.Session\022/\n\005close\022\017.CnText." +
      "Session\032\025.CnText.CloseResponse\022B\n\006upload" +
      "\022\025.CnText.UploadRequest\032\035.CnText.UploadR" +
      "equestResponse(\0010\001\022B\n\ttranslate\022\030.CnText" +
      ".TranslateRequest\032\031.CnText.TranslateResp" +
      "onse0\001B\n\n\006CnTextP\001b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_CnText_Login_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_CnText_Login_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_CnText_Login_descriptor,
        new java.lang.String[] { "User", "Password", });
    internal_static_CnText_Session_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_CnText_Session_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_CnText_Session_descriptor,
        new java.lang.String[] { "SessionId", "User", "Status", });
    internal_static_CnText_CloseResponse_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_CnText_CloseResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_CnText_CloseResponse_descriptor,
        new java.lang.String[] { "Status", });
    internal_static_CnText_UploadRequest_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_CnText_UploadRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_CnText_UploadRequest_descriptor,
        new java.lang.String[] { "SessionId", "Image", "Mime", "Extension", });
    internal_static_CnText_UploadRequestResponse_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_CnText_UploadRequestResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_CnText_UploadRequestResponse_descriptor,
        new java.lang.String[] { "UploadToken", "Status", });
    internal_static_CnText_TranslateRequest_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_CnText_TranslateRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_CnText_TranslateRequest_descriptor,
        new java.lang.String[] { "SessionId", "UploadToken", "Language", });
    internal_static_CnText_TranslateResponse_descriptor =
      getDescriptor().getMessageTypes().get(6);
    internal_static_CnText_TranslateResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_CnText_TranslateResponse_descriptor,
        new java.lang.String[] { "Text", "Translation", "Status", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
