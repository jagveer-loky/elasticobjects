package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.models.Config;

import java.util.List;

/**
 * Created by werner.diwischek on 03.12.16.
 */
public class ScsReadCall extends ListReadCall {
    private static final Logger LOG = LogManager.getLogger(ScsReadCall.class);

    public ScsReadCall()  {
        super();
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return FileConfig.class;
    }

    @Override
    public Object execute(EO eo) {
        resolve(eo.getConfigsCache());
        super.execute(eo);
        List rawInput = ((ScsConfig)getConfig()).read();
        return transform(rawInput);
    }
}
