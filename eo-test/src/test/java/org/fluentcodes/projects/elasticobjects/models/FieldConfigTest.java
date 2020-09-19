package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.*;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.calls.configs.ConfigKeysCall;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
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
    public void givenConfigCallWithFieldNaturalIdAndTestEo_whenExecuteCall_thenListIsReturned() {
        ConfigKeysCall call = new ConfigKeysCall();
        call.setConfigType(FieldConfig.class.getSimpleName());
        call.setConfigFilter("naturalId");
        List<String> result = (List<String>) call.execute(ProviderRootTestScope.createEo());
        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void givenEoWithConfigCallWithTargetPathAndConfigFilterNaturalId_whenExecuteEo_thenListIsSet() {
        ConfigKeysCall call = new ConfigKeysCall();
        call.setConfigType(FieldConfig.class.getSimpleName());
        call.setTargetPath("fieldKeys");
        call.setConfigFilter("naturalId");

        EO eo = ProviderRootTestScope.createEo();
        eo.addCall(call);
        eo.execute();

        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(((List)eo.get("fieldKeys")).size()).isGreaterThan(0);
    }

    @Test
    public void findFieldConfigInModelCache()  {
        final ModelInterface fieldModel = ProviderRootTestScope.EO_CONFIGS.findModel(FieldConfig.class.getSimpleName());
        Assert.assertEquals(FieldConfig.class.getSimpleName(), fieldModel.getModelKey());
        Assert.assertEquals(FieldConfig.class, fieldModel.getModelClass());
    }

    // TODO check when it's time with properties
    @Ignore
    @Test
    public void getId()  {
        final FieldConfig idConfig = ProviderRootTestScope.EO_CONFIGS.findField(Model.ID);
        Assert.assertEquals(Long.class, idConfig.getModelConfig().getModelClass());
    }



    @Test
    public void assertId()  {
        FieldConfig field = ProviderRootTestScope.EO_CONFIGS.findField(Model.ID);
        Assert.assertEquals(Model.ID, field.getFieldKey());
        Assert.assertEquals(S_BOOLEAN, field.getUnique());
        Assert.assertEquals(S_BOOLEAN, field.getNotNull());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, field.getDescription());
        Assert.assertEquals(Long.class, field.getModelClass());
    }

    /**
     * Will test the alias thing to id.
     *
     * @
     */
    @Test
    public void assertID()  {
        FieldConfig field = ProviderRootTestScope.EO_CONFIGS.findField(F_UPPER_ID_KEY);
        Assert.assertEquals(F_UPPER_ID, field.getFieldKey());
        Assert.assertEquals(S_BOOLEAN, field.getUnique());
        Assert.assertEquals(S_BOOLEAN, field.getNotNull());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, field.getDescription());
        Assert.assertEquals(Long.class, field.getModelClass());
    }

    /**
     * Will test the alias thing to id.
     *
     * @
     */
    @Test
    public void assertTestObject()  {
        FieldConfig field = ProviderRootTestScope.EO_CONFIGS.findField(BasicTest.TEST_OBJECT);
        Assert.assertEquals(BasicTest.TEST_OBJECT, field.getFieldKey());
        Assert.assertEquals(false, field.getUnique());
        Assert.assertEquals(false, field.getNotNull());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, field.getDescription());
        Assert.assertEquals(Object.class, field.getModelClass());
    }

    @Test
    public void checkFieldConfig() {
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
