// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: rpcService.proto

package CnText;

public interface TranslateResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:CnText.TranslateResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string text = 1;</code>
   */
  java.lang.String getText();
  /**
   * <code>string text = 1;</code>
   */
  com.google.protobuf.ByteString
      getTextBytes();

  /**
   * <code>string translation = 2;</code>
   */
  java.lang.String getTranslation();
  /**
   * <code>string translation = 2;</code>
   */
  com.google.protobuf.ByteString
      getTranslationBytes();

  /**
   * <code>.CnText.TranslateStatus status = 3;</code>
   */
  int getStatusValue();
  /**
   * <code>.CnText.TranslateStatus status = 3;</code>
   */
  CnText.TranslateStatus getStatus();
}
