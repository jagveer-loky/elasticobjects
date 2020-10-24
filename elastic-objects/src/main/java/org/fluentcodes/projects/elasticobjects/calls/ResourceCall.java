package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.EO;
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
public abstract class ResourceCall extends CallImpl {
    public static final String CONFIG_KEY = "configKey";
    public static final String PERMISSIONS = "permissions";
    private Config config;
    private String configKey;
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

    @Override
    public void setByString(final String values) {
        if (values == null||values.isEmpty()) {
            throw new EoException("Set by empty input values");
        }
        String[] array = values.split(", ");
        if (array.length>5) {
            throw new EoException("Short form should have form '<configKey>[, <sourcePath>][,<targetPath>][,<condition>]' with max length 3 but has size " + array.length + ": '" + values + "'." );
        }
        if (array.length>0) {
            configKey = array[0];
        }
        if (array.length>1) {
            setTargetPath( array[1]);
        }
        if (array.length>2) {
            setCondition( array[2]);
        }
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
}
