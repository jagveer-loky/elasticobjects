package org.fluentcodes.projects.elasticobjects;

public class ConfigTypeImpl {
    private String name;
    public ConfigTypeImpl() {

    }
    public ConfigTypeImpl(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
