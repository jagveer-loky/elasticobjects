package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.assets.ClassTest;
import org.fluentcodes.projects.elasticobjects.assets.SubClassForTest;
import org.fluentcodes.projects.elasticobjects.assets.SubTest;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.fileprovider.ProviderConfig;
import org.fluentcodes.projects.elasticobjects.fileprovider.ProviderRootTest;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Date;
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
    public void givenModel_whenCreate_thenThrowsException()  {
        ConfigChecks.findModelAndCreateInstanceExceptionThrown(ModelConfig.class);
    }

    @Test
    public void givenConfigEntries_whenResolve_thenNoErrors()  {
        ConfigChecks.resolveConfigs(ModelConfig.class);
    }


    @Test
    public void testClassTestFromClassPath_Found()  {
        EOConfigsCache cache = ProviderRootTest.EO_CONFIGS;
        ModelInterface model = cache.findModel(ClassTest.class);
        for (String key: model.getFieldNames()) {
            Assert.assertNotNull(model.getFieldConfig(key));
        }
        ClassTest instance = (ClassTest)model.create();
        Assert.assertNotNull(instance);
        model.set("id", instance, 1L);
        Assert.assertEquals(1L, model.get("id", instance));
    }

    @Test
    public void testSubClassForTestFromClassPath_Found()  {
        EOConfigsCache cache = ProviderRootTest.EO_CONFIGS;
        ModelInterface model = cache.findModel(SubClassForTest.class);
        for (String key: model.getFieldNames()) {
            Assert.assertNotNull(model.getFieldConfig(key));
        }
        SubClassForTest instance = (SubClassForTest)model.create();
        Assert.assertNotNull(instance);
        model.set("value", instance, "value");
        Assert.assertEquals("value", model.get("value", instance));
    }

    @Test
    public void testReadConfig()  {
        EOConfigMapModels map = new EOConfigMapModels(ProviderRootTest.EO_CONFIGS);
        Assert.assertTrue(map.hasKey(CONFIG_MODEL));
    }

    @Test
    public void findCachedUnknown_fails()  {
        try {
            ProviderRootTest.EO_CONFIGS.findModel(SAMPLE_KEY_UNKNOW);
            Assert.fail(INFO_EXPECTED_EXCEPTION_FAILS);
        } catch (EoException e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }



    @Test
    public void findCachedST()  {
        ModelInterface model = ProviderRootTest.EO_CONFIGS.findModel(SubTest.class);
        Assert.assertEquals(SubTest.class, model.getModelClass());
    }

    @Test
    public void modelTest()  {
        ModelInterface model = ProviderRootTest.EO_CONFIGS.findModel(ModelInterface.class);
        Assert.assertEquals(ModelInterface.class.getSimpleName(), model.getModelKey());
        Assert.assertEquals(INFO_COMPARE_FAILS + model.getModelClass().getSimpleName(),
                ModelInterface.class, model.getModelClass());

        Assert.assertEquals(INFO_COMPARE_FAILS + M_CONFIG_IMPL + CON_SPACE + model.getSuperKey(),
                M_CONFIG, model.getSuperKey());

        Assert.assertNotNull(INFO_NOT_NULL_FAILS + model.getSuperKey(),
                model.getSuperModel());

        Assert.assertNull(INFO_NULL_FAILS + model.getDefaultImplementation(),
                model.getDefaultImplementation());

        Assert.assertNotNull(INFO_NOT_NULL_FAILS,
                model.getShapeType());

        Assert.assertTrue(INFO_CONDITION_TRUE_FAILS + model.isObject(),
                model.isObject());

        Assert.assertEquals(INFO_COMPARE_FAILS + model.getPackagePath(),
                PACK_CONFIG, model.getPackagePath());

        Assert.assertEquals(INFO_COMPARE_FAILS + model.getModule(),
                ELASTIC_OBJECTS, model.getModule());

        Assert.assertEquals(INFO_COMPARE_FAILS + model.getSubModule(),
                MAIN, model.getSubModule());

        Assert.assertEquals(INFO_COMPARE_FAILS + model.getPackageGroup(),
                PCK_CONFIG, model.getPackageGroup());

        Assert.assertEquals(INFO_COMPARE_FAILS + model.getAuthor(),
                AUTHOR0, model.getAuthor());

        Assert.assertFalse(INFO_EMPTY_FAILS + Model.DESCRIPTION + CON_SPACE + model.getDescription(), model.getDescription().isEmpty());

        Assert.assertNull(INFO_NULL_FAILS + Model.CREATION_DATE,
                model.getCreationDate());

        Assert.assertEquals(INFO_COMPARE_FAILS + F_FIELD_KEYS + CON_SPACE + model.getFieldKeys().size(),
                19, model.getFieldKeys().size());

        Assert.assertEquals(INFO_COMPARE_FAILS + Model.DESCRIPTION + CON_SPACE + model.getFieldConfig(Model.DESCRIPTION).getModelClass().getSimpleName(),
                String.class, model.getFieldConfig(Model.DESCRIPTION).getModelClass());

        //TODO
        //Assert.assertTrue(INFO_COMPARE_FAILS + F_DESCRIPTION + CON_SPACE + model.find(F_DESCRIPTION, model),
        //        ((String)model.find(F_DESCRIPTION, model)).isEmpty());
        //http://blog.codeleak.pl/2013/07/3-ways-of-handling-exceptions-in-junit.html
        try {
            model.set(Model.DESCRIPTION, model, S_STRING);
            Assert.fail(INFO_EXPECTED_EXCEPTION_FAILS);
        } catch (Exception e) {
            Assert.assertEquals(EoException.class, e.getClass());
        }

        Assert.assertEquals(INFO_COMPARE_FAILS + model.getFieldConfig(Model.CREATION_DATE).getModelClass().getSimpleName(),
                Date.class, model.getFieldConfig(Model.CREATION_DATE).getModelClass());

    }

    @Test
    public void checkDependentModels()  {
        // Check if basic Models are available
        ModelInterface model = ProviderRootTest.EO_CONFIGS.findModel(M_BASIC_TEST);
        Assert.assertEquals(M_BASIC_TEST, model.getModelKey());
        model = ProviderRootTest.EO_CONFIGS.findModel(M_SUB_TEST);
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
        ModelInterface config = (ModelInterface) build.invoke(childObject, ProviderRootTest.EO_CONFIGS, MODEL_CONFIG_MAP);
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
        EOConfigsCache cache = ProviderRootTest.EO_CONFIGS;
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
