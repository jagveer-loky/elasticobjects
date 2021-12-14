package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL0;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_MESSAGE;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_STRING;

/**
 * Created by werner.diwischek on 12.12.17.
 */
public class IEOLogTest {

    @Test
    public void ____getLogLevel_WARN() {
        EoRoot eo = ProviderConfigMapsDev.createEo();
        Assertions.assertThat(eo.getLogLevel()).isEqualTo(LogLevel.WARN);
    }

    @Test
    public void ERROR__LogLevelIsPersist() {
        EoRoot eo = ProviderConfigMapsDev.createEo();
        eo.setLogLevel(LogLevel.ERROR);
        EoRoot fromJson = ProviderConfigMapsDev.assertCompare(eo, "{\n" +
                "  \"(LogLevel)_logLevel\": \"ERROR\"\n" +
                "}");
        Assertions.assertThat(fromJson.getLogLevel()).isEqualTo(LogLevel.ERROR);
    }

    @Test
    public void ERROR__error__logNotEmpty() {
        EoRoot eo = ProviderConfigMapsDev.createEo();
        eo.setLogLevel(LogLevel.ERROR);
        eo.error("test");
        Assert.assertFalse(eo.getLog().isEmpty());
    }

    @Test
    public void ERROR__info__logEmpty() {
        EoRoot eo = ProviderConfigMapsDev.createEo();
        eo.setLogLevel(LogLevel.ERROR);
        eo.info("test");
        Assert.assertTrue(eo.getLog().isEmpty());
    }

    @Test
    public void __error__errorLevel_ERROR() {
        EoRoot eo = ProviderConfigMapsDev.createEo();
        eo.error(S_MESSAGE, new Exception(S_STRING));
        Assertions.assertThat(eo.getLog()).isNotEmpty();
        Assertions.assertThat(eo.getErrorLevel()).isEqualTo(LogLevel.ERROR);
    }

    @Test
    public void __error__log_isNotEmpty() {
        EoRoot eo = ProviderConfigMapsDev.createEo();
        eo.error(S_MESSAGE);
        Assertions.assertThat(eo.getLog()).isNotEmpty();
    }

    @Test
    public void __warn_exception__log_isNotEmpty() {
        EoRoot eo = ProviderConfigMapsDev.createEo();
        eo.warn(S_MESSAGE, new Exception(S_STRING));
        Assertions.assertThat(eo.getLog()).isNotEmpty();
    }


    @Test
    public void __warn__log_isNotEmpty() {
        EoRoot eo = ProviderConfigMapsDev.createEo();
        eo.warn(S_MESSAGE);
        Assertions.assertThat(eo.getLog()).isNotEmpty();
    }

    @Test
    public void __info__log_isEmpty() {
        EoRoot eo = ProviderConfigMapsDev.createEo();
        eo.info(S_MESSAGE);
        Assertions.assertThat(eo.getLog()).isEmpty();
    }

    @Test
    public void __debug__log_isEmpty() {
        EoRoot eo = ProviderConfigMapsDev.createEo();
        eo.debug(S_MESSAGE);
        Assertions.assertThat(eo.getLog()).isEmpty();
    }

    @Test
    public void child__warn__log_isNotEmpty() {
        EoRoot eo = ProviderConfigMapsDev.createEo();
        IEOScalar eoChild = eo
                .createChild(S_LEVEL0);
        Assertions.assertThat(eo.getLogLevel()).isEqualTo(LogLevel.WARN);
        eo.warn(S_MESSAGE);
        Assertions.assertThat(eo.getLog()).isNotEmpty();
    }

    @Test
    public void child__info__log_isEmpty() {
        EoRoot eo = ProviderConfigMapsDev.createEo();
        eo.createChild(S_LEVEL0);
        eo.info(S_MESSAGE);
        Assertions.assertThat(eo.getLog()).isEmpty();
    }

    @Test
    public void child_LogLevel_INFO__info__log_isNotEmpty() {
        EoRoot eo = ProviderConfigMapsDev.createEo();
        IEOScalar child = eo.createChild(S_LEVEL0);
        child.setLogLevel(LogLevel.INFO);
        child.info(S_MESSAGE);
        Assertions.assertThat(eo.getLog()).isNotEmpty();
    }
}
