package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.calls.generate.helper.FieldHelper;
import org.fluentcodes.projects.elasticobjects.calls.templates.Parser;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.tools.xpect.IOString;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.FIELD_KEYS;

public class GenerateJsonConfigCall extends GenerateCall {
    public static final String CONFIG_TYPE = "configType";
    private String configType;

    public GenerateJsonConfigCall() {
        super();
    }
    public GenerateJsonConfigCall(final String configType) {
        super();
        this.configType = configType;
    }

    @Override
    public Object execute(EO eo) {
        StringBuilder feedback = new StringBuilder();
        if (eo.isEmpty()) {
            throw new EoException("Eo for generating json configuration files is empty! " + eo.getPathAsString());
        }
        if (!hasConfigType()) {
            throw new EoInternalException("No config type set");
        }
        if (!hasBuildPath()) {
            throw new EoInternalException("No build path set so nothing will generated");
        }
        init(eo);
        this.configType = Parser.replace(configType, eo);
        for (String module: eo.keys()) {
            if ("basic".equals(module)) {
                continue;
            }
            EO child = eo.getEo(module);
            if (child.isEmpty()) {
                continue;
            }
            if (hasModule() && !module.equals(getModule())) {
                feedback.append("Skip module '" + module + "'\n");
                continue;
            }
            for (String moduleScope: child.keys()) {
                if (child.isEmpty()) {
                    continue;
                }
                if (hasClassPath()) {
                    feedback.append("Skip subModule '" + getClassPath() + "'\n");
                    continue;
                }
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

                String targetPath = getBuildPath()
                        + Path.DELIMITER + module
                        + Path.DELIMITER + "src"
                        + Path.DELIMITER + moduleScope
                        + Path.DELIMITER + "resources"
                        + Path.DELIMITER + getConfigType()
                        + "." + getFileEnding();
                String jsonConfig = new EOToJSON()
                        .setSerializationType(JSONSerializationType.STANDARD)
                        .toJSON(eo.getConfigsCache(), result);
                if (hasFileEnding()) {
                    feedback.append("Written configuration to  '" + targetPath + "'\n");
                    try {
                        new IOString().setFileName(targetPath).write(jsonConfig);
                    }
                    catch (Exception e) {
                        System.out.println(new File(targetPath).getAbsoluteFile());
                    }
                }
                else {
                    feedback.append("Configuration not persisted to  '" + targetPath + "' due to missing file ending\n");
                    eo.warn("No file ending is set, so no file is written to " + targetPath);
                }
            }
        }
        return feedback;
    }

    public GenerateJsonConfigCall setConfigType(String configType) {
        this.configType = configType;
        return this;
    }

    public String getConfigType() {
        return configType;
    }

    public boolean hasConfigType() {
        return configType!=null && !configType.isEmpty();
    }


}
