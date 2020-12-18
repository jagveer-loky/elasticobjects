package org.fluentcodes.projects.elasticobjects;


import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.domain.test.TestProviderAnObjectJson;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.SAMPLE_DOUBLE;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.SAMPLE_FLOAT;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_BOOLEAN;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_INTEGER;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_LEVEL0;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_STRING;

/**
 * @author Werner Diwischek
 * @since 27.10.2018.
 */

public class EoMapObjectMapJsonTest {

    /**
     * Wiki example
     */
    @Test
    public void scopeDev_whenMapTestStringWithPathLevel0_thenValueIsSet() {
        final EO eo = ProviderRootDevScope.createEo();
        final String jsonString = "{\n" +
                "\t\"level0\":{\n" +
                "\t\t\"" + AnObject.MY_STRING + "\":\"value\"\n" +
                "    }\n" +
                "}";
        eo.mapObject(jsonString);
        assertThat(eo.get("level0/" + AnObject.MY_STRING)).isEqualTo("value");
        assertThat(eo.getEo("level0").getModelClass()).isEqualTo(Map.class);
    }

    @Test
    public void scopeDev_Map_first_1_second_2_third_3__mapObject__first_is_1() {
        final EO eo = ProviderRootDevScope.createEo();
        final String mapJson = "{\"first\": 1,\"second\": 2,\"third\": 3}";
        eo.mapObject(mapJson);

        Assertions.assertThat(eo.get("first")).isEqualTo(1);
    }

    @Test
    public void scopeDev_whenEmpty_thenNothingSet()  {
        EO eo = ProviderRootDevScope.createEo();
        eo.mapObject("{}");
        Assert.assertTrue(eo.isEmpty());
    }

    @Test
    public void scopeDev_whenMapStringValue_thenValuesSet()  {
        final EO eoEmpty = ProviderRootDevScope.createEo();
        final String jsnString = TestProviderAnObjectJson.STRING.content();
        eoEmpty
                .mapObject(jsnString);
        Assert.assertEquals(S_STRING, eoEmpty.get(AnObject.MY_STRING));
        Assert.assertEquals(Map.class, eoEmpty.getModelClass());
        Assert.assertEquals(String.class, eoEmpty.getEo(AnObject.MY_STRING).getModelClass());
    }

    @Test
    public void scopeDev_whenMapBooleanValue_thenValuesSet()  {
        final EO eoEmpty = ProviderRootTestScope.createEo();
        final String jsonBoolean = TestProviderAnObjectJson.BOOLEAN.content();
        eoEmpty
                .mapObject(jsonBoolean);
        Assert.assertEquals(S_BOOLEAN, eoEmpty.get(AnObject.MY_BOOLEAN));
        Assert.assertEquals(Map.class, eoEmpty.getModelClass());
        Assert.assertEquals(Boolean.class, eoEmpty.getEo(AnObject.MY_BOOLEAN).getModelClass());
    }

    @Test
    public void scopeDev__int__$()  {
        final EO eo = ProviderRootDevScope.createEo();;
        eo.mapObject("{\"" + S_LEVEL0 + "\":1}");
        assertThat(eo.getLog()).isEmpty();
        assertThat(eo.get(S_LEVEL0)).isEqualTo(1);
    }

    @Test
    public void scopeDev__OneValueEmbedded__$()  {
        EO eo = ProviderRootDevScope.createEo();
        eo.mapObject("{\"test1\":{\"test2\":\"testObject\"}}");
        assertThat(eo.get("test1/test2")).isEqualTo("testObject");
    }

    @Test
        public void scopeDev__2Strings__$()  {
        EO eo = ProviderRootDevScope.createEo();
        eo.mapObject("{\"test1\":\"testObject1\",\"test2\":\"testObject2\"}");
        assertThat(eo.get("test1")).isEqualTo("testObject1");
        assertThat(eo.get("test2")).isEqualTo("testObject2");
    }

    @Test
    public void scopeDev__float__xpected()  {
        final EO eo = ProviderRootDevScope.createEo();;
        eo.mapObject(TestProviderAnObjectJson.FLOAT.content());
        assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
        Assertions.assertThat(eo.get(AnObject.MY_FLOAT)).isEqualTo(SAMPLE_FLOAT);
     }

    @Test
    public void scopeDev__small__xpected()  {
        final EO eo = ProviderRootDevScope.createEo();;
        eo.mapObject(TestProviderAnObjectJson.SMALL.content());
        assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
        Assertions.assertThat(eo.get(AnObject.MY_STRING)).isEqualTo(S_STRING);
        Assertions.assertThat(eo.get(AnObject.MY_INT)).isEqualTo(S_INTEGER);
    }

    @Test
    public void scopeDev__all__$()  {
        final EO eo = ProviderRootDevScope.createEo();;
        eo.mapObject(TestProviderAnObjectJson.ALL.content());
        assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
        Assertions.assertThat(eo.get(AnObject.MY_STRING)).isEqualTo(S_STRING);
        Assertions.assertThat(eo.get(AnObject.MY_INT)).isEqualTo(S_INTEGER);
    }

    @Test
    public void scopeTest__double_typed__$()  {
        EO eo = TestProviderAnObjectJson.DOUBLE_TYPED.createEoTest();
        assertThat(eo.getLog()).isEmpty();
        assertThat(eo.get(AnObject.MY_DOUBLE)).isEqualTo((SAMPLE_DOUBLE));
    }

    @Test
    public void scopeTest__float_typed__$()  {
        EO eo = TestProviderAnObjectJson.FLOAT_TYPED.createEoTest();
        assertThat(eo.getLog()).isEmpty();
        assertThat(eo.get(AnObject.MY_FLOAT)).isEqualTo((SAMPLE_FLOAT));
    }

    @Test
    public void scopeTest__all_typed__$()  {
        EO eo = TestProviderAnObjectJson.ALL_TYPED.createEoTest();
        assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
        Assertions.assertThat(eo.get(AnObject.MY_STRING)).isEqualTo(S_STRING);
        Assertions.assertThat(eo.get(AnObject.MY_INT)).isEqualTo(S_INTEGER);
    }
}
