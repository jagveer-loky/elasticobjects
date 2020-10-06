package org.fluentcodes.projects.elasticobjects.calls.generate.helper;

import java.util.ArrayList;
import java.util.List;

public class JavaImportHelper {
    private final List<String> importModels;
    private final List<String> importSkipModels;
    private final String modelPackagePath;

    public JavaImportHelper(final String modelPackagePath) {
        this.modelPackagePath = modelPackagePath;
        importModels = new ArrayList<>();
        importSkipModels = new ArrayList<>();
    }

    public void check(final String packagePath, final String model) {
        if (model == null|| model.isEmpty()) {
            return;
        }
        if (packagePath == null|| packagePath.isEmpty()) {
            return;
        }
        if (importModels.contains(model) || importSkipModels.contains(model)) {
            return;
        }
        if (packagePath.equals("java.lang")) {
            importSkipModels.add(model);
            return;
        }
        if (packagePath.equals("java.lang")) {
            importSkipModels.add(model);
            return;
        }
        if (packagePath.equals(modelPackagePath)) {
            importSkipModels.add(model);
            return;
        }
        importModels.add(model);
        if (model.endsWith("Map") && !importModels.contains("Map")) {
            importModels.add("Map");
        }
        if (model.endsWith("List") && !importModels.contains("List")) {
            importModels.add("List");
        }

    }
    public List<String> getImportModels() {
        return importModels;
    }
}
