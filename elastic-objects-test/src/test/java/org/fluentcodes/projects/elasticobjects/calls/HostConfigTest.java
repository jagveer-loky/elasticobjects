package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IConfigurationTests;
import org.junit.Test;

/**
 * Created by Werner on 21.11.2021.
 */
public class HostConfigTest implements IConfigurationTests {

    @Override
    public Class<?> getModelConfigClass() {
        return HostConfig.class;
    }
    @Override
    @Test
    public void create_throwsEoException() {
        assertCreateThrowingException();
    }
    @Override
    @Test
    public void compareModelConfig() {
        assertModelConfigEqualsPersisted();
    }
    @Override
    @Test
    public void compareBeanFromModelConfig() {
        assertBeanFromModelConfigEqualsPersisted();
    }

    @Test
    public void givenConfigEntries_whenResolve_thenNoErrors() {
        ConfigChecks.resolveConfigs(HostConfig.class);
    }

    @Test
    public void resolveConfigurations() {
        ConfigChecks.resolveConfigurations(HostConfig.class);
    }

    @Override
    @Test
    public void compareConfigurations() {
        assertLoadedConfigurationsEqualsPersisted();
    }

    @Test
    public void elasticobjects__compare__xpected() {
        assertConfigBeanEqualsPersisted("elasticobjects");
    }

    @Test
    public void dummyftp__compare__xpected() {
        assertConfigBeanEqualsPersisted("dummyftp");
    }

    @Test
    public void dummyftpurl__compare__xpected() {
        assertConfigBeanEqualsPersisted("dummyftpurl");
    }

}
