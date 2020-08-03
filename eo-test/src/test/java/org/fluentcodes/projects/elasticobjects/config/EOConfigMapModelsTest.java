package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.fileprovider.ProviderRootDev;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.F_TEST_FLOAT;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.SAMPLE_FLOAT;

public class EOConfigMapModelsTest {
    @Test
    public void devFindClassBTQualified()  {
        final EOConfigsCache configsCache = ProviderRootDev.EO_CONFIGS;
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
        final EOConfigsCache configsCache = ProviderRootDev.EO_CONFIGS;
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

}
