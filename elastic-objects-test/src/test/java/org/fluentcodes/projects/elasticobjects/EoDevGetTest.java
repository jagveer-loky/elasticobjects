package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_LEVEL0;

public class EoDevGetTest {

    @Test
    public void __get_path__exception_thrown()  {
        EO eo = EoRoot.of(ProviderRootDevScope.CONFIG_MAPS_DEV);
        Assertions.assertThatThrownBy(()->{eo.get(S_LEVEL0);})
                .hasMessage("No value add for fieldName=level0");
    }

    @Test
    public void __map_put_key_value__eo_get_key_value()  {
        final EO eo = EoRoot.of(ProviderRootDevScope.CONFIG_MAPS_DEV);
        ((Map) eo.get()).put("key", "value");
        Assertions.assertThat(eo.get("key")).isEqualTo("value");
    }

    @Test
    public void eo_set_key_value__map_put_key_value2___eo_get_key_value2()  {
        final EO eo = EoRoot.of(ProviderRootDevScope.CONFIG_MAPS_DEV);
        eo.set("value","key");
        ((Map)eo.get()).put("key", "value2");
        Assertions.assertThat(eo.get("key")).isEqualTo("value2");
    }
}

