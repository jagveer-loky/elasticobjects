package org.fluentcodes.projects.elasticobjects.config;


import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.assets.SubTest;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
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
public class ModelConfigTest extends TestHelper {


    @Test
    public void findConfigInCache() throws Exception {
        ModelConfig config = TestEOProvider.EO_CONFIGS.findModel(M_XLSX_CALL);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config.getDescription());
    }

    @Test
    public void readConfigClassPath() throws Exception {
        Map<String, Config> map = TestConfig.readClassPathConfig(ModelConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(M_XLSX_CALL));
    }

    @Test
    public void readMapMain() throws Exception {
        Map map = TestConfig.readMapFromFile(CONFIG_MODEL_MAIN);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(M_XLSX_CALL));
    }

    @Test
    public void readConfigMain() throws Exception {
        Map<String, Config> map = TestConfig.readConfigMapFromFile(CONFIG_MODEL_MAIN, ModelConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(M_XLSX_CALL));
    }


    @Test
    public void checkDependentModels() throws Exception {
        TestHelper.printStartMethod();
        // Check if basic Models are available
        ModelInterface model = TestEOProvider.EO_CONFIGS
                .findModel(ModelInterface.class.getSimpleName());
        //Assert.assertEquals(ModelInterface.class.getSimpleName(),model.getModelKey());
        model = TestEOProvider.EO_CONFIGS.findModel(FieldConfig.class.getSimpleName());
        Assert.assertEquals(M_FIELD_CONFIG, model.getModelKey());

        model = TestEOProvider.EO_CONFIGS.findModel(BasicTest.class);
        Assert.assertEquals(BasicTest.class.getSimpleName(), model.getModelKey());
        model = TestEOProvider.EO_CONFIGS.findModel(SubTest.class);
        Assert.assertEquals(SubTest.class.getSimpleName(), model.getModelKey());

        model = TestEOProvider.EO_CONFIGS.findModel(HostConfig.class);
        model = TestEOProvider.EO_CONFIGS.findModel(FileConfig.class);
        model = TestEOProvider.EO_CONFIGS.findModel(UserConfig.class);
    }

    @Test
    public void checkModelXlsxConfig() throws Exception {
        TestHelper.printStartMethod();
        ModelConfig model = TestEOProvider.EO_CONFIGS
                .findModel(XlsxConfig.class);
        Assert.assertEquals(M_XLSX_CONFIG, model.getModelKey());
    }

    @Test
    public void checkModelXlsxAction() throws Exception {
        final ModelInterface model = TestEOProvider.EO_CONFIGS
                .findModel(M_XLSX_CALL);
        Assert.assertEquals(M_XLSX_CALL, model.getModelKey());
        model.resolve();
        FieldConfig fieldConfig = model.getField(F_LIST_PARAMS);
        Assert.assertNotNull(fieldConfig);
        fieldConfig = model.getField(F_LIST_MAPPER);
        Assert.assertNotNull(fieldConfig);
    }

}
