package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.fluentcodes.projects.elasticobjects.models.Model.NATURAL_ID;

/**
 * @Author Werner Diwischek
 * @since 12.10.2018.
 */
public class EOConfigMapFile extends EOConfigMap {
    public static final Logger LOG = LogManager.getLogger(EOConfigMapFile.class);

    public EOConfigMapFile(final EOConfigsCache eoConfigsCache)  {
        super(eoConfigsCache, FileConfig.class);
    }

}
