package org.fluentcodes.projects.elasticobjects.models;

public enum ModuleScope {
    MAIN("main"),TEST("test");
    private String directory;
    ModuleScope(final String directory) {
        this.directory = directory;
    }
    public String dir() {
        return directory;
    }
}
