package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootDev;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;

import org.fluentcodes.tools.xpect.XpectEo;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by werner.diwischek on 12.12.17.
 */
public class EoLogTest {
    private static final Logger LOG = LogManager.getLogger(EOKeysTest.class);

    private static EO createWarnAdapter()  {
        EO root = TestProviderRootDev.createEo();
        Assertions.assertThat(root.getLogLevel()).isEqualTo(LogLevel.WARN);
        return root;
    }

    @Test
    public void givenEORootWithWarn_whenErrorMessage_thenLogNotEmpty()  {
        EO eo = createWarnAdapter();
        eo.error(S_MESSAGE, new Exception(S_STRING));
        Assertions.assertThat(eo.getLog()).isNotEmpty();
        Assertions.assertThat(eo.getErrorLevel()).isEqualTo(LogLevel.ERROR);
        //new XpectEo().compareAsString(eo);
    }

    @Test
    public void error_WarnAdapter()  {
        EO eo = createWarnAdapter();
        eo.error(S_MESSAGE);
        Assert.assertFalse(INFO_LOG_NOT_EMPTY_FAILS, eo.getLog().isEmpty());
    }

    @Test
    public void warnWithException_WarnAdapter()  {
        EO eo = createWarnAdapter();
        eo.warn(S_MESSAGE, new Exception(S_STRING));
        Assert.assertFalse(INFO_LOG_NOT_EMPTY_FAILS, eo.getLog().isEmpty());
    }


    @Test
    public void warn_WarnAdapter()  {
        EO eo = createWarnAdapter();
        eo.warn(S_MESSAGE);
        Assert.assertFalse(INFO_LOG_NOT_EMPTY_FAILS, eo.getLog().isEmpty());
    }

    @Test
    public void info_WarnAdapter()  {
        EO eo = createWarnAdapter();
        eo.info(S_MESSAGE);
        Assert.assertTrue(INFO_LOG_EMPTY_FAILS + eo.getLog(), eo.getLog().isEmpty());
    }

    @Test
    public void debug_WarnAdapter()  {
        EO eo = createWarnAdapter();
        eo.debug(S_MESSAGE);
        Assert.assertTrue(INFO_LOG_EMPTY_FAILS + eo.getLog(), eo.getLog().isEmpty());
    }

    @Test
    public void givenChild_whenWarnMessage_thenLogNotEmpty()  {
        EO eo = createWarnAdapter();
        EO eoChild = eo
                .setEmpty(S_LEVEL0);
        Assertions.assertThat(eo.getLogLevel()).isEqualTo(LogLevel.WARN);
        eoChild.warn(S_MESSAGE);
        Assert.assertFalse(INFO_LOG_NOT_EMPTY_FAILS + eo.getLog(), eo.getLog().isEmpty());
    }

    @Test
    public void givenChild_whenInfoMessage_thenLogEmpty()  {
        EO eo = createWarnAdapter();
        EO eoChild = eo
                .setEmpty(S_LEVEL0);
        eoChild.info(S_MESSAGE);
        Assertions.assertThat(eo.getLog()).isEmpty();
    }

    @Test
    public void givenChildWithLogLevelInfo_whenInfoMessage_thenLogNotEmpty()  {
        EO eo = createWarnAdapter();
        EO eoChild = eo
                .setEmpty(S_LEVEL0)
                .setLogLevel(LogLevel.INFO);
        eoChild.info(S_MESSAGE);
        Assertions.assertThat(eo.getLog()).isNotEmpty();
        //new XpectEo().compareAsString(eo);
    }
}
