package org.datahem.processor.generic;

/*-
 * ========================LICENSE_START=================================
 * DataHem
 * %%
 * Copyright (C) 2018 Robert Sahlin and MatHem Sverige AB
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * =========================LICENSE_END==================================
 */


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import java.util.List;
import java.io.IOException;

import org.apache.beam.sdk.Pipeline;

import org.apache.beam.sdk.coders.AvroCoder;
import org.apache.avro.Schema;
import org.apache.avro.SchemaCompatibility;
import org.apache.avro.SchemaCompatibility.SchemaCompatibilityType;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubMessage;
import org.apache.beam.sdk.transforms.SerializableFunction;
import org.apache.beam.sdk.io.kinesis.KinesisIO;
import org.apache.beam.sdk.io.kinesis.KinesisRecord;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.InitialPositionInStream;
import com.amazonaws.regions.Regions;

import com.google.auth.oauth2.GoogleCredentials;
import org.datahem.avro.message.AvroToBigQuery;
//import org.apache.beam.sdk.io.gcp.bigquery.BigQueryAvroUtils;
import org.apache.beam.sdk.values.ValueInSingleWindow;
import org.apache.beam.sdk.io.gcp.bigquery.TableDestination;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.io.gcp.bigquery.BigQuery;
import org.apache.beam.sdk.io.gcp.bigquery.DynamicDestinations;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO.Write.CreateDisposition;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO.Write.WriteDisposition;
import com.google.cloud.bigquery.BigQueryOptions;

import org.apache.beam.sdk.io.gcp.bigquery.InsertRetryPolicy;
import org.apache.beam.sdk.io.gcp.bigquery.WriteResult;
import org.apache.beam.sdk.metrics.Counter;
import org.apache.beam.sdk.metrics.Metrics;
import org.apache.beam.sdk.extensions.gcp.options.GcpOptions;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.DefaultValueFactory;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.options.Validation.Required;
import org.apache.beam.sdk.options.ValueProvider;
import org.apache.beam.sdk.options.ValueProvider.StaticValueProvider;
import org.apache.beam.sdk.transforms.Count;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.transforms.windowing.FixedWindows;
import org.apache.beam.sdk.transforms.windowing.Window;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import com.google.api.services.bigquery.model.TableRow;
import com.google.api.services.bigquery.model.TableSchema;
import org.datahem.avro.message.Converters;
import org.datahem.avro.message.DatastoreCache;
import org.datahem.avro.message.DynamicBinaryMessageDecoder;
import org.apache.avro.generic.GenericData.Record;
import org.apache.avro.generic.GenericData;
import org.apache.avro.SchemaNormalization;
import org.apache.beam.sdk.coders.CoderRegistry;
import org.apache.beam.sdk.coders.Coder;

