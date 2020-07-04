package org.fluentcodes.projects.elasticobjects.executor.statics;

import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.executor.ExecutorItem;
import org.fluentcodes.projects.elasticobjects.executor.ExecutorProvider;
import org.fluentcodes.projects.elasticobjects.executor.ExecutorValues;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

/**
 *
 */
public class ValuesEOExecutorItemTest {

    @Test
    public void getConfigurationKeys_withoutParameters()  {
        ExecutorItem item = new ExecutorItem(ValuesEO.EXECUTE_GET_CONFIGURATION_KEYS, ExecutorItem.TYPES.value);
        EO eo = TestEOProvider.createEmptyMap();
        Object[] args = new Object[]{eo};
        List result = (List) item.invoke(args);
        Assert.assertEquals(M_MODEL_CONFIG, result.get(0));
    }

    @Test
    public void getConfigurationKeys_withModelConfig()  {
        ExecutorItem item = new ExecutorItem(ValuesEO.EXECUTE_GET_CONFIGURATION_KEYS, ExecutorItem.TYPES.value);
        List result = (List) item.invoke(new Object[]{TestEOProvider.createEmptyMap(), M_MODEL_CONFIG});
        Assert.assertEquals(M_LINKED_HASH_SET, result.get(0));
    }

    @Test
    public void getConfigurationKeysExecutor_withoutParameter()  {
        ExecutorValues executor = ValuesEO.createsExecutorGetConfigurationKeys(F_MAP_PATH,".");
        final EO result = ExecutorProvider.execute(executor);
        Assert.assertEquals(M_MODEL_CONFIG, result.get("0"));
    }

    @Test
    public void getConfigurationKeysExecutor_withModelConfig()  {
        ExecutorValues executor = ValuesEO.createsExecutorGetConfigurationKeys(F_MAP_PATH,".",ValuesEO.CONFIG_TYPE, M_MODEL_CONFIG);
        final EO result = ExecutorProvider.execute(executor);
        Assert.assertEquals(M_LINKED_HASH_SET, result.get("0"));
    }

    @Test
    public void getConfigurationKeysExecutor_withFieldConfig()  {
        ExecutorValues executor = ValuesEO.createsExecutorGetConfigurationKeys(F_MAP_PATH,".",ValuesEO.CONFIG_TYPE, M_FIELD_CONFIG);
        final EO result = ExecutorProvider.execute(executor);
        Assert.assertEquals(F_PATH_PATTERN_AS_STRING, result.get("0"));
    }
}
