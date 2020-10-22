package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.calls.generate.helper.FieldHelper;
import org.fluentcodes.projects.elasticobjects.calls.generate.helper.JavaImportHelper;
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
import static org.fluentcodes.projects.elasticobjects.models.FieldConfig.MODEL_KEYS;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.*;

public class GenerateJavaCall extends GenerateCall {
    private final static String JAVA_GEN_MODEL = "/modelKey";
    private final static String JAVA_GEN_FIELD_TYPE = "javaGenFieldType";
    private final static String JAVA_GEN_FIELD_OVERRIDE = "javaGenFieldOverride";
    private final static String JAVA_GEN_FIELD_FINAL = "javaGenFieldFinal";
    private final static String JAVA_GEN_EXTEND = "javaGenExtend";
    private final static String JAVA_GEN_IMPLEMENTS = "javaGenImplement";
    private final static String JAVA_GEN_IMPORT = "javaGenImport";
    private final static String JAVA_GEN_FIELD_KEYS_STRIPPED = "javaGenFieldKeysStripped";
    public final static String OVERRIDE = "override";
    public final static String FINAL = "final";
    public final static String GEN_IGNORE = "genIgnore";
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
            if (hasModuleScope() && !getModuleScope().equals(moduleScope)) {
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
            FieldHelper fields = new FieldHelper(eoModel.get(FIELD_KEYS), GEN_IGNORE);
            eoModel.set(fields.getFieldKeys(), JAVA_GEN_FIELD_KEYS_STRIPPED);

            for (String fieldKey : fields.getFieldKeys()) {
                EO eoField = eo.getEo(Path.DELIMITER, FieldConfig.class.getSimpleName(), fieldKey);
                if (eoField.isEmpty()) {
                    continue;
                }
                Map<String,Object> modifiers = fields.get(fieldKey);
                eoField.set(modifiers.containsKey(OVERRIDE) ? "@Override":"", JAVA_GEN_FIELD_OVERRIDE);
                eoField.set(modifiers.containsKey(FINAL) ? "final":"", JAVA_GEN_FIELD_FINAL);
                String fieldModelKeys = ((String) eoField.get(MODEL_KEYS));
                String[] models = fieldModelKeys.split(",");
                for (String model : models) {
                    String fieldPackagePath = (String) eo.get(Path.DELIMITER, ModelConfig.class.getSimpleName(), model, PACKAGE_PATH);
                    javaImport.check(fieldPackagePath, model);
                }
                if (models.length == 2) {
                    if (models[0].endsWith("Map")) {
                        eoField.set("Map<String, " + models[1] + ">", JAVA_GEN_FIELD_TYPE);
                    }
                    else if (models[0].endsWith("List")) {
                        eoField.set("List<" + models[1] + ">", JAVA_GEN_FIELD_TYPE);
                    }
                    else {
                        eoField.set(models[0], JAVA_GEN_FIELD_TYPE);
                    }
                }
                else {
                    eoField.set(fieldModelKeys, JAVA_GEN_FIELD_TYPE);
                }
            }

            // enrich interfaces (imports)
            if (eoModel.hasEo(INTERFACES)) {
                Object value = eoModel.get(INTERFACES);

                List<String> interfaces = (value instanceof String) ? Arrays.asList(((String)value).split(",")) : (List<String>) value;
                StringBuilder interfacePart = new StringBuilder("implements");
                for (String interfaceKey : interfaces) {
                    String interfacePackagePath = (String) eo.get(Path.DELIMITER, ModelConfig.class.getSimpleName(), interfaceKey, PACKAGE_PATH);
                    javaImport.check(interfacePackagePath, interfaceKey);
                    interfacePart.append(" " + interfaceKey + ",");
                }
                eoModel.set(interfacePart.toString().replaceAll(",$",""), JAVA_GEN_IMPLEMENTS);
            }
            else {
                eoModel.set(" ", JAVA_GEN_IMPLEMENTS);
            }

            // super key
            if (eoModel.hasEo(SUPER_KEY)) {
                String superModelKey = (String)eoModel.get(SUPER_KEY);
                try {
                    EO eoSuper = eo.getEo(superModelKey);
                    if (eoSuper.hasEo(PACKAGE_PATH)) {
                        String superPackagePath = (String) eoSuper.get(PACKAGE_PATH);
                        javaImport.check(superPackagePath, superModelKey);
                        eoModel.set("extends " + superModelKey, JAVA_GEN_EXTEND);
                    }
                    else {
                        throw new EoException("No packagePath for '" + superModelKey + "'.");
                    }
                }
                catch (Exception e) {
                    throw new EoException("Problem resolving packagePath for '" + superModelKey + "': " + e.getMessage());
                }
            }
            else {
                eoModel.set("", JAVA_GEN_EXTEND);
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
