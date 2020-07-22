package org.fluentcodes.projects.elasticobjects.calls.values;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.LogLevel;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.config.ModelConfig;
import org.fluentcodes.projects.elasticobjects.executor.CallExecutorImpl;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.paths.PathElement;
import org.fluentcodes.projects.elasticobjects.test.TestProviderMapJsn;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;
import org.fluentcodes.tools.xpect.XpectEo;
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
        return TestProviderMapJsn.VALUES_CALL_NUMBER_SCALAR.createMapEo().getEo(SOURCE);
    }

    public static final EO createArray() {
        return TestProviderMapJsn.VALUES_CALL_NUMBER_ARRAY.createMapEo().getEo(SOURCE);
    }

    @Test
    public void direct_ok()  {
        final Call call = new SinusValueCall();
        EO eo = createSimple();
        Double result = (Double) call.execute(eo);
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void checkSerializationSimple()  {
        Call call = new SinusValueCall();
        EO eo = createSimple();
        eo.addCall(call);
        Assertions.assertThat(eo.getLog()).isEmpty();
        String value = new EOToJSON().setSerializationType(JSONSerializationType.EO).toJSON(eo);
        EO eoFromString = TestProviderRootTest.createEo(value);
        Assertions.assertThat(eoFromString.getLog()).isEmpty();
        eoFromString.execute();
    }

    @Test
    public void createFromModel_ok()  {
        final ModelConfig model = TestProviderRootTest.findModel(SinusValueCall.class);
        final Call call = (Call) model.create();
        EO eo = createSimple();
        Double result = (Double) call.execute(eo);
        Assertions.assertThat(result).isNotNull();
        final EO eoCall = TestProviderRootTest.createEo(call);
        final String asString = new EOToJSON()
                .setSerializationType(JSONSerializationType.EO)
                .toJSON(eoCall);
        final Call fromString = (Call)TestProviderRootTest.createEo(asString).get();
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
        //Assertions.assertThat(eo.get(Path.DELIMITER + PathElement.CALLS)).isNotNull();
        //Assertions.assertThat(eo.get(Path.DELIMITER + PathElement.CALLS, "0")).isNotNull();
        //Assertions.assertThat(eo.get(Path.DELIMITER + PathElement.CALLS, "0","duration")).isNotNull();
        //eo.set(0L, "/" + PathElement.CALLS, "0", "duration");
        //sleep(10L);
        //Long duration = (Long)eo.get("/" + PathElement.CALLS, "0", "duration");
        //Assertions.assertThat(duration).isGreaterThan(-1);
        new XpectEo().compareAsString(eo.getRoot());
        Assertions.assertThat(eo.getLog()).isEmpty();
        final String asString = new EOToJSON()
                .setSerializationType(JSONSerializationType.EO)
                .toJSON(eo.getRoot());
        final EO fromString = TestProviderRootTest.createEo(asString);
        fromString.setLogLevel(LogLevel.INFO);
        fromString.execute();
        //Assertions.assertThat(fromString.getLog()).contains("Already executed within");
        Assertions.assertThat(fromString.get(SOURCE)).isEqualTo(SIMPLE_RESULT);
        Long duration = (Long)fromString.get(PathElement.CALLS, "0", "duration");
        Assertions.assertThat(duration).isNotNull();
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
        EO eoFromJson = TestProviderRootTest.createEo(value);
        Assertions.assertThat(eoFromJson.get(TARGET ,"2")).isEqualTo(ARRAY_RESULT2);
        Assertions.assertThat(eoFromJson.get(PathElement.CALLS,"0")).isNotNull();
        Assertions.assertThat(eoFromJson.get(PathElement.CALLS,"0","targetPath")).isEqualTo(TARGET);

    }
}
