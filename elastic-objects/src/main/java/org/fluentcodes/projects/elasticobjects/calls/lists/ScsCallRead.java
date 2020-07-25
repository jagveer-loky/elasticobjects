package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.config.Config;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.config.Permissions;
import org.fluentcodes.projects.elasticobjects.config.ScsConfig;

/**
 * Created by werner.diwischek on 03.12.16.
 */
public class ScsCallRead extends ListCallRead {
    private static final Logger LOG = LogManager.getLogger(ScsCallRead.class);

    public ScsCallRead()  {
        super();
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return ScsConfig.class;
    }
}
