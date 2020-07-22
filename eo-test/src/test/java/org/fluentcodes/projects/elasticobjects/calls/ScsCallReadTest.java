package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.executor.CallExecutorResource;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;

import org.junit.Test;

import java.util.List;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 28.10.2018.
 */
public class ScsCallReadTest {
    private static final Logger LOG = LogManager.getLogger(ScsCallReadTest.class);


    @Test
    public void sourceCsvDirect_returnsList()  {
        final Call call = new ScsCallRead()
                .setConfigKey(CS_SOURCE_CSV)
                .resolve(TestProviderRootTest.EO_CONFIGS);
        List value = ((ScsCallRead)call).execute();
        Assertions.assertThat(value).isNotEmpty();
        Assertions.assertThat(value.size()).isEqualTo(2);
        Assertions.assertThat(((List)value.get(0)).size()).isEqualTo(2);
    }



    @Test
    public void readSourceCsvAnonym_hasLog()  {
        final Call call = new ScsCallRead()
                .setConfigKey(CS_SOURCE_CSV)
                .setTargetPath("target");
        EO eo = TestProviderRootTest.createEo();
        eo.setRoles(R_ANONYM);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isNotEmpty();
        Assertions.assertThat(eo.isEmpty()).isTrue();
    }

    @Test
    public void readSourceCsvGuest_hasValues()  {
        final Call call = new ScsCallRead().setConfigKey(CS_SOURCE_CSV).setTargetPath("target");
        EO eo = TestProviderRootTest.createEo();
        eo.setRoles(R_GUEST);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).doesNotContain("ERROR");
        Assertions.assertThat(eo.get("target/0/0")).isEqualTo("value11");
    }
}
