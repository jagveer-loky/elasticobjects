package org.fluentcodes.projects.elasticobjects.calls.values;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.LogLevel;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.Parser;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderMapJson;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.projects.elasticobjects.testitemprovider.TestProviderJsonCalls;
import org.fluentcodes.tools.xpect.XpectEo;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link SinusValueCall}
 *
 * @author Werner Diwischek
 * @since 13.07.2020.
 */
public class SinusValueCallTest implements IModelConfigCreateTests {
    private static final Logger LOG = LogManager.getLogger(SinusValueCallTest.class);
    private static final String SOURCE = "source";
    private static final String TARGET = "/target";
    private static final Double SIMPLE_RESULT = 0.8632093666488737;
    private static final Double ARRAY_RESULT2 = 0.1411200080598672;


    @Override
    public Class<?> getModelConfigClass() {
        return SinusValueCall.class;
    }

    @Override
    @Test
    public void create_noEoException() {
        assertCreateNoException();
    }

    @Override
    @Test
    public void compareModelConfig() {
        assertModelConfigEqualsPersisted();
    }

    @Override
    @Test
    public void compareBeanFromModelConfig() {
        assertBeanFromModelConfigEqualsPersisted();
    }


    public static final EO createSimple() {
        return ProviderConfigMaps.createEo("{\"(Double)source\":2.1}");
    }

    /**
     * Wiki example
     */
    @Test
    public void eo_source_1_call_target__execute__get_target_0_8414709848078965() {
        final EO eo = ProviderConfigMaps.createEo();
        final String jsonString = "{\n" +
                "  \"(Double)source\":1,\n" +
                "  \"(SinusValueCall)/target\": {\n" +
                "    \"sourcePath\": \"/source\"\n" +
                "  }\n" +
                "}";
        eo.mapObject(jsonString);
        eo.execute();
        assertThat(eo.get("target")).isEqualTo(0.8414709848078965);
    }

    public static final EO createArray() {
        return ProviderMapJson.VALUES_CALL_NUMBER_ARRAY.createMapTestEo().getEo(SOURCE);
    }

    /**
     * Basic Wiki Example
     */
    @Ignore
    @Test
    public void givenCallSinusValue_thenInputValueIsReplaced() {
        EO eo = TestProviderJsonCalls.CALL_SINUS_VALUE_JSON.createMapEo();
        eo.execute();
        Assertions.assertThat(eo.get("source")).isEqualTo(0.8414709848078965);
        Assertions.assertThat(eo.getEo("source").isChanged()).isTrue();
        new XpectEo<>().compareAsString(eo);
    }

    @Test
    public void eo_source_1__call_execute__return_0_8632093666488737() {
        final Call call = new SinusValueCall();
        EO eo = createSimple();
        Double result = (Double) call.execute(eo.getEo("source"));
        Assertions.assertThat(result).isEqualTo(0.8632093666488737);
    }

    @Test
    public void checkSerializationSimple() {
        Call call = new SinusValueCall();
        call.setSourcePath(SOURCE);
        EO eo = createSimple().getRoot();
        eo.addCall(call);
        Assertions.assertThat(eo.getLog()).isEmpty();
        String value = new EOToJSON().setSerializationType(JSONSerializationType.EO).toJson(eo);
        EO eoFromString = ProviderConfigMaps.createEo(value);
        Assertions.assertThat(eoFromString.getLog()).isEmpty();
        eoFromString.execute();
    }

    @Ignore
    @Test
    public void createFromModel_ok() {
        final ModelConfig model = ProviderConfigMaps.findModel(SinusValueCall.class);
        final Call call = (Call) model.create();
        EO eo = createSimple();
        Double result = (Double) call.execute(eo);
        Assertions.assertThat(result).isNotNull();
        final EO eoCall = ProviderConfigMaps.createEo(call);
        final String asString = new EOToJSON()
                .setSerializationType(JSONSerializationType.EO)
                .toJson(eoCall);
        final Call fromString = (Call) ProviderConfigMaps.createEo(asString).get();
        Double fromResult = (Double) fromString.execute(eo);
        Assertions.assertThat(fromResult).isEqualTo(result);
    }

    @Test
    public void givenEoSimple_WhenExecuteDirect_ResultExpected() {
        EO eo = createSimple();
        Double result = (Double) new SinusValueCall().execute(eo.getEo("source"));
        Assertions.assertThat(result).isEqualTo(SIMPLE_RESULT);
    }

