package org.fluentcodes.projects.elasticobjects.calls.json;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.calls.Call;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTest;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created 12.6.2018
 */
public class JsonCallReadTest {

    @Test
    public void whenCompareConfigurations_thenXpected()  {
        ConfigModelChecks.compare(JsonCallRead.class);
    }

    @Test
    public void givenModelClass_whenCreate_thenNoException()  {
        ConfigModelChecks.create(JsonCallRead.class);
    }

    @Test
    public void givenCallWithModuleConfig_whenExecuteCall_thenReturnContent()  {
        final JsonCallRead call = new JsonCallRead(J_MODULE_CONFIG_JSON);
        String content = call.execute(ProviderRootTest.createEo());
        Assertions.assertThat(content).contains("\"srcDir\": \"src/main/java\",\n");
    }

    @Test
    public void givenCallRoleGuestAndFileTmp_whenExecuteCall_thenThrowException()  {
        final JsonCallRead call = new JsonCallRead(FILE_TMP_JSON);
        EO eo = ProviderRootTest.createEo();
        eo.setRoles(R_GUEST);
        Assertions
                .assertThatThrownBy(()->{call.execute(eo);})
                .isInstanceOf(EoException.class)
                .hasMessageContaining("No permissions for roles");
    }

    @Test
    public void givenCallRoleAnonymAndFileSource_whenExecuteCall_thenThrowException()  {
        final JsonCallRead call = new JsonCallRead(FILE_SOURCE_JSON);
        EO eo = ProviderRootTest.createEo();
        eo.setRoles(R_ANONYM);
        Assertions
                .assertThatThrownBy(()->{call.execute(eo);})
                .isInstanceOf(EoException.class)
                .hasMessageContaining("No permissions for roles");
    }

    @Test
    public void givenCallRoleGuestAndFileSource_whenExecuteCall_ok()  {
        final JsonCallRead call = new JsonCallRead(FILE_SOURCE_JSON);
        EO eo = ProviderRootTest.createEo();
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
        final JsonCallRead call = new JsonCallRead(FILE_SOURCE_JSON);
        EO eo = ProviderRootTest.createEo();
        eo.setRoles(R_GUEST);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getEo(S0)).isNotNull();
        Assertions.assertThat(eo.getEo(S0).get(S_KEY1)).isEqualTo(S_VALUE11);
    }

    @Test
    public void givenEoBasicTestJsonWithModels_whenExecuteEo_thenBasicTestForRow()  {
        final Call call = new JsonCallRead()
                .setConfigKey("BasicTest.json")
                .setTargetPath("(List,BasicTest)level0");
        EO eo = ProviderRootTest.createEo();
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog())
                .isEmpty();
        Assertions.assertThat(eo.getEo("level0","0").get(F_TEST_STRING)).isEqualTo(S_STRING);
        Assertions.assertThat(eo.getEo("level0","0").getModelClass()).isEqualTo(BasicTest.class);
    }

}
