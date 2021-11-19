package org.fluentcodes.projects.elasticobjects.domain.test;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigMethods;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Assert;
import org.junit.Test;

public class SubExampleClassTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return SubExampleClass.class;
    }

    @Override
    @Test
    public void create_noEoException() {
        assertCreateNoException();
    }

    @Override
    @Test
    public void compareModelConfig() {
        assertModelConfigEqualsPersisted();
    }

    @Override
    @Test
    public void compareBeanFromModelConfig() {
        assertBeanFromModelConfigEqualsPersisted();
    }

}
