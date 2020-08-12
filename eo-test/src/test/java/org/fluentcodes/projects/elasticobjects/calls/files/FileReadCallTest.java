package org.fluentcodes.projects.elasticobjects.calls.files;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;

import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 11.10.2016.
 */
public class FileReadCallTest {
    @Test
    public void givenModelClass_whenCreate_thenNoException()  {
        ConfigModelChecks.create(FileReadCall.class);
    }

    @Test
    public void whenCompareConfigurations_thenXpected()  {
        ConfigModelChecks.compare(FileReadCall.class);
    }

    @Test
    public void givenCallWithSourceTxtCached_whenExecuteCall_thenReturnContent()  {
        final FileReadCall call = new FileReadCall().setConfigKey(FILE_SOURCE_CACHED_TXT);
        final String content = call.execute(ProviderRootTestScope.createEo());
        Assert.assertEquals(S_STRING, content);
    }

    @Test
    public void givenCallWithSourceTxt_whenExecuteCall_thenReturnContent()  {
        final FileReadCall call = new FileReadCall().setConfigKey(FILE_SOURCE_TXT);
        final String content = call.execute(ProviderRootTestScope.createEo());
        Assert.assertEquals(S_STRING, content);
    }

    @Test
    public void givenCallWithRoleGuestAndSourceTxt_whenExecuteCall_thenReturnContent()  {
        final Call call = new FileReadCall().setConfigKey(FILE_SOURCE_TXT);
        EO eo = ProviderRootTestScope.createEo();
        eo.setRoles(R_GUEST);
        String content = (String)call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(content).isEqualTo(S_STRING);
    }

    @Test
    public void givenEoWithRoleGuestAndSourceTxt_whenExecuteCall_thenContentIsOnTargetPath()  {
        final Call call = new FileReadCall().setConfigKey(FILE_SOURCE_TXT)
                .setTargetPath(F_TEST_STRING);
        EO eo = ProviderRootTestScope.createEo();
        eo.setRoles(R_GUEST);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(F_TEST_STRING)).isEqualTo(S_STRING);
    }

    @Test
    public void givenRoleAnonymAndSourceTxt_whenExecuteCall_thenThrowException()  {
        final FileReadCall call = new FileReadCall().setConfigKey(FILE_SOURCE_TXT);
        EO eo = ProviderRootTestScope.createEo();
        eo.setRoles(R_ANONYM);
        Assertions
                .assertThatThrownBy(()->{call.execute(eo);})
                .isInstanceOf(EoException.class)
                .hasMessageContaining("No permissions for roles");
    }

    @Test
    public void givenEoWithRoleAnonymAndSourceTxt_whenExecuteEo_thenHasLog()  {
        final FileReadCall call = new FileReadCall().setConfigKey(FILE_SOURCE_TXT);
        EO eo = ProviderRootTestScope.createEo();
        eo.addCall(call);
        eo.setRoles(R_ANONYM);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isNotEmpty();
    }

    @Test
    public void givenEoWithChildAddedSourceTxt_whenExecuteEo_thenChildPathIsAdded() {
        final EO root = ProviderRootTestScope.createEo();
        root.set(S_STRING, S_LEVEL0, S_LEVEL1);
        final EO child = root.getEo(S_LEVEL0);

        final Call call = new FileReadCall(FILE_SOURCE_TXT)
                .setTargetPath(S_LEVEL2);
        Assert.assertEquals(S_LEVEL2, call.getTargetPath());
        child.addCall(call);

        child.execute();
        Assertions.assertThat(child.getLog()).isEmpty();
        Assertions.assertThat(root.get(S_LEVEL2)).isEqualTo(S_STRING);
    }

    @Test
    public void givenJsonWithSource_ok() {
        final String json = "{" +
                "\"(List)content\":" +
                "{" +
                "\"(FileReadCall)call\":" +
                "{" +
                "\"configKey\":\"" + FILE_SOURCE_TXT + "\"" +
                "}" +
                "}" +
                "}";
        EO eo = ProviderRootTestScope.createEo(json);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assert.assertEquals(FileReadCall.class, eo.getEo("_calls", "0").getModelClass());
        Assert.assertEquals(FILE_SOURCE_TXT, eo.get("_calls", "0", "configKey"));
        eo.execute();
    }

}
