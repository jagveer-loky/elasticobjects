package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.assets.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class EoMergeTest {
    @Ignore
    @Test
    public void test()  {
        final EO eo = ProviderRootTestScope.createEo();

        final AnObject anObject1 = new AnObject();
        anObject1.setMyString( "value");

        final AnObject anObject2 = new AnObject();
        anObject2.setMyFloat( 1.1F);

        eo.mapObject(anObject1);
        eo.mapObject(anObject2);

        assertEquals("value", eo.get(AnObject.MY_STRING));
        assertEquals(1.1F, eo.get(AnObject.MY_FLOAT));
        assertEquals(1.1F, anObject1.getMyFloat());

        assertEquals(AnObject.class, eo.getModelClass());
        assertEquals(AnObject.class, eo.get().getClass());

    }
}