import org.joda.time.Duration;
import org.joda.time.Instant;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.DateTimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadStreamPipeline {
	
	private static final Logger LOG = LoggerFactory.getLogger(LoadStreamPipeline.class);

	public interface Options extends PipelineOptions, GcpOptions {
		
		@Description("Pub/Sub subscription")
		//@Default.String("projects/mathem-data/subscriptions/measurementprotocol-1-dev")
		ValueProvider<String> getPubsubSubscription();
		void setPubsubSubscription(ValueProvider<String> subscription);
	}


	public static void main(String[] args) throws IOException {
		Options options = PipelineOptionsFactory.fromArgs(args).withValidation().as(Options.class);
		Pipeline pipeline = Pipeline.create(options);
		CoderRegistry cr = pipeline.getCoderRegistry();
		GenericRecordCoder coder = new GenericRecordCoder();
		cr.registerCoderForClass(Record.class, coder);
		DatastoreCache cache = new DatastoreCache();
		Map tableFingerprintLabel = new HashMap<string, string>();

		PCollection<Record> incomingRecords = 
			pipeline
				.apply("Read pubsub messages", 
					PubsubIO
						.readMessagesWithAttributes()
						.fromSubscription(options.getPubsubSubscription()))
				.apply("Convert PubsubMessage payload from Avro Binary to Avro Generic Record", 
					ParDo.of(new DoFn<PubsubMessage,Record>() {
						//private DatastoreCache cache;
						private DynamicBinaryMessageDecoder<Record> decoder;
						
						@Setup
						public void setup() throws Exception {
							//cache = new DatastoreCache();
							String SCHEMA_STR_V1 = "{\"type\":\"record\", \"namespace\":\"foo\", \"name\":\"Man\", \"fields\":[ { \"name\":\"name\", \"type\":\"string\" }, { \"name\":\"age\", \"type\":[\"null\",\"double\"] } ] }";
							Schema SCHEMA_V1 = new Schema.Parser().parse(SCHEMA_STR_V1);
							decoder = new DynamicBinaryMessageDecoder<>(GenericData.get(), SCHEMA_V1, new DatastoreCache());
						}
						@ProcessElement
						public void processElement(ProcessContext c) {
							PubsubMessage received = c.element();
							try{
								c.output(decoder.decode(received.getPayload()));	
							}catch(IOException e){
								LOG.error(e.toString());
							}
						}
					}));
			
			WriteResult writeResult = 
				incomingRecords.apply(
					"Wite to dynamic BigQuery destinations", 
					BigQueryIO.<Record>write()
					.to(new DynamicDestinations<Record, String>() {
						public String getDestination(ValueInSingleWindow<Record> element) {
							String fingerprint = Long.toString(SchemaNormalization.parsingFingerprint64(element.getValue().getSchema()));
							return fingerprint;
						}
						public TableDestination getTable(String fingerprint) {
							Schema schema = cache.findByFingerprint(Long.parseLong(fingerprint));
							String project = "mathem-ml-datahem-test";
							String dataset = "generic_streams";
							String table = schema.getName();
							TableId tableId = TableId.of(project, dataset, table);
							if(tableFingerprintLabel.get(tableId) == null){
								Table table = bigQuery.getTable(tableId);
								if(table.getLabels().get(fingerprint) == null){
									table.setLabels(table.getLabels().set("fingerprint", fingerprint));
									tableFingerprintLabel.set(tableId, fingerprint);
								}
							}
							return new TableDestination(dataset + "." + table, "Table for:" + fingerprint);
							//return new TableDestination("generic_streams." + fingerprint, "Table for:" + fingerprint);
						}
						public TableSchema getSchema(String fingerprint) {
							String SCHEMA_STR_V1 = "{\"type\":\"record\", \"namespace\":\"foo\", \"name\":\"Man\", \"fields\":[ { \"name\":\"name\", \"type\":\"string\" }, { \"name\":\"age\", \"type\":[\"null\",\"double\"] } ] }";
							Schema SCHEMA_V1 = new Schema.Parser().parse(SCHEMA_STR_V1);
							return AvroToBigQuery.getTableSchemaRecord(SCHEMA_V1);
						}
					})
					.withFormatFunction(new SerializableFunction<Record, TableRow>() {
						public TableRow apply(Record record) {
							return AvroToBigQuery.getTableRow(record);
						}
					})
					.withWriteDisposition(WriteDisposition.WRITE_APPEND)
					.withFailedInsertRetryPolicy(InsertRetryPolicy.retryTransientErrors()));
					

			writeResult
				.getFailedInserts()
				//.apply("MutateSchema", BigQuerySchemaMutator.mutateWithSchema(incomingRecordsView))
				.apply("Mutate schema", 
					ParDo.of(new DoFn<Record,Record>() {
						//private DatastoreCache cache;
						private DynamicBinaryMessageDecoder<Record> decoder;
						private transient BigQuery bigQuery;
						
						@Setup
						public void setup() throws Exception {
							//cache = new DatastoreCache();
							String SCHEMA_STR_V1 = "{\"type\":\"record\", \"namespace\":\"foo\", \"name\":\"Man\", \"fields\":[ { \"name\":\"name\", \"type\":\"string\" }, { \"name\":\"age\", \"type\":[\"null\",\"double\"] } ] }";
							Schema SCHEMA_V1 = new Schema.Parser().parse(SCHEMA_STR_V1);
							decoder = new DynamicBinaryMessageDecoder<>(GenericData.get(), SCHEMA_V1, new DatastoreCache());
							bigQuery =
								BigQueryOptions.newBuilder()
									.setCredentials(GoogleCredentials.getApplicationDefault())
									.build()
									.getService();
						}
						
						@ProcessElement
						public void processElement(ProcessContext c) {
							Record record = c.element();
							Schema writer = record.getSchema();
							//Schema writer = cache.findByFingerprint(Long.parseLong(fingerprint));
							String project = "mathem-ml-datahem-test";
							String dataset = "generic_streams";
							
							String table = reader.getName();
							TableId tableId = TableId.of(project, dataset, table);
							Schema reader = cache.findByFingerprint(tableFingerprintLabel.get(tableId));
							SchemaPairCompatibility compatibility = SchemaCompatibility.checkReaderWriterCompatibility(reader, writer);
							if(compatibility.getType() == SchemaCompatibilityType.COMPATIBLE){
								LOG.info("hello");
							}
							try{
								c.output(decoder.decode(received.getPayload()));	
							}catch(IOException e){
								LOG.error(e.toString());
							}
						}
					}))
				.apply(
					"RetryWriteMutatedRows",
					BigQueryIO.<Record>write()
						//.withFormatFunction(TableRowWithSchema::getTableRow)
						.withFormatFunction(new SerializableFunction<Record, TableRow>() {
							public TableRow apply(Record record) {
								return AvroToBigQuery.getTableRow(record);
							}
						})
						.withCreateDisposition(CreateDisposition.CREATE_NEVER)
						.withWriteDisposition(WriteDisposition.WRITE_APPEND));

		pipeline.run();
	}
}
