package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.Map;

/**
 * Created by Werner on 10.10.2016.
 */
public class EOConfigsImmutable extends EOConfigs {
    public static final Logger LOG = LogManager.getLogger(EOConfigsImmutable.class);

    public EOConfigsImmutable(final EOConfigsCache eoConfigsCache, final Class<? extends Config> configClass, final Scope scope) throws Exception {
        super(
                eoConfigsCache,
                configClass,
                Collections.unmodifiableMap(new EOConfigReader(eoConfigsCache, configClass).read()),
                scope);
    }
}
