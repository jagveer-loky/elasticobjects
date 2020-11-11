package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
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
public class EoMergeTest {

    @Test
    public void AnObject_myString_value_Map_myNaturalId_id__merge__extended()  {
        AnObject anObject = new AnObject().setMyString("value");
        final EO eo = ProviderRootTestScope.createEo(anObject);

        final Map map = new HashMap();
        map.put(AnObject.NATURAL_ID, "id");
        eo.mapObject(map);
        Assertions.assertThat(anObject.getNaturalId()).isEqualTo("id");
    }

    @Test
    public void AnObject_myString_value_Map_unknown_id__merge__exception()  {
        AnObject anObject = new AnObject().setMyString("value");
        final EO eo = ProviderRootTestScope.createEo(anObject);

        final Map map = new HashMap();
        map.put("unknown", "id");
        Assertions.assertThatThrownBy(()->{eo.mapObject(map);})
        .isInstanceOf(EoException.class);
    }

    @Test
    public void AnObject1_myString_value_AnObject2_myFloat_11__merge__AnObject1_myFloat_11()  {

        final AnObject anObject1 = new AnObject().setMyString( "value");
        final AnObject anObject2 = new AnObject().setMyFloat( 1.1F);
        final EO eo = ProviderRootTestScope.createEo(anObject1);
        eo.mapObject(anObject2);
        assertEquals(1.1F, anObject1.getMyFloat());
    }
}
