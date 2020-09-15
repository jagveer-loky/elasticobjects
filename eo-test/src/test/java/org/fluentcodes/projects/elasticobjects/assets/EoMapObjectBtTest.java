package org.fluentcodes.projects.elasticobjects.assets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_INTEGER;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_STRING;

/**
 * @author Werner Diwischek
 * @since 1.10.2018
 */

public class EoMapObjectBtTest {
    private static final Logger LOG = LogManager.getLogger(EoMapObjectBtTest.class);

    /**
     * Basic Wiki Example
    */
    @Test
    public void givenTest_whenMapBT_thenObjectIsMapType()  {
        final EO eo = ProviderRootTestScope.createEo();
        BasicTest bt = new BasicTest()
                .setTestString("value");
        eo.mapObject(bt);
        assertThat(eo.get("testString")).isEqualTo("value");
        assertThat(eo.getModelClass()).isEqualTo(Map.class);
    }


    @Test
    public void givenTestString_whenMapBT_thenHasLog()  {
        final EO eo = ProviderRootTestScope.createEo(S_STRING);
        eo.mapObject(new BasicTest());
        assertThat(eo.getLog()).contains("Could not map container to scalar");
        assertThat(eo.get()).isEqualTo(S_STRING);
    }

    @Test
    public void givenBTString_whenMapJsonInt_thenValuesSet()  {
        final EO eo = TestProviderBtJson.STRING.createBtEo();
        eo.mapObject(TestProviderBtJson.INT.createMapTest());
        assertThat(eo.getModelClass()).isEqualTo(BasicTest.class);
        assertThat(eo.get(BasicTest.TEST_STRING)).isEqualTo(S_STRING);
        assertThat(eo.get(BasicTest.TEST_INTEGER)).isEqualTo(S_INTEGER);
    }

}


