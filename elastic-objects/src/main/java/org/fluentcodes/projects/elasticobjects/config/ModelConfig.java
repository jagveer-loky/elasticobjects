package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import org.fluentcodes.projects.elasticobjects.eo.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.paths.PathPattern;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by Werner on 09.10.2016.
 */
public abstract class ModelConfig extends ConfigImpl implements ModelInterface {
    private static final Logger LOG = LogManager.getLogger(ModelConfig.class);
    private final String modelKey;
    private final DBParams dbParams;
    private final EOParams eoParams;
    private final ViewParams viewParams;
    private final Map customParams;
    private final List<String> fieldKeys;
    private final String packagePath;
    private final String packageGroup;
    private final String author;
    private final String superKey;
    private final String interfaces;
    private final Map<String, FieldConfig> fieldCacheMap;
    private final Map<String, ModelInterface> importClasses;
    private final Map<String, ModelInterface> interfacesMap;
    private Class modelClass;
    private ModelInterface superModel;

    public ModelConfig(EOConfigsCache provider, Builder bean) {
        super(provider, bean);

        //<call keep="JAVA" templateKey="CacheSetter.tpl" }

        this.modelKey = bean.modelKey;
        this.eoParams = bean.eoParams;
        this.dbParams = bean.dbParams;
        this.viewParams = bean.viewParams;
        this.customParams = bean.customParams;

        this.fieldKeys = bean.fieldKeys;
        this.packagePath = bean.packagePath;
        this.packageGroup = bean.packageGroup;
        this.author = bean.author;
        this.superKey = bean.superKey;
        this.interfaces = bean.interfaces;
        //</call>

        this.fieldCacheMap = new LinkedHashMap<>();
        this.interfacesMap = new LinkedHashMap<>();
        this.importClasses = new LinkedHashMap<>();

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

    @Override
    public EOParams getEoParams() {
        return eoParams;
    }
    //<call keep="JAVA" templateKey="CacheGetter.tpl" }

    @Override
    public DBParams getDbParams() {
        return dbParams;
    }

    @Override
    public ViewParams getViewParams() {
        return viewParams;
    }

    @Override
    public Map getCustomParams() {
        return customParams;
    }

    /**
     * Table definitions with pojo.
     */
    public List<String> getFieldKeys() {
        if (fieldKeys.isEmpty()) {
            return null;
        }
        return this.fieldKeys;
    }

    private final void setFieldKeys(List<String> fieldsNames) throws Exception {
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

    /**
     * Table definitions with pojo.
     */
    public String getPackagePath() {
        return this.packagePath;
    }

    /**
     * Defines to which group a model belong e.g. asset/config/actions
     */
    public String getPackageGroup() {
        return this.packageGroup;
    }

    /**
     * The autor of something e,g. a class.
     */
    public String getAuthor() {
        return this.author;
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

    public Class getModelClass() throws Exception {
        resolve();
        if (modelClass == null) {
            return Object.class;
        }
        return modelClass;
    }

    public Map<String, ModelInterface> getImportClasses() throws Exception {
        resolve();
        return importClasses;
    }

    public ModelInterface getImportClasses(final String fieldName) throws Exception {
        resolve();
        return this.importClasses.get(fieldName);
    }

    public Map<String, FieldConfig> getFieldCacheMap() throws Exception {
        resolve();
        return fieldCacheMap;
    }

    public boolean hasField(final String fieldName) {
        return this.fieldCacheMap.get(fieldName) != null;
    }

    public FieldConfig getField(final String fieldName) throws Exception {
        resolve();
        FieldConfig fieldCache = this.fieldCacheMap.get(fieldName);
        if (fieldCache == null) {
            throw new Exception("No fieldName defined " + fieldName + "(" + this.getModelKey() + ").");
        }
        return fieldCache;
    }

    public void setModelClass() throws Exception {
        if (getModelKey() == null) {
            throw new Exception("No modelkey defined. No model class could be derived!");
        }
        try {
            if (getConfigsCache().getScope() != Scope.CREATE) {
                this.modelClass = (Class.forName(packagePath + "." + modelKey));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Could not resolve class with packagePath" + packagePath + " and modelKey " + modelKey, e);
        }
    }

    public ModelInterface getSuperModel() throws Exception {
        resolve();
        return superModel;
    }

    private final void setSuperModel() throws Exception {
        if (superKey == null || superKey.isEmpty()) {
            return;
        }
        ModelInterface model = getConfigsCache().findModel(superKey);
        this.superModel = model;
    }

    private final void setInterfacesMap() throws Exception {
        if (interfaces == null || interfaces.isEmpty()) {
            return;
        }
        String[] interfaceArray = interfaces.split(",");
        for (String interfaceEntry : interfaceArray) {
            interfacesMap.put(interfaceEntry, getConfigsCache().findModel(interfaceEntry));
        }
    }

    public Map<String, Object> getNaturalValues(Object object) throws Exception {
        Map<String, Object> where = new HashMap<String, Object>();
        if (object == null) {
            throw new Exception("Null entry object. No map could be created!");
        }
        if (getNaturalKeys() == null) {
            throw new Exception("Null natural keys defined. No map could be created!");
        }
        for (String key : getNaturalKeys()) {
            Object value = get(key, object);
            where.put(key, value);
        }
        return where;
    }

    public Class getFieldClass(String fieldName) throws Exception {
        if (!this.isObject()) {
            return Object.class;
        }
        return getField(fieldName).getModelClass();
    }

    private void setFieldKeys() throws Exception {
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
    public boolean isEmpty(final Object object) throws Exception {
        resolve();
        if (object == null) {
            return true;
        }
        Map valueMap = getKeyValues(object, new PathPattern("*"));

        return valueMap.size() == 0;
    }

    @Override
    public Map getKeyValues(final Object object, final PathPattern pathPattern) throws Exception {
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

    public void resolve() throws Exception {
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

    private void setModelCacheClass() throws Exception {
        if (!hasModelConfigKey()) {
            return;
        }
        ModelInterface cacheModel = getConfigsCache().findModel(getModelConfigKey());
    }

    /**
     * Table definitions with pojo.
     */
    public ShapeTypes getShapeType() {
        if (eoParams == null) {
            return ShapeTypes.NONE;
        }
        if (eoParams.getShapeType() == null) {
            return ShapeTypes.NONE;
        }
        return eoParams.getShapeType();
    }

    public boolean isToSerialize() {
        return getShapeType() == ShapeTypes.SCALAR_SERIALIZED;
    }

    /**
     * The model config key for the config object {@link Config}.
     */
    public boolean hasModelConfigKey() {
        return getModelConfigKey() != null;
    }


//</call>

    public String getModelConfigKey() {
        if (eoParams == null) {
            return null;
        }
        return eoParams.getModelConfigKey();
    }

    /**
     * A default Implementation if defined
     */
    public String getDefaultImplementation() {
        if (eoParams == null) {
            return null;
        }
        return eoParams.getDefaultImplementation();
    }

    public PathPattern getPathPattern() {
        if (eoParams == null) {
            return null;
        }
        return eoParams.getPathPattern();
    }

    /**
     * The id key for a table
     */
    public String getIdKey() {
        if (dbParams == null) {
            return null;
        }
        return dbParams.getIdKey();
    }

    /**
     * The natural keys of a table entry.
     */
    public List<String> getNaturalKeys() {
        if (dbParams == null) {
            return null;
        }
        return dbParams.getNaturalKeys();
    }

    /**
     * Table definitions with pojo.
     */
    public String getTable() {
        if (dbParams == null) {
            return null;
        }
        return dbParams.getTable();
    }

    public Object getId(Object object) throws Exception {
        return this.get(getIdKey(), object);
    }

    /**
     * sets a Flag for using hibernate annotations when bean is created.
     */
    public boolean isHibernateAnnotations() {
        if (dbParams == null) {
            return false;
        }
        return dbParams.isHibernateAnnotations();
    }

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

    public boolean isContainer() {
        return isMap() || isObject() || isList();
    }


    protected static final ModelConfig add(EOConfigsCache configsCache, String key) throws Exception {
        LOG.info("Started find class " + key);
        final Map modelInfo = new HashMap();
        Class modelClass;
        try {
            modelClass = Class.forName(key);
        }
        catch(Exception e) {
            throw new Exception(e.getMessage());
        }
        final String modelKey = key.replaceAll(".*\\.", "");
        modelInfo.put(F_MODEL_KEY, modelKey);
        modelInfo.put(F_NATURAL_ID, modelKey);
        final Package inPackage = modelClass.getPackage();
        modelInfo.put(F_PACKAGE_PATH, inPackage.getName());

        final Field[] fields = modelClass.getDeclaredFields();
        final List<String> fieldKeys = new ArrayList();

        final Method[] methods = modelClass.getMethods();
        EOConfigsField fieldConfigs = (EOConfigsField) configsCache.getConfig(FieldConfig.class);
        for (Field field: fields) {
            LOG.info(field.getName());
            final String fieldName = field.getName();
            Method setMethod = ModelConfigObject.findSetter(field);
            Method getMethod = ModelConfigObject.findGetter(field);
            if (getMethod == null && setMethod == null) {
                continue;
            }
            fieldKeys.add(modelKey + "." + fieldName);
            try {
                FieldConfig.add(configsCache, field);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        final Map <String,Object> eoParams = new HashMap<>();
        modelInfo.put("eoParams", eoParams);
        if (fieldKeys.isEmpty()) {
            eoParams.put(F_SHAPE_TYPE, ShapeTypes.SCALAR);
        }
        else {
            eoParams.put(F_SHAPE_TYPE, ShapeTypes.BEAN);
        }
        modelInfo.put(F_FIELD_KEYS, fieldKeys);
        ModelConfig config = (ModelConfig) new Builder().build(configsCache, modelInfo);
        configsCache.getConfig(ModelConfig.class).add(config);
        return config;
    }

    public static class Builder extends ConfigImpl.Builder {
        //<call keep="JAVA" templateKey="BeanInstanceVars.tpl" }
        private String modelKey;
        private DBParams dbParams;
        private EOParams eoParams;
        private ViewParams viewParams;
        private Map customParams;
        //<call keep="JAVA" templateKey="CacheInstanceVars.tpl" }
        private List<String> fieldKeys;
        private String packagePath;
        private String packageGroup;
        private String author;
        private String superKey;
        private String interfaces;

        public Builder() {
            super();
        }
//</call>

        protected void prepare(EOConfigsCache configsCache, Map<String, Object> values) throws Exception {
            modelKey = ScalarConverter.toString(values.get(F_MODEL_KEY));
            packageGroup = ScalarConverter.toString(values.get(F_PACKAGE_GROUP));
            packagePath = ScalarConverter.toString(values.get(F_PACKAGE_PATH));
            author = ScalarConverter.toString(values.get(F_AUTHOR));
            superKey = ScalarConverter.toString(values.get(F_SUPER_KEY));
            interfaces = ScalarConverter.toString(values.get(F_INTERFACES));

            Object fieldKeysAsObject = values.get(F_FIELD_KEYS);


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
            dbParams = new DBParams(values.get(F_DB_PARAMS));
            eoParams = new EOParams(values.get(F_EO_PARAMS));
            viewParams = new ViewParams(values.get(F_VIEW_PARAMS));
            customParams = (Map) values.get(F_CUSTOM_PARAMS);

            super.prepare(configsCache, values);
        }


        public Config build(EOConfigsCache configsCache, Map values) throws Exception {
            prepare(configsCache, values);
            ShapeTypes shapeType = eoParams.getShapeType();
            if (shapeType == ShapeTypes.MAP) {
                return new ModelConfigMap(configsCache, this);
            } else if (shapeType == ShapeTypes.NONE) {
                return new ModelConfigNone(configsCache, this);
            } else if (shapeType == ShapeTypes.LIST) {
                return new ModelConfigList(configsCache, this);
            } else if (shapeType == ShapeTypes.SET) {
                return new ModelConfigSet(configsCache, this);
            } else if (shapeType == ShapeTypes.SCALAR) {
                return new ModelConfigScalar(configsCache, this);
            } else {
                return new ModelConfigObject(configsCache, this);
            }
        }

    }

}
