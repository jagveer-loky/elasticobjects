package org.fluentcodes.projects.elasticobjects.assets;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Map;

public class TestProviderAnObjectJsonTest {
    @Test
    public void testHealth() {
        for (TestProviderAnObjectJson json: TestProviderAnObjectJson.values()) {
            Assertions.assertThat(json.content()).isNotEmpty();
            Assertions.assertThat(json.createBt()).isNotNull();
            Assertions.assertThat(json.createMapTest()).isNotNull();
        }
    }

    @Test
    public void testString() {
        Map map = TestProviderAnObjectJson.STRING.createMapDev();
        Assertions.assertThat(map.get(AnObject.MY_STRING)).isEqualTo("test");
    }
}
