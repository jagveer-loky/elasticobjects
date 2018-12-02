package org.fluentcodes.projects.elasticobjects.wiki.eo;

import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class CompareDifferent {
    @Test
    public void test() throws Exception {
        final Map map = new HashMap();
        map.put("testString", "value");
        final EO eo = TestObjectProvider.create();
        eo.add().set(map);

        BasicTest BT = new BasicTest();
        BT.setTestString("value2");
        final EO eo2 = TestObjectProvider.create();
        eo2.add().set(BT);

        StringBuilder diff = new StringBuilder();
        eo.compare(diff, eo2);
        Assert.assertEquals("/testString = value: != value2\n", diff.toString());
    }
}
