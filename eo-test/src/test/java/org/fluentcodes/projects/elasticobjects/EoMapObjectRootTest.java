package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.assets.ProviderMapJson;
import org.fluentcodes.projects.elasticobjects.fileprovider.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 1.10.2018
 */

public class EoMapObjectRootTest {
    private static final Logger LOG = LogManager.getLogger(EoMapObjectRootTest.class);

    @Test
    public void givenString_withInteger_ok()  {
        final EO eo = TestProviderRootDev.createEo("");
        eo.mapObject(S_INTEGER);
        Assertions.assertThat(eo.get()).isEqualTo(S_INTEGER.toString());
    }

    @Test
    public void givenString_withBoolean_ok()  {
        final EO eo = TestProviderRootDev.createEo("");
        eo.mapObject(S_BOOLEAN);
        Assertions.assertThat(eo.get()).isEqualTo(S_BOOLEAN.toString());
    }

    @Test
    public void givenString_withString_ok()  {
        final EO eo = TestProviderRootDev.createEo("");
        eo.mapObject(S_STRING_OTHER);
        Assertions.assertThat(eo.get()).isEqualTo(S_STRING_OTHER);
    }

    @Test
    public void givenString_withMap_fails()  {
        final EO eo = TestProviderRootDev.createEo(S_STRING);
        eo.mapObject(new LinkedHashMap());
        Assertions.assertThat(eo.getLog()).isNotEmpty();
        Assertions.assertThat(eo.get()).isEqualTo(S_STRING);
    }

    @Test
    public void givenString_withBT_hasLog()  {
        final EO eo = TestProviderRootTest.createEo(S_STRING);
        eo.mapObject(new BasicTest());
        Assertions.assertThat(eo.getLog()).contains("Tried to map scalar child");
        Assertions.assertThat(eo.get()).isEqualTo(S_STRING);
    }

    @Test
    public void givenMap_withString_fails()  {
        final EO eo = TestProviderRootDev.createEo();
        eo.mapObject(S_STRING);
        Assertions.assertThat(eo.getLog()).isNotEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
    }

    @Test
    public void givenMap_withNull_ok_nothingChanged()  {
        final EO eo = TestProviderRootDev.createEo();
        eo.mapObject(null);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
    }

    @Test
    public void givenMapEmpty_withMapString_ok()  {
        final EO eo = TestProviderRootDev.createEo();
        eo.mapObject(TestProviderMapJson.STRING.createMap());
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
        Assertions.assertThat(eo.get(F_TEST_STRING)).isEqualTo(S_STRING);
    }

    @Test
    public void givenBTString_withMapInteger_ok()  {
        final EO eo = TestProviderMapJson.STRING.createBtEo();
        eo.mapObject(TestProviderMapJson.INT.createMap());
        Assertions.assertThat(eo.getModelClass()).isEqualTo(BasicTest.class);
        Assertions.assertThat(eo.get(F_TEST_STRING)).isEqualTo(S_STRING);
        Assertions.assertThat(eo.get(F_TEST_INTEGER)).isEqualTo(S_INTEGER);
    }

    /*@Test
    public void givenBTString_withJson_ok()  {
        final EO eo = BTTestObjectProvider.STRING.getBTEo();
        eo.mapObject(MapProviderJSON.toJSONMap(F_TEST_STRING, S_STRING_OTHER));
        Assertions.assertThat(eo.getModelClass()).isEqualTo(BasicTest.class);
        Assertions.assertThat(eo.get(F_TEST_STRING)).isEqualTo(S_STRING_OTHER);
    }*/

    /**
     * Given eo empty with no Model
     * when adding jsn map string
     * value will be added with the correct type.
     */
    @Test
    public void givenMapString_withJsonMapString_ok()  {
        final EO eoEmpty = TestProviderRootTest.createEo();
        final String jsnString = TestProviderMapJson.STRING.content();
        eoEmpty
                .mapObject(jsnString);

        Assert.assertEquals(S_STRING, eoEmpty.get(F_TEST_STRING));
        Assert.assertEquals(Map.class, eoEmpty.getModelClass());
        Assert.assertEquals(String.class, eoEmpty.getEo(F_TEST_STRING).getModelClass());
    }

    /**
     * Given eo empty with no Model
     * when adding jsn map boolean
     * value will be added with the correct type.
     */
    @Test
    public void givenMapEmpty_withJsonMapBoolean_ok()  {
        final EO eoEmpty = TestProviderRootTest.createEo();
        final String jsonBoolean = ProviderMapJson.BOOLEAN.content();
        eoEmpty
                .mapObject(jsonBoolean);
        Assert.assertEquals(S_BOOLEAN, eoEmpty.get(F_TEST_BOOLEAN));
        Assert.assertEquals(Map.class, eoEmpty.getModelClass());
        Assert.assertEquals(Boolean.class, eoEmpty.getEo(F_TEST_BOOLEAN).getModelClass());
    }


