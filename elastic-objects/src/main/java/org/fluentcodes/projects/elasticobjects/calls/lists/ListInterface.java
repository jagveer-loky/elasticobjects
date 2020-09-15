package org.fluentcodes.projects.elasticobjects.calls.lists;

import java.util.List;
import java.util.Map;

/**
 * Created by Werner on 13.9.2020.
 */
public interface ListInterface {
    public static final String LIST_PARAMS = "listParams";
    ListParams getListParams();

    default Integer getRowHead() {
        if (!hasListParams()) {
            return -1;
        }
        return getListParams().getRowHead();
    }
    default void setRowHead(Integer rowHead) {
        getListParams().setRowHead(rowHead);
    }

    default boolean hasRowHead() {
        return getRowHead()!=null && getRowHead()>-1;
    }

    default boolean isRowHead(Integer rowCounter) {
        return getListParams().hasRowHead(rowCounter);
    }

    default boolean hasListParams() {
        return getListParams()!=null;
    }

    default boolean hasRowStart() {
        return getRowStart()!=null && getRowStart()>-1;
    }
    default void setRowStart(Integer rowStart) {
        getListParams().setRowStart(rowStart);
    }

    default Integer getRowStart() {
        if (!hasListParams()) {
            return -1;
        }
        return getListParams().getRowStart();
    }

    default boolean isRowStart(Integer rowCounter) {
        return getListParams().isRowStart(rowCounter);
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

    default void setRowEnd(Integer rowEnd) {
        getListParams().setRowEnd(rowEnd);
    }

    default boolean isRowEnd(Integer rowCounter) {
        return getListParams().isRowEnd(rowCounter);
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

    default boolean hasColKeys() {
        return getListParams().hasColKeys();
    }

    default List<String> getColKeys() {
        return getListParams().getColKeys();
    }

    default void setColKeys(List<String> colKeys) {
        getListParams().setColKeys(colKeys);
    }

    default void setLength(Integer rowEnd) {
        getListParams().setRowStart(rowEnd);
    }

    default boolean hasFilterRaw() {
        return getListParams().hasFilter();
    }

    default void setFilter(String filter) {
        getListParams().setFilter(filter);
    }

    default String getFilter() {
        return getListParams().getFilter();
    }

    default Map<String, Object> createMapFromRow(List row) {
        return getListParams().createMapFromRow(row);
    }
}
