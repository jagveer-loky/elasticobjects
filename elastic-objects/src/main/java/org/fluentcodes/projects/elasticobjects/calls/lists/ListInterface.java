package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.Parser;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.TemplateMarker;
import org.fluentcodes.projects.elasticobjects.domain.BaseInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Werner on 13.9.2020.
 */
public interface ListInterface {
    String LIST_PARAMS = "listParams";
    ListParams getListParams();
    ListInterface setListParams(ListParams listParams);
    String getTargetPath();

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

    default String mapEo(EO eo, List filteredResult) {
        if (filteredResult.isEmpty())  {
            return "";
        }
        String targetPath = getTargetPath();
        boolean isMapped = TemplateMarker.SQUARE.hasStartSequence(targetPath);
        if (!isMapped) {
            eo.set(filteredResult, targetPath);
        }
        else {
            for (int i = 0; i < filteredResult.size(); i++) {
                Object row = filteredResult.get(i);
                if (isMapped) {
                    String target = Parser.replacePathValues(targetPath, EoRoot.ofValue(eo.getConfigsCache(), row));
                    eo.set(row, target);
                }
            }
        }
        if (targetPath!=null && targetPath.equals(Call.TARGET_AS_STRING)) {
            return "TODO asString";
        }
        return "";
    }

    default List toList(EO adapter) {
        List toWrite = new ArrayList();
        if (adapter.isEoEmpty()) {
            adapter.warn("Empty adapter -- nothing to write for " + adapter.getPath());
            return toWrite;
        }
        List<String> keys = null;
        try {
            keys = new ArrayList<>(adapter.keysEo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (keys == null || keys.isEmpty()) {
            return toWrite;
        }
        EO firstChild = null;
        try {
            firstChild = adapter.getEo(keys.get(0));
        } catch (Exception e) {
            e.printStackTrace();
            return toWrite;
        }
        if (firstChild == null || firstChild.isEoEmpty()) {
            return toWrite;
        }

        List<String> colKeys = null;
        try {
            colKeys = new ArrayList<>(firstChild.keysEo());
        } catch (Exception e) {
            e.printStackTrace();
            return toWrite;
        }
        getListParams().prepare();
        int rowHead = getListParams().getRowHead();
        if (rowHead > -1) {
            for (int i = 0; i < rowHead; i++) {
                toWrite.add(new ArrayList<>());
            }
            toWrite.add(colKeys);
        }
        for (String key : keys) {
            EO child = null;
            try {
                child = adapter.getEo(key);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            if (child == null) {
                continue;
            }
            List row = new ArrayList();
            if (child.isEoEmpty()) {
                toWrite.add(row);
                continue;
            }
            for (String colKey : colKeys) {
                try {
                    row.add(child.get(colKey));
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
            toWrite.add(row);
        }
        return toWrite;
    }

    default List<List<String>> flattenToStringList(EO eo, List values, List<String> keys) {
        List<List<String>> result = new ArrayList<>();
        Map<String, Integer> keyPosition = new LinkedHashMap<>();
        boolean externalKey = true;
        if (keys == null || keys.isEmpty()) {
            keyPosition.put(BaseInterface.NATURAL_ID, 0);
            externalKey = false;
        }
        else {
            for (int i = 0; i<keys.size();i++) {
                keyPosition.put(keys.get(i), i);
            }
        }
        int keyMap = 1;
        for (Object row : values) {
            List<String> rowList = new ArrayList<>(Collections.nCopies(keyPosition.size(), ""));
            Map<String, Object> valueMap = (Map<String, Object>) row;

            for (String key: valueMap.keySet()) {
                Object valueMapValue = valueMap.get(key);
                if (valueMapValue==null) {
                    continue;
                }
                String value = null;
                if (valueMapValue instanceof String) {
                    value = (String)valueMapValue;
                }
                else if (valueMapValue instanceof Enum) {
                    value = ((Enum) valueMapValue).toString();
                }
                else if ((valueMapValue instanceof Map)) {
                    if (((Map) valueMapValue).isEmpty()) {
                        value = "";
                    }
                    else{
                        value = new EOToJSON().setSerializationType(JSONSerializationType.STANDARD).toJson(eo.getConfigsCache(), valueMapValue);
                    }                }
                else if ((valueMapValue instanceof List)) {
                    if (((List) valueMapValue).isEmpty()) {
                        value = "";
                    }
                    else{
                        value = new EOToJSON().setSerializationType(JSONSerializationType.STANDARD).toJson(eo.getConfigsCache(), valueMapValue);
                    }
                }
                else if ((valueMapValue instanceof Date) || (valueMapValue instanceof Integer) || (valueMapValue instanceof Float) || (valueMapValue instanceof Double) || (valueMapValue instanceof Long)){
                    value = valueMapValue.toString();
                }
                else {
                    value = new EOToJSON().setSerializationType(JSONSerializationType.STANDARD).toJson(eo.getConfigsCache(), valueMapValue);
                }
                try {
                    if (!keyPosition.containsKey(key)) {
                        if (!externalKey) {
                            keyPosition.put(key, keyMap);
                            keyMap++;
                            rowList.add(value);
                        }
                    } else {
                        rowList.set(keyPosition.get(key), value);
                    }
                }
                catch (Exception e) {
                    System.out.println();
                }
            }
            result.add(rowList);
        }
        result.add(0, new ArrayList<>(keyPosition.keySet()));
        return result;
    }

    default String asString(EO eo, List values, List<String> keys) {
        List<List<String>> flattened = flattenToStringList(eo, values, keys);
        int max = flattened.get(0).size();
        StringBuilder builder = new StringBuilder();
        for (List<String> row: flattened) {
            for (int i=0; i< row.size();i++) {
                builder.append("\"");
                builder.append(
                        row.get(i)
                                .replaceAll("\"", "\"\"")
                                .replaceAll("\n", "\r"));
                builder.append("\"");
                if (i<max) {
                    builder.append(";");
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
