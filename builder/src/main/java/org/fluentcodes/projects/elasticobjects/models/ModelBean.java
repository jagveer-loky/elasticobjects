package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.FIELD_KEYS;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.INTERFACES;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.MODEL_KEY;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.PACKAGE_PATH;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.SUPER_KEY;

public class ModelBean extends ConfigBean implements ModelBeanProperties {
    public static final String FIELD_BEANS = "fieldBeans";
    private boolean resolved;
    private String modelKey;
    private String packagePath;
    private String superKey;
    private String interfaces;
    private Map<String, FieldBean> fieldBeans;

    public ModelBean() {
        super();
    }
    public ModelBean(String key) {
        this();
        setNaturalId(key);
    }

    public ModelBean(final Map values) {
        super(values);
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
                Map<String, Object> fieldBeans = (Map<String,Object>) ((Map)raw).get(key);
                FieldBean fieldJoiner = new FieldBean(fieldBeans);
                fieldJoiner.setNaturalId((String)key);
                this.fieldBeans.put((String)key, fieldJoiner);
            }
        }
        else {
            throw new EoInternalException("FieldKeys are neither String, Map or List");
        }
    }

    public List<String> getLocalFieldKeys() {
        return getFieldKeys();
    }

    public void setLocalFieldKeys(List<String> fieldKeys) {

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
    public List<String> getFieldKeys() {
        return new ArrayList<>(getFieldBeans().keySet());
    }

    public Map<String, FieldBean> getFieldBeans() {
        if (!resolved) {
            for (FieldBean fieldBean: fieldBeans.values()) {
                fieldBean.setModelBean(this);
            }
            resolved = true;
        }
        return fieldBeans;
    }

    public FieldBean getFieldBean(final String fieldKey) {
        return getFieldBeans().get(fieldKey);
    }

    public void setFieldBeans(Map<String, FieldBean> fieldBeans) {
        this.fieldBeans = fieldBeans;
    }

    public boolean hasFieldBeans() {
        return fieldBeans!=null && !fieldBeans.isEmpty();
    }

    public void resolveFieldBeans(Map<String, Map> fields) {
        for (FieldBean fieldBean: fieldBeans.values()) {
            if (!fieldBean.hasNaturalId()) {
                throw new EoInternalException("Could not get field definition for '" + fieldBean.getNaturalId() + "'.");
            }
            if (!fields.containsKey(fieldBean.getNaturalId())) {
                throw new EoInternalException("Could not get field definition for '" + fieldBean.getNaturalId() + "'.");
            }
            fieldBean.merge(new FieldBean(fields.get(fieldBean.getNaturalId())));
        }
    }

    public void resolveSuper(Map<String, ModelBean> modelBeans) {
        if (resolved) {
            return;
        }
        if ("CallImpl".equals(this.getNaturalId())) {
            System.out.println("");
        }
        if (this.hasSuperKey()) {
            ModelBean superModelBean = modelBeans.get(this.getSuperKey());
            superModelBean.resolveSuper(modelBeans, this.fieldBeans);
        }
        if (this.hasInterfaces()) {
            String[] interfaces = this.getInterfaces().split(",");
            for (String interfaceKey: interfaces) {
                if (!modelBeans.containsKey(interfaceKey)) {
                    throw new EoInternalException("Could not find interface '" + interfaceKey + "' for '" + getNaturalId() + "'." );
                }
                ModelBean interfaceModelBean = modelBeans.get(interfaceKey);
                interfaceModelBean.resolveSuper(modelBeans, this.fieldBeans);
            }
        }
        for (FieldBean fieldBean: fieldBeans.values()) {
            fieldBean.setModelBean(this);
        }
        resolved = true;
    }

    protected void resolveSuper(Map<String, ModelBean> modelBeans, Map<String, FieldBean> subFieldBeans) {
        if (!resolved) {

            if (this.hasSuperKey()) {
                ModelBean superModelBean = modelBeans.get(this.getSuperKey());
                superModelBean.resolveSuper(modelBeans, this.fieldBeans);
            }

            if (this.hasInterfaces()) {
                String[] interfaces = this.getInterfaces().split(",");
                for (String interfaceKey: interfaces) {
                    if (!modelBeans.containsKey(interfaceKey)) {
                        throw new EoInternalException("Could not find interface '" + interfaceKey + "' for '" + getNaturalId() + "'." );
                    }
                    ModelBean interfaceModelBean = modelBeans.get(interfaceKey);
                    interfaceModelBean.resolveSuper(modelBeans, this.fieldBeans);
                }
            }

            resolved = true;
        }
        if ("CallImpl".equals(this.getNaturalId())) {
            System.out.println("");
        }
        for (FieldBean fieldBean: this.fieldBeans.values()) {
            if (subFieldBeans.containsKey(fieldBean.getNaturalId())) {
                subFieldBeans.get(fieldBean.getNaturalId()).setOverride(true);
                continue;
            }
            subFieldBeans.put(fieldBean.getNaturalId(), new FieldBean(fieldBean));
        }
    }

    @Override
    public String toString() {
        return "ModelBean(" + getNaturalId() + ")";
    }
}
