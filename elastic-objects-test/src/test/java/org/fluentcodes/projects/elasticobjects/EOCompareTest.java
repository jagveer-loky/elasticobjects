package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

/**
 * New compare method tests
 * Created by Werner on 6.6.2018.
 */
public class EOCompareTest {

    @Test
    public void EO_first_test_EO_first_test__compare__equals()  {
        EO eo1 = ProviderRootTestScope.createEo();
        eo1.set(TEO_STATIC.S_STRING, "first");

        EO eo2 = ProviderRootTestScope.createEo();
        eo2.set(TEO_STATIC.S_STRING,"first");

        String diff = eo1.compare(eo2);
        Assertions.assertThat(diff).isEmpty();
    }

    @Test
    public void Map_myString_value_AnObject_myString_value__compare__equals()  {
        final Map map = new HashMap();
        map.put(AnObject.MY_STRING, "value");
        final EO eo1 = ProviderRootTestScope.createEo(map);

        final EO eo2 = ProviderRootTestScope.createEo(new AnObject().setMyString("value"));

        String diff = eo1.compare(eo2);
        Assertions.assertThat(diff).isEmpty();
    }

    @Test
    public void Map_myString_value_AnObject_myString_value2__compare__notquals()  {
        final Map map = new HashMap();
        map.put(AnObject.MY_STRING, "value");
        final EO eoMap = ProviderRootTestScope.createEo(map);

        final EO eoAnObject = ProviderRootTestScope.createEo(new AnObject().setMyString("value2"));

        String diff = eoMap.compare(eoAnObject);
        Assertions.assertThat(diff).isNotEmpty();
    }

    @Test
    public void Map_key0_test_Map_key1_test__compare__notEquals()  {
        EO eo1 = ProviderRootDevScope.createEo();
        eo1.set(TEO_STATIC.S_STRING, TEO_STATIC.S_KEY0);

        EO eo2 = ProviderRootDevScope.createEo();
        eo2.set(TEO_STATIC.S_STRING, TEO_STATIC.S_KEY1);

        String diff = eo1.compare(eo2);
        Assertions.assertThat(diff).isNotEmpty();
        Assertions.assertThat(diff).contains("null <> ");
        Assertions.assertThat(diff).contains("<> null");
    }

}