    @Test
    public void givenListEmpty_withBoolean_fails()  {
        final EO root = TestProviderRootDev.createEoWithClasses(List.class);
        root
                .mapObject(S_BOOLEAN);
        Assert.assertEquals(List.class, root.getModelClass());
        Assert.assertFalse(INFO_LOG_NOT_EMPTY_FAILS, root.getLog().isEmpty());
    }

    @Test
    public void givenListEmpty_withBTBoolean_ok()  {
        final EO root = TestProviderRootDev.createEoWithClasses(List.class);
        root.mapObject(ProviderMapJson.BOOLEAN.content());
        Assert.assertEquals(1, root.keysEo().size());
        Assert.assertEquals(1, ((EoChild) root).keysValue().size());
        Assert.assertEquals(S_BOOLEAN, root.get(S0));
    }

    @Test
    public void givenListStringEmpty_withBTBoolean_ok()  {
        EO root = TestProviderRootDev.createEoWithClasses(List.class, String.class);
        root
                .mapObject(ProviderMapJson.BOOLEAN.content());
        Assert.assertEquals(1, root.keysEo().size());
        Assert.assertEquals(1, ((EoChild) root).keysValue().size());
        Assert.assertEquals(S_BOOLEAN.toString(), root.get(S0));
    }

    /*@Test
    public void givenListStringEmpty_withSTString_ok()  {
        final EO eoLListString = TestEOProvider.createWithClasses(List.class, String.class);
        EOTestHelper
                .mapEOValue_ok(eoLListString, STProvider.createString());
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eoLListString.get(S0));
    }*/

    /**
     * Given eo empty with no Model
     * when adding json map string
     * value will be added with the correct type.
     */
    /*
    @Test
    public void givenMapEmpty_withJsonString_ok()  {
        final EO eoEmpty = TestEOProvider.create();
        final String jsonString = MapProviderJSON.readString();

        eoEmpty
                .mapObject(jsonString);

        Assert.assertEquals(S_STRING, eoEmpty.get(F_TEST_STRING));
        Assert.assertEquals(Map.class, eoEmpty.getModelClass());
        Assert.assertEquals(String.class, eoEmpty.getEo(F_TEST_STRING).getModelClass());
    }*/

    /**
     * Given eo empty with no Model
     * when adding json map boolean
     * value will be added with the correct type.
     */
    /*@Test
    public void givenMapEmpty_withJsonBoolean_ok()  {
        final EO eoEmpty = TestEOProvider.create();
        final String jsonBoolean = MapProviderJSON.readBoolean();

        eoEmpty
                .mapObject(jsonBoolean);

        Assert.assertEquals(S_BOOLEAN, eoEmpty.get(F_TEST_BOOLEAN));
        Assert.assertEquals(Map.class, eoEmpty.getModelClass());
        Assert.assertEquals(Boolean.class, eoEmpty.getEo(F_TEST_BOOLEAN).getModelClass());
    }*/

    /**
     * Given eo empty with no Model
     * when adding json list small
     * the eo map will be transformed to a list but type will be used.
     */
/*
    @Test
    public void givenMapEmpty_withListJsonSmall_ok()  {
        final EO eoEmpty = MapProviderEO.createEmpty();
        final String jsonListSmall = ListProviderJSON.readSmall();
        eoEmpty
                .mapObject(jsonListSmall);

        Assert.assertEquals(List.class, eoEmpty.getModelClass());
        Assert.assertEquals(ArrayList.class, eoEmpty.get().getClass());
        Assert.assertEquals(S_STRING, eoEmpty.get(S0));
        Assert.assertEquals(new Long(S_INTEGER), eoEmpty.get(S1));
    }
*/
    /**
     * Given eo empty with no Model
     * when adding jsn list small
     * the eo map will be transformed to a list but type will be used.
     */
  /*  @Test
    public void givenMapEmpty_withJsnListSmall_ok()  {
        final EO eoEmpty = MapProviderEO.createEmpty();
        final String jsnListSmall = ListProviderJSN.readSmall();
        eoEmpty
                .mapObject(jsnListSmall);
        Assert.assertEquals(List.class, eoEmpty.getModelClass());
        Assert.assertEquals(ArrayList.class, eoEmpty.get().getClass());
        Assert.assertEquals(S_STRING, eoEmpty.get(S0));
        Assert.assertEquals(S_INTEGER, eoEmpty.get(S1));
    }
*/
    /**
     * Given eo with values
     * when adding json list small
     * the eo map should be used and its position used as key.
     */
  /*  @Test
    public void givenMapSmall_withJsonListSmall_ok()  {
        final EO eoSmall = MapProviderEO.createSmall();
        final String jsonListSmall = ListProviderJSON.readSmall();

        eoSmall
                .mapObject(jsonListSmall);

        Assert.assertEquals(Map.class, eoSmall.getModelClass());
        Assert.assertEquals(LinkedHashMap.class, eoSmall.get().getClass());
        Assert.assertEquals(S_STRING, eoSmall.get(F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, eoSmall.get(F_TEST_INTEGER));
        Assert.assertEquals(S_STRING, eoSmall.get(S0));
        Assert.assertEquals(new Long(S_INTEGER), eoSmall.get(S1));
    }*/

