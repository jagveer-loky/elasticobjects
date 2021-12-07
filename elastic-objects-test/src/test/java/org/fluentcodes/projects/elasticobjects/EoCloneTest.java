package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.MY_STRING;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.NATURAL_ID;

public class EoCloneTest {

    @Test
    public void AnObject_myString_value__non_clone__object_is_not_equal()  {
        final AnObject anObject1 = new AnObject().setMyString( "value");
        final EO eo1 = ProviderConfigMaps.createEo(anObject1);
        final EO eo2 = ProviderConfigMaps.createEo(eo1.get());
        eo1.set("id", NATURAL_ID);
        Assertions.assertThat(anObject1).isNotEqualTo(eo2.get());
    }

    @Test
    public void AnObject_myString_value__setNaturalId_id__is_same_in_AnObject()  {
        final AnObject anObject1 = new AnObject().setMyString( "value");
        final EO eo1 = ProviderConfigMaps.createEo(anObject1);
        eo1.set("id", NATURAL_ID);
        Assertions.assertThat(eo1.get(NATURAL_ID)).isEqualTo("id");
        Assertions.assertThat(anObject1.getNaturalId()).isNull();
    }

}
