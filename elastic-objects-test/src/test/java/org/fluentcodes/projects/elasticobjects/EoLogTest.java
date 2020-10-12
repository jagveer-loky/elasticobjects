package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by werner.diwischek on 12.12.17.
 */
public class EoLogTest {

    @Test
    public void givenDev_thenLogLevelWarn()  {
        EO eo = ProviderRootDevScope.createEo();
        Assertions.assertThat(eo.getLogLevel()).isEqualTo(LogLevel.WARN);
    }

    @Test
    public void givenDev_WhenSetLogLevelError_thenLogLevelError()  {
        EO eo = ProviderRootDevScope.createEo();
        eo.setLogLevel(LogLevel.ERROR);
        Assertions.assertThat(eo.getLogLevel()).isEqualTo(LogLevel.ERROR);
    }

    @Test
    public void givenEORootWithWarn_whenErrorMessage_thenLogNotEmpty()  {
        EO eo = ProviderRootDevScope.createEo();
        eo.error(S_MESSAGE, new Exception(S_STRING));
        Assertions.assertThat(eo.getLog()).isNotEmpty();
        Assertions.assertThat(eo.getErrorLevel()).isEqualTo(LogLevel.ERROR);
    }

    @Test
    public void error_WarnAdapter()  {
        EO eo = ProviderRootDevScope.createEo();
        eo.error(S_MESSAGE);
        Assert.assertFalse(INFO_LOG_NOT_EMPTY_FAILS, eo.getLog().isEmpty());
    }

    @Test
    public void warnWithException_WarnAdapter()  {
        EO eo = ProviderRootDevScope.createEo();
        eo.warn(S_MESSAGE, new Exception(S_STRING));
        Assert.assertFalse(INFO_LOG_NOT_EMPTY_FAILS, eo.getLog().isEmpty());
    }


    @Test
    public void warn_WarnAdapter()  {
        EO eo = ProviderRootDevScope.createEo();
        eo.warn(S_MESSAGE);
        Assert.assertFalse(INFO_LOG_NOT_EMPTY_FAILS, eo.getLog().isEmpty());
    }

    @Test
    public void info_WarnAdapter()  {
        EO eo = ProviderRootDevScope.createEo();
        eo.info(S_MESSAGE);
        Assert.assertTrue(INFO_LOG_EMPTY_FAILS + eo.getLog(), eo.getLog().isEmpty());
    }

    @Test
    public void debug_WarnAdapter()  {
        EO eo = ProviderRootDevScope.createEo();
        eo.debug(S_MESSAGE);
        Assert.assertTrue(INFO_LOG_EMPTY_FAILS + eo.getLog(), eo.getLog().isEmpty());
    }

    @Test
    public void givenDevWitChild_whenWarnMessage_thenLogNotEmpty()  {
        EO eo = ProviderRootDevScope.createEo();
        EO eoChild = eo
                .setEmpty(S_LEVEL0);
        Assertions.assertThat(eo.getLogLevel()).isEqualTo(LogLevel.WARN);
        eoChild.warn(S_MESSAGE);
        Assert.assertFalse(INFO_LOG_NOT_EMPTY_FAILS + eo.getLog(), eo.getLog().isEmpty());
    }

    @Test
    public void givenDevWithChild_whenInfoMessage_thenLogEmpty()  {
        EO eo = ProviderRootDevScope.createEo();
        EO eoChild = eo
                .setEmpty(S_LEVEL0);
        eoChild.info(S_MESSAGE);
        Assertions.assertThat(eo.getLog()).isEmpty();
    }

    @Test
    public void givenChildWithLogLevelInfo_whenInfoMessage_thenLogNotEmpty()  {
        EO eo = ProviderRootDevScope.createEo();
        EO child = eo.setEmpty(S_LEVEL0);
        child.setLogLevel(LogLevel.INFO);
        child.info(S_MESSAGE);
        Assertions.assertThat(eo.getLog()).isNotEmpty();
        //new XpectEo().compareAsString(eo);
    }
}
