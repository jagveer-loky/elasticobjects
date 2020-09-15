package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class EoCloneTest {
    @Ignore
    @Test
    public void test()  {
        final EO eo = ProviderRootTestScope.createEo(BasicTest.class);

        final BasicTest BT1 = new BasicTest();
        BT1.setTestString( "value");
        eo.mapObject(BT1);

        assertEquals("value", eo.get("testString"));

        assertEquals(BasicTest.class, eo.getModelClass());
        Assert.assertNotEquals(BT1, eo.get());
        Assert.assertTrue(BT1 == BT1);
        Assert.assertTrue(eo.get() == eo.get());

        final EO eo2 = ProviderRootTestScope.createEo(BasicTest.class);
        eo2.mapObject(BT1);
        Assert.assertEquals(BT1, eo2.get());
    }
}
