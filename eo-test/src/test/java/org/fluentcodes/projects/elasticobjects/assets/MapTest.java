package org.fluentcodes.projects.elasticobjects.assets;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.fileprovider.ProviderMapJson;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.F_TEST_STRING;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_STRING;

public class MapTest {
    @Test
    public void testMapSmall()  {
        Map map = ProviderMapJson.SMALL.createMap();
        Assertions.assertThat(map.get(F_TEST_STRING)).isEqualTo(S_STRING);
    }
}
