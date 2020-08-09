package org.fluentcodes.projects.elasticobjects.calls.files;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.json.JsonCallRead;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTest;

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
    public void givenTestProvider_whenFindModel_thenCreateOk()  {
        ConfigChecks.findModelAndCreateInstance(FileCallRead.class);
    }

    @Test
    public void whenCompareConfigurations_thenXpected()  {
        ConfigChecks.findModelAndCompare(JsonCallRead.class);
    }

    @Test
    public void givenCallWithSourceTxtCached_whenExecuteCall_thenReturnContent()  {
        final FileCallRead call = new FileCallRead().setConfigKey(FILE_SOURCE_CACHED_TXT);
        final String content = call.execute(ProviderRootTest.createEo());
        Assert.assertEquals(S_STRING, content);
    }

    @Test
    public void givenCallWithSourceTxt_whenExecuteCall_thenReturnContent()  {
        final FileCallRead call = new FileCallRead().setConfigKey(FILE_SOURCE_TXT);
        final String content = call.execute(ProviderRootTest.createEo());
        Assert.assertEquals(S_STRING, content);
    }

    @Test
    public void givenCallWithRoleGuestAndSourceTxt_whenExecuteCall_thenReturnContent()  {
        final Call call = new FileCallRead().setConfigKey(FILE_SOURCE_TXT);
        EO eo = ProviderRootTest.createEo();
        eo.setRoles(R_GUEST);
        String content = (String)call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(content).isEqualTo(S_STRING);
    }

    @Test
    public void givenEoWithRoleGuestAndSourceTxt_whenExecuteCall_thenContentIsOnTargetPath()  {
        final Call call = new FileCallRead().setConfigKey(FILE_SOURCE_TXT)
                .setTargetPath(F_TEST_STRING);
        EO eo = ProviderRootTest.createEo();
        eo.setRoles(R_GUEST);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(F_TEST_STRING)).isEqualTo(S_STRING);
    }

    @Test
    public void givenRoleAnonymAndSourceTxt_whenExecuteCall_thenThrowException()  {
        final FileCallRead call = new FileCallRead().setConfigKey(FILE_SOURCE_TXT);
        EO eo = ProviderRootTest.createEo();
        eo.setRoles(R_ANONYM);
        Assertions
                .assertThatThrownBy(()->{call.execute(eo);})
                .isInstanceOf(EoException.class)
                .hasMessageContaining("No permissions for roles");
    }

    @Test
    public void givenEoWithRoleAnonymAndSourceTxt_whenExecuteEo_thenHasLog()  {
        final FileCallRead call = new FileCallRead().setConfigKey(FILE_SOURCE_TXT);
        EO eo = ProviderRootTest.createEo();
        eo.addCall(call);
        eo.setRoles(R_ANONYM);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isNotEmpty();
        Assert.assertNull(INFO_NULL_FAILS, eo.get(F_CONTENT));
    }

    @Test
    public void givenEoWithChildAddedSourceTxt_whenExecuteEo_thenChildPathIsAdded() {
        final EO root = ProviderRootTest.createEo();
        root.set(S_STRING, S_LEVEL0, S_LEVEL1);
        final EO child = root.getEo(S_LEVEL0);

        final Call call = new FileCallRead(FILE_SOURCE_TXT)
                .setTargetPath(S_LEVEL2);
        Assert.assertEquals(S_LEVEL2, call.getTargetPath());
        child.addCall(call);

        child.execute();
        Assertions.assertThat(child.getLog()).isEmpty();
        Assertions.assertThat(root.get(S_LEVEL0, S_LEVEL2)).isEqualTo(S_STRING);
    }

    @Test
    public void givenJsonWithSource_ok() {
        final String json = "{" +
                "\"(List)content\":" +
                "{" +
                "\"(FileCallRead)call\":" +
                "{" +
                "\"configKey\":\"" + FILE_SOURCE_TXT + "\"" +
                "}" +
                "}" +
                "}";
        EO eo = ProviderRootTest.createEo(json);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assert.assertEquals(FileCallRead.class, eo.getEo("_calls", "0").getModelClass());
        Assert.assertEquals(FILE_SOURCE_TXT, eo.get("_calls", "0", "configKey"));
        eo.execute();
    }

}
