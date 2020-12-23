package org.fluentcodes.projects.elasticobjects.calls.values;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.Models;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Ignore;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_LEVEL0;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_LEVEL1;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_LEVEL2;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_LEVEL3;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_STRING;

/**
 * Tests for {@link SinusValueCall}
 * @author Werner Diwischek
 * @since 13.07.2020.
 */
public class ValueCallTest {

    @Test
    public void createByModelConfig()  {
        ModelConfigChecks.create(ValueCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(ValueCall.class);
    }

    @Test
    public void givenModelConfig_whenIsCall_thenTrue()  {
        Assertions
                .assertThat(
                        ProviderRootTestScope.findModel(ValueCall.class).isCall()
                ).isTrue();
    }

    @Test
    public void givenModelCreate_whenSetValue_thenValueIsSet()  {
        final ModelConfig model = ProviderRootTestScope.findModel(ValueCall.class);
        final ValueCall call = (ValueCall)model.create();
        model.set(ValueCall.CONTENT, call, S_STRING);
        Assertions.assertThat(model.get(ValueCall.CONTENT, call)).isEqualTo(S_STRING);
    }

    @Test
    public void givenModels_thenChildValueTypeIsString()  {
        final EO eo = ProviderRootTestScope.createEo();
        final Models models = new Models(eo.getConfigsCache(), ValueCall.class);
        Models childModels = models.getChildModels(eo, new PathElement(ValueCall.CONTENT));
        Assertions.assertThat(childModels.isScalar()).isTrue();
        Assertions.assertThat(childModels.getModelClass()).isEqualTo(String.class);
    }

    @Test
    public void call_String__execute__return_String()  {
        final ValueCall call = new ValueCall(S_STRING);
        EO eo = ProviderRootTestScope.createEo();
        Assertions.assertThat(call.execute(eo)).isEqualTo(S_STRING);
    }

    @Ignore
    @Test
    public void givenEoWithCallString_whenExecuteEo_thenEoIsValue()  {
        EO eo = ProviderRootTestScope.createEo();
        final ValueCall call = new ValueCall(S_STRING);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get()).isEqualTo(S_STRING);
    }

    @Test
    public void givenEoWithCallStringWithPath_whenExecuteEo_thenEoIsValue()  {
        EO eo = ProviderRootTestScope.createEo();
        final ValueCall call = new ValueCall(S_STRING);
        call.setTargetPath(S_LEVEL0);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(S_LEVEL0)).isEqualTo(S_STRING);
    }

    @Test
    public void givenEoWithCallJsonMapWithPath_whenExecuteEo_thenEoIsMap()  {
        EO eo = ProviderRootTestScope.createEo();
        final ValueCall call = new ValueCall("{\"key1\":\"test\"}");
        call.setTargetPath("key0");
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get("key0", "key1")).isEqualTo(S_STRING);
    }

    @Test
    public void givenEoWithCallJsonListWithPath_whenExecuteEo_thenEoIsList()  {
        EO eo = ProviderRootTestScope.createEo();
        final ValueCall call = new ValueCall("[1,2]");
        call.setTargetPath(S_LEVEL0);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(S_LEVEL0, "0")).isEqualTo(1);
    }

    @Test
    public void call_TemplateCall_level0_content__execute__level0_1()  {
        EO eo = ProviderRootTestScope.createEo();
        final String template = " ===>{\"(ValueCall).\":{" +
                "\"targetPath\":\"level0\"}" +
                "}|" +
                "[1,2]" +
                "=>{}.";
        final TemplateCall call = new TemplateCall(template);
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(S_LEVEL0, "0")).isEqualTo(1);
    }

    @Test
    public void givenTemplateWithValueCallJsonMap_whenExecute_thenEoIsMap()  {
        EO eo = ProviderRootTestScope.createEo();
        final String template = " ===>{\"(ValueCall).\":{" +
                "\"targetPath\":\"level0\"}" +
                "}|" +
                "{\"level1\",\"test\"}" +
                "=>{}.";
        final TemplateCall call = new TemplateCall(template);
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(S_LEVEL0, S_LEVEL1)).isEqualTo(S_STRING);
    }

    @Test
    public void givenTemplateWithValueCallJsonMapAndLongerPath_whenExecute_thenEoIsMap()  {
        EO eo = ProviderRootTestScope.createEo();
        final String template = " ===>{\"(ValueCall).\":{" +
                "\"targetPath\":\"level0/level1/level2\"}" +
                "}|" +
                "{\"level3\",\"test\"}" +
                "=>{}.";
        final TemplateCall call = new TemplateCall(template);
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(S_LEVEL0, S_LEVEL1, S_LEVEL2, S_LEVEL3)).isEqualTo(S_STRING);
    }
}
