package org.fluentcodes.projects.elasticobjects.executor.statics;

import org.fluentcodes.projects.elasticobjects.executor.ExecutorProvider;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.eo.EOToJSON;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.executor.statics.ValuesEOTemplateCallTest.RESULT;

/**
 * Created by Werner on 3.10.2019.
 */
public class ValuesEOExecutorTest extends TestHelper {


    @Test
    public void getConfigurationKeys_withoutParameters()  {
        EO root = ExecutorProvider.execute(
                ValuesEO.createGetConfigurationKeys()
        );
        Assert.assertEquals(M_MODEL_CONFIG, root.get("0"));
    }

    @Test
    public void getConfigurationKeys_withModelConfig()  {
        EO root = ExecutorProvider.execute(
                ValuesEO.createGetConfigurationKeys(M_MODEL_CONFIG)
        );
        Assert.assertEquals(M_LINKED_HASH_SET, root.get("0"));
    }

    @Test
    public void getConfigurationKeys_withModelConfig_andMap()  {
        EO root = ExecutorProvider.execute(
                ValuesEO.createGetConfigurationKeys(M_MODEL_CONFIG, ".*Map")
        );
        Assert.assertEquals(M_HASH_MAP, root.get("0"));
    }

    @Test
    public void getConfigurationList_withModelConfig_andMap()  {
        final EO root = ExecutorProvider.execute(
                ValuesEO.createGetConfigurationList(M_MODEL_CONFIG, ".*Map")
        );
        Assert.assertEquals(M_HASH_MAP, root.getChild("0").get(F_MODEL_KEY));
    }

    @Test
    public void getConfiguration_withModelConfig_andMap()  {
        final EO eo = ExecutorProvider.execute(
                ValuesEO.createCallGetConfiguration(M_MODEL_CONFIG, M_MAP)
        );
        Assert.assertEquals("Problem: " + new EOToJSON().setStartIndent(1).toJSON(eo), M_MAP, eo.get(F_MODEL_KEY));
    }

    @Test
    public void getConfigurationKeys_withMapPath()  {
        final EO eo = ExecutorProvider.execute(
                ValuesEO.createGetConfigurationKeys(),
                F_MAP_PATH, RESULT
        );
        Assert.assertEquals(M_MODEL_CONFIG, eo.get(RESULT + "/0"));
    }

    @Test
    public void getConfigurationKeys_withMapPath_andFieldConfig()  {
        final EO eo = ExecutorProvider.execute(
                ValuesEO.createGetConfigurationKeys(M_FIELD_CONFIG),
                F_MAP_PATH, RESULT
        );
        Assert.assertEquals(F_PATH_PATTERN_AS_STRING, eo.get(RESULT + "/0"));
    }

    @Test
    public void getConfigurationKeysBy()  {
        final EO eo = ExecutorProvider.execute(
                ValuesEO.createGetConfigurationKeys(M_FIELD_CONFIG),
                F_MAP_PATH, RESULT
        );
        Assert.assertEquals(F_PATH_PATTERN_AS_STRING, eo.get(RESULT + "/0"));
    }
}
