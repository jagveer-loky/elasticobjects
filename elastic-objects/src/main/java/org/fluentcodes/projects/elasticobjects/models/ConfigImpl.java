package org.fluentcodes.projects.elasticobjects.models;


import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Werner on 10.10.2016.
 */
public abstract class ConfigImpl extends ModelImpl implements Config {
    public static final String CONFIG_MODEL_KEY = "configModelKey";
    public static final String MODULE = "module";
    public static final String SUB_MODULE = "subModule";
    private static final String SCOPE = "scope";
    public static final String EXPOSE = "expose";
    public static final String JAVA_PROPERTIES = "javaProperties";
    private final JavaProperties javaProperties;
    private final EOConfigsCache configsCache;
    private final String module;
    private final String subModule;
    private final String configModelKey;
    private final List<Scope> scope;
    private Expose expose;
    private boolean resolved = false;

    public ConfigImpl(EOConfigsCache configsCache, Map map) {
        super(map);
        try {
            this.module = (String) map.get(MODULE);
            this.subModule = (String) map.get(SUB_MODULE);
            this.configModelKey = (String) map.get(CONFIG_MODEL_KEY);
            this.scope = new ArrayList<>();
            if (map.containsKey(SCOPE)) {
                List<String> scopes = (List<String>) map.get(SCOPE);
                if (scopes != null && !scope.isEmpty()) {
                    for (String scopeValue : scopes) {
                        scope.add(Scope.valueOf((String) map.get(SCOPE)));
                    }
                } else {
                    scopes.add(Scope.ALL.name());
                }
            }
            this.expose = map.containsKey(EXPOSE) ? Expose.valueOf((String) map.get(EXPOSE)) : Expose.INFO;
            this.configsCache = configsCache;
            this.javaProperties = map.containsKey(JAVA_PROPERTIES) && map.get(JAVA_PROPERTIES)!=null ?
                    new JavaPropertiesImmutable((Map)map.get(JAVA_PROPERTIES)):
                    null;
        }
        catch (Exception e) {
            throw new EoInternalException("Problem setting field with " + map.get(NATURAL_ID));
        }
    }

    public JavaProperties getJavaProperties() {
        return javaProperties;
    }

    @Override
    public String getKey() {
        return getNaturalId();
    }

    @Override
    public Expose getExpose() {
        return expose;
    }


    public String getConfigModelKey() {
        return configModelKey;
    }

    @Override
    public void setExpose(Expose expose) {
        this.expose = expose;
    }
    @Override
    public boolean hasExpose() {
        return expose != null && expose != Expose.NONE;
    }

    protected boolean isResolved() {
        return this.resolved;
    }

    protected void setResolved() {
        this.resolved = true;
    }

    public void resolve()  {
        this.resolved = true;
    }

    /**
     * Defines a target module where generating occurs.
     */
    public String getModule() {
        return this.module;
    }
    //<call keep="JAVA" templateKey="CacheGetter.tpl">

    /**
     * Defines the target  sub module like 'main' or 'test'.
     */
    public String getSubModule() {
        return this.subModule;
    }


    /**
     * A scope for the config value.
     */
    public List<Scope> getScope() {
        return this.scope;
    }

    public boolean hasScope(final Scope scope) {
        if (scope == Scope.ALL) {
            return true;
        }
        if (this.scope == null) {
            return true;
        } else if (this.scope.isEmpty()) {
            return true;
        }
        if (this.scope.contains(scope)) {
            return true;
        }
        return false;
    }

    public EOConfigsCache getConfigsCache() {
        return configsCache;
    }


    @Override
    public String toString() {
        if (this == null) {
            return "{}";
        }
        try {
            return new EOToJSON().toJSON(getConfigsCache(), this);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String serialize() {
        if (this == null) {
            return "{}";
        }
        try {
            return new EOToJSON().setStartIndent(1).toJSON(getConfigsCache(), this);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
