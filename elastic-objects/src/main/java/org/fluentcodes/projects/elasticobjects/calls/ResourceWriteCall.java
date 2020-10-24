package org.fluentcodes.projects.elasticobjects.calls;


/**
 * Created by werner.diwischek on 22.10.2020.
 */
public abstract class ResourceWriteCall extends ResourceCall {
    public ResourceWriteCall() {
        super(PermissionType.READ);
    }

    public ResourceWriteCall(final String configKey) {
        super(PermissionType.WRITE);
        setConfigKey(configKey);
    }
}
