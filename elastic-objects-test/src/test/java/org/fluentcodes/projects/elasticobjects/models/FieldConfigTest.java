package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Werner on 27.8.2018.
 */
public class FieldConfigTest {

    @Test
    public void createByModelConfig_throwsException()  {
        ModelConfigChecks.createThrowsException(FieldConfig.class);
    }

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(FieldConfig.class);
    }

    @Test
    public void scopeTest__findModel_FieldConfig__$()  {
        final ModelConfig fieldModel = ProviderRootTestScope.EO_CONFIGS.findModel(FieldConfig.class.getSimpleName());
        Assert.assertEquals(FieldConfig.class.getSimpleName(), fieldModel.getModelKey());
        Assert.assertEquals(FieldConfig.class, fieldModel.getModelClass());
    }
}