    @Test
    public void givenEoSimple_whenExecuteWithEo_thenDurationIsSet() throws InterruptedException {
        final Call call = new SinusValueCall().setSourcePath(SOURCE);
        EO eo = createSimple();
        eo.setLogLevel(LogLevel.WARN);
        eo.addCall(call);
        eo.execute();
        eo.setSerializationType(JSONSerializationType.STANDARD);
        new XpectEo().compareAsString(eo.getRoot());
        Assertions.assertThat(eo.getLog()).isEmpty();
        eo.setSerializationType(JSONSerializationType.EO);
        final String asString = new EOToJSON()
                .toJson(eo.getRoot());
        final EO fromString = ProviderConfigMaps.createEo(asString);
        fromString.setLogLevel(LogLevel.INFO);
        fromString.execute();
        Assertions.assertThat(fromString.get(SOURCE)).isEqualTo(SIMPLE_RESULT);
        Long duration = (Long) fromString.get(PathElement.CALLS, "0", "duration");
        Assertions.assertThat(duration).isNotNull();
    }

    @Test
    public void givenEoArrayWithSourceAndTargetFromFile_whenExecute_hasSinusValueInTarget() {
        EO eo = TestProviderJsonCalls.CALL_SINUS_ARRAY_JSON.createMapEo();
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(TARGET, "2")).isEqualTo(ARRAY_RESULT2);
    }

    //TODO later
    @Ignore
    @Test
    public void givenEoArrayWithSourceAndTargetFromFileOnTargetPath_whenExecute_hasSinusValueInTarget() {
        EO eoBefore = ProviderMapJson.SIMPLE_INSERT_WITH_PATH.createMapTestEo();
        EO eo = TestProviderJsonCalls.CALL_SINUS_ARRAY_ON_TARGET_PATH_JSON.createMapEo();
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(TARGET, "2")).isEqualTo(ARRAY_RESULT2);
    }

    @Test
    public void eo_1_2_3__execute_serialize__() {
        Call call = new SinusValueCall()
                .setTargetPath(TARGET)
                .setSourcePath("/source/*");
        EO eo = createArray();
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(TARGET, "2")).isEqualTo(ARRAY_RESULT2);
        String value = new EOToJSON()
                .setSerializationType(JSONSerializationType.EO)
                .toJson(eo.getRoot());
        EO eoFromJson = ProviderConfigMaps.createEo(value);
        Assertions.assertThat(eoFromJson.get(TARGET, "2")).isEqualTo(ARRAY_RESULT2);
        Assertions.assertThat(eoFromJson.get(PathElement.CALLS, "0")).isNotNull();
        Assertions.assertThat(eoFromJson.get(PathElement.CALLS, "0", "targetPath")).isEqualTo(TARGET + "/2");
    }

    @Test
    public void givenEoWithSimpleSinusCall_whenExecuteEo_thenPlaceHolderIsReplaced() {
        TemplateCall call = new TemplateCall();
        call.setContent("sin(.{testKey}.) = \n" +
                "@{\"(SinusValueCall).\":{" +
                "\"sourcePath\":\"testKey\", " +
                "\"targetPath\":\"" + Call.TARGET_AS_STRING + "\"}" +
                "}.");
        EO eo = ProviderConfigMaps.createEo();
        eo.addCall(call);
        eo.set(2, "testKey");
        eo.execute();
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String) eo.get("_template")).isEqualTo("sin(2) = 0.9092974268256817");
    }


    @Test
    public void eo_CallSinusArrayTemplateJson__execute__3times() {
        EO eo = TestProviderJsonCalls.CALL_SINUS_ARRAY_TEMPLATE_JSON.getEo();
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String) eo.get("_template")).isEqualTo(
                "sin(1.0) = 0.8414709848078965\n" +
                        "sin(2.0) = 0.9092974268256817\n" +
                        "sin(3.0) = 0.1411200080598672\n");
    }

    @Test
    public void Eo_value_2_template__parse__get_value_0() {
        EO eo = ProviderConfigMaps.createEo();
        eo.set(2, "value");
        String result = new Parser("-" +
                " @{\"(SinusValueCall).\":{" +
                "\"sourcePath\":\"value\"}" +
                "}." +
                "-").parse(eo);
        Assertions.assertThat(result).isEqualTo("--");
        Assertions.assertThat(eo.get("value")).isEqualTo(0); // was integer before.
    }

    @Test
    public void givenEo_whenReplaceStringInTemplate_thenPlaceHolderIsReplaced() {
        EO eo = ProviderConfigMaps.createEo();
        eo.set(2, "value");
        String result = new Parser("-" +
                " @{\"(SinusValueCall).\":{" +
                "\"sourcePath\":\"value\", " +
                "\"targetPath\":\"" + Call.TARGET_AS_STRING + "\"}" +
                "}." +
                "-").parse(eo);
        Assertions.assertThat(result).isEqualTo("-0.9092974268256817-");
        Assertions.assertThat(eo.get("value")).isEqualTo(2); // was integer before.
    }
}
