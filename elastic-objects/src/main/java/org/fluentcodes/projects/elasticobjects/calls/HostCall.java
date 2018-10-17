package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.config.HostConfig;

/**
 * Created by werner.diwischek on 03.12.16.
 */
public class HostCall extends CallIO {

    public HostCall(EOConfigsCache provider, String cacheKey) throws Exception {
        super(provider, cacheKey);
    }

    public HostConfig getHostConfig() throws Exception {
        return ((HostConfig) getConfig());
    }
}
