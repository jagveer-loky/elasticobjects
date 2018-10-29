package org.fluentcodes.projects.elasticobjects.executor;

import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.executor.statics.ValuesMisc;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.M_VALUES_MISC;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 *
 */
public class ExecutorItemTest {
    private static final String METHOD_VALUE_CALL_SET = ".set(content)";
    private static final String METHOD_VALUES_MISC_SET = ".set(adapter, key, value)";

    @Test
    public void valuesMisc() throws Exception {
        ExecutorItem item = new ExecutorItem(M_VALUES_MISC + METHOD_VALUES_MISC_SET, ExecutorItem.TYPES.value);
        Assert.assertNotNull(item);
        Assert.assertEquals(M_VALUES_MISC, item.getClassName());
        Assert.assertEquals(ValuesMisc.class, item.getExecutorClass());
        Assert.assertEquals(CON_SET, item.getMethodName());
        Assert.assertNotNull(item.getMethod());
        EO eoEmpty = TestObjectProvider.createEOFromJson();
        Object[] args = new Object[]{eoEmpty, F_TEST_STRING, S_STRING};
        item.getMethod().invoke(null, new Object[]{args});
        Assert.assertEquals(S_STRING, eoEmpty.get(F_TEST_STRING));
    }

    //TODO check if something like this is necessary....
    @Ignore
    @Test
    public void checkAction() throws Exception {
        ExecutorItem item = new ExecutorItem(M_VALUES_MISC + METHOD_VALUE_CALL_SET, ExecutorItem.TYPES.value);
        Assert.assertNotNull(item);
        Assert.assertEquals(M_VALUES_MISC, item.getClassName());
        Assert.assertEquals(ValuesMisc.class, item.getExecutorClass());
        Assert.assertEquals(CON_SET, item.getMethodName());
        Assert.assertNotNull(item.getMethod());
        EO eoEmpty = TestObjectProvider.createEOFromJson();
        Object call = item
                .getExecutorClass()
                .getConstructor(EOConfigsCache.class, String.class)
                .newInstance(TestObjectProvider.EO_CONFIGS_CACHE, item.getArgs(0));
        //item.getMethod().invoke(action, eoEmpty);
        Assert.assertEquals(S_STRING, eoEmpty.get(SAMPLE_CONTENT));
    }
}
