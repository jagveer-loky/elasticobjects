package org.fluentcodes.projects.elasticobjects;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.assets.TestProviderBtJson;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 27.10.2018.
 */

public class EoMapObjectMapJsonTest {
    private static final Logger LOG = LogManager.getLogger(EoMapObjectMapJsonTest.class);

    @Test
    public void givenDev_whenEmpty_thenNothingSet()  {
        EO eo = ProviderRootDevScope.createEo();
        eo.mapObject("{}");
        Assert.assertTrue(eo.isEmpty());
    }

    @Test
    public void givenDev_whenMapStringValue_thenValuesSet()  {
        final EO eoEmpty = ProviderRootDevScope.createEo();
        final String jsnString = TestProviderBtJson.STRING.content();
        eoEmpty
                .mapObject(jsnString);
        Assert.assertEquals(S_STRING, eoEmpty.get(BasicTest.TEST_STRING));
        Assert.assertEquals(Map.class, eoEmpty.getModelClass());
        Assert.assertEquals(String.class, eoEmpty.getEo(BasicTest.TEST_STRING).getModelClass());
    }

    @Test
    public void givenDev_whenMapBooleanValue_thenValuesSet()  {
        final EO eoEmpty = ProviderRootTestScope.createEo();
        final String jsonBoolean = TestProviderBtJson.BOOLEAN.content();
        eoEmpty
                .mapObject(jsonBoolean);
        Assert.assertEquals(S_BOOLEAN, eoEmpty.get(BasicTest.TEST_BOOLEAN));
        Assert.assertEquals(Map.class, eoEmpty.getModelClass());
        Assert.assertEquals(Boolean.class, eoEmpty.getEo(BasicTest.TEST_BOOLEAN).getModelClass());
    }

    @Test
    public void givenDev_whenJsonMap_thenValueSet()  {
        final EO eo = ProviderRootDevScope.createEo();;
        eo.mapObject("{\"" + S_LEVEL0 + "\":1}");
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(S_LEVEL0)).isEqualTo(1);
    }

    @Test
    public void givenDev_whenOneValueEmbedded_thenValuesSet()  {
        EO eo = ProviderRootDevScope.createEo();
        eo.mapObject("{\"test1\":{\"test2\":\"testObject\"}}");
        Assertions.assertThat(eo.get("test1/test2")).isEqualTo("testObject");
    }

    @Test
        public void givenDev_whenTwoValues_thenValuesSet()  {
        EO eo = ProviderRootDevScope.createEo();
        eo.mapObject("{\"test1\":\"testObject1\",\"test2\":\"testObject2\"}");
        Assertions.assertThat(eo.get("test1")).isEqualTo("testObject1");
        Assertions.assertThat(eo.get("test2")).isEqualTo("testObject2");
    }

    @Test
    public void givenDev_whenFileFloat_thenValuesSet()  {
        final EO eo = ProviderRootDevScope.createEo();;
        eo.mapObject(TestProviderBtJson.FLOAT.content());
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assert.assertEquals(INFO_COMPARE_FAILS, Map.class, eo.getModelClass());
        Assert.assertEquals(INFO_COMPARE_FAILS, SAMPLE_FLOAT, eo.get(BasicTest.TEST_FLOAT));
    }

    @Test
    public void givenDev_whenSmall_thenValuesSet()  {
        final EO eo = ProviderRootDevScope.createEo();;
        eo.mapObject(TestProviderBtJson.SMALL.content());
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assert.assertEquals(INFO_COMPARE_FAILS, Map.class, eo.getModelClass());
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eo.get(BasicTest.TEST_STRING));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_INTEGER, eo.get(BasicTest.TEST_INTEGER));
    }

    @Test
    public void givenDev_whenFileAll_thenValuesSet()  {
        final EO eo = ProviderRootDevScope.createEo();;
        eo.mapObject(TestProviderBtJson.ALL.content());
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assert.assertEquals(INFO_COMPARE_FAILS, Map.class, eo.getModelClass());
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eo.get(BasicTest.TEST_STRING));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_INTEGER, eo.get(BasicTest.TEST_INTEGER));
        //AssertEO.compareJSON(eo);
    }

    @Test
    public void testDouble()  {
        EO eo = TestProviderBtJson.DOUBLE_TYPED.createEoTest();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(BasicTest.TEST_DOUBLE)).isEqualTo((SAMPLE_DOUBLE));
    }

    @Test
    public void givenJsonFloatTypes_thenSetValue()  {
        EO eo = TestProviderBtJson.FLOAT_TYPED.createEoTest();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(BasicTest.TEST_FLOAT)).isEqualTo((SAMPLE_FLOAT));
    }

    @Test
    public void testAll()  {
        EO eo = TestProviderBtJson.ALL_TYPED.createEoTest();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assert.assertEquals(INFO_COMPARE_FAILS, Map.class, eo.getModelClass());
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eo.get(BasicTest.TEST_STRING));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_INTEGER, eo.get(BasicTest.TEST_INTEGER));
        //AssertEO.compareJSON(eo);
    }
}
