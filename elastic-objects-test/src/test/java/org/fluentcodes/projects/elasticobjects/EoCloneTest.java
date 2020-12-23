package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.MY_STRING;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.NATURAL_ID;

public class EoCloneTest {

    @Test
    public void AnObject_myString_value__clone__isDifferent_but_same_toString()  {

        final AnObject anObject1 = new AnObject().setMyString( "value");
        final EO eo1 = ProviderRootTestScope.createEo(anObject1);

        final EO eo2 = ProviderRootTestScope.createEo(AnObject.class);
        eo2.mapObject(eo1.get());
        Assertions.assertThat(anObject1).isNotEqualTo(eo2.get());
        Assertions.assertThat(eo1.toString(JSONSerializationType.STANDARD))
                .isEqualTo(eo2.toString(JSONSerializationType.STANDARD));
        eo1.set("id", NATURAL_ID);
        Assertions.assertThat(anObject1.getNaturalId()).isEqualTo("id");
        Assertions.assertThat(((AnObject)eo2.get()).getNaturalId()).isNull();
    }

    @Test
    public void AnObject_myString_value__non_clone__object_is_equal()  {
        final AnObject anObject1 = new AnObject().setMyString( "value");
        final EO eo1 = ProviderRootTestScope.createEo(anObject1);
        final EO eo2 = ProviderRootTestScope.createEo(eo1.get());
        eo1.set("id", NATURAL_ID);
        Assertions.assertThat(anObject1).isEqualTo(eo2.get());
    }

    @Test
    public void AnObject_myString_value__setNaturalId_id__is_same_in_AnObject()  {
        final AnObject anObject1 = new AnObject().setMyString( "value");
        EO eo0 = ProviderRootTestScope.createEo();
        final EO eo1 = ProviderRootTestScope.createEo(anObject1);
        eo1.set("id", NATURAL_ID);
        Assertions.assertThat(eo1.get(MY_STRING)).isEqualTo("value");
        Assertions.assertThat(eo1.get(NATURAL_ID)).isEqualTo("id");
        Assertions.assertThat(anObject1.getNaturalId()).isEqualTo("id");
    }

}
