package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ConfigConfigInterface;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;

import java.util.List;
import java.util.Map;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/

/**
 * For getting a map of configurations for a specific configuration type.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Tue Dec 08 09:33:28 CET 2020
 */
public class ConfigCall extends ConfigKeysCall  {
/*=>{}.*/

    /*==>{ALLStaticNames.tpl, fieldBeans/*, super eq false, JAVA|>}|*/
   public static final String MODULE = "module";
   public static final String MODULE_SCOPE = "moduleScope";
/*=>{}.*/

    /*==>{ALLInstanceVars.tpl, fieldBeans/*, super eq false, JAVA|>}|*/
   private  String module;
   private  String moduleScope;
/*=>{}.*/

    public ConfigCall() {
        super();
    }

    public ConfigCall(final Class<? extends ConfigConfigInterface> configClass) {
        super(configClass);
    }

    public ConfigCall(final Class<? extends ConfigConfigInterface> configClass, final String configFilter) {
        super(configClass, configFilter);
    }

    @Override
    public Object execute(EO eo)  {
        EO result = EoRoot.ofClass(eo.getConfigsCache(),List.class,Map.class);
        result.setSerializationType(JSONSerializationType.STANDARD);
        String targetPath = getTargetPath();
        setTargetPath(null);
        List<String> keys = (List<String>) super.execute(eo);
        setTargetPath(targetPath);
        ModelConfig model  = eo.getConfigsCache().findModel(getConfigType());
        Class configClass = model.getModelClass();
        for (String key : keys) {
            ConfigConfigInterface configEntry = (ConfigConfigInterface) eo.getConfigsCache().find(configClass, key);
            try {
                if (hasModule() && (configEntry.getModule() == null || !configEntry.getModule().equals(this.getModule()))) {
                    continue;
                }
                if (hasModuleScope() && (configEntry.getModuleScope() == null || !configEntry.getModuleScope().equals(this.getModuleScope()))) {
                    continue;
                }
            } catch (Exception e) {
                throw new EoException(e);
            }
            EO child = result.set(configEntry, Integer.valueOf(result.size()).toString());
            child.set(key, "naturalId");
        }
        return super.createReturnType(eo,result.get());
    }

    /*==>{ALLSetter.tpl, fieldBeans/*, super eq false, JAVA|>}|*/
    /**
    Defines a target module where generating occurs. 
    */

    public ConfigCall setModule(String module) {
        this.module = module;
        return this;
    }
    
    public String getModule () {
       return this.module;
    }
    
    public boolean hasModule () {
        return module!= null && !module.isEmpty();
    }
    /**
    Defines scope of the configuration within module, eg 'test' or 'main' .
    */

    public ConfigCall setModuleScope(String moduleScope) {
        this.moduleScope = moduleScope;
        return this;
    }
    
    public String getModuleScope () {
       return this.moduleScope;
    }
    
    public boolean hasModuleScope () {
        return moduleScope!= null && !moduleScope.isEmpty();
    }
/*=>{}.*/

}
