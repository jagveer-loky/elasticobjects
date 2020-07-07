package org.fluentcodes.projects.elasticobjects.wiki.deserialization;

import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class Jsn {
    @Test
    public void test()  {
        final EO eo = TestEOProvider.create();

        final String jsnString = "{\"(BasicTest)_data\":{\"testFloat\":1.1}";
        eo.mapObject(jsnString);

        assertEquals(BasicTest.class, eo.getModelClass());
        assertEquals(1.1F, eo.get("testFloat"));
    }
}
