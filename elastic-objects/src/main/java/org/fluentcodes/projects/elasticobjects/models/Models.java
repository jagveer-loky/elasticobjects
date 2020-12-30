package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.JSONToEO;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * An array of models defining types
 *
 * @author Werner Diwischek
 * @since 20.05.16.
 */

public class Models {
    private static final List<Class> DEFAULT_CLASSES = Arrays.asList(new Class[] {Map.class, LinkedHashMap.class, String.class, Boolean.class, Integer.class});
    private final ModelConfig[] models;
    private final boolean hasChildModel;

    public Models(final ModelConfig... models) {
        this.models = models;
        hasChildModel = models.length > 1;
    }

    protected Models(final List<ModelConfig> models) {
        this.models = new ModelConfig[models.size()];
        for (int i = 0; i<models.size(); i++) {
            if (models.get(i) == null) {
                throw new EoInternalException("Null model for " + models);
            }
            this.models[i] = models.get(i);
        }
        hasChildModel = models.size() > 1;
    }

    //https://stackoverflow.com/questions/997482/does-java-support-default-parameter-values
    public Models(final EOConfigsCache cache, final Class... classes)  {
        this(cache, convert(classes));
    }

    public Models(final EOConfigsCache cache, final String... modelKeysIn)  {
        String[] modelKeys = strip(modelKeysIn);
        this.models = new ModelConfig[modelKeys.length];
        for (int i=0; i < modelKeysIn.length; i++) {
            models[i] = cache.findModel(modelKeysIn[i]);
        }
        hasChildModel = models.length > 1;
    }

    public Models(final EOConfigsCache cache) {
        hasChildModel = false;
        this.models = new ModelConfig[]{cache.findModel(Map.class)};
    }

    public static final Models ofValue(final EOConfigsCache cache, final Object value)  {
        if (value == null) {
            return new Models(cache, Map.class);
        }
        if (value.getClass() == Class.class) {
            return new Models(cache, (Class)value);
        }

        if (value instanceof String) {
            if  (JSONToEO.jsonMapPattern.matcher((String) value).find()) {
                return new Models(cache, Map.class);
            }
            else if  (JSONToEO.jsonListPattern.matcher((String) value).find()) {
                return new Models(cache, List.class);
            }
            else {
                return new Models(cache, String.class);
            }
        }
        return new Models(cache, value.getClass());
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

    public Models getChildModelsTrue()  {
        if (models.length < 2) {
            return null;
        }
        return new Models(Arrays.copyOfRange(this.models, 1, models.length));
    }

    public Models getChildModels()  {
        if (models.length < 2) {
            return new Models(EOConfigMapModels.DEFAULT_MODEL);
        }
        return new Models(Arrays.copyOfRange(this.models, 1, models.length));
    }

    public Models getChildModels(final String fieldKey) {
        if (fieldKey == null) {
            throw new EoException("Field key to derive child models should never be null for '" + toString() + "'!");
        }
        if (!PathElement.isParentSet(fieldKey)) {  // all fieldkeys starting with _ will be independent of the parent model.
            return null;
        }
        if (!getModel().isContainer()) {
            return null;
        }
        if (getModel().isList() || getModel().isMap()) {
            return getChildModelsTrue();
        }

        return getModel().getFieldConfig(fieldKey).getModels();
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

    public List<ModelConfigInterfaceMethods> getModels() {
        List<ModelConfigInterfaceMethods> models = new ArrayList<>();

        for (ModelConfigInterfaceMethods model : this.models) {
            models.add(model);
        }
        return models;
    }

    public String[] getModelsStringArray() {
        String[] modelsStringArray = new String[models.length];
        for (int i = 0; i< models.length; i++) {
            modelsStringArray[i] = models[i].getModelKey();
        }
        return modelsStringArray;
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

    public boolean isNull() {
        return getModel().isNull();
    }

    public Class getModelClass() {
        return getModel().getModelClass();
    }

    public ModelConfig getModel() {
        return models[0];
    }
    public boolean hasChildModel() {
        return hasChildModel;
    }

    public ModelConfig getChildModel() {
        if (!hasChildModel()) {
            return null;
        }
        return models[1];
    }

    public int size() {
        return this.models.length;
    }

    public ModelConfig get(int i) {
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

    public String createDirective() {
        if (DEFAULT_CLASSES.contains(getModelClass())) {
            return "";
        }
        return "(" + toString() + ")";
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
