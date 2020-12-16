package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.Scope;

public class ProviderRootDevScope {
    public static final EOConfigsCache EO_CONFIGS = new EOConfigsCache();

    public static final EoRoot createEo(Class... classes)  {
        return new EoRoot(EO_CONFIGS, classes);
    }

    public static final EoRoot createEo()  {
        return new EoRoot(EO_CONFIGS);
    }

    public static final EO createEo(Object value)  {
        return new EoRoot(EO_CONFIGS, value);
    }
}