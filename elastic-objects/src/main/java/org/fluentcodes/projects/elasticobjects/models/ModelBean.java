package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.calls.Permission;
import org.fluentcodes.projects.elasticobjects.calls.PermissionRole;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static org.fluentcodes.projects.elasticobjects.calls.PermissionConfig.ROLE_PERMISSIONS;
import static org.fluentcodes.projects.elasticobjects.models.FieldConfig.FIELD_KEY;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.FIELD_KEYS;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.INTERFACES;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.MODEL_KEY;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.PACKAGE_PATH;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.SUPER_KEY;

public class ModelBean extends ConfigBean implements Model, Permission {
    public static final String FIELD_BEANS = "fieldBeans";
    private boolean resolved;
    private String modelKey;
    private String packagePath;
    private String superKey;
    private String interfaces;
    private Map<String, FieldBeanInterface> fieldBeans;
    private PermissionRole rolePermissions;

    public ModelBean() {
        super();
        fieldBeans = new TreeMap<>();
    }

    public ModelBean(final String key) {
        super();
        setNaturalId(key);
        fieldBeans = new TreeMap<>();
    }

    public ModelBean(final Class modelClass, ShapeTypes shapeType) {
        this();
        setNaturalId(modelClass.getSimpleName());
        setModelKey(getNaturalId());
        setPackagePath(modelClass.getPackage().getName());
        setShapeType(shapeType);
        setConfigModelKey(shapeType.getModelConfigKey());
    }

    public ModelBean(final Map values) {
        super(values);
        setModelKey((String)values.get(MODEL_KEY));
        setPackagePath((String)values.get(PACKAGE_PATH));
        setInterfaces((String)values.get(INTERFACES));
        setSuperKey((String)values.get(SUPER_KEY));
        mergePermission(values.get(ROLE_PERMISSIONS));
        defaultShapeType();
        fieldBeans = new TreeMap<>();
        if (!values.containsKey(FIELD_KEYS)) {
            return;
        }
        Object raw = values.get(FIELD_KEYS);
        if ((raw instanceof String)) {
            if (((String) raw).isEmpty()) {
                return;
            }
            List<String> fieldKeys = Arrays.asList(((String) raw).split(","));
            for (String key : fieldKeys) {
                fieldBeans.put(key, new FieldBean(key));
            }
        }
        else if (raw instanceof List) {
            if (((List) raw).isEmpty()) {
                return;
            }
            List<String> fieldKeys = new ArrayList<>((List) raw);
            for (Object key : fieldKeys) {
                fieldBeans.put((String) key, new FieldBean((String)key));
            }
        }
        else if (raw instanceof Map) {
            if (((Map) raw).isEmpty()) {
                return;
            }
            for (Object key : ((Map)raw).keySet()) {
                Map<String, Object> fieldMap = (Map<String,Object>) ((Map)raw).get(key);
                FieldBean fieldBean = new FieldBean(fieldMap);
                fieldBean.setNaturalId((String)key);
                this.fieldBeans.put((String)key, fieldBean);
            }
        }
        else {
            throw new EoInternalException("FieldKeys are neither String, Map or List");
        }
    }

