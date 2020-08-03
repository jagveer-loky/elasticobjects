package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

/**
 * Created by werner.diwischek on 19.02.18.
 */
public class DBParams {
    private String table;
    private String idKey;
    private List<String> naturalKeys;
    private Boolean hibernateAnnotations;

    public DBParams(Object object) {
        if (object == null) {
            return;
        }
        if (!(object instanceof Map)) {
            return;
        }
        Map map = (Map) object;
        this.table = ScalarConverter.toString(map.get(F_TABLE));
        this.idKey = ScalarConverter.toString(map.get(F_ID_KEY));
        this.hibernateAnnotations = ScalarConverter.toBoolean(map.get(F_HIBERNATE_ANNOTATIONS));
        if (map.get(F_NATURAL_KEYS) != null) {
            if (map.get(F_NATURAL_KEYS) instanceof List) {
                this.naturalKeys = (List) map.get(F_NATURAL_KEYS);
            } else if (map.get(F_NATURAL_KEYS) instanceof String) {
                this.naturalKeys = Arrays.asList(((String) map.get(F_NATURAL_KEYS)).split(","));
            } else {
                this.naturalKeys = new ArrayList<>();
            }
        } else {
            this.naturalKeys = new ArrayList<>();
        }
    }

    public String getTable() {
        return table;
    }

    public String getIdKey() {
        return idKey;
    }

    public List<String> getNaturalKeys() {
        return naturalKeys;
    }

    public Boolean isHibernateAnnotations() {
        return hibernateAnnotations;
    }
}
