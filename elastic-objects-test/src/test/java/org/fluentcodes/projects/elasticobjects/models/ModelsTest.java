package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.M_MODEL_INTERFACE;

/**
 * Created by werner.diwischek on 06.01.18.
 */
public class ModelsTest {
    @Test
    public void withModelConfigInterface()  {
        Models models = new Models(ProviderRootTestScope.EO_CONFIGS, ModelConfig.class.getSimpleName());
        Assert.assertTrue(models.hasModel());
        Assert.assertFalse(models.hasChildModel());
        Assert.assertFalse(models.isEmpty());
    }

    @Test
    public void createArrayList() {
        EO eo = ProviderRootDevScope.createEo();
        Models models = new Models(eo.getConfigsCache(), ArrayList.class);
        Assertions.assertThat(models.isCreate()).isTrue();
        Assertions.assertThat(models.isScalar()).isFalse();
    }


}
