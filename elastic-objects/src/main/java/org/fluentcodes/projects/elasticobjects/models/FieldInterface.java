package org.fluentcodes.projects.elasticobjects.models;

/*.{javaHeader}|*/
/**
 * 
 * Access methods for field properties map and get method definitions for final fields.  
 * @author Werner Diwischek
 * @creationDate Sat Sep 19 00:00:00 CEST 2020
 * @modificationDate Thu Jan 14 06:06:25 CET 2021
 */
public interface FieldInterface extends ConfigInterface {
/*.{}.*/

/*.{javaStaticNames}|*/
   String F_DEFAULT = "default";
   String F_FIELD_KEY = "fieldKey";
   String F_FIELD_NAME = "fieldName";
   String F_FINAL = "final";
   String F_GENERATED = "generated";
   String F_JAVASCRIPT_TYPE = "javascriptType";
   String F_JSON_IGNORE = "jsonIgnore";
   String F_LENGTH = "length";
   String F_MAX = "max";
   String F_MIN = "min";
   String F_MODEL_KEYS = "modelKeys";
   String F_NOT_NULL = "notNull";
   String F_OVERRIDE = "override";
   String F_PROPERTY = "property";
   String F_STATIC_NAME = "staticName";
   String F_SUPER = "super";
   String F_TRANSIENT = "transient";
   String F_UNIQUE = "unique";
/*.{}.*/

    /*.{javaAccessors}|*/
   default Boolean getDefault(){
      return (Boolean) getProperties().get(F_DEFAULT);
   }
   default boolean hasDefault() {
      return getProperties().containsKey(F_DEFAULT) && getProperties().get(F_DEFAULT) != null;
   }

   default boolean isDefault() {
      return hasDefault() && getDefault();
   }

   String getFieldKey();
   default String getKey() {
      if (hasNaturalId()) return getNaturalId();
      if (hasFieldKey()) return getFieldKey();
      return "";
   }
   default boolean hasKey() {
      return !getKey().isEmpty();
   }

   default boolean hasFieldKey() {
      return getFieldKey() != null && !getFieldKey().isEmpty();
   }

   default String getFieldName(){
      return (String) getProperties().get(F_FIELD_NAME);
   }
   default boolean hasFieldName() {
      return getProperties().containsKey(F_FIELD_NAME) && getProperties().get(F_FIELD_NAME) != null;
   }

   default Boolean getFinal(){
      return (Boolean) getProperties().get(F_FINAL);
   }
   default boolean hasFinal() {
      return getProperties().containsKey(F_FINAL) && getProperties().get(F_FINAL) != null;
   }

   default boolean isFinal() {
      return hasFinal() && getFinal();
   }

   default Boolean getGenerated(){
      return (Boolean) getProperties().get(F_GENERATED);
   }
   default boolean hasGenerated() {
      return getProperties().containsKey(F_GENERATED) && getProperties().get(F_GENERATED) != null;
   }

   default boolean isGenerated() {
      return hasGenerated() && getGenerated();
   }

   default String getJavascriptType(){
      return (String) getProperties().get(F_JAVASCRIPT_TYPE);
   }
   default boolean hasJavascriptType() {
      return getProperties().containsKey(F_JAVASCRIPT_TYPE) && getProperties().get(F_JAVASCRIPT_TYPE) != null;
   }

   default Boolean getJsonIgnore(){
      return (Boolean) getProperties().get(F_JSON_IGNORE);
   }
   default boolean hasJsonIgnore() {
      return getProperties().containsKey(F_JSON_IGNORE) && getProperties().get(F_JSON_IGNORE) != null;
   }

   default boolean isJsonIgnore() {
      return hasJsonIgnore() && getJsonIgnore();
   }

   Integer getLength();

   default boolean hasLength() {
      return getLength() != null;
   }

   default Integer getMax(){
      return (Integer) getProperties().get(F_MAX);
   }
   default boolean hasMax() {
      return getProperties().containsKey(F_MAX) && getProperties().get(F_MAX) != null;
   }

   default Integer getMin(){
      return (Integer) getProperties().get(F_MIN);
   }
   default boolean hasMin() {
      return getProperties().containsKey(F_MIN) && getProperties().get(F_MIN) != null;
   }

   String getModelKeys();

   default boolean hasModelKeys() {
      return getModelKeys() != null && !getModelKeys().isEmpty();
   }

   default Boolean getNotNull(){
      return (Boolean) getProperties().get(F_NOT_NULL);
   }
   default boolean hasNotNull() {
      return getProperties().containsKey(F_NOT_NULL) && getProperties().get(F_NOT_NULL) != null;
   }

   default boolean isNotNull() {
      return hasNotNull() && getNotNull();
   }

   default Boolean getOverride(){
      return (Boolean) getProperties().get(F_OVERRIDE);
   }
   default boolean hasOverride() {
      return getProperties().containsKey(F_OVERRIDE) && getProperties().get(F_OVERRIDE) != null;
   }

   default boolean isOverride() {
      return hasOverride() && getOverride();
   }

   default Boolean getProperty(){
      return (Boolean) getProperties().get(F_PROPERTY);
   }
   default boolean hasProperty() {
      return getProperties().containsKey(F_PROPERTY) && getProperties().get(F_PROPERTY) != null;
   }

   default boolean isProperty() {
      return hasProperty() && getProperty();
   }

   default Boolean getStaticName(){
      return (Boolean) getProperties().get(F_STATIC_NAME);
   }
   default boolean hasStaticName() {
      return getProperties().containsKey(F_STATIC_NAME) && getProperties().get(F_STATIC_NAME) != null;
   }

   default boolean isStaticName() {
      return hasStaticName() && getStaticName();
   }

   default Boolean getSuper(){
      return (Boolean) getProperties().get(F_SUPER);
   }
   default boolean hasSuper() {
      return getProperties().containsKey(F_SUPER) && getProperties().get(F_SUPER) != null;
   }

   default boolean isSuper() {
      return hasSuper() && getSuper();
   }

   default Boolean getTransient(){
      return (Boolean) getProperties().get(F_TRANSIENT);
   }
   default boolean hasTransient() {
      return getProperties().containsKey(F_TRANSIENT) && getProperties().get(F_TRANSIENT) != null;
   }

   default boolean isTransient() {
      return hasTransient() && getTransient();
   }

   default Boolean getUnique(){
      return (Boolean) getProperties().get(F_UNIQUE);
   }
   default boolean hasUnique() {
      return getProperties().containsKey(F_UNIQUE) && getProperties().get(F_UNIQUE) != null;
   }

   default boolean isUnique() {
      return hasUnique() && getUnique();
   }

/*.{}.*/
    default Object getDefaultValue() {
        return null;
    }
    default boolean hasDefaultValue() {
        return getDefaultValue()!=null;
    }

    default boolean hasSize() {
        return hasMax()||hasMin();
    }


   ModelInterface getParentModel();
   default boolean hasParentModel() {
      return getParentModel()!=null;
   }
   default boolean hasParentModelKey() {
      return hasParentModel() && getParentModel().hasModelKey();
   }

    default String getModelKey() {
       if (!hasParentModelKey()) return "";
       if (this.hasParentModelKey()) return getParentModel().getModelKey();
       if (hasParentModelNaturalId()) return getParentModel().getNaturalId();
       return "";
    }

   default boolean hasParentModelNaturalId() {
      return hasParentModel() && getParentModel().hasNaturalId() ;
   }
}


