package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by werner.diwischek on 12.12.17.
 */
public class EOLogTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(EOKeysTest.class);

    private EO createWarnAdapter() throws Exception {
        return TestEOProvider.createEOBuilder()
                .setLogLevel(LogLevel.WARN)
                .setPath(S_TEST_STRING)
                .set(S_STRING)
                .getRoot();
    }

    @Test
    public void errorWithException_WarnAdapter() throws Exception {
        EO adapter = createWarnAdapter();
        adapter.error(S_MESSAGE, new Exception(S_STRING));
        Assert.assertFalse(INFO_LOG_NOT_EMPTY_FAILS, adapter.getLog().isEmpty());
    }

    @Test
    public void error_WarnAdapter() throws Exception {
        EO adapter = createWarnAdapter();
        adapter.error(S_MESSAGE);
        Assert.assertFalse(INFO_LOG_NOT_EMPTY_FAILS, adapter.getLog().isEmpty());
    }

    @Test
    public void warnWithException_WarnAdapter() throws Exception {
        EO adapter = createWarnAdapter();
        adapter.warn(S_MESSAGE, new Exception(S_STRING));
        Assert.assertFalse(INFO_LOG_NOT_EMPTY_FAILS, adapter.getLog().isEmpty());
    }


    @Test
    public void warn_WarnAdapter() throws Exception {
        EO adapter = createWarnAdapter();
        adapter.warn(S_MESSAGE);
        Assert.assertFalse(INFO_LOG_NOT_EMPTY_FAILS, adapter.getLog().isEmpty());
    }

    @Test
    public void info_WarnAdapter() throws Exception {
        EO adapter = createWarnAdapter();
        adapter.info(S_MESSAGE);
        Assert.assertTrue(INFO_LOG_EMPTY_FAILS + adapter.getLog(), adapter.getLog().isEmpty());
    }

    @Test
    public void debug_WarnAdapter() throws Exception {
        EO adapter = createWarnAdapter();
        adapter.debug(S_MESSAGE);
        Assert.assertTrue(INFO_LOG_EMPTY_FAILS + adapter.getLog(), adapter.getLog().isEmpty());
    }

    @Test
    public void info_InfoChildAdapter() throws Exception {
        EO adapter = createWarnAdapter();
        EO childAdapter = adapter
                .add(S_LEVEL0)
                .setLogLevel(LogLevel.INFO)
                .build();
        childAdapter.info(S_MESSAGE);
        Assert.assertFalse(INFO_LOG_NOT_EMPTY_FAILS + adapter.getLog(), adapter.getLog().isEmpty());
    }

    @Test
    public void info_childAdapterHas_HasDefaultWarningFromParent() throws Exception {
        EO adapter = createWarnAdapter();
        adapter.info(S_MESSAGE);
        Assert.assertTrue(INFO_LOG_EMPTY_FAILS + adapter.getLog(), adapter.getLog().isEmpty());
    }


}
