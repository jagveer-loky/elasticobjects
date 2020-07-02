package org.fluentcodes.projects.elasticobjects.executor.statics;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.config.ModelConfigMap;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

public class ValuesEOTest {

    public static final Logger LOG = LogManager.getLogger(ValuesEOTest.class);

    @Test
    public void getConfigurationKeys_withNoName_returnConfigTypes() throws Exception {
        List<String> names = ValuesEO.getConfigurationKeys(new Object[]{TestEOProvider.create()});
        Assert.assertNotNull(names);
        Assert.assertFalse(names.isEmpty());
    }

    @Test
    public void getConfigurationKeys_withModelConfig_returnModelConfigNames() throws Exception {
        List<String> names = ValuesEO.getConfigurationKeys(new Object[]{TestEOProvider.create(), M_MODEL_CONFIG});
        Assert.assertNotNull(names);
        Assert.assertFalse(names.isEmpty());
    }

    @Test
    public void getConfigurationKeys_withModelConfig_andConfigFilterMap_returnMapTypes() throws Exception {
        List<String> names = ValuesEO.getConfigurationKeys(new Object[]{TestEOProvider.create(), M_MODEL_CONFIG, ".*Map"});
        Assert.assertNotNull(names);
        Assert.assertFalse(names.isEmpty());
        Assert.assertEquals(M_HASH_MAP, names.get(0));
    }

    @Test
    public void getConfigurationKeys_withFieldConfig_returnFieldConfigNames() throws Exception {
        List<String> names = ValuesEO.getConfigurationKeys(new Object[]{TestEOProvider.create(), M_FIELD_CONFIG});
        Assert.assertNotNull(names);
        Assert.assertFalse(names.isEmpty());
    }

    @Test
    public void getConfigurationKeys_withValueCall_returnConfigNames() {
        try {
            List<String> names = ValuesEO.getConfigurationKeys(new Object[]{TestEOProvider.create(), M_VALUE_CALL});
            Assert.fail("Should throw an Exception");
        }
        catch (Exception e) {
            LOG.info("Expected Exception " + e.getMessage());
        }
    }

    @Test
    public void getConfigurationKeys_withNull_throwsException() {
        try {
            List<String> names = ValuesEO.getConfigurationKeys(null);
            Assert.fail("Should throw an Exception");
        }
        catch (Exception e) {
            LOG.info("Expected Exception " + e.getMessage());
        }
    }

    @Test
    public void getConfigurationKeys_withNullValueArray_throwsException() {
        try {
            List<String> names = ValuesEO.getConfigurationKeys(new Object[]{null});
            Assert.fail("Should throw an Exception");
        }
        catch (Exception e) {
            LOG.info("Expected Exception " + e.getMessage());
        }
    }

    @Test
    public void getConfigurationKeys_withFirstParamNoEO_throwsException() {
        try {
            List<String> names = ValuesEO.getConfigurationKeys(new Object[]{"test"});
            Assert.fail("Should throw an Exception");
        }
        catch (Exception e) {
            LOG.info("Expected Exception " + e.getMessage());
        }
    }

    @Test
    public void getConfiguration_withConfigKeyHashMap() throws Exception {
        Object modelConfig = ValuesEO.getConfiguration(new Object[]{TestEOProvider.create(), M_MODEL_CONFIG, M_HASH_MAP});
        Assert.assertEquals(ModelConfigMap.class, modelConfig.getClass());
        Assert.assertEquals(M_HASH_MAP, ((ModelConfigMap)modelConfig).getModelKey());
    }

    @Test
    public void getConfigurationList_withConfigFilterMap() throws Exception {
        List modelConfig = ValuesEO.getConfigurationList(new Object[]{TestEOProvider.create(), M_MODEL_CONFIG, ".*Map"});
        Assert.assertEquals(ArrayList.class, modelConfig.getClass());
        Assert.assertEquals(M_HASH_MAP, ((ModelConfigMap)modelConfig.get(0)).getModelKey());
    }


    @Test
    public void getConfigurationList_withFirstParamNoEO_throwsException() {
        try {
            Object names = ValuesEO.getConfigurationList(new Object[]{"test"});
            Assert.fail("Should throw an Exception");
        }
        catch (Exception e) {
            LOG.info("Expected Exception " + e.getMessage());
        }
    }
}
