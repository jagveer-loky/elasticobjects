package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.calls.ListMapper;
import org.fluentcodes.projects.elasticobjects.calls.ListParams;
import org.fluentcodes.projects.elasticobjects.condition.Or;

import java.util.List;

/**
 * Created by Werner on 30.10.2016.
 */
interface ListConfigInterface extends ConfigIOInterface {

    ListParams getListParams();

    ListMapper getListMapper();

    Integer getRowHead();

    boolean hasRowHead();

    Integer getRowStart();

    Integer getRowEnd();

    Integer getLength();

    Or getOr();

    boolean hasColKeys();

    List<String> getColKeys();

    void setColKeys(Object object);

}
