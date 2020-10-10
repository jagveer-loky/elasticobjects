package org.fluentcodes.projects.elasticobjects.models;

// $[(TemplateResourceCall)javaGenImport/* configKey="ALLImport.tpl" keepCall="JAVA" ]
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.domain.BaseImpl;
//$[/]
import java.util.LinkedHashMap;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.UnmodifiableMap;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

/**
 * Created by Werner on 10.10.2016.
 */
public abstract class ConfigImpl extends BaseImpl implements Config {
 // $[(TemplateResourceCall)fieldKeys/* configKey="ALLInstanceVars.tpl" keepCall="JAVA"]
    private final List<Scope> scope;
    private  Expose expose;
    private final Map properties;
    private final String module;
    private final String moduleScope;
//$[/]
    private final EOConfigsCache configsCache;
    private final String configModelKey;
    private boolean resolved = false;

    public ConfigImpl(EOConfigsCache configsCache, Map map) {
        super(map);
        try {
            this.configModelKey = (String) map.get(CONFIG_MODEL_KEY);
            this.module = (String) map.get(MODULE);
            this.moduleScope = (String) map.get(MODULE_SCOPE);
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
            Map properties = (Map)map.get(PROPERTIES);
            if (properties == null) {
                properties = new LinkedHashMap();
            }
            properties.put(MODULE, module);
            this.properties = new UnmodifiableMap(new LinkedHashMap(properties));
        }
        catch (Exception e) {
            throw new EoInternalException("Problem setting field with " + map.get(NATURAL_ID));
        }
    }

    @Override
    public String getKey() {
        return getNaturalId();
    }

    public String getConfigModelKey() {
        return configModelKey;
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
            EO eoMap = new EoRoot(getConfigsCache());
            eoMap.mapObject(this);
            return new EOToJSON()
                    .setSerializationType(JSONSerializationType.STANDARD)
                    .toJSON(eoMap);
        } catch (Exception e) {
            return "Exception serializing configuration! " + e.getMessage();
        }
    }

    public String serialize() {
        if (this == null) {
            return "{}";
        }
        try {
            return new EOToJSON().toJSON(getConfigsCache(), this);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

// $[(TemplateResourceCall)fieldKeys/* configKey="ALLSetter.tpl" keepCall="JAVA"]
    /**
     A scope for the cache value.
     */

    @Override
    public final List<Scope> getScope () {
        return this.scope;
    }
    @Override
    public boolean hasScope () {
        return scope != null  && !scope.isEmpty();
    }

    /**
     expose
     */

    @Override
    public final ConfigImpl setExpose(Expose expose) {
        this.expose = expose;
        return this;
    }
    @Override
    public final Expose getExpose () {
        return this.expose;
    }
    @Override
    public boolean hasExpose () {
        return expose != null ;
    }

    /**
     Properties for configurations.
     */

    @Override
    public final Map getProperties () {
        return this.properties;
    }
    @Override
    public boolean hasProperties () {
        return properties != null  && !properties.isEmpty();
    }

    /**
     Defines a target module where generating occurs.
     */

    @Override
    public final String getModule () {
        return this.module;
    }
    @Override
    public boolean hasModule () {
        return module != null  && !module.isEmpty();
    }

    /**
     Defines scope of the configuration within module, eg 'test' or 'main' .
     */

    @Override
    public final String getModuleScope () {
        return this.moduleScope;
    }
    @Override
    public boolean hasModuleScope () {
        return moduleScope != null  && !moduleScope.isEmpty();
    }

//$[/]
}
