package org.fluentcodes.projects.elasticobjects.calls.generate.java.helper;

import org.fluentcodes.projects.elasticobjects.models.ModelConfigInterface;
import org.fluentcodes.projects.elasticobjects.models.Models;

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
