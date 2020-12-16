package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.calls.PermissionConfigInterface;
import org.fluentcodes.projects.elasticobjects.calls.PermissionConfigInterfaceMethods;
import org.fluentcodes.projects.elasticobjects.calls.PermissionRole;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.calls.PermissionConfig.ROLE_PERMISSIONS;

/**
 * Created by Werner on 01.11.2020.
 */
public class ModelConfigDbObject extends ModelConfigObject implements PermissionConfigInterfaceMethods {
    public final static String HOST_CONFIG_KEY = "hostConfigKey";
    private final PermissionRole permissionRole;
    private final String hostConfigKey;
    public ModelConfigDbObject(Map map) {
        this(new ModelBean(map));
    }

    public ModelConfigDbObject(ConfigBean bean) {
        this((ModelBean) bean);
    }

    public ModelConfigDbObject(ModelBean bean) {
        super(bean);
        this.permissionRole = null;
        this.hostConfigKey = null;
        //this.permissionRole = new PermissionRole((Map)map.get(ROLE_PERMISSIONS));
        //this.hostConfigKey = (String)map.get(HOST_CONFIG_KEY);
    }

    public PermissionRole getRolePermissions() {
        return permissionRole;
    }

    public String getHostConfigKey() {
        return hostConfigKey;
    }

    public boolean hasHostConfigKey() {
        return hostConfigKey!=null && !hostConfigKey.isEmpty();
    }
}
