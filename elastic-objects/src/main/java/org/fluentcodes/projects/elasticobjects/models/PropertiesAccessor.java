package org.fluentcodes.projects.elasticobjects.models;

import java.util.Map;

public interface PropertiesAccessor extends Model{

    Map getProperties();
    default boolean hasProperties() {
        return getProperties()!=null;
    }


}
