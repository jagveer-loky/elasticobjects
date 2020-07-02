package org.fluentcodes.projects.elasticobjects.wiki.eo;

import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.junit.Assert;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class Clone {
    @Test
    public void test() throws Exception {
        final EO eo = TestEOProvider.create(BasicTest.class);

        final BasicTest BT1 = new BasicTest();
        BT1.setTestString( "value");
        eo.add()
                .map(BT1);

        assertEquals("value", eo.get("testString"));

        assertEquals(BasicTest.class, eo.getModelClass());
        Assert.assertNotEquals(BT1, eo.get());
        Assert.assertTrue(BT1 == BT1);
        Assert.assertTrue(eo.get() == eo.get());

        final EO eo2 = TestEOProvider.create(BasicTest.class);
        eo2.add()
                .set(BT1);
        Assert.assertEquals(BT1, eo2.get());

    }
}
