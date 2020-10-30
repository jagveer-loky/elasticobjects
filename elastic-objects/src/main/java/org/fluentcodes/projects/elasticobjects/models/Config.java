package org.fluentcodes.projects.elasticobjects.models;

//  ===>{"(TemplateResourceCall).":{"sourcePath":"javaGenImport/*", "configKey":"ALLImport.tpl", "keepCall":"JAVA"}}|
import java.util.List;
import java.util.Map;
import org.fluentcodes.projects.elasticobjects.domain.Base;
//=>{}.


/**
 * Created by Werner on 10.10.2016.
 */
public interface Config extends ModuleProperties, Base {
    String CONFIG_MODEL_KEY = "configModelKey";
//  ===>{"(TemplateResourceCall).":{"sourcePath":"fieldKeys/*", "configKey":"INTERFACEStaticNames.tpl", "keepCall":"JAVA"}}|
    String SCOPE = "scope";
    String EXPOSE = "expose";
    String PROPERTIES = "properties";
    String MODULE = "module";
    String MODULE_SCOPE = "moduleScope";
//=>{}.

    void resolve() ;
    String getKey();
    EOConfigsCache getConfigsCache();
    String getConfigModelKey();
    boolean hasScope(final Scope scope);

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
    boolean hasProperties();

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
