package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.fluentcodes.projects.elasticobjects.BuilderParams;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.calls.files.FileWriteCall;
import org.fluentcodes.projects.elasticobjects.calls.xlsx.XlsxReadCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.FieldHelper;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.FIELD_KEYS;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/
/**
 * Call for generation json configurations from the sheet.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Wed Nov 11 07:39:50 CET 2020
 */
public class GenerateEoConfigJsonCall extends GenerateAbstract {
/*=>{}.*/
    public static final String DATA = "data";
/*==>{ALLStaticNames.tpl, fieldBeans/*, override eq false, JAVA|>}|*/
   public static final String CONFIG_TYPE = "configType";
    /*=>{}.*/
    static final String CONFIG_RESOURCES = "ConfigResources";
    /*=>{}.*/

/*==>{ALLInstanceVars.tpl, fieldBeans/*, , JAVA|>}|*/
   private  String configType;
/*=>{}.*/

    public GenerateEoConfigJsonCall() {
        super();
        setTargetFileConfigKey(CONFIG_RESOURCES);
    }
    public GenerateEoConfigJsonCall(final String configType) {
        super();
        this.configType = configType;
        setTargetFileConfigKey(CONFIG_RESOURCES);
    }

    @Override
    public void mergeParams(BuilderParams builderParams) {
        super.mergeParams(builderParams);
        if (builderParams.hasConfigType()) configType = builderParams.getConfigType();
    }

    @Override
    public boolean init(final EO eo) {
        if (!hasConfigType()) {
            throw new EoInternalException("No config type set");
        }
        return super.init(eo);
    }

    @Override
    public String execute(EO rootEo) {
        init(rootEo);
        StringBuilder feedback = new StringBuilder();

        XlsxReadCall readCall = new XlsxReadCall(getSourceFileConfigKey() + ":" + getConfigType());
        readCall.setTargetPath("/data/=>[module]./=>[moduleScope]./=>[naturalId].");
        readCall.setFilter("module eq =>[/module].");
        feedback.append(readCall.execute(rootEo));

        EO eoWrite = ProviderRootTestScope.createEo();
        eoWrite.set(configType, CONFIG_TYPE);
        eoWrite.set(getFileEnding(), FILE_ENDING);
        eoWrite.set(getProjectDirectory(), PROJECT_DIRECTORY);

        EO configTypeEo = rootEo.getEo(DATA);
        for (String module: configTypeEo.keys()) {
            if ("basic".equals(module)) {
                continue;
            }
            EO eoModule = configTypeEo.getEo(module);
            if (eoModule.isEmpty()) {
                continue;
            }
            if (hasModule() && !getModule().equals("*") && !module.matches(getModule())) {
                feedback.append("Skip module '" + module + "' with filter module '" + getModule() + "'\n");
                continue;
            }
            eoWrite.set(module, MODULE);
            for (String moduleScope: eoModule.keys()) {
                if (eoModule.isEmpty()) {
                    continue;
                }
                eoWrite.set(moduleScope, MODULE_SCOPE);
                Map result = new LinkedHashMap();
                EO eoModuleScope = eoModule.getEo(moduleScope);
                Map<String, Object> configurations = (Map<String, Object>)eoModuleScope.get();
                for (String id: configurations.keySet()) {
                    EO eoConfiguration = eoModuleScope.getEo(id);
                    Map configurationMap = (Map)eoConfiguration.get();
                    String naturalId = (String)configurationMap.get(NATURAL_ID);
                    if (naturalId == null || naturalId.isEmpty()) {
                        throw new EoInternalException("Could not find natural Id in config " + configTypeEo.toString());
                    }
                    if (ModelConfig.class.getSimpleName().equals(getConfigType())) {
                        FieldHelper fieldMap = eoConfiguration.hasEo(FIELD_KEYS)?
                            new FieldHelper(eoConfiguration):
                            new FieldHelper("");
                        configurationMap.put(FIELD_KEYS, fieldMap.filterKeys());
                    }
                    configurationMap.remove(NATURAL_ID);
                    result.put(naturalId, configurationMap);
                }
                String jsonConfig = new EOToJSON()
                        .setSerializationType(JSONSerializationType.STANDARD)
                        .toJson(configTypeEo.getConfigsCache(), result);
                FileWriteCall call = new FileWriteCall(getTargetFileConfigKey(), jsonConfig);
                call.setCompare(true);
                feedback.append(call.execute(eoWrite));
                feedback.append("\n");
            }
        }
        return feedback.toString();
    }

/*==>{ALLSetter.tpl, fieldBeans/*, , JAVA|>}|*/
    /**
    Key for configuration type like ModelConfig, FileConfig, FieldConfig, HostConfig, DbSqlConfig.
    */

    public GenerateEoConfigJsonCall setConfigType(String configType) {
        this.configType = configType;
        return this;
    }
    
    public String getConfigType () {
       return this.configType;
    }
    
    public boolean hasConfigType () {
        return configType!= null && !configType.isEmpty();
    }
/*=>{}.*/

}
