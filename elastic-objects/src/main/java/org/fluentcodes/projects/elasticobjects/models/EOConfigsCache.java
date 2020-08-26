package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.json.JsonConfig;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateConfig;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Storage for configurations
 *
 * @author Werner Diwischek
 * @since 13.10.2016.
 */
public class EOConfigsCache {
    private static final Logger LOG = LogManager.getLogger(EOConfigsCache.class);
    private final Map<Class, EOConfigMap> eoConfigsMap;
    private final boolean localSerialized;
    private final Scope scope;
    private String serialized;
    private Pattern modelPattern;

    public EOConfigsCache() {
        this(Scope.ALL, false);
    }

    public EOConfigsCache(final Scope scope) {
        this(scope, false);
    }

    public EOConfigsCache(final Scope scope, final boolean localSerialized) {
        eoConfigsMap = new LinkedHashMap<>();
        this.scope = scope;
        this.localSerialized = localSerialized;
        this.modelPattern = null;
        eoConfigsMap.put(ModelConfig.class, new EOConfigMapModels(this));
        eoConfigsMap.put(FieldConfig.class, new EOConfigMapFields(this));
        if (scope != Scope.DEV) {
            eoConfigsMap.get(ModelConfig.class).addJsonConfigs();
            eoConfigsMap.get(FieldConfig.class).addJsonConfigs();
            ((EOConfigMapModels)eoConfigsMap.get(ModelConfig.class)).addJsonClassNames();
            for (String key: eoConfigsMap.get(ModelConfig.class).getKeys()) {
                eoConfigsMap.get(ModelConfig.class).find(key).resolve();
            }
        }
    }

    protected Scope getScope() {
        return scope;
    }

    public Set<Class> getKeys() {
        return eoConfigsMap.keySet();
    }

    public Set<String> getKeys(final Class<? extends Config> configClass) {
        return getConfigMap(configClass).getKeys();
    }

    public List<String> getConfigClassesAsStringList() {
        return eoConfigsMap.keySet().stream().map(x -> x.getSimpleName()).collect(Collectors.toCollection(ArrayList::new));
    }

    public Set<String> getConfigNames(final String configName)  {
        return getConfigMap(configName).getKeys();
    }

    public EOConfigMap getConfigMap(final String configName)  {
        if (configName == null) {
            throw new EoException("Null search name for configMap entry");
        }
        for (Class configClass: getKeys()) {
            if (configName.equals(configClass.getSimpleName())) {
                return eoConfigsMap.get(configClass);
            }
        }

        throw new EoException("Could not find search name '" + configName + "'for configMap entry");
    }

    public Object find(final Class configClass, final String configKey)  {
        if (configClass == null) {
            throw new EoException("Cacheclass is null for finder!");
        }
        if (configKey == null || configKey.isEmpty()) {
            throw new EoException("assetKey is empty for finder '" + configClass.getSimpleName() + "'!");
        }
        Object config = getConfigMap(configClass).find(configKey);
        if (config == null) {
            throw new EoException("Could not resolve config for configClass " + configClass.getSimpleName() + " for configKey=" + configKey + ".");
        }
        return config;
    }


    public Object find(final String packagePath, final String cacheClassName, final String assetKey)  {
        Class cacheClass = null;
        try {
            cacheClass = Class.forName(packagePath + "." + cacheClassName);
        } catch (ClassNotFoundException e) {
            throw new EoException(e);
        }
        return find(cacheClass, assetKey);
    }

    public boolean hasConfigKey(final Class configClass, final String key) {
        try {
            return getConfigMap(configClass).hasKey(key);
        }
        catch (EoException e) {
            return false;
        }
    }

    public Set<String> getConfigKeys(Class configClass) {
        return getConfigMap(configClass).getKeys();
    }

    private EOConfigMap getConfigMap(Class configClass) {
        if (eoConfigsMap.get(configClass) == null) {
            eoConfigsMap.put(configClass, new EOConfigMapImmutable(this, configClass));
        }
        EOConfigMap configs = eoConfigsMap.get(configClass);
        if (configs == null) {
            throw new EoException("No provider defined for " + configClass.getSimpleName());
        }
        return configs;
    }

    public void add(Class configClass, Config config) {
        getConfigMap(configClass).addConfig(config);
    }

    public FieldConfig findField(final String fieldKey)  {
        return (FieldConfig) find(FieldConfig.class, fieldKey);
    }

    public ModelConfig findModel(final String modelKey)  {
        return (ModelConfig) find(ModelConfig.class, modelKey);
    }

    public ModelConfig findModel(final Class modelClass)  {
        return findModel(modelClass.getSimpleName());
    }

    public TemplateConfig findTemplate(final String key)  {
        return (TemplateConfig) find(TemplateConfig.class, key);
    }

    public FileConfig findFile(final String key)  {
        return (FileConfig) find(FileConfig.class, key);
    }

    public JsonConfig findJson(final String key)  {
        return (JsonConfig) find(JsonConfig.class, key);
    }

    public ModelInterface findModel(final Object modelValue)  {
        if (modelValue == null) {
            throw new EoException("null model value");
        }
        return findModel(modelValue.getClass());
    }

    public final Object transform(final String fieldKey, Map attributes)  {
        if (fieldKey == null) {
            return null;
        }
        if (attributes == null) {
            return null;
        }
        return transform(fieldKey, attributes.get(fieldKey));
    }

    public final Object transform(final String fieldKey, Map attributes, Object defaultValue)  {
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

    public final Object transform(final String fieldKey, final Object source)  {
        return transform(fieldKey, source, null);
    }

    public final Object transform(final String fieldKey, Object source, Object defaultValue)  {
        FieldConfig fieldConfig = null;
        try {
            fieldConfig = findField(fieldKey);
        }
        catch (EoException e) {
           LOG.error(e.getMessage());
           return null;
        }
        if (fieldConfig == null || fieldKey.isEmpty()) {
            throw new EoException("No fieldKey provided for " + fieldKey + " and source=" + source);
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
        EO eo = new EoRoot(this, source);
        return eo.get();
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
            builder.append(eoConfigsMap.get(entry).toStringx());
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
