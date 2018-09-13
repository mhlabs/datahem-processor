package org.datahem.processor.measurementprotocol;

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

import com.google.api.services.bigquery.model.TableRow;

import com.google.gson.Gson;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.stream.Stream;
import org.datahem.processor.utils.FieldMapper;

import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.testing.ValidatesRunner;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.options.ValueProvider.StaticValueProvider;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.Count;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.SerializableFunction;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.transforms.windowing.FixedWindows;
import org.apache.beam.sdk.transforms.windowing.Window;

import org.datahem.processor.measurementprotocol.utils.*;

import org.datahem.protobuf.collector.v1.CollectorPayloadEntityProto.CollectorPayloadEntity;
//import org.datahem.processor.measurementprotocol.utils.PayloadToMPEntityFn;
//import org.datahem.processor.measurementprotocol.utils.MPEntityToTableRowFn;
import org.datahem.protobuf.measurementprotocol.v1.MPEntityProto.*;
import org.datahem.protobuf.measurementprotocol.v1.MPEntityProto;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(JUnit4.class)
public class MeasurementProtocolPipelineTest {
	
	private static final Logger LOG = LoggerFactory.getLogger(MeasurementProtocolPipelineTest.class);
	
	@Rule public transient TestPipeline p = TestPipeline.create();

	private static TableRow parameterToTR(Parameter parameter){
		String s = "";
		switch(parameter.getValueType()){
			case "Integer":	s = "intValue";
				break;
			case "String":	s= "stringValue";
				break;
			case "Boolean":	s= "intValue";
				break;
			case "Double":	s= "floatValue";
		}
		return new TableRow()
			.set("key",parameter.getExampleParameterName())
			.set("value", new TableRow()
				.set(s, parameter.getExampleValue()));
	}

	/*
	 * User headers
	 */
	private static Map<String,String> user = new HashMap<String, String>(){
		{
			put("X-AppEngine-Country","SE");
			put("X-AppEngine-Region","ab");
			put("X-AppEngine-City","stockholm");
			put("X-AppEngine-CityLatLong","59.422571,17.833131");
			put("User-Agent","Opera/9.80 (Windows NT 6.0) Presto/2.12.388 Version/12.14");
		}
	};
	
	/*
	 * Bot headers
	 */
	private static Map<String,String> bot = new HashMap<String, String>(){
		{
			put("X-AppEngine-Country","SE");
			put("X-AppEngine-City","stockholm");
			put("User-Agent","Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)");
		}
	};
	
	/*
	 * Base entity
	 */

	private static BaseEntity baseEntity = new BaseEntity();
	private static TableRow baseTR = new TableRow()
		.set("type","pageview")
		.set("clientId","35009a79-1a05-49d7-b876-2b884d0f825b")
		.set("userId","as8eknlll")
		.set("utcTimestamp","2018-03-02 07:50:53")
		.set("epochMillis",1519977053236L)
		.set("date","2018-03-02");
	private static String basePayload = baseEntity.getParameters().stream().map(p -> p.getExampleParameter() + "=" + FieldMapper.encode(p.getExampleValue())).collect(Collectors.joining("&"));

	/*
	 * Pageview entity
	 */

	private static PageviewEntity pageviewEntity = new PageviewEntity();
	private static TableRow pageviewTR = baseTR.clone()
		.set("params", Stream
			.concat(baseEntity.getParameters().stream(), pageviewEntity.getParameters().stream())
			.sorted(Comparator.comparing(Parameter::getExampleParameterName))
			.map(p -> parameterToTR(p))
			.collect(Collectors.toList()));	
	private static String pageviewPayload = pageviewEntity.getParameters().stream().map(p -> p.getExampleParameter() + "=" + FieldMapper.encode(p.getExampleValue())).collect(Collectors.joining("&"));


	/*
	 * Event entity
	 */

	private static EventEntity eventEntity = new EventEntity();
	private static TableRow eventTR = baseTR.clone()
		.set("params", Stream
			.concat(baseEntity.getParameters().stream(), eventEntity.getParameters().stream())
			.sorted(Comparator.comparing(Parameter::getExampleParameterName))
			.map(p -> parameterToTR(p))
			.collect(Collectors.toList()));	
	private static String eventPayload = eventEntity.getParameters().stream().map(p -> p.getExampleParameter() + "=" + FieldMapper.encode(p.getExampleValue())).collect(Collectors.joining("&"));


	
	
