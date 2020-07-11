package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Werner on 10.10.2016.
 */
public class EOConfigMapImmutable extends EOConfigMap {
    public static final Logger LOG = LogManager.getLogger(EOConfigMapImmutable.class);

    public EOConfigMapImmutable(final EOConfigsCache eoConfigsCache, final Class<? extends Config> configClass)  {
        super(
                eoConfigsCache,
                configClass);
        if (getScope() == Scope.DEV) {
            return;
        }
        addJsonConfigs();
    }
}
