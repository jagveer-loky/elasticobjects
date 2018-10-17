package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by Werner on 10.10.2016.
 */
public class EOConfigsField extends EOConfigs {
    public static final Logger LOG = LogManager.getLogger(EOConfigsField.class);

    public EOConfigsField(final EOConfigsCache eoConfigsCache, final Scope scope) throws Exception {
        super(eoConfigsCache, FieldConfig.class, scope);
    }

    public Config find(String key) throws Exception {
        Config cached = super.find(key);
        return cached;
    }

}
