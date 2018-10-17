package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.calls.ListParams;

import java.util.List;

public interface ListIOInterface extends IOInterface {
    List read(ListParams listParams) throws Exception;

    Object readRow() throws Exception;

    Object readRow(int i) throws Exception;

    List readHead(int i) throws Exception;

    void write(List rows) throws Exception;

    void reset() throws Exception;

    void close() throws Exception;
}
