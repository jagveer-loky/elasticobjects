package org.fluentcodes.projects.elasticobjects;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.domain.test.TestProviderAnObjectJson;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderListJson;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.fluentcodes.tools.xpect.XpectEo;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 27.10.2018.
 */

public class EoMapObjectListJsonTest {

    @Test
    public void givenDev_whenEmpty_thenNothingSet()  {
        EO eo = ProviderRootDevScope.createEo(List.class);
        eo.mapObject("[]");
        Assert.assertTrue(eo.isEoEmpty());
        Assert.assertEquals(List.class, eo.getModelClass());
    }

    @Test
    public void givenDev_whenListString_thenSet()  {
        EO eo = ProviderRootDevScope.createEo(List.class);
        eo.mapObject("[\"testObject\"]");
        Assertions.assertThat(eo.get("0")).isEqualTo("testObject");
    }

    @Test
    public void givenDev_whenListListString_thenSet()  {
        EO eo = ProviderRootDevScope.createEo(List.class);
        eo.mapObject("[[\"testObject\"]]");
        Assertions.assertThat(eo.get("0/0")).isEqualTo("testObject");
    }

    @Test
    public void givenDev_whenListInteger_thenSet()  {
        EO eo = ProviderRootDevScope.createEo(List.class);
        eo.mapObject("[1]");
        Assertions.assertThat(eo.get("0")).isEqualTo(1);
    }

    @Test
    public void givenDev_whenListTwoValues_thenSet()  {
        EO eo = ProviderRootDevScope.createEo(List.class);
        eo.mapObject("[\"testObject\",1]");
        Assertions.assertThat(eo.get("0")).isEqualTo("testObject");
        Assertions.assertThat(eo.get("1")).isEqualTo(1);
    }

    @Test
    public void givenDev_whenFileFloat_thenSet()  {
        EO eo = ProviderRootDevScope.createEo(List.class);
        eo.mapObject(ProviderListJson.FLOAT.content());
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(S0)).isEqualTo(SAMPLE_FLOAT);
    }

    @Test
    public void givenDev_whenFileSmall_thenSet()  {
        EO eo = ProviderRootDevScope.createEo(List.class);
        eo.mapObject(ProviderListJson.SMALL.content());
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(S0)).isEqualTo(S_STRING);
        Assertions.assertThat(eo.get(S1)).isEqualTo(S_INTEGER);
    }

    @Test
    public void checkListModelNoCalls()  {
        final String toParse = "{\n" +
                "\"(List)noCalls\":\n" +
                "  {\n" +
                "    \"0\": \"test\",\n" +
                "    \"1\": 1\n" +
                "  }\n" +
                "}";
        EO eo = ProviderRootDevScope.createEo(toParse);
        Assert.assertEquals(ArrayList.class, eo.get("noCalls").getClass());
        Assert.assertEquals("test",eo.get("noCalls/0"));
    }

    @Test
    public void givenDevListStringEmpty_whenMapAnObjectBoolean_thenFirstListElementSetWithString()  {
        EO root = ProviderRootDevScope.createEo(List.class, String.class);
        root
                .mapObject("{\"" + AnObject.MY_BOOLEAN + "\": true}");
        Assertions.assertThat(root.getLog()).isEmpty();
        Assertions.assertThat(root.get(S0)).isEqualTo(S_BOOLEAN.toString());
    }

    @Test
    public void givenDevList_whenMapJsonBoolean_thenFirstListElementSetWithBoolean()  {
        final EO root = ProviderRootDevScope.createEo(List.class);
        root.mapObject("{\"" + AnObject.MY_BOOLEAN + "\": true}");
        Assertions.assertThat(root.getLog()).isEmpty();
        Assertions.assertThat(root.get(S0)).isEqualTo(S_BOOLEAN);
    }

    @Test
    public void givenDev_whenMapJsonListWithRootModel_thenLogEmptyAndModelInteger()  {
        final EO root = ProviderRootDevScope.createEo();
        root
                .mapObject("{\"_rootmodel\": \"List\",\"test\": 1}");
        Assertions.assertThat(root.getLog())
                .isEmpty();
        Assertions.assertThat(root.getModelClass())
                .isEqualTo(List.class);
        Assertions.assertThat(root.get("0"))
                .isEqualTo(1);
    }

    @Test
    public void givenDev_whenMapJsonList1_thenLogEmptyAndModelInteger()  {
        final EO root = ProviderRootDevScope.createEo();
        root
                .mapObject("[1]");
        Assertions.assertThat(root.getLog()).isEmpty();
        Assertions.assertThat(root.getEo("0").getModelClass()).isEqualTo(Integer.class);
        Assertions.assertThat(root.get("0")).isEqualTo(1);
    }

    @Test
    public void givenDev_whenListDouble_thenXpected()  {
        final EO eo = EoRoot.OF(ProviderRootDevScope.EO_CONFIGS);
        eo.mapObject("{\"(List,Double)items\":{\"0\":1,\"1\":2}}");
        Assertions.assertThat(eo.getEo("items").getModelClass()).isEqualTo(List.class);
        new XpectEo<>().compareAsString(eo);
    }
}
