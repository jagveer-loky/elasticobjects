package org.fluentcodes.projects.elasticobjects.calls.lists;


import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.calls.CallResource;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.Model;
import org.fluentcodes.projects.elasticobjects.models.ModelInterface;

import java.util.*;

/**
 * Write call for list type calls.
 * @author Werner Diwischek
 * @since 10.7.2020.
 */

public class ListWriteCall extends CallResource implements ListInterface {
    private ListParams listParams;

    public ListWriteCall() {
        super(PermissionType.WRITE);
    }

    public ListParams getListParams() {
        return listParams;
    }

    public Boolean execute(final EO eo){
        if (eo.isEoEmpty()) {
            throw new EoException("Empty adapter -- nothing to write for " + eo.getPath());
        }
        List toWrite = toList(eo, null);
        try {
            //((ListIOInterface) getListConfig().createIO()).write(toWrite);
        } catch (Exception e) {
            throw new EoException(e);
        }
        return true;
    }

    public List toList(EO adapter, Map externalAttributes) {
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
        listParams.prepare();
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

    public List<List<String>> flattenToStringList(EO eo, List values, List<String> keys) {
        List<List<String>> result = new ArrayList<>();
        Map<String, Integer> keyPosition = new LinkedHashMap<>();
        boolean externalKey = true;
        if (keys == null || keys.isEmpty()) {
            keyPosition.put(Model.NATURAL_ID, 0);
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
                    value = (String) valueMapValue;
                }
                else if ((valueMapValue instanceof Map)) {
                    if (((Map) valueMapValue).isEmpty()) {
                        value = "";
                    }
                    else{
                        value = new EOToJSON().setSerializationType(JSONSerializationType.STANDARD).toJSON(eo.getConfigsCache(), valueMapValue);
                    }                }
                else if ((valueMapValue instanceof List)) {
                    if (((List) valueMapValue).isEmpty()) {
                        value = "";
                    }
                    else{
                        value = new EOToJSON().setSerializationType(JSONSerializationType.STANDARD).toJSON(eo.getConfigsCache(), valueMapValue);
                    }
                }
                else if ((valueMapValue instanceof Integer) || (valueMapValue instanceof Float) || (valueMapValue instanceof Double) || (valueMapValue instanceof Long)){
                    value = valueMapValue.toString();
                }
                else {
                    value = new EOToJSON().setSerializationType(JSONSerializationType.STANDARD).toJSON(eo.getConfigsCache(), valueMapValue);
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

    public String asString(EO eo, List values, List<String> keys) {
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
