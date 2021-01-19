package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.calls.PermissionBeanInterface;
import org.fluentcodes.projects.elasticobjects.calls.PermissionRole;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import static org.fluentcodes.projects.elasticobjects.models.FieldConfigInterface.FIELD_KEY;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.INTERFACES;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.MODEL_KEY;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.PACKAGE_PATH;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.SUPER_KEY;

public class ModelBean extends ConfigBean implements Model, PermissionBeanInterface, Comparable<ModelBean> {
    public static final String FIELD_BEANS = "fieldBeans";
    public static final String FIELD_KEYS = "fieldKeys";
    private boolean resolved;
    private String modelKey;
    private String packagePath;
    private String superKey;
    private String interfaces;
    private Map<String, FieldBeanInterface> fieldBeans;
    private PermissionRole rolePermissions;
    private Set<ModelBean> modelSet;

    public ModelBean() {
        super();
        fieldBeans = new TreeMap<>();
        modelSet = new TreeSet<>();
    }

    public ModelBean(final String key) {
        super();
        setNaturalId(key);
        fieldBeans = new TreeMap<>();
        modelSet = new TreeSet<>();
    }

    public ModelBean(final Class modelClass, ShapeTypes shapeType) {
        this();
        setNaturalId(modelClass.getSimpleName());
        setModelKey(getNaturalId());
        setPackagePath(modelClass.getPackage().getName());
        setShapeType(shapeType);
        setConfigModelKey(shapeType.getModelConfigKey());
    }


    public ModelBean(final ModelConfig config) {
        super(config);
        setModelKey(config.getModelKey());
        setPackagePath(config.getPackagePath());
        setInterfaces(config.getInterfaces());
        setSuperKey(config.getSuperKey());
        //setRolePermissions(config.g);
        defaultShapeType();
        fieldBeans = new TreeMap<>();
        modelSet = new TreeSet<>();
        addFieldMap(config);
    }

    public ModelBean(final Map values) {
        super(values);
        /* setModelKey((String)values.get(MODEL_KEY));
        setPackagePath((String)values.get(PACKAGE_PATH));
        setInterfaces((String)values.get(INTERFACES));
        setSuperKey((String)values.get(SUPER_KEY));
        mergePermission(values.get(ROLE_PERMISSIONS));
        defaultShapeType();
        fieldBeans = new TreeMap<>();
        modelSet = new TreeSet<>();
        if (!values.containsKey(FIELD_KEYS)) {
            return;
        }
        Object fieldEntries = values.get(FIELD_KEYS);
        if ((fieldEntries instanceof String)) {
            if (((String) fieldEntries).isEmpty()) {
                return;
            }
            List<String> fieldKeys = Arrays.asList(((String) fieldEntries).split(",\\s*"));
            for (String key : fieldKeys) {
                addField(key);
            }
        }
        else if (fieldEntries instanceof List) {
            if (((List) fieldEntries).isEmpty()) {
                return;
            }
            List<String> fieldKeys = new ArrayList<>((List) fieldEntries);
            for (Object key : fieldKeys) {
                addField((String)key);
            }
        }
        else if (fieldEntries instanceof Map) {
            if (((Map) fieldEntries).isEmpty()) {
                return;
            }
            addField((Map)fieldEntries);
        }
        else {
            throw new EoInternalException("FieldKeys are neither String, Map or List");
        }*/
    }


    protected void addFieldMap(final ModelConfig config) {
        for (FieldConfig fieldConfig: config.getFieldMap().values()) {
            fieldBeans.put(config.getNaturalId(), new FieldBean(fieldConfig));
        }
    }

    protected void addField(final String fieldKey) {
        FieldBean fieldBean = new FieldBean(fieldKey);
        if (hasFinal()) fieldBean.setFinal(getFinal());
        if (hasOverride()) fieldBean.setOverride(getOverride());
        fieldBeans.put(fieldKey, fieldBean);
    }

    protected void addFieldSuper(final FieldBean fieldBean) {
        FieldBean fieldBeanNew = new FieldBean(fieldBean);
        fieldBeanNew.setSuper(true);
        fieldBeans.put(fieldBean.getNaturalId(), fieldBean);
    }

    protected void addField(final Map fieldConfigMap) {
        for (Object key : fieldConfigMap.keySet()) {
            Map<String, Object> fieldConfigEntry = (Map<String,Object>) fieldConfigMap.get(key);
            FieldBean fieldBean = new FieldBean(fieldConfigEntry);
            fieldBean.setNaturalId((String)key);
            if (hasFinal()) fieldBean.setFinal(getFinal());
            if (hasOverride()) fieldBean.setOverride(getOverride());
            this.fieldBeans.put((String)key, fieldBean);
        }
    }

