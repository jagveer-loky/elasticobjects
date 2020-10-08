package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.domain.test.TestProviderAnObjectJson;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderMapJson;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by werner.diwischek on 14.1.18.
 */
public class EoToJsonMapTest {

    @Test
    public void withDefault()  {
        EO eo = ProviderMapJson.EMPTY.createMapEo();
        String serialized = new EOToJSON().toJSON(eo);
        //AssertEO.compare(serialized);
    }

    @Test
    public void withIndent0()  {
        
        EO eo = ProviderMapJson.EMPTY.createMapEo();
        String stringified = new EOToJSON()
                .setStartIndent(0)
                .toJSON(eo);
        //AssertEO.compare(stringified);
    }

    @Test
    public void withIndent1()  {
        
        EO eo = ProviderMapJson.EMPTY.createMapEo();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .toJSON(eo);
        //AssertEO.compare(stringified);
    }

    @Test
    public void withIndent2()  {
        
        EO eo = ProviderMapJson.EMPTY.createMapEo();
        String stringified = new EOToJSON()
                .setStartIndent(2)
                .toJSON(eo);
        //AssertEO.compare(stringified);
    }

    @Test
    public void withStringScalar()  {
        final EO eo = TestProviderAnObjectJson.STRING.createEoTest();
        String stringified = new EOToJSON()
                .setStartIndent(2)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(eo);
    }

    @Test
    public void withIntegerScalar()  {
        
        final EO eo = TestProviderAnObjectJson.INT.createEoTest();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(eo);
    }

    /*@Test
    public void withLongScalar()  {
        
        final EO eo = TestProviderMapJsn.LONG.createMapEo();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(eo);
    }*/

    @Test
    public void withFloatScalar()  {
        
        final EO eo = TestProviderAnObjectJson.FLOAT.createEoTest();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(eo);
    }

    @Test
    public void withDoubleScalar()  {
        
        final EO eo = TestProviderAnObjectJson.DOUBLE.createEoTest();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(eo);
    }

    @Test
    public void withDateScalar()  {
        
        final EO eo = TestProviderAnObjectJson.DATE.createEoTest();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(eo);
     }

    @Test
    public void withBooleanScalar()  {
        
        final EO eo = TestProviderAnObjectJson.BOOLEAN.createEoTest();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(eo);
    }

    @Test
    public void withIndentScalar()  {
        EO eo = TestProviderAnObjectJson.SMALL_TYPED.createEoTest();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(eo);
    }

    @Test
    public void loopScalar()  {
        EO eo = ProviderMapJson.EMPTY.createMapEo();
        long duration = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            String stringified = new EOToJSON()
                    .setSerializationType(JSONSerializationType.SCALAR)
                    .toJSON(eo);
        }
        duration = System.currentTimeMillis() - duration;
        System.out.println("Duration: " + duration + " ms.");
    }

    @Test
    public void setSameMaps()  {
        Map map = new LinkedHashMap<>();
        map.put(S_KEY0, S_STRING);
        map.put(S_KEY1, S_INTEGER);
        
        EO eo = ProviderRootTestScope.createEo();
        eo.set(map, S_LEVEL0);
        eo.set(map, S_LEVEL1);
        /*String toCompare = MapProviderJSON.toJSONMap(S_LEVEL0,
                MapProviderJSON.toJSONMap(S_KEY0, S_STRING, S_KEY1, S_INTEGER),
                S_LEVEL1,
                MapProviderJSON.toJSONMap(S_KEY0, S_STRING, S_KEY1, S_INTEGER)
        );
        String serialized = new EOToJSON()
                .setStartIndent(0)
                .toJSON(eo);

        Assert.assertEquals(toCompare, serialized);*/
    }

    @Test
    public void setSameMapsWithCheck()  {
        Map map = new LinkedHashMap<>();
        map.put(S_KEY0, S_STRING);
        
        EO eo = ProviderRootTestScope.createEo();
        eo.mapObject(map);
        eo
                .set(map, S_LEVEL0);
        eo.setCheckObjectReplication(true);
        String serialized = new EOToJSON()
                .setStartIndent(0)
                .toJSON(eo);
        /*String toCompare = MapProviderJSON.toJSONMap(S_KEY0, S_STRING, S_LEVEL0,
                MapProviderJSON.toJSONMap(EOToJSON.REPEATED, Path.DELIMITER));
        Assert.assertEquals(toCompare, serialized);*/
    }

    /**
     * Given eo empty with no Model
     * when adding jsn map string
     * value will be added with the correct type.
     */
    @Test
    public void givenMapString_withJsonMapString_ok()  {
        final EO eoEmpty = ProviderRootTestScope.createEo();
        final String jsnString = TestProviderAnObjectJson.STRING.content();
        eoEmpty
                .mapObject(jsnString);
        Assert.assertEquals(S_STRING, eoEmpty.get(AnObject.MY_STRING));
        Assert.assertEquals(Map.class, eoEmpty.getModelClass());
        Assert.assertEquals(String.class, eoEmpty.getEo(AnObject.MY_STRING).getModelClass());
    }

    /**
     * Given eo empty with no Model
     * when adding jsn map boolean
     * value will be added with the correct type.
     */
    @Test
    public void givenMapEmpty_withJsonMapBoolean_ok()  {
        final EO eoEmpty = ProviderRootTestScope.createEo();
        final String jsonBoolean = TestProviderAnObjectJson.BOOLEAN.content();
        eoEmpty
                .mapObject(jsonBoolean);
        Assert.assertEquals(S_BOOLEAN, eoEmpty.get(AnObject.MY_BOOLEAN));
        Assert.assertEquals(Map.class, eoEmpty.getModelClass());
        Assert.assertEquals(Boolean.class, eoEmpty.getEo(AnObject.MY_BOOLEAN).getModelClass());
    }
}
