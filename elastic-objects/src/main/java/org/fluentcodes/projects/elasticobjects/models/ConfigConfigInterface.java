package org.fluentcodes.projects.elasticobjects.models;
import org.fluentcodes.projects.elasticobjects.domain.BaseConfigInterface;

import java.util.List;
import java.util.Map;
/*=>{javaHeader}|*/

/**
 * Basic config interface as super interface for other cached items. 
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Tue Dec 08 17:46:47 CET 2020
 */
public interface ConfigConfigInterface extends BaseConfigInterface {
/*=>{}.*/

    /*=>{javaStaticNames}|*/
    String EXPOSE = "expose";
    String MODULE = "module";
    String MODULE_SCOPE = "moduleScope";
    String SCOPE = "scope";
    String PROPERTIES = "properties";
    /*=>{}.*/

    default boolean hasScope(final Scope scope) {
        if (scope == Scope.ALL) {
            return true;
        }
        if (getScope() == null) {
            return true;
        } else if (getScope().isEmpty()) {
            return true;
        }
        if (getScope().contains(scope)) {
            return true;
        }
        return false;
    }

    /*=>{javaAccessors}|*/
    default boolean hasProperties() {
        return getProperties()!=null && !getProperties().isEmpty();
    }

    /**
     Properties for configurations.
     */
    Map getProperties();


  /**
  * expose
  */
   Expose getExpose();
   default Boolean hasExpose () {
      return getExpose()!= null;
    }

  /**
  * Defines a target module where generating occurs. 
  */
   String getModule();
   default Boolean hasModule () {
      return getModule()!= null && !getModule().isEmpty();
    }

  /**
  * Defines scope of the configuration within module, eg 'test' or 'main' .
  */
   String getModuleScope();
   default Boolean hasModuleScope () {
      return getModuleScope()!= null && !getModuleScope().isEmpty();
    }

  /**
  * A scope for the cache value.
  */
   List<Scope> getScope();
   default Boolean hasScope () {
      return getScope()!= null && !getScope().isEmpty();
    }
/*=>{}.*/
}
