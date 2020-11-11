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
   public static final String FILTER_MODULE = "filterModule";
   public static final String FILTER_SUB_MODULE = "filterSubModule";
/*=>{}.*/

    /*==>{ALLInstanceVars.tpl, fieldMap/*, , JAVA|>}|*/
   private  String filterModule;
   private  String filterSubModule;
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
                if (hasFilterModule() && (configEntry.getModule() == null || !configEntry.getModule().equals(this.getFilterModule()))) {
                    continue;
                }
                if (hasFilterSubModule() && (configEntry.getModuleScope() == null || !configEntry.getModuleScope().equals(this.getFilterSubModule()))) {
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

    public ConfigCall setFilterModule(String filterModule) {
        this.filterModule = filterModule;
        return this;
    }
    
    public String getFilterModule () {
       return this.filterModule;
    }
    
    public boolean hasFilterModule () {
        return filterModule!= null && !filterModule.isEmpty();
    }
    /**
    Filter for subModules
    */

    public ConfigCall setFilterSubModule(String filterSubModule) {
        this.filterSubModule = filterSubModule;
        return this;
    }
    
    public String getFilterSubModule () {
       return this.filterSubModule;
    }
    
    public boolean hasFilterSubModule () {
        return filterSubModule!= null && !filterSubModule.isEmpty();
    }
/*=>{}.*/

}
