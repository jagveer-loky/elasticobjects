package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.NATURAL_ID;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.FIELD_KEYS;

public class FieldHelper {
    private Map<String, FieldBean> fieldMap;

    public FieldHelper(EO eoModel) {
            this(eoModel.get(FIELD_KEYS));
    }

    public FieldHelper(Object raw) {
        fieldMap = new TreeMap<>();
        if (raw == null) {
            return;
        }
        if ((raw instanceof String)) {
            if (((String) raw).isEmpty()) {
                return;
            }
            List<String> fieldKeys = Arrays.asList(((String) raw).split(","));
            for (String key : fieldKeys) {
                fieldMap.put(key, new FieldBean(key));
            }
        }
        else if (raw instanceof List) {
            if (((List) raw).isEmpty()) {
                return;
            }
            List<String> fieldKeys = new ArrayList<>((List) raw);
            for (Object key : fieldKeys) {
                fieldMap.put((String) key, new FieldBean((String)key));
            }
        }
        else if (raw instanceof Map) {
            if (((Map) raw).isEmpty()) {
                return;
            }
            for (Object key : ((Map)raw).keySet()) {
                try {
                    Map<String, Object> fieldMap = (Map<String, Object>) ((Map) raw).get(key);
                    fieldMap.put(NATURAL_ID, key);
                    FieldBean fieldJoiner = new FieldBean(fieldMap);
                    this.fieldMap.put((String)key, fieldJoiner);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            throw new EoInternalException("FieldKeys are neither String, Map or List");
        }
        /*for (String fieldKey : fieldMap.keySet()) {
            FieldBean fieldConfig =  new FieldBean((Map)eoModel.get("/" + FieldConfig.class.getSimpleName(), fieldKey));
            merge(fieldKey, fieldConfig);
        }*/

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
    public Map<String, FieldBean> getFieldMap() {
        return fieldMap;
    }

}
