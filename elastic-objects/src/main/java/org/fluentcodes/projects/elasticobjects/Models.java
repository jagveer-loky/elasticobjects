package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.config.ModelConfigObject;
import org.fluentcodes.projects.elasticobjects.config.ModelInterface;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.*;

/**
 * A list of models defining types
 *
 * @author Werner Diwischek
 * @since 20.05.16.
 */
public class Models {
    private static final Logger LOG = LogManager.getLogger(Models.class);
    private List<ModelInterface> models;
    private EOConfigsCache configsCache;
    private boolean hasModel = true;
    private EO eo;

    protected Models(EOConfigsCache configsCache, Object value, boolean map)  {
        this.configsCache = configsCache;
        this.models = new ArrayList();
        if (value == null || (value instanceof JSONToEO)) {
            models.add(configsCache.findModel(Map.class));
            hasModel = false;
            return;
        }
        models.add(configsCache.findModel(value.getClass()));
    }

    protected Models(EOConfigsCache configsCache, Object value)  {
        this.configsCache = configsCache;
        this.models = new ArrayList();
        if (value instanceof String) {
            if (JSONToEO.jsonListPattern.matcher((String)value).find()) {
                models.add(configsCache.findModel(List.class));
            }
            else if (JSONToEO.jsonMapPattern.matcher((String)value).find()) {
                models.add(configsCache.findModel(Map.class));
            }
            else {
                models.add(configsCache.findModel(String.class));
            }
        }
        else {
            models.add(configsCache.findModel(value));
        }
    }

    /**
     * Creates a root adapter with an ItemsCache
     */
    private Models(EOConfigsCache configsCache)  {
        this.configsCache = configsCache;
        this.models = new ArrayList();
        models.add(configsCache.findModel(Map.class));
        hasModel = false;
    }

    public Models(final ModelInterface... models) {
        if (models == null) {
            this.models = new ArrayList<>();
        } else {
            this.models = Arrays.asList(models);
        }
        if (this.models.size() > 0) {
            configsCache = this.models.get(0).getConfigsCache();
        }
    }

    public Models(final List<ModelInterface> models) {
        if (models == null) {
            this.models = new ArrayList<>();
        } else {
            this.models = models;
        }
        if (models.size() > 0) {
            configsCache = models.get(0).getConfigsCache();
        }
    }


    public Models(final EOConfigsCache configsCache, final String classesString)  {
        this.configsCache = configsCache;
        if (classesString == null) {
            throw new EoException("Null value");
        }
        this.models = getByClasses(classesString);
    }

    //https://stackoverflow.com/questions/997482/does-java-support-default-parameter-values
    public Models(final EOConfigsCache configsCache, final Class... classes)  {
        this.configsCache = configsCache;
        if (classes.length == 0) {
            this.models = getByClasses(Map.class.getSimpleName());
            return;
        }
        this.models = getByClasses(configsCache, classes);
    }

    public Models(final EOConfigsCache configsCache, final List<String> classKeys)  {
        this.configsCache = configsCache;
        if (classKeys == null) {
            throw new EoException("Null classKeys");
        }
        if (classKeys.isEmpty()) {
            classKeys.add(Map.class.getSimpleName());
        }
        this.models = getByClasses(configsCache, classKeys);
    }

    private List<ModelInterface> getByClasses(final String classesString)  {
        if (classesString == null) {
            throw new EoException("Null value");
        }
        return getByClasses(configsCache, Arrays.asList(classesString.split(",")));
    }

    private List<ModelInterface> getByClasses(final EOConfigsCache provider, final String[] classes)  {
        return getByClasses(provider, Arrays.asList(classes));
    }

    private List<ModelInterface> getByClasses(final EOConfigsCache provider, final List<String> classes)  {
        if (classes == null) {
            throw new EoException("Null value");
        }
        List<ModelInterface> models = new ArrayList<>();
        if (classes.size() == 0) {
            return models;
        }
        ModelInterface buffer;
        for (String classEntry : classes) {
            if ("Object".equals(classEntry) && models.size() > 0) {
                break;
            }
            buffer = provider.findModel(classEntry);
            models.add(buffer);
            if (buffer.isObject() || buffer.isScalar()) {
                break;
            }

        }
        return models;
    }

    protected void setByClasses(final EOConfigsCache provider, final Class... classes)  {
        this.models = getByClasses(provider, classes);
    }

