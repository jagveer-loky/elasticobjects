package org.fluentcodes.projects.elasticobjects;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.fileprovider.ProviderMapJsn;
import org.fluentcodes.projects.elasticobjects.fileprovider.ProviderMapJson;
import org.fluentcodes.projects.elasticobjects.fileprovider.TestProviderRootDev;
import org.fluentcodes.projects.elasticobjects.fileprovider.TestProviderRootTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 27.10.2018.
 */

public class JsonToEoMapJsnTest {
    private static final Logger LOG = LogManager.getLogger(JsonToEoMapJsnTest.class);

    @Test
    public void testEmpty()  {
        EO eo = TestProviderRootDev.createEo();
        String valueBeforeCheckLog = new EOToJSON().setSerializationType(JSONSerializationType.EO).toJSON(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        String value = new EOToJSON().setSerializationType(JSONSerializationType.EO).toJSON(eo);
        EO eoFromJson = TestProviderRootTest.createEo();
        eoFromJson.mapObject(value);
        Assertions.assertThat(eo.getLog()).isEmpty();
    }

    @Test
    public void testDouble()  {
        EO eo = ProviderMapJsn.DOUBLE.createMapEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(F_TEST_DOUBLE)).isEqualTo((SAMPLE_DOUBLE));
    }

    @Test
    public void testFloat()  {
        EO eo = ProviderMapJsn.FLOAT.createMapEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(F_TEST_FLOAT)).isEqualTo((SAMPLE_FLOAT));
    }

    @Test
    public void testSmall()  {
        EO eo = ProviderMapJson.SMALL.createMapEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assert.assertEquals(INFO_COMPARE_FAILS, Map.class, eo.getModelClass());
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eo.get(F_TEST_STRING));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_INTEGER, eo.get(F_TEST_INTEGER));
    }

    @Test
    public void testAll()  {
        EO eo = ProviderMapJsn.ALL.createMapEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assert.assertEquals(INFO_COMPARE_FAILS, Map.class, eo.getModelClass());
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eo.get(F_TEST_STRING));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_INTEGER.longValue(), eo.get(F_TEST_INTEGER));
        //AssertEO.compareJSON(eo);
    }
}
