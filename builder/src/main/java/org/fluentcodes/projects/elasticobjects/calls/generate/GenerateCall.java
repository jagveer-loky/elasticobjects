package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.commands.GenerateCommand;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserSqareBracket;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/
/**
 * Abstract super class for generating code.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Wed Nov 11 07:29:32 CET 2020
 */
public abstract class GenerateCall extends CallImpl implements GenerateCommand {
/*=>{}.*/
/*==>{ALLStaticNames.tpl, fieldMap/*, override eq false, JAVA|>}|*/
   public static final String FILE_ENDING = "fileEnding";
   public static final String MODULE = "module";
   public static final String MODULE_SCOPE = "moduleScope";
/*=>{}.*/

/*==>{ALLInstanceVars.tpl, fieldMap/*, , JAVA|>}|*/
   private  String fileEnding;
   private  String module;
   private  String moduleScope;
/*=>{}.*/

    private FileConfig targetFileConfig;
    public GenerateCall() {
        super();
    }

    @Override
    protected boolean init(final EO eo) {
        this.module = ParserSqareBracket.replacePathValues(module,eo);
        this.moduleScope = ParserSqareBracket.replacePathValues(moduleScope, eo);
        this.fileEnding = ParserSqareBracket.replacePathValues(fileEnding, eo);
        targetFileConfig = eo.getConfigsCache().findFile(getTargetFileConfigKey());
        targetFileConfig.hasPermissions(PermissionType.WRITE, eo.getRoles());
        eo.set(getFileEnding(), Path.DELIMITER, FILE_ENDING);
        return true;
    }

    public String getTargetFileConfigKey() {
        throw new EoException("Should be overwritten by child classes.");
    }

    public FileConfig getTargetFileConfig() {
        return targetFileConfig;
    }


    protected boolean filter(EO eoModel) {
        if (eoModel.isEmpty()) {
            return false;
        }
        final String modelModule = (String) eoModel.get(MODULE);
        if ("basic".equals(modelModule)) {
            return false;
        }
        if (hasModule() && ! modelModule.matches(module)) {
            return false;
        }
        final String moduleScope = (String) eoModel.get(MODULE_SCOPE);
        //if (hasModuleScope() && !getModuleScope().equals(eoModel.get(MODULE_SCOPE))) {
        if (hasModuleScope() && !moduleScope.matches(getModuleScope())) {
            return false;
        }
        return true;
    }

/*==>{ALLSetter.tpl, fieldMap/*, , JAVA|>}|*/
    /**
    It's used to specify the file ending in different context. 
    */

    public GenerateCall setFileEnding(String fileEnding) {
        this.fileEnding = fileEnding;
        return this;
    }
    
    public String getFileEnding () {
       return this.fileEnding;
    }
    
    public boolean hasFileEnding () {
        return fileEnding!= null && !fileEnding.isEmpty();
    }
    /**
    Defines a target module where generating occurs. 
    */

    public GenerateCall setModule(String module) {
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

    public GenerateCall setModuleScope(String moduleScope) {
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
