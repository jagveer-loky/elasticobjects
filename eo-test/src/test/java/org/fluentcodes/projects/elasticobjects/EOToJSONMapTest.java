package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.testitemprovider.*;

import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by werner.diwischek on 14.1.18.
 */
public class EOToJSONMapTest {

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
        
        final EO eo = ProviderMapJson.STRING.createMapEo();
        String stringified = new EOToJSON()
                .setStartIndent(2)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(eo);
    }

    @Test
    public void withIntegerScalar()  {
        
        final EO eo = ProviderMapJson.INT.createMapEo();
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
        
        final EO eo = ProviderMapJson.EMPTY.createMapEo();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(eo);
    }

    @Test
    public void withDoubleScalar()  {
        
        final EO eo = ProviderMapJsn.DOUBLE.createMapEo();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(eo);
    }

    @Test
    public void withDateScalar()  {
        
        final EO eo = ProviderMapJsn.DATE.createMapEo();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(eo);
     }

    @Test
    public void withBooleanScalar()  {
        
        final EO eo = ProviderMapJsn.DOUBLE.createMapEo();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(eo);
    }

    @Test
    public void withIndentScalar()  {
        EO eo = ProviderMapJsn.SMALL.createMapEo();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(eo);
    }

    @Test
    public void loopScalar()  {
        EO eo = ProviderMapJsn.EMPTY.createMapEo();
        long duration = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            String stringified = new EOToJSON()
                    .setSerializationType(JSONSerializationType.SCALAR)
                    .toJSON(eo);
        }
        duration = System.currentTimeMillis() - duration;
        System.out.println("Duration: " + duration + " ms.");
    }

   /* @Test
    public void mapBigAdapter10000()  {
        EO eo = null;//MapProviderEO.createBigEO(100);
        long duration = System.currentTimeMillis();
        String stringified = new EOToJSON()
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(eo);
        duration = System.currentTimeMillis() - duration;
        System.out.println("Duration: " + duration + " ms.");
        //AssertEO.compare(stringified);
    }*/

    /*@Test
    public void mapBigAdapterUnexpanded10000()  {
        
        EO eo = null;//DevObjectProvider(MapProvider.creatcreateBigAEOUnexpanded(100);
        long duration = System.currentTimeMillis();
        String stringified = new EOToJSON()
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(eo);
        duration = System.currentTimeMillis() - duration;
        System.out.println("Duration: " + duration + " ms.");
        //AssertEO.compare(stringified);
    }*/

    @Test
    public void setSameMaps()  {
        Map map = new LinkedHashMap<>();
        map.put(S_KEY0, S_STRING);
        map.put(S_KEY1, S_INTEGER);
        
        EO eo = ProviderRootTest.createEo();
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
        
        EO eo = ProviderRootTest.createEo();
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

}
