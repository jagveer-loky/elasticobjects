package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;

import java.util.List;

/**
 * Created by Werner on 10.10.2016.
 * Elementary call with mapping configuration keys to configuration via constructor.
 */
public abstract class CallResource extends CallImpl {
    private static final Logger LOG = LogManager.getLogger(CallResource.class);
    private Config config;
    private String configKey;
    private PermissionType permissions;

    public CallResource() {
    }
    public CallResource(PermissionType permissionType) {
        this.permissions = permissionType;
    }

    public CallResource(PermissionType permissionType, final String configKey) {
        this.permissions = permissionType;
        this.configKey = configKey;
    }

    public boolean hasPermissions(final List<String> roles) {
        return ((ConfigResourcesImpl)getConfig()).getRolePermissions().hasPermissions(permissions, roles);
    }

    protected void init(final EO eo)  {
        resolve(eo.getConfigsCache());
        hasPermissions(eo.getRoles());
    }

    public CallResource resolve(EOConfigsCache cache) {
        if (config!=null) {
            return this;
        }
        this.config = (Config) cache.find(getConfigClass(), getConfigKey());
        return this;
    }

    public String getConfigKey() {
        return configKey;
    }

    public CallResource setConfigKey(String configKey) {
        this.configKey = configKey;
        return this;
    }

    public EOConfigsCache getProvider() {
        return getConfig().getConfigsCache();
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(EOConfigsCache cache) {
        this.config = (Config)cache.find(getConfigClass(), getConfigKey());
    }



    public Class<? extends Config> getConfigClass()  {
        throw new EoInternalException("Problem with the implementation of call '" + this.getClass().getSimpleName() + "': Method configClass should be overwritten");
    }

    public boolean hasConfig() {
        return !(config == null);
    }
}
