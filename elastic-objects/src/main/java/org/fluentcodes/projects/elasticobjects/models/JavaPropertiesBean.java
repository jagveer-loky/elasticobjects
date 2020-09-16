package org.fluentcodes.projects.elasticobjects.models;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.models.ConfigImpl.MODULE;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.PACKAGE_PATH;

public class JavaPropertiesBean implements JavaProperties {
    public static final String CLASS_PATH = "classPath";
    private String module;
    private String classPath;
    private String packagePath;

    public JavaPropertiesBean() {
    }
    public JavaPropertiesBean(JavaPropertiesImmutable immutable) {
        module = immutable.getModule();
        classPath = immutable.getClassPath();
        packagePath = immutable.getPackagePath();
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

    public void setModule(String module) {
        this.module = module;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }
}