    public void merge(final Map valueMap) {
        if (valueMap == null) {
            throw new EoInternalException("Null mapping model values for '" + getNaturalId() + "'.");
        }
        super.merge(valueMap);
        setModelKey((String) valueMap.get(MODEL_KEY));
        setPackagePath((String) valueMap.get(PACKAGE_PATH));
        setInterfaces((String) valueMap.get(INTERFACES));
        setSuperKey((String) valueMap.get(SUPER_KEY));
        mergeRolePermissions(valueMap.get(ROLE_PERMISSIONS));
        defaultShapeType();
        modelSet = new TreeSet<>();
        fieldBeans = new TreeMap<>();
        if (!valueMap.containsKey(FIELD_KEYS)) {
            return;
        }
        Object fieldsFromMap = valueMap.get(FIELD_KEYS);
        if ((fieldsFromMap instanceof String)) {
            if (((String) fieldsFromMap).isEmpty()) {
                return;
            }
            List<String> fieldKeys = Arrays.asList(((String) fieldsFromMap).split(",\\s*"));
            for (String key : fieldKeys) {
                addField(key);
            }
        }
        else if (fieldsFromMap instanceof List) {
            if (((List) fieldsFromMap).isEmpty()) {
                return;
            }
            List<String> fieldKeys = new ArrayList<>((List) fieldsFromMap);
            for (String key : fieldKeys) {
                addField(key);
            }
        }
        else if (fieldsFromMap instanceof Map) {
            if (((Map) fieldsFromMap).isEmpty()) {
                return;
            }
            for (Object key : ((Map)fieldsFromMap).keySet()) {
                Map<String, Object> fieldMap = null;
                try {
                    fieldMap = (Map<String, Object>) ((Map) fieldsFromMap).get(key);
                }
                catch (Exception e) {
                    throw new EoException("Problem casting field value " + fieldsFromMap);
                }
                if (!fieldMap.containsKey(NATURAL_ID)) {
                    fieldMap.put(NATURAL_ID, key);
                }
                if (!fieldMap.containsKey(FIELD_KEY)) {
                    fieldMap.put(FIELD_KEY, key);
                }
                FieldBean fieldJoiner = new FieldBean(fieldMap);
                fieldJoiner.setNaturalId((String)key);
                this.fieldBeans.put((String)key, fieldJoiner);
            }
        }
        else {
            throw new EoInternalException("FieldKeys are neither String, Map or List");
        }
    }

    public Set<ModelBean> getModelSet() {
        return modelSet;
    }

    @Override
    public void defaultConfigModelKey() {
        if (hasConfigModelKey()) {
            return;
        }
        setConfigModelKey(ModelConfigObject.class.getSimpleName());
    }

    public void merge(final ModelBean modelBean) {
        super.merge(modelBean);
    }

    @Override
    public String getModelKey() {
        return modelKey;
    }

    @Override
    public void setModelKey(String modelKey) {
        this.modelKey = modelKey;
    }

    @Override
    public String getPackagePath() {
        return packagePath;
    }

    @Override
    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    @Override
    public String getSuperKey() {
        return superKey;
    }

    public void setFieldKeys(List<String> fieldKeys) {
    }

    @Override
    public void setSuperKey(String superKey) {
        this.superKey = superKey;
    }

    @Override
    public String getInterfaces() {
        return interfaces;
    }

    @Override
    public void setInterfaces(String interfaces) {
        this.interfaces = interfaces;
    }

    @Override
    public Set<String> getFieldKeys() {
        return getFieldBeans().keySet();
    }

    @Override
    public Map<String, FieldBeanInterface> getFieldBeans() {
        return fieldBeans;
    }

    @Override
    public Map<String, FieldConfig> getFieldMap() {
        throw new EoException("TODO");
    }

    @Override
    public void setFieldBeans(Map<String, FieldBeanInterface> fieldBeans) {
        this.fieldBeans = fieldBeans;
    }

