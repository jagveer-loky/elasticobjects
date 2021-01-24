package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.ModelBeanMap;

public class ProviderRootDevScope {
    public static final ConfigMaps EO_CONFIGS = new ConfigMaps();
    public static final ModelBeanMap MODEL_BEAN_MAP = new ModelBeanMap();

    public static final EoRoot createEoWithClasses(Class... classes)  {
        return EoRoot.ofClass(EO_CONFIGS, classes);
    }

    public static final EoRoot createEo()  {
        return EoRoot.of(EO_CONFIGS);
    }

    public static final EO createEo(Object value)  {
        return EoRoot.ofValue(EO_CONFIGS, value);
    }
}