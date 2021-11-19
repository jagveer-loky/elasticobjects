package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigNoCreateTests;
import org.junit.Test;

/**
 * Created by Werner on 17.11.2021.
 */
public class CallImplTest implements IModelConfigNoCreateTests {
    @Override
    public Class<?> getModelConfigClass() {
        return CallImpl.class;
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
