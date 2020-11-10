package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.calls.PermissionProperties;
import org.fluentcodes.projects.elasticobjects.calls.PermissionRole;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.calls.ResourceConfig.ROLE_PERMISSIONS;

/**
 * Created by Werner on 01.11.2020.
 */
public class ModelConfigDbObject extends ModelConfigObject implements ModelConfigDbProperties, PermissionProperties {
    public final static String HOST_CONFIG_KEY = "hostConfigKey";
    private final PermissionRole permissionRole;
    private final String hostConfigKey;
    public ModelConfigDbObject(EOConfigsCache configsCache, Map map) {
        super(configsCache, map);
        this.permissionRole = new PermissionRole((Map)map.get(ROLE_PERMISSIONS));
        this.hostConfigKey = (String)map.get(HOST_CONFIG_KEY);
    }

    public PermissionRole getPermissionRole() {
        return permissionRole;
    }

    public String getHostConfigKey() {
        return hostConfigKey;
    }

    public boolean hasHostConfigKey() {
        return hostConfigKey!=null && !hostConfigKey.isEmpty();
    }
}
