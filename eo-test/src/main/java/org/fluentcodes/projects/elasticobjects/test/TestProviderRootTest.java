package org.fluentcodes.projects.elasticobjects.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.config.Scope;

public class TestProviderRootTest {
    public static final EOConfigsCache EO_CONFIGS = new EOConfigsCache(Scope.TEST);
    private static final Logger LOG = LogManager.getLogger(TestProviderRootTest.class);

    public static final EO createEoWithClasses(Class... classes) {
        try {
            return new EoRoot(EO_CONFIGS, classes);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final EO createEo() {
        try {
            return new EoRoot(EO_CONFIGS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final EO createEo(Object value) {
        try {
            return EoRoot.ofValue(EO_CONFIGS, value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}