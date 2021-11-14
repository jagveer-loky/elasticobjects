package org.fluentcodes.projects.elasticobjects.models;


import java.util.List;

/*=>{javaHeader}|*/
/**
 * 
 * Basic config interface as super interface for other cached items.  
 * @author Werner Diwischek
 * @creationDate null
 * @modificationDate Sun Jan 10 09:07:24 CET 2021
 */
public interface ConfigBeanInterface extends ConfigConfigInterface  {
/*=>{}.*/

/*=>{javaAccessors}|*/
/*=>{}.*/

  /**
  * Defines a target module where generating occurs.
  */
   ConfigBeanInterface setModule(String module);
  /**
  * Defines scope of the configuration within module, eg 'test' or 'main' .
  */
  ConfigBeanInterface setModuleScope(String moduleScope);
  ConfigBean setExpose(final Expose expose);
  /**
  * A scope for the cache value.
  */
   ConfigBeanInterface setScope(List<Scope> scope);

    String getConfigModelKey();

    ConfigBeanInterface setConfigModelKey(String configModelKey);
}
