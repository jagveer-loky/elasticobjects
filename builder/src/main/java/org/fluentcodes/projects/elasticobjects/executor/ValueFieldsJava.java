package org.fluentcodes.projects.elasticobjects.calls.executor;

import org.fluentcodes.projects.elasticobjects.CreatorParams;
import org.fluentcodes.projects.elasticobjects.config.ShapeTypes;
import org.fluentcodes.projects.elasticobjects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;
import org.fluentcodes.projects.elasticobjects.utils.Util;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;


/**
 * Created by werner.diwischek on 20.05.18.
 */
public class ValueFieldsJava {

    protected final static EO getFieldDbParams(EO fields)  {
        return fields
                .getChild(F_DB_FIELD_PARAMS);
    }

    protected final static EO getFieldEoParams(EO fields)  {
        return fields
                .getChild(F_EO_FIELD_PARAMS);
    }

    protected final static EO getFieldDbParams(EO adapter, String fieldKey)  {
        return getFieldParams(adapter, fieldKey)
                .getChild(F_DB_FIELD_PARAMS);
    }

    protected final static EO getFieldParams(EO adapter, String fieldKey)  {
        return adapter.getChild(CreatorParams.FIELDS_MAP_PATH + Path.DELIMITER + fieldKey);
    }

    private final static String[] getModelKeys(EO fieldParams)  {
        String modelKeys = (String) fieldParams.get(F_MODEL_KEYS);
        if (modelKeys == null || modelKeys.isEmpty()) {
            return null;
        }
        return modelKeys.split(",");
    }

    private final static ShapeTypes getModelShapeType(final EO fieldParams)  {
        String[] modelKeys = getModelKeys(fieldParams);
        if (modelKeys == null || modelKeys.length == 0) {
            return ShapeTypes.NONE;
        }
        EO modelParams = ValueModelsJava.getModelParams(fieldParams, modelKeys[0]);
        if (modelParams == null || modelParams.isEmpty()) {
            return ShapeTypes.NONE;
        }
        EO modelEoParams = ValueModelsJava.getModelEoParams(modelParams);
        if (modelEoParams == null || modelEoParams.isEmpty()) {
            return ShapeTypes.NONE;
        }
        String shapeTypeAsString = ScalarConverter.toString(modelEoParams.get(F_SHAPE_TYPE));
        if (shapeTypeAsString == null || shapeTypeAsString.isEmpty()) {
            return ShapeTypes.NONE;
        }
        return ShapeTypes.valueOf(shapeTypeAsString);
    }

    public static String getModelKeys(Object... values)  {
        EO adapter = ValueParamsHelper.getAdapter(0, values);
        String fieldKey = ValueParamsHelper.getString(1, values);
        EO fieldParams = getFieldParams(adapter, fieldKey);
        if (fieldParams == null || fieldParams.isEmpty()) {
            throw new Exception("No definitions loaded for " + fieldKey);
        }
        String modelKeys = (String) fieldParams.get("modelKeys");
        if (modelKeys == null || modelKeys.isEmpty()) {
            throw new Exception("No models defined for " + fieldKey);
        }
        return modelKeys;
    }

    public static String getDefaultValue(Object... values)  {
        EO adapter = ValueParamsHelper.getAdapter(0, values);
        String fieldKey = ValueParamsHelper.getString(1, values);
        EO fieldParams = getFieldParams(adapter, fieldKey);
        if (fieldParams == null || fieldParams.isEmpty()) {
            throw new Exception("No definitions loaded for " + fieldKey);
        }
        String value = (String) fieldParams.get("dbFieldParams/defaultValue");
        if (value == null) {
            return "";
        }
        return value;
    }

    protected static String[] getModelKeys(final EO adapter, final String fieldKey)  {
        EO fieldParams = getFieldParams(adapter, fieldKey);
        if (fieldParams == null) {
            throw new Exception("Could not find field configurations for " + fieldKey);
        }
        return getModelKeys(fieldParams);
    }

    public static String createInstanceVar(final EO adapter, final String fieldKey)  {
        EO fieldParams = getFieldParams(adapter, fieldKey);
        if (fieldParams == null) {
            throw new Exception("Could not find field configurations for " + fieldKey);
        }
        StringBuilder result = new StringBuilder("    private ");
        result.append(createType(adapter, fieldKey));
        result.append(" ");
        result.append(fieldKey);
        result.append(";\n");
        return result.toString();
    }

