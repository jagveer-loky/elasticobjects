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
    public void ____getLogLevel_WARN()  {
        EO eo = ProviderRootDevScope.createEo();
        Assertions.assertThat(eo.getLogLevel()).isEqualTo(LogLevel.WARN);
    }

    @Test
    public void __setLogLevel_ERROR__getLogLevel_ERROR()  {
        EO eo = ProviderRootDevScope.createEo();
        eo.setLogLevel(LogLevel.ERROR);
        Assertions.assertThat(eo.getLogLevel()).isEqualTo(LogLevel.ERROR);
    }

    @Test
    public void __error__errorLevel_ERROR()  {
        EO eo = ProviderRootDevScope.createEo();
        eo.error(S_MESSAGE, new Exception(S_STRING));
        Assertions.assertThat(eo.getLog()).isNotEmpty();
        Assertions.assertThat(eo.getErrorLevel()).isEqualTo(LogLevel.ERROR);
    }

    @Test
    public void __error__log_isNotEmpty()  {
        EO eo = ProviderRootDevScope.createEo();
        eo.error(S_MESSAGE);
        Assertions.assertThat(eo.getLog()).isNotEmpty();
    }

    @Test
    public void __warn_exception__log_isNotEmpty()  {
        EO eo = ProviderRootDevScope.createEo();
        eo.warn(S_MESSAGE, new Exception(S_STRING));
        Assertions.assertThat(eo.getLog()).isNotEmpty();
    }


    @Test
    public void __warn__log_isNotEmpty()  {
        EO eo = ProviderRootDevScope.createEo();
        eo.warn(S_MESSAGE);
        Assertions.assertThat(eo.getLog()).isNotEmpty();
    }

    @Test
    public void __info__log_isEmpty()  {
        EO eo = ProviderRootDevScope.createEo();
        eo.info(S_MESSAGE);
        Assertions.assertThat(eo.getLog()).isEmpty();
    }

    @Test
    public void __debug__log_isEmpty()  {
        EO eo = ProviderRootDevScope.createEo();
        eo.debug(S_MESSAGE);
        Assertions.assertThat(eo.getLog()).isEmpty();
    }

    @Test
    public void child__warn__log_isNotEmpty()  {
        EO eo = ProviderRootDevScope.createEo();
        EO eoChild = eo
                .setEmpty(S_LEVEL0);
        Assertions.assertThat(eo.getLogLevel()).isEqualTo(LogLevel.WARN);
        eoChild.warn(S_MESSAGE);
        Assertions.assertThat(eo.getLog()).isNotEmpty();
    }

    @Test
    public void child__info__log_isEmpty()  {
        EO eo = ProviderRootDevScope.createEo();
        EO eoChild = eo
                .setEmpty(S_LEVEL0);
        eoChild.info(S_MESSAGE);
        Assertions.assertThat(eo.getLog()).isEmpty();
    }

    @Test
    public void child_LogLevel_INFO__info__log_isNotEmpty()  {
        EO eo = ProviderRootDevScope.createEo();
        EO child = eo.setEmpty(S_LEVEL0);
        child.setLogLevel(LogLevel.INFO);
        child.info(S_MESSAGE);
        Assertions.assertThat(eo.getLog()).isNotEmpty();
    }
}
