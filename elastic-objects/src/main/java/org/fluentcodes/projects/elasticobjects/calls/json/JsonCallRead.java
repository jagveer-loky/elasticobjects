package org.fluentcodes.projects.elasticobjects.calls.json;

import org.fluentcodes.projects.elasticobjects.calls.files.FileCallRead;
import org.fluentcodes.projects.elasticobjects.models.Config;

/**
 * Created by werner.diwischek on 20.03.2018.
 * Refactored 10.07.2020
 */
public class JsonCallRead extends FileCallRead {
    public JsonCallRead()  {
        super();
    }

    public JsonCallRead(final String configKey) {
        super(configKey);
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return JsonConfig.class;
    }

}