    public static String createType(Object... values)  {
        EO adapter = ValueParamsHelper.getAdapter(0, values);
        String fieldKey = ValueParamsHelper.getString(1, values);
        EO fieldParams = getFieldParams(adapter, fieldKey);
        if (fieldParams == null) {
            throw new Exception("Could not find field configurations for " + fieldKey);
        }
        if (fieldParams.get("type") != null) {
            return (String) fieldParams.get("type");
        }
        String[] models = getModelKeys(fieldParams);
        if (models == null) {
            throw new Exception("Null model for " + fieldParams.get(F_FIELD_KEY));
        }
        StringBuilder result = new StringBuilder("");
        String baseClass = models[0];
        result.append(baseClass);
        if (models.length > 1) {
            result.append("<");
            if (baseClass.matches("Set|List|ArrayList|LinkedSet")) {
                result.append(models[1]);
            } else if (baseClass.matches("Map|HashMap|LinkedHashMap")) {
                result.append("String,");
                result.append(models[1]);
            }
            result.append("> ");
        }
        fieldParams.add("type").set(result.toString());
        return result.toString();
    }

    public static String createDescription(Object... values)  {
        EO adapter = ValueParamsHelper.getAdapter(0, values);
        String fieldKey = ValueParamsHelper.getString(1, values);
        EO fieldParams = getFieldParams(adapter, fieldKey);
        if (fieldParams == null) {
            throw new Exception("Could not find field configurations for " + fieldKey);
        }
        if (fieldParams.get("description") == null || ((String) fieldParams.get("description")).isEmpty()) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        result.append("\n    /**\n");
        result.append("    * ");
        result.append(fieldParams.get("description"));
        result.append("\n    */\n");
        return result.toString();
    }

    protected static String createAnnotation(EO adapter, String fieldKey)  {
        StringBuilder result = new StringBuilder();
        EO fieldParams = getFieldParams(adapter, fieldKey);
        if (fieldParams == null) {
            throw new Exception("Could not find field configurations for " + fieldKey);
        }
        String models = (String) fieldParams.get("modelKeys");

        /*String modelTable = "";
        if (modelTable == null ||  modelTable.isEmpty()) {
            return "";
        }*/

        EO fieldDbParams = getFieldDbParams(fieldParams);
        if (fieldDbParams == null || fieldDbParams.isEmpty()) {
            return "\n  @Transient()";
        }

        String fieldName = (String) fieldDbParams.get(F_FIELD_NAME);
        if (fieldName == null || fieldName.isEmpty()) {
            return "\n  @Transient()";
        }

        String modelIdKey = "id";
        if (fieldKey.equals(modelIdKey)) {
            return "\n  @id\n  @GeneratedValue(strategy=GenerationType.AUTO)";
        }
        String hibernate = (String) fieldDbParams.get("annotation");
        StringBuilder builder = new StringBuilder();
        if (hibernate == null || hibernate.isEmpty() || hibernate.contains("Column")) {
            builder.append("\n  @Column(");
            builder.append("name=\"");
            builder.append(fieldKey);
            builder.append("\"");
            builder.append(", ");
            builder.append(fieldAllAttributes(fieldParams));
            builder.append(")");
            return builder.toString();
        }
        if (hibernate.contains("OneToMany")) {
            return getOneToManyAnnotation(fieldParams);
        }
        if (hibernate.contains("ManyToOne")) {
            return getManyToOneAnnotation(fieldParams);
        }
        if (hibernate.contains("ManyToMany")) {
            return getManyToManyAnnotation(fieldParams);
        }
        return "\n  @Transient()";
    }

    protected static String fieldAllAttributes(EO fieldParams)  {
        StringBuilder builder = new StringBuilder();
        EO fieldDbParams = getFieldDbParams(fieldParams);
        builder.append(addAttribute(fieldDbParams, FIELD_KEY_MAP.column));
        builder.append(hasTypeOrClass(fieldParams));
        builder.append(addAttribute(fieldDbParams, FIELD_KEY_MAP.unique));
        builder.append(addAttribute(fieldDbParams, FIELD_KEY_MAP.notnull));
        builder.append(addAttribute(fieldDbParams, FIELD_KEY_MAP.length));
        return builder.toString().replaceAll(", $", "");
    }