	/*
	 * Event entity
	 */

/*
	private static List<Param> eventParams = Arrays.asList(
		new Param("eventCategory", "stringValue", "/varor/kott-o-chark"), 
		new Param("eventAction", "stringValue", "www.datahem.org"),
		new Param("eventLabel", "stringValue", "947563"),
		new Param("eventValue", "intValue", 25)
	);*/


	private static CollectorPayloadEntity cpeBuilder(Map headers, String payload){
			return CollectorPayloadEntity.newBuilder()
				.setPayload(payload)
				.putAllHeaders(headers)
				.setEpochMillis("1519977053236")
				.setUuid("5bd43e1a-8217-4020-826f-3c7f0b763c32")
				.build();
	}

	@Test
	public void userPageviewTest() throws Exception {
		String uppayload = basePayload + "&" + pageviewPayload;
		LOG.info("userPageviewTest:" + uppayload);
		PCollection<TableRow> output = p
			.apply(Create.of(Arrays.asList(cpeBuilder(user, uppayload))))
			.apply(ParDo.of(new PayloadToMPEntityFn(
				StaticValueProvider.of(".*(www.google.|www.bing.|search.yahoo.).*"),
				StaticValueProvider.of(".*(foo.com|www.foo.com).*"),
				StaticValueProvider.of(".*(facebook.|instagram.|pinterest.|youtube.|linkedin.|twitter.).*"),
				StaticValueProvider.of(".*(foo.com|www.foo.com).*"),
				StaticValueProvider.of(".*(^$|bot|spider|crawler).*"),
				StaticValueProvider.of(".*q=(([^&#]*)|&|#|$)"),
				StaticValueProvider.of("Europe/Stockholm"))))
			.apply(ParDo.of(new MPEntityToTableRowFn()));
		PAssert.that(output).containsInAnyOrder(pageviewTR);
		p.run();
	}
	
	@Test
	public void botPageviewTest() throws Exception {
		String bppayload = basePayload + "&" + pageviewPayload;
		LOG.info("botPageviewTest: " + bppayload);
		PCollection<TableRow> output = p
		.apply(Create.of(Arrays.asList(cpeBuilder(bot, bppayload))))
		.apply(ParDo.of(new PayloadToMPEntityFn(
				StaticValueProvider.of(".*(www.google.|www.bing.|search.yahoo.).*"),
				StaticValueProvider.of(".*(foo.com|www.foo.com).*"),
				StaticValueProvider.of(".*(facebook.|instagram.|pinterest.|youtube.|linkedin.|twitter.).*"),
				StaticValueProvider.of(".*(foo.com|www.foo.com).*"),
				StaticValueProvider.of(".*(^$|bot|spider|crawler).*"),
				StaticValueProvider.of(".*q=(([^&#]*)|&|#|$)"),
				StaticValueProvider.of("Europe/Stockholm"))))
		.apply(ParDo.of(new MPEntityToTableRowFn()));
		PAssert.that(output).containsInAnyOrder();
		p.run();
	}

	@Test
	public void userEventTest() throws Exception {
		String uepayload = basePayload + "&" + eventPayload;
		LOG.info("userEventTest: " + eventPayload);
		LOG.info("userEventTest: " + uepayload);
		PCollection<TableRow> output = p
			.apply(Create.of(Arrays.asList(cpeBuilder(user, uepayload))))
			.apply(ParDo.of(new PayloadToMPEntityFn(
				StaticValueProvider.of(".*(www.google.|www.bing.|search.yahoo.).*"),
				StaticValueProvider.of(".*(foo.com|www.foo.com).*"),
				StaticValueProvider.of(".*(facebook.|instagram.|pinterest.|youtube.|linkedin.|twitter.).*"),
				StaticValueProvider.of(".*(foo.com|www.foo.com).*"),
				StaticValueProvider.of(".*(^$|bot|spider|crawler).*"),
				StaticValueProvider.of(".*q=(([^&#]*)|&|#|$)"),
				StaticValueProvider.of("Europe/Stockholm"))))
			.apply(ParDo.of(new MPEntityToTableRowFn()));
		PAssert.that(output).containsInAnyOrder(eventTR);
		p.run();
	}

	

}
