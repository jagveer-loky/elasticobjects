package org.fluentcodes.projects.elasticobjects.calls.values;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.executor.CallExecutor;
import org.fluentcodes.projects.elasticobjects.executor.CallExecutorImpl;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;

import org.junit.Test;

import java.util.ArrayList;

/**
 * @author Werner Diwischek
 * @since 13.07.2020.
 */
public class SinusValueCallTest {
    private static final Logger LOG = LogManager.getLogger(SinusValueCallTest.class);
    private static final String SOURCE = "source";
    private static final String TARGET = "/target";

    private static final EO createSimple() {
        return TestProviderRootTest.createEo()
                .set(2.1, SOURCE);
    }

    private static final EO createArray() {
        EO eo = TestProviderRootTest.createEo().set(new ArrayList(), "source");
        eo.set(1.0D,"0");
        eo.set(2.0D, "1");
        eo.set(3.0D, "2");
        return eo;
    }

    @Test
    public void direct_ok()  {
        final CallImpl call = new SinusValueCall();
        Double result = (Double) call.execute(createSimple());
        Assertions.assertThat(result).isNotNull();
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
    }
}
