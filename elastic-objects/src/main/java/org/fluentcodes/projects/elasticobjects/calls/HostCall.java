package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/**
 * Created by werner.diwischek on 8.11.2020.
 */
public abstract class HostCall extends CallImpl {
    private String hostConfigKey;
    private HostConfig hostConfig;

    public HostCall() {
        super();
    }

    public HostCall(final String configKey) {
        super();
        setHostConfigKey(configKey);
    }

    protected HostConfig initHostConfig(final PermissionType permissionType, final EO eo) {
        if (!hasHostConfigKey()) {
            throw new EoException("Empty key for host");
        }
        hostConfig = eo.getConfigsCache().findHost(hostConfigKey);
        hostConfig.hasPermissions(permissionType, eo.getRoles());
        return hostConfig;
    }

    public HostConfig getHostConfig()  {
        return hostConfig;
    }
    public String getHostConfigKey() {
        return hostConfigKey;
    }
    public boolean hasHostConfigKey() {
        return hostConfigKey!=null && ! hostConfigKey.isEmpty();
    }

    public void setHostConfigKey(String hostConfigKey) {
        this.hostConfigKey = hostConfigKey;
    }
}