    protected void merge(final Map values) {
        if (values == null) {
            throw new EoInternalException("Null mapping model values for '" + getNaturalId() + "'.");
        }
        super.merge(values);
        setModelKey((String)values.get(MODEL_KEY));
        setPackagePath((String)values.get(PACKAGE_PATH));
        setInterfaces((String)values.get(INTERFACES));
        setSuperKey((String)values.get(SUPER_KEY));
        fieldBeans = new TreeMap<>();
        if (!values.containsKey(FIELD_KEYS)) {
            return;
        }
        Object raw = values.get(FIELD_KEYS);
        if ((raw instanceof String)) {
            if (((String) raw).isEmpty()) {
                return;
            }
            List<String> fieldKeys = Arrays.asList(((String) raw).split(","));
            for (String key : fieldKeys) {
                fieldBeans.put(key, new FieldBean(key));
            }
        }
        else if (raw instanceof List) {
            if (((List) raw).isEmpty()) {
                return;
            }
            List<String> fieldKeys = new ArrayList<>((List) raw);
            for (Object key : fieldKeys) {
                fieldBeans.put((String) key, new FieldBean((String)key));
            }
        }
        else if (raw instanceof Map) {
            if (((Map) raw).isEmpty()) {
                return;
            }
            for (Object key : ((Map)raw).keySet()) {
                Map<String, Object> fieldMap = null;
                try {
                    fieldMap = (Map<String, Object>) ((Map) raw).get(key);
                }
                catch (Exception e) {
                    throw new EoException("Problem casting field value " + raw);
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

    public Map<String, FieldBeanInterface> getFieldBeans() {
        return fieldBeans;
    }

    public FieldBeanInterface getFieldBean(final String fieldKey) {
        return getFieldBeans().get(fieldKey);
    }

    public void setFieldBeans(Map<String, FieldBeanInterface> fieldBeans) {
        this.fieldBeans = fieldBeans;
    }

    public boolean hasFieldBeans() {
        return fieldBeans!=null && !fieldBeans.isEmpty();
    }

    public void mergeFieldDefinition(Map<String, Map> fields) {
        for (FieldBeanInterface fieldBean: fieldBeans.values()) {
            if (!fieldBean.hasNaturalId()) {
                throw new EoInternalException("Could not get field definition for '" + fieldBean.getNaturalId() + "'.");
            }
            if (!fields.containsKey(fieldBean.getNaturalId())) {
                throw new EoInternalException("Could not get field definition for '" + fieldBean.getNaturalId() + "'.");
            }
            FieldBean fieldBeanFromConf = new FieldBean(fields.get(fieldBean.getNaturalId()));
            ((FieldBean) fieldBean).merge(fieldBeanFromConf);
        }
    }

    public void resolveSuper(Map<String, ModelBean> modelBeans, boolean mergeList) {
        if (resolved) {
            return;
        }
        if (this.hasSuperKey()) {
            ModelBean superModelBean = modelBeans.get(this.getSuperKey());
            superModelBean.resolveSuper(modelBeans, this.fieldBeans, mergeList);
        }
        if (this.hasInterfaces()) {
            String[] interfaces = this.getInterfaces().split(",");
            for (String interfaceKey: interfaces) {
                if (!modelBeans.containsKey(interfaceKey)) {
                    throw new EoInternalException("Could not find interface '" + interfaceKey + "' for '" + getNaturalId() + "'." );
                }
                ModelBean interfaceModelBean = modelBeans.get(interfaceKey);
                interfaceModelBean.resolveSuper(modelBeans, this.fieldBeans, mergeList);
            }
        }
        for (FieldBeanInterface fieldBean: fieldBeans.values()) {
            fieldBean.setModelBean(this);
        }
        resolved = true;
    }

    protected void resolveSuper(Map<String, ModelBean> modelBeans, Map<String, FieldBeanInterface> subFieldBeans, boolean mergeList) {
        if (resolved) {
            return;
        }
        if (this.hasSuperKey()) {
            if (!modelBeans.containsKey(this.getSuperKey())) {
                throw new EoException("Could not resolve super key '" + getSuperKey() + "' for '" + getNaturalId() + "'.");
            }
            ModelBean superModelBean = modelBeans.get(this.getSuperKey());
            superModelBean.resolveSuper(modelBeans, this.fieldBeans, mergeList);
        }

        if (this.hasInterfaces()) {
            String[] interfaces = this.getInterfaces().split(",");
            for (String interfaceKey : interfaces) {
                if (!modelBeans.containsKey(interfaceKey)) {
                    throw new EoInternalException("Could not find interface '" + interfaceKey + "' for '" + getNaturalId() + "'.");
                }
                ModelBean interfaceModelBean = modelBeans.get(interfaceKey);
                interfaceModelBean.resolveSuper(modelBeans, this.fieldBeans, mergeList);
            }
        }
        resolved = true;

        if (!mergeList) {
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
        return getShapeType() + " " + getNaturalId() ;
    }
}
