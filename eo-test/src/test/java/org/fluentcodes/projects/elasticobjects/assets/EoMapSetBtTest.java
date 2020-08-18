package org.fluentcodes.projects.elasticobjects.assets;

import static org.assertj.core.api.Assertions.assertThat;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_LEVEL0;


public class EoMapSetBtTest {
    /**
     * Basic Wiki Example
     */
    @Test
    public void givenTest_whenSetBTWithTestStringOnExistingModelMap_thenModelIsMap()  {
        final EO eo = ProviderRootTestScope.createEo();
        BasicTest bt = new BasicTest()
                .setTestString("value");
        eo.set(bt, "level0");
        assertThat(eo.get("level0/testString")).isEqualTo("value");
        assertThat(eo.getEo("level0").getModelClass()).isEqualTo(BasicTest.class);
    }


    @Test
    public void givenTest_whenSetBTOnExistingModelMap_thenModelIsMap()  {
        final EO eo = ProviderRootTestScope.createEo();
        eo.setEmpty(S_LEVEL0);
        eo.set(new BasicTest(), S_LEVEL0);
        assertThat(eo.getEo(S_LEVEL0).getModelClass()).isEqualTo(Map.class);
        assertThat(eo.getLog()).isEmpty();
    }
}
