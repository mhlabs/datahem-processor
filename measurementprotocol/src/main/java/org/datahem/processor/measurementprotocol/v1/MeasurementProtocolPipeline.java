package org.datahem.processor.measurementprotocol.v1;

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

/*
import org.datahem.protobuf.measurementprotocol.v2.*;
import org.datahem.processor.measurementprotocol.v2.utils.MeasurementProtocolBuilder;
import org.datahem.processor.utils.ProtobufUtils;
import org.datahem.processor.measurementprotocol.v2.utils.PayloadToMeasurementProtocolFn;
import org.datahem.processor.measurementprotocol.v2.utils.MeasurementProtocolToTableRowFn;
*/

import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableRow;
import com.google.api.services.bigquery.model.TableSchema;
import com.google.api.services.bigquery.model.TimePartitioning;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.extensions.gcp.options.GcpOptions;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubMessage;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.options.ValueProvider;
import org.apache.beam.sdk.options.ValueProvider.StaticValueProvider;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.windowing.FixedWindows;
import org.apache.beam.sdk.transforms.windowing.Window;
import org.apache.beam.sdk.values.PCollection;
import org.datahem.processor.measurementprotocol.v1.utils.MPEntityToTableRowFn;
import org.datahem.processor.measurementprotocol.v1.utils.PayloadToMPEntityFn;
import org.datahem.processor.utils.ProtobufUtils;
import org.datahem.protobuf.measurementprotocol.v1.MPEntityProto;
import org.datahem.protobuf.measurementprotocol.v1.MPEntityProto.MPEntity;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MeasurementProtocolPipeline {
    private static final Logger LOG = LoggerFactory.getLogger(MeasurementProtocolPipeline.class);

    public interface MeasurementProtocolPipelineOptions extends PipelineOptions {
        @Description("JSON Configuration string")
        ValueProvider<String> getConfig();

        void setConfig(ValueProvider<String> value);
        //String getConfig();
        //void setConfig(String value);
    }

    public static void main(String[] args) {
        MeasurementProtocolPipelineOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(MeasurementProtocolPipeline.MeasurementProtocolPipelineOptions.class);
        Pipeline pipeline = Pipeline.create(options);
        // Create schemas from protocol buffers

        TableSchema eventSchema = ProtobufUtils.makeTableSchema(MPEntityProto.MPEntity.getDescriptor());
        List<TableFieldSchema> fieldsList = eventSchema.getFields();
        TableFieldSchema date = new TableFieldSchema().setName("date").setType("STRING").setMode("NULLABLE");
        fieldsList.set(fieldsList.indexOf(date), date.setType("DATE"));
        TableFieldSchema localDateTime = new TableFieldSchema().setName("local_date_time").setType("STRING").setMode("NULLABLE");
        fieldsList.set(fieldsList.indexOf(localDateTime), localDateTime.setType("DATETIME"));
        TableSchema schema = new TableSchema().setFields(fieldsList);


        for (Config.Account.Property property : Config.read(options.getConfig().get())) {
            String pubsubSubscription = "projects/" + options.as(GcpOptions.class).getProject() + "/subscriptions/" + property.id;
            LOG.info("pubsibSubscription: " + pubsubSubscription);

            PCollection<PubsubMessage> payload = pipeline
                    .apply(property.id + " - Read payloads from pubsub",
                            PubsubIO
                                    .readMessagesWithAttributes()
                                    .fromSubscription(pubsubSubscription));

            for (Config.Account.Property.View view : property.views) { //Start view
                PCollection<MPEntity> enrichedEntities = payload
                        .apply(view.id + " - Payload to multiple Events",
                                ParDo.of(new PayloadToMPEntityFn(
                                        StaticValueProvider.of(view.searchEnginesPattern),
                                        StaticValueProvider.of(view.ignoredReferersPattern),
                                        StaticValueProvider.of(view.socialNetworksPattern),
                                        StaticValueProvider.of(view.includedHostnamesPattern),
                                        StaticValueProvider.of(view.excludedBotsPattern),
                                        StaticValueProvider.of(view.siteSearchPattern),
                                        StaticValueProvider.of(view.timeZone)
                                )));

                enrichedEntities
                        .apply(view.id + " - Event to tablerow",
                                ParDo.of(new MPEntityToTableRowFn()))
                        .apply(view.id + " - Fixed Windows",
                                Window.<TableRow>into(FixedWindows.of(Duration.standardMinutes(1)))
                                        .withAllowedLateness(Duration.standardDays(7))
                                        .discardingFiredPanes())
                        .apply(view.id + " - Write to bigquery",
                                BigQueryIO
                                        .writeTableRows()
                                        .to(property.id + "." + view.id)
                                        .withSchema(eventSchema)
                                        .withTimePartitioning(new TimePartitioning().setField("date").setType("DAY"))
                                        .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED)
                                        .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND));

                if (view.pubSubTopic != null) {
                    String pubSubTopic = "projects/" + options.as(GcpOptions.class).getProject() + "/topics/" + view.pubSubTopic;
                    enrichedEntities
                            .apply(view.id + " - Write to pubsub",
                                    PubsubIO
                                            .writeProtos(MPEntityProto.MPEntity.class)
                                            .to(pubSubTopic));
                }
            } //End View
        }
        pipeline.run();
    }
}
