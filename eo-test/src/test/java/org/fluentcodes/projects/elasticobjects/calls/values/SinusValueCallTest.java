package org.fluentcodes.projects.elasticobjects.calls.values;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.config.ModelConfig;
import org.fluentcodes.projects.elasticobjects.executor.CallExecutor;
import org.fluentcodes.projects.elasticobjects.executor.CallExecutorImpl;
import org.fluentcodes.projects.elasticobjects.test.TestProviderMapJsn;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;
import org.junit.Test;

/**
 * @author Werner Diwischek
 * @since 13.07.2020.
 */
public class SinusValueCallTest {
    private static final Logger LOG = LogManager.getLogger(SinusValueCallTest.class);
    private static final String SOURCE = "source";
    private static final String TARGET = "/target";

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
    public void createFromJsn_sameExecutionValues()  {
        final Call call = new SinusValueCall();
        EO eo = createSimple();
        Double result = (Double) call.execute(eo);
        final EO eoCall = TestProviderRootTest.createEo(call);
        final String asString = new EOToJSON()
                .setSerializationType(JSONSerializationType.EO)
                .toJSON(eoCall);
        final Call fromString = (Call)TestProviderRootTest.createEo(asString).get();
        Double fromResult = (Double) fromString.execute(eo);
        Assertions.assertThat(fromResult).isEqualTo(result);
    }

    @Test
    public void createFromJsnCallExecutor_sameExecutionValues()  {
        CallExecutor executor =  new CallExecutorImpl(new SinusValueCall());
        EO eo = createSimple();
        Double result = (Double) executor.execute(eo);
        final EO eoExecutor = TestProviderRootTest.createEo(executor);
        final String asString = new EOToJSON()
                .setSerializationType(JSONSerializationType.EO)
                .toJSON(eoExecutor);
        final CallExecutor fromString = (CallExecutor)TestProviderRootTest.createEo(asString).get();
        Double fromResult = (Double) fromString.execute(eo);
        Assertions.assertThat(fromResult).isEqualTo(result);
    }

    @Test
    public void givenCallExecutor()  {
        CallExecutor executor =  new CallExecutorImpl(new SinusValueCall());
        EO eo = createSimple();
        Double result = (Double) executor.execute(eo);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(eo.get()).isEqualTo(result);
    }

    @Test
    public void givenCallExecutorWithTarget()  {
        CallExecutor executor =  new CallExecutorImpl(new SinusValueCall()).setTargetPath(TARGET);
        EO eo = createSimple();
        Double result = (Double) executor.execute(eo);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(eo.get(TARGET)).isEqualTo(result);
    }

    @Test
    public void givenCallExecutorWithSourceAndTarget()  {
        CallExecutor executor =  new CallExecutorImpl(new SinusValueCall())
                .setTargetPath(TARGET)
                .setFilter("*");
        EO eo = createArray();
        Double result = (Double) executor.execute(eo);
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(eo.get(TARGET ,"2")).isEqualTo(result);
    }

    @Test
    public void givenEoWithSourceAndTarget()  {
        CallExecutor executor =  new CallExecutorImpl(new SinusValueCall())
                .setTargetPath(TARGET)
                .setSourcePath("/source")
                .setFilter("*");
        EO eo = createArray();
        eo.addCall(executor);
        eo.execute();
        Assertions.assertThat(eo.get(TARGET ,"2")).isNotNull();
        String value = new EOToJSON()
                .setSerializationType(JSONSerializationType.EO)
                .toJSON(eo.getRoot());
        //EoRoot eoExecutorList = TestProviderRootTest.createEo(eo.)
    }
}
