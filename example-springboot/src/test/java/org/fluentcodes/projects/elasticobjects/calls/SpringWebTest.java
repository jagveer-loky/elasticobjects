package org.fluentcodes.projects.elasticobjects.calls;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderJsonCalls;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.tools.xpect.XpectString;
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
    public void givenGetContent_whenImpressumHtml_thenContentBodyIsLoaded() {
        String url = "http://localhost:" + port + "/Impressum.html";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String parsedContent = response.getBody();
        Assertions.assertThat(parsedContent).isNotEmpty();
        new XpectString().compareAsString(parsedContent);
    }

    @Test
    public void givenGetContent_whenHomeHtml_thenContentBodyIsLoaded() {
        String url = "http://localhost:" + port + "/Home.html";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String parsedContent = response.getBody();
        Assertions.assertThat(parsedContent).isNotEmpty();
        new XpectString().compareAsString(parsedContent);
    }

    @Test
    public void givenGetContent_whenEo_thenContentBodyIsLoaded() {
        String url = "http://localhost:" + port + "/docs/Eo";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String parsedContent = response.getBody();
        Assertions.assertThat(parsedContent).isNotEmpty();
        new XpectString().compareAsString(parsedContent);
    }
}