    public void mergeFieldDefinition(Map<String, Map> fieldMap) {
        for (FieldBeanInterface fieldBean: fieldBeans.values()) {
            if (!fieldBean.hasNaturalId()) {
                throw new EoInternalException("Could not get field definition for '" + fieldBean.getNaturalId() + "'.");
            }
            if (!fieldMap.containsKey(fieldBean.getNaturalId())) {
                throw new EoInternalException("Could not get field definition for '" + fieldBean.getNaturalId() + "'.");
            }
            FieldBean fieldBeanFromMap = new FieldBean(fieldMap.get(fieldBean.getNaturalId()));
            if (isFinal()) fieldBeanFromMap.setFinal(true);
            ((FieldBean) fieldBean).merge(fieldBeanFromMap);
            fieldBean.setModelBean(this);
        }
    }

    public void resolveSuper(Map<String, ModelBean> modelBeans, boolean mergeFields) {
        if (resolved) {
            return;
        }
        if (this.hasSuperKey()) {
            if (!modelBeans.containsKey(superKey) || modelBeans.get(superKey) == null) {
                throw new EoInternalException("No model configuration found for superKey '" + superKey + "'");
            }
            ModelBean superModelBean = modelBeans.get(this.getSuperKey());
            modelSet.add(superModelBean);
            superModelBean.resolveSuper(modelBeans, this.fieldBeans, mergeFields);
        }
        if (this.hasInterfaces()) {
            String[] interfaces = this.getInterfaces().split(",");
            for (String interfaceKey: interfaces) {
                if (!modelBeans.containsKey(interfaceKey) || modelBeans.get(interfaceKey) == null) {
                    throw new EoInternalException("Could not find interface '" + interfaceKey + "' for '" + getNaturalId() + "'." );
                }
                ModelBean interfaceModelBean = modelBeans.get(interfaceKey);
                modelSet.add(interfaceModelBean);
                interfaceModelBean.resolveSuper(modelBeans, this.fieldBeans, mergeFields);
            }
        }
        for (FieldBeanInterface fieldBean: fieldBeans.values()) {
            fieldBean.setModelBean(this);
            for (String fieldModelKey: fieldBean.getModelKeys().split(",")) {
                if (!modelBeans.containsKey(fieldModelKey) || modelBeans.get(fieldModelKey) == null) {
                    throw new EoInternalException("Could not find model '" + fieldModelKey + "' for '" + fieldBean.getFieldKey() + "'." );
                }
                modelSet.add(modelBeans.get(fieldModelKey));
            }
        }
        resolved = true;
    }

    protected void resolveSuper(Map<String, ModelBean> modelBeans, Map<String, FieldBeanInterface> subFieldBeans, boolean mergeFields) {
        if (resolved) {
            return;
        }
        if (this.hasSuperKey()) {
            if (!modelBeans.containsKey(this.getSuperKey())) {
                throw new EoException("Could not resolve super key '" + getSuperKey() + "' for '" + getNaturalId() + "'.");
            }
            ModelBean superModelBean = modelBeans.get(this.getSuperKey());
            superModelBean.resolveSuper(modelBeans, this.fieldBeans, mergeFields);
        }

        if (this.hasInterfaces()) {
            String[] interfaces = this.getInterfaces().split(",");
            for (String interfaceKey : interfaces) {
                if (!modelBeans.containsKey(interfaceKey)) {
                    throw new EoInternalException("Could not find interface '" + interfaceKey + "' for '" + getNaturalId() + "'.");
                }
                ModelBean interfaceModelBean = modelBeans.get(interfaceKey);
                interfaceModelBean.resolveSuper(modelBeans, this.fieldBeans, mergeFields);
            }
        }
        resolved = true;

        if (!mergeFields) {
            return;
        }
        for (FieldBeanInterface fieldBean: this.fieldBeans.values()) {
            if (subFieldBeans.containsKey(fieldBean.getNaturalId())) {
                subFieldBeans.get(fieldBean.getNaturalId()).setOverride(true);
                continue;
            }
            FieldBean fieldBeanLocal = new FieldBean(fieldBean);
            fieldBeanLocal.setSuper(true);
            subFieldBeans.put(fieldBean.getNaturalId(), fieldBeanLocal);
        }
    }

    @Override
    public PermissionRole getRolePermissions() {
        return rolePermissions;
    }

    @Override
    public ModelBean setRolePermissions(PermissionRole rolePermissions) {
        this.rolePermissions = rolePermissions;
        return this;
    }

    @Override
    public String toString() {
        return getNaturalId() + "(" + getShapeType() + ")";
    }

    public String getClassName()  {
        return this.packagePath + "." + this.modelKey;
    }

    @Override
    public int compareTo(ModelBean modelBean) {
        return getClassName().compareTo(modelBean.getClassName());
    }
}
