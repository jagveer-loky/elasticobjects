package org.fluentcodes.projects.elasticobjects.assets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_LEVEL0;

/**
 * Created by Werner on 04.11.2016.
 */
public class EoMapSetBtTest {
    private static final Logger LOG = LogManager.getLogger(EoMapSetBtTest.class);
    @Test
    public void givenTest_whenSetBTOnExistingModelMap_thenModelIsMap()  {
        final EO eo = ProviderRootTestScope.createEo();
        eo.setEmpty(S_LEVEL0);
        eo.set(new BasicTest(), S_LEVEL0);
        Assertions.assertThat(eo.getEo(S_LEVEL0).getModelClass()).isEqualTo(Map.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
    }
}
