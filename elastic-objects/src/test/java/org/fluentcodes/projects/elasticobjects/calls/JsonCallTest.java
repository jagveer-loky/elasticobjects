package org.fluentcodes.projects.elasticobjects.calls;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.test.AssertEO;
import org.fluentcodes.projects.elasticobjects.test.TestCallsProvider;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created 12.6.2018
 */
public class JsonCallTest {
    @Test
    public void readModuleConfigJson() throws Exception {
        final EO eoEmpty = TestObjectProvider.createEO();
        final JsonCall action = TestCallsProvider.createJsonCall( J_MODULE_CONFIG_JSON);
        action.read(eoEmpty);
        Assert.assertEquals(MODULE_SHORT, eoEmpty.getChild(MODULE).get(F_SHORT));
        AssertEO.compare(eoEmpty);
    }
}
