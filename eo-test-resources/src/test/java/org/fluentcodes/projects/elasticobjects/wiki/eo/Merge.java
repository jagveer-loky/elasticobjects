package org.fluentcodes.projects.elasticobjects.wiki.eo;

import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class Merge {
    @Test
    public void test() throws Exception {
        final EO eo = TestEOProvider.create();

        final BasicTest BT1 = new BasicTest();
        BT1.setTestString( "value");

        final BasicTest BT2 = new BasicTest();
        BT2.setTestFloat( 1.1F);

        eo.add()
                .set(BT1);
        eo.add()
                .map(BT2);

        assertEquals("value", eo.get("testString"));
        assertEquals(1.1F, eo.get("testFloat"));
        assertEquals(1.1F, BT1.getTestFloat());

        assertEquals(BasicTest.class, eo.getModelClass());
        assertEquals(BasicTest.class, eo.get().getClass());

    }
}
