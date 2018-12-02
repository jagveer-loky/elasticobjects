package org.fluentcodes.projects.elasticobjects.wiki.deserialization;

import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class Jsn {
    @Test
    public void test() throws Exception {
        final EO eo = TestObjectProvider.create();

        final String jsnString = "{\"(BasicTest)_data\":{\"testFloat\":1.1}";
        eo
                .add()
                .map(jsnString);

        assertEquals(BasicTest.class, eo.getModelClass());
        assertEquals(1.1F, eo.get("testFloat"));
    }
}
