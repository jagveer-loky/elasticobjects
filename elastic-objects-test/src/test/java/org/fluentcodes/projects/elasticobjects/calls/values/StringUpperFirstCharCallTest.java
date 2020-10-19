package org.fluentcodes.projects.elasticobjects.calls.values;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_LEVEL0;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_STRING;
import static org.fluentcodes.projects.elasticobjects.calls.values.StringUpperFirstCharCall.upperFirstCharacter;

/**
 * Tests for {@link StringUpperFirstCharCall}
 * @author Werner Diwischek
 * @since 13.07.2020.
 */
public class StringUpperFirstCharCallTest {
    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(StringUpperFirstCharCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(StringUpperFirstCharCall.class);
    }
    @Test
    public void givenModelCreateAndValueTest_whenExecute_thenUpperCaseReturned()  {
        final ModelConfig model = ProviderRootTestScope.findModel(StringUpperFirstCharCall.class);
        final StringUpperFirstCharCall call = (StringUpperFirstCharCall)model.create();
        EO eo = ProviderRootTestScope.createEo().set("test",S_LEVEL0);
        Assertions.assertThat(eo.get()).isEqualTo("test");
        Assertions.assertThat(call.execute(eo)).isEqualTo("Test");
    }
    
    @Test
    public void call_TemplateCall_level0_test__execute__Test()  {
        EO eo = ProviderRootTestScope.createEo();
        final String template = "START" +
                "===>{\"level0\":\"test\"}." +
                " - \n" +
                "===>{\"(StringUpperFirstCharCall).\":{" +
                "\"sourcePath\":\"level0\", " +
                "\"targetPath\":\"" + Call.TARGET_AS_STRING + "\"}" +
                "}." +
                " END";
        final TemplateCall call = new TemplateCall(template);
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).isEqualTo("START - Test END");
    }

    @Test
    public void upperFirstChar() {
        Assert.assertEquals("Test", upperFirstCharacter(S_STRING));
    }
}
