package org.fluentcodes.projects.elasticobjects.calls.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.TestBuilderEOProvider;
import org.fluentcodes.projects.elasticobjects.EO;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Werner on 04.11.2016.
 */
public class ValueModelsJavaTest {
    private static final Logger LOG = LogManager.getLogger(ValueModelsJavaTest.class);
/*
    @Test
    public void readModelConfigMain()  {
        EO modelValues = ValueModelsJava.getModelParams(TestBuilderEOProvider.STATIC, "BuilderTest");
        Assert.assertNotNull(modelValues);
        Assert.assertFalse(modelValues.isEmpty());

        modelValues = ValueModelsJava.getModelParams(TestBuilderEOProvider.STATIC, "BuilderTestWithAnnotations");
        Assert.assertNotNull(modelValues);
        Assert.assertFalse(modelValues.isEmpty());
    }

 */
}
