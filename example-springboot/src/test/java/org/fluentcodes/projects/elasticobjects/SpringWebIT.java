package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
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
public class SpringWebIT {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testExampleListDouble() {
        String json = "{\"(List,Double)items\":{\"0\":1,\"1\":2}}";
        Assertions.assertThat(json).isNotEmpty();
        String url = "http://localhost:" + port + "/eo";
        ResponseEntity<String> result = restTemplate.postForEntity(url, json, String.class);
        String body = result.getBody();
        new XpectString().compareAsString(body);
    }
    @Test
    public void testSinusValueCall() {
        String json = ProviderJsonCalls.CALL_SINUS_ARRAY.content();
        Assertions.assertThat(json).isNotEmpty();
        String url = "http://localhost:" + port + "/eo";
        ResponseEntity<String> result = restTemplate.postForEntity(url, json, String.class);
        String body = result.getBody();
        new XpectString().compareAsString(body);
    }
    // TODO so why in mvn does not work
    @Ignore
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
    // TODO so why in mvn does not work
    @Ignore
    @Test
    public void givenGetContent_whenImpressumHtml_thenContentBodyIsLoaded() {
        String url = "http://localhost:" + port + "/Impressum.html";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String parsedContent = response.getBody();
        Assertions.assertThat(parsedContent).isNotEmpty();
        new XpectString().compareAsString(parsedContent);
    }
    // TODO so why in mvn does not work
    @Ignore
    @Test
    public void givenGetContent_whenHomeHtml_thenContentBodyIsLoaded() {
        String url = "http://localhost:" + port + "/Home.html";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String parsedContent = response.getBody();
        Assertions.assertThat(parsedContent).isNotEmpty();
        new XpectString().compareAsString(parsedContent);
    }
    // TODO so why in mvn does not work
    @Ignore
    @Test
    public void givenGetExamplesStart_whenEo_thenContentBodyIsLoaded() {
        String url = "http://localhost:" + port + "/examples/ExamplesStart.html";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String parsedContent = response.getBody();
        Assertions.assertThat(parsedContent).isNotEmpty();
        new XpectString().compareAsString(parsedContent);
    }


}
