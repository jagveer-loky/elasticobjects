package org.fluentcodes.projects.elasticobjects.calls.xlsx;

import org.fluentcodes.projects.elasticobjects.calls.files.XlsxConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigNoCreateTests;
import org.junit.Test;

/**
 * Created by Werner on 04.11.2016.
 */
public class XlsxConfigTest implements IModelConfigNoCreateTests {
    private static final String LIST_SIMPLE_XLSX = "ListSimple.xlsx:test";

    @Override
    public Class<?> getModelConfigClass() {
        return XlsxConfig.class;
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
