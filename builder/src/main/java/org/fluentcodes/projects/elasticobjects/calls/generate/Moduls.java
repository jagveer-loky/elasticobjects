package org.fluentcodes.projects.elasticobjects.calls.generate;

public enum Moduls {
    BUILDER("builder"),
    BUILDER_EO("builder-eo"),
    EO("elastic-objects"),
    EO_CSV("eo-csv"),
    EO_DB("eo-db"),
    EO_TEST("eo-test"),
    EO_XLSX("eo-xlsx"),
    SP("example-springboot");
    private String name;
    Moduls(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
