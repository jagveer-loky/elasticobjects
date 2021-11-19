package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.domain.BaseBean;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by werner.diwischek on 06.01.18.
 */
public class ConfigBeanTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return ConfigBean.class;
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

    @Test
    public void merge_mapWithProperties() {
        Map<String, Object> testMap = new HashMap<>();
        Map<String, String> properties = new HashMap<>();
        properties.put("Test", "Test");
        testMap.put("properties", properties);
        ConfigBean bean = new ConfigBean("test", testMap);
        Assert.assertEquals("Test", bean.getProperties().get("Test"));
    }

}
