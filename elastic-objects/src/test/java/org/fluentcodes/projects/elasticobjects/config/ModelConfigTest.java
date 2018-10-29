package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.assets.SubTest;
import org.fluentcodes.projects.elasticobjects.eo.EOBuilder;
import org.fluentcodes.projects.elasticobjects.eo.LoggingObjectsImpl;
import org.fluentcodes.projects.elasticobjects.test.MapProvider;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 04.11.2016.
 */
public class ModelConfigTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(ModelConfigTest.class);
    private static final Map<String, Object> MODEL_CONFIG_MAP = MapProvider.createModelConfigMap();
    private static final String F_BUILD = "build";
    private static final String M_BUILDER = "$Builder";

    @Test
    public void readModelConfigMain() throws Exception {
        Map<String, Config> configMap = TestConfig.readConfigMapFromFile(CONFIG_MODEL_MAIN, ModelConfig.class);
        Assert.assertTrue(configMap.containsKey(CONFIG_MODEL));
    }

    @Test
    public void findCachedUnknown_fails() throws Exception {
        try {
            TestObjectProvider.EO_CONFIGS_CACHE.findModel(M_SUB_TEST + SAMPLE_KEY_UNKNOW);
            Assert.fail(INFO_EXPECTED_EXCEPTION_FAILS);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void findCachedString() throws Exception {
        ModelInterface model = TestObjectProvider.EO_CONFIGS_CACHE.findModel(M_STRING);
        Assert.assertEquals(String.class, model.getModelClass());
    }

    @Test
    public void findCachedST() throws Exception {
        ModelInterface model = TestObjectProvider.EO_CONFIGS_CACHE.findModel(SubTest.class.getSimpleName());
        Assert.assertEquals(SubTest.class, model.getModelClass());
    }

    @Test
    public void modelTest() throws Exception {
        TestHelper.printStartMethod();
        ModelInterface model = TestObjectProvider.EO_CONFIGS_CACHE.findModel(ModelInterface.class);
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

        Assert.assertFalse(INFO_EMPTY_FAILS + F_DESCRIPTION + CON_SPACE + model.getDescription(), model.getDescription().isEmpty());

        Assert.assertNull(INFO_NULL_FAILS + F_CREATION_DATE,
                model.getCreationDate());

        Assert.assertEquals(INFO_COMPARE_FAILS + F_FIELD_KEYS + CON_SPACE + model.getFieldKeys().size(),
                19, model.getFieldKeys().size());

        Assert.assertEquals(INFO_COMPARE_FAILS + F_DESCRIPTION + CON_SPACE + model.getField(F_DESCRIPTION).getModelClass().getSimpleName(),
                String.class, model.getField(F_DESCRIPTION).getModelClass());

        //TODO
        //Assert.assertTrue(INFO_COMPARE_FAILS + F_DESCRIPTION + CON_SPACE + model.find(F_DESCRIPTION, model),
        //        ((String)model.find(F_DESCRIPTION, model)).isEmpty());
        //http://blog.codeleak.pl/2013/07/3-ways-of-handling-exceptions-in-junit.html
        try {
            model.set(F_DESCRIPTION, model, S_STRING);
            Assert.fail(INFO_EXPECTED_EXCEPTION_FAILS);
        } catch (Exception e) {
            Assert.assertEquals(Exception.class, e.getClass());
        }

        Assert.assertEquals(INFO_COMPARE_FAILS + model.getField(F_CREATION_DATE).getModelClass().getSimpleName(),
                Date.class, model.getField(F_CREATION_DATE).getModelClass());

    }

    @Test
    public void assertLoggingObjectImpl() throws Exception {
        TestHelper.printStartMethod();
        ModelInterface model = TestObjectProvider.EO_CONFIGS_CACHE.findModel(LoggingObjectsImpl.class);
        Assert.assertEquals(ShapeTypes.INSTANCE, model.getShapeType());
        Assert.assertTrue(model.hasModel());
        Assert.assertFalse(model.isMap());
        Assert.assertFalse(model.isSet());
        Assert.assertFalse(model.isList());
        Assert.assertFalse(model.isScalar());
        Assert.assertTrue(model.isObject());

        LoggingObjectsImpl object = (LoggingObjectsImpl) model.create();
        model.set(F_LOG, object, S_STRING);
        Assert.assertEquals(S_STRING, model.get(F_LOG, object));
        Assert.assertEquals(S_STRING, object.getLog());
    }


    @Test
    public void assertAdapterBuilder() throws Exception {
        ModelInterface model = TestObjectProvider.EO_CONFIGS_CACHE.findModel(EOBuilder.class);
        Assert.assertEquals(ShapeTypes.ADAPTER, model.getShapeType());
        Assert.assertTrue(model.hasModel());
        Assert.assertTrue(model.isObject());
        Assert.assertFalse(model.isScalar());
    }

    @Test
    public void checkDependentModels() throws Exception {
        // Check if basic Models are available
        ModelInterface model = TestObjectProvider.EO_CONFIGS_CACHE.findModel(M_BASIC_TEST);
        Assert.assertEquals(M_BASIC_TEST, model.getModelKey());
        model = TestObjectProvider.EO_CONFIGS_CACHE.findModel(M_SUB_TEST);
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
        ModelInterface config = (ModelInterface) build.invoke(childObject, TestObjectProvider.EO_CONFIGS_CACHE, MODEL_CONFIG_MAP);
        Assert.assertEquals(F_MODEL_KEY, config.getModelKey());
        Assert.assertEquals(F_AUTHOR, config.getAuthor());
        Assert.assertEquals(F_PACKAGE_GROUP, config.getPackageGroup());
        Assert.assertEquals(F_PACKAGE_PATH, config.getPackagePath());
        Assert.assertEquals(F_INTERFACES, config.getInterfaces());
        Assert.assertEquals(F_MODULE, config.getModule());
        Assert.assertEquals(F_SUB_MODULE, config.getSubModule());
    }
}
