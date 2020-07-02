package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.test.AssertEO;
import org.fluentcodes.projects.elasticobjects.test.TestCallsProvider;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created 12.6.2018
 */
public class ResourcesJsonCallTest {
    @Test
    public void readFileSourceJson() throws Exception {
        final EO eoEmpty = TestEOProvider.createEmptyMap();
        final JsonCall call = TestCallsProvider.createJsonCall(FILE_SOURCE_JSON);
        call.read(eoEmpty);
        Assert.assertEquals(S_VALUE11, eoEmpty.getChild(S0).get(S_KEY1));
        AssertEO.compare(eoEmpty);
    }
}
