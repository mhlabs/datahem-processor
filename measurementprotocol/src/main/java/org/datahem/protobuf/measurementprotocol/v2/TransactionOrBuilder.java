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

public interface TransactionOrBuilder extends
        // @@protoc_insertion_point(interface_extends:datahem.protobuf.measurementprotocol.v2.Transaction)
        com.google.protobuf.MessageOrBuilder {

    /**
     * <pre>
     * ti	The transaction ID of the ecommerce transaction.
     * </pre>
     *
     * <code>optional string id = 1;</code>
     */
    java.lang.String getId();

    /**
     * <pre>
     * ti	The transaction ID of the ecommerce transaction.
     * </pre>
     *
     * <code>optional string id = 1;</code>
     */
    com.google.protobuf.ByteString
    getIdBytes();

    /**
     * <pre>
     * tr	 Total transaction revenue
     * </pre>
     *
     * <code>optional double revenue = 2;</code>
     */
    double getRevenue();

    /**
     * <pre>
     * tt	Total transaction tax
     * </pre>
     *
     * <code>optional double tax = 3;</code>
     */
    double getTax();

    /**
     * <pre>
     * ts  Total transaction shipping cost, expressed as the value passed to Analytics multiplied by 10^6. (e.g., 2.40 would be given as 2400000).
     * </pre>
     *
     * <code>optional double shipping = 4;</code>
     */
    double getShipping();

    /**
     * <pre>
     * ta	 The affiliate information passed to the ecommerce tracking code.
     * </pre>
     *
     * <code>optional string affiliation = 5;</code>
     */
    java.lang.String getAffiliation();

    /**
     * <pre>
     * ta	 The affiliate information passed to the ecommerce tracking code.
     * </pre>
     *
     * <code>optional string affiliation = 5;</code>
     */
    com.google.protobuf.ByteString
    getAffiliationBytes();

    /**
     * <pre>
     * cu	The local currency code for the transaction.
     * </pre>
     *
     * <code>optional string currency = 6;</code>
     */
    java.lang.String getCurrency();

    /**
     * <pre>
     * cu	The local currency code for the transaction.
     * </pre>
     *
     * <code>optional string currency = 6;</code>
     */
    com.google.protobuf.ByteString
    getCurrencyBytes();

    /**
     * <pre>
     * tcc	 The coupon code associated with the transaction.
     * </pre>
     *
     * <code>optional string coupon = 7;</code>
     */
    java.lang.String getCoupon();

    /**
     * <pre>
     * tcc	 The coupon code associated with the transaction.
     * </pre>
     *
     * <code>optional string coupon = 7;</code>
     */
    com.google.protobuf.ByteString
    getCouponBytes();

    /**
     * <pre>
     * pa
     * </pre>
     *
     * <code>optional string action = 8;</code>
     */
    java.lang.String getAction();

    /**
     * <pre>
     * pa
     * </pre>
     *
     * <code>optional string action = 8;</code>
     */
    com.google.protobuf.ByteString
    getActionBytes();
}
