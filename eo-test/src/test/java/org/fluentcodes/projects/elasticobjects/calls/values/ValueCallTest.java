package org.fluentcodes.projects.elasticobjects.calls.values;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.Models;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTest;
import org.fluentcodes.projects.elasticobjects.paths.PathElement;
import org.junit.Ignore;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Tests for {@link SinusValueCall}
 * @author Werner Diwischek
 * @since 13.07.2020.
 */
public class ValueCallTest {
    private static final Logger LOG = LogManager.getLogger(ValueCallTest.class);
    private static final String SOURCE = "source";
    private static final String TARGET = "/target";
    private static final Double SIMPLE_RESULT = 0.8632093666488737;
    private static final Double ARRAY_RESULT2 = 0.1411200080598672;


    @Test
    public void givenModelCreate_whenSetValue_thenValueIsSet()  {
        final ModelConfig model = ProviderRootTest.findModel(ValueCall.class);
        final ValueCall call = (ValueCall)model.create();
        model.set(ValueCall.VALUE, call, S_STRING);
        Assertions.assertThat(model.get(ValueCall.VALUE, call)).isEqualTo(S_STRING);
    }

    @Test
    public void givenModels_thenChildValueTypeIsString()  {
        final EO eo = ProviderRootTest.createEo();
        final Models models = new Models(eo.getConfigsCache(), ValueCall.class);
        Models childModels = models.getChildModels(eo, new PathElement(ValueCall.VALUE));
        Assertions.assertThat(childModels.isScalar()).isTrue();
        Assertions.assertThat(childModels.getModelClass()).isEqualTo(String.class);

        childModels = models.createChildModels(eo, new PathElement(ValueCall.VALUE),"{}");
        Assertions.assertThat(childModels.isScalar()).isTrue();
        Assertions.assertThat(childModels.getModelClass()).isEqualTo(String.class);
    }

    @Test
    public void givenCallString_whenExecuteDirect_thenReturnValueIsEqualToValue()  {
        final ValueCall call = new ValueCall(S_STRING);
        EO eo = ProviderRootTest.createEo();
        Assertions.assertThat(call.execute(eo)).isEqualTo(S_STRING);
    }

    @Ignore
    @Test
    public void givenEoWithCallString_whenExecuteEo_thenEoIsValue()  {
        EO eo = ProviderRootTest.createEo();
        final ValueCall call = new ValueCall(S_STRING);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get()).isEqualTo(S_STRING);
    }

    @Test
    public void givenEoWithCallStringWithPath_whenExecuteEo_thenEoIsValue()  {
        EO eo = ProviderRootTest.createEo();
        final ValueCall call = new ValueCall(S_STRING);
        call.setTargetPath(S_LEVEL0);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(S_LEVEL0)).isEqualTo(S_STRING);
    }

    @Test
    public void givenEoWithCallJsonMapWithPath_whenExecuteEo_thenEoIsMap()  {
        EO eo = ProviderRootTest.createEo();
        final ValueCall call = new ValueCall("{\"level1\":\"test\"}");
        call.setTargetPath(S_LEVEL0);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(S_LEVEL0, S_LEVEL1)).isEqualTo(S_STRING);
    }

    @Test
    public void givenEoWithCallJsonListWithPath_whenExecuteEo_thenEoIsList()  {
        EO eo = ProviderRootTest.createEo();
        final ValueCall call = new ValueCall("[1,2]");
        call.setTargetPath(S_LEVEL0);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(S_LEVEL0, "0")).isEqualTo(1);
    }

    @Test
    public void givenTemplateWithValueCallJsonList_whenExecute_thenEoIsMap()  {
        EO eo = ProviderRootTest.createEo();
        final String template = "$[(ValueCall)level0][1,2]$[/]";
        final TemplateCall call = new TemplateCall(template);
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(S_LEVEL0, "0")).isEqualTo(1);
    }

    @Test
    public void givenTemplateWithValueCallJsonMap_whenExecute_thenEoIsMap()  {
        EO eo = ProviderRootTest.createEo();
        final String template = "$[(ValueCall)level0]{\"level1\",\"test\"}$[/]";
        final TemplateCall call = new TemplateCall(template);
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(S_LEVEL0, S_LEVEL1)).isEqualTo(S_STRING);
    }

    @Test
    public void givenTemplateWithValueCallJsonMapAndLongerPath_whenExecute_thenEoIsMap()  {
        EO eo = ProviderRootTest.createEo();
        final String template = "$[(ValueCall)level0/level1/level2]{\"level3\",\"test\"}$[/]";
        final TemplateCall call = new TemplateCall(template);
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(S_LEVEL0, S_LEVEL1, S_LEVEL2, S_LEVEL3)).isEqualTo(S_STRING);
    }
}
