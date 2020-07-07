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

import java.util.LinkedHashMap;
import java.util.List;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_NAME;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class EOPathMap_value_Test extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(EOPathMap_value_Test.class);

    @Test
    public void givenString_withString_fails()  {
        final EO eoString = DevObjectProvider.createEO(String.class);
        EOTestHelper
                .mapEO_fails(eoString, F_TEST_STRING, S_STRING_OTHER);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eoString.get());
    }

    @Test
    public void givenMapString_withString_ok()  {
        final EO eoMapString = TestEOProvider.create(String.class);
        EOTestHelper
                .mapEO_ok(eoMapString, F_TEST_STRING, S_STRING_OTHER);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING_OTHER, eoMapString.get(F_TEST_STRING));
    }


    @Test
    public void givenMapEmpty_withString_ok()  {
        final EO eoMapString = DevObjectProvider.createEO();
        EOTestHelper
                .mapEO_ok(eoMapString, F_TEST_STRING, S_STRING_OTHER);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING_OTHER, eoMapString.get(F_TEST_STRING));
    }

    @Test
    public void givenMapString_withInteger_ok()  {
        final EO eoMapString = TestEOProvider.create(String.class);
        EOTestHelper
                .mapEO_ok(eoMapString, F_TEST_STRING, S_INTEGER);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_INTEGER.toString(), eoMapString.get(F_TEST_STRING));
    }

    @Test
    public void givenMapMapEmpty_withMapString_ok()  {
        final EO eoMapString = MapProviderEO.createEmpty();
        final EO child = EOTestHelper
                .mapEO_ok(eoMapString, F_UNTYPED_MAP, MapProvider.createString(), LinkedHashMap.class);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, child.get(F_TEST_STRING));
    }


    @Test
    public void givenMapString_withLinkedHashMap_fails()  {
        final EO eoMapString = TestEOProvider.create(String.class);
        EOTestHelper
                .mapEO_fails(eoMapString, F_TEST_STRING, new LinkedHashMap());
    }

    @Test
    public void givenMapSTEmpty_withST_ok()  {
        final EO eoMapEmpty = MapProviderEO.createEmpty();
        final EO child = EOTestHelper
                .mapEO_ok(eoMapEmpty, F_SUB_TEST, STProvider.createString());
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, child.get(F_TEST_STRING));
        Assert.assertEquals(INFO_COMPARE_FAILS, SubTest.class, child.getModelClass());
    }

    @Test
    public void givenMapEmpty_withJsonMap_ok()  {
        final EO eoBTEmpty = BTProviderEO.createEmpty();
        EOTestHelper
                .mapEO_fails(eoBTEmpty, F_SUB_TEST_MAP, BTProvider.createString(), BasicTest.class);
    }

    @Test
    public void givenMapEmpty_withSTAndLongerPath()  {
        final EO eoMapEmpty = TestEOProvider.create();
        final String path = toPath(F_TEST_OBJECT, F_SUB_TEST);
        final EO child = eoMapEmpty
                .setPathValue(path, STProvider.createSimple());
        Assert.assertEquals(SubTest.class, eoMapEmpty.getChild(path).getModelClass());
        Assert.assertEquals(SubTest.class, eoMapEmpty.get(path).getClass());
        Assert.assertEquals(S_STRING_OTHER, eoMapEmpty.get(path + Path.DELIMITER + F_NAME));
        Assert.assertEquals(S_STRING, child.get(F_TEST_STRING));
    }


    @Test
    public void givenMapEmpty_withMapPathAlreadyUsedByScalarTypeValue()  {
        final EO eoMap = TestEOProvider.create();
        eoMap.setPathValue(S_LEVEL0,MapProvider.createSmall());

        eoMap.setPathValue(toPath(S_LEVEL0, F_TEST_INTEGER), MapProvider.createSmall());
        Assert.assertFalse(INFO_LOG_NOT_EMPTY_FAILS, eoMap.getLog().isEmpty());
    }

    /**
     * Given eo with no Model
     * when adding jsn map small to a path
     * values will be added with the correct type to the path.
     */
    @Test
    public void givenMapEmpty_withJsnSmallLevel0_ok()  {
        final String jsnSmall = MapProviderJSON.readSmall();
        final EO eoEmpty = TestEOProvider.create();
        final EO child = eoEmpty.setPathValue(S_LEVEL0, jsnSmall);
        Assert.assertEquals(new Long(S_INTEGER), eoEmpty.getChild(S_LEVEL0).get(F_TEST_INTEGER));
        Assert.assertEquals(S_STRING, child.get(F_TEST_STRING));
    }

    /**
     * Given eo with no Model
     * when adding json map small to a path
     * values will be added with the correct type to the path.
     */
    @Test
    public void givenMapEmpty_withJsonSmallLevel0_ok()  {
        final EO eoEmpty = TestEOProvider.create();
        final String jsonSmall = MapProviderJSON.readSmall();

        final EO child = eoEmpty.setPathValue(S_LEVEL0, jsonSmall);
        Assert.assertEquals(new Long(S_INTEGER), eoEmpty.getChild(S_LEVEL0).get(F_TEST_INTEGER));
        Assert.assertEquals(S_STRING, child.get(F_TEST_STRING));
    }

    /**
     * Given eo empty with no Model
     * when adding jsn map small to a longer path
     * values will be added with the correct type to the path.
     */
    @Test
    public void givenMapEmpty_withJsnSmallPath2_ok()  {
        final EO eoEmpty = TestEOProvider.create();
        final String jsnSmall = MapProviderJSN.readSmall();

        final EO child = eoEmpty.setPathValue(S_PATH2, jsnSmall);
        Assert.assertEquals(S_INTEGER, eoEmpty.getChild(S_PATH2).get(F_TEST_INTEGER));
        Assert.assertEquals(S_STRING, child.get(F_TEST_STRING));
    }

    /**
     * Given eo empty with no Model
     * when adding json map small to a longer path
     * values will be added with the correct type to the path.
     */
    @Test
    public void givenMapEmpty_withJsonSmallPath2_ok()  {
        final EO eoEmpty = TestEOProvider.create();
        final String jsonSmall = MapProviderJSON.readSmall();

        final EO child = eoEmpty.setPathValue(S_PATH2, jsonSmall);

        Assert.assertEquals(new Long(S_INTEGER), eoEmpty.getChild(S_PATH2).get(F_TEST_INTEGER));
        Assert.assertEquals(S_STRING, child.get(F_TEST_STRING));
    }


    /**
     * Given eo empty with no Model
     * when adding jsn map all to a longer path
     * values will be added with the correct type to the path.
     */
    @Test
    public void givenMapEmpty_withJsnAllPath2_ok()  {
        final EO eoEmpty = TestEOProvider.create();
        final String jsnAll = MapProviderJSN.readAll();

        final EO child = eoEmpty.setPathValue(S_PATH2, jsnAll);
        Assert.assertEquals(S_INTEGER, eoEmpty.getChild(S_PATH2).get(F_TEST_INTEGER));
        Assert.assertEquals(S_STRING, child.get(F_TEST_STRING));
    }

    /**
     * Given eo empty with no Model
     * when adding json map all to a longer path
     * values will be added with the correct type to the path.
     */
    @Test
    public void givenMapEmpty_withJsonAllPath2_ok()  {
        final EO eoEmpty = TestEOProvider.create();
        final String jsonAll = MapProviderJSON.readAll();

        final EO child = eoEmpty.setPathValue(S_PATH2, jsonAll);

        Assert.assertEquals(new Long(S_INTEGER), eoEmpty.getChild(S_PATH2).get(F_TEST_INTEGER));
        Assert.assertEquals(S_STRING, child.get(F_TEST_STRING));
    }

    @Test
    public void givenListEmpty_withBoolean_ok()  {
        final EO root = DevObjectProvider.createEO(List.class);
        root
                .setPathValue("test", S_BOOLEAN);
        Assert.assertEquals(1, root.keys().size());
        Assert.assertEquals(1, ((EOContainer) root).keysValue().size());
        Assert.assertEquals(S_BOOLEAN, root.get(S0));
    }


    @Test
    public void givenBTString_WithString_ok()  {
        final EO eoBTString = BTProviderEO.createString();
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eoBTString.get(F_TEST_STRING));
        EOTestHelper
                .mapEO_ok(eoBTString, F_TEST_STRING, S_STRING_OTHER);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING_OTHER, eoBTString.get(F_TEST_STRING));
    }

    @Test
    public void givenBTString_WithIntegerAtStringField_ok()  {
        final EO eoBTString = BTProviderEO.createString();
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eoBTString.get(F_TEST_STRING));
        EOTestHelper
                .mapEO_ok(eoBTString, F_TEST_STRING, S_INTEGER);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_INTEGER.toString(), eoBTString.get(F_TEST_STRING));
    }

    @Test
    public void givenBTEmpty_WithIntegerAtStringField_ok()  {
        final EO eoBTString = BTProviderEO.createEmpty();
        Assert.assertNull(INFO_NULL_FAILS, eoBTString.get(F_TEST_STRING));
        EOTestHelper
                .mapEO_ok(eoBTString, F_TEST_STRING, S_INTEGER, String.class);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_INTEGER.toString(), eoBTString.get(F_TEST_STRING));
    }

    @Test
    public void givenBTEmpty_WithInteger_ok()  {
        final EO eoBTString = BTProviderEO.createEmpty();
        EOTestHelper
                .mapEO_ok(eoBTString, F_TEST_INTEGER, S_INTEGER);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_INTEGER, eoBTString.get(F_TEST_INTEGER));
    }

    @Test
    public void givenBTString_WithMap_fails()  {
        final EO eoString = BTProviderEO.createString();
        EOTestHelper
                .mapEO_fails(eoString, F_TEST_STRING, new LinkedHashMap());
    }

    @Test
    public void givenBTEmpty_withUnknownFieldKey_fails()  {
        final EO eoBTEmpty = TestEOProvider.create(BasicTest.class);
        eoBTEmpty.setPathValue(SAMPLE_KEY_UNKNOW, S_INTEGER);
        TestObjectProvider.checkLogNotEmpty(eoBTEmpty);
    }


    @Test
    public void givenBTDateEmpty_WithDate_ok()  {
        final EO eoBTString = BTProviderEO.createEmpty();
        EOTestHelper
                .mapEO_ok(eoBTString, F_TEST_DATE, SAMPLE_DATE);
        Assert.assertEquals(INFO_COMPARE_FAILS, SAMPLE_DATE, eoBTString.get(F_TEST_DATE));
    }

    @Test
    public void givenBTUntypedMap_WithHashMap_ok()  {
        final EO eoString = BTProviderEO.createMap();
        EOTestHelper
                .mapEO_ok(eoString, F_UNTYPED_MAP, new LinkedHashMap());
    }

    @Test
    public void givenBTST_withSTString_ok()  {
        final EO eoBTEmpty = BTProviderEO.createEmpty();
        final EO child = EOTestHelper
                .mapEO_ok(eoBTEmpty, F_SUB_TEST, STProvider.createString(), SubTest.class);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, child.get(F_TEST_STRING));
        Assert.assertEquals(INFO_COMPARE_FAILS, SubTest.class, child.getModelClass());
    }

    @Test
    public void givenBTST_withMapString_ok()  {
        final EO eoBTEmpty = BTProviderEO.createEmpty();
        final EO child = EOTestHelper
                .mapEO_ok(eoBTEmpty, F_SUB_TEST, MapProvider.createString(), SubTest.class);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, child.get(F_TEST_STRING));
        Assert.assertEquals(INFO_COMPARE_FAILS, SubTest.class, child.getModelClass());
    }

    @Test
    public void givenBTST_withBTString_ok()  {
        final EO eoBTEmpty = BTProviderEO.createEmpty();
        final EO child = EOTestHelper
                .mapEO_ok(eoBTEmpty, F_SUB_TEST, BTProvider.createString(), SubTest.class);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, child.get(F_TEST_STRING));
        Assert.assertEquals(INFO_COMPARE_FAILS, SubTest.class, child.getModelClass());
    }

    @Test
    public void givenBTST_withJSONString_ok()  {
        final EO eoBTEmpty = BTProviderEO.createEmpty();
        final String json = MapProviderJSON.toJSONMap(F_TEST_STRING, S_STRING);
        final EO child = EOTestHelper
                .mapEO_ok(eoBTEmpty, F_SUB_TEST, json, SubTest.class);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, child.get(F_TEST_STRING));
        Assert.assertEquals(INFO_COMPARE_FAILS, SubTest.class, child.getModelClass());
    }

    @Test
    public void givenBTST_withJSONUnknown_fails()  {
        final EO eoBTEmpty = BTProviderEO.createEmpty();
        final String jsonUnknown = MapProviderJSON.toJSONMap(SAMPLE_KEY_UNKNOW, S_STRING);
        EOTestHelper
                .mapEO_fails(eoBTEmpty, F_SUB_TEST, jsonUnknown, SubTest.class);
    }

    @Test
    public void givenBTMapST_withBTString_fails()  {
        final EO eoBTEmpty = BTProviderEO.createEmpty();
        EOTestHelper
                .mapEO_fails(eoBTEmpty, F_SUB_TEST_MAP, BTProvider.createString(), BasicTest.class);
    }

    @Test
    public void givenBTMapST_withJSONMap_fails()  {
        final EO eoBTEmpty = BTProviderEO.createEmpty();
        final String json = MapProviderJSON.toJSONMap(S_TEST_STRING, S_STRING);
        EOTestHelper
                .mapEO_fails(eoBTEmpty, F_SUB_TEST_MAP, json);
    }


}

