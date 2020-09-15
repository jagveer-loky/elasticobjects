package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class EoMergeTest {
    @Ignore
    @Test
    public void test()  {
        final EO eo = ProviderRootTestScope.createEo();

        final BasicTest BT1 = new BasicTest();
        BT1.setTestString( "value");

        final BasicTest BT2 = new BasicTest();
        BT2.setTestFloat( 1.1F);

        eo.mapObject(BT1);
        eo.mapObject(BT2);

        assertEquals("value", eo.get("testString"));
        assertEquals(1.1F, eo.get("testFloat"));
        assertEquals(1.1F, BT1.getTestFloat());

        assertEquals(BasicTest.class, eo.getModelClass());
        assertEquals(BasicTest.class, eo.get().getClass());

    }
}
