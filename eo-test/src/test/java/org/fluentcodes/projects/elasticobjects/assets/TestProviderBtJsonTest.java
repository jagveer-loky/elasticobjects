package org.fluentcodes.projects.elasticobjects.assets;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Map;

public class TestProviderBtJsonTest {
    @Test
    public void testHealth() {
        for (TestProviderBtJson json: TestProviderBtJson.values()) {
            Assertions.assertThat(json.content()).isNotEmpty();
            Assertions.assertThat(json.createBt()).isNotNull();
            Assertions.assertThat(json.createMapTest()).isNotNull();
        }
    }

    @Test
    public void testString() {
        Map map = TestProviderBtJson.STRING.createMapDev();
        Assertions.assertThat(map.get("testString")).isEqualTo("test");
    }
}
