package org.fluentcodes.projects.elasticobjects.models;

import java.util.Map;
import org.fluentcodes.projects.elasticobjects.UnmodifiableMap;

/*=>{javaHeader}|*/
import java.util.List;
import org.fluentcodes.projects.elasticobjects.domain.BaseConfig;
/**
 * 
 * Basic cache as super object for other cached items.  
 * @author Werner Diwischek
 * @creationDate null
 * @modificationDate Sun Jan 10 11:32:02 CET 2021
 */
public class ConfigConfig extends BaseConfig implements ConfigConfigInterface  {
/*=>{}.*/
   private final Map properties;
    /*=>{javaInstanceVars}|*/
   /* expose */
   private final Expose expose;
   /* Defines a target module where generating occurs.  */
   private final String module;
   /* Defines scope of the configuration within module, eg 'test' or 'main' . */
   private final String moduleScope;
   /* A scope for the cache value. */
   private final List<Scope> scope;
/*=>{}.*/

    public ConfigConfig(ConfigBeanInterface config) {
        super(config);
        this.module = config.getModule();
        this.moduleScope = config.getModuleScope();
        this.scope = config.getScope();
        this.expose = config.getExpose();
        this.properties = new UnmodifiableMap(config.getProperties());
    }

    @Override
    public Map<String, Object> getProperties() {
        return properties;
    }

    /*=>{javaAccessors}|*/
   @Override
   public Expose getExpose() {
      return this.expose;
   }

   @Override
   public String getModule() {
      return this.module;
   }

   @Override
   public String getModuleScope() {
      return this.moduleScope;
   }

   @Override
   public List<Scope> getScope() {
      return this.scope;
   }

/*=>{}.*/
}
