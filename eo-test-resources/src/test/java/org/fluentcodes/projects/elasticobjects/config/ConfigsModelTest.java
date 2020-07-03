package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.test.DevObjectProvider;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.F_TEST_FLOAT;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.SAMPLE_FLOAT;

public class ConfigsModelTest {
    @Test
    public void devFindClassBTQualified()  {
        final EOConfigsCache configsCache = DevObjectProvider.EO_CONFIGS;
        final ModelConfig modelBT = configsCache.findModel("org.fluentcodes.projects.elasticobjects.assets.BasicTest");
        Assert.assertNotNull(modelBT);
        final FieldConfig fieldFloat = configsCache.findField("BasicTest.testFloat");
        Assert.assertNotNull(fieldFloat);
        fieldFloat.resolve();
        final ModelConfig modelFloat = configsCache.findModel("Float");
        Assert.assertNotNull(modelFloat);
        modelFloat.resolve();
        try {
            BasicTest test = (BasicTest) modelBT.create();
            Assert.assertNotNull(test);
            modelBT.set(F_TEST_FLOAT, test, SAMPLE_FLOAT);

            Assert.assertEquals(SAMPLE_FLOAT, test.getTestFloat());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void devFindClassBT()  {
        final EOConfigsCache configsCache = DevObjectProvider.EO_CONFIGS;
        final ModelConfig modelBT = configsCache.findModel("BasicTest");
        Assert.assertNotNull(modelBT);
        final FieldConfig fieldFloat = configsCache.findField("BasicTest.testFloat");
        Assert.assertNotNull(fieldFloat);
        fieldFloat.resolve();
        final ModelConfig modelFloat = configsCache.findModel("Float");
        Assert.assertNotNull(modelFloat);
        modelFloat.resolve();
        try {
            BasicTest test = (BasicTest) modelBT.create();
            Assert.assertNotNull(test);
            modelBT.set(F_TEST_FLOAT, test, SAMPLE_FLOAT);
            Assert.assertEquals(SAMPLE_FLOAT, test.getTestFloat());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkModelCalls()  {
        final EOConfigsCache configsCache = TestEOProvider.EO_CONFIGS;
        Set<String> mapCalls = configsCache.getCallSet();
    }

}
