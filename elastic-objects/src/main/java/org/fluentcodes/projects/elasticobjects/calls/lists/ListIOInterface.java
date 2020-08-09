package org.fluentcodes.projects.elasticobjects.calls.lists;

import java.util.List;

public interface ListIOInterface {
    List read(ListParams listParams) ;

    Object readRow() ;

    Object readRow(int i) ;

    List readHead(int i) ;

    void write(List rows) ;

    void reset() ;

    void close() ;
}
