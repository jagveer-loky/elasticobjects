package org.fluentcodes.projects.elasticobjects.wiki.eo;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.fileprovider.ProviderRootTest;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class CompareSerialized {
    @Test
    public void test()  {
        final EO eo = ProviderRootTest.createEo();
        final String mapJson = "{\"first\": 1,\"second\": 2,\"third\": 3}";
        eo.mapObject(mapJson);

        assertEquals(1L, eo.get("first"));
        assertEquals(mapJson, new EOToJSON().toJSON(eo));
    }
}
