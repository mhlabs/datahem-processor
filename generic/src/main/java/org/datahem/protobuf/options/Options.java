// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: datahem/options/options.proto

package org.datahem.protobuf.options;

/*-
 * ========================LICENSE_START=================================
 * DataHem
 * %%
 * Copyright (C) 2018 - 2019 MatHem Sverige AB
 * %%
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 * =========================LICENSE_END==================================
 */

public final class Options {
  private Options() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
    registry.add(org.datahem.protobuf.options.Options.bqmessage);
    registry.add(org.datahem.protobuf.options.Options.bqfield);
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public static final int BQMESSAGE_FIELD_NUMBER = 66666668;
  /**
   * <code>extend .google.protobuf.MessageOptions { ... }</code>
   */
  public static final
    com.google.protobuf.GeneratedMessage.GeneratedExtension<
      com.google.protobuf.DescriptorProtos.MessageOptions,
      org.datahem.protobuf.options.Bigquery.BigQueryMessageOptions> bqmessage = com.google.protobuf.GeneratedMessage
          .newFileScopedGeneratedExtension(
        org.datahem.protobuf.options.Bigquery.BigQueryMessageOptions.class,
        org.datahem.protobuf.options.Bigquery.BigQueryMessageOptions.getDefaultInstance());
  public static final int BQFIELD_FIELD_NUMBER = 66666667;
  /**
   * <code>extend .google.protobuf.FieldOptions { ... }</code>
   */
  public static final
    com.google.protobuf.GeneratedMessage.GeneratedExtension<
      com.google.protobuf.DescriptorProtos.FieldOptions,
      org.datahem.protobuf.options.Bigquery.BigQueryFieldOptions> bqfield = com.google.protobuf.GeneratedMessage
          .newFileScopedGeneratedExtension(
        org.datahem.protobuf.options.Bigquery.BigQueryFieldOptions.class,
        org.datahem.protobuf.options.Bigquery.BigQueryFieldOptions.getDefaultInstance());

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\035datahem/options/options.proto\022\017datahem" +
      ".options\032\036datahem/options/bigquery.proto" +
      "\032 google/protobuf/descriptor.proto:^\n\tbq" +
      "message\022\037.google.protobuf.MessageOptions" +
      "\030\254\201\345\037 \001(\0132\'.datahem.options.BigQueryMess" +
      "ageOptions:X\n\007bqfield\022\035.google.protobuf." +
      "FieldOptions\030\253\201\345\037 \001(\0132%.datahem.options." +
      "BigQueryFieldOptionsB\036\n\034org.datahem.prot" +
      "obuf.optionsb\006proto3"
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
          org.datahem.protobuf.options.Bigquery.getDescriptor(),
          com.google.protobuf.DescriptorProtos.getDescriptor(),
        }, assigner);
    bqmessage.internalInit(descriptor.getExtensions().get(0));
    bqfield.internalInit(descriptor.getExtensions().get(1));
    org.datahem.protobuf.options.Bigquery.getDescriptor();
    com.google.protobuf.DescriptorProtos.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}