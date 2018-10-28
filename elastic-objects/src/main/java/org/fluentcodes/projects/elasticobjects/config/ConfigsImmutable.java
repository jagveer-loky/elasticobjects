package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;

/**
 * Created by Werner on 10.10.2016.
 */
public class ConfigsImmutable extends EOConfigs {
    public static final Logger LOG = LogManager.getLogger(ConfigsImmutable.class);

    public ConfigsImmutable(final EOConfigsCache eoConfigsCache, final Class<? extends Config> configClass, final Scope scope) throws Exception {
        super(
                eoConfigsCache,
                configClass,
                Collections.unmodifiableMap(new EOConfigReader(eoConfigsCache, configClass).read()),
                scope);
    }
}
