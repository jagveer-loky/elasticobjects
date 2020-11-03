package org.fluentcodes.projects.elasticobjects.calls.generate.java;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.calls.generate.GenerateCall;
import org.fluentcodes.projects.elasticobjects.calls.generate.java.helper.FieldHelper;
import org.fluentcodes.projects.elasticobjects.calls.generate.java.helper.JavaImportHelper;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserSqareBracket;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.tools.xpect.IOString;

import java.io.File;
import java.util.*;

import static org.fluentcodes.projects.elasticobjects.models.Config.MODULE;
import static org.fluentcodes.projects.elasticobjects.models.Config.MODULE_SCOPE;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.*;

public class GenerateJavaCall extends GenerateCall {
    private final static String JAVA_GEN_MODEL = "/modelKey";
    private final static String JAVA_GEN_IMPORT = "javaGenImport";
    public final static String GEN_IGNORE = "genIgnore";
    public final static String FIELD_MAP = "fieldMap";
    public GenerateJavaCall() {
        super();
    }

    @Override
    public Object execute(EO eo) {
        if (!hasOverwrite()) {
            setOverwrite(true);
        }
        eo.set(new Date().toString(), "/date");
        StringBuilder feedback = new StringBuilder();
        if (eo.isEmpty()) {
            throw new EoException("Eo for generating json configuration files is empty! " + eo.getPathAsString());
        }
        if (!hasBuildPath()) {
            throw new EoInternalException("No build path set so nothing will generated");
        }
        init(eo);
        this.setNaturalId(ParserSqareBracket.replacePathValues(getNaturalId(), eo));
        int counter = 1;
        for (final String naturalId: eo.keys()) {
            EO eoModel = eo.getEo(naturalId);
            if (eoModel.isEmpty()) {
                continue;
            }
            final String module = (String) eoModel.get(MODULE);
            if ("basic".equals(module)) {
                continue;
            }
            if (hasModule() && !getModule().equals(module)) {
                continue;
            }
            final String moduleScope = (String) eoModel.get(MODULE_SCOPE);
            //if (hasModuleScope() && !getModuleScope().equals(eoModel.get(MODULE_SCOPE))) {
            if (hasModuleScope() && !moduleScope.matches(getModuleScope())) {
                continue;
            }
            if (hasNaturalId() && !getNaturalId().equals(naturalId)) {
                continue;
            }
            final String modelKey = (String)eoModel.get(MODEL_KEY);
            final String modelPackagePath = (String)eoModel.get(PACKAGE_PATH);
            final String modelType = (String)eoModel.get(Config.PROPERTIES, SHAPE_TYPE);
            if (modelType == null || modelType.isEmpty()) {
                continue;
            }


            // create target path and load targetFile or template to create template call
            String targetPath = getBuildPath()
                    + Path.DELIMITER + module
                    + Path.DELIMITER + "src"
                    + Path.DELIMITER + moduleScope
                    + Path.DELIMITER + "java"
                    + Path.DELIMITER + modelPackagePath.replaceAll("\\.", "/")
                    + Path.DELIMITER + modelKey
                    + "." + getFileEnding();
            File targetFile = new File(targetPath);
            TemplateCall templateCall = new TemplateCall();
            if (targetFile.exists() && !isOverwrite()) {
                templateCall.setContent(new IOString().setFileName(targetPath).read());
            }
            else {
                FileReadCall call = new FileReadCall(modelType + "Create.tpl");
                templateCall.setContent((String)call.execute(eo));
            }
            if (!templateCall.isContentActive()) {
                feedback.append("Content is not active for '" + modelKey + "'");
                return feedback;
            }


            // Enrich model entry with java stuff.
            //------------------------------------
            JavaImportHelper javaImport = new JavaImportHelper(modelPackagePath);
            // Enrich field entries with java stuff.
            FieldHelper fieldMap = new FieldHelper(eoModel.get(FIELD_KEYS), GEN_IGNORE);
            for (String fieldKey : fieldMap.getFieldKeys()) {
                FieldConfig fieldConfig = eo.getConfigsCache().findField(fieldKey);
                javaImport.check(fieldConfig.getModels());
                fieldMap.merge(fieldKey, fieldConfig);
            }
            eoModel.set(fieldMap.createValueMap(), FIELD_MAP);
            // enrich interfaces (imports)
            if (eoModel.hasEo(INTERFACES)) {
                Object value = eoModel.get(INTERFACES);
                List<String> interfaces = (value instanceof String) ? Arrays.asList(((String)value).split(",")) : (List<String>) value;
                for (String interfaceKey : interfaces) {
                    String interfacePackagePath = (String) eo.get(Path.DELIMITER, ModelConfig.class.getSimpleName(), interfaceKey, PACKAGE_PATH);
                    javaImport.check(interfacePackagePath, interfaceKey);
                }
            }

            // super key
            if (eoModel.hasEo(SUPER_KEY)) {
                String superModelKey = (String)eoModel.get(SUPER_KEY);
                try {
                    EO eoSuper = eo.getEo(superModelKey);
                    if (eoSuper.hasEo(PACKAGE_PATH)) {
                        String superPackagePath = (String) eoSuper.get(PACKAGE_PATH);
                        javaImport.check(superPackagePath, superModelKey);
                    }
                    else {
                        throw new EoException("No packagePath for '" + superModelKey + "'.");
                    }
                }
                catch (Exception e) {
                    throw new EoException("Problem resolving packagePath for '" + superModelKey + "': " + e.getMessage());
                }
            }

            eoModel.set(javaImport.getImportModels(), JAVA_GEN_IMPORT);
            eo.set(modelKey, JAVA_GEN_MODEL);

            // Now execute template template
            String result = templateCall.execute(eoModel);
            if (eoModel.hasErrors()) {
                throw new EoException("Problem with template " + modelKey + ": " + eoModel.getLog());
            }
            if (hasFileEnding()) {
                feedback.append("Written configuration to  '" + targetPath + "'\n");
                new IOString().setFileName(targetPath).write(result);
            }
            else {
                feedback.append("Configuration not persisted to  '" + targetPath + "' due to missing file ending\n");
                eo.warn("No file ending is set, so no file is written to " + targetPath);
            }
            counter++;
        }
        if (counter==1) {
            throw new EoException("No java entry in ModelConfig found matching " + getModule() + " " + getModuleScope() + " " + getNaturalId());
        }
        return feedback;
    }
}
