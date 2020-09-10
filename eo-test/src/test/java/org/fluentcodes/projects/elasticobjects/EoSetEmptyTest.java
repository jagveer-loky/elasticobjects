package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;

import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;


public class EoSetEmptyTest {
    private static final Logger LOG = LogManager.getLogger(EoSetEmptyTest.class);

    @Test
    public void givenDev_whenSetEmptyWithLevel0_thenIsMapClass()  {
        final EO eo = ProviderRootDevScope.createEo();
        eo.setEmpty(S_LEVEL0);
        Assertions.assertThat(eo.getEo(S_LEVEL0).getModelClass()).isEqualTo(Map.class);
    }


    @Test
    public void dev__set_empty_Long_path1_and_Path2_partly_overlapping__BothObjectsRemain()  {
        final EO eo = ProviderRootTestScope.createEo();
        eo.setEmpty(S_LEVEL0, S_LEVEL1, S_LEVEL2,  S_LEVEL3);
        eo.setEmpty(S_LEVEL0, S_LEVEL1, S_LEVEL4,  S_LEVEL5);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getEo(S_LEVEL0, S_LEVEL1, S_LEVEL2,  S_LEVEL3)).isNotNull();
        Assertions.assertThat(eo.getEo(S_LEVEL0, S_LEVEL1, S_LEVEL4,  S_LEVEL5)).isNotNull();
    }
}