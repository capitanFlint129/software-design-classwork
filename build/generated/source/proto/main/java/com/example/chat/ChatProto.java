// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: chat.proto

package com.example.chat;

public final class ChatProto {
  private ChatProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_chat_Message_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_chat_Message_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\nchat.proto\022\004chat\"(\n\007Message\022\014\n\004name\030\001 " +
      "\001(\t\022\017\n\007message\030\002 \001(\t22\n\004Chat\022*\n\004Chat\022\r.c" +
      "hat.Message\032\r.chat.Message\"\000(\0010\001B\037\n\020com." +
      "example.chatB\tChatProtoP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_chat_Message_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_chat_Message_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_chat_Message_descriptor,
        new java.lang.String[] { "Name", "Message", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
