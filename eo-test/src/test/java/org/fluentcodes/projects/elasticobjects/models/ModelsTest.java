package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDev;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTest;
import org.fluentcodes.projects.elasticobjects.paths.PathElement;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.M_MODEL_INTERFACE;

/**
 * Created by werner.diwischek on 06.01.18.
 */
public class ModelsTest {
    @Test
    public void withModelInterface()  {
        Models models = new Models(ProviderRootTest.EO_CONFIGS, M_MODEL_INTERFACE);
        Assert.assertTrue(models.hasModel());
        Assert.assertFalse(models.hasChildModel());
        Assert.assertFalse(models.isEmpty());
    }
    @Test
    public void createChildModelsWithList() {
        EO eo = ProviderRootDev.createEo();
        Models models = new Models(eo.getConfigsCache(), Map.class);
        Models childModels = models.createChildModels(eo, new PathElement("(ArrayList)list"), null);
        Assertions.assertThat(childModels.getModelClass()).isEqualTo(ArrayList.class);
    }

    @Test
    public void createArrayList() {
        EO eo = ProviderRootDev.createEo();
        Models models = new Models(eo.getConfigsCache(), ArrayList.class);
        Assertions.assertThat(models.isCreate()).isTrue();
        Assertions.assertThat(models.isScalar()).isFalse();
    }

    @Test
    public void givenProviderTestWithMap_whenCreateChildWithJsonSerializationType_thenStringType() {
        EO eo = ProviderRootTest.createEo();
        Models models = new Models(eo.getConfigsCache(), Map.class);
        Models childModels = models.createChildModelsSimple(eo, new PathElement("test"), JSONSerializationType.STANDARD);
        Assertions.assertThat(childModels.getModelClass()).isEqualTo(String.class);
    }

}
