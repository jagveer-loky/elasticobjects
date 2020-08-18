package org.fluentcodes.projects.elasticobjects.calls.csv;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListReadCall;
import org.fluentcodes.projects.elasticobjects.calls.lists.ScsConfig;
import org.fluentcodes.projects.elasticobjects.models.Config;

import java.security.Permissions;
import java.util.List;

/**
 * Created by werner.diwischek on 03.12.16.
 */
public class CsvReadCall extends ListReadCall {
    private static final Logger LOG = LogManager.getLogger(CsvReadCall.class);

    public CsvReadCall()  {
        super();
    }

    public CsvReadCall(final String configKey)  {
        super(configKey);
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return CsvConfig.class;
    }

    public CsvConfig getCsvConfig() {
        return ((CsvConfig) getConfig());
    }

    public Object execute(EO eo) {
        resolve(eo.getConfigsCache());
        super.execute(eo);
        List rawInput = getCsvConfig().read();
        return transform(rawInput);
    }
}
