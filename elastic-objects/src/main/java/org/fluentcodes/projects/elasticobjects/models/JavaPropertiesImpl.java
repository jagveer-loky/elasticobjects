package org.fluentcodes.projects.elasticobjects.models;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.models.ConfigImpl.MODULE;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.PACKAGE_PATH;

public class JavaPropertiesImpl implements JavaProperties {
    public static final String CLASS_PATH = "classPath";
    private final String module;
    private final String classPath;
    private final String packagePath;

    public JavaPropertiesImpl(Map<String,Object> map) {
        this.module = (String) map.get(MODULE);
        this.classPath = (String) map.get(CLASS_PATH);
        this.packagePath = (String) map.get(PACKAGE_PATH);
    }

    public String getModule() {
        return module;
    }

    public String getClassPath() {
        return classPath;
    }

    public String getPackagePath() {
        return packagePath;
    }
}
