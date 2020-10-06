package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.fluentcodes.projects.elasticobjects.calls.CallImpl;

public abstract class GenerateCall extends CallImpl {
    private String buildPath;
    private String configType;
    private String module;
    private String moduleScope;
    private String classPath;
    private String fileEnding;

    public GenerateCall() {
        super();
    }
    public GenerateCall(final String configType) {
        super();
        this.configType = configType;
    }
    public String getBuildPath() {
        return buildPath;
    }

    public GenerateCall setBuildPath(String buildPath) {
        this.buildPath = buildPath;
        return this;
    }
    public boolean hasBuildPath() {
        return buildPath!=null && !buildPath.isEmpty();
    }

    public String getConfigType() {
        return configType;
    }

    public boolean hasConfigType() {
        return configType!=null && !configType.isEmpty();
    }

    public GenerateCall setConfigType(String configType) {
        this.configType = configType;
        return this;
    }

    public String getModule() {
        return module;
    }

    public GenerateCall setModule(String module) {
        this.module = module;
        return this;
    }
    public boolean hasModule() {
        return module!=null && !module.isEmpty();
    }

    public String getClassPath() {
        return classPath;
    }

    public GenerateCall setClassPath(String classPath) {
        this.classPath = classPath;
        return this;
    }

    public boolean hasClassPath() {
        return classPath !=null && !classPath.isEmpty();
    }

    public String getModuleScope() {
        return moduleScope;
    }

    public GenerateCall setModuleScope(String moduleScope) {
        this.moduleScope = moduleScope;
        return this;
    }

    public boolean hasModuleScope() {
        return moduleScope !=null && !moduleScope.isEmpty();
    }

    public String getFileEnding() {
        return fileEnding;
    }

    public void setFileEnding(String fileEnding) {
        this.fileEnding = fileEnding;
    }

    public boolean hasFileEnding() {
        return fileEnding!=null && !fileEnding.isEmpty();
    }
}