    protected static String addAttribute(final EO fieldDbParams, FIELD_KEY_MAP key)  {
        String value = ScalarConverter.toString(fieldDbParams.get(key.annotation));
        if (value == null || value.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(key.name());
        builder.append("=\"");
        builder.append(value);
        builder.append("\", ");
        return builder.toString();
    }

    protected final static String hasTypeOrClass(final EO fieldParams)  {
        ShapeTypes shapeType = getModelShapeType(fieldParams);
        if (shapeType == ShapeTypes.SCALAR) {
            return ("type=\"string\", ");
        } else if (shapeType == ShapeTypes.MAP) {
        } else if (shapeType == ShapeTypes.LIST) {
        } else {
            return ("class=\"" + fieldParams.get(F_FIELD_KEY) + "\", ");
        }
        return "";
    }

    protected static String getOneToManyAnnotation(final EO dbParams)  {
        String join = ScalarConverter.toString(dbParams.get(F_JOIN));
        StringBuilder builder = new StringBuilder("\n  @OneToMany (");
        try {
            if (join == null || join.isEmpty()) {
                builder.append(")\n");
                return builder.toString();
            } else {
                builder.append("mappedBy=\"");
                builder.append(join);
                builder.append("\")\n");
            }
            builder.append(getMapKeyAnnotation(dbParams));
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "//" + e.getMessage();
        }
    }

    protected static String getManyToOneAnnotation(final EO dbParams)  {
        String join = ScalarConverter.toString(dbParams.get(F_JOIN));
        StringBuilder builder = new StringBuilder("\n  @ManyToOne ()\n");
        try {
            if (join != null && !join.isEmpty()) {
                builder.append("  @JoinColumn(name=\"");
                builder.append(join);
                builder.append("\")");
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "//" + e.getMessage();
        }
    }

    protected static String getManyToManyAnnotation(final EO dbParams)  {
        String table = ScalarConverter.toString(dbParams.get(F_TABLE));
        String join = ScalarConverter.toString(dbParams.get(F_JOIN));
        String joinInverse = ScalarConverter.toString(dbParams.get(F_JOIN_INVERSE));
        StringBuilder builder = new StringBuilder("\n  @ManyToMany ()\n");
        try {
            builder.append("  @JoinTable(name = \"");
            builder.append(table);
            builder.append("\",\n");
            builder.append("    joinColumns = @JoinColumn(name = \"");
            builder.append(join);
            builder.append("\", referencedColumnName = \"id\"),\n");
            builder.append("    inverseJoinColumns = @JoinColumn(name = \"");
            builder.append(joinInverse);
            builder.append("\", referencedColumnName = \"id\")\n");
            builder.append("  )\n");
            builder.append(getMapKeyAnnotation(dbParams));
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "//" + e.getMessage();
        }
    }

    protected static String getMapKeyAnnotation(final EO dbParams)  {
        String mapKey = ScalarConverter.toString(dbParams.get(F_MAP_KEY));
        if (mapKey == null || mapKey.isEmpty()) {
            return "";
        }
        return "   @MapKey(name = \"" + mapKey + "\")";
    }

    public static String getManyToManyInverseJoin(final EO dbParams)  {
        String modelTable = ScalarConverter.toString(dbParams.get(F_TABLE));
        if (modelTable == null) {
            throw new Exception("Null parent table for field ");
        }
        return "    inverseJoinColumns=@JoinColumn(name=\"" + Util.lowerFirstCharacter(modelTable) + "_id\", referencedColumnName=\"id\")\n";
    }

    public static String getManyToManyJoin(final EO dbParams)  {
        /*String [] modelKeys = getFieldModelKeys();
        if (modelKeys.length!=2) {
            throw new Exception("modelKeys has not target type " + getFieldKey());
        }
        String className = modelKeys[1];
        return "    joinColumns=@JoinColumn(name=\"" + Util.lowerFirstCharacter(className) + "_id\", referencedColumnName=\"id\"),\n";
        */
        return "";
    }

    protected static String getJoinTable(final EO dbParams)  {
        /*List<String> tables = new ArrayList<>();
        tables.add(getParent().getModelKey());

        String[] modelKeys = getModels().toString().split(",");

        if (modelKeys.length != 2) {
            throw new Exception ("Could not create reference table without type.");
        }
        tables.add(modelKeys[1]);
        StringBuilder builder = new StringBuilder("  @JoinTable(name=\"");
        Collections.sort(tables);
        for (String table: tables) {
            builder.append(table);
        }
        builder.append("\",\n");
        builder.append(getManyToManyJoin());
        builder.append(getManyToManyInverseJoin());
        builder.append("   )\n");
        return builder.toString();*/
        return "";
    }

    public enum FIELD_KEY_MAP {
        column(F_FIELD_NAME),
        unique(F_UNIQUE),
        length(F_LENGTH),
        notnull(F_NOT_NULL);
        private final String annotation;

        FIELD_KEY_MAP(String value) {
            this.annotation = value;
        }

        public String getAnnotation() {
            return this.annotation;
        }
    }
}

