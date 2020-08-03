package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.configs.ConfigKeysCall;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.fileprovider.ProviderRootDev;
import org.fluentcodes.projects.elasticobjects.fileprovider.ProviderRootTest;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.paths.PathElement;
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
    public void givenModel_whenCreate_thenThrowsException()  {
        ConfigChecks.findModelAndCreateInstanceExceptionThrown(FieldConfig.class);
    }

    @Test
    public void givenConfigEntries_whenResolve_thenNoErrors()  {
        ConfigChecks.resolveConfigs(FieldConfig.class);
    }

    @Test
    public void givenConfigCallWithFieldNaturalIdAndTestEo_whenExecuteCall_thenListIsReturned() {
        ConfigKeysCall call = new ConfigKeysCall();
        call.setConfigType(FieldConfig.class.getSimpleName());
        call.setConfigFilter("naturalId");
        List<String> result = call.execute(ProviderRootTest.createEo());
        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void givenConfigCallWithFieldKeysDevEo_whenExecuteCall_thenListIsReturned() {
        ConfigKeysCall call = new ConfigKeysCall();
        call.setConfigType(FieldConfig.class.getSimpleName());
        List<String> result = call.execute(ProviderRootDev.createEo());
        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void givenEoWithConfigCallWithTargetPathAndConfigFilterNaturalId_whenExecuteEo_thenListIsSet() {
        ConfigKeysCall call = new ConfigKeysCall();
        call.setConfigType(FieldConfig.class.getSimpleName());
        call.setTargetPath("fieldKeys");
        call.setConfigFilter("naturalId");

        EO eo = ProviderRootTest.createEo();
        eo.addCall(call);
        eo.execute();

        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(((List)eo.get("fieldKeys")).size()).isGreaterThan(0);
    }

    @Test
    public void givenEoWithTemplateWithConfigKeyListCallAndConfigFilterNaturalId_whenExecuteEo_thenTemplateResultContainsNaturalId()  {
        final TemplateCall call = new TemplateCall();
        final String template = "Load model configuration key list: " +
                "$[(ConfigKeyListCall)fieldKey\" " +
                "configType=\"ModelConfig\" " +
                "configFilter=\"FieldConfig\" " +
                "inTemplate=\"true\"/]";
        call.setContent(template);

        EO eo = ProviderRootTest.createEo();
        eo.addCall(call);
        eo.execute();

        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get("_template")).isEqualTo("Load model configuration key list: [FieldConfig]");
    }

    @Test
    public void findFieldConfigInModelCache()  {
        final ModelInterface fieldModel = ProviderRootTest.EO_CONFIGS.findModel(FieldConfig.class.getSimpleName());
        Assert.assertEquals(FieldConfig.class.getSimpleName(), fieldModel.getModelKey());
        Assert.assertEquals(FieldConfig.class, fieldModel.getModelClass());
    }

    @Test
    public void testFieldFromClassPath_Found()  {
        EOConfigsCache cache = ProviderRootTest.EO_CONFIGS;
        FieldConfig field = cache.findField("ClassTest.id");
        Assert.assertNotNull(field);
        Assert.assertEquals("ClassTest.id", field.getNaturalId());
        Assert.assertEquals("id", field.getKey());
    }

    @Test
    public void testConfigMap()  {
        EOConfigMapFields map = new EOConfigMapFields(ProviderRootTest.EO_CONFIGS);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.find(F_ID_KEY));
        Assert.assertTrue(map.hasKey(F_MODEL_KEYS));
    }


    @Test
    public void getId()  {
        
        final FieldConfig idConfig = ProviderRootTest.EO_CONFIGS.findField(Model.ID);
        Assert.assertEquals(Long.class, idConfig.getModelConfig().getModelClass());
        Assert.assertNotNull(idConfig.getEoFieldParams());
        Assert.assertFalse(idConfig.getEoFieldParams().isDeliverAction());
        Assert.assertEquals(Path.DELIMITER + PathElement.MATCHER, idConfig.getEoFieldParams().getPathPatternAsString());
    }



    @Test
    public void assertId()  {
        FieldConfig field = ProviderRootTest.EO_CONFIGS.findField(Model.ID);
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
        FieldConfig field = ProviderRootTest.EO_CONFIGS.findField(F_UPPER_ID_KEY);
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
        FieldConfig field = ProviderRootTest.EO_CONFIGS.findField(F_TEST_OBJECT);
        Assert.assertEquals(F_TEST_OBJECT, field.getFieldKey());
        Assert.assertEquals(F_TEST_OBJECT, field.getFieldName());
        Assert.assertEquals(false, field.isUnique());
        Assert.assertEquals(false, field.isNotNull());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, field.getDescription());
        Assert.assertEquals(Object.class, field.getModelClass());
    }

}
