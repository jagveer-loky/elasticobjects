package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.fluentcodes.projects.elasticobjects.domain.BaseBean;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by werner.diwischek on 06.01.18.
 */
public class ConfigBeanTest {
    @Test
    public void createByModelConfig() {
        ModelConfigChecks.create(ConfigBean.class);
    }

    @Test
    public void compareModelConfig() {
        ModelConfigChecks.compare(ConfigBean.class);
    }

    @Test
    public void TEST__findModel__fieldKeys_size_5() {
        ModelConfig modelConfig = ProviderRootTestScope.EO_CONFIGS.findModel(ConfigBean.class);
        Assert.assertEquals(ModelConfigObject.class, modelConfig.getClass());
        Assert.assertEquals(ConfigBean.class, modelConfig.getModelClass());
        Assert.assertEquals(11, modelConfig.getFieldMap().size());
        Assert.assertEquals(11, modelConfig.getFieldKeys().size());
        BaseBean modelImpl = (BaseBean) modelConfig.create();
        Assert.assertNotNull(modelImpl);
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
