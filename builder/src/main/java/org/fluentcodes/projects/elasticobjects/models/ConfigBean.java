package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.domain.BaseImpl;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigBean extends BaseImpl implements ConfigProperties, Config {
    private Expose expose;
    private String module;
    private String moduleScope;
    private Map properties;
    private List<Scope> scope;

    public ConfigBean() {
        super();
        properties = new HashMap<>();
    }
    public ConfigBean(String key) {
        super(key);
        properties = new HashMap<>();
    }

    public ConfigBean(Map values) {
        super(values);
        if (values == null) {
            return;
        }
        mergeModule(values.get(MODULE));
        mergeModuleScope(values.get(MODULE_SCOPE));
        mergeExpose(values.get(EXPOSE));
        mergeScope(values.get(SCOPE));
        if (values.containsKey(PROPERTIES)) {
            properties = (Map)values.get(PROPERTIES);
        }
        else {
            properties = new HashMap<>();
        }
    }

    @Override
    public void resolve() {

    }

    public EOConfigsCache getConfigsCache() {
        return null;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void merge(final ConfigBean values) {
        super.merge(values);
    }

    public Expose getExpose() {
        return expose;
    }

    public ConfigBean setExpose(Expose expose) {
        this.expose = expose;
        return this;
    }

    private void mergeExpose(Object value) {
        if (value==null) {
            return;
        }
        if (hasExpose()) {
            return;
        }
        if (value instanceof Expose) {
            setExpose((Expose)value);
            return;
        }
        if (value instanceof String) {
            setExpose(Expose.valueOf((String) value));
            return;
        }
        throw new EoException("Could not set expose from class '" + value.getClass() + "' and value '" + value + "'");
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    private void mergeModule(Object value) {
        if (value==null) {
            return;
        }
        if (hasModule()) {
            return;
        }
        if (value instanceof String) {
            setModule((String) value);
            return;
        }
        throw new EoException("Could not set module from class '" + value.getClass() + "' and value '" + value + "'");
    }

    public String getModuleScope() {
        return moduleScope;
    }

    public void setModuleScope(String moduleScope) {
        this.moduleScope = moduleScope;
    }

    private void mergeModuleScope(Object value) {
        if (value==null) {
            return;
        }
        if (hasModuleScope()) {
            return;
        }
        if (value instanceof String) {
            setModuleScope((String) value);
            return;
        }
        throw new EoException("Could not set moduleScope from class '" + value.getClass() + "' and value '" + value + "'");
    }

    public void setProperties(Map properties) {
        this.properties = properties;
    }

    public List<Scope> getScope() {
        return scope;
    }

    public void setScope(List<Scope> scope) {
        this.scope = scope;
    }

    private List<Scope> createScopeList(List values) {
        List<Scope> scopeList = new ArrayList<>();
        for (Object value:values) {
            if (value instanceof String) {
                try {
                    scopeList.add(Scope.valueOf((String) value));
                }
                catch (Exception e) {
                    throw new EoException("Could not set scope from string with value '" + value + "'");
                }
            }
            else if (value instanceof Scope) {
                scopeList.add((Scope)value);
            }
            else {
                throw new EoException("Could not set scope from class '" + value.getClass() + "' and value '" + value + "'");
            }
        }
        return scopeList;
    }

    private void mergeScope(Object value) {
        if (value==null) {
            return;
        }
        if (hasScope()) {
            return;
        }
        if (value instanceof String) {
            if (!((String)value).isEmpty()) {
                String[] values = ((String)value).split(",");
                setScope(createScopeList(Arrays.asList(values)));
                return;
            }
        }
        else if (value instanceof List) {
            setScope(createScopeList((List) value));
            return;
        }
        throw new EoException("Could not set moduleScope from class '" + value.getClass() + "' and value '" + value + "'");
    }
}
