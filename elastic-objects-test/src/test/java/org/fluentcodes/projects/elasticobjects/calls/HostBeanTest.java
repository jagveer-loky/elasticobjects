package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.junit.Test;

/**
 * Created by Werner on 11.10.2016.
 */
public class HostBeanTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return HostBean.class;
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
