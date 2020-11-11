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

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/
import org.fluentcodes.projects.elasticobjects.models.Expose;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
/**
 * Creates an open api schema from model configuration.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Wed Nov 11 05:43:36 CET 2020
 */
public class ConfigOpenApiCall extends CallImpl implements SimpleCommand {
/*=>{}.*/

    /*==>{ALLStaticNames.tpl, fieldMap/*, override eq false, JAVA|>}|*/
   public static final String CONFIG_FILTER = "configFilter";
   public static final String EXPOSE = "expose";
   public static final String FILTER_MODULE = "filterModule";
   public static final String FILTER_SUB_MODULE = "filterSubModule";
/*=>{}.*/

    /*==>{ALLInstanceVars.tpl, fieldMap/*, , JAVA|>}|*/
   private  String configFilter;
   private  Expose expose;
   private  String filterModule;
   private  String filterSubModule;
/*=>{}.*/
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

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public Object execute(final EO eo)  {
        if (!hasExpose()) {
            expose = Expose.NONE;
        }
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

    /*==>{ALLSetter.tpl, fieldMap/*, , JAVA|>}|*/
    /**
    Key for filter configuration
    */

    public ConfigOpenApiCall setConfigFilter(String configFilter) {
        this.configFilter = configFilter;
        return this;
    }
    
    public String getConfigFilter () {
       return this.configFilter;
    }
    
    public boolean hasConfigFilter () {
        return configFilter!= null && !configFilter.isEmpty();
    }
    /**
    expose
    */

    public ConfigOpenApiCall setExpose(Expose expose) {
        this.expose = expose;
        return this;
    }
    
    public Expose getExpose () {
       return this.expose;
    }
    
    public boolean hasExpose () {
        return expose!= null;
    }
    /**
    Filter for modules in  {{@link link} ConfigAction}
    */

    public ConfigOpenApiCall setFilterModule(String filterModule) {
        this.filterModule = filterModule;
        return this;
    }
    
    public String getFilterModule () {
       return this.filterModule;
    }
    
    public boolean hasFilterModule () {
        return filterModule!= null && !filterModule.isEmpty();
    }
    /**
    Filter for subModules in  {{@link link} ConfigAction}
    */

    public ConfigOpenApiCall setFilterSubModule(String filterSubModule) {
        this.filterSubModule = filterSubModule;
        return this;
    }
    
    public String getFilterSubModule () {
       return this.filterSubModule;
    }
    
    public boolean hasFilterSubModule () {
        return filterSubModule!= null && !filterSubModule.isEmpty();
    }
/*=>{}.*/

}
