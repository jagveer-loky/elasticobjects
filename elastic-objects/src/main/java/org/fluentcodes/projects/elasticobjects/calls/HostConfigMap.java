package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.ConfigConfigInterface;
import org.fluentcodes.projects.elasticobjects.models.ConfigConfigMap;
import org.fluentcodes.projects.elasticobjects.models.Scope;

import java.util.Map;

/**
 * Created by Werner on 22.1.2021.
 */
public class HostConfigMap extends ConfigConfigMap {
    public HostConfigMap(Scope scope)  {
        super(scope, HostConfig.class);
    }

    @Override
    protected Map<String, ConfigConfigInterface> initMap() {
        return new HostBeanMap(getScope()).createConfigMap();
    }
}
