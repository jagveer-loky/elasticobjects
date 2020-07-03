package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.config.ScsConfig;

/**
 * Created by werner.diwischek on 03.12.16.
 */
public class ScsCall extends ListCall {
    private static final Logger LOG = LogManager.getLogger(ScsCall.class);

    public ScsCall(EOConfigsCache provider, String cacheKey)  {
        super(provider, cacheKey);
    }

    public ScsConfig getScsConfig() {
        return ((ScsConfig) getConfig());
    }
}
