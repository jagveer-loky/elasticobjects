package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

public interface JavaProperties {
    String getModule();
    String getClassPath();
    String getPackagePath();
    void setModule(String module);
    void setClassPath(String classPath);
    void setPackagePath(String packagePath);
}
