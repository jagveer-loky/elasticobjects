package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.calls.ListParams;

import java.util.List;

public interface ListIOInterface extends IOInterface {
    List read(ListParams listParams) ;

    Object readRow() ;

    Object readRow(int i) ;

    List readHead(int i) ;

    void write(List rows) ;

    void reset() ;

    void close() ;
}
