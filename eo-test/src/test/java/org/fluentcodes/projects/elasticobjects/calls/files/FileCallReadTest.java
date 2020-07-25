package org.fluentcodes.projects.elasticobjects.calls.files;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.config.ModelConfig;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_CONTENT;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 11.10.2016.
 */
public class FileCallReadTest {
    @Test
    public void testFindModelCall()  {
        ModelConfig model = TestProviderRootTest.EO_CONFIGS.findModel("FileCallRead");
        Assertions.assertThat(model).isNotNull();
        model.resolve();
        FileCallRead call =  (FileCallRead)model.create();
        Assertions.assertThat(call).isNotNull();
    }

    @Test
    public void testFindModelConfig()  {
        ModelConfig config = TestProviderRootTest.EO_CONFIGS.findModel("FileConfig");
        Assertions.assertThat(config).isNotNull();
        config.resolve();
    }

    @Test
    public void givenCallWithSourceTxtCached_whenExecuteCall_thenReturnContent()  {
        final FileCallRead call = new FileCallRead().setConfigKey(FILE_SOURCE_CACHED_TXT);
        final String content = call.execute(TestProviderRootTest.createEo());
        Assert.assertEquals(S_STRING, content);
    }

    @Test
    public void givenCallWithSourceTxt_whenExecuteCall_thenReturnContent()  {
        final FileCallRead call = new FileCallRead().setConfigKey(FILE_SOURCE_TXT);
        final String content = call.execute(TestProviderRootTest.createEo());
        Assert.assertEquals(S_STRING, content);
    }

    @Test
    public void givenCallWithRoleGuestAndSourceTxt_whenExecuteCall_thenReturnContent()  {
        final Call call = new FileCallRead().setConfigKey(FILE_SOURCE_TXT);
        EO eo = TestProviderRootTest.createEo();
        eo.setRoles(R_GUEST);
        String content = (String)call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(content).isEqualTo(S_STRING);
    }

    @Test
    public void givenEoWithRoleGuestAndSourceTxt_whenExecuteCall_thenContentIsOnTargetPath()  {
        final Call call = new FileCallRead().setConfigKey(FILE_SOURCE_TXT)
                .setTargetPath(F_TEST_STRING);
        EO eo = TestProviderRootTest.createEo();
        eo.setRoles(R_GUEST);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(F_TEST_STRING)).isEqualTo(S_STRING);
    }

    @Test
    public void givenRoleAnonymAndSourceTxt_whenExecuteCall_thenThrowException()  {
        final FileCallRead call = new FileCallRead().setConfigKey(FILE_SOURCE_TXT);
        EO eo = TestProviderRootTest.createEo();
        eo.setRoles(R_ANONYM);
        Assertions
                .assertThatThrownBy(()->{call.execute(eo);})
                .isInstanceOf(EoException.class)
                .hasMessageContaining("No permissions for roles");
    }

    @Test
    public void givenEoWithRoleAnonymAndSourceTxt_whenExecuteEo_thenHasLog()  {
        final FileCallRead call = new FileCallRead().setConfigKey(FILE_SOURCE_TXT);
        EO eo = TestProviderRootTest.createEo();
        eo.addCall(call);
        eo.setRoles(R_ANONYM);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isNotEmpty();
        Assert.assertNull(INFO_NULL_FAILS, eo.get(F_CONTENT));
    }

}
