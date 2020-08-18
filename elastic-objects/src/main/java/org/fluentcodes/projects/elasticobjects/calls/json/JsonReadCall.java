package org.fluentcodes.projects.elasticobjects.calls.json;

import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.models.Config;

/**
 * Created by werner.diwischek on 20.03.2018.
 * Refactored 10.07.2020
 */
public class JsonReadCall extends FileReadCall {
    public JsonReadCall()  {
        super();
    }

    public JsonReadCall(final String configKey) {
        super(configKey);
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return JsonConfig.class;
    }

}
