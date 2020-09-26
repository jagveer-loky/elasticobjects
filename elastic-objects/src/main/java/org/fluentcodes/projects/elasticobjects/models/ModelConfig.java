package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.PathPattern;
import org.fluentcodes.projects.elasticobjects.UnmodifiableList;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by Werner on 09.10.2016.
 */
public abstract class ModelConfig extends ConfigImpl implements PropertiesModelAccessor {
    public static final String MODEL_KEY = "modelKey";
    public static final String FIELD_KEYS = "fieldKeys";
    public static final String INTERFACES = "interfaces";
    public static final String SUPER_KEY = "superKey";
    public static final String PACKAGE_PATH = "packagePath";

    private static final Logger LOG = LogManager.getLogger(ModelConfig.class);

    private final String modelKey;
    private final String packagePath;
    private final String superKey;
    private final String interfaces;

    private Class modelClass;
    private ModelInterface superModel;

    private final List<String> localFieldKeys;

    //resolved
    private final List<String> fieldKeys;
    private final Map<String, FieldConfig> fieldCacheMap;
    private final Map<String, ModelInterface> importClasses;
    private final Map<String, ModelInterface> interfacesMap;

    public ModelConfig(EOConfigsCache configsCache, Map map) {
        super(configsCache, map);
        modelKey = (String) map.get(MODEL_KEY);
        packagePath = (String) map.get(PACKAGE_PATH);
        superKey = (String) map.get(SUPER_KEY);
        interfaces = (String) map.get(INTERFACES);

        Object fieldKeysAsObject = map.get(FIELD_KEYS);
        if (fieldKeysAsObject == null) {
            fieldKeys = new ArrayList<>();
        } else if (fieldKeysAsObject instanceof String) {
            String fieldKeysAsString = (String) fieldKeysAsObject;
            if (fieldKeysAsString.isEmpty()) {
                fieldKeys = new ArrayList<>();
            } else {
                fieldKeys = Arrays.asList(fieldKeysAsString.split(","));
            }
        } else if (fieldKeysAsObject instanceof List) {
            fieldKeys = (List) fieldKeysAsObject;
        } else {
            fieldKeys = new ArrayList<>();
        }
        this.localFieldKeys = new UnmodifiableList<>(new ArrayList<>(fieldKeys));
        // resolved
        this.fieldCacheMap = new LinkedHashMap<>();
        this.interfacesMap = new LinkedHashMap<>();
        this.importClasses = new LinkedHashMap<>();
    }

    protected static final ModelConfig addByClassName(EOConfigsCache configsCache, String key) {
        LOG.info("Started find class " + key);
        final Map modelMap = new HashMap();
        Class modelClass;
        try {
            modelClass = Class.forName(key);
        } catch (Exception e) {
            throw new EoException("Could not find Class " + key + ": " + e.getMessage());
        }
        if (modelClass.getSuperclass() != null && modelClass.getSuperclass() != Object.class) {
            ModelConfig.addByClassName(configsCache, modelClass.getSuperclass().getName());
        }
        final String modelKey = key.replaceAll(".*\\.", "");
        if (configsCache.hasConfigKey(ModelConfig.class, modelKey)) {
            throw new EoException("An entry for modelConfig '" + modelKey + "' already exists and would not be resolved by class '" + key + "'.");
        }
        modelMap.put(MODEL_KEY, modelKey);
        modelMap.put(NATURAL_ID, modelKey);
        modelMap.put(PACKAGE_PATH, modelClass.getPackage().getName());

        final Map<String, Object> properties = new HashMap<>();
        properties.put(PropertiesModelAccessor.CREATE, true);
        modelMap.put(PROPERTIES, properties);

        final Field[] fields = modelClass.getDeclaredFields();
        final List<String> fieldKeys = new ArrayList();
        Map<String, Field> fieldMap = new HashMap<>();
        for (Field field : fields) {
            LOG.info(field.getName());
            final String fieldName = field.getName();
            Method setMethod = ModelConfigObject.findSetter(field);
            Method getMethod = ModelConfigObject.findGetter(field);
            if (getMethod == null && setMethod == null) {
                continue;
            }
            String fieldKey = modelKey + "." + fieldName;
            fieldKeys.add(fieldKey);
            fieldMap.put(fieldKey, field);
        }
        if (fieldKeys.isEmpty()) {
            properties.put(PropertiesModelAccessor.SHAPE_TYPE, ShapeTypes.SCALAR);
        } else {
            properties.put(PropertiesModelAccessor.SHAPE_TYPE, ShapeTypes.BEAN.name());
        }
        modelMap.put(FIELD_KEYS, fieldKeys);
        configsCache.addModel(modelMap);
        for (String fieldKey: fieldMap.keySet()) {
            if (!configsCache.hasConfigKey(FieldConfig.class, fieldKey)) {
                FieldConfig.addByClassField(configsCache, fieldMap.get(fieldKey));
            }
        }
        return configsCache.findModel(modelKey);
    }

