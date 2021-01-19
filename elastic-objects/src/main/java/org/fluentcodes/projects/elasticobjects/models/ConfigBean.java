package org.fluentcodes.projects.elasticobjects.models;

import java.util.HashMap;
import java.util.Map;
/*=>{javaHeader}|*/
import java.util.List;
import org.fluentcodes.projects.elasticobjects.domain.BaseBean;
/**
 * 
 * The basic bean class for the configuration classes. 
 * @author Werner Diwischek
 * @creationDate null
 * @modificationDate Sun Jan 10 10:57:55 CET 2021
 */
public class ConfigBean extends BaseBean implements ConfigBeanInterface  {
/*=>{}.*/

    private static final String CONFIG_MODEL_KEY = "configModelKey";
/*=>{javaInstanceVars}|*/
   /* The model of the configuration to determine type. */
   private String configModelKey;
   /* expose */
   private Expose expose;
   /* Defines a target module where generating occurs.  */
   private String module;
   /* Defines scope of the configuration within module, eg 'test' or 'main' . */
   private String moduleScope;
   /* A scope for the cache value. */
   private List<Scope> scope;
/*=>{}.*/
    private Map properties;

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

    public ConfigBean(final ConfigConfig config) {
        super(config);
        setModule(config.getModule());
        setModuleScope(config.getModuleScope());
        setExpose(config.getExpose());
        properties = new HashMap();
        for (Object key : config.getProperties().keySet()) {
            properties.put(key, config.getProperties().get(key));
        }
    }

    public void merge(ConfigBean configBean) {
        super.merge(configBean);
    }

    public void merge(final Map configMap) {
        super.merge(configMap);
        if (configMap == null || configMap.isEmpty()) {
            return;
        }
        mergeConfigModelKey(configMap.get(CONFIG_MODEL_KEY));
        mergeModule(configMap.get(MODULE));
        mergeModuleScope(configMap.get(MODULE_SCOPE));
        mergeExpose(configMap.get(EXPOSE));
        mergeScope(configMap.get(SCOPE));
        properties = new HashMap<>();
        if (configMap.containsKey(PROPERTIES)) {
            Object props = configMap.get(PROPERTIES);
            if (props instanceof Map) {
                properties.putAll((Map) props);
            }

        }
        defaultConfigModelKey();
    }

    @Override
    public Map<String, Object> getProperties() {
        return properties;
    }
    public void setProperties(Map properties) {
        this.properties = properties;
    }

    /*=>{javaAccessors}|*/
   @Override
   public String getConfigModelKey() {
      return this.configModelKey;
   }

   @Override
   public ConfigBean setConfigModelKey(final String configModelKey) {
      this.configModelKey = configModelKey;
      return this;
    }

   @Override
   public Expose getExpose() {
      return this.expose;
   }

   @Override
   public ConfigBean setExpose(final Expose expose) {
      this.expose = expose;
      return this;
    }

   @Override
   public String getModule() {
      return this.module;
   }

   @Override
   public ConfigBean setModule(final String module) {
      this.module = module;
      return this;
    }

   @Override
   public String getModuleScope() {
      return this.moduleScope;
   }

   @Override
   public ConfigBean setModuleScope(final String moduleScope) {
      this.moduleScope = moduleScope;
      return this;
    }

   @Override
   public List<Scope> getScope() {
      return this.scope;
   }

   @Override
   public ConfigBean setScope(final List<Scope> scope) {
      this.scope = scope;
      return this;
    }

/*=>{}.*/
}
