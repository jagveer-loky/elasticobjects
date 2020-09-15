package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.models.ConfigImpl.EXPOSE;
import static org.fluentcodes.projects.elasticobjects.models.Model.NATURAL_ID;

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
        map.put(FieldConfig.FIELD_KEY, "log");
        map.put(FieldConfig.MODEL_KEYS, "String");
        addConfigByMap(map);
    }
    protected void addConfigByMap(final Map map) {
        String naturalId = (String)map.get(NATURAL_ID);
        if (naturalId == null) {
            throw new EoInternalException("No naturalid provided for FileConfig");
        }
        if (hasKey(naturalId)) {
            throw new EoInternalException("NaturalId " + naturalId + " already exists FileConfig.");
        }
        if (!map.containsKey(EXPOSE)) {
            map.put(EXPOSE, Expose.NONE.name());
        }
        try {
            Class configurationClass = FieldConfig.class;
            Constructor configurationConstructor = configurationClass.getConstructor(EOConfigsCache.class, Map.class);
            try {
                addConfig((Config)configurationConstructor.newInstance(getConfigsCache(), map));
            } catch (InstantiationException|IllegalAccessException|InvocationTargetException e) {
                throw new EoInternalException("Problem with '" + naturalId + " in '/'FieldConfig'", e);
            }
            catch (Exception e) {
                throw new EoInternalException(e);
            }
        } catch (NoSuchMethodException e) {
            throw new EoInternalException("Problem with '" + naturalId + "'/'FieldConfig' ", e);
        }
    }
}
