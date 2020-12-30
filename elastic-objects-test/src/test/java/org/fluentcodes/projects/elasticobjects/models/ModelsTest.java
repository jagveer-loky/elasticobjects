package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by werner.diwischek on 06.01.18.
 */
public class ModelsTest {
    @Test
    public void ModelConfig____isObject_true()  {
        Models models = new Models(ProviderRootTestScope.EO_CONFIGS, ModelConfig.class);
        Assert.assertTrue(models.hasModel());
        Assertions.assertThat(models.isScalar()).isFalse();
        Assertions.assertThat(models.isObject()).isTrue();
    }

    @Test
    public void ArrayList____isList_true() {
        EO eo = ProviderRootDevScope.createEo();
        Models models = new Models(eo.getConfigsCache(), ArrayList.class);
        Assertions.assertThat(models.isCreate()).isFalse();
        Assertions.assertThat(models.isScalar()).isFalse();
        Assertions.assertThat(models.isList()).isTrue();
    }


}
