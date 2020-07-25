package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.calls.file.FileCallRead;
import org.fluentcodes.projects.elasticobjects.config.Config;
import org.fluentcodes.projects.elasticobjects.config.JsonConfig;

/**
 * Created by werner.diwischek on 20.03.2018.
 * Refactored 10.07.2020
 */
public class JsonCallRead extends FileCallRead {
    public JsonCallRead()  {
        super();
    }

    public JsonCallRead(final String configKey) {
        super();
        setConfigKey(configKey);
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return JsonConfig.class;
    }

}
