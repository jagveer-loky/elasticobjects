package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.PathPattern;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

/**
 * Created by Werner on 09.10.2016.
 */
public abstract class ModelConfig extends ConfigImpl implements ModelInterface {
    public static final String MODEL_KEY = "modelKey";
    public static final String FIELD_KEYS = "fieldKeys";
    public static final String VIEW_PARAMS = "viewParams";
    public static final String EO_PARAMS = "eoParams";
    public static final String CUSTOM_FIELD_PARAMS = "customFieldParams";


    public static final String F_PATH = "path";
    public static final String F_PACKAGE_GROUP = "packageGroup";
    public static final String MODULE = "module";
    public static final String SUB_MODULE = "subModule";
    public static final String PACKAGE_PATH = "packagePath";

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

    public ModelConfig(EOConfigsCache configsCache, Builder builder) {
        super(configsCache, builder);

        //<call keep="JAVA" templateKey="CacheSetter.tpl" }

        this.modelKey = builder.modelKey;
        this.eoParams = builder.eoParams;
        this.dbParams = builder.dbParams;
        this.viewParams = builder.viewParams;
        this.customParams = builder.customParams;

        this.fieldKeys = builder.fieldKeys;
        this.packagePath = builder.packagePath;
        this.packageGroup = builder.packageGroup;
        this.author = builder.author;
        this.superKey = builder.superKey;
        this.interfaces = builder.interfaces;


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
        final Package inPackage = modelClass.getPackage();
        modelMap.put(PACKAGE_PATH, inPackage.getName());

        final Map<String, Object> eoParams = new HashMap<>();
        eoParams.put(F_CREATE, true);
        modelMap.put("eoParams", eoParams);

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
            eoParams.put(F_SHAPE_TYPE, ShapeTypes.SCALAR);
        } else {
            eoParams.put(F_SHAPE_TYPE, ShapeTypes.BEAN);
        }
        modelMap.put(FIELD_KEYS, fieldKeys);
        ModelConfig config = (ModelConfig) new Builder().build(configsCache, modelMap);
        configsCache.add(ModelConfig.class, config);
        for (String fieldKey: fieldMap.keySet()) {
            if (!configsCache.hasConfigKey(FieldConfig.class, fieldKey)) {
                FieldConfig.addByClassField(configsCache, fieldMap.get(fieldKey));
            }
        }
        return config;
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
    public EOParams getEoParams() {
        return eoParams;
    }

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

    private void setModelCacheClass() {
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

    public Object getId(Object object) {
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


        protected void prepare(EOConfigsCache configsCache, Map<String, Object> values) {
            modelKey = ScalarConverter.toString(values.get(MODEL_KEY));
            packageGroup = ScalarConverter.toString(values.get(F_PACKAGE_GROUP));
            packagePath = ScalarConverter.toString(values.get(PACKAGE_PATH));
            author = ScalarConverter.toString(values.get(AUTHOR));
            superKey = ScalarConverter.toString(values.get(F_SUPER_KEY));
            interfaces = ScalarConverter.toString(values.get(F_INTERFACES));

            Object fieldKeysAsObject = values.get(FIELD_KEYS);


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
            try {
                dbParams = new DBParams(values.get(F_DB_PARAMS));
                eoParams = new EOParams(values.get(EO_PARAMS));
                viewParams = new ViewParams(values.get(VIEW_PARAMS));
                customParams = (Map) values.get(F_CUSTOM_PARAMS);
            }
            catch(Exception e) {
                e.printStackTrace();
                throw new EoException(e);
            }
            super.prepare(configsCache, values);
        }


        public Config build(EOConfigsCache configsCache, Map values) {
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
            }
            else if (shapeType == ShapeTypes.ENUM) {
                return new ModelConfigScalar(configsCache, this);
            }else {
                return new ModelConfigObject(configsCache, this);
            }
        }
    }

}
