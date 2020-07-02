package org.fluentcodes.projects.elasticobjects.wiki.eo;

import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class Compare {
    @Test
    public void test() throws Exception {
        final Map map = new HashMap();
        map.put("testString", "value");
        final EO eo = TestEOProvider.create();
        eo.add().set(map);

        BasicTest BT = new BasicTest();
        BT.setTestString("value");
        final EO eo2 = TestEOProvider.create();
        eo2.add().set(BT);

        StringBuilder diff = new StringBuilder();
        eo.compare(diff, eo2);
        Assert.assertEquals("", diff.toString());
    }
}
