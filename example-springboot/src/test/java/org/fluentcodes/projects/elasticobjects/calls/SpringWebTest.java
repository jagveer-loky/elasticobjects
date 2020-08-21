package org.fluentcodes.projects.elasticobjects.calls;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.JSONToEO;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceCall;
import org.fluentcodes.projects.elasticobjects.calls.testitemproviders.TestProviderTemplateContent;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderJsonCalls;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.tools.xpect.XpectString;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringWebTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void testSinusValueCall() {
        String json = ProviderJsonCalls.CALL_SINUS_ARRAY.content();
        Assertions.assertThat(json).isNotEmpty();
        String url = "http://localhost:" + port + "/eo";
        ResponseEntity<String> result = restTemplate.postForEntity(url, json, String.class);
        String body = result.getBody();
        Assertions.assertThat(body).isNotEmpty();
    }

    @Test
    public void testConfigKeyCallModelConfig() {
        String json = ProviderJsonCalls.CONFIG_KEYS_CALL_MODEL_CONFIG.content();
        String url = "http://localhost:" + port + "/eo";
        ResponseEntity<String> result = restTemplate.postForEntity(url, json, String.class);
        String body = result.getBody();
        Assertions.assertThat(body).isNotEmpty();
        EO eo = ProviderRootTestScope.createEo(body);
        Assertions.assertThat((List)eo.get("keys")).isNotEmpty();
    }

    @Test
    public void testTemplateConfigKeyCall() {
        String test = TestProviderTemplateContent.CONFIG_KEYS_CALL.content();
        String url = "http://localhost:" + port + "/eo/template";
        ResponseEntity<String> result = restTemplate.postForEntity(url, test, String.class);
        String body = result.getBody();
        Assertions.assertThat(body).isNotEmpty();
        new XpectString().compareAsString(body);
    }

    @Test
    public void testConfigKeysCall_LinkListHtml_whenConfigTypeFieldConfig_thenFieldConfigsUsedForLinkList() {
        EO eo = ProviderRootTestScope.createEo();
        eo.set("FieldConfig", "configType");
        eo.addCall(new TemplateResourceCall("ConfigKeysCall_LinkListHtml"));
        String post = new EOToJSON().toJSON(eo);
        String url = "http://localhost:" + port + "/eo";
        ResponseEntity<String> result = restTemplate.postForEntity(url, post, String.class);
        String body = result.getBody();
        Assertions.assertThat(body).isNotEmpty();
        EO resultEo = ProviderRootTestScope.createEo(body);
        Assertions.assertThat((String)resultEo.get("_template")).isNotEmpty();
        new XpectString().compareAsString((String)resultEo.get("_template"));
    }

    @Test
    public void testGetConfigs_whenFieldConfig_thenFieldConfigsUsedForLinkList() {
        String url = "http://localhost:" + port + "/config/FieldConfig";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String parsedContent = response.getBody();
        Assertions.assertThat(parsedContent).isNotEmpty();
        new XpectString().compareAsString(parsedContent);
    }
}
