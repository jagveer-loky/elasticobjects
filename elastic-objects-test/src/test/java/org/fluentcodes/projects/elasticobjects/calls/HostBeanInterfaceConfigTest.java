package org.fluentcodes.projects.elasticobjects.calls;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.tools.xpect.XpectString;
import org.junit.Test;

/**
 * Created by Werner on 11.10.2016.
 */
public class HostBeanInterfaceConfigTest {
    @Test
    public void createByModelConfig_throwsException()  {
        ModelConfigChecks.createThrowsException(HostConfig.class);
    }

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(HostConfig.class);
    }

    @Test
    public void givenConfigEntries_whenResolve_thenNoErrors()  {
        ConfigChecks.resolveConfigs(HostConfig.class);
    }

    @Test
    public void resolveConfigurations()  {
        ConfigChecks.resolveConfigurations(HostConfig.class);
    }

    @Test
    public void compareConfigurations()  {
        ConfigChecks.compareConfigurations(HostConfig.class);
    }

    @Test
    public void elasticobjects__compare__xpected()  {
        ConfigMaps cache = ProviderRootTestScope.EO_CONFIGS;
        HostConfig config = cache.findHost("elasticobjects");
        Assertions.assertThat(config).isNotNull();
        Assertions.assertThat(config.getPassword()).isNull();
        Assertions.assertThat(config.getPasswordReal()).isNull();
        Assertions.assertThat(config.getUser()).isNull();
        Assertions.assertThat(config.getProtocol()).isEqualTo("http");
        Assertions.assertThat(config.getPort()).isEqualTo(80);
        new XpectString().compareAsString(config.toString());
    }

    @Test
    public void dummyftp__compare__xpected()  {
        ConfigMaps cache = ProviderRootTestScope.EO_CONFIGS;
        HostConfig config = cache.findHost("dummyftp");
        Assertions.assertThat(config).isNotNull();
        Assertions.assertThat(config.getHostName()).isEqualTo("dummyftp");
        Assertions.assertThat(config.getPassword()).isEqualTo("test1234");
        Assertions.assertThat(config.getPasswordReal()).isEqualTo("test1234");
        Assertions.assertThat(config.getUser()).isEqualTo("dummy");
        Assertions.assertThat(config.getProtocol()).isEqualTo("ftp");
        Assertions.assertThat(config.getUrl()).isNull();
        Assertions.assertThat(config.getPort()).isEqualTo(21);
        Assertions.assertThat(config.getUrlPath()).isEqualTo("ftp://dummyftp:21");
        new XpectString().compareAsString(config.toString());
    }

    @Test
    public void dummyftpurl__compare__xpected()  {
        ConfigMaps cache = ProviderRootTestScope.EO_CONFIGS;
        HostConfig config = cache.findHost("dummyftpurl");
        Assertions.assertThat(config).isNotNull();
        Assertions.assertThat(config.getHostName()).isNull();
        Assertions.assertThat(config.getPassword()).isEqualTo("test1234");
        Assertions.assertThat(config.getPasswordReal()).isEqualTo("test1234");
        Assertions.assertThat(config.getUser()).isEqualTo("dummy");
        Assertions.assertThat(config.getProtocol()).isNull();
        Assertions.assertThat(config.getUrl()).isEqualTo("ftp://dummyftp:21");
        Assertions.assertThat(config.getPort()).isNull();
        Assertions.assertThat(config.getUrlPath()).isEqualTo("ftp://dummyftp:21");
        new XpectString().compareAsString(config.toString());
    }

    @Test
    public void __compareConfiguration_elasticobjects__xpected()  {
        ConfigChecks.compareConfiguration(HostConfig.class, "elasticobjects");
    }


}
