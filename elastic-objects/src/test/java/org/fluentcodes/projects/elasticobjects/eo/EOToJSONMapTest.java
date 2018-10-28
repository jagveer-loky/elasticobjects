package org.fluentcodes.projects.elasticobjects.eo;

import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.test.*;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by werner.diwischek on 14.1.18.
 */
public class EOToJSONMapTest extends TestHelper {

    @Test
    public void withDefault() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = MapProviderEO.create();
        String serialized = new EOToJSON().toJSON(adapter);

        AssertEO.compare(serialized);
    }

    @Test
    public void withIndent0() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = MapProviderEO.create();
        String stringified = new EOToJSON()
                .setStartIndent(0)
                .toJSON(adapter);
        AssertEO.compare(stringified);
    }

    @Test
    public void withIndent1() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = MapProviderEO.create();
        String stringified = new EOToJSON()
                .setStartIndent(2)
                .toJSON(adapter);
        AssertEO.compare(stringified);
    }

    @Test
    public void withIndent2() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = MapProviderEO.create();
        String stringified = new EOToJSON()
                .setStartIndent(2)
                .toJSON(adapter);
        AssertEO.compare(stringified);
    }

    @Test
    public void withStringScalar() throws Exception {
        TestHelper.printStartMethod();
        final EO adapter = MapProviderEO.createString();
        String stringified = new EOToJSON()
                .setStartIndent(2)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
        String file = AssertEO.compare(stringified);
        EO fromJson = TestObjectProvider.createEOBuilder().mapFile(file);
        Assert.assertEquals(S_STRING, fromJson.get(F_TEST_STRING));
    }

    @Test
    public void withIntegerScalar() throws Exception {
        TestHelper.printStartMethod();
        final EO adapter = MapProviderEO.createInteger();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
        String file = AssertEO.compare(stringified);
        EO fromJson = TestObjectProvider.createEOBuilder().mapFile(file);
        Assert.assertEquals(S_INTEGER, fromJson.get(F_TEST_INTEGER));
    }

    @Test
    public void withLongScalar() throws Exception {
        TestHelper.printStartMethod();
        final EO adapter = MapProviderEO.createLong();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
        String file = AssertEO.compare(stringified);
        EO fromJson = TestObjectProvider.createEOBuilder().mapFile(file);
        Assert.assertEquals(SAMPLE_LONG, fromJson.get(F_TEST_LONG));
    }

    @Test
    public void withFloatScalar() throws Exception {
        TestHelper.printStartMethod();
        final EO adapter = MapProviderEO.createFloat();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
        String file = AssertEO.compare(stringified);
        EO fromJson = TestObjectProvider.createEOBuilder().mapFile(file);
        Assert.assertEquals(SAMPLE_FLOAT, fromJson.get(F_TEST_FLOAT));
    }

    @Test
    public void withDoubleScalar() throws Exception {
        TestHelper.printStartMethod();
        final EO adapter = MapProviderEO.createDouble();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
        String file = AssertEO.compare(stringified);
        EO fromJson = TestObjectProvider.createEOBuilder().mapFile(file);
        Assert.assertEquals(SAMPLE_DOUBLE, fromJson.get(F_TEST_DOUBLE));
    }

    @Test
    public void withDateScalar() throws Exception {
        TestHelper.printStartMethod();
        final EO adapter = MapProviderEO.createDate();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
        String file = AssertEO.compare(stringified);
        EO fromJson = TestObjectProvider.createEOBuilder().mapFile(file);
        Assert.assertEquals(SAMPLE_DATE, fromJson.get(F_TEST_DATE));
    }

    @Test
    public void withBooleanScalar() throws Exception {
        TestHelper.printStartMethod();
        final EO adapter = MapProviderEO.createBoolean();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
        String file = AssertEO.compare(stringified);
        EO fromJson = TestObjectProvider.createEOBuilder().mapFile(file);
        Assert.assertEquals(S_BOOLEAN, fromJson.get(F_TEST_BOOLEAN));
    }

    @Test
    public void withIndentScalar() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = MapProviderEO.create();
        String stringified = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
        AssertEO.compare(stringified);
    }

    @Test
    public void loopScalar() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = MapProviderEO.create();
        long duration = System.currentTimeMillis();
        for (int i = 0; i<100; i++) {
            String stringified = new EOToJSON()
                    .setSerializationType(JSONSerializationType.SCALAR)
                    .toJSON(adapter);
        }
        duration = System.currentTimeMillis() - duration;
        System.out.println("Duration: " + duration + " ms.");
    }

    @Test
    public void mapBigAdapter10000() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = MapProviderEO.createBigEO(100);
        long duration = System.currentTimeMillis();
            String stringified = new EOToJSON()
                    .setSerializationType(JSONSerializationType.SCALAR)
                    .toJSON(adapter);
        duration = System.currentTimeMillis() - duration;
        System.out.println("Duration: " + duration + " ms.");
        AssertEO.compare(stringified);
    }

    @Test
    public void mapBigAdapterUnexpanded10000() throws Exception {
        TestHelper.printStartMethod();
        EO adapter = MapProviderEO.createBigAEOUnexpanded(100);
        long duration = System.currentTimeMillis();
        String stringified = new EOToJSON()
                .setSerializationType(JSONSerializationType.SCALAR)
                .toJSON(adapter);
        duration = System.currentTimeMillis() - duration;
        System.out.println("Duration: " + duration + " ms.");
        AssertEO.compare(stringified);
    }

    @Test
    public void setSameMaps() throws Exception {
        Map map = new LinkedHashMap<>();
        map.put(S_KEY0, S_STRING);
        map.put(S_KEY1, S_INTEGER);
        TestHelper.printStartMethod();
        EO adapter = TestObjectProvider.createEOFromJson();
        adapter.add(S_LEVEL0)
                .set(map);
        adapter.add(S_LEVEL1)
                .set(map);
        String toCompare = MapProviderJSON.toJSONMap(S_LEVEL0,
                MapProviderJSON.toJSONMap(S_KEY0, S_STRING, S_KEY1, S_INTEGER),
                S_LEVEL1,
                MapProviderJSON.toJSONMap(S_KEY0, S_STRING, S_KEY1, S_INTEGER)
        );
        String serialized = new EOToJSON()
                .setStartIndent(0)
                .toJSON(adapter);

        Assert.assertEquals(toCompare, serialized);
    }

    @Test
    public void setSameMapsWithCheck() throws Exception {
        Map map = new LinkedHashMap<>();
        map.put(S_KEY0, S_STRING);
        TestHelper.printStartMethod();
        EO adapter = TestObjectProvider.createEOFromJson();
        adapter.add()
                .set(map);
        adapter
                .add(S_LEVEL0)
                .set(map);
        adapter.setCheckObjectReplication(true);
        String serialized = new EOToJSON()
                .setStartIndent(0)
                .toJSON(adapter);
        String toCompare = MapProviderJSON.toJSONMap(S_KEY0, S_STRING, S_LEVEL0,
                MapProviderJSON.toJSONMap(EOToJSON.REPEATED, Path.DELIMITER));
        Assert.assertEquals(toCompare, serialized);
    }

}