    @Override
    public String getKey() {
        return modelKey;
    }

    /**
     * The model name for the actions
     */
    public String getModelKey() {
        return this.modelKey;
    }
    //<call keep="JAVA" templateKey="CacheGetter.tpl" }

    @Override
    public boolean hasKey(Path path) {
        return true;
    }

    /**
     * Table definitions with pojo.
     */
    public List<String> getFieldKeys() {
        resolve();
        return this.fieldKeys;
    }

    public List<String> getLocalFieldKeys() {
        return this.localFieldKeys;
    }

    public Set<String> getFieldNames() {
        if (fieldCacheMap.isEmpty()) {
            return new HashSet<>();
        }
        return fieldCacheMap.keySet();
    }

    private final void setFieldKeys(List<String> fieldsNames) {
        if (isScalar()) {
            return;
        }
        resolve();
        for (String field : fieldKeys) {
            if (fieldsNames.contains(field)) {
                continue;
            }
            fieldsNames.add(field);
        }
    }

    public String getPackagePath() {
        return this.packagePath;
    }

    /**
     * A key for the super class of a class.
     */
    public String getSuperKey() {
        return this.superKey;
    }

    /**
     * A comma separeted list of interface keys.
     */
    public String getInterfaces() {
        return this.interfaces;
    }

    public Class getModelClass() {
        resolve();
        if (modelClass == null) {
            return Object.class;
        }
        return modelClass;
    }

    public Map<String, ModelInterface> getImportClasses() {
        resolve();
        return importClasses;
    }

    public ModelInterface getImportClasses(final String fieldName) {
        resolve();
        return this.importClasses.get(fieldName);
    }

    public Map<String, FieldConfig> getFieldCacheMap() {
        resolve();
        return fieldCacheMap;
    }

    public boolean hasFieldConfig(final String fieldName) {
        return this.fieldCacheMap.get(fieldName) != null;
    }

    public FieldConfig getFieldConfig(final String fieldName) {
        resolve();
        FieldConfig fieldCache = this.fieldCacheMap.get(fieldName);
        if (fieldCache == null) {
            throw new EoException("No fieldName defined " + fieldName + "(" + this.getModelKey() + ").");
        }
        return fieldCache;
    }

