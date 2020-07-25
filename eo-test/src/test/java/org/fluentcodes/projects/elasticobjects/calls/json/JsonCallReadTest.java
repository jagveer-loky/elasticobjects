package org.fluentcodes.projects.elasticobjects.calls.json;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.files.FileCallRead;
import org.fluentcodes.projects.elasticobjects.calls.json.JsonCallRead;
import org.fluentcodes.projects.elasticobjects.calls.json.JsonCallWrite;

import org.fluentcodes.projects.elasticobjects.config.ModelConfig;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created 12.6.2018
 */
public class JsonCallReadTest {
    @Test
    public void testFindModelCall()  {
        ModelConfig model = TestProviderRootTest.EO_CONFIGS.findModel("JsonCallRead");
        Assertions.assertThat(model).isNotNull();
        model.resolve();
        FileCallRead call =  (FileCallRead)model.create();
        Assertions.assertThat(call).isNotNull();
    }



    @Test
    public void givenCallWithModuleConfig_whenExecuteCall_thenReturnContent()  {
        final JsonCallRead call = new JsonCallRead(J_MODULE_CONFIG_JSON);
        String content = call.execute(TestProviderRootTest.createEo());
        Assertions.assertThat(content).isEqualTo("{\n" +
                "  \"sourceDirs\": {\n" +
                "    \"main\": {\n" +
                "      \"srcDir\": \"src/main/java\",\n" +
                "      \"static\": \"EO_STATIC_MAIN\"\n" +
                "    },\n" +
                "    \"test\": {\n" +
                "      \"srcDir\": \"src/test/java\",\n" +
                "      \"static\": \"EO_STATIC_TEST\"\n" +
                "    },\n" +
                "    \"build\": {\n" +
                "      \"srcDir\": \"src/build/java\",\n" +
                "      \"static\": \"EO_STATIC_BUILD\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"short\": \"EO\",\n" +
                "  \"dir\": \"elastic-objects\",\n" +
                "  \"package\": \"org.fluentcodes.projects.elasticobjects\",\n" +
                "  \"configs\": [\n" +
                "    \"Field\",\n" +
                "    \"Model\",\n" +
                "    \"Role\",\n" +
                "    \"User\",\n" +
                "    \"Config\",\n" +
                "    \"Host\",\n" +
                "    \"File\",\n" +
                "    \"Json\",\n" +
                "    \"Template\"\n" +
                "  ]\n}");
    }

    @Test
    public void givenCallRoleGuestAndFileTmp_whenExecuteCall_thenThrowException()  {
        final JsonCallRead call = new JsonCallRead(FILE_TMP_JSON);
        EO eo = TestProviderRootTest.createEo();
        eo.setRoles(R_GUEST);
        Assertions
                .assertThatThrownBy(()->{call.execute(eo);})
                .isInstanceOf(EoException.class)
                .hasMessageContaining("No permissions for roles");
    }

    @Test
    public void givenCallRoleAnonymAndFileSource_whenExecuteCall_thenThrowException()  {
        final JsonCallRead call = new JsonCallRead(FILE_SOURCE_JSON);
        EO eo = TestProviderRootTest.createEo();
        eo.setRoles(R_ANONYM);
        Assertions
                .assertThatThrownBy(()->{call.execute(eo);})
                .isInstanceOf(EoException.class)
                .hasMessageContaining("No permissions for roles");
    }

    @Test
    public void givenCallRoleGuestAndFileSource_whenExecuteCall_ok()  {
        final JsonCallRead call = new JsonCallRead(FILE_SOURCE_JSON);
        EO eo = TestProviderRootTest.createEo();
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
    public void givenEoRoleGuestAndFileSource_whenExecuteEo_thenValuesAreMapped()  {
        final JsonCallRead call = new JsonCallRead(FILE_SOURCE_JSON);
        EO eo = TestProviderRootTest.createEo();
        eo.setRoles(R_GUEST);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getEo(S0)).isNotNull();
        Assertions.assertThat(eo.getEo(S0).get(S_KEY1)).isEqualTo(S_VALUE11);
    }

}
