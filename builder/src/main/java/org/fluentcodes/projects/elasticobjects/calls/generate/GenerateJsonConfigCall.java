package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.files.FileWriteCall;
import org.fluentcodes.projects.elasticobjects.calls.generate.java.helper.FieldHelper;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserSqareBracket;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.FIELD_KEYS;

/*==>{TemplateResourceCall->ALLHeader.tpl, ., JAVA|>}|*/

/**
 * Call for generation json configurations from the sheet.
 * Created by Werner Diwischek at date Thu Nov 05 15:31:43 CET 2020.
 */
public class GenerateJsonConfigCall extends GenerateCall  {
/*=>{}.*/
    public GenerateJsonConfigCall() {
        super();
    }
    public GenerateJsonConfigCall(final String configType) {
        super();
        this.configType = configType;
    }
    /*==>{TemplateResourceCall->ALLStaticNames.tpl, fieldMap/*, JAVA, override eq false|>}|*/
   public static final String CONFIG_TYPE = "configType";
/*=>{}.*/

    /*==>{TemplateResourceCall->ALLInstanceVars.tpl, fieldMap/*, JAVA|>}|*/
   private  String configType;
/*=>{}.*/
    private FileConfig targetConfig;



    @Override
    public String getTargetFileConfigKey() {
        return "ConfigResources";
    }

    @Override
    public boolean init(final EO eo) {
        if (eo.isEmpty()) {
            throw new EoException("Eo for generating json configuration files is empty! " + eo.getPathAsString());
        }
        if (!hasConfigType()) {
            throw new EoInternalException("No config type set");
        }
        return super.init(eo);
    }

    @Override
    public Object execute(EO eo) {
        init(eo);
        StringBuilder feedback = new StringBuilder();
        EO eoPath = ProviderRootTestScope.createEo();
        this.configType = ParserSqareBracket.replacePathValues(configType, eo);
        eoPath.set(configType, CONFIG_TYPE);
        for (String module: eo.keys()) {
            if ("basic".equals(module)) {
                continue;
            }
            EO child = eo.getEo(module);
            if (child.isEmpty()) {
                continue;
            }
            if (hasModule() && !getModule().equals("*") && !module.matches(getModule())) {
                feedback.append("Skip module '" + module + "' with filter module '" + getModule() + "'\n");
                continue;
            }
            eoPath.set(module, MODULE);
            for (String moduleScope: child.keys()) {
                if (child.isEmpty()) {
                    continue;
                }
                eoPath.set(moduleScope, MODULE_SCOPE);
                Map result = new LinkedHashMap();
                Map<String, Object> configurations = (Map<String, Object>)child.get(moduleScope);
                for (String id: configurations.keySet()) {
                    Map configurationMap = (Map)configurations.get(id);
                    String naturalId = (String)configurationMap.get(NATURAL_ID);
                    if (naturalId == null || naturalId.isEmpty()) {
                        throw new EoInternalException("Could not found natural Id in config " + eo.toString());
                    }
                    if (ModelConfig.class.getSimpleName().equals(getConfigType())) {
                        FieldHelper fields = new FieldHelper(configurationMap.get(FIELD_KEYS), "jsonIgnore", "override");
                            configurationMap.put(FIELD_KEYS, fields.getFieldKeys());
                    }
                    configurationMap.remove(NATURAL_ID);
                    result.put(naturalId, configurationMap);
                }
                String jsonConfig = new EOToJSON()
                        .setSerializationType(JSONSerializationType.STANDARD)
                        .toJSON(eo.getConfigsCache(), result);
                FileWriteCall call = new FileWriteCall(getTargetFileConfigKey(), jsonConfig);
                call.setCompare(true);
                feedback.append(call.execute(eoPath));
                feedback.append("\n");
            }
        }
        return feedback;
    }

    /*==>{TemplateResourceCall->ALLSetter.tpl, fieldMap/*, JAVA|>}|*/

    /**
    Key for configuration type.
    */
    
    public GenerateJsonConfigCall setConfigType(String configType) {
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