    public void setModelClass()  {
        if (getModelKey() == null) {
            throw new EoException("No modelkey defined. No model class could be derived!");
        }
        String packagePath = getPackagePath();
        if (packagePath == null) {
            throw new EoException("No packagePath for " + modelKey + "defined.");
        }
        try {
            if (getConfigsCache().getScope() != Scope.CREATE) {
                this.modelClass = (Class.forName(packagePath + "." + modelKey));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new EoException("Could not resolve class with packagePath " + packagePath + " and modelKey " + modelKey, e);
        }
    }

    public ModelInterface getSuperModel()  {
        resolve();
        return superModel;
    }

    private final void setSuperModel() {
        if (superKey == null || superKey.isEmpty()) {
            return;
        }
        ModelInterface model = getConfigsCache().findModel(superKey);
        this.superModel = model;
    }

    private final void setInterfacesMap() {
        if (interfaces == null || interfaces.isEmpty()) {
            return;
        }
        String[] interfaceArray = interfaces.split(",");
        for (String interfaceEntry : interfaceArray) {
            interfacesMap.put(interfaceEntry, getConfigsCache().findModel(interfaceEntry));
        }
    }
    /*
    public Map<String, Object> getNaturalValues(Object object) {
        Map<String, Object> where = new HashMap<String, Object>();
        if (object == null) {
            throw new EoException("Null entry object. No map could be created!");
        }
        if (getNaturalKeys() == null) {
            throw new EoException("Null natural keys defined. No map could be created!");
        }
        for (String key : getNaturalKeys()) {
            Object value = get(key, object);
            where.put(key, value);
        }
        return where;
    }

     */

    public Class getFieldClass(String fieldName) {
        if (!this.isObject()) {
            return Object.class;
        }
        return getFieldConfig(fieldName).getModelClass();
    }

    private void setFieldKeys() {
        if (getConfigsCache().getScope() == Scope.CREATE && getShapeType() == ShapeTypes.INTERFACE) {
            return;
        }
        for (String key : interfacesMap.keySet()) {
            ((ModelConfig) interfacesMap.get(key)).setFieldKeys(this.fieldKeys);
        }

        if (getConfigsCache().getScope() != Scope.CREATE) {
            if (superModel != null) {
                ((ModelConfig) superModel).setFieldKeys(this.fieldKeys);
            }
        }
    }

    @Override
    public boolean isEmpty(final Object object) {
        resolve();
        if (object == null) {
            return true;
        }
        Map valueMap = getKeyValues(object, new PathPattern("*"));

        return valueMap.size() == 0;
    }

    @Override
    public Map getKeyValues(final Object object, final PathPattern pathPattern) {
        resolve();
        Set<String> keySet = keys(object);
        Map keyValues = new LinkedHashMap();
        List<String> keys;
        if (pathPattern == null || pathPattern.get().size() == 0) {
            keys = new ArrayList(keySet);
        } else {
            keys = pathPattern.filter(keySet);
        }
        for (String key : keys) {
            try {
                Object value = get(key, object);
                if (value == null) {
                    continue;
                }
                keyValues.put(key, value);
            } catch (Exception e) {
                LOG.warn("Problem getting value for " + key + ": " + e.getMessage());
            }
        }
        return keyValues;
    }

    public void resolve()  {
        if (isResolved()) {
            return;
        }
        setResolved();
        setModelClass();
        setSuperModel();
        setInterfacesMap();
        if (!isObject()) {
            return;
        }
        //setModelCacheClass();
        setFieldKeys();
    }

    public boolean isToSerialize() {
        return getShapeType() == ShapeTypes.SCALAR_SERIALIZED;
    }

    /*public Object getId(Object object) {
        return this.get(getIdKey(), object);
    }*/

    public boolean equals(ModelInterface modelCache) {
        if (modelKey == null) {
            return false;
        }
        if (modelCache == null) {
            return false;
        }
        return modelKey.equals(modelCache.getModelKey());
    }

    @Override
    public String toString() {
        if (this == null) {
            return "NULL";
        }
        if (this.modelKey == null) {
            return "EMPTY";
        }
        return this.modelKey;
    }

    public String toJSON(final JSONSerializationType serializationType, final Object object) {
        final StringBuffer content = new StringBuffer("");

        String value = ScalarConverter.toString(object);
        value = value.replaceAll("\n", "\\\\n");
        if (serializationType == JSONSerializationType.SCALAR) {
            if (modelClass != String.class) {
                return "\"(" + modelKey + ")" + value + "\"";
            }
        }
        content.append(value);
        if (modelClass == String.class) {
            return "\"" + content.toString() + "\"";
        } else {
            return content.toString();
        }
    }

    @Override
    public boolean hasSetter(final String fieldName) {
        return false;
    }
    @Override
    public boolean hasGetter(final String fieldName) {
        return false;
    }

    @Override
    public boolean isNumber() {
        return false;
    }
    @Override
    public boolean hasModel() {
        return true;
    }
    @Override
    public boolean isCreate() {
        return false;
    }
    @Override
    public boolean isMap() {
        return false;
    }
    @Override
    public boolean isSet() {
        return false;
    }
    @Override
    public boolean isList() {
        return false;
    }
    @Override
    public boolean isObject() {
        return false;
    }
    @Override
    public boolean isListType() {
        return false;
    }
    @Override
    public boolean isCall() {
        return false;
    }
    @Override
    public boolean isMapType() {
        return false;
    }
    @Override
    public boolean isScalar() {
        return false;
    }
    @Override
    public boolean isNull() {
        return false;
    }

}
