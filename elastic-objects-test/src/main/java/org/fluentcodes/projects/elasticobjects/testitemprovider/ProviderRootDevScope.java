package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.Scope;

public class ProviderRootDevScope {
    public static final EOConfigsCache EO_CONFIGS = new EOConfigsCache();

    public static final EoRoot createEo(Class... classes)  {
        return EoRoot.OF(EO_CONFIGS, classes);
    }

    public static final EoRoot createEo()  {
        return EoRoot.OF(EO_CONFIGS);
    }

    public static final EO createEo(Object value)  {
        return EoRoot.OF(EO_CONFIGS, value);
    }
}