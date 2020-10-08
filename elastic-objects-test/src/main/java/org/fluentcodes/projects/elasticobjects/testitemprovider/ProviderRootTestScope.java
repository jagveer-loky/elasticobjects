package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.Scope;

public class ProviderRootTestScope {
    public static final EOConfigsCache EO_CONFIGS = new EOConfigsCache(Scope.TEST);

    public static final EO createEoWithClasses(Class... classes) {
        return new EoRoot(EO_CONFIGS, classes);
    }

    public static final EO createEo() {
        return new EoRoot(EO_CONFIGS);
    }

    public static final EO createEo(Object value) {
        return new EoRoot(EO_CONFIGS, value);
    }

    public static final ModelConfig findModel (final Class eoClass) {
        return EO_CONFIGS.findModel(eoClass);
    }

}