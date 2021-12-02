package org.fluentcodes.projects.elasticobjects.calls.values;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL0;
import static org.fluentcodes.projects.elasticobjects.calls.values.StringLowerCall.lower;

/**
 * Tests for {@link StringLowerCall}
 * @author Werner Diwischek
 * @since 13.07.2020.
 */
public class StringLowerCallTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return StringLowerCall.class;
    }

    @Override
    @Test
    public void create_noEoException()  {
        assertCreateNoException();
    }

    @Override
    @Test
    public void compareModelConfig()  {
        assertModelConfigEqualsPersisted();
    }

    @Override
    @Test
    public void compareBeanFromModelConfig()  {
        assertBeanFromModelConfigEqualsPersisted();
    }


    @Test
    public void givenModelCreateAndValueTest_whenExecute_thenUpperCaseReturned()  {
        final ModelConfig model = ProviderConfigMaps.findModel(StringLowerCall.class);
        final StringLowerCall call = (StringLowerCall)model.create();
        EO eo = ProviderConfigMaps.createEo().set("tEsT",S_LEVEL0);
        Assertions.assertThat(eo.get()).isEqualTo("tEsT");
        Assertions.assertThat(call.execute(eo)).isEqualTo("test");
    }
    
    @Test
    public void givenTemplateWithValueCallJsonMap_whenExecute_thenEoIsMap()  {
        EO eo = ProviderConfigMaps.createEo();
        final String template = "^" +
                "@{" +
                "\"level0\":\"tEsT\"}." +
                " - \n" +
                "@{\"(StringLowerCall).\":{" +
                "\"sourcePath\":\"level0\"," +
                "\"targetPath\":\"" + Call.TARGET_AS_STRING + "\"}" +
                "}." +
                " $";
        final TemplateCall call = new TemplateCall(template);
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).isEqualTo("^ - test $");
    }

    @Test
    public void givenStringWithUpperCase_whenLower_thenLowerCase() {
        Assert.assertEquals("testUpdateAll", lower("TEST_UPDATE_ALL"));
    }
}
