package org.fluentcodes.projects.elasticobjects.calls;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.models.ConfigConfigInterface;
import org.fluentcodes.projects.elasticobjects.models.Scope;
import org.junit.Test;

import java.util.Map;

public class HostBeanMapTest {
    public static final HostBeanMap HOST_BEAN_MAP = new HostBeanMap(Scope.TEST);

    @Test
    public void TEST_hostBeanMap__find_localhost__notNull() {
        HostBean bean = HOST_BEAN_MAP.find("localhost");
        Assertions.assertThat(bean).isNotNull();
    }

    @Test
    public void TEST_hostBeanMap_createConfigMap__find_localhost__notNull() {
        Map<String, ConfigConfigInterface> hostConfigMap = HOST_BEAN_MAP.createConfigMap();
        Assertions.assertThat(hostConfigMap.containsKey("localhost")).isTrue();
    }



}
