package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDev;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTest;

import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;


public class EoSetEmptyTest {
    private static final Logger LOG = LogManager.getLogger(EoSetEmptyTest.class);

    @Test
    public void level0_isMap()  {
        final EO eo = ProviderRootDev.createEo();
        eo.setEmpty(S_LEVEL0);
        Assertions.assertThat(eo.getEo(S_LEVEL0).getModelClass()).isEqualTo(Map.class);
    }


    @Test
    public void longPath1AndLongPath2PartlyOverlapping_bothObjectsRemain()  {
        final EO eo = ProviderRootTest.createEo();
        eo.setEmpty(S_LEVEL0, S_LEVEL1, S_LEVEL2,  S_LEVEL3);
        eo.setEmpty(S_LEVEL0, S_LEVEL1, S_LEVEL4,  S_LEVEL5);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getEo(S_LEVEL0, S_LEVEL1, S_LEVEL2,  S_LEVEL3)).isNotNull();
        Assertions.assertThat(eo.getEo(S_LEVEL0, S_LEVEL1, S_LEVEL4,  S_LEVEL5)).isNotNull();
    }
}