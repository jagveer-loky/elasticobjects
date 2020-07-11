package org.fluentcodes.projects.elasticobjects.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.LogLevel;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.config.Scope;

public class TestProviderRootDev {
    public static final EOConfigsCache EO_CONFIGS = new EOConfigsCache(Scope.DEV);
    private static final Logger LOG = LogManager.getLogger(TestProviderRootDev.class);

    public static final EoRoot createEoWithClasses(Class... classes)  {
        return new EoRoot(EO_CONFIGS, LogLevel.WARN, classes);
    }

    public static final EoRoot createEo()  {
        return EoRoot.ofMap(EO_CONFIGS);
    }

    public static final EO createEo(Object value)  {
        return EoRoot.ofValue(EO_CONFIGS, value);
    }
}