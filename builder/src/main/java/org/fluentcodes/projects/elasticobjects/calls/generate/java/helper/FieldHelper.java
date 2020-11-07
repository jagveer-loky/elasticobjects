package org.fluentcodes.projects.elasticobjects.calls.generate.java.helper;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;

import java.util.*;

import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.FIELD_KEYS;

public class FieldHelper {
    private Map<String, FieldBean> fieldMap;

    public FieldHelper(EO eoModel) {
        fieldMap = new TreeMap<>();
        if (!eoModel.hasEo(FIELD_KEYS)) {
            return;
        }
        Object raw = eoModel.get(FIELD_KEYS);
        if (raw == null) {
            fieldMap = null;
            return;
        }
        if ((raw instanceof String) && !((String) raw).isEmpty()) {
            List<String> fieldKeys = Arrays.asList(((String) raw).split(","));
            for (String key : fieldKeys) {
                fieldMap.put(key, new FieldBean(key));
            }
        } else if ((raw instanceof List) && !((List) raw).isEmpty()) {
            List<String> fieldKeys = new ArrayList<>((List) raw);
            for (Object key : fieldKeys) {
                fieldMap.put((String) key, new FieldBean((String)key));
            }
        }
        else if ((raw instanceof Map) && !((Map) raw).isEmpty()) {

            for (Object key : ((Map)raw).keySet()) {
                Map<String, Object> fieldMap = (Map<String,Object>) ((Map)raw).get(key);
                FieldBean fieldJoiner = new FieldBean((String)key, fieldMap);
                boolean skip = false;
                if (!skip) {
                    this.fieldMap.put((String)key, fieldJoiner);
                }
            }
        }
        else {
            throw new EoInternalException("FieldKeys are neither String, Map or List");
        }
        for (String fieldKey : fieldMap.keySet()) {
            FieldConfig fieldConfig = eoModel.getConfigsCache().findField(fieldKey);
            merge(fieldKey, fieldConfig);
        }

    }

    public FieldBean get(String key) {
        return fieldMap.get(key);
    }

    public List<String> filterKeys() {
        List <String> filtered = new ArrayList<>();
        for (String fieldKey: fieldMap.keySet()) {
            FieldBean fieldProperties = fieldMap.get(fieldKey);
            if (fieldProperties.hasOverride()) {
                continue;
            }
            if (fieldProperties.hasJsonIgnore()) {
                continue;
            }
            filtered.add(fieldKey);
        }
        return filtered;
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
