package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.eo.EOBuilder;
import org.fluentcodes.projects.elasticobjects.eo.Models;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Storage for configurations
 *
 * @author Werner Diwischek
 * @since 13.10.2016.
 */
public class EOConfigsCache {
    private static final Logger LOG = LogManager.getLogger(EOConfigsCache.class);
    private final Map<Class, EOConfigs> eoConfigsMap;
    private final boolean localSerialized;
    private final Scope scope;
    private String serialized;
    private Pattern modelPattern;

    public EOConfigsCache() {
        this(Scope.ALL, false);
    }

    public EOConfigsCache(Scope scope) {
        this(scope, false);
    }

    public EOConfigsCache(Scope scope, boolean localSerialized) {
        eoConfigsMap = new LinkedHashMap<>();
        this.scope = scope;
        this.localSerialized = localSerialized;
        this.modelPattern = null;
        try {
            ConfigsModel models = new ConfigsModel(this, scope);
            eoConfigsMap.put(ModelConfig.class, models);

            EOConfigsField fields = new EOConfigsField(this, scope);
            eoConfigsMap.put(FieldConfig.class, fields);
            models.init();
            if (scope != Scope.DEV) {
                models.addConfigs();
                models.initCallMap();
                fields.addConfigs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Scope getScope() {
        return scope;
    }

    public Set<Class> getKeys() {
        return eoConfigsMap.keySet();
    }

    public Map<Class, EOConfigs> getEoConfigsMap() {
        return eoConfigsMap;
    }

    public Map getProviderMap(final Class cacheClass) throws Exception {
        return getConfig(cacheClass).getConfigMap();
    }

    public Object find(final Class cacheClass, final String assetKey) throws Exception {
        if (cacheClass == null) {
            throw new Exception("Cacheclass is null for finder!");
        }
        if (assetKey == null || assetKey.isEmpty()) {
            throw new Exception("assetKey is empty for finder '" + cacheClass.getSimpleName() + "'!");
        }
        Object asset = getConfig(cacheClass).find(assetKey);
        if (asset == null) {
            throw new Exception("Could not resolve asset " + cacheClass + " for  key=" + assetKey + ".");
        }
        return asset;
    }


    public Object find(final String packagePath, final String cacheClassName, final String assetKey) throws Exception {
        Class cacheClass = Class.forName(packagePath + "." + cacheClassName);
        return find(cacheClass, assetKey);
    }

    public EOConfigs getConfig(Class configClass) throws Exception {

        if (eoConfigsMap.get(configClass) == null) {
            eoConfigsMap.put(configClass, new ConfigsImmutable(this, configClass, scope));
        }
        EOConfigs configs = eoConfigsMap.get(configClass);
        if (configs == null) {
            throw new Exception("No provider defined for " + configClass.getSimpleName());
        }
        return configs;
    }

    public Set<String> getCallSet() throws Exception {
        return ((ConfigsModel) getConfig(ModelConfig.class)).getCallSet();
    }

    public Set<String> getKeys(final Class<?> cacheClass) throws Exception {
        return getConfig(cacheClass).getKeys();
    }

    public FieldConfig findField(final String fieldKey) throws Exception {
        return (FieldConfig) find(FieldConfig.class, fieldKey);
    }

    public ModelConfig findModel(final String modelKey) throws Exception {
        return (ModelConfig) find(ModelConfig.class, modelKey);
    }

    public ModelConfig findModel(final Class modelClass) throws Exception {
        return findModel(modelClass.getSimpleName());
    }

    public TemplateConfig findTemplate(final String key) throws Exception {
        return (TemplateConfig) find(TemplateConfig.class, key);
    }

    public FileConfig findFile(final String key) throws Exception {
        return (FileConfig) find(FileConfig.class, key);
    }

    public JsonConfig findJson(final String key) throws Exception {
        return (JsonConfig) find(JsonConfig.class, key);
    }

    public ValueConfig findValue(final String key) throws Exception {
        return (ValueConfig) find(ValueConfig.class, key);
    }

    public ModelInterface findModel(final Object modelValue) throws Exception {
        if (modelValue == null) {
            throw new Exception("null model value");
        }
        return findModel(modelValue.getClass());
    }

    public final Object transform(final String fieldKey, Map attributes) throws Exception {
        if (fieldKey == null) {
            return null;
        }
        if (attributes == null) {
            return null;
        }
        return transform(fieldKey, attributes.get(fieldKey));
    }

    public final Object transform(final String fieldKey, Map attributes, Object defaultValue) throws Exception {
        if (fieldKey == null) {
            return null;
        }
        if (attributes == null) {
            return null;
        }
        if (attributes.get(fieldKey) != null) {
            return transform(fieldKey, attributes.get(fieldKey));
        } else {
            return transform(fieldKey, defaultValue);
        }
    }

    public final Object transform(final String fieldKey, final Object source) throws Exception {
        return transform(fieldKey, source, null);
    }

    public final Object transform(final String fieldKey, Object source, Object defaultValue) throws Exception {
        FieldConfig fieldConfig = findField(fieldKey);
        if (fieldConfig == null || fieldKey.isEmpty()) {
            throw new Exception("No fieldKey provided for " + fieldKey + " and source=" + source);
        }
        DBFieldParams dbFieldParams = fieldConfig.getDbFieldParams();
        if (dbFieldParams != null) {
            Object configDefaultValue = dbFieldParams.getDefaultValue();
            if (configDefaultValue != null && defaultValue == null) {
                defaultValue = configDefaultValue;
            }
        }
        if (source == null && defaultValue == null) {
            return null;
        }
        if (source == null) {
            source = defaultValue;
        }
        Models models = fieldConfig.getModels();
        if (models.isScalar()) {
            String scalarClassName = models.getModelClass().getSimpleName();
            switch (scalarClassName) {
                case "String":
                    return ScalarConverter.toString(source);
                case "Integer":
                    return ScalarConverter.toInt(source);
                case "Boolean":
                    return ScalarConverter.toBoolean(source);
                case "Date":
                    return ScalarConverter.toDate(source);
                case "Double":
                    return ScalarConverter.toDouble(source);
                case "Float":
                    return ScalarConverter.toFloat(source);
            }
        }
        if (models.isEnum()) {
            String enumValue = ScalarConverter.toString(source);
            return Enum.valueOf(models.getModelClass(), enumValue);
            //https://stackoverflow.com/questions/26357132/generic-enum-valueof-method-enum-class-in-parameter-and-return-enum-item/26357317
        }
        // complex types use MODULE_NAME...
        return new EOBuilder(this)
                .setModels(models)
                .map(source)
                .get();

    }

    public String serialize() {
        if (localSerialized && serialized != null) {
            return serialized;
        }
        StringBuilder builder = new StringBuilder("{\n");
        int counter = 0;
        for (Class entry : eoConfigsMap.keySet()) {
            builder.append("  \"");
            builder.append(entry.getSimpleName());
            builder.append("\":{\n");
            builder.append(eoConfigsMap.get(entry).toString());
            counter++;
            builder.append("  }");
            if (counter < eoConfigsMap.keySet().size()) {
                builder.append(",");
            }
            builder.append("\n");

        }
        builder.append("}");
        if (localSerialized) {
            this.serialized = builder.toString();
        }
        return builder.toString();
    }


}
