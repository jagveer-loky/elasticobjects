package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.tools.xpect.IOString;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.fluentcodes.projects.elasticobjects.models.Model.NATURAL_ID;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.MODEL_KEY;

/**
 * @Author Werner Diwischek
 * @since 12.10.2018.
 */
public class EOConfigMapFile extends EOConfigMap {
    public static final Logger LOG = LogManager.getLogger(EOConfigMapFile.class);
    public static final String CONFIG_MODEL_KEY = "configModelKey";

    public EOConfigMapFile(final EOConfigsCache eoConfigsCache)  {
        super(eoConfigsCache, FileConfig.class);
    }

    protected void addConfigByMap(final Map map) {
        String naturalId = (String)map.get(NATURAL_ID);
        if (naturalId == null) {
            throw new EoInternalException("No naturalid provided for FileConfig");
        }
        if (hasKey(naturalId)) {
            throw new EoInternalException("NaturalId " + naturalId + " already exists FileConfig.");
        }
        String modelKey =
                map.containsKey(CONFIG_MODEL_KEY) && map.get(CONFIG_MODEL_KEY) !=null
                        ? (String) map.get(CONFIG_MODEL_KEY)
                        : "FileConfig";
        if (modelKey.isEmpty()) {
            modelKey = "FileConfig";
        }
        else {
            System.out.println("");
        }
        ModelConfig modelConfig = getConfigsCache().findModel(modelKey);
        try {
            Class fileConfigClass = modelConfig.getModelClass();
            Constructor fileTypeConstructor = fileConfigClass.getConstructor(EOConfigsCache.class, Map.class);
            try {
                addConfig((FileConfig)fileTypeConstructor.newInstance(getConfigsCache(), map));
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
    }
}
