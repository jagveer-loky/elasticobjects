package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.models.Config;

/**
 * Created by werner.diwischek on 03.12.16.
 */
public class ScsCallWrite extends ListCallRead {
    private static final Logger LOG = LogManager.getLogger(ScsCallWrite.class);

    public ScsCallWrite()  {
        super();
    }

    public ScsConfig getScsConfig() {
        return ((ScsConfig) getConfig());
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return ScsConfig.class;
    }
}