    protected List<ModelInterface> getByClasses(final EOConfigsCache provider, final Class... classes)  {
        List<ModelInterface> models = new ArrayList<>();
        ModelInterface modelConfig;
        for (Class classEntry : classes) {
            if (classEntry == null) {
                break;
            }
            if (classEntry == Object.class) {
                if (models.size() > 0) {
                    break;
                } else {
                    classEntry = Map.class;
                }
            }
            modelConfig = provider.findModel(classEntry);
            models.add(modelConfig);
            if (modelConfig.isObject() || modelConfig.isScalar()) {
                break;
            }

        }
        return models;
    }


    public boolean isEmpty() {
        if (this.models.isEmpty()) {
            return true;
        }
        return !hasChildModel() && !hasModel();
    }

    public boolean isEmpty(Object source) {
        try {
            return getModel().isEmpty(source);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    protected void mapClasses(Object value)  {
        if (value == null) {
            throw new EoException("Could not map null value! ");
        }
        if (value instanceof JSONToEO) {
            if (isScalar()) {
                throw new EoException("Could not map container to scalar: " + value.getClass().getSimpleName());
            }
            if (eo != null) {
                ((JSONToEO) value).createChild(eo);
            }
            return;
        }
        ModelInterface valueModel = configsCache.findModel(value);
        if (isContainer() && valueModel.isScalar()) {
            throw new EoException("Could not map scalar for to container: " + value.getClass().getSimpleName());
        }
        if (isScalar() && valueModel.isContainer()) {
            throw new EoException("Could not map container to scalar: " + value.getClass().getSimpleName());
        }
        if (eo != null) {
            if (isScalar()) {
                eo.mapObject(ScalarConverter.transform(getModelClass(), value));
            } else {
                eo.mapObject(value);
            }
        }
    }

    protected void setClasses(Object value)  {
        if (value == null) {
            return;
        }
        if (value instanceof JSONToEO) {
            if (isScalar()) {
                throw new EoException("Could not map container to scalar: " + value.getClass().getSimpleName());
            }
            return;
        }
        setClasses(new Models(configsCache, value.getClass()), value);
    }

    protected void setClasses(Models valueModels)  {
        setClasses(valueModels, valueModels.create());
    }

    protected void setClasses(Models valueModels, Object value)  {
        if (valueModels.isEmpty()) {
            return;
        }
        if (this.eo != null) {
            if (eo.hasParent()) {
                Models parentModel = ((EoChild) eo).getParentAdapter().getModels();
                Models child = parentModel.createChild(eo.getParentKey());
                if (child.hasModel()) {
                    setModelConcurrent(valueModels, child);
                } else {
                    setModelConcurrent(valueModels);
                }
            } else {
                setModelConcurrent(valueModels);
            }
            ((EoChild) eo).removeChildEO(value);
        } else {
            setModelConcurrent(valueModels);
        }
    }


    private final boolean isModelConcurrent(Models newModels, Models models)  {
        return newModels.getModelClass() == models.getModelClass()
                || (newModels.isMap() && models.isMap())
                || (newModels.isList() && models.isList());
    }

    private final boolean isModelConcurrent(ModelInterface valueModel, Models models)  {
        return valueModel.getModelClass() == models.getModelClass()
                || (valueModel.isMap() && models.isMap())
                || (valueModel.isList() && models.isList());
    }

    private final void setModelConcurrent(final ModelInterface valueModel)  {
        if (isModelConcurrent(valueModel, this)) {
            return;
        }
        this.models = new ArrayList<>();
        this.models.add(valueModel);
    }

    private final void setModelConcurrent(final Models newModels)  {
        if (isModelConcurrent(newModels.getModel(), this)) {
            return;
        }
        this.models = newModels.models;
    }

    private final void setModelConcurrent(final ModelInterface valueModel, final Models child)  {
        if (!isModelConcurrent(valueModel, child)) {
            throw new EoException("Child is typed with '" + child.getModelClass() + "' but value has '" + valueModel.getModelClass().getSimpleName() + "'.");
        }
    }

    private final void setModelConcurrent(final Models newModels, final Models child)  {
        if (!isModelConcurrent(newModels, child)) {
            throw new EoException("Child is typed with '" + child.getModelClass() + "' but value has '" + newModels.getModelClass().getSimpleName() + "'.");
        }
    }

    protected void checkRootValue(Object value, boolean map)  {
        if (value == null) {
            return;
        }
        if (map) {
            mapClasses(value);
            return;
        }
        setClasses(value);
    }


    public Models getChildModelsList()  {
        if (models.size() < 2) {
            return new Models(configsCache);
        }
        return new Models(this.models.subList(1, models.size()));
    }
    public Models getChildModels(Path path, Object value) {
        Models childModels = getChildModels(path);
        Models valueModels = null;
        if (childModels!=null) {
            if (value == null) {
                return childModels;
            }
            if (value instanceof String) {
                if (JSONToEO.jsonListPattern.matcher((String) value).find()) {
                    valueModels = new Models(configsCache.findModel(List.class));
                } else if (JSONToEO.jsonMapPattern.matcher((String) value).find()) {
                    valueModels = new Models(configsCache.findModel(Map.class));
                }
                else {
                    valueModels = new Models(configsCache.findModel(value));
                }
            }
            else {
                valueModels = new Models(configsCache.findModel(value));
            }
            checkModels(path.getFirstEntry(), valueModels.getModel(), childModels.getModel());
            return childModels;
        }
        if (value == null) {
            return new Models(configsCache);
        }
        if (valueModels!=null) {
            return valueModels;
        }
        if (value instanceof String) {
            if (JSONToEO.jsonListPattern.matcher((String) value).find()) {
                return new Models(configsCache.findModel(List.class));
            } else if (JSONToEO.jsonMapPattern.matcher((String) value).find()) {
                return new Models(configsCache.findModel(Map.class));
            }
        }
        return new Models(configsCache.findModel(value));
    }

    private void checkModels(String path, ModelInterface valueModel, ModelInterface childModel)  {
        if (childModel.isScalar() && !valueModel.isScalar()) {
            throw new EoException("Problem setting non scalar value ("
                    + valueModel.getNaturalId() + ") for field name "
                    + path + ". Expected is "
                    + childModel.getNaturalId() + "!");
        }
        else if (!childModel.isScalar() && valueModel.isScalar()) {
            throw new EoException("Problem setting scalar value ("
                    + valueModel.getNaturalId() + ") for field name "
                    + path + ". Expected is "
                    + childModel.getNaturalId() + "!");
        }
    }

    public Models getChildModels(Path path) {
        if (getModel().isObject()) {
            return new Models(getModel().getFieldModel(path.getFirstEntry()));
        }
        if (hasChildModel()) {
            return getChildModelsList();
        }
        if (path.hasModel()) {
            return new Models(configsCache, path.getModels());
        }
        if (path.hasChild()) { // default as map
            return new Models(configsCache);
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
        return hasModel;
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

    public boolean isNull() {
        if (this.models.isEmpty()) {
            return false;
        }
        return getModel().isNull();
    }

    public Class getModelClass() {
        if (models == null || models.size() == 0) {
            return Object.class;
        }
        try {
            return models.get(0).getModelClass();
        } catch (Exception e) {
            e.printStackTrace();
            return Object.class;
        }
    }

    public Models createChild(final String name)  {
        if (models.isEmpty()) {
            return new Models(configsCache);
        }
        if (isMap() || isList()) {
            if (hasChildModel()) {
                return new Models(getChildModel());
            } else {
                return new Models(configsCache);
            }
        } else if (isObject()) {
            return ((ModelConfigObject) getModel()).getFieldModels(name);
        }
        return null;
    }

    public ModelInterface getModel() {
        if (models == null || models.size() == 0) {
            return null;
        }
        return models.get(0);
    }

    public Models setModel(final ModelInterface model) {
        if (models.size() == 0) {
            models.add(model);
        } else {
            models.set(0, model);
        }
        return this;
    }

    public boolean hasChildModel() {
        if (models == null || models.size() < 2) {
            return false;
        }
        if (models.get(1) == null) {
            return false;
        }
        return models.get(1).hasModel();
    }

    public boolean hasDefaultMap() {
        return isMap() && !hasChildModel() && (getModelClass() == Map.class || getModelClass() == LinkedHashMap.class);
    }

    public ModelInterface getChildModel() {
        if (!hasChildModel()) {
            return null;
        }
        return models.get(1);
    }


    protected Class getChildModelClass()  {
        if (models.size() < 2) {
            return Object.class;
        }
        return models.get(1).getModelClass();
    }


    public int size() {
        return this.models.size();
    }

    public ModelInterface get(int i) {
        return this.models.get(i);
    }

    @Override
    public String toString() {
        if (models == null) {
            return "";
        }
        if (models.isEmpty()) {
            return "";
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < models.size(); i++) {
            if ("Object".equals(this.models.get(i).getModelKey())) {
                return buffer.toString();
            }
            if (i > 0) {
                buffer.append(",");
            }
            buffer.append(this.models.get(i).getModelKey());

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
