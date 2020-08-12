package org.fluentcodes.projects.elasticobjects.config;


import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.assets.SubTest;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;

import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelInterface;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_NOT_EMPTY_FAILS;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_NOT_NULL_FAILS;
import static org.fluentcodes.projects.elasticobjects.XEO_STATIC.M_XLSX_CALL;
import static org.fluentcodes.projects.elasticobjects.XEO_STATIC.M_XLSX_CONFIG;


/**
 * Created by Werner on 04.11.2016.
 */
public class ModelConfigTest {


    @Test
    public void findConfigInCache()  {
        ModelConfig config = ProviderRootTestScope.EO_CONFIGS.findModel(M_XLSX_CALL);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config.getDescription());
    }

    @Test
    public void readConfigClassPath()  {
        Map<String, Config> map = TestConfig.readClassPathConfig(ModelConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(M_XLSX_CALL));
    }

    @Test
    public void readMapMain()  {
        Map map = TestConfig.readMapFromFile(CONFIG_MODEL_MAIN);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(M_XLSX_CALL));
    }

    @Test
    public void readConfigMain()  {
        Map<String, Config> map = TestConfig.readConfigMapFromFile(CONFIG_MODEL_MAIN, ModelConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(M_XLSX_CALL));
    }


    @Test
    public void checkDependentModels()  {
        
        // Check if basic Models are available
        ModelInterface model = ProviderRootTestScope.EO_CONFIGS
                .findModel(ModelInterface.class.getSimpleName());
        //Assert.assertEquals(ModelInterface.class.getSimpleName(),model.getModelKey());
        model = ProviderRootTestScope.EO_CONFIGS.findModel(FieldConfig.class.getSimpleName());
        Assert.assertEquals(M_FIELD_CONFIG, model.getModelKey());

        model = ProviderRootTestScope.EO_CONFIGS.findModel(BasicTest.class);
        Assert.assertEquals(BasicTest.class.getSimpleName(), model.getModelKey());
        model = ProviderRootTestScope.EO_CONFIGS.findModel(SubTest.class);
        Assert.assertEquals(SubTest.class.getSimpleName(), model.getModelKey());

        model = ProviderRootTestScope.EO_CONFIGS.findModel(HostConfig.class);
        model = ProviderRootTestScope.EO_CONFIGS.findModel(FileConfig.class);
        model = ProviderRootTestScope.EO_CONFIGS.findModel(UserConfig.class);
    }

    @Test
    public void checkModelXlsxConfig()  {
        
        ModelConfig model = ProviderRootTestScope.EO_CONFIGS
                .findModel(XlsxConfig.class);
        Assert.assertEquals(M_XLSX_CONFIG, model.getModelKey());
    }

    @Test
    public void checkModelXlsxAction()  {
        final ModelInterface model = ProviderRootTestScope.EO_CONFIGS
                .findModel(M_XLSX_CALL);
        Assert.assertEquals(M_XLSX_CALL, model.getModelKey());
        model.resolve();
        FieldConfig fieldConfig = model.getFieldConfig(F_LIST_PARAMS);
        Assert.assertNotNull(fieldConfig);
        fieldConfig = model.getFieldConfig(F_LIST_MAPPER);
        Assert.assertNotNull(fieldConfig);
    }

}
