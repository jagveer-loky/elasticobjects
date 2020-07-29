package org.fluentcodes.projects.elasticobjects.fileprovider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.LogLevel;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.config.ModelConfig;
import org.fluentcodes.projects.elasticobjects.config.Scope;

public class TestProviderRootTest {
    public static final EOConfigsCache EO_CONFIGS = new EOConfigsCache(Scope.TEST);
    private static final Logger LOG = LogManager.getLogger(TestProviderRootTest.class);

    public static final EO createEoWithClasses(Class... classes) {
        return new EoRoot(EO_CONFIGS, classes);
    }

    public static final EO createEo() {
        return new EoRoot(EO_CONFIGS);
    }

    public static final EO createEo(Object value) {
        EoRoot eo =  EoRoot.ofValue(EO_CONFIGS, value);
        eo.setLogLevel(LogLevel.WARN);
        return eo;
    }

    public static final ModelConfig findModel (final Class eoClass) {
        return EO_CONFIGS.findModel(eoClass);
    }

}