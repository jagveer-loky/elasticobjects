package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.templates.KeepCalls;
import org.fluentcodes.projects.elasticobjects.calls.templates.Parser;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;

import java.util.List;

/**
 * Created by Werner on 10.10.2016.
 * Elementary call with mapping configuration keys to configuration via constructor.
 */
public abstract class ResourceCall extends CallImpl implements CallKeep {
    public static final String CONFIG_KEY = "configKey";
    public static final String PERMISSIONS = "permissions";
    private Config config;
    private String configKey;
    private KeepCalls keepCall;
    private PermissionType permissions;

    public ResourceCall() {
    }
    public ResourceCall(PermissionType permissionType) {
        this.permissions = permissionType;
    }

    public ResourceCall(PermissionType permissionType, final String configKey) {
        this.permissions = permissionType;
        this.configKey = configKey;
    }

    public boolean hasPermissions(final List<String> roles) {
        return ((ConfigResourcesImpl)getConfig()).getRolePermissions().hasPermissions(permissions, roles);
    }

    @Override
    public boolean init(final EO eo)  {
        resolve(eo);
        hasPermissions(eo.getRoles());
        return super.init(eo);
    }

    protected ResourceCall resolve(EO eo) {
        if (config!=null) {
            return this;
        }
        this.configKey = Parser.replacePathValues(this.configKey, eo);
        this.config = (Config) eo.getConfigsCache().find(getConfigClass(), getConfigKey());
        return this;
    }

    protected static String replace(final String value) {
        return Parser.replacePathValues(value, (EO)null);
    }

    public String getConfigKey() {
        return configKey;
    }

    public ResourceCall setConfigKey(String configKey) {
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


    public boolean hasKeepCall() {
        return keepCall !=null  && keepCall != KeepCalls.NONE;
    }

    public KeepCalls getKeepCall() {
        return keepCall;
    }

    public void setKeepCall(KeepCalls keepCalls) {
        this.keepCall = keepCalls;
    }

}
