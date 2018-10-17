package org.fluentcodes.projects.elasticobjects.executor;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

import org.fluentcodes.projects.elasticobjects.TEO_STATIC;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.executor.statics.ValuesMisc;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link ValuesMisc}
 * Created 4.6.2018
 */

public class ValuesMiscTest {
    @Test
    public void join() throws Exception {
        EO adapter = TestObjectProvider.createEO();
        adapter.add(S_KEY0).set(S_STRING);
        adapter.add(S_KEY1).set(S_STRING_OTHER);
        adapter.add(S_KEY2).set(S1);
        String result = ValuesMisc.join(new Object[]{adapter, _VALUE, CON_COMMA});
        Assert.assertEquals(TEO_STATIC.join(CON_COMMA, S_STRING,S_STRING_OTHER,S1), result);
    }
}
