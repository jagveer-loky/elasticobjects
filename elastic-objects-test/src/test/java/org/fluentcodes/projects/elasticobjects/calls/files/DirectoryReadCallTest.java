package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.junit.Test;

/**
 * @author Werner Diwischek
 * @since 7.10.2020.
 */
public class DirectoryReadCallTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return DirectoryReadCall.class;
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
