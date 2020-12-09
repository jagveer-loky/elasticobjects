package org.fluentcodes.projects.elasticobjects.domain.test;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigInterface;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

public class AnObjectTest {

    @Test
    public void DEV__findModel__exception()  {
        try {
            ModelConfigInterface model = ProviderRootDevScope.EO_CONFIGS.findModel(AnObject.class);
            Assert.fail("Should throw EoException since " + AnObject.class.getSimpleName() + " is not in the cache");
        }
        catch(EoException e) {

        }
    }

    @Test
    public void TEST__findModel__$()  {
         Assertions.assertThat(ProviderRootTestScope.EO_CONFIGS.findModel(AnObject.class)).isNotNull();
    }

    @Test
    public void scopeTest__findFieldConfig_myObject__found()  {
        FieldConfig field = ProviderRootTestScope.EO_CONFIGS.findField(AnObject.MY_OBJECT);
        Assert.assertEquals(AnObject.MY_OBJECT, field.getFieldKey());
        Assertions.assertThat(field.hasUnique()).isFalse();
        Assertions.assertThat(field.hasNotNull()).isFalse();
        Assertions.assertThat(field.getDescription()).isNotNull();
        Assert.assertEquals(Object.class, field.getModelClass());
    }

    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(AnObject.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(AnObject.class);
    }

}
