package org.fluentcodes.projects.elasticobjects.config;

public interface IOInterface {
    Object read() throws Exception;
    void write(Object object) throws Exception;
}
