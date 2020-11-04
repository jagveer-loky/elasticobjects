package org.fluentcodes.projects.elasticobjects.calls.generate.java.helper;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigInterface;
import org.fluentcodes.projects.elasticobjects.models.Models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.fluentcodes.projects.elasticobjects.calls.generate.java.GenerateJavaCall.FIELD_MAP;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.FIELD_KEYS;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.INTERFACES;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.PACKAGE_PATH;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.SUPER_KEY;

public class JavaImportHelper {
    private final static String JAVA_GEN_IMPORT = "javaGenImport";
    private final List<String> importModels;
    private final List<String> importSkipModels;
    private final String modelPackagePath;

    public JavaImportHelper( EO eoModel) {
        this.modelPackagePath = (String)eoModel.get(PACKAGE_PATH);
        importModels = new ArrayList<>();
        importSkipModels = new ArrayList<>();

        Set<String> fields = eoModel.getEo(FIELD_MAP).keys();
        for (String fieldKey : fields) {
            FieldConfig fieldConfig = eoModel.getConfigsCache().findField(fieldKey);
            check(fieldConfig.getModels());
        }
        // enrich interfaces (imports)
        if (eoModel.hasEo(INTERFACES)) {
            Object value = eoModel.get(INTERFACES);
            List<String> interfaces = (value instanceof String) ? Arrays.asList(((String)value).split(",")) : (List<String>) value;
            for (String interfaceKey : interfaces) {
                String interfacePackagePath = (String) eoModel.get(PathElement.BACK, interfaceKey, PACKAGE_PATH);
                check(interfacePackagePath, interfaceKey);
            }
        }

        // super key
        if (eoModel.hasEo(SUPER_KEY)) {
            String superModelKey = (String)eoModel.get(SUPER_KEY);
            try {
                EO eoSuper = eoModel.getEo(PathElement.BACK, superModelKey);
                if (eoSuper.hasEo(PACKAGE_PATH)) {
                    String superPackagePath = (String) eoSuper.get(PACKAGE_PATH);
                    check(superPackagePath, superModelKey);
                }
                else {
                    throw new EoException("No packagePath for '" + superModelKey + "'.");
                }
            }
            catch (Exception e) {
                throw new EoException("Problem resolving packagePath for '" + superModelKey + "': " + e.getMessage());
            }
        }
        eoModel.set(getImportModels(), JAVA_GEN_IMPORT);
    }

    public void check(final Models models) {
        if (models.isEmpty()) {
            return;
        }
        for (ModelConfigInterface model: models.getModels()) {
            check(model.getPackagePath(), model.getModelKey());
        }
    }

    public void check(final String packagePath, final String model) {
        if (model == null|| model.isEmpty()) {
            return;
        }
        if (packagePath == null|| packagePath.isEmpty()) {
            return;
        }
        final String packagePathAndModel = packagePath + "." + model;
        if (importModels.contains(packagePathAndModel) || importSkipModels.contains(packagePathAndModel)) {
            return;
        }
        if (packagePath.equals("java.lang")) {
            importSkipModels.add(packagePathAndModel);
            return;
        }
        if (packagePath.equals(modelPackagePath)) {
            importSkipModels.add(packagePathAndModel);
            return;
        }
        importModels.add(packagePathAndModel);
        if (model.endsWith("Map") && !importModels.contains("java.util.Map")) {
            importModels.add(packagePath + ".Map");
        }
        if (model.endsWith("List") && !importModels.contains("java.util.List")) {
            importModels.add(packagePath + ".List");
        }

    }
    public List<String> getImportModels() {
        return importModels;
    }
}
