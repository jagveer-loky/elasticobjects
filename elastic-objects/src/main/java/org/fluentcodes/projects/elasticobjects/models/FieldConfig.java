package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.PathPattern;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_NAME;

/**
 * Created by Werner on 09.10.2016.
 */
public class FieldConfig extends ConfigImpl {
    public static final String DB_FIELD_PARAMS = "dbFieldParams";
    public static final String VIEW_FIELD_PARAMS = "viewFieldParams";
    public static final String EO_FIELD_PARAMS = "eoFieldParams";
    public static final String CUSTOM_FIELD_PARAMS = "customFieldParams";
    public static final String FIELD_KEY = "fieldKey";
    public static final String MODEL_KEYS = "modelKeys";
    public static final String TO_SERIALIZE = "toSerialize";
    private final Boolean toSerialize;
    private final String fieldKey;
    private final DBFieldParams dbFieldParams;
    private final EOFieldParams eoFieldParams;
    private final ViewFieldParams viewFieldParams;
    private final Map customFieldParams;
    private final String modelKeys;
    private List<String> modelList;
    private Models models;

    public FieldConfig(EOConfigsCache configsCache, Map map) {
        super(configsCache, map);
        try {

            this.toSerialize = map.containsKey(TO_SERIALIZE) ? ScalarConverter.toBoolean(TO_SERIALIZE) : false;
            this.fieldKey = (String) map.get(FIELD_KEY);
            this.dbFieldParams = new DBFieldParams(map.get(DB_FIELD_PARAMS));
            this.eoFieldParams = new EOFieldParams(configsCache, map.get(EO_FIELD_PARAMS));
            this.viewFieldParams = new ViewFieldParams(map.get(VIEW_FIELD_PARAMS));
            this.customFieldParams = (Map) map.get(CUSTOM_FIELD_PARAMS);
            this.modelKeys = (String) map.get(MODEL_KEYS);
            this.modelList = new ArrayList<>();
            super.setExpose(Expose.NONE);
        }
        catch (Exception e) {
            throw new EoInternalException("Problem setting field with " + map.get(NATURAL_ID));
        }
    }

    protected void addModel(final ModelConfig modelConfig) {
        if (modelConfig == null || modelConfig.getNaturalId() == null)  {
            throw new EoInternalException("Problem with modelConfig where naturalId could not be resolved");
        }
        if (modelList.contains(modelConfig.getNaturalId())) {
            return;
        }
        modelList.add(modelConfig.getNaturalId());
        if (getExpose().ordinal() >= modelConfig.getExpose().ordinal()) {
            super.setExpose(modelConfig.getExpose());
        }
    }

    public List<String> getModelList() {
        return new ArrayList<>(modelList);
    }
    public boolean hasModelList() {
        return !modelList.isEmpty();
    }

    protected final static void addByClassField(EOConfigsCache configsCache, Field field)  {
        Class modelClass = field.getDeclaringClass();
        Class typeClass = field.getType();
        Map map = new HashMap();

        Map dbFieldParams = new HashMap();
        map.put(DB_FIELD_PARAMS, dbFieldParams);
        Map eoFieldParams = new HashMap();
        map.put(EO_FIELD_PARAMS, eoFieldParams);
        Map viewFieldParams = new HashMap();
        map.put(VIEW_FIELD_PARAMS, viewFieldParams);
        Map customFieldParams = new HashMap();
        map.put(CUSTOM_FIELD_PARAMS, customFieldParams);

        map.put(FIELD_KEY, field.getName());
        map.put(NATURAL_ID, modelClass.getSimpleName() + "." + field.getName());
        map.put(F_NAME, field.getName());
        map.put(MODEL_KEYS, typeClass.getSimpleName());
        FieldConfig config = new FieldConfig(configsCache, map);
        configsCache.add(FieldConfig.class, config);
        if (!configsCache.hasConfigKey(ModelConfig.class, typeClass.getSimpleName())) {
            ModelConfig.addByClassName(configsCache, typeClass.getName());
        }
    }

    @Override
    public void resolve()  {
        super.resolve();
        this.models = new Models(getConfigsCache(), modelKeys.split(","));
    }

    @Override
    public String getKey() {
        return fieldKey;
    }



    /**
     * Fielddefinitions depending on MetaModels
     */
    public String getFieldKey() {
        return this.fieldKey;
    }

    //<call keep="JAVA" templateKey="CacheGetter.tpl" }

    public DBFieldParams getDbFieldParams() {
        return dbFieldParams;
    }

    public EOFieldParams getEoFieldParams() {
        return eoFieldParams;
    }

    public ViewFieldParams getViewFieldParams() {
        return viewFieldParams;
    }

    public Map getCustomFieldParams() {
        return customFieldParams;
    }

    public Models getModels() {
        resolve();
        return models;
    }

    public String getModelKeys() {
        return modelKeys;
    }

    /**
     * A path pattern for {@link FieldConfig}.
     */
    public PathPattern getPathPattern()  {
        return eoFieldParams.getPathPattern();
    }

    public boolean hasPathPattern()  {
        return eoFieldParams.hasPathPattern();
    }

    public boolean isFilterNothing()  {
        return eoFieldParams.isFilterNothing();
    }


    /**
     * Fielddefinitions depending on MetaModels
     */
    public Boolean isNotNull() {
        if (dbFieldParams.isNotNull() == null) {
            return false;
        }
        return dbFieldParams.isNotNull();
    }

    public Boolean getNotNull() {
        return dbFieldParams.isNotNull();
    }

    /**
     * Fielddefinitions depending on MetaModels
     */
    public Boolean isUnique() {
        if (dbFieldParams.isUnique() == null) {
            return false;
        }
        return dbFieldParams.isUnique();
    }

    public Boolean getUnique() {
        return dbFieldParams.isUnique();
    }

    /**
     * Fielddefinitions depending on MetaModels
     */
    public String getHibernate() {
        return dbFieldParams.getHibernate();
    }

    /**
     * The name of the persistence entity in {@link ModelInterface} or the join table in {@link FieldConfig}.
     */
    public String getTable() {
        return dbFieldParams.getTable();
    }

    /**
     * A reference key to a id field for joining.
     */
    public String getJoin() {
        return dbFieldParams.getJoin();
    }

    /**
     * A self reference key to a id field on a glue n-m table.
     */
    public String getJoinInverse() {
        return dbFieldParams.getJoinInverse();
    }

    public String getMapKey() {
        return dbFieldParams.getMapKey();
    }

    /**
     * A length instance var.
     */
    public Integer getLength() {
        return dbFieldParams.getLength();
    }

    public Class getModelClass()  {
        return getModelConfig().getModelClass();
    }

    public Class getChildClass()  {
        return getChildModel().getModelClass();
    }

    public String getModel()  {
        return getModels().getModel().getModelKey();
    }

    public ModelInterface getModelConfig()  {
        return getModels().getModel();
    }

    public ModelInterface getChildModel()  {
        return getModels().getChildModel();
    }
}
