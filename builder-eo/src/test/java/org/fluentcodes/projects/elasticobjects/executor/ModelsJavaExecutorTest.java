package org.fluentcodes.projects.elasticobjects.calls.executor;

import org.fluentcodes.projects.elasticobjects.TestBuilderEOProvider;
import org.fluentcodes.projects.elasticobjects.elasticobjects.EO;

import org.junit.Assert;
import org.junit.Test;

public class ModelsJavaExecutorTest {

    @Test
    public void createInstanceVars()  {
        EO modelValues = ValueModelsJava.getModelParams(TestBuilderEOProvider.STATIC, "BuilderTest");
        String result = ValueModelsJava.createInstanceVars(modelValues);
        Assert.assertNotNull(result);
        Assert.assertFalse(result.isEmpty());
        //AssertEO.compare(result);
    }
}
