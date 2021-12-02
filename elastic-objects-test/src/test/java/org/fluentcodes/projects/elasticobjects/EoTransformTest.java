package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.MY_STRING;

/**
 * Transform examples
 * Created by Werner on 11.11.2020
 */
public class EoTransformTest {

    @Test
    public void eo_Map_empty__mapObject_AnObject_myString_value__Map_naturalId_value()  {
        AnObject anObject = new AnObject().setMyString("value");
        final EO eo = ProviderConfigMaps.createEo();
        eo.mapObject(anObject);
        Assertions.assertThat(eo.get().getClass()).isEqualTo(LinkedHashMap.class);
        Assertions.assertThat(((Map)eo.get()).get("myString")).isEqualTo("value");
    }

    @Test
    public void eo_AnObject_empty__mapObject_Map_myString_value__AnObject_naturalId_value()  {
        final EO eo = ProviderConfigMaps.createEo(AnObject.class);
        Map map = new HashMap();
        map.put(MY_STRING, "value");
        eo.mapObject(map);
        Assertions.assertThat(eo.get().getClass()).isEqualTo(AnObject.class);
        Assertions.assertThat(((AnObject)eo.get()).getMyString()).isEqualTo("value");
    }

    @Test
    public void eo_AnObject_empty__mapObject_Map_unknown_value__exception()  {
        final EO eo = ProviderConfigMaps.createEo(AnObject.class);
        Map map = new HashMap();
        map.put("unknown", "value");
        Assertions.assertThatThrownBy(()->{eo.mapObject(map);})
        .isInstanceOf(EoException.class);
    }
}
