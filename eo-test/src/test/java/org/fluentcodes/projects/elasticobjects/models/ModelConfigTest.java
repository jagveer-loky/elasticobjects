package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 04.11.2016.
 */
public class ModelConfigTest {
    private static final Logger LOG = LogManager.getLogger(ModelConfigTest.class);
    private static final Map<String, Object> MODEL_CONFIG_MAP = ProviderConfig.createModelConfigMap();
    private static final String F_BUILD = "build";
    private static final String M_BUILDER = "$Builder";

    @Test
    public void createByModelConfig_throwsException()  {
        ConfigModelChecks.createThrowsException(ModelConfig.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(ModelConfig.class);
    }

    @Ignore
    @Test
    public void givenConfigEntries_whenResolve_thenNoErrors()  {
        ConfigChecks.resolveConfigs(ModelConfig.class);
    }

    @Ignore
    @Test
    public void resolveConfigurations()  {
        ConfigChecks.resolveConfigurations(ModelConfig.class);
    }

    @Ignore
    @Test
    public void compareConfigurations()  {
        ConfigChecks.compareConfigurations(ModelConfig.class);
    }

    @Test
    public void givenTest_whenFindModelNotInChache_thenExceptionThrown()  {
        try {
            ProviderRootTestScope.EO_CONFIGS.findModel(SAMPLE_KEY_UNKNOW);
            Assert.fail(INFO_EXPECTED_EXCEPTION_FAILS);
        } catch (EoException e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void checkDependentModels()  {
        // Check if basic Models are available
        ModelInterface model = ProviderRootTestScope.EO_CONFIGS.findModel(M_BASIC_TEST);
        Assert.assertEquals(M_BASIC_TEST, model.getModelKey());
        model = ProviderRootTestScope.EO_CONFIGS.findModel(M_SUB_TEST);
        Assert.assertEquals(M_SUB_TEST, model.getModelKey());
    }

    @Test
    public void callStaticMethods() throws Exception {
        Class parentClass = ModelConfig.class;
        Class childClass = Class.forName(parentClass.getName() + M_BUILDER);

        Method[] methods = childClass.getMethods();
        Method build = childClass.getMethod(F_BUILD, new Class[]{EOConfigsCache.class, Map.class});
        Constructor constructor = childClass.getConstructor(null);
        Object childObject = constructor.newInstance();
        ModelInterface config = (ModelInterface) build.invoke(childObject, ProviderRootTestScope.EO_CONFIGS, MODEL_CONFIG_MAP);
        Assert.assertEquals(F_MODEL_KEY, config.getModelKey());
        Assert.assertEquals(Model.AUTHOR, config.getAuthor());
        Assert.assertEquals(F_PACKAGE_GROUP, config.getPackageGroup());
        Assert.assertEquals(F_PACKAGE_PATH, config.getPackagePath());
        Assert.assertEquals(F_INTERFACES, config.getInterfaces());
        Assert.assertEquals(F_MODULE, config.getModule());
        Assert.assertEquals(F_SUB_MODULE, config.getSubModule());
    }

    @Test
    public void checkConfig() {
        EOConfigsCache cache = ProviderRootTestScope.EO_CONFIGS;
        TreeSet<String> keys = new TreeSet<>(cache.getConfigKeys(ModelConfig.class));
        int counter = 0;
        final Set<String> allFields = new TreeSet<>();
        for (String key: keys) {
            counter++;
            LOG.info("Check " + counter + ": " + key);
            ModelConfig model = cache.findModel(key);
            Assertions.assertThat(model).isNotNull();
            try {
                model.resolve();
            }
            catch (EoException e) {
                LOG.info(e.getMessage());
            }
            if (model.getFieldKeys()!=null) {
                allFields.addAll(model.getFieldKeys());
            }
        }
        counter = 0;
        for (String key: allFields) {
            counter++;
            //LOG.info("Check field " + counter + ": " + key);
            try {
                FieldConfig fieldConfig = cache.findField(key);
                Assertions.assertThat(fieldConfig).isNotNull();
            }
            catch (EoException e) {
                LOG.info(e.getMessage());
            }
        }
        keys = new TreeSet<>(cache.getConfigKeys(FieldConfig.class));
        counter = 0;
        for (String fieldKey: keys) {
            counter++;

            if (!allFields.contains(fieldKey)) {
                LOG.info("Not found in models " + fieldKey);
            }
        }
    }
}
