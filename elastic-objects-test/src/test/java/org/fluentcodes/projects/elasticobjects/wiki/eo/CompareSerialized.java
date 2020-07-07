package org.fluentcodes.projects.elasticobjects.wiki.eo;

import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.eo.EOToJSON;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class CompareSerialized {
    @Test
    public void test()  {
        final EO eo = TestEOProvider.create();
        final String mapJson = "{\"first\": 1,\"second\": 2,\"third\": 3}";
        eo.mapObject(mapJson);

        assertEquals(1L, eo.get("first"));
        assertEquals(mapJson, new EOToJSON().toJSON(eo));
    }
}
