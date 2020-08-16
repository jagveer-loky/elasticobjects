package org.fluentcodes.projects.elasticobjects.assets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 1.10.2018
 */

public class EoMapObjectBtTest {
    private static final Logger LOG = LogManager.getLogger(EoMapObjectBtTest.class);

    @Test
    public void givenDevString_whenMapBT_thenHasLog()  {
        final EO eo = ProviderRootTestScope.createEo(S_STRING);
        eo.mapObject(new BasicTest());
        Assertions.assertThat(eo.getLog()).contains("Could not map container to scalar");
        Assertions.assertThat(eo.get()).isEqualTo(S_STRING);
    }

    @Test
    public void givenBTString_whenMapJsonInt_thenValuesSet()  {
        final EO eo = TestProviderBtJson.STRING.createBtEo();
        eo.mapObject(TestProviderBtJson.INT.createMapTest());
        Assertions.assertThat(eo.getModelClass()).isEqualTo(BasicTest.class);
        Assertions.assertThat(eo.get(BasicTest.TEST_STRING)).isEqualTo(S_STRING);
        Assertions.assertThat(eo.get(BasicTest.TEST_INTEGER)).isEqualTo(S_INTEGER);
    }

}


