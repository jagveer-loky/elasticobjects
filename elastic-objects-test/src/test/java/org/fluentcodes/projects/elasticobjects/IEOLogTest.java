package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL0;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_MESSAGE;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_STRING;

/**
 * Created by werner.diwischek on 12.12.17.
 */
public class IEOLogTest {

    @Test
    public void ____getLogLevel_WARN() {
        EO eo = ProviderConfigMapsDev.createEo();
        Assertions.assertThat(eo.getLogLevel()).isEqualTo(LogLevel.WARN);
    }

    @Test
    public void ERROR__LogLevelIsPersist() {
        EO eo = ProviderConfigMapsDev.createEo();
        eo.setLogLevel(LogLevel.ERROR);
        EO fromJson = ProviderConfigMapsDev.assertCompare(eo, "{\n" +
                "  \"(LogLevel)_logLevel\": \"ERROR\"\n" +
                "}");
        Assertions.assertThat(fromJson.getLogLevel()).isEqualTo(LogLevel.ERROR);
    }

    @Test
    public void ERROR__error__logNotEmpty() {
        EO eo = ProviderConfigMapsDev.createEo();
        eo.setLogLevel(LogLevel.ERROR);
        eo.error("test");
        Assert.assertFalse(eo.getLog().isEmpty());
    }

    @Test
    public void ERROR__info__logEmpty() {
        EO eo = ProviderConfigMapsDev.createEo();
        eo.setLogLevel(LogLevel.ERROR);
        eo.info("test");
        Assert.assertTrue(eo.getLog().isEmpty());
    }

    @Test
    public void __error__errorLevel_ERROR() {
        EO eo = ProviderConfigMapsDev.createEo();
        eo.error(S_MESSAGE, new Exception(S_STRING));
        Assertions.assertThat(eo.getLog()).isNotEmpty();
        Assertions.assertThat(eo.getErrorLevel()).isEqualTo(LogLevel.ERROR);
    }

    @Test
    public void __error__log_isNotEmpty() {
        EO eo = ProviderConfigMapsDev.createEo();
        eo.error(S_MESSAGE);
        Assertions.assertThat(eo.getLog()).isNotEmpty();
    }

    @Test
    public void __warn_exception__log_isNotEmpty() {
        EO eo = ProviderConfigMapsDev.createEo();
        eo.warn(S_MESSAGE, new Exception(S_STRING));
        Assertions.assertThat(eo.getLog()).isNotEmpty();
    }


    @Test
    public void __warn__log_isNotEmpty() {
        EO eo = ProviderConfigMapsDev.createEo();
        eo.warn(S_MESSAGE);
        Assertions.assertThat(eo.getLog()).isNotEmpty();
    }

    @Test
    public void __info__log_isEmpty() {
        EO eo = ProviderConfigMapsDev.createEo();
        eo.info(S_MESSAGE);
        Assertions.assertThat(eo.getLog()).isEmpty();
    }

    @Test
    public void __debug__log_isEmpty() {
        EO eo = ProviderConfigMapsDev.createEo();
        eo.debug(S_MESSAGE);
        Assertions.assertThat(eo.getLog()).isEmpty();
    }

    @Test
    public void child__warn__log_isNotEmpty() {
        EO eo = ProviderConfigMapsDev.createEo();
        EO eoChild = eo
                .setEmpty(S_LEVEL0);
        Assertions.assertThat(eo.getLogLevel()).isEqualTo(LogLevel.WARN);
        eoChild.warn(S_MESSAGE);
        Assertions.assertThat(eo.getLog()).isNotEmpty();
    }

    @Test
    public void child__info__log_isEmpty() {
        EO eo = ProviderConfigMapsDev.createEo();
        EO eoChild = eo
                .setEmpty(S_LEVEL0);
        eoChild.info(S_MESSAGE);
        Assertions.assertThat(eo.getLog()).isEmpty();
    }

    @Test
    public void child_LogLevel_INFO__info__log_isNotEmpty() {
        EO eo = ProviderConfigMapsDev.createEo();
        EO child = eo.setEmpty(S_LEVEL0);
        child.setLogLevel(LogLevel.INFO);
        child.info(S_MESSAGE);
        Assertions.assertThat(eo.getLog()).isNotEmpty();
    }
}
