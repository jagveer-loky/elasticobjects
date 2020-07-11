package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Werner on 10.10.2016.
 */
public class EOConfigMapFields extends EOConfigMap {
    public static final Logger LOG = LogManager.getLogger(EOConfigMapFields.class);

    public EOConfigMapFields(final EOConfigsCache eoConfigsCache)  {
        super(eoConfigsCache, FieldConfig.class);
    }
}
