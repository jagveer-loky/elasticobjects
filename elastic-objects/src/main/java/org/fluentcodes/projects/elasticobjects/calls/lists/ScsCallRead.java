package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.models.Config;

import java.util.List;

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

    public ScsConfig getScsConfig()  {
        return ((ScsConfig) getConfig());
    }

    public Object execute(EO eo) {
        resolve(eo.getConfigsCache());
        super.execute(eo);
        List rawInput = getScsConfig().read();
        return transform(rawInput);
    }
}
