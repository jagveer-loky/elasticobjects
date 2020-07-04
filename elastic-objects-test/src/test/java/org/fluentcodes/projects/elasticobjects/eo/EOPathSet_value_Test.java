package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.assets.SubTest;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.test.*;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 1.10.2018
 */
public class EOPathSet_value_Test extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(EOPathSet_value_Test.class);

    @Test
    public void givenMapString_withString_ok()  {
        final EO eoString = DevObjectProvider.createEOMapString();
        EOTestHelper
                .setEO_ok(eoString, F_TEST_STRING, S_STRING_OTHER);
    }

    @Test
    public void givenString_withStringToString_fails()  {
        final EO eoString = DevObjectProvider.createEOString();
        EOTestHelper
                .setEO_fails(eoString, F_TEST_STRING, S_STRING_OTHER);
    }

    @Test
    public void givenString_withIntegerToString_fails()  {
        final EO eoString = DevObjectProvider.createEOString();
        EOTestHelper
                .setEO_fails(eoString, F_TEST_STRING, S_INTEGER);
    }

    @Test
    public void givenMapEmpty_withStringPath2_ok()  {
        final EO eoMapString = DevObjectProvider.createEOMapString();
        EOTestHelper
                .setEO_ok(eoMapString, S_PATH2, S_STRING);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eoMapString.get(S_PATH2));
    }

    @Test
    public void givenMapString_withHashMap_ok()  {
        final EO eoMapString = DevObjectProvider.createEOMapString();
        EOTestHelper
                .setEO_ok(eoMapString, F_TEST_STRING, new LinkedHashMap());
        Assert.assertEquals(INFO_COMPARE_FAILS, 0, eoMapString.getChild(F_TEST_STRING).size());
    }


    @Test
    public void givenMapStringTyped_withInteger_ok()  {
        final EO eoString = DevObjectProvider.createEO(Map.class, String.class);
        eoString
                .add(F_TEST_STRING)
                .set(S_INTEGER);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_INTEGER.toString(), eoString.get(F_TEST_STRING));
    }

    @Test
    public void givenMapStringTyped_withHashMap_fails()  {
        final EO eoString = DevObjectProvider.createEO(Map.class, String.class);
        EOTestHelper.setEO_fails(eoString, F_TEST_STRING, new LinkedHashMap());
    }

    @Test
    public void givenMapMapString_withString_ok()  {
        final EO eoMapEmpty = DevObjectProvider.createEO();
        EO child = EOTestHelper
                .setEO_ok(eoMapEmpty, F_UNTYPED_MAP, MapProvider.createString());
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, child.get(F_TEST_STRING));
        EOTestHelper
                .setEO_ok(eoMapEmpty, F_UNTYPED_MAP, S_STRING);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eoMapEmpty.get(F_UNTYPED_MAP));
        Assert.assertNull(INFO_NULL_FAILS, child.get(F_TEST_STRING));
    }

    @Test
    public void givenListEmpty_withBoolean_ok()  {
        final EO root = DevObjectProvider.createEO(List.class);
        root
                .add(F_TEST_BOOLEAN)
                .set(S_BOOLEAN);
        Assert.assertEquals(1, root.keys().size());
        Assert.assertEquals(1, ((EOContainer) root).keysValue().size());
        Assert.assertEquals(S_BOOLEAN, root.get(S0));
    }

    @Test
    public void givenMapEmpty_withListStringLevel0()  {
        EO adapter = DevObjectProvider.createEO();
        List<String> list = ListProvider.createString();
        adapter.add(S_LEVEL0)
                .set(list);
        Assert.assertEquals(S_STRING, adapter.get(S_LEVEL0 + Path.DELIMITER + S0));
    }


    @Test
    public void givenBTString_withString_ok()  {
        final EO eoBTString = BTProviderEO.createString();
        EOTestHelper
                .setEO_ok(eoBTString, F_TEST_STRING, S_STRING_OTHER);

    }

    @Test
    public void givenBTString_withInteger_fails()  {
        final EO eoBTString = BTProviderEO.createString();
        EOTestHelper
                .setEO_fails(eoBTString, F_TEST_STRING, S_INTEGER);
    }

    @Test
    public void givenBTIntegerEmpty_withInteger_ok()  {
        final EO eoBTString = BTProviderEO.createSmall();
        EOTestHelper
                .setEO_ok(eoBTString, F_TEST_INTEGER, S_INTEGER);
    }

    @Test
    public void givenBTLongEmpty_withLong_ok()  {
        final EO eoBTString = BTProviderEO.create();
        EOTestHelper
                .setEO_ok(eoBTString, F_TEST_LONG, SAMPLE_LONG);
    }

    @Test
    public void givenBTDoubleEmpty_withDouble_ok()  {
        final EO eoBTString = BTProviderEO.create();
        EOTestHelper
                .setEO_ok(eoBTString, F_TEST_DOUBLE, SAMPLE_DOUBLE);
    }

    @Test
    public void givenBTDatempty_withDate_ok()  {
        final EO eoBTString = BTProviderEO.create();
        EOTestHelper
                .setEO_ok(eoBTString, F_TEST_DATE, SAMPLE_DATE);
    }

    @Test
    public void givenBTUnknownEmpty_withString_fails()  {
        final EO eoBTString = BTProviderEO.createString();
        EOTestHelper
                .setEO_fails(eoBTString, S_KEY_NOT_EXISTING, S_STRING);
    }

    @Test
    public void givenBTFloatEmpty_withFloat_ok()  {
        final EO eoEmpty = BTProviderEO.createEmpty();
        EOTestHelper
                .setEO_ok(eoEmpty, F_TEST_FLOAT, SAMPLE_FLOAT);
    }

    @Test
    public void givenBTFloatEmpty_withString_ok()  {
        final EO eoEmpty = BTProviderEO.createEmpty();
        eoEmpty.add(F_TEST_FLOAT)
                .setModels(Float.class)
                .set(SAMPLE_FLOAT.toString());
        Assert.assertEquals(INFO_COMPARE_FAILS, SAMPLE_FLOAT, eoEmpty.get(F_TEST_FLOAT));
    }

    @Test
    public void givenBTObjectEmpty_withHashMap_fails()  {
        final EO eoEmpty = BTProviderEO.createEmpty();
        EOTestHelper
                .setEO_fails(eoEmpty, F_TEST_OBJECT, new HashMap(), Object.class);
    }

    @Test
    public void givenBTString_withLinkedHashMap_fails()  {
        final EO eoString = BTProviderEO.createString();
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eoString.get(F_TEST_STRING));
        EOTestHelper
                .setEO_fails(eoString, F_TEST_STRING, new LinkedHashMap());
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eoString.get(F_TEST_STRING));
    }


    @Test
    public void givenBTUntypedMap_withLinkedHashMap_ok()  {
        final EO eoBTUntypedMap = BTProviderEO.createMap();
        Assert.assertNotNull(INFO_NULL_FAILS, eoBTUntypedMap.get(F_UNTYPED_MAP));
        Assert.assertEquals(INFO_COMPARE_FAILS, 2, eoBTUntypedMap.getChild(F_UNTYPED_MAP).size());

        eoBTUntypedMap.add(F_UNTYPED_MAP).set(new LinkedHashMap());
        Assert.assertEquals(INFO_COMPARE_FAILS, HashMap.class, eoBTUntypedMap.getChild(F_UNTYPED_MAP).getModelClass());
        Assert.assertEquals(INFO_COMPARE_FAILS, 0, eoBTUntypedMap.getChild(F_UNTYPED_MAP).size());
    }

    @Test
    public void givenBTEmpty_withSTString_ok()  {
        final EO eoBTEmpty = BTProviderEO.createEmpty();
        final String path = toPath(F_SUB_TEST, F_TEST_STRING);
        EOTestHelper
                .setEO_ok(eoBTEmpty, path, S_INTEGER, Integer.class);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_INTEGER.toString(), eoBTEmpty.get(path));
    }

    // added 15.9.2018 wdi
    @Test
    public void setWithLongSTPath_storeValueWithSTModel()  {
        final EO eoBT = BTProviderEO.createEmpty();
        final EO child = eoBT.add(STProviderEO.createSTPath(3) + Path.DELIMITER + F_TEST_STRING)
                .set(S_STRING);

        Assert.assertEquals(SubTest.class, eoBT.getChild(STProviderEO.createSTPath(1)).getModelClass());
        Assert.assertEquals(SubTest.class, eoBT.getChild(STProviderEO.createSTPath(2)).getModelClass());
        Assert.assertEquals(SubTest.class, eoBT.getChild(STProviderEO.createSTPath(3)).getModelClass());
        Assert.assertEquals(S_STRING, child.get());
    }

    @Test
    public void givenBTMapST_withString_fails()  {
        final EO eoBTEmpty = BTProviderEO.createEmpty();
        EOTestHelper
                .setEO_fails(eoBTEmpty, F_SUB_TEST_MAP, S_STRING);
    }


    @Test
    public void givenListBTEmpty_withSTString_ok()  {
        final EO eoBTEmpty = TestEOProvider.createEOFromJson(List.class, BasicTest.class);
        final String path = toPath(S0, F_SUB_TEST, F_TEST_STRING);
        EOTestHelper
                .setEO_ok(eoBTEmpty, path, S_STRING);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eoBTEmpty.get(path));
    }

    @Test
    public void givenListBTEmpty_withSTStringAt1_ok()  {
        final EO eoBTEmpty = TestEOProvider.createEOFromJson(List.class, BasicTest.class);
        final String path = toPath(S1, F_SUB_TEST, F_TEST_STRING);
        EOTestHelper
                .setEO_ok(eoBTEmpty, path, S_STRING);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eoBTEmpty.get(path));
    }

    @Test
    public void givenListBTEmpty_withMap_ok()  {
        final EO eoBTEmpty = TestEOProvider.createEOFromJson(List.class, BasicTest.class);
        EOTestHelper
                .setEO_fails(eoBTEmpty, F_SUB_TEST_MAP, MapProvider.createSmall());
    }
}

