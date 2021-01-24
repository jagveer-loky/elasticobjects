package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.ConfigBeanMap;
import org.fluentcodes.projects.elasticobjects.models.Scope;

import java.util.Map;

/**
 * Created by Werner on 21.1.2021.
 */

public class HostBeanMap extends ConfigBeanMap<HostBean> {
    public HostBeanMap(final Scope scope)  {
        super(scope, HostConfig.class);
    }

    @Override
    protected HostBean createBean(final String naturalId, final Map valueMap) {
        return new HostBean(naturalId, valueMap);
    }
}
