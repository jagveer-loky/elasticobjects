package org.fluentcodes.projects.elasticobjects.models;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
/*=>{javaHeader}|*/
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.JSONToEO;
import org.fluentcodes.projects.elasticobjects.domain.BaseBean;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

/**
 * 
 * The basic bean class for the configuration classes. 
 * @author Werner Diwischek
 * @creationDate null
 * @modificationDate Sun Jan 10 10:57:55 CET 2021
 */
public class ConfigBean extends BaseBean implements ConfigBeanInterface  {
/*=>{}.*/
    private static final Logger LOG = LogManager.getLogger(ConfigBean.class);
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

   private Class<? extends ConfigConfigInterface> configModelClass;
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
        if (values == null) {
            throw new EoInternalException("Null value for initial map. Could not create configuration bean");
        }
        properties = new HashMap();
        merge(values);
    }

    public ConfigBean(final String naturalId, final Map values) {
        super();
        properties = new HashMap();
        merge(values);
        if (!hasNaturalId()) setNaturalId(naturalId);
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
        this.properties = new HashMap<>();
        if (configMap.containsKey(PROPERTIES)) {
            Object props = configMap.get(PROPERTIES);
            if (props instanceof Map) {
                this.properties.putAll((Map) props);
            }

        }
    }

    public Map<String, Object> getProperties() {
        return properties;
    }
    public void setProperties(Map properties) {
        this.properties = properties;
    }

    /*=>{javaAccessors}|*/
   public String getConfigModelKey() {
       return this.configModelKey;
   }

    public boolean hasConfigModelKey() {
        return configModelKey != null && !configModelKey.isEmpty();
    }

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

    /**
     * Defines scope of the configuration within module, eg 'test' or 'main' .
     */

    void mergeConfigModelKey(Object value) {
        if (value == null) {
            return;
        }
        if (hasConfigModelKey()) {
            return;
        }
        setConfigModelKey(ScalarConverter.toString(value));
    }

    Class deriveConfigClass(final String configModelKey) {
        try {
            return Class.forName(
                    this.getClass().getPackage().toString().replace("package ", "")
                            + "." + configModelKey);
        } catch (Exception e) {
            throw new EoException(e.getMessage());
        }
    }

    Class deriveConfigClass() {
        return deriveConfigClass(getConfigModelKey());
    }

    ConfigConfigInterface createConfig(Class<? extends ConfigConfig> configClass) {
        try {
            Constructor configurationConstructor = configClass.getConstructor(ConfigBean.class);
            try {
                return (ConfigConfigInterface)configurationConstructor.newInstance(this);
            } catch (Exception e) {
                throw new EoInternalException("Problem with create new instance for config constructor with bean class for '" + getNaturalId() + "'/'" + configClass.getSimpleName() + "' in ModelConfig", e);
            }
        }
        catch (NoSuchMethodException e) {
            throw new EoInternalException("Problem find constructor for '" + getNaturalId() + "'  '" + configClass.getSimpleName() + "' with ConfigBean", e);
        }
    }

    ConfigConfigInterface createConfig(ConfigMaps configsCache) {
        if (!hasConfigModelKey()) {
            throw new EoException("No configModelKey is set!");
        }
        ModelConfig configModel = configsCache.findModel(getConfigModelKey());
        return createConfig(configModel.getModelClass());

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

    private void mergeModuleScope(Object value) {
        if (value == null) {
            return;
        }
        if (hasModuleScope()) {
            return;
        }
        setModuleScope(ScalarConverter.toString(value));
    }

    private void mergeModule(Object value) {
        if (value == null) {
            return;
        }
        if (hasModule()) {
            return;
        }
        setModule(ScalarConverter.toString(value));
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

/*=>{}.*/

}
