package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.config.CsvConfig;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;

/**
 * Created by werner.diwischek on 03.12.16.
 */
public class CsvCall extends ListCall {
    private static final Logger LOG = LogManager.getLogger(CsvCall.class);

    public CsvCall(EOConfigsCache provider, String cacheKey)  {
        super(provider, cacheKey);
    }

    public CsvConfig getCsvConfig() {
        return ((CsvConfig) getConfig());
    }
}
