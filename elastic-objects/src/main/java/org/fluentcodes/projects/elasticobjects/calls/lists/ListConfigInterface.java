package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.calls.ConfigResources;
import org.fluentcodes.projects.elasticobjects.calls.condition.Or;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Werner on 30.10.2016.
 */
public interface ListConfigInterface extends ConfigResources {
    public static final String LIST_PARAMS = "listParams";
    public static final String LIST_MAPPER = "listMapper";
    ListParams getListParams();

    ListMapper getListMapper();

    default Integer getRowHead() {
        if (!hasListParams()) {
            return -1;
        }
        return getListParams().getRowHead();
    }

    default boolean hasRowHead() {
        return getRowHead()!=null && getRowHead()>-1;
    }

    default boolean hasListParams() {
        return getListMapper()!=null;
    }

    default boolean hasRowStart() {
        return getRowStart()!=null && getRowStart()>-1;
    }

    default Integer getRowStart() {
        if (!hasListParams()) {
            return -1;
        }
        return getListParams().getRowStart();
    }

    default boolean hasRowEnd() {
        return getRowEnd()!=null && getRowEnd()>-1;
    }

    default Integer getRowEnd() {
        if (!hasListParams()) {
            return -1;
        }
        return getListParams().getRowEnd();
    }

    default boolean hasLength() {
        return getLength()!=null && getLength()>-1;
    }

    default Integer getLength() {
        if (!hasListParams()) {
            return -1;
        }
        return getListParams().getLength();
    }

    default boolean hasOr() {
        return getOr()!=null && !getOr().isEmpty();
    }

    default Or getOr() {
        if (!hasListParams()) {
            return new Or();
        }
        return getListParams().getFilter();
    }

    default boolean hasColKeys() {
        if (!hasListParams()) {
            return false;
        }
        return getListParams().getColKeys()!=null && !getListParams().getColKeys().isEmpty();
    }

    default List<String> getColKeys() {
        if (!hasListParams()) {
            return new ArrayList<>();
        }
        return getListParams().getColKeys();
    }

    default void setColKeys(List<String> colKeys) {
        if (!hasListParams()) {
            return ;
        }
        getListParams().setColKeys(colKeys);
    }

}
