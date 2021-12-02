package org.fluentcodes.projects.elasticobjects.domain.test;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL0;


public class EoMapSetAnObjectTest {
    /**
     * Basic Wiki Example
     */
    @Test
    public void givenTest_whenSetAnObjectWithTestStringOnExistingModelMap_thenModelIsMap()  {
        final EO eo = ProviderConfigMaps.createEo();
        AnObject bt = new AnObject()
                .setMyString("value");
        eo.set(bt, "level0");
        assertThat(eo.get("level0/" + AnObject.MY_STRING)).isEqualTo("value");
        assertThat(eo.getEo("level0").getModelClass()).isEqualTo(AnObject.class);
    }


    @Test
    public void givenTest_whenSetAnObjectOnExistingModelMap_thenModelIsMap()  {
        final EO eo = ProviderConfigMaps.createEo();
        eo.setEmpty(S_LEVEL0);
        eo.set(new AnObject(), S_LEVEL0);
        assertThat(eo.getEo(S_LEVEL0).getModelClass()).isEqualTo(Map.class);
        assertThat(eo.getLog()).isEmpty();
    }
}
