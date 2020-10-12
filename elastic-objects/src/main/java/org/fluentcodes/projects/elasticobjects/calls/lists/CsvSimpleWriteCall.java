package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;

/**
 * Created by werner.diwischek on 03.12.16.
 */
public class CsvSimpleWriteCall extends ListWriteCall {
    private static final Logger LOG = LogManager.getLogger(CsvSimpleWriteCall.class);

    public CsvSimpleWriteCall()  {
        super();
    }

    protected CsvConfig getCsvConfig() {
        if (getConfig() instanceof CsvConfig) {
            return (CsvConfig) getConfig();
        }
        throw new EoException("Could not cast to 'CsvConfig': " + getConfig().getClass().getSimpleName());
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return CsvConfig.class;
    }


}
