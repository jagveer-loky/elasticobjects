package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.Scope;

public class ProviderRootDevScope {
    public static final ConfigMaps CONFIG_MAPS_DEV = new ConfigMaps(Scope.DEV);

    public static final EoRoot createEoWithClasses(Class... classes)  {
        return EoRoot.ofClass(CONFIG_MAPS_DEV, classes);
    }

    public static final EoRoot createEo()  {
        return EoRoot.of(CONFIG_MAPS_DEV);
    }

    public static final EO createEo(Object value)  {
        return EoRoot.ofValue(CONFIG_MAPS_DEV, value);
    }
}