package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

/**
 * Created by Werner on 10.10.2016.
 */
public class EOConfigMapFields extends EOConfigMap {
    public static final Logger LOG = LogManager.getLogger(EOConfigMapFields.class);

    public EOConfigMapFields(final EOConfigsCache eoConfigsCache)  {
        super(eoConfigsCache, FieldConfig.class);
        addBasicConfigs();
    }

    protected void addBasicConfigs() {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> eoParamsMap = new HashMap<>();
        map.put(Model.NATURAL_ID, "log");
        map.put(F_FIELD_KEY, "log");
        map.put(F_MODEL_KEYS, "String");
        super.addConfigByMap(map);
    }
}
