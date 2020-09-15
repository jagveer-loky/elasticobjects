package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.calls.HostConfig;

/**
 * @Author Werner Diwischek
 * @since 12.10.2018.
 */
public class EOConfigMapHost extends EOConfigMap {
    public EOConfigMapHost(final EOConfigsCache eoConfigsCache)  {
        super(eoConfigsCache, HostConfig.class);
    }
}
