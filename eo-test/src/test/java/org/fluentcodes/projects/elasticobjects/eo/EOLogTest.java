package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.LogLevel;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by werner.diwischek on 12.12.17.
 */
public class EOLogTest {
    private static final Logger LOG = LogManager.getLogger(EOKeysTest.class);

    private EO createWarnAdapter()  {
        EO root = new EoRoot(TestProviderRootTest.EO_CONFIGS, LogLevel.WARN);
        return root.setPathValue(S_TEST_STRING,S_STRING);
    }

    @Test
    public void errorWithException_WarnAdapter()  {
        EO adapter = createWarnAdapter();
        adapter.error(S_MESSAGE, new Exception(S_STRING));
        Assert.assertFalse(INFO_LOG_NOT_EMPTY_FAILS, adapter.getLog().isEmpty());
    }

    @Test
    public void error_WarnAdapter()  {
        EO adapter = createWarnAdapter();
        adapter.error(S_MESSAGE);
        Assert.assertFalse(INFO_LOG_NOT_EMPTY_FAILS, adapter.getLog().isEmpty());
    }

    @Test
    public void warnWithException_WarnAdapter()  {
        EO adapter = createWarnAdapter();
        adapter.warn(S_MESSAGE, new Exception(S_STRING));
        Assert.assertFalse(INFO_LOG_NOT_EMPTY_FAILS, adapter.getLog().isEmpty());
    }


    @Test
    public void warn_WarnAdapter()  {
        EO adapter = createWarnAdapter();
        adapter.warn(S_MESSAGE);
        Assert.assertFalse(INFO_LOG_NOT_EMPTY_FAILS, adapter.getLog().isEmpty());
    }

    @Test
    public void info_WarnAdapter()  {
        EO adapter = createWarnAdapter();
        adapter.info(S_MESSAGE);
        Assert.assertTrue(INFO_LOG_EMPTY_FAILS + adapter.getLog(), adapter.getLog().isEmpty());
    }

    @Test
    public void debug_WarnAdapter()  {
        EO adapter = createWarnAdapter();
        adapter.debug(S_MESSAGE);
        Assert.assertTrue(INFO_LOG_EMPTY_FAILS + adapter.getLog(), adapter.getLog().isEmpty());
    }

    @Test
    public void info_InfoChildAdapter()  {
        EO adapter = createWarnAdapter();
        EO childAdapter = adapter
                .setPathValue(S_LEVEL0);
        childAdapter.info(S_MESSAGE);
        Assert.assertFalse(INFO_LOG_NOT_EMPTY_FAILS + adapter.getLog(), adapter.getLog().isEmpty());
    }

    @Test
    public void info_childAdapterHas_HasDefaultWarningFromParent()  {
        EO adapter = createWarnAdapter();
        adapter.info(S_MESSAGE);
        Assert.assertTrue(INFO_LOG_EMPTY_FAILS + adapter.getLog(), adapter.getLog().isEmpty());
    }


}
