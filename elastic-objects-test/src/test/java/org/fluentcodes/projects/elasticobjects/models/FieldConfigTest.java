package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.calls.configs.ConfigKeysCall;
import org.fluentcodes.projects.elasticobjects.domain.Base;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.tools.xpect.XpectString;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_UPPER_ID;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_UPPER_ID_KEY;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_NOT_NULL_FAILS;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_BOOLEAN;

/**
 * Created by Werner on 27.8.2018.
 */
public class FieldConfigTest {

    @Test
    public void createByModelConfig_throwsException()  {
        ConfigModelChecks.createThrowsException(FieldConfig.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(FieldConfig.class);
    }

    @Test
    public void givenConfigEntries_whenResolve_thenNoErrors()  {
        ConfigChecks.resolveConfigs(FieldConfig.class);
    }

    @Test
    public void resolveConfigurations()  {
        ConfigChecks.resolveConfigurations(FieldConfig.class);
    }

    @Test
    public void compareConfigurations()  {
        ConfigChecks.compareConfigurations(FieldConfig.class);
    }


    @Test
    public void scopeTest__findModel_FieldConfig__$()  {
        final ModelConfigInterface fieldModel = ProviderRootTestScope.EO_CONFIGS.findModel(FieldConfig.class.getSimpleName());
        Assert.assertEquals(FieldConfig.class.getSimpleName(), fieldModel.getModelKey());
        Assert.assertEquals(FieldConfig.class, fieldModel.getModelClass());
    }


    @Test
    public void scopeTest__findField_id__xpected()  {
        FieldConfig fieldConfig = ProviderRootTestScope.EO_CONFIGS.findField(Base.ID);
        Assert.assertEquals(Base.ID, fieldConfig.getFieldKey());
        Assert.assertEquals(S_BOOLEAN, fieldConfig.getUnique());
        Assert.assertEquals(S_BOOLEAN, fieldConfig.getNotNull());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, fieldConfig.getDescription());
        Assert.assertEquals(Long.class, fieldConfig.getModelConfig().getModelClass());
        Assert.assertEquals(Long.class, fieldConfig.getModelClass());
        String toString = fieldConfig.toString();
        new XpectString().compareAsString(toString);
    }

    @Test
    public void scopeTest__getConfigKeys__printWithEmptyModels() {
        EOConfigsCache cache = ProviderRootTestScope.EO_CONFIGS;
        Set<String> keys = cache.getConfigKeys(FieldConfig.class);
        int counter = 0;
        for (String key: keys) {
            FieldConfig fieldConfig = cache.findField(key);
            if (!fieldConfig.hasModelList()) {
                counter++;
                System.out.println(counter  + " no model for field " + key);
            }
            Assertions.assertThat(fieldConfig).isNotNull();
        }
    }

}
