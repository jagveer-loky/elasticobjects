package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.fluentcodes.projects.elasticobjects.calls.CallImpl;

public abstract class CreateCall extends CallImpl {
    private String buildPath;
    private String configType;
    private String module;
    private String moduleScope;
    private String classPath;
    private String fileEnding;

    public CreateCall() {
        super();
    }
    public CreateCall(final String configType) {
        super();
        this.configType = configType;
    }
    public String getBuildPath() {
        return buildPath;
    }

    public CreateCall setBuildPath(String buildPath) {
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

    public CreateCall setConfigType(String configType) {
        this.configType = configType;
        return this;
    }

    public String getModule() {
        return module;
    }

    public CreateCall setModule(String module) {
        this.module = module;
        return this;
    }
    public boolean hasModule() {
        return module!=null && !module.isEmpty();
    }

    public String getClassPath() {
        return classPath;
    }

    public CreateCall setClassPath(String classPath) {
        this.classPath = classPath;
        return this;
    }

    public boolean hasClassPath() {
        return classPath !=null && !classPath.isEmpty();
    }

    public String getModuleScope() {
        return moduleScope;
    }

    public CreateCall setModuleScope(String moduleScope) {
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
