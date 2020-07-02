package org.fluentcodes.projects.elasticobjects.executor;

import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.executor.statics.ValuesEO;
import org.fluentcodes.projects.elasticobjects.executor.statics.ValuesMisc;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.M_MODEL_CONFIG;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 *
 */
public class ExecutorItemTest {

    @Test
    public void valuesMiscSet() throws Exception {
        ExecutorItem item = new ExecutorItem(ValuesMisc.DEFAULT_SET, ExecutorItem.TYPES.value);
        Assert.assertNotNull(item);
        Assert.assertEquals(ValuesMisc.class, item.getExecutorClass());
        Assert.assertEquals(CON_SET, item.getMethodName());
        Assert.assertNotNull(item.getMethod());
        EO eo = TestEOProvider.createEmptyMap();
        Object[] args = new Object[]{eo, F_TEST_STRING, S_STRING};
        item.getMethod().invoke(null, new Object[]{args});
        Assert.assertEquals(S_STRING, eo.get(F_TEST_STRING));
    }
}
