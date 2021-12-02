package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * New compare method tests
 * Created by Werner on 6.6.2018.
 */
public class EOCompareTest {

    @Test
    public void EO_first_test_EO_first_test__compare__equals()  {
        EO eo1 = ProviderConfigMaps.createEo();
        eo1.set(EoTestStatic.S_STRING, "first");

        EO eo2 = ProviderConfigMaps.createEo();
        eo2.set(EoTestStatic.S_STRING,"first");

        String diff = eo1.compare(eo2);
        Assertions.assertThat(diff).isEmpty();
    }

    @Test
    public void Map_myString_value_AnObject_myString_value__compare__equals()  {
        final Map map = new HashMap();
        map.put(AnObject.MY_STRING, "value");
        final EO eo1 = ProviderConfigMaps.createEo(map);

        final EO eo2 = ProviderConfigMaps.createEo(new AnObject().setMyString("value"));

        String diff = eo1.compare(eo2);
        Assertions.assertThat(diff).isEmpty();
    }

    @Test
    public void Map_myString_value_AnObject_myString_value2__compare__notquals()  {
        final Map map = new HashMap();
        map.put(AnObject.MY_STRING, "value");
        final EO eoMap = ProviderConfigMaps.createEo(map);

        final EO eoAnObject = ProviderConfigMaps.createEo(new AnObject().setMyString("value2"));

        String diff = eoMap.compare(eoAnObject);
        Assertions.assertThat(diff).isNotEmpty();
    }

    @Test
    public void Map_key0_test_Map_key1_test__compare__notEquals()  {
        EO eo1 = ProviderConfigMaps.createEoDev();
        eo1.set(EoTestStatic.S_STRING, EoTestStatic.S_KEY0);

        EO eo2 = ProviderConfigMaps.createEoDev();
        eo2.set(EoTestStatic.S_STRING, EoTestStatic.S_KEY1);

        String diff = eo1.compare(eo2);
        Assertions.assertThat(diff).isNotEmpty()
                .contains("null <> ")
                .contains("<> null");
    }

}
