package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.test.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 1.10.2018
 */

public class EONoPathRootMap_value_Test {
    private static final Logger LOG = LogManager.getLogger(EONoPathRootMap_value_Test.class);

    @Test
    public void givenString_withInteger_ok() throws Exception {
        final EO eoString = DevObjectProvider.createEOString();
        EOTest
                .mapEOValue_ok(eoString, S_INTEGER);
        Assert.assertEquals(S_INTEGER.toString(), eoString.get());
    }

    @Test
    public void givenString_withBoolean_ok() throws Exception {
        final EO eoString = DevObjectProvider.createEOString();
        EOTest
                .mapEOValue_ok(eoString, S_BOOLEAN);
        Assert.assertEquals(S_BOOLEAN.toString(), eoString.get());
    }

    @Test
    public void givenString_withString_ok() throws Exception {
        final EO eoString = DevObjectProvider.createEOString();
        EOTest
                .mapEOValue_ok(eoString, S_STRING_OTHER);
        Assert.assertEquals(S_STRING_OTHER, eoString.get());
    }

    @Test
    public void givenString_withMap_fails() throws Exception {
        final EO eoString = DevObjectProvider.createEOString();
        EOTest
                .mapEOValue_fails(eoString, new LinkedHashMap());
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eoString.get());
    }

    @Test
    public void givenString_withBT_fails() throws Exception {
        final EO eoString = TestObjectProvider.createEOString();
        EOTest
                .mapEOValue_fails(eoString, new BasicTest());
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eoString.get());
    }

    @Test
    public void givenMapEmpty_withString_fails() throws Exception {
        final EO eoMap = DevObjectProvider.createEOMapEmpty();
        EOTest
                .mapEOValue_fails(eoMap, S_STRING_OTHER);
    }

    @Test
    public void givenMapString_withEmptyMap_ok_changesNothing() throws Exception {
        final EO eoMapString = MapProviderEO.createString();
        Assert.assertEquals(S_STRING, eoMapString.get(F_TEST_STRING));
        EOTest
                .mapEOValue_ok(eoMapString, new HashMap(), Map.class);
        Assert.assertEquals(S_STRING, eoMapString.get(F_TEST_STRING));
    }

    @Test
    public void givenMapString_withLinkedHashMapEmpty_ok() throws Exception {
        final EO eoMapString = DevObjectProvider.createEOMapString();
        EOTest
                .mapEOValue_ok(eoMapString, new LinkedHashMap());
    }

    @Test
    public void givenMapString_withMapInteger_ok() throws Exception {
        final EO eoMapString = MapProviderEO.createString();
        EOTest
                .mapEOValue_ok(eoMapString, MapProvider.createInteger());
        Assert.assertEquals(S_STRING, eoMapString.get(F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, eoMapString.get(F_TEST_INTEGER));
    }

    @Test
    public void givenMapEmpty_withST_ok() throws Exception {
        final EO eoMapEmpty = MapProviderEO.createEmpty();
        EOTest
                .mapEOValue_ok(eoMapEmpty, STProvider.createString());
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eoMapEmpty.get(F_TEST_STRING));
        Assert.assertEquals(INFO_COMPARE_FAILS, Map.class, eoMapEmpty.getModelClass());
    }

    @Test
    public void givenMapEmpty_withJsonMap_ok() throws Exception {
        final EO eoEmpty = MapProviderEO.createEmpty();
        final String json = MapProviderJSON.toJSONMap(F_TEST_STRING, S_STRING);
        EOTest
                .mapEOValue_ok(eoEmpty, json, Map.class);
        Assert.assertEquals(S_STRING, eoEmpty.get(F_TEST_STRING));
    }

    @Test
    public void givenMapString_withJsonMap_ok() throws Exception {
        final EO eoMapString = MapProviderEO.createString();
        final String json = MapProviderJSON.toJSONMap(F_TEST_STRING, S_STRING_OTHER);
        EOTest
                .mapEOValue_ok(eoMapString, json, Map.class);
        Assert.assertEquals(S_STRING_OTHER, eoMapString.get(F_TEST_STRING));
    }

    /**
     * Given eo empty with no Model
     * when adding jsn map string
     * value will be added with the correct type.
     */
    @Test
    public void givenMapString_withJsonMapString_ok() throws Exception {
        final EO eoEmpty = TestObjectProvider.createEOFromJson();
        final String jsnString = MapProviderJSON.readString();
        eoEmpty
                .add()
                .map(jsnString);

        Assert.assertEquals(S_STRING, eoEmpty.get(F_TEST_STRING));
        Assert.assertEquals(Map.class, eoEmpty.getModelClass());
        Assert.assertEquals(String.class, eoEmpty.getChild(F_TEST_STRING).getModelClass());
    }

    /**
     * Given eo empty with no Model
     * when adding jsn map boolean
     * value will be added with the correct type.
     */
    @Test
    public void givenMapEmpty_withJsonMapBoolean_ok() throws Exception {
        final EO eoEmpty = TestObjectProvider.createEOFromJson();
        final String jsonBoolean = MapProviderJSON.readBoolean();
        eoEmpty
                .add()
                .map(jsonBoolean);
        Assert.assertEquals(S_BOOLEAN, eoEmpty.get(F_TEST_BOOLEAN));
        Assert.assertEquals(Map.class, eoEmpty.getModelClass());
        Assert.assertEquals(Boolean.class, eoEmpty.getChild(F_TEST_BOOLEAN).getModelClass());
    }


    @Test
    public void givenListEmpty_withBoolean_fails() throws Exception {
        final EO root = DevObjectProvider.createEO(List.class);
        root
                .add()
                .map(S_BOOLEAN);
        Assert.assertEquals(List.class, root.getModelClass());
        Assert.assertFalse(INFO_LOG_NOT_EMPTY_FAILS, root.getLog().isEmpty());
    }

    @Test
    public void givenListEmpty_withBTBoolean_ok() throws Exception {
        final EO root = TestObjectProvider.createEOFromJson(List.class);
        root.add().map(BTProvider.createBoolean());
        Assert.assertEquals(1, root.keys().size());
        Assert.assertEquals(1, ((EOContainer) root).keysValue().size());
        Assert.assertEquals(S_BOOLEAN, root.get(S0));
    }

    @Test
    public void givenListStringEmpty_withBTBoolean_ok() throws Exception {
        EO root = TestObjectProvider.createEOFromJson(List.class, String.class);
        root
                .add()
                .map(BTProvider.createBoolean());
        Assert.assertEquals(1, root.keys().size());
        Assert.assertEquals(1, ((EOContainer) root).keysValue().size());
        Assert.assertEquals(S_BOOLEAN.toString(), root.get(S0));
    }

    @Test
    public void givenListStringEmpty_withSTString_ok() throws Exception {
        final EO eoLListString = TestObjectProvider.createEOFromJson(List.class, String.class);
        EOTest
                .mapEOValue_ok(eoLListString, STProvider.createString());
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eoLListString.get(S0));
    }

    /**
     * Given eo empty with no Model
     * when adding json map string
     * value will be added with the correct type.
     */
    @Test
    public void givenMapEmpty_withJsonString_ok() throws Exception {
        final EO eoEmpty = TestObjectProvider.createEOFromJson();
        final String jsonString = MapProviderJSON.readString();

        eoEmpty
                .add()
                .map(jsonString);

        Assert.assertEquals(S_STRING, eoEmpty.get(F_TEST_STRING));
        Assert.assertEquals(Map.class, eoEmpty.getModelClass());
        Assert.assertEquals(String.class, eoEmpty.getChild(F_TEST_STRING).getModelClass());
    }

    /**
     * Given eo empty with no Model
     * when adding json map boolean
     * value will be added with the correct type.
     */
    @Test
    public void givenMapEmpty_withJsonBoolean_ok() throws Exception {
        final EO eoEmpty = TestObjectProvider.createEOFromJson();
        final String jsonBoolean = MapProviderJSON.readBoolean();

        eoEmpty
                .add()
                .map(jsonBoolean);

        Assert.assertEquals(S_BOOLEAN, eoEmpty.get(F_TEST_BOOLEAN));
        Assert.assertEquals(Map.class, eoEmpty.getModelClass());
        Assert.assertEquals(Boolean.class, eoEmpty.getChild(F_TEST_BOOLEAN).getModelClass());
    }

    /**
     * Given eo empty with no Model
     * when adding json list small
     * the eo map will be transformed to a list but type will be used.
     */

    @Test
    public void givenMapEmpty_withListJsonSmall_ok() throws Exception {
        final EO eoEmpty = MapProviderEO.createEmpty();
        final String jsonListSmall = ListProviderJSON.readSmall();
        eoEmpty
                .add()
                .map(jsonListSmall);

        Assert.assertEquals(List.class, eoEmpty.getModelClass());
        Assert.assertEquals(ArrayList.class, eoEmpty.get().getClass());
        Assert.assertEquals(S_STRING, eoEmpty.get(S0));
        Assert.assertEquals(new Long(S_INTEGER), eoEmpty.get(S1));
    }

    /**
     * Given eo empty with no Model
     * when adding jsn list small
     * the eo map will be transformed to a list but type will be used.
     */
    @Test
    public void givenMapEmpty_withJsnListSmall_ok() throws Exception {
        final EO eoEmpty = MapProviderEO.createEmpty();
        final String jsnListSmall = ListProviderJSN.readSmall();
        eoEmpty
                .add()
                .map(jsnListSmall);
        Assert.assertEquals(List.class, eoEmpty.getModelClass());
        Assert.assertEquals(ArrayList.class, eoEmpty.get().getClass());
        Assert.assertEquals(S_STRING, eoEmpty.get(S0));
        Assert.assertEquals(S_INTEGER, eoEmpty.get(S1));
    }

    /**
     * Given eo with values
     * when adding json list small
     * the eo map should be used and its position used as key.
     */
    @Test
    public void givenMapSmall_withJsonListSmall_ok() throws Exception {
        final EO eoSmall = MapProviderEO.createSmall();
        final String jsonListSmall = ListProviderJSON.readSmall();

        eoSmall
                .add()
                .map(jsonListSmall);

        Assert.assertEquals(Map.class, eoSmall.getModelClass());
        Assert.assertEquals(LinkedHashMap.class, eoSmall.get().getClass());
        Assert.assertEquals(S_STRING, eoSmall.get(F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, eoSmall.get(F_TEST_INTEGER));
        Assert.assertEquals(S_STRING, eoSmall.get(S0));
        Assert.assertEquals(new Long(S_INTEGER), eoSmall.get(S1));
    }

    /**
     * Given eo with values
     * when adding jsn list small
     * the eo map should be used and add integer position as key.
     */

    @Test
    public void givenMapSmall_withJsnListSmall2_fails() throws Exception {
        final EO eoSmall = MapProviderEO.createSmall();
        final String jsnListSmall = ListProviderJSON.readSmall();
        eoSmall
                .add()
                .map(jsnListSmall);
        Assert.assertEquals(Map.class, eoSmall.getModelClass());
        Assert.assertEquals(LinkedHashMap.class, eoSmall.get().getClass());
        Assert.assertFalse(INFO_LOG_EMPTY_FAILS + eoSmall.getLog(), eoSmall.getLog().isEmpty());
        Assert.assertEquals(S_STRING, eoSmall.get(F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, eoSmall.get(F_TEST_INTEGER));
        Assert.assertEquals(S_STRING, eoSmall.get(S0));
        Assert.assertEquals(new Long(S_INTEGER), eoSmall.get(S1));
    }

    /**
     * Given empty eo with List model
     * when adding jsn map empty
     * original model ListModel is kept.
     */
    @Test
    public void givenListEmpty_withJsnEmpty_ok() throws Exception {
        final EO listEO = ListProviderEO.createEmpty();
        final String jsnListEmpty = ListProviderJSON.readEmpty();
        listEO
                .add()
                .map(jsnListEmpty);
        Assert.assertEquals(List.class, listEO.getModelClass());
        Assert.assertEquals(Object.class, listEO.getModels().getChildModelClass());
        //TODO checkConfig if necessary Assert.assertTrue(params.isFromJSON());
    }

    /**
     * Given empty eo with ListModel model
     * when adding json map small
     * original model ListModel is kept and values mapped to List.
     */

    @Test
    public void givenListEmpty_withJsnMapSmall_ok() throws Exception {
        final EO listEO = ListProviderEO.createEmpty();
        final String jsnMapSmall = ListProviderJSN.readSmall();
        listEO
                .add()
                .map(jsnMapSmall);
        Assert.assertEquals(S_STRING, listEO.get(S0));
        Assert.assertEquals(S_INTEGER, listEO.get(S1));
        Assert.assertEquals(List.class, listEO.getModelClass());
        Assert.assertEquals(String.class, listEO.getChild(S0).getModelClass());
    }

    @Test
    public void givenBTString_withMapInteger_ok() throws Exception {
        final EO eoBTString = BTProviderEO.createString();
        EOTest
                .mapEOValue_ok(eoBTString, MapProvider.createInteger());
        Assert.assertEquals(S_STRING, eoBTString.get(F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, eoBTString.get(F_TEST_INTEGER));
    }

    @Test
    public void givenBTWithString_withBTWithInteger_ok() throws Exception {
        final EO eoBTString = BTProviderEO.createString();

        EOTest
                .mapEOValue_ok(eoBTString, BTProvider.createInteger());
        Assert.assertEquals(S_STRING, eoBTString.get(F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, eoBTString.get(F_TEST_INTEGER));
    }

    @Test
    public void givenBTWithString_withMapWithInteger_ok() throws Exception {
        final EO eoBTString = BTProviderEO.createString();
        Assert.assertEquals(S_STRING, eoBTString.get(F_TEST_STRING));

        EOTest
                .mapEOValue_ok(eoBTString, MapProvider.createInteger());
        Assert.assertEquals(S_STRING, eoBTString.get(F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, eoBTString.get(F_TEST_INTEGER));
    }

    @Test
    public void givenBTWithString_withString_fails() throws Exception {
        final EO eoBTString = BTProviderEO.createString();
        EOTest
                .mapEO_fails(eoBTString, S_STRING_OTHER);
        Assert.assertEquals(S_STRING, eoBTString.get(F_TEST_STRING));
    }

    @Test
    public void givenBTString_withMap_ok() throws Exception {
        final EO eoBTString = BTProviderEO.createString();
        EOTest
                .mapEOValue_ok(eoBTString, new HashMap());
        Assert.assertEquals(S_STRING, eoBTString.get(F_TEST_STRING));
    }

    @Test
    public void givenBTString_WithBTInteger_ok() throws Exception {
        final EO eoBTString = BTProviderEO.createString();
        EOTest
                .mapEOValue_ok(eoBTString, BTProvider.createInteger());

        Assert.assertEquals(S_STRING, eoBTString.get(F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, eoBTString.get(F_TEST_INTEGER));
    }

    @Test
    public void givenBTEmpty_withJSONMap_ok() throws Exception {
        final EO eoBTEmpty = BTProviderEO.createEmpty();
        final String json = MapProviderJSON.toJSONMap(F_TEST_STRING, S_STRING);
        EOTest
                .mapEOValue_ok(eoBTEmpty, json, BasicTest.class);
        Assert.assertEquals(S_STRING, eoBTEmpty.get(F_TEST_STRING));
        Assert.assertEquals(BasicTest.class, eoBTEmpty.getModelClass());
    }

    @Test
    public void givenBTString_withJSONMap_ok() throws Exception {
        final EO eoBTString = BTProviderEO.createString();
        final String json = MapProviderJSON.toJSONMap(F_TEST_STRING, S_STRING_OTHER);
        EOTest
                .mapEOValue_ok(eoBTString, json, BasicTest.class);
        Assert.assertEquals(S_STRING_OTHER, eoBTString.get(F_TEST_STRING));
        Assert.assertEquals(BasicTest.class, eoBTString.getModelClass());
    }

    @Test
    public void givenListString_WithBTString_ok() throws Exception {
        final EO eoBTString = TestObjectProvider.createEOFromJson(List.class, String.class);
        EOTest
                .mapEOValue_ok(eoBTString, BTProvider.createString());

        Assert.assertEquals(S_STRING, eoBTString.get(S0));
    }


    // Given eo BasicTest with values
    // when adding jsn list small
    // no mapping defined for list and object like 'BasicTest'.

    @Test
    public void givenBTSmall_withJsonListSmall_fails() throws Exception {
        final EO eoBTSmall = BTProviderEO.createSmall();
        eoBTSmall
                .add()
                .map(ListProviderJSON.getSmall());
        Assert.assertFalse(INFO_EXPECTED_EXCEPTION, eoBTSmall.getLog().isEmpty());
        TestObjectProvider.checkLogNotEmpty(eoBTSmall);
    }

    /**
     * Given empty eo with BasicTest model
     * when adding jsn map empty
     * original model BasicTest is kept.
     */
    @Test
    public void givenBTEmpty_withJsnMapEmpty_ok() throws Exception {
        final EO eoBTEmpty = BTProviderEO.createEmpty();
        final String jsnMapEmpty = MapProviderJSON.getJSONEmpty();

        eoBTEmpty
                .add()
                .map(jsnMapEmpty);

        Assert.assertEquals(BasicTest.class, eoBTEmpty.getModelClass());
        TestObjectProvider.checkLogEmpty(eoBTEmpty);
    }

    // Given eo BasicTest with no values
    // when adding jsn map small
    // values will be set and original model is kept.
    @Test
    public void givenBTEmpty_withJsnMapSmall_ok() throws Exception {
        final EO eoBTEmpty = BTProviderEO.createEmpty();
        final String jsnMapSmall = MapProviderJSON.readSmall();

        eoBTEmpty
                .add()
                .map(jsnMapSmall);

        Assert.assertEquals(BasicTest.class, eoBTEmpty.getModelClass());
        Assert.assertEquals(Object.class, eoBTEmpty.getModels().getChildModelClass());
        Assert.assertEquals(S_INTEGER, eoBTEmpty.get(F_TEST_INTEGER));
        TestObjectProvider.checkLogEmpty(eoBTEmpty);
    }


    /**
     * Given empty eo with BasicTest model
     * when adding json map with non existing key
     * log will be written and no value set
     */
    @Test
    public void givenBTEmpty_withJsonMapWrongName_fails() throws Exception {
        final EO eoBTEmpty = BTProviderEO.createEmpty();
        final String jsonMapWrongName = MapProviderJSON.toJSONMap(S_KEY0, S_STRING);

        eoBTEmpty
                .add()
                .map(jsonMapWrongName);

        Assert.assertEquals(BasicTest.class, eoBTEmpty.getModelClass());
        Assert.assertEquals(BasicTest.class, eoBTEmpty.get().getClass());
        Assert.assertNull(INFO_NULL_FAILS + S_KEY0 + eoBTEmpty.get(S_KEY0), eoBTEmpty.get(S_KEY0));
        Assert.assertFalse(INFO_LOG_NOT_EMPTY_FAILS + eoBTEmpty.getLog(), eoBTEmpty.getLog().isEmpty());
    }


}


