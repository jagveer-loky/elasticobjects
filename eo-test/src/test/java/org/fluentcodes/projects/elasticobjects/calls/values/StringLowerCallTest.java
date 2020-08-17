package org.fluentcodes.projects.elasticobjects.calls.values;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_LEVEL0;
import static org.fluentcodes.projects.elasticobjects.calls.values.StringLowerCall.lower;

/**
 * Tests for {@link StringLowerCall}
 * @author Werner Diwischek
 * @since 13.07.2020.
 */
public class StringLowerCallTest {
    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(StringLowerCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(StringLowerCall.class);
    }


    @Test
    public void givenModelCreateAndValueTest_whenExecute_thenUpperCaseReturned()  {
        final ModelConfig model = ProviderRootTestScope.findModel(StringLowerCall.class);
        final StringLowerCall call = (StringLowerCall)model.create();
        EO eo = ProviderRootTestScope.createEo().set("tEsT",S_LEVEL0);
        Assertions.assertThat(eo.get()).isEqualTo("tEsT");
        Assertions.assertThat(call.execute(eo)).isEqualTo("test");
    }
    
    @Test
    public void givenTemplateWithValueCallJsonMap_whenExecute_thenEoIsMap()  {
        EO eo = ProviderRootTestScope.createEo();
        final String template = "^$[(ValueCall)level0 value=\"tEsT\"/] - $[(StringLowerCall)level0 inTemplate=\"true\"/] $";
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
