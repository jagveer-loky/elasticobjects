package org.fluentcodes.projects.elasticobjects.calls;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.models.Scope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.junit.Test;

public class HostConfigMapTest {
    public static final HostConfigMap HOST_CONFIG_MAP = new HostConfigMap(Scope.TEST);

    @Test
    public void TEST_hostConfigMap__find_localhost__notNull() {
        HostConfig bean = (HostConfig) HOST_CONFIG_MAP.find("localhost");
        Assertions.assertThat(bean).isNotNull();
    }
}
