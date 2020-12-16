package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.models.ConfigConfigInterface;
import org.fluentcodes.projects.elasticobjects.models.ConfigProperties;

import java.util.List;

import static org.fluentcodes.projects.elasticobjects.calls.lists.ListParams.COL_KEYS;
import static org.fluentcodes.projects.elasticobjects.calls.lists.ListParams.FILTER_RAW;
import static org.fluentcodes.projects.elasticobjects.calls.lists.ListParams.LENGTH;
import static org.fluentcodes.projects.elasticobjects.calls.lists.ListParams.ROW_END;
import static org.fluentcodes.projects.elasticobjects.calls.lists.ListParams.ROW_HEAD;
import static org.fluentcodes.projects.elasticobjects.calls.lists.ListParams.ROW_START;

public interface ListProperties extends ConfigConfigInterface, ConfigProperties {
    
    default boolean hasRowHead() {
        return getRowHead()!=null;
    }

    default Integer getRowHead() {
        return hasProperties()?(Integer)getProperties().get(ROW_HEAD):null;
    }

    default void setRowHead(final Integer value) {
        if (hasProperties()) {
            getProperties().put(ROW_HEAD, value);
        }
    }

    default boolean hasColKeys() {
        return getColKeys()!=null && !getColKeys().isEmpty();
    }

    default List<String> getColKeys() {
        return hasProperties()?(List<String>)getProperties().get(COL_KEYS):null;
    }

    default void setColKeys(final List<String> value) {
        if (hasProperties()) {
            getProperties().put(COL_KEYS, value);
        }
    }

    default boolean hasRowStart() {
        return getRowStart()!=null;
    }

    default Integer getRowStart() {
        return hasProperties()?(Integer)getProperties().get(ROW_START):null;
    }

    default void setRowStart(final Integer value) {
        if (hasProperties()) {
            getProperties().put(ROW_START, value);
        }
    }

    default boolean hasRowEnd() {
        return getRowEnd()!=null;
    }

    default Integer getRowEnd() {
        return hasProperties()?(Integer)getProperties().get(ROW_END):null;
    }

    default void setRowEnd(final Integer value) {
        if (hasProperties()) {
            getProperties().put(ROW_END, value);
        }
    }

    default boolean hasLength() {
        return getLength()!=null;
    }

    default Integer getLength() {
        return hasProperties()?(Integer)getProperties().get(LENGTH):null;
    }

    default void setLength(final Integer value) {
        if (hasProperties()) {
            getProperties().put(LENGTH, value);
        }
    }

    default boolean hasFilter() {
        return getFilter()!=null && !getFilter().isEmpty();
    }

    default String getFilter() {
        return hasProperties()?(String)getProperties().get(FILTER_RAW):null;
    }

    default void setFilter(final String value) {
        if (hasProperties()) {
            getProperties().put(FILTER_RAW, value);
        }
    }

}
