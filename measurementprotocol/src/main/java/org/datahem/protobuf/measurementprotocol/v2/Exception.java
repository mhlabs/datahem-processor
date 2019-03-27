// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: datahem/protobuf/measurementprotocol/v2/measurement_protocol.proto

package org.datahem.protobuf.measurementprotocol.v2;

/*-
 * ========================LICENSE_START=================================
 * Datahem.processor.measurementprotocol
 * %%
 * Copyright (C) 2018 - 2019 Robert Sahlin
 * %%
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 * =========================LICENSE_END==================================
 */

/**
 * Protobuf type {@code datahem.protobuf.measurementprotocol.v2.Exception}
 */
public  final class Exception extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:datahem.protobuf.measurementprotocol.v2.Exception)
    ExceptionOrBuilder {
  // Use Exception.newBuilder() to construct.
  private Exception(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Exception() {
    description_ = "";
    isFatal_ = 0;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
  }
  private Exception(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    int mutable_bitField0_ = 0;
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!input.skipField(tag)) {
              done = true;
            }
            break;
          }
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            description_ = s;
            break;
          }
          case 16: {

            isFatal_ = input.readInt32();
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
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return org.datahem.protobuf.measurementprotocol.v2.MeasurementProtocolOuterClass.internal_static_datahem_protobuf_measurementprotocol_v2_Exception_descriptor;
  }

  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.datahem.protobuf.measurementprotocol.v2.MeasurementProtocolOuterClass.internal_static_datahem_protobuf_measurementprotocol_v2_Exception_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.datahem.protobuf.measurementprotocol.v2.Exception.class, org.datahem.protobuf.measurementprotocol.v2.Exception.Builder.class);
  }

  public static final int DESCRIPTION_FIELD_NUMBER = 1;
  private volatile java.lang.Object description_;
  /**
   * <pre>
   *exd. The exception description.
   * </pre>
   *
   * <code>optional string description = 1;</code>
   */
  public java.lang.String getDescription() {
    java.lang.Object ref = description_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      description_ = s;
      return s;
    }
  }
  /**
   * <pre>
   *exd. The exception description.
   * </pre>
   *
   * <code>optional string description = 1;</code>
   */
  public com.google.protobuf.ByteString
      getDescriptionBytes() {
    java.lang.Object ref = description_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      description_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int ISFATAL_FIELD_NUMBER = 2;
  private int isFatal_;
  /**
   * <pre>
   *exf	If the exception was fatal, this is set to true.        
   * </pre>
   *
   * <code>optional int32 isFatal = 2;</code>
   */
  public int getIsFatal() {
    return isFatal_;
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
    if (!getDescriptionBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, description_);
    }
    if (isFatal_ != 0) {
      output.writeInt32(2, isFatal_);
    }
  }

  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getDescriptionBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, description_);
    }
    if (isFatal_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(2, isFatal_);
    }
    memoizedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof org.datahem.protobuf.measurementprotocol.v2.Exception)) {
      return super.equals(obj);
    }
    org.datahem.protobuf.measurementprotocol.v2.Exception other = (org.datahem.protobuf.measurementprotocol.v2.Exception) obj;

    boolean result = true;
    result = result && getDescription()
        .equals(other.getDescription());
    result = result && (getIsFatal()
        == other.getIsFatal());
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptorForType().hashCode();
    hash = (37 * hash) + DESCRIPTION_FIELD_NUMBER;
    hash = (53 * hash) + getDescription().hashCode();
    hash = (37 * hash) + ISFATAL_FIELD_NUMBER;
    hash = (53 * hash) + getIsFatal();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static org.datahem.protobuf.measurementprotocol.v2.Exception parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.datahem.protobuf.measurementprotocol.v2.Exception parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.datahem.protobuf.measurementprotocol.v2.Exception parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.datahem.protobuf.measurementprotocol.v2.Exception parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.datahem.protobuf.measurementprotocol.v2.Exception parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.datahem.protobuf.measurementprotocol.v2.Exception parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.datahem.protobuf.measurementprotocol.v2.Exception parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static org.datahem.protobuf.measurementprotocol.v2.Exception parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.datahem.protobuf.measurementprotocol.v2.Exception parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.datahem.protobuf.measurementprotocol.v2.Exception parseFrom(
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
  public static Builder newBuilder(org.datahem.protobuf.measurementprotocol.v2.Exception prototype) {
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
   * Protobuf type {@code datahem.protobuf.measurementprotocol.v2.Exception}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:datahem.protobuf.measurementprotocol.v2.Exception)
      org.datahem.protobuf.measurementprotocol.v2.ExceptionOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.datahem.protobuf.measurementprotocol.v2.MeasurementProtocolOuterClass.internal_static_datahem_protobuf_measurementprotocol_v2_Exception_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.datahem.protobuf.measurementprotocol.v2.MeasurementProtocolOuterClass.internal_static_datahem_protobuf_measurementprotocol_v2_Exception_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.datahem.protobuf.measurementprotocol.v2.Exception.class, org.datahem.protobuf.measurementprotocol.v2.Exception.Builder.class);
    }

    // Construct using org.datahem.protobuf.measurementprotocol.v2.Exception.newBuilder()
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
      description_ = "";

      isFatal_ = 0;

      return this;
    }

    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.datahem.protobuf.measurementprotocol.v2.MeasurementProtocolOuterClass.internal_static_datahem_protobuf_measurementprotocol_v2_Exception_descriptor;
    }

    public org.datahem.protobuf.measurementprotocol.v2.Exception getDefaultInstanceForType() {
      return org.datahem.protobuf.measurementprotocol.v2.Exception.getDefaultInstance();
    }

    public org.datahem.protobuf.measurementprotocol.v2.Exception build() {
      org.datahem.protobuf.measurementprotocol.v2.Exception result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public org.datahem.protobuf.measurementprotocol.v2.Exception buildPartial() {
      org.datahem.protobuf.measurementprotocol.v2.Exception result = new org.datahem.protobuf.measurementprotocol.v2.Exception(this);
      result.description_ = description_;
      result.isFatal_ = isFatal_;
      onBuilt();
      return result;
    }

    public Builder clone() {
      return (Builder) super.clone();
    }
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
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
        int index, Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof org.datahem.protobuf.measurementprotocol.v2.Exception) {
        return mergeFrom((org.datahem.protobuf.measurementprotocol.v2.Exception)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.datahem.protobuf.measurementprotocol.v2.Exception other) {
      if (other == org.datahem.protobuf.measurementprotocol.v2.Exception.getDefaultInstance()) return this;
      if (!other.getDescription().isEmpty()) {
        description_ = other.description_;
        onChanged();
      }
      if (other.getIsFatal() != 0) {
        setIsFatal(other.getIsFatal());
      }
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
      org.datahem.protobuf.measurementprotocol.v2.Exception parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (org.datahem.protobuf.measurementprotocol.v2.Exception) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object description_ = "";
    /**
     * <pre>
     *exd. The exception description.
     * </pre>
     *
     * <code>optional string description = 1;</code>
     */
    public java.lang.String getDescription() {
      java.lang.Object ref = description_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        description_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     *exd. The exception description.
     * </pre>
     *
     * <code>optional string description = 1;</code>
     */
    public com.google.protobuf.ByteString
        getDescriptionBytes() {
      java.lang.Object ref = description_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        description_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     *exd. The exception description.
     * </pre>
     *
     * <code>optional string description = 1;</code>
     */
    public Builder setDescription(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      description_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *exd. The exception description.
     * </pre>
     *
     * <code>optional string description = 1;</code>
     */
    public Builder clearDescription() {
      
      description_ = getDefaultInstance().getDescription();
      onChanged();
      return this;
    }
    /**
     * <pre>
     *exd. The exception description.
     * </pre>
     *
     * <code>optional string description = 1;</code>
     */
    public Builder setDescriptionBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      description_ = value;
      onChanged();
      return this;
    }

    private int isFatal_ ;
    /**
     * <pre>
     *exf	If the exception was fatal, this is set to true.        
     * </pre>
     *
     * <code>optional int32 isFatal = 2;</code>
     */
    public int getIsFatal() {
      return isFatal_;
    }
    /**
     * <pre>
     *exf	If the exception was fatal, this is set to true.        
     * </pre>
     *
     * <code>optional int32 isFatal = 2;</code>
     */
    public Builder setIsFatal(int value) {
      
      isFatal_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *exf	If the exception was fatal, this is set to true.        
     * </pre>
     *
     * <code>optional int32 isFatal = 2;</code>
     */
    public Builder clearIsFatal() {
      
      isFatal_ = 0;
      onChanged();
      return this;
    }
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }

    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return this;
    }


    // @@protoc_insertion_point(builder_scope:datahem.protobuf.measurementprotocol.v2.Exception)
  }

  // @@protoc_insertion_point(class_scope:datahem.protobuf.measurementprotocol.v2.Exception)
  private static final org.datahem.protobuf.measurementprotocol.v2.Exception DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.datahem.protobuf.measurementprotocol.v2.Exception();
  }

  public static org.datahem.protobuf.measurementprotocol.v2.Exception getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Exception>
      PARSER = new com.google.protobuf.AbstractParser<Exception>() {
    public Exception parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
        return new Exception(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<Exception> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Exception> getParserForType() {
    return PARSER;
  }

  public org.datahem.protobuf.measurementprotocol.v2.Exception getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

