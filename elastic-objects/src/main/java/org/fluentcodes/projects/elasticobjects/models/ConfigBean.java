package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.domain.BaseBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigBean extends BaseBean implements Config {
    private static final String CONFIG_MODEL_KEY = "configModelKey";

    private Expose expose;
    private String module;
    private String moduleScope;
    private String configModelKey;
    private Map properties;
    private List<Scope> scope;

    public ConfigBean() {
        super();
        properties = new HashMap<>();
    }
    public ConfigBean(final String key) {
        super(key);
        properties = new HashMap<>();
    }

    public ConfigBean(final Map values) {
        super();
        properties = new HashMap();
        merge(values);
    }

    public void merge(ConfigBean configBean) {
        super.merge(configBean);
    }

    protected void merge(final Map values) {
        super.merge(values);
        if (values == null || values.isEmpty()) {
            return;
        }
        mergeConfigModelKey(values.get(CONFIG_MODEL_KEY));
        mergeModule(values.get(MODULE));
        mergeModuleScope(values.get(MODULE_SCOPE));
        mergeExpose(values.get(EXPOSE));
        mergeScope(values.get(SCOPE));
        if (values.containsKey(PROPERTIES)) {
            if (values.get(PROPERTIES) instanceof String ) {
                System.out.println("x");
            }
            properties = (Map)values.get(PROPERTIES);
        }
        else {
            properties = new HashMap<>();
        }
        defaultConfigModelKey();
    }

    @Override
    public Map<String, Object> getProperties() {
        return properties;
    }

    @Override
    public Expose getExpose() {
        return expose;
    }

    @Override
    public Config setExpose(Expose expose) {
        this.expose = expose;
        return this;
    }

    @Override
    public String getModule() {
        return module;
    }

    @Override
    public Config setModule(String module) {
        this.module = module;
        return this;
    }

    @Override
    public String getModuleScope() {
        return moduleScope;
    }

    @Override
    public Config setModuleScope(String moduleScope) {
        this.moduleScope = moduleScope;
        return this;
    }

    public void setProperties(Map properties) {
        this.properties = properties;
    }

    @Override
    public List<Scope> getScope() {
        return scope;
    }

    @Override
    public Config setScope(List<Scope> scope) {
        this.scope = scope;
        return this;
    }

    public String getConfigModelKey() {
        return configModelKey;
    }

    public Config setConfigModelKey(String configModelKey) {
        this.configModelKey = configModelKey;
        return this;
    }
}
