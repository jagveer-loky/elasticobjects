package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.domain.Base;

import java.util.Map;

public interface PropertiesAccessor extends Base {

    Map getProperties();
    default boolean hasProperties() {
        return getProperties()!=null;
    }


}
