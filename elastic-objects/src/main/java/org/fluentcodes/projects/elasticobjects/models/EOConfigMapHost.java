package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.models.Model.NATURAL_ID;

/**
 * @Author Werner Diwischek
 * @since 12.10.2018.
 */
public class EOConfigMapHost extends EOConfigMap {
    public EOConfigMapHost(final EOConfigsCache eoConfigsCache)  {
        super(eoConfigsCache, HostConfig.class);
    }

    /*protected void addConfigByMap(final Map map) {
        String naturalId = (String)map.get(NATURAL_ID);
        if (naturalId == null) {
            throw new EoInternalException("No naturalid provided for FileConfig");
        }
        if (hasKey(naturalId)) {
            throw new EoInternalException("NaturalId " + naturalId + " already exists FileConfig.");
        }
        String modelKey =
                map.containsKey(ConfigImpl.CONFIG_MODEL_KEY) && map.get(ConfigImpl.CONFIG_MODEL_KEY) !=null
                        ? (String) map.get(ConfigImpl.CONFIG_MODEL_KEY)
                        : "HostConfig";
        if (modelKey.isEmpty()) {
            modelKey = "HostConfig";
        }
        ModelConfig modelConfig = getConfigsCache().findModel(modelKey);
        try {
            Class hostConfigClass = modelConfig.getModelClass();
            Constructor hostTypeConstructor = hostConfigClass.getConstructor(EOConfigsCache.class, Map.class);
            try {
                addConfig((HostConfig)hostTypeConstructor.newInstance(getConfigsCache(), map));
            } catch (InstantiationException e) {
                throw new EoInternalException(e);
            } catch (IllegalAccessException e) {
                throw new EoInternalException(e);
            } catch (InvocationTargetException e) {
                throw new EoInternalException(e);
            }
            catch (Exception e) {
                throw new EoInternalException(e);
            }
        } catch (NoSuchMethodException e) {
            throw new EoInternalException(e);
        }
    }*/
}
