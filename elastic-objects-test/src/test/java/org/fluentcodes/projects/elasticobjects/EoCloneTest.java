package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class EoCloneTest {
    @Ignore
    @Test
    public void test()  {
        final EO eo = ProviderRootTestScope.createEo(AnObject.class);

        final AnObject anObject1 = new AnObject();
        anObject1.setMyString( "value");
        eo.mapObject(anObject1);

        assertEquals("value", eo.get(AnObject.MY_STRING));

        assertEquals(AnObject.class, eo.getModelClass());
        Assert.assertNotEquals(anObject1, eo.get());
        Assert.assertTrue(anObject1 == anObject1);
        Assert.assertTrue(eo.get() == eo.get());

        final EO eo2 = ProviderRootTestScope.createEo(AnObject.class);
        eo2.mapObject(anObject1);
        Assert.assertEquals(anObject1, eo2.get());
    }
}