    /**
     * Given eo with values
     * when adding jsn list small
     * the eo map should be used and add integer position as key.
     */
/*
    @Test
    public void givenMapSmall_withJsnListSmall2_fails()  {
        final EO eoSmall = MapProviderEO.createSmall();
        final String jsnListSmall = ListProviderJSON.readSmall();
        eoSmall
                .mapObject(jsnListSmall);
        Assert.assertEquals(Map.class, eoSmall.getModelClass());
        Assert.assertEquals(LinkedHashMap.class, eoSmall.get().getClass());
        Assert.assertFalse(INFO_LOG_EMPTY_FAILS + eoSmall.getLog(), eoSmall.getLog().isEmpty());
        Assert.assertEquals(S_STRING, eoSmall.get(F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, eoSmall.get(F_TEST_INTEGER));
        Assert.assertEquals(S_STRING, eoSmall.get(S0));
        Assert.assertEquals(new Long(S_INTEGER), eoSmall.get(S1));
    }
*/
    /**
     * Given empty eo with List model
     * when adding jsn map empty
     * original model ListModel is kept.
     */
  /*  @Test
    public void givenListEmpty_withJsnEmpty_ok()  {
        final EO listEO = ListProviderEO.createEmpty();
        final String jsnListEmpty = ListProviderJSON.readEmpty();
        listEO
                .mapObject(jsnListEmpty);
        Assert.assertEquals(List.class, listEO.getModelClass());
        Assert.assertEquals(Object.class, listEO.getModels().getChildModelClass());
        //TODO checkConfig if necessary Assert.assertTrue(params.isFromJSON());
    }
*/
    /**
     * Given empty eo with ListModel model
     * when adding json map small
     * original model ListModel is kept and values mapped to List.
     */

  /*  @Test
    public void givenListEmpty_withJsnMapSmall_ok()  {
        final EO listEO = ListProviderEO.createEmpty();
        final String jsnMapSmall = ListProviderJSN.readSmall();
        listEO
                .mapObject(jsnMapSmall);
        Assert.assertEquals(S_STRING, listEO.get(S0));
        Assert.assertEquals(S_INTEGER, listEO.get(S1));
        Assert.assertEquals(List.class, listEO.getModelClass());
        Assert.assertEquals(String.class, listEO.getEo(S0).getModelClass());
    }
*/
    /*@Test
    public void givenBTString_withMapInteger_ok()  {
        final EO eoBTString = BTProviderEO.createString();
        EOTestHelper
                .mapEOValue_ok(eoBTString, MapProvider.createInteger());
        Assert.assertEquals(S_STRING, eoBTString.get(F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, eoBTString.get(F_TEST_INTEGER));
    }

    @Test
    public void givenBTWithString_withBTWithInteger_ok()  {
        final EO eoBTString = BTProviderEO.createString();

        EOTestHelper
                .mapEOValue_ok(eoBTString, BTProvider.createInteger());
        Assert.assertEquals(S_STRING, eoBTString.get(F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, eoBTString.get(F_TEST_INTEGER));
    }

    @Test
    public void givenBTWithString_withMapWithInteger_ok()  {
        final EO eoBTString = BTProviderEO.createString();
        Assert.assertEquals(S_STRING, eoBTString.get(F_TEST_STRING));

        EOTestHelper
                .mapEOValue_ok(eoBTString, MapProvider.createInteger());
        Assert.assertEquals(S_STRING, eoBTString.get(F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, eoBTString.get(F_TEST_INTEGER));
    }

    /*@Test
    public void givenBTWithString_withString_fails()  {
        final EO eoBTString = BTProviderEO.createString();
        EOTestHelper
                .mapEO_fails(eoBTString, S_STRING_OTHER);
        Assert.assertEquals(S_STRING, eoBTString.get(F_TEST_STRING));
    }*/

