package org.fluentcodes.projects.elasticobjects.calls.values;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.*;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserTemplate;
import org.fluentcodes.projects.elasticobjects.testitemprovider.*;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.tools.xpect.XpectEo;
import org.junit.Ignore;
import org.junit.Test;

import static java.lang.Thread.sleep;

/**
 * Tests for {@link SinusValueCall}
 * @author Werner Diwischek
 * @since 13.07.2020.
 */
public class SinusValueCallTest {
    private static final Logger LOG = LogManager.getLogger(SinusValueCallTest.class);
    private static final String SOURCE = "source";
    private static final String TARGET = "/target";
    private static final Double SIMPLE_RESULT = 0.8632093666488737;
    private static final Double ARRAY_RESULT2 = 0.1411200080598672;

    public static final EO createSimple() {
        try {
            return ProviderMapJson.VALUES_CALL_NUMBER_SCALAR.createMapEo().getEo(SOURCE);
        }
        catch (Exception e) {
            LOG.info(e.getMessage());
            throw e;
        }
    }

    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(SinusValueCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(SinusValueCall.class);
    }


    public static final EO createArray() {
        return ProviderMapJson.VALUES_CALL_NUMBER_ARRAY.createMapEo().getEo(SOURCE);
    }

    @Test
    public void direct_ok()  {
        final Call call = new SinusValueCall();
        EO eo = createSimple();
        Double result = (Double) call.execute(eo);
        Assertions.assertThat(result).isEqualTo(0.8632093666488737);
    }

    @Test
    public void checkSerializationSimple()  {
        Call call = new SinusValueCall();
        call.setSourcePath(SOURCE);
        EO eo = createSimple();
        eo.addCall(call);
        Assertions.assertThat(eo.getLog()).isEmpty();
        String value = new EOToJSON().setSerializationType(JSONSerializationType.EO).toJSON(eo);
        EO eoFromString = ProviderRootTestScope.createEo(value);
        Assertions.assertThat(eoFromString.getLog()).isEmpty();
        eoFromString.execute();
    }

    @Ignore
    @Test
    public void createFromModel_ok()  {
        final ModelConfig model = ProviderRootTestScope.findModel(SinusValueCall.class);
        final Call call = (Call) model.create();
        EO eo = createSimple();
        Double result = (Double) call.execute(eo);
        Assertions.assertThat(result).isNotNull();
        final EO eoCall = ProviderRootTestScope.createEo(call);
        final String asString = new EOToJSON()
                .setSerializationType(JSONSerializationType.EO)
                .toJSON(eoCall);
        final Call fromString = (Call) ProviderRootTestScope.createEo(asString).get();
        Double fromResult = (Double) fromString.execute(eo);
        Assertions.assertThat(fromResult).isEqualTo(result);
    }

    @Test
    public void givenEoSimple_WhenExecuteDirect_ResultExpected()  {
        EO eo = createSimple();
        Double result = new SinusValueCall().execute(eo);
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
                .toJSON(eo.getRoot());
        final EO fromString = ProviderRootTestScope.createEo(asString);
        fromString.setLogLevel(LogLevel.INFO);
        fromString.execute();
        Assertions.assertThat(fromString.get(SOURCE)).isEqualTo(SIMPLE_RESULT);
        Long duration = (Long)fromString.get(PathElement.CALLS, "0", "duration");
        Assertions.assertThat(duration).isNotNull();
    }

    @Test
    public void givenEoArrayWithSourceAndTargetFromFile_whenExecute_hasSinusValueInTarget() {
        EO eo = TestProviderJsonCalls.CALL_SINUS_ARRAY.createMapEo();
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(TARGET ,"2")).isEqualTo(ARRAY_RESULT2);
    }

    @Test
    public void givenEoArrayWithSourceAndTargetFromFileOnTargetPath_whenExecute_hasSinusValueInTarget() {
        EO eoBefore = ProviderMapJson.SIMPLE_INSERT_WITH_PATH.createMapEo();
        EO eo = TestProviderJsonCalls.CALL_SINUS_ARRAY_ON_TARGET_PATH.createMapEo();
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(TARGET ,"2")).isEqualTo(ARRAY_RESULT2);
    }

    @Test
    public void givenEoArrayAndCallWithSourceAndFilterAndTarget_WhenExecuteWithinEo()  {
        Call call =  new SinusValueCall()
                .setTargetPath(TARGET)
                .setSourcePath("/source")
                .setFilterPath("*");
        EO eo = createArray();
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(TARGET ,"2")).isEqualTo(ARRAY_RESULT2);
        String value = new EOToJSON()
                .setSerializationType(JSONSerializationType.EO)
                .toJSON(eo.getRoot());
        EO eoFromJson = ProviderRootTestScope.createEo(value);
        Assertions.assertThat(eoFromJson.get(TARGET ,"2")).isEqualTo(ARRAY_RESULT2);
        Assertions.assertThat(eoFromJson.get(PathElement.CALLS,"0")).isNotNull();
        Assertions.assertThat(eoFromJson.get(PathElement.CALLS,"0","targetPath")).isEqualTo(TARGET);
    }

    @Test
    public void givenEoWithSimpleSinusCall_whenExecuteEo_thenPlaceHolderIsReplaced()  {
        TemplateCall call = new TemplateCall();
        call.setContent("sin($[testKey]) = $[(SinusValueCall)testKey inTemplate=\"true\"/]");
        EO eo = ProviderRootTestScope.createEo();
        eo.addCall(call);
        eo.set(2, "testKey");
        eo.execute();
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String)eo.get("_template")).isEqualTo("sin(2) = 0.9092974268256817");
    }


    @Test
    public void givenEoWithContentWithSinusCallPlaceholderJson_whenExecuteEo_thenPlaceHolderIsReplaced()  {
        EO eox = TestProviderJson.MAP_SMALL_WITH_KEY.getEoTest();
        EO eo = TestProviderJsonCalls.CALL_SINUS_ARRAY.getEo();
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String)eo.get("_template")).isEqualTo(
                "sin(1.0) = 0.8414709848078965\n" +
                        "sin(2.0) = 0.9092974268256817\n" +
                        "sin(3.0) = 0.1411200080598672\n");
    }

    @Test
    public void givenEo_whenReplaceString_thenPlaceHolderIsReplaced()  {
        EO eo = ProviderRootTestScope.createEo();
        eo.set(2, "value");
        String result = new ParserTemplate("-$[(SinusValueCall)value/]-").parse(eo);
        Assertions.assertThat(result).isEqualTo("--");
        Assertions.assertThat(eo.get("value")).isEqualTo(0); // was integer before.
    }

    @Test
    public void givenEo_whenReplaceStringInTemplate_thenPlaceHolderIsReplaced()  {
        EO eo = ProviderRootTestScope.createEo();
        eo.set(2, "value");
        String result = new ParserTemplate("-$[(SinusValueCall)value inTemplate=\"true\"/]-").parse(eo);
        Assertions.assertThat(result).isEqualTo("-0.9092974268256817-");
        Assertions.assertThat(eo.get("value")).isEqualTo(2); // was integer before.
    }
}
