package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.domain.BaseBean;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.FieldBeanInterface;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.Models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class EoChild extends EoChildScalar implements EO {
    private Map<String, IEOScalar> eoMap;
    private Object fieldValue;

    EoChild(final Object value, final Models models) {
        super(value, models);
    }

    public EoChild(final EO parentEo, final String fieldKey, final Object value, final Models fieldModels) {
        super(parentEo, fieldKey, value, fieldModels);
    }

    void setFieldValue(final Object value) {
        this.fieldValue = value;
    }

    protected void setParentValue(final Object value) {
        if (isRoot()) {
            throw new EoException("Root has no parent!");
        }
        if (!isParentSet()) {
            setFieldValue(value);
            return;
        }
        getParentEo().getModel().set(getFieldKey(), getParent().get(), value);
    }

    @Override
    public boolean hasEo(final String key) {
        return eoMap != null && eoMap.containsKey(key);
    }

    protected boolean hasEo(PathElement pathElement) {
        return hasEo(pathElement.getKey());
    }

    @Override
    public IEOScalar set(Object value, final String... paths) {
        if (value == null) {
            throw new EoException("Null value not allowed: Occured when setting null to + '" + Arrays.stream(paths).collect(Collectors.joining(Path.DELIMITER)) + "' at '" + getPathAsString() + "'.");
        }
        return createChild(new Path(paths), value);
    }

    protected Object getValue(final String fieldName) {
        try {
            return getModel().get(fieldName, get());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Object get(final String... pathStrings) {
        try {
            return getEo(pathStrings).get();
        } catch (EoException e) {
            if (pathStrings.length == 1) {
                return this.getModel().get(pathStrings[0], get());
            }
            throw new EoException(String.join("/", pathStrings) + e.getMessage());
        }
    }

    @Override
    public IEOScalar getEo(String... pathString) {
        return getEo(new Path(pathString));
    }

    public IEOScalar getEo(Path path) {
        IEOScalar target = this;
        if (path.isAbsolute()) {
            target = getRoot();
        }
        for (PathElement element : path.getEntries()) {
            if (element.isBack()) {
                target = target.getParent();
            } else if (element.isSame()) {
            } else {
                if (!(target instanceof EoChild)) {
                    throw new EoException("Could not move to path '" + element.getKey() + "' because wrapper is scalar ' for " + getPathAsString());
                }
                target = ((EoChild) target).getEo(element);
            }
        }
        return target;
    }

    public IEOScalar getEo(final PathElement pathElement) {
        if (!hasEo(pathElement)) {
            throw new EoException("Could not move to path '" + pathElement.getKey() + "' because key '" + pathElement.toString() + "' does not exist on '" + this.getPathAsString() + "'.");
        }
        return eoMap.get(pathElement.getKey());
    }

    @Override
    public IEOScalar createChild(final String... paths) {
        return createChild(new Path(paths), null);
    }

    protected IEOScalar createChild(Path path, Object value) {
        if (path.isEmpty()) {
            mapObject(value);
            return this;
        }
        IEOScalar parent = this;
        if (path.isAbsolute()) {
            parent = getRoot();
        }
        int counter = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            PathElement pathElement = path.getPathElement(i);
            if (((EoChild) parent).hasEo(pathElement)) {
                parent = ((EoChild) parent).getEo(pathElement);
                continue;
            }
            parent = getModels().createChild((EO) parent, pathElement, null);
            if (!(parent instanceof IEOObject)) {
                throw new EoException("");
            }
        }
        PathElement pathElement = path.getPathElement(path.size() - 1);
        if (((EoChild) parent).hasEo(pathElement)) {
            EoChildScalar child = (EoChildScalar) ((EoChild) parent).getEo(pathElement);
            child.set(value);
            return child;
        }
        return parent.getModels().createChild((EO) parent, path.getPathElement(path.size() - 1), value);
    }

    IEOScalar createChild(PathElement element) {
        if (element.isBack()) {
            return getParent();
        } else if (hasEo(element)) {
            return eoMap.get(element.getKey());
        }
        return getModels().createChild(this, element, null);
    }

    IEOScalar createChild(final PathElement pathElement, final Object childValue) {
        return getModels().createChild(this, pathElement, childValue);
    }

    protected void removeChild(String fieldName) {
        this.eoMap.remove(fieldName);
        getModel().remove(fieldName, get());
    }

    @Override
    public EO remove(final String... path) {
        Path removePath = new Path(path);
        EoChild eoChild = (EoChild) getEo(removePath.parent());
        String parentFieldName = removePath.getParentKey();
        if (!eoChild.hasEo(parentFieldName)) {
            throw new EoException("Could not remove entry '" + parentFieldName + "' because it is not set in '" + getModel().getModelKey() + "'");
        }
        eoChild.removeChild(parentFieldName);
        return eoChild;
    }

    public IEOScalar overWrite(final Object value, final String... path) {
        remove(path);
        return set(value, path);
    }

    @Override
    public void set(Object value) {
        if (fieldValue == null) {
            fieldValue = getModels().create();
        }
        if (hasParent()) {
            getParentEo().set(getFieldKey(), fieldValue);
        } else if (eoMap == null) {
            eoMap = new LinkedHashMap<>();
        }
        mapObject(value);
    }

    void set(String key, Object value) {
        getModel().set(key, get(), value);
    }

    void addEo(String key, IEOScalar child) {
        if (eoMap == null) {
            eoMap = new LinkedHashMap<>();
        }
        eoMap.put(key, child);
    }

    @Override
    public Object get() {
        return fieldValue;
    }

    protected void addChildEo(EoChildScalar childEo) {
        eoMap.put(childEo.getFieldKey(), childEo);
    }

    void setModels(String modelString) {
        Models models = new Models(getConfigMaps(), modelString.split(","));
        setModels(models);
        if (models.isCreate()) {
            this.fieldValue = models.create();
            if (hasParent()) {
                getParent().set(this.fieldValue, this.getFieldKey());
            }
        }
    }

    @Override
    public EO mapObject(Object value) {
        if (value == null) {
            return this;
        }
        ModelConfig valueModel = getConfigMaps().findModel(value);

        if (valueModel.isScalar()) {
            if (value instanceof String) {
                if (JSONToEO.JSON_PATTERN.matcher((String) value).find()) {
                    new JSONToEO((String) value).createChild(this);
                    return this;
                }
                Object base = createBaseObject(value);
                if (base == null) {
                    throw new EoException("Could not map scalar to container model '" + getModels().toString() + "' '" + this.getPath().toString() + "'");
                }
                value = base;
            } else if (value instanceof Long) {
                Object base = createBaseObject(value);
                if (base == null) {
                    throw new EoException("Could not map scalar to container model '" +
                            getModels().toString() + "' '" +
                            this.getPath().toString() + "'");
                }
                value = base;
            } else {
                throw new EoException("Could not map scalar '" +
                        value.toString() + "'(" +
                        valueModel.getModelKey() + ") to container model '" +
                        getModels().toString() + "' '" +
                        this.getPath().toString() + "'");
            }
        }
        Set<String> fieldNameSet = valueModel.keys(value);
        for (String fieldName : fieldNameSet) {
            if (valueModel.isObject()) {
                FieldBeanInterface fieldBean = valueModel.getField(fieldName);
                if (fieldBean == null) {
                    continue;
                }
            }
            PathElement pathElement = new PathElement(fieldName);
            if (valueModel.isJsonIgnore(fieldName)) continue;
            if (valueModel.isProperty(fieldName)) continue;
            if (!valueModel.exists(fieldName, value)) continue;
            Object childValue = valueModel.get(fieldName, value);

            if (childValue == null && hasEo(pathElement)) {
                continue;
            }
            createChild(pathElement, childValue);
        }
        return this;
    }

    private Object createBaseObject(Object value) {
        Object object = getModels().create();
        if (object instanceof BaseBean) {

            if (value instanceof Long) {
                ((BaseBean) object).setId((Long) object);
            } else if (value instanceof String) {
                ((BaseBean) object).setNaturalId((String) object);
            }
            return object;
        }
        return null;
    }

    @Override
    public boolean isEoEmpty() {
        return eoMap == null || eoMap.isEmpty();
    }

    @Override
    public Set<String> keysEo() {
        return eoMap.keySet();
    }

    @Override
    public Set<String> keys() {
        Set<String> filteredSet = new LinkedHashSet<>();
        if (this.eoMap == null || get() == null) {
            return filteredSet;
        }
        for (String key : keysEo()) {
            if (PathElement.isParentNotSet(key)) {
                continue;
            }
            filteredSet.add(key);
        }
        return filteredSet;
    }

    public List<String> keys(String pathString) {
        if (pathString == null || pathString.isEmpty() || ".".equals(pathString)) {
            List<String> keys = new ArrayList<>();
            keys.add(".");
            return keys;
        }
        return keys(new PathPattern(pathString));
    }

    public List<String> keys(PathPattern pathPattern) {
        return pathPattern.filter(keysEo());
    }

    public final Map<String, Object> getKeyValues() {
        if (getModel() == null) {
            throw new EoException("Null model!");
        }
        if (!isContainer()) {
            throw new EoException("Not a container model " + getModel().getModelKey() + "'!");
        }
        return getModel().getKeyValues(get(), new PathPattern("*"));
    }

    @Override
    public List<String> filterPaths(String pathString) {
        if (pathString == null || pathString.isEmpty() || ".".equals(pathString)) {
            List<String> keys = new ArrayList<>();
            keys.add(".");
            return keys;
        }
        return filterPaths(new PathPattern(pathString), "");
    }

    public List<String> filterPaths(PathPattern pathPattern, String path) {
        List<String> result = new ArrayList<>();
        List<String> filter = pathPattern.filter(keysEo());
        for (String key : filter) {
            if (key.equals(".config")) {
                continue;
            }
            String nextPath = path + Path.DELIMITER + key;
            nextPath = nextPath.replaceAll("^" + Path.DELIMITER, "");
            IEOScalar childAdapter = getEo(key);
            if (childAdapter == null) {
                continue;
            }
            PathPattern childPathPattern = pathPattern.getPathList(key);
            if ((!childPathPattern.isEmpty() || childPathPattern.isAll()) && !childAdapter.isScalar()) {
                List<String> keys = ((EoChild) childAdapter).filterPaths(childPathPattern, nextPath);
                result.addAll(keys);
            } else {
                result.add(nextPath);
            }
        }
        return result;
    }

    @Override
    public EoRoot getRoot() {
        return getParentEo().getRoot();
    }

    @Override
    public Path getPath() {
        return new Path(getPathAsString());
    }

    @Override
    public String getPathAsString() {
        final StringBuilder builder = new StringBuilder();
        getPathAsString(builder);
        return builder.toString();
    }

    void getPathAsString(final StringBuilder builder) {
        builder.insert(0, getFieldKey());
        builder.insert(0, Path.DELIMITER);
        getParentEo().getPathAsString(builder);
    }

    @Override
    public String toString() {
        return "(" + getModels().toString() + ") " + getPathAsString() + " -> " + get().toString() + "";
    }

    public String toString(JSONSerializationType serializationType) {
        if (isScalar()) {
            return get().toString();
        }
        try {
            return new EOToJSON()
                    .setSerializationType(serializationType)
                    .toJson(this);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error getSerialized " + getPath() + ": " + e.getMessage();
        }
    }

    @Override
    protected void compare(final StringBuilder builder, final IEOScalar other) {
        if (!other.isContainer()) {
            builder.append(getPathAsString() + ": other is not container but '" + other.getModelClass().getSimpleName());
            return;
        }
        List<String> list = new ArrayList<>(this.keys());
        List<String> otherList = new ArrayList<>(((IEOObject) other).keys());
        for (String key : otherList) {
            if (list.contains(key)) {
                continue;
            }
            builder.append(getPathAsString() + Path.DELIMITER + key + ": null <> " + other.getEo(key).getModelClass().getSimpleName() + "\n");
        }
        for (String key : list) {
            if (!other.hasEo(key)) {
                builder.append(getPathAsString() + key + ": " + getEo(key).getModelClass().getSimpleName() + "<> null \n");
                continue;
            }
            IEOScalar childEo = getEo(key);
            IEOScalar otherChildEo = other.getEo(key);
            ((EoChildScalar) childEo).compare(builder, otherChildEo);
        }
    }
}
