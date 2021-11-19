package org.fluentcodes.projects.elasticobjects.domain.test;

import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.junit.Test;

public class ASubObjectTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return ASubObject.class;
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
