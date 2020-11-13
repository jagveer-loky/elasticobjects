package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;

import java.util.List;
import java.util.Map;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/
/**
 * For getting a map of configurations for a specific configuration type.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Wed Nov 11 05:37:16 CET 2020
 */
public class ConfigCall extends ConfigKeysCall {
/*=>{}.*/

    /*==>{ALLStaticNames.tpl, fieldMap/*, override eq false, JAVA|>}|*/
   public static final String MODULE = "module";
   public static final String MODULE_SCOPE = "moduleScope";
/*=>{}.*/

    /*==>{ALLInstanceVars.tpl, fieldMap/*, , JAVA|>}|*/
   private  String module;
   private  String moduleScope;
/*=>{}.*/

    public ConfigCall() {
        super();
    }

    public ConfigCall(final Class<? extends Config> configClass) {
        super(configClass);
    }

    public ConfigCall(final Class<? extends Config> configClass, final String configFilter) {
        super(configClass, configFilter);
    }

    @Override
    public Object execute(EO eo)  {
        EO result = new EoRoot(eo.getConfigsCache(),List.class,Map.class);
        result.setSerializationType(JSONSerializationType.STANDARD);
        String targetPath = getTargetPath();
        setTargetPath(null);
        List<String> keys = (List<String>) super.execute(eo);
        setTargetPath(targetPath);
        ModelConfig model  = eo.getConfigsCache().findModel(getConfigType());
        Class configClass = model.getModelClass();
        for (String key : keys) {
            Config configEntry = (Config) eo.getConfigsCache().find(configClass, key);
            try {
                if (hasFilterModule() && (configEntry.getModule() == null || !configEntry.getModule().equals(this.getModule()))) {
                    continue;
                }
                if (hasFilterSubModule() && (configEntry.getModuleScope() == null || !configEntry.getModuleScope().equals(this.getModuleScope()))) {
                    continue;
                }
            } catch (Exception e) {
                throw new EoException(e);
            }
            configEntry.resolve();
            EO child = result.set(configEntry, Integer.valueOf(result.size()).toString());
            child.set(key, "naturalId");
        }
        return super.createReturnType(eo,result.get());
    }

    /*==>{ALLSetter.tpl, fieldMap/*, , JAVA|>}|*/
    /**
    Filter for modules
    */

    public ConfigCall setModule(String module) {
        this.module = module;
        return this;
    }
    
    public String getModule() {
       return this.module;
    }
    
    public boolean hasFilterModule () {
        return module != null && !module.isEmpty();
    }
    /**
    Filter for subModules
    */

    public ConfigCall setModuleScope(String moduleScope) {
        this.moduleScope = moduleScope;
        return this;
    }
    
    public String getModuleScope() {
       return this.moduleScope;
    }
    
    public boolean hasFilterSubModule () {
        return moduleScope != null && !moduleScope.isEmpty();
    }
/*=>{}.*/

}
