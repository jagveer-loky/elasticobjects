package org.fluentcodes.projects.elasticobjects.performance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;

import org.fluentcodes.projects.elasticobjects.assets.TestProviderBtJson;
import org.fluentcodes.projects.elasticobjects.testitemprovider.*;

import org.fluentcodes.tools.xpect.IOJsonGson;
import org.fluentcodes.tools.xpect.IOJsonJackson;
import org.fluentcodes.tools.xpect.IOString;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

@Ignore
public class EOPerformance {
    private static final Logger LOG = LogManager.getLogger(EOPerformance.class);
    private static final int maxRoot = 10000;
    private static final ObjectMapper JACKSON_MAPPER = new ObjectMapper();
    private static final Gson GSON_MAPPER = new GsonBuilder().setPrettyPrinting().create();
    private static final Map MAP = createExampleMap(50);
    private static final String MAP_JSON = createExampleMapString(50);
    private static final List LIST = createExampleList(50);
    private static final String LIST_JSON = createExampleListString(50);
    private static final BasicTest EMPTY = TestProviderBtJson.EMPTY.createBt();
    private static final String EMPTY_JSON = TestProviderBtJson.EMPTY.content();
    private static final BasicTest SMALL = TestProviderBtJson.SMALL.createBt();
    private static final String SMALL_JSON = TestProviderBtJson.SMALL.content();
    private static final BasicTest ALL = TestProviderBtJson.ALL.createBt();
    private static final String ALL_JSON = TestProviderBtJson.ALL.content();
    @Test
    public void root() throws Exception {
        StringBuilder result = new StringBuilder();
        result.append("Create " + maxRoot + " times\n" );

        result.append(runPerformanceStep("Map", MAP_JSON, MAP, LinkedHashMap.class));
        result.append(runPerformanceStep("List", LIST_JSON, LIST, ArrayList.class));
        result.append(runPerformanceStep("Empty", EMPTY_JSON, EMPTY, BasicTest.class));
        result.append(runPerformanceStep("Small", SMALL_JSON, SMALL, BasicTest.class));
        result.append(runPerformanceStep("All", ALL_JSON, ALL, BasicTest.class));
        System.out.println(result);
        new IOString()
                .setFileName("src/test/resources/performance-" + new Date().toString() + ".info")
                .write(result.toString());
    }

    private String runPerformanceStep(String head, String json, Object object, final Class mappingClass) throws Exception {
        StringBuilder result = new StringBuilder("\n** ");
        result.append(head);
        result.append(" **\n");
        result.append("--> toJson\n");
        result.append(serializeWithEo(object));
        EO eo = ProviderRootTestScope.createEo(object);
        result.append(serializeWithEoAndEoObject(eo));
        result.append(serializeWithJackson(object));
        result.append(serializeWithGson(object));

        result.append("<-- from JSON\n");
        result.append(createWithEo(json));
        result.append(createWithJackson(json, mappingClass));
        if (!"All".equals(head)) {
            result.append(createWithGson(json, mappingClass));
        }
        return result.toString();
    }


    private static Map createExampleMap(int counter)  {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < counter; i++) {
            map.put(new Integer(i).toString(), new Integer(i).toString());
        }
        return map;
    }

    private static String createExampleMapString(int counter)  {
        StringBuffer buffer = new StringBuffer("{");
        for (int i = 0; i < counter; i++) {
            buffer.append("\"");
            buffer.append(i);
            buffer.append("\": ");
            buffer.append(i);
            buffer.append(", ");
        }
        return buffer.toString().replaceAll(", $","}" );
    }

    private static List createExampleList(int counter)  {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < counter; i++) {
            list.add(new Integer(i).toString());
        }
        return list;
    }

    private static String createExampleListString(int counter)  {
        StringBuffer buffer = new StringBuffer("[");
        for (int i = 0; i < counter; i++) {
            buffer.append(i);
            buffer.append(", ");
        }
        return buffer.toString().replaceAll(", $","]" );
    }

    private String createWithEo(Object object)  {
        long start = System.currentTimeMillis();
        for (long i = 0; i < maxRoot; i++) {
            EO eo = ProviderRootTestScope.createEo(object);
        }
        long duration = System.currentTimeMillis() - start;
        String result =  "EO     : " + duration  + " ms\n";
        System.out.print(result);
        return result;
    }

    private String serializeWithEo(Object object)  {
        long start = System.currentTimeMillis();
        for (long i = 0; i < maxRoot; i++) {
            EO eo = ProviderRootTestScope.createEo(object);
            String json = new EOToJSON().toJSON(eo);
        }
        long duration = System.currentTimeMillis() - start;
        String result =  "EO     : " + duration  + " ms\n";
        System.out.print(result);
        return result;
    }

    private String serializeWithEoAndEoObject(EO eo)  {
        long start = System.currentTimeMillis();
        for (long i = 0; i < maxRoot; i++) {
            String json = new EOToJSON().toJSON(eo);
        }
        long duration = System.currentTimeMillis() - start;
        String result =  "EO dir. : " + duration  + " ms\n";
        System.out.print(result);
        return result;
    }

    private long createIOJsonJackson(String json)  {
        long start = System.currentTimeMillis();
        for (long i = 0; i < maxRoot; i++) {
            BasicTest basicTest = (BasicTest) new IOJsonJackson<BasicTest>().setMappingClass(BasicTest.class).asObject(json);
        }
        return System.currentTimeMillis() - start;
    }

    private String createWithJackson(String json, Class mappingClass) throws JsonProcessingException {
        long start = System.currentTimeMillis();
        for (long i = 0; i < maxRoot; i++) {
            Object object = JACKSON_MAPPER.readValue(json, mappingClass);
        }
        long duration = System.currentTimeMillis() - start;
        String result =  "Jackson: " + duration  + " ms\n";
        System.out.print(result);
        return result;
    }

    private String serializeWithJackson(Object object) throws JsonProcessingException {
        long start = System.currentTimeMillis();
        for (long i = 0; i < maxRoot; i++) {
            String valueAsString = JACKSON_MAPPER
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(object);
        }
        long duration = System.currentTimeMillis() - start;
        String result =  "Jackson: " + duration  + " ms\n";
        System.out.print(result);
        return result;
    }

    private long createIOJsonGson(String json)  {
        long start = System.currentTimeMillis();
        for (long i = 0; i < maxRoot; i++) {
            BasicTest basicTest = (BasicTest) new IOJsonGson<BasicTest>().setMappingClass(BasicTest.class).asObject(json);
        }
        return System.currentTimeMillis() - start;
    }

    private String createWithGson(String json, Class mappingClass)  {
        long start = System.currentTimeMillis();
        for (long i = 0; i < maxRoot; i++) {
            Object object = GSON_MAPPER.fromJson(json, mappingClass);
        }
        long duration = System.currentTimeMillis() - start;
        String result =  "Gson   : " + duration  + " ms\n";
        System.out.println(result);
        return result;
    }

    private String serializeWithGson(Object json)  {
        long start = System.currentTimeMillis();
        for (long i = 0; i < maxRoot; i++) {
            String test = GSON_MAPPER.toJson(json);
        }
        long duration = System.currentTimeMillis() - start;
        String result =  "Gson   : " + duration  + " ms\n";
        System.out.println(result);
        return result;
    }
}