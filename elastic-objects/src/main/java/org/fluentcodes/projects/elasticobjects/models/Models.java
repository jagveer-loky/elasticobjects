package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.JSONToEO;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.paths.PathElement;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.*;

/**
 * A list of models defining types
 *
 * @author Werner Diwischek
 * @since 20.05.16.
 */
public class Models {
    private final ModelInterface[] models;
    private final boolean hasChildModel;

    private Models(final ModelInterface... models) {
        this.models = models;
        hasChildModel = models.length > 1;
    }

    //https://stackoverflow.com/questions/997482/does-java-support-default-parameter-values
    public Models(final EOConfigsCache cache, final Class... classes)  {
        this(cache, convert(classes));
    }

    public Models(final EOConfigsCache cache, final String... modelKeysIn)  {
        String[] modelKeys = strip(modelKeysIn);
        this.models = new ModelInterface[modelKeys.length];
        for (int i=0; i < modelKeysIn.length; i++) {
            models[i] = cache.findModel(modelKeysIn[i]);
        }
        hasChildModel = models.length > 1;
    }

    private static String[] convert (Class[] classes) {
        if (classes == null || classes.length==0 || Object.class.equals(classes[0])) {
            return new String[] {"Map"};
        }
        String[] modelNames = new String[classes.length];
        for (int i = 0; i< classes.length;i++) {
            modelNames[i] = classes[i].getSimpleName();
        }
        return modelNames;
    }
    private static String[] strip (String[] toBeStripped) {
        if (toBeStripped == null || toBeStripped.length==0 || "Object".equals(toBeStripped[0])) {
            return new String[] {"Map"};
        }
        return toBeStripped;
    }

    public boolean isEmpty() {
        return models.length == 0;
    }

    public boolean isEmpty(Object source) {
        try {
            return getModel().isEmpty(source);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public Models getChildModels()  {
        if (models.length < 2) {
            return new Models(models[0].getConfigsCache(), "Map");
        }
        return new Models(Arrays.copyOfRange(this.models, 1, models.length));
    }

    private Models createValueModels(EO eo, Models childModels, Object value) {
        return new Models(eo.getConfigsCache(), getValueClass(childModels, value));
    }

    public static Class getValueClass(final Models childModel, final Object value) {
        if (value == null) {
            return Map.class;
        }
        if (childModel == null|| childModel.isEmpty()) {
            if (value instanceof String) {
                if (JSONToEO.jsonListPattern.matcher((String) value).find()) {
                    return List.class;
                } else if (JSONToEO.jsonMapPattern.matcher((String) value).find()) {
                    return Map.class;
                } else {
                    return String.class;
                }
            }
        }
        else if (!childModel.isScalar() && (value instanceof String)) {
            if (JSONToEO.jsonListPattern.matcher((String) value).find()) {
                return childModel.getModelClass();
            } else if (JSONToEO.jsonMapPattern.matcher((String) value).find()) {
                return childModel.getModelClass();
            }
        }
        else if (childModel.isEnum()) {
            return childModel.getModelClass();
        }
        //return null;
        return value.getClass();
    }

    public static Class getValueClass(final Object value) {
        return getValueClass(new Models(), value);
    }

    public Models getChildModels(final EO eo, final PathElement pathElement) {
        if (pathElement == null) {
            return null;
        }
        final String key = pathElement.getKey();
        if (key == null) {
            return null;
        }
        if (pathElement.isParentNotSet()) {
            return null;
        }
        if (getModel().isObject() && !PathElement.isParentNotSet(key)) {
            Models fieldModels = new Models(getModel().getFieldModel(key));
            if (fieldModels.isCreate() || fieldModels.isScalar() ) {
                return fieldModels;
            }
        }
        if (hasChildModel() && (getChildModel().isCreate()||getChildModel().isScalar())) {
            return getChildModels();
        }
        return null;
    }

    public List<ModelInterface> getModels() {
        List<ModelInterface> models = new ArrayList<>();

        for (ModelInterface model : this.models) {
            models.add(model);
        }
        return models;
    }

    public boolean hasModel() {
        return getModel()!=null && getModel().getModelClass()!=Map.class;
    }

    public boolean isCreate() {
        return getModel().isCreate();
    }

    public boolean isList() {
        return getModel().isList();
    }

    public boolean isObject() {
        return getModel().isObject();
    }

    public boolean isEnum() {
        return getModel().isEnum();
    }

    public boolean isMap() {
        return getModel().isMap();
    }

    public boolean isCall() {
        return getModel().isCall();
    }

    public boolean isDefaultMap() {
        return models.length == 1 && getModelClass() == Map.class;
    }

    public boolean isNull() {
        return getModel().isNull();
    }

    public Class getModelClass() {
        return getModel().getModelClass();
    }

    public ModelInterface getModel() {
        return models[0];
    }
    public boolean hasChildModel() {
        return hasChildModel;
    }

    public boolean hasDefaultMap() {
        return isMap() && !hasChildModel() && (getModelClass() == Map.class || getModelClass() == LinkedHashMap.class);
    }

    public ModelInterface getChildModel() {
        if (!hasChildModel()) {
            return null;
        }
        return models[1];
    }

    public int size() {
        return this.models.length;
    }

    public ModelInterface get(int i) {
        if (i>size()-1) {
            throw new EoException("Index out of bounds " + i + ">" + size());
        }
        return this.models[i];
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < size(); i++) {
            if (get(i) !=null) {
                if (i > 0) {
                    buffer.append(",");
                }
                buffer.append(this.get(i).getModelKey());
            }
        }
        return buffer.toString();
    }

    public boolean isScalar() {
        return getModel().isScalar();
    }


    public boolean isContainer() {
        return getModel().isContainer();
    }


    public Object transform(Object source)  {
        if (source == null) {
            return null;
        }
        if (!isScalar()) {
            return source;
        }
        return ScalarConverter.transform(getModelClass(), source);
    }

    public Object create()  {
        return getModel().create();
    }
}
