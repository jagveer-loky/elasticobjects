package org.fluentcodes.projects.elasticobjects.calls.configs;

import java.util.*;

public class FieldHelper {
    private final Map<String,Map<String, Object>> fieldModifier;
    private final List<String> fieldKeys;

    protected FieldHelper(Object raw, String ignoreKey) {
        if (raw == null) {
            fieldModifier = null;
            fieldKeys = null;
            return;
        }
        fieldModifier = new HashMap();
        fieldKeys = new ArrayList<>();
        if ((raw instanceof String) && !((String) raw).isEmpty()) {
            fieldKeys.addAll(Arrays.asList(((String) raw).split(",")));
            for (String key : fieldKeys) {
                fieldModifier.put(key, new HashMap<>());
            }
        } else if ((raw instanceof List) && !((List) raw).isEmpty()) {
            fieldKeys.addAll((List) raw);
            for (Object key : fieldKeys) {
                fieldModifier.put((String) key, new HashMap<>());
            }
        } else if ((raw instanceof Map) && !((Map) raw).isEmpty()) {
            fieldModifier.putAll((Map<String, Map<String, Object>>) raw);
            for (String key : fieldModifier.keySet()) {
                if (fieldModifier.get(key).containsKey(ignoreKey)) {
                    continue;
                }
                fieldKeys.add(key);
            }
        }
    }

    public boolean isEmpty() {
        return fieldKeys==null || fieldKeys.isEmpty();
    }

    public Map<String, Object> get(String key) {
        return fieldModifier.get(key);
    }

    public List<String> getFieldKeys() {
        return fieldKeys;
    }
}
