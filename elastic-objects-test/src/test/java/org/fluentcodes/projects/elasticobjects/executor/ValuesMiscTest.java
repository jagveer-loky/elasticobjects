package org.fluentcodes.projects.elasticobjects.executor;

import org.fluentcodes.projects.elasticobjects.TEO_STATIC;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.executor.statics.ValuesMisc;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC._VALUE;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Tests for {@link ValuesMisc}
 * Created 4.6.2018
 */

public class ValuesMiscTest {
    @Test
    public void join()  {
        EO eo = TestEOProvider.create();
        eo.setPathValue(S_KEY0,S_STRING);
        eo.setPathValue(S_KEY1,S_STRING_OTHER);
        eo.setPathValue(S_KEY2,S1);
        String result = ValuesMisc.join(new Object[]{eo, _VALUE, CON_COMMA});
        Assert.assertEquals(TEO_STATIC.join(CON_COMMA, S_STRING, S_STRING_OTHER, S1), result);
    }
}
