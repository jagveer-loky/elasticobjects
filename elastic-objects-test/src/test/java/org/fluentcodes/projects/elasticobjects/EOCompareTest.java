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
        EO adapter = ProviderRootTestScope.createEo();
        adapter.set(TEO_STATIC.S_STRING, "first");

        EO other = ProviderRootTestScope.createEo();
        other.set(TEO_STATIC.S_STRING,"first");

        StringBuilder builder = new StringBuilder();
        adapter.compare(builder, other);
        Assert.assertEquals("", builder.toString());
    }

    @Test
    public void Map_myString_value_AnObject_myString_value__compare__equals()  {
        final Map map = new HashMap();
        map.put(AnObject.MY_STRING, "value");
        final EO eo = ProviderRootTestScope.createEo();
        eo.mapObject(map);

        AnObject anObject = new AnObject();
        anObject.setMyString("value");
        final EO eo2 = ProviderRootTestScope.createEo();
        eo2.mapObject(anObject);

        StringBuilder diff = new StringBuilder();
        eo.compare(diff, eo2);
        Assert.assertEquals("", diff.toString());
    }

    @Test
    public void Map_myString_value_AnObject_myString_value2__compare__notquals()  {
        final Map map = new HashMap();
        map.put(AnObject.MY_STRING, "value");
        final EO eo = ProviderRootTestScope.createEo();
        eo.mapObject(map);

        AnObject anObject = new AnObject();
        anObject.setMyString("value2");
        final EO eo2 = ProviderRootTestScope.createEo();
        eo2.mapObject(anObject);

        StringBuilder diff = new StringBuilder();
        eo.compare(diff, eo2);
        Assertions.assertThat(diff).isNotEmpty();
    }

    @Test
    public void Map_key0_test_Map_key1_test__compare__notEquals()  {
        EO adapter = ProviderRootDevScope.createEo();
        adapter.set(TEO_STATIC.S_STRING, TEO_STATIC.S_KEY0);

        EO other = ProviderRootDevScope.createEo();
        other.set(TEO_STATIC.S_STRING, TEO_STATIC.S_KEY1);

        StringBuilder builder = new StringBuilder();
        adapter.compare(builder, other);
        Assertions.assertThat(builder.toString()).isNotEmpty();
        Assertions.assertThat(builder.toString()).contains("null <> ");
        Assertions.assertThat(builder.toString()).contains("<> null");
    }

}
