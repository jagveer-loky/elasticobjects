package org.fluentcodes.projects.elasticobjects.calls;


/**
 * Created by werner.diwischek on 9.7.2020.
 */
public abstract class ResourceReadCall extends ResourceCall {
    public ResourceReadCall() {
        super(PermissionType.READ);
    }

    public ResourceReadCall(final String configKey) {
        super(PermissionType.READ);
        setConfigKey(configKey);
    }
}
