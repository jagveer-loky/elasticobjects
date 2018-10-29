package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.config.ModelConfigObject;
import org.fluentcodes.projects.elasticobjects.config.ModelInterface;
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

    protected Models(EOConfigsCache configsCache, Object value, boolean map) throws Exception {
        this.configsCache = configsCache;
        this.models = new ArrayList();
        if (value == null || (value instanceof JSONToEO)) {
            models.add(configsCache.findModel(Map.class));
            hasModel = false;
            return;
        }
        models.add(configsCache.findModel(value.getClass()));
    }

    /**
     * Creates a root adapter with an ItemsCache
     */
    private Models(EOConfigsCache configsCache) throws Exception {
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


    public Models(final EOConfigsCache configsCache, final String classesString) throws Exception {
        this.configsCache = configsCache;
        if (classesString == null) {
            throw new Exception("Null value");
        }
        this.models = getByClasses(classesString);
    }

    //https://stackoverflow.com/questions/997482/does-java-support-default-parameter-values
    public Models(final EOConfigsCache configsCache, final Class... classes) throws Exception {
        this.configsCache = configsCache;
        if (classes.length == 0) {
            this.models = new ArrayList();
            try {
                this.setByClasses(configsCache, Map.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.hasModel = false;
            return;
        }
        this.models = getByClasses(configsCache, classes);
    }

    private List<ModelInterface> getByClasses(final String classesString) throws Exception {
        if (classesString == null) {
            throw new Exception("Null value");
        }
        return getByClasses(configsCache, classesString.split(","));
    }

    private List<ModelInterface> getByClasses(final EOConfigsCache provider, final String[] classes) throws Exception {
        if (classes == null) {
            throw new Exception("Null value");
        }
        List<ModelInterface> models = new ArrayList<>();
        if (classes.length == 0) {
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

    protected void setByClasses(final EOConfigsCache provider, final Class... classes) throws Exception {
        this.models = getByClasses(provider, classes);
    }

    protected List<ModelInterface> getByClasses(final EOConfigsCache provider, final Class... classes) throws Exception {
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

    protected void mapClasses(Object value) throws Exception {
        if (value == null) {
            throw new Exception("Could not map null value! ");
        }
        if (value instanceof JSONToEO) {
            if (isScalar()) {
                throw new Exception("Could not map container to scalar: " + value.getClass().getSimpleName());
            }
            if (eo != null) {
                ((JSONToEO) value).createChild(eo);
            }
            return;
        }
        ModelInterface valueModel = configsCache.findModel(value);
        if (isContainer() && valueModel.isScalar()) {
            throw new Exception("Could not map scalar for to container: " + value.getClass().getSimpleName());
        }
        if (isScalar() && valueModel.isContainer()) {
            throw new Exception("Could not map container to scalar: " + value.getClass().getSimpleName());
        }
        if (eo != null) {
            if (isScalar()) {
                ((EOContainer) eo).map(ScalarConverter.transform(getModelClass(), value));
            } else {
                ((EOContainer) eo).map(value);
            }
        }
    }

    protected void setClasses(Object value) throws Exception {
        if (value == null) {
            return;
        }
        if (value instanceof JSONToEO) {
            if (isScalar()) {
                throw new Exception("Could not map container to scalar: " + value.getClass().getSimpleName());
            }
            return;
        }
        setClasses(new Models(configsCache, value.getClass()), value);
    }

    protected void setClasses(Models valueModels) throws Exception {
        setClasses(valueModels, valueModels.create());
    }

    protected void setClasses(Models valueModels, Object value) throws Exception {
        if (valueModels.isEmpty()) {
            return;
        }
        if (this.eo != null) {
            if (eo.hasParent()) {
                Models parentModel = ((EOContainer) eo).getParentAdapter().getModels();
                Models child = parentModel.createChild(eo.getParentKey());
                if (child.hasModel()) {
                    setModelConcurrent(valueModels, child);
                } else {
                    setModelConcurrent(valueModels);
                }
            } else {
                setModelConcurrent(valueModels);
            }
            ((EOContainer) eo).removeChildEO(value);
        } else {
            setModelConcurrent(valueModels);
        }
    }


    private final boolean isModelConcurrent(Models newModels, Models models) throws Exception {
        return newModels.getModelClass() == models.getModelClass()
                || (newModels.isMap() && models.isMap())
                || (newModels.isList() && models.isList());
    }

    private final boolean isModelConcurrent(ModelInterface valueModel, Models models) throws Exception {
        return valueModel.getModelClass() == models.getModelClass()
                || (valueModel.isMap() && models.isMap())
                || (valueModel.isList() && models.isList());
    }

    private final void setModelConcurrent(final ModelInterface valueModel) throws Exception {
        if (isModelConcurrent(valueModel, this)) {
            return;
        }
        this.models = new ArrayList<>();
        this.models.add(valueModel);
    }

    private final void setModelConcurrent(final Models newModels) throws Exception {
        if (isModelConcurrent(newModels.getModel(), this)) {
            return;
        }
        this.models = newModels.models;
    }

    private final void setModelConcurrent(final ModelInterface valueModel, final Models child) throws Exception {
        if (!isModelConcurrent(valueModel, child)) {
            throw new Exception("Child is typed with '" + child.getModelClass() + "' but value has '" + valueModel.getModelClass().getSimpleName() + "'.");
        }
    }

    private final void setModelConcurrent(final Models newModels, final Models child) throws Exception {
        if (!isModelConcurrent(newModels, child)) {
            throw new Exception("Child is typed with '" + child.getModelClass() + "' but value has '" + newModels.getModelClass().getSimpleName() + "'.");
        }
    }

    protected void checkRootValue(Object value, boolean map) throws Exception {
        if (value == null) {
            return;
        }
        if (map) {
            mapClasses(value);
            return;
        }
        setClasses(value);
    }


    public Models getChildModelsList() throws Exception {
        if (models.size() < 2) {
            return new Models(configsCache);
        }
        return new Models(this.models.subList(1, models.size()));
    }

    public List<ModelInterface> getModels() {
        List<ModelInterface> models = new ArrayList<>();

        for (ModelInterface model : this.models) {
            models.add(model);
        }
        return models;
    }

    protected boolean hasModel() {
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

    public Models createChild(final String name) throws Exception {
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

    protected Models createChildWithValue(String name, Object value) throws Exception {
        if (name == null) {
            throw new Exception("Null name throw an Exception");
        }
        Models childModels = createChild(name);
        if (value == null) {
            return childModels;
        }
        if (value instanceof JSONToEO) {
            return childModels;
        }
        ModelInterface valueModel = configsCache.findModel(value.getClass());
        if (valueModel == null) {
            throw new Exception("No model defined for  " + value.getClass().getSimpleName());
        }
        if (childModels == null || childModels.isEmpty()) {
            return new Models(valueModel);
        }
        return childModels;
    }

    protected Models createChildForSet(final Models childModels, final Object value, final String name) throws Exception {
        if (value == null) {
            return childModels;
        }
        ModelInterface valueModel = null;
        if (!(value instanceof JSONToEO)) {
            valueModel = configsCache.findModel(value);
        }

        if (valueModel.getModelClass() == childModels.getModelClass()) {
            return childModels;
        }
        if (childModels.isScalar() && valueModel.isScalar()) {
            return childModels;
        }
        if (childModels.isMap() && valueModel.isMap()) {
            return childModels;
        }
        if (childModels.isList() && valueModel.isList()) {
            return childModels;
        }
        throw new Exception("Typed Child " + name + " is " + childModels.getModelClass().getSimpleName() + " and non scalar value " + value.getClass().getSimpleName());
    }

    protected Models createChildForSet(final String name, final Object value) throws Exception {
        if (value instanceof JSONToEO) {
            throw new Exception("Json to EO should be mapped, not set!");
        }
        Models childModels = createChildWithValue(name, value);
        return createChildForSet(childModels, value, name);
    }

    protected Models createChildForSet(final String name, final Object value, final Models targetModels) throws Exception {
        if (value instanceof JSONToEO) {
            throw new Exception("Json to EO should be mapped, not set!");
        }
        Models childModels = createChildWithValue(name, value);
        if (value == null) {
            if (!childModels.hasModel() && targetModels != null && targetModels.hasModel()) {
                return createChildForSet(targetModels, value, name);
            }
        }
        return createChildForSet(childModels, value, name);
    }


    protected Models createChildForMap(final String key, final Object value) throws Exception {
        Models childModels = createChildWithValue(key, value);
        return createChildForMap(childModels, value, key);
    }

    protected Models createChildForMap(Models childModels, final Object value, final String key) throws Exception {
        if (value == null) {
            return childModels;
        }
        if (value instanceof JSONToEO) {
            if (childModels.isScalar()) {
                throw new Exception("child model is scalar and could not be mapped to scalar!");
            }
            return childModels;
        }
        if (!childModels.hasModel()) {
            return new Models(configsCache, value.getClass());
        }
        ModelInterface valueModel = configsCache.findModel(value.getClass());
        if (childModels.isScalar() && valueModel.isScalar()) {
            return childModels;
        }
        if (valueModel.isList()) {
            if (childModels.isList()) {
                return childModels;
            }
            if (childModels.isMap()) {
                return childModels;
            }
        } else {
            if (valueModel.isContainer() && childModels.isContainer()) {
                return childModels;
            }
        }
        throw new Exception("Could not find a map type for name='" + key + "' with type='" + childModels.getModelClass().getSimpleName() + "' and value class='" + valueModel.getModelClass().getSimpleName() + "'");

    }

    protected Models createChildForMap(final String key, final Object value, final Class... classes) throws Exception {
        if (classes == null || classes.length == 0) {
            return createChildForMap(key, value);
        }
        return createChildForMap(key, value, new Models(configsCache, classes));
    }

    protected Models createChildForMap(final String key, final Object value, final Models classModels) throws Exception {
        Models childModels = createChild(key);

        if (classModels == null || !classModels.hasModel()) {
            return createChildForMap(childModels, value, key);
        } else if (childModels.hasModel()) {
            if (!childModels.toString().equals(classModels.toString())) {
                if (childModels.isScalar()) {
                    LOG.debug("Child has a model '" + childModels.toString() + "' and targetModels '" + classModels.toString() + "'is set! Using childModels");
                } else if (classModels.isMap()) {
                    LOG.debug("Child has a model '" + childModels.toString() + "' and targetModels '" + classModels.toString() + "'is set! Using childModels");
                } else {
                    LOG.info("Child has a model '" + childModels.toString() + "' and targetModels '" + classModels.toString() + "'is set! Using childModels");
                }
            }
            return createChildForMap(childModels, value, key);
        } else if (classModels.hasModel()) {
            return createChildForMap(classModels, value, key);
        } else if (value != null) {
            return new Models(configsCache, value.getClass());
        } else {
            return new Models(configsCache);
        }
    }

    protected Models createChild(final String key, final Object value, final Models classModels, final boolean map) throws Exception {
        if (map) {
            return createChildForMap(key, value, classModels);
        }
        return createChildForSet(key, value, classModels);
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


    protected Class getChildModelClass() throws Exception {
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


    public Object transform(Object source) throws Exception {
        if (source == null) {
            return null;
        }
        if (!isScalar()) {
            return source;
        }
        return ScalarConverter.transform(getModelClass(), source);
    }

    protected void setEO(EO eo) {
        this.eo = eo;
    }


    public Object create() throws Exception {
        return getModel().create();
    }
}
