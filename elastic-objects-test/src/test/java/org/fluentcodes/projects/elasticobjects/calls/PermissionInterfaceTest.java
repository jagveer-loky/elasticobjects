package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigNoCreateTests;
import org.junit.Test;

/**
 * Created by Werner on 3.8.2017.
 */
public class PermissionInterfaceTest implements IModelConfigNoCreateTests {
    @Override
    public Class<?> getModelConfigClass() {
        return PermissionInterface.class;
    }

    @Override
    @Test
    public void create_throwsEoException() {
        assertCreateThrowingException();
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
