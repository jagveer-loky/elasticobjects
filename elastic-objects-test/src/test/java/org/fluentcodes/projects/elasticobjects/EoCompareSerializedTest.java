package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class EoCompareSerializedTest {
    @Ignore
    @Test
    public void test()  {
        final EO eo = ProviderRootTestScope.createEo();
        final String mapJson = "{\"first\": 1,\"second\": 2,\"third\": 3}";
        eo.mapObject(mapJson);

        assertEquals(1L, eo.get("first"));
        assertEquals(mapJson, new EOToJSON().toJSON(eo));
    }
}
