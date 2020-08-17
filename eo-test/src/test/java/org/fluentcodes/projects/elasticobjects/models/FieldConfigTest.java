package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.calls.configs.ConfigKeysCall;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

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
        List<String> result = call.execute(ProviderRootTestScope.createEo());
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

    @Test
    public void getId()  {
        
        final FieldConfig idConfig = ProviderRootTestScope.EO_CONFIGS.findField(Model.ID);
        Assert.assertEquals(Long.class, idConfig.getModelConfig().getModelClass());
        Assert.assertNotNull(idConfig.getEoFieldParams());
        Assert.assertFalse(idConfig.getEoFieldParams().isDeliverAction());
        Assert.assertEquals(Path.DELIMITER + PathElement.MATCHER, idConfig.getEoFieldParams().getPathPatternAsString());
    }



    @Test
    public void assertId()  {
        FieldConfig field = ProviderRootTestScope.EO_CONFIGS.findField(Model.ID);
        Assert.assertEquals(Model.ID, field.getFieldKey());
        Assert.assertEquals(Model.ID, field.getFieldName());
        Assert.assertEquals(S_BOOLEAN, field.isUnique());
        Assert.assertEquals(S_BOOLEAN, field.isNotNull());
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
        Assert.assertEquals(F_UPPER_ID, field.getFieldName());
        Assert.assertEquals(S_BOOLEAN, field.isUnique());
        Assert.assertEquals(S_BOOLEAN, field.isNotNull());
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
        Assert.assertEquals(BasicTest.TEST_OBJECT, field.getFieldName());
        Assert.assertEquals(false, field.isUnique());
        Assert.assertEquals(false, field.isNotNull());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, field.getDescription());
        Assert.assertEquals(Object.class, field.getModelClass());
    }

}
