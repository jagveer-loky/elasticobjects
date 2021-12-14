package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.IEOObject;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.Models;
import org.fluentcodes.projects.elasticobjects.models.Scope;
import org.junit.Assert;

public class ProviderConfigMapsDev {
    public static final ConfigMaps CONFIG_MAPS_DEV = new ConfigMaps(Scope.DEV);

    public static final EoRoot createEoWithClasses(Class... classes) {
        return EoRoot.ofClass(CONFIG_MAPS_DEV, classes);
    }

    public static final Models createModels(Class... classes) {
        return new Models(CONFIG_MAPS_DEV, classes);
    }

    public static final EoRoot createEo() {
        return EoRoot.of(CONFIG_MAPS_DEV);
    }

    public static final EoRoot createEo(Object value) {
        return EoRoot.ofValue(CONFIG_MAPS_DEV, value);
    }

    public static final EoRoot assertCompare(IEOObject in, String toCompare) {
        String stringify = new EOToJSON().toJson(in);
        Assert.assertEquals(toCompare, stringify);
        return createEo(stringify);
    }
}