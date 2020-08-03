package org.fluentcodes.projects.elasticobjects;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.fileprovider.*;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 27.10.2018.
 */

public class JsonToEoListJsonTest {
    private static final Logger LOG = LogManager.getLogger(JsonToEoListJsonTest.class);

    @Test
    public void testEmpty()  {
        EO eo = ProviderRootDev.createEo("[]");
        Assert.assertTrue(eo.isEmpty());
        Assert.assertEquals(List.class, eo.getModelClass());
    }

    @Test
    public void testString()  {
        EO eo = ProviderRootDev.createEo("[\"testObject\"]");
        Assertions.assertThat(eo.get("0")).isEqualTo("testObject");
    }

    @Test
    public void testStringEmbedded()  {
        EO eo = ProviderRootDev.createEo("[[\"testObject\"]]");
        Assertions.assertThat(eo.get("0/0")).isEqualTo("testObject");
    }

    @Test
    public void testInteger()  {
        EO eo = ProviderRootDev.createEo("[1]");
        Assertions.assertThat(eo.get("0")).isEqualTo(1);
    }

    @Test
    public void testTwoValues()  {
        EO eo = ProviderRootDev.createEo("[\"testObject\",1]");
        Assertions.assertThat(eo.get("0")).isEqualTo("testObject");
        Assertions.assertThat(eo.get("1")).isEqualTo(1);
    }

    @Test
    public void testFromFile()  {
        EO eo = ProviderListJson.EMPTY.createEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(List.class);
    }

    @Test
    public void testFloat()  {
        EO eo = ProviderListJson.FLOAT.createEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(S0)).isEqualTo(SAMPLE_FLOAT);
    }

    @Test
    public void testSmall()  {
        EO eo = ProviderListJson.SMALL.createEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(S0)).isEqualTo(S_STRING);
        Assertions.assertThat(eo.get(S1)).isEqualTo(S_INTEGER);
    }

    @Test
    public void testAll()  {
        EO eo = ProviderListJson.ALL.createEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(S0)).isEqualTo(S_STRING);
        Assertions.assertThat(eo.get(S1)).isEqualTo(S_INTEGER);
    }

    @Test
    public void checkListModel_Calls()  {
        final String toParse = "{\n" +
                "\"(List)_calls\":\n" +
                "  {\n" +
                "    \"0\": {\"execute\": \"testCall()\"}\n" +
                "  }\n" +
                "}";
        EO eo = ProviderRootTest.createEo(toParse);
        String log = eo.getLog();
        Assert.assertFalse(eo.getLog().isEmpty());
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
        EO eo = ProviderRootTest.createEo(toParse);
        Assert.assertEquals(ArrayList.class, eo.get("noCalls").getClass());
        Assert.assertEquals("test",eo.get("noCalls/0"));
    }
}
