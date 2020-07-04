package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.test.AssertEO;
import org.fluentcodes.projects.elasticobjects.test.TestCallsProvider;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 21.10.2018
 */
public class ResourcesScsCallTest {
    @Test
    public void readFileSourceJson()  {
        final EO eoEmpty = TestEOProvider.createEmptyMap();
        final ScsCall call = TestCallsProvider.createScsCall(CS_SOURCE_CSV);
        call.read(eoEmpty);
        Assert.assertEquals(S_VALUE11, eoEmpty.getChild(S0).get(S_KEY1));
        AssertEO.compare(eoEmpty);
    }
}
