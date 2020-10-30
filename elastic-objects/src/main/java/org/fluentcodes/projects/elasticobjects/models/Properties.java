package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.domain.Base;

import java.util.Map;

public interface Properties extends Base {

    Map getProperties();
    default boolean hasProperties() {
        return getProperties()!=null;
    }


}
