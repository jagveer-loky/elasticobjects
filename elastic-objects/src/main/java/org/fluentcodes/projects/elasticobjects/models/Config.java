package org.fluentcodes.projects.elasticobjects.models;


import org.fluentcodes.projects.elasticobjects.domain.Base;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*==>{ALLHeader.tpl, ., , JAVA}|*/

/**
 * Basic config interface as super interface for other cached items. 
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Tue Dec 08 17:46:47 CET 2020
 */
public interface Config extends Base, ConfigConfigInterface {
/*=>{}.*/
/*==>{INTERFACEStaticNames.tpl, fieldBeans/*, super eq false, JAVA}|*/
/*=>{}.*/

    /*==>{INTERFACESetter.tpl, fieldBeans/*, super eq false, JAVA}|*/

   Config setExpose (Expose expose);
    default void mergeExpose(Object value) {
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

  /**
  * Defines a target module where generating occurs.
  */
   Config setModule(String module);
   default Boolean hasModule () {
      return getModule()!= null && !getModule().isEmpty();
    }

    default void mergeModule(Object value) {
        if (value == null) {
            return;
        }
        if (hasModule()) {
            return;
        }
        setModule(ScalarConverter.toString(value));
    }

  /**
  * Defines scope of the configuration within module, eg 'test' or 'main' .
  */
  Config setModuleScope(String moduleScope);
    default void mergeModuleScope(Object value) {
        if (value == null) {
            return;
        }
        if (hasModuleScope()) {
            return;
        }
        setModuleScope(ScalarConverter.toString(value));
    }
    
  /**
  * A scope for the cache value.
  */
   Config setScope(List<Scope> scope);

    default void mergeScope(Object value) {
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

    String getConfigModelKey();
    default boolean hasConfigModelKey() {
        return getConfigModelKey() != null && !getConfigModelKey().isEmpty();
    }
    Config setConfigModelKey(String configModelKey);
    default void defaultConfigModelKey() {}
    default void mergeConfigModelKey(Object value) {
        if (value == null) {
            return;
        }
        if (hasConfigModelKey()) {
            return;
        }
        setConfigModelKey(ScalarConverter.toString(value));
    }

    default ConfigConfigInterface createConfig() {
        if (!hasConfigModelKey()) {
            throw new EoException("No configModelKey is set!");
        }
        try {
            Class modelConfigClass = Class.forName("org.fluentcodes.projects.elasticobjects.models." + getConfigModelKey());
            return createConfig(modelConfigClass);
        } catch (ClassNotFoundException e) {
            throw new EoException(e);
        }

    }

    default ConfigConfigInterface createConfig(final Class<? extends ConfigConfig> configClass) {
        try {
            Constructor configurationConstructor = configClass.getConstructor(ConfigBean.class);
            try {
                return (ConfigConfigInterface)configurationConstructor.newInstance(this);
            } catch (Exception e) {
                throw new EoInternalException("Problem with '" + getNaturalId() + "'/'" + configClass.getSimpleName() + "' in ModelConfig", e);
            }
        }
        catch (NoSuchMethodException e) {
            throw new EoInternalException("Problem find constructor for '" + getNaturalId() + "'  '" + configClass.getSimpleName() + "' with ConfigBean", e);
        }
    }

    default ConfigConfigInterface createConfig(EOConfigsCache configsCache) {
        if (!hasConfigModelKey()) {
            throw new EoException("No configModelKey is set!");
        }
        ModelConfig configModel = configsCache.findModel(getConfigModelKey());
        return createConfig(configModel.getModelClass());

    }

    default List<Scope> createScopeList(List values) {
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
/*=>{}.*/
}
