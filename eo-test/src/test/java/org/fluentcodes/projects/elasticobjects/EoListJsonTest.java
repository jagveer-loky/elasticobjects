package org.fluentcodes.projects.elasticobjects;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.assets.TestProviderBtJson;
import org.fluentcodes.projects.elasticobjects.testitemprovider.*;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 27.10.2018.
 */

public class EoListJsonTest {
    private static final Logger LOG = LogManager.getLogger(EoListJsonTest.class);

    @Test
    public void testEmpty()  {
        EO eo = ProviderRootDevScope.createEo("[]");
        Assert.assertTrue(eo.isEmpty());
        Assert.assertEquals(List.class, eo.getModelClass());
    }

    @Test
    public void testString()  {
        EO eo = ProviderRootDevScope.createEo("[\"testObject\"]");
        Assertions.assertThat(eo.get("0")).isEqualTo("testObject");
    }

    @Test
    public void testStringEmbedded()  {
        EO eo = ProviderRootDevScope.createEo("[[\"testObject\"]]");
        Assertions.assertThat(eo.get("0/0")).isEqualTo("testObject");
    }

    @Test
    public void testInteger()  {
        EO eo = ProviderRootDevScope.createEo("[1]");
        Assertions.assertThat(eo.get("0")).isEqualTo(1);
    }

    @Test
    public void testTwoValues()  {
        EO eo = ProviderRootDevScope.createEo("[\"testObject\",1]");
        Assertions.assertThat(eo.get("0")).isEqualTo("testObject");
        Assertions.assertThat(eo.get("1")).isEqualTo(1);
    }

    @Test
    public void testFromFile()  {
        EO eo = ProviderListJson.EMPTY.createEoDev();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(List.class);
    }

    @Test
    public void testFloat()  {
        EO eo = ProviderListJson.FLOAT.createEoDev();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(S0)).isEqualTo(SAMPLE_FLOAT);
    }

    @Test
    public void testSmall()  {
        EO eo = ProviderListJson.SMALL.createEoDev();
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
        EO eo = ProviderRootTestScope.createEo(toParse);
        Assert.assertEquals(ArrayList.class, eo.get("noCalls").getClass());
        Assert.assertEquals("test",eo.get("noCalls/0"));
    }

    @Test
    public void givenListStringEmpty_whenMapBTBoolean_thenFirstListElementSetWithString()  {
        EO root = ProviderRootDevScope.createEo(List.class, String.class);
        root
                .mapObject(TestProviderBtJson.BOOLEAN.content());
        Assertions.assertThat(root.getLog()).isEmpty();
        Assertions.assertThat(root.get(S0)).isEqualTo(S_BOOLEAN.toString());
    }

    @Test
    public void givenListEmpty_whenMapBTBoolean_thenFirstListElementSetWithBoolean()  {
        final EO root = ProviderRootDevScope.createEo(List.class);
        root.mapObject(TestProviderBtJson.BOOLEAN.content());
        Assertions.assertThat(root.getLog()).isEmpty();
        Assertions.assertThat(root.get(S0)).isEqualTo(S_BOOLEAN);
    }
}
