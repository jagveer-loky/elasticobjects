package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Call set parts of the config cache to the adapter.
 * Created by werner.diwischek on 10.6.2018
 */
public class ConfigOpenApiCall extends CallImpl {
    private String filterModule;
    private String filterSubModule;
    private String configFilter = ".*";
    private Expose expose = Expose.WEB;
    private SortOrder sortOrder = SortOrder.ASC;

    private Set <String> created;
    private Set <String> toCreate;

    public ConfigOpenApiCall() {
        super();
    }


    public ConfigOpenApiCall(final String configFilter) {
        super();
        this.configFilter = configFilter;
    }

    public void setByParameter(final String values) {
        if (values == null||values.isEmpty()) {
            throw new EoException("Set by empty input values");
        }
        String[] array = values.split(", ");
        if (array.length>4) {
            throw new EoException("Short form should have form '<configType>[,<naturalId>][,<expose>][,<targetPath>]' with max length 3 but has size " + array.length + ": '" + values + "'." );
        }
        setByString(array);
    }

    protected void setByString(final String[] array) {
        if (array == null||array.length == 0) {
            throw new EoException("Set by empty input values");
        }
        if (array.length>0) {
            setConfigFilter(array[0]);
        }
        if (array.length>1) {
            setTargetPath(array[1]);
        }
        if (array.length>2) {
            setExpose(Expose.valueOf(array[2]));
        }

    }

    public boolean hasFilterModule() {
        return filterModule != null && !filterModule.isEmpty();
    }

    public String getFilterModule() {
        return filterModule;
    }

    public ConfigOpenApiCall setFilterModule(final String entry) {
        this.filterModule = entry;
        return this;
    }

    public boolean hasFilterSubModule() {
        return filterSubModule != null && !filterSubModule.isEmpty();
    }

    public String getFilterSubModule() {
        return filterSubModule;
    }

    public ConfigOpenApiCall setFilterSubModule(final String entry) {
        this.filterSubModule = entry;
        return this;
    }

    public String getConfigFilter() {
        return configFilter;
    }

    public void setConfigFilter(String configFilter) {
        this.configFilter = configFilter;
    }

    public Expose getExpose() {
        return expose;
    }

    public void setExpose(Expose expose) {
        this.expose = expose;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public Object execute(final EO eo)  {
        created = new HashSet<>();
        toCreate = new HashSet<>();
        ConfigKeysCall keysCall = new ConfigKeysCall(ModelConfig.class, configFilter)
                .setExpose(expose)
                .setSortOrder(sortOrder);
        List<String> keys = (List<String>)keysCall.execute(eo);
        EO schemeRoot = new EoRoot(eo.getConfigsCache());
        EO components = schemeRoot.setEmpty("components");
        EO schemas = components.setEmpty("schemas");

        for (String key : keys) {
            if (created.contains(key)) {
                continue;
            }
            ModelConfig configEntry = eo.getConfigsCache().findModel(key);
            try {
                if (hasFilterModule() && (configEntry.getModule() == null || !configEntry.getModule().equals(this.getFilterModule()))) {
                    continue;
                }
                if (hasFilterSubModule() && (configEntry.getConfigsCache() == null || !configEntry.getConfigsCache().equals(this.getFilterSubModule()))) {
                    continue;
                }
            } catch (Exception e) {
                throw new EoException(e);
            }
            configEntry.resolve();
            create(schemas, configEntry);
        }
        return super.createReturnType(eo, schemeRoot.get());
    }

    private void create(EO schemasEo, ModelConfig config) {
        EO entry = schemasEo.setEmpty(config.getModelKey());
        created.add(config.getModelKey());
        entry.set("object","type");
        entry.set(config.getDescription(), "description");
        EO properties = entry.setEmpty("properties");
        for (String fieldKey: config.getFieldKeys()) {
            FieldConfig fieldConfig = schemasEo.getConfigsCache().findField(fieldKey);
            EO field = properties.setEmpty(fieldConfig.getFieldKey());
            Models fieldModels = fieldConfig.getModels();
            if (fieldConfig.hasDescription()) {
                field.set(fieldConfig.getDescription(), "description");
            }
            if (fieldModels.isEnum()) {
                field.set("string", "type" );
                Class enumClass = fieldModels.getModelClass();
                List values = Arrays.asList(enumClass.getEnumConstants());
                field.set(values, "(List)enum");

            }
            else if (fieldModels.isScalar()) {
                field.set(JsonTypes.getType(fieldModels.getModelClass()), "type" );
                if (JsonTypes.hasFormat(fieldModels.getModelClass())) {
                    field.set(JsonTypes.getFormat(fieldModels.getModelClass()), "format");
                }
            }
            else if (fieldModels.isObject()) {
                field.set("#/components/schemas/" + fieldModels.getModelClass().getSimpleName(), "$ref");
                if (!created.contains(fieldModels.getModelClass().getSimpleName())) {
                    toCreate.add(fieldModels.getModelClass().getSimpleName());
                }
            }
            else if (fieldModels.isList()) {
                EO array = field.set("array", "$ref");
                if (fieldModels.hasChildModel()) {
                    ModelConfigInterface childModel = fieldModels.getChildModel();
                    array.setEmpty("items");
                    if (childModel.isScalar()) {
                        array.set(JsonTypes.getType(childModel.getModelClass()), "type");
                        /*if (JsonTypes.hasFormat(childModel.getModelClass())) {
                            array.set(JsonTypes.getType(childModel.getModelClass()), "format");
                        }*/
                    }
                    else if (childModel.isObject()) {
                        array.set("#/components/schemas/" + childModel.getModelClass().getSimpleName(), "$ref");
                        if (!created.contains(childModel.getModelClass().getSimpleName())) {
                            toCreate.add(childModel.getModelClass().getSimpleName());
                        }
                    }
                    else {
                        throw new EoException("Problem");
                    }
                }
            }
            else {
                field.set(fieldModels.toString(), "type");
            }
        }
        created.add(config.getModelKey());
        toCreate.remove(config.getModelKey());
        if (toCreate.isEmpty()) {
            return;
        }
        for (String modelKey: toCreate) {
            ModelConfig createConfig = schemasEo.getConfigsCache().findModel(modelKey);
            create(schemasEo, createConfig);
        }
    }

}
