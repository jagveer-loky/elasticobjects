package org.fluentcodes.projects.elasticobjects.assets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_INTEGER;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_STRING;

/**
 * @author Werner Diwischek
 * @since 1.10.2018
 */

public class EoMapObjectAnObjectTest {
    private static final Logger LOG = LogManager.getLogger(EoMapObjectAnObjectTest.class);

    /**
     * Basic Wiki Example
    */
    @Test
    public void givenTest_whenMapAnObject_thenObjectIsMapType()  {
        final EO eo = ProviderRootTestScope.createEo();
        AnObject bt = new AnObject()
                .setMyString("value");
        eo.mapObject(bt);
        assertThat(eo.get(AnObject.MY_STRING)).isEqualTo("value");
        assertThat(eo.getModelClass()).isEqualTo(Map.class);
    }


    @Test
    public void givenTestString_whenMapAnObject_thenHasLog()  {
        final EO eo = ProviderRootTestScope.createEo(S_STRING);
        eo.mapObject(new AnObject());
        assertThat(eo.getLog()).contains("Could not map container to scalar");
        assertThat(eo.get()).isEqualTo(S_STRING);
    }

    @Test
    public void AnObjectString__mapJsonInt__thenValuesSet()  {
        final EO eo = TestProviderAnObjectJson.STRING.createBtEo();
        eo.mapObject(TestProviderAnObjectJson.INT.createMapTest());
        assertThat(eo.getModelClass()).isEqualTo(AnObject.class);
        assertThat(eo.get(AnObject.MY_STRING)).isEqualTo(S_STRING);
        assertThat(eo.get(AnObject.MY_INT)).isEqualTo(S_INTEGER);
    }

}


