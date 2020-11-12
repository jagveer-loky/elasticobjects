package org.fluentcodes.projects.elasticobjects.models;

//  ===>{"(TemplateResourceCall).":{"sourcePath":"javaGenImport/*", "configKey":"ALLImport.tpl", "keepCall":"JAVA"}}|

import org.fluentcodes.projects.elasticobjects.domain.Base;

import java.util.List;
import java.util.Map;
//=>{}.


/**
 * Created by Werner on 10.10.2016.
 */
public interface Config extends ConfigProperties, Base {
    String CONFIG_MODEL_KEY = "configModelKey";
//  ===>{"(TemplateResourceCall).":{"sourcePath":"fieldKeys/*", "configKey":"INTERFACEStaticNames.tpl", "keepCall":"JAVA"}}|
    String SCOPE = "scope";
    String EXPOSE = "expose";
    String MODULE = "module";
    String MODULE_SCOPE = "moduleScope";
//=>{}.
    void resolve() ;
    EOConfigsCache getConfigsCache();
    String getConfigModelKey();

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

    default boolean hasProperties() {
        return getProperties()!=null && !getProperties().isEmpty();
    }

//  ===>{"(TemplateResourceCall).":{"sourcePath":"fieldKeys/*", "configKey":"INTERFACESetter.tpl", "keepCall":"JAVA"}}|
    /**
     A scope for the cache value.
     */
    List<Scope> getScope();
    boolean hasScope();


    /**
     expose
     */
    Expose getExpose();
    boolean hasExpose();
    ConfigImpl setExpose(Expose expose);

    /**
     Properties for configurations.
     */
    Map getProperties();

    /**
     Defines a target module where generating occurs.
     */
    String getModule();
    boolean hasModule();

    /**
     Defines scope of the configuration within module, eg 'test' or 'main' .
     */
    String getModuleScope();
    boolean hasModuleScope();

//=>{}.
}
