package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.templates.Parser;

public abstract class GenerateCall extends CallImpl {
    public static String BUILD_PATH = "buildPath";
    private String buildPath;
    private String module;
    private String moduleScope;
    private String fileEnding;
    private String classPath;

    public GenerateCall() {
        super();
    }

    @Override
    protected boolean init(final EO eo) {
        this.buildPath = Parser.replace(buildPath, eo);
        this.module = Parser.replace(module,eo);
        this.moduleScope = Parser.replace(moduleScope, eo);
        this.fileEnding = Parser.replace(fileEnding, eo);
        this.classPath = Parser.replace(classPath, eo);
        return true;
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
}
