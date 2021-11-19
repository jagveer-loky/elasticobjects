package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigNoCreateTests;
import org.junit.Test;

/**
 * Created by Werner on 3.8.2020.
 */
public class CallTest implements IModelConfigNoCreateTests {
    @Override
    public Class<?> getModelConfigClass() {
        return Call.class;
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
