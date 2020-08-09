package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.calls.ConfigResources;
import org.fluentcodes.projects.elasticobjects.calls.condition.Or;

import java.util.List;

/**
 * Created by Werner on 30.10.2016.
 */
interface ListConfigInterface extends ConfigResources {

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
