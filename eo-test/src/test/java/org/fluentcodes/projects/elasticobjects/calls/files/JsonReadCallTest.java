package org.fluentcodes.projects.elasticobjects.calls.files;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.calls.Call;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderListJson;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Ignore;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created 12.6.2018
 */
public class JsonReadCallTest {
    private static String LIST_SIMPLE_CONFIG = ProviderListJson.LIST_SIMPLE.getConfigKey();
    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(JsonReadCall.class);
    }

    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(JsonReadCall.class);
    }

    @Ignore
    @Test
    public void givenCallWithModuleConfig_whenExecuteCall_thenReturnContent()  {
        final JsonReadCall call = new JsonReadCall(J_MODULE_CONFIG_JSON);
        String content = call.execute(ProviderRootTestScope.createEo());
        Assertions.assertThat(content).contains("\"srcDir\": \"src/main/java\",\n");
    }

    @Test
    public void givenCallRoleGuestAndFileTmp_whenExecuteCall_thenThrowException()  {
        final JsonReadCall call = new JsonReadCall(JsonWriteCallTest.FILE_TMP_JSON);
        EO eo = ProviderRootTestScope.createEo();
        eo.setRoles(R_GUEST);
        Assertions
                .assertThatThrownBy(()->{call.execute(eo);})
                .isInstanceOf(EoException.class)
                .hasMessageContaining("No permissions for roles");
    }

    @Test
    public void givenCallRoleAnonymAndFileSource_whenExecuteCall_thenThrowException()  {
        final JsonReadCall call = new JsonReadCall(LIST_SIMPLE_CONFIG);
        EO eo = ProviderRootTestScope.createEo();
        eo.setRoles(R_ANONYM);
        Assertions
                .assertThatThrownBy(()->{call.execute(eo);})
                .isInstanceOf(EoException.class)
                .hasMessageContaining("No permissions for roles");
    }

    @Test
    public void givenCallRoleGuestAndFileSource_whenExecuteCall_ok()  {
        final JsonReadCall call = new JsonReadCall(LIST_SIMPLE_CONFIG);
        EO eo = ProviderRootTestScope.createEo();
        eo.setRoles(R_GUEST);
        String content = call.execute(eo);
        Assertions.assertThat(content).isEqualTo("[\n" +
                "  {\n" +
                "    \"key1\": \"value11\",\n" +
                "    \"key2\": \"value12\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"key1\": \"value21\",\n" +
                "    \"key2\": \"value22\"\n" +
                "  }\n" +
                "]");
        //Assert.assertNotNull(INFO_NOT_NULL_FAILS, eo.getEo(S0));
        //Assert.assertEquals(INFO_COMPARE_FAILS, S_VALUE11, eo.getEo(S0).get(S_KEY1));
    }

    @Test
    public void givenEoRoleGuestAndFileSource_whenExecute_thenValuesAreMapped()  {
        final JsonReadCall call = new JsonReadCall(LIST_SIMPLE_CONFIG);
        EO eo = ProviderRootTestScope.createEo();
        eo.setRoles(R_GUEST);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getEo(S0)).isNotNull();
        Assertions.assertThat(eo.getEo(S0).get(S_KEY1)).isEqualTo(S_VALUE11);
    }

    @Test
    public void givenEoBasicTestJsonWithModels_whenExecuteEo_thenBasicTestForRow()  {
        final Call call = new JsonReadCall()
                .setConfigKey("BasicTest.json")
                .setTargetPath("(List,BasicTest)level0");
        EO eo = ProviderRootTestScope.createEo();
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog())
                .isEmpty();
        Assertions.assertThat(eo.getEo("level0","0").get(BasicTest.TEST_STRING)).isEqualTo(S_STRING);
        Assertions.assertThat(eo.getEo("level0","0").getModelClass()).isEqualTo(BasicTest.class);
    }

}