    /*@Test
    public void givenBTString_withMap_ok()  {
        final EO eoBTString = BTProviderEO.createString();
        EOTestHelper
                .mapEOValue_ok(eoBTString, new HashMap());
        Assert.assertEquals(S_STRING, eoBTString.get(F_TEST_STRING));
    }

    @Test
    public void givenBTString_WithBTInteger_ok()  {
        final EO eoBTString = BTProviderEO.createString();
        EOTestHelper
                .mapEOValue_ok(eoBTString, BTProvider.createInteger());

        Assert.assertEquals(S_STRING, eoBTString.get(F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, eoBTString.get(F_TEST_INTEGER));
    }

    @Test
    public void givenBTEmpty_withJSONMap_ok()  {
        final EO eoBTEmpty = BTProviderEO.createEmpty();
        final String json = MapProviderJSON.toJSONMap(F_TEST_STRING, S_STRING);
        EOTestHelper
                .mapEOValue_ok(eoBTEmpty, json, BasicTest.class);
        Assert.assertEquals(S_STRING, eoBTEmpty.get(F_TEST_STRING));
        Assert.assertEquals(BasicTest.class, eoBTEmpty.getModelClass());
    }

    @Test
    public void givenBTString_withJSONMap_ok()  {
        final EO eoBTString = BTProviderEO.createString();
        final String json = MapProviderJSON.toJSONMap(F_TEST_STRING, S_STRING_OTHER);
        EOTestHelper
                .mapEOValue_ok(eoBTString, json, BasicTest.class);
        Assert.assertEquals(S_STRING_OTHER, eoBTString.get(F_TEST_STRING));
        Assert.assertEquals(BasicTest.class, eoBTString.getModelClass());
    }

    @Test
    public void givenListString_WithBTString_ok()  {
        final EO eoBTString = TestEOProvider.createWithClasses(List.class, String.class);
        EOTestHelper
                .mapEOValue_ok(eoBTString, BTProvider.createString());

        Assert.assertEquals(S_STRING, eoBTString.get(S0));
    }


    // Given eo BasicTest with values
    // when adding jsn list small
    // no mapping defined for list and object like 'BasicTest'.

    @Test
    public void givenBTSmall_withJsonListSmall_fails()  {
        final EO eoBTSmall = BTProviderEO.createSmall();
        eoBTSmall
                .mapObject(ListProviderJSON.getSmall());
        Assert.assertFalse(INFO_EXPECTED_EXCEPTION, eoBTSmall.getLog().isEmpty());
        TestObjectProvider.checkLogNotEmpty(eoBTSmall);
    }

    /**
     * Given empty eo with BasicTest model
     * when adding jsn map empty
     * original model BasicTest is kept.
     */
    /*@Test
    public void givenBTEmpty_withJsnMapEmpty_ok()  {
        final EO eoBTEmpty = BTProviderEO.createEmpty();
        final String jsnMapEmpty = MapProviderJSON.getJSONEmpty();

        eoBTEmpty
                .mapObject(jsnMapEmpty);

        Assert.assertEquals(BasicTest.class, eoBTEmpty.getModelClass());
        TestObjectProvider.checkLogEmpty(eoBTEmpty);
    }*/

    // Given eo BasicTest with no values
    // when adding jsn map small
    // values will be set and original model is kept.
    /*@Test
    public void givenBTEmpty_withJsnMapSmall_ok()  {
        final EO eoBTEmpty = BTProviderEO.createEmpty();
        final String jsnMapSmall = MapProviderJSON.readSmall();

        eoBTEmpty
                .mapObject(jsnMapSmall);

        Assert.assertEquals(BasicTest.class, eoBTEmpty.getModelClass());
        Assert.assertEquals(Object.class, eoBTEmpty.getModels().getChildModelClass());
        Assert.assertEquals(S_INTEGER, eoBTEmpty.get(F_TEST_INTEGER));
        TestObjectProvider.checkLogEmpty(eoBTEmpty);
    }*/


    /**
     * Given empty eo with BasicTest model
     * when adding json map with non existing key
     * log will be written and no value set
     */
    /*@Test
    public void givenBTEmpty_withJsonMapWrongName_fails()  {
        final EO eoBTEmpty = BTProviderEO.createEmpty();
        final String jsonMapWrongName = MapProviderJSON.toJSONMap(S_KEY0, S_STRING);

        eoBTEmpty
                .mapObject(jsonMapWrongName);

        Assert.assertEquals(BasicTest.class, eoBTEmpty.getModelClass());
        Assert.assertEquals(BasicTest.class, eoBTEmpty.get().getClass());
        Assert.assertNull(INFO_NULL_FAILS + S_KEY0 + eoBTEmpty.get(S_KEY0), eoBTEmpty.get(S_KEY0));
        Assert.assertFalse(INFO_LOG_NOT_EMPTY_FAILS + eoBTEmpty.getLog(), eoBTEmpty.getLog().isEmpty());
    }*/


}


