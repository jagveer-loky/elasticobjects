package org.fluentcodes.projects.elasticobjects.calls.generate.java.helper;

import org.fluentcodes.projects.elasticobjects.models.FieldConfig;

import java.util.*;

public class FieldHelper {
    private Map<String, FieldBean> fieldMap;
    private List<String> fieldKeys;

    public FieldHelper(Object raw, String... ignoreKeys) {
        fieldMap = new TreeMap<>();
        fieldKeys = new ArrayList<>();
        if (raw == null) {
            fieldMap = null;
            fieldKeys = null;
            return;
        }
        if ((raw instanceof String) && !((String) raw).isEmpty()) {
            fieldKeys.addAll(Arrays.asList(((String) raw).split(",")));
            for (String key : fieldKeys) {
                fieldMap.put(key, new FieldBean(key));
            }
        } else if ((raw instanceof List) && !((List) raw).isEmpty()) {
            fieldKeys.addAll((List) raw);
            for (Object key : fieldKeys) {
                fieldMap.put((String) key, new FieldBean((String)key));
            }
        }
        else if ((raw instanceof Map) && !((Map) raw).isEmpty()) {

            for (Object key : ((Map)raw).keySet()) {
                Map<String, Object> fieldMap = (Map<String,Object>) ((Map)raw).get(key);
                FieldBean fieldJoiner = new FieldBean((String)key, fieldMap);
                boolean skip = false;

                for (String ignoreKey:ignoreKeys) {
                    /*if (fieldModifier.(key).containsKey(ignoreKey)) {
                        skip =true;
                        break;
                    }*/
                }
                if (!skip) {
                    this.fieldMap.put((String)key, fieldJoiner);
                    fieldKeys.add((String)key);
                }
            }
        }
    }

    public FieldBean get(String key) {
        return fieldMap.get(key);
    }

    public List<String> getFieldKeys() {
        return fieldKeys;
    }

    public void merge(final String fieldKey, final FieldConfig fieldConfig) {
        get(fieldKey).merge(fieldConfig);
    }

    public Map<String, Map<String, Object>> createValueMap() {
        Map<String, Map<String, Object>> result = new TreeMap<>();
        for (String key: fieldMap.keySet()) {
            result.put(key, fieldMap.get(key).getProperties());
        }
        return result;
    }
}
