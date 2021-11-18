package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigNoCreateTests;
import org.junit.Test;

/**
 * Created by Werner on 17.11.2021.
 */
public class FieldConfigTest implements IModelConfigNoCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return FieldConfig.class;
    }

    @Test
    public void create_throwsEoException()  {
        assertCreateThrowingException();
    }

    @Test
    public void compareModelConfig()  {
        assertModelConfigEqualsPersisted();
    }

    @Test
    public void compareBeanFromModelConfig()  {
        assertBeanFromModelConfigEqualsPersisted();
    }
}
