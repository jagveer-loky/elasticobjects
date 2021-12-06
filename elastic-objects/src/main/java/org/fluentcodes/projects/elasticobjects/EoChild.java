package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.domain.BaseBean;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.FieldBeanInterface;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.Models;
import org.fluentcodes.projects.elasticobjects.utils.ScalarComparator;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class EoChild implements EO {
    private final EO parentEo;
    private final String fieldKey;
    private final Map<String, EO> eoMap;
    private Models fieldModels;
    private Object fieldValue;
    private boolean changed;

    EoChild() {
        parentEo = null;
        fieldKey = null;
        eoMap = new LinkedHashMap<>();
    }

    public EoChild(final EO parentEo, final String fieldKey, final Object value, final Models fieldModels) {
        if (parentEo != null && (fieldKey == null || fieldKey.isEmpty())) {
            throw new EoException("Could not create parent EO without a fieldKey for '" + fieldModels.toString() + "'");
        }
        this.parentEo = parentEo;
        this.fieldKey = fieldKey;
        this.fieldModels = fieldModels;
        this.changed = false;
        this.eoMap = fieldModels.isScalar() ? null : new LinkedHashMap<>();

        if (fieldModels.isScalar()) {
            if (parentEo == null) {
                throw new EoException("Null scalar value not supported for root");
            }
            if (isParentSet())
                setParentValue(ScalarConverter.transformScalar(fieldModels.getModelClass(), value));
            else
                this.fieldValue = ScalarConverter.transformScalar(fieldModels.getModelClass(), value);
            ((EoChild) parentEo).addChildEo(this);
            return;
        } else if (value != null && value.getClass() == fieldModels.getModelClass()) {  // use existing non scalar
            this.fieldValue = value;
        } else {
            this.fieldValue = fieldModels.create();
        }
        if (parentEo == null) return;
        // container
        if (value != null) {
            mapObject(value);
        }
        setParentValue(this.fieldValue);
        ((EoChild) parentEo).addChildEo(this);

    }

    private void setFieldValue(final Object value) {
        this.fieldValue = value;
    }

    protected void setParentValue(final Object value) {
        if (!hasParent()) throw new EoException("Root has no parent!");
        if (getParentEo().hasEo(fieldKey)) {
            this.changed = true;
        }
        if (!isParentSet()) {
            setFieldValue(value);
            return;
        }
        getParentEo().setKeyValue(getFieldKey(), value);
    }

    protected void setKeyValue(final String fieldKey, final Object value) {
        getModel().set(fieldKey, get(), value);
    }

    @Override
    public String getFieldKey() {
        return fieldKey;
    }

    public EoChild getParentEo() {
        return (EoChild) parentEo;
    }

    @Override
    public EO getParent() {
        if (parentEo == null) {
            throw new EoException("Root has no parent");
        }
        return parentEo;
    }

    @Override
    public boolean hasParent() {
        return parentEo != null;
    }

    protected Object getValueFromParent() {
        return getParentEo().getValue(getFieldKey());
    }

    @Override
    public boolean hasEo(final String key) {
        return eoMap != null && eoMap.containsKey(key);
    }

    protected boolean hasEo(PathElement pathElement) {
        return hasEo(pathElement.getKey());
    }

    @Override
    public EO set(Object value, final String... paths) {
        if (value == null) {
            throw new EoException("Null value not allowed: Occured when setting null to + '" + Arrays.stream(paths).collect(Collectors.joining(Path.DELIMITER)) + "' at '" + getPathAsString() + "'.");
        }
        return createChild(new Path(paths), value);
    }


    @Override
    public EO setEmpty(final String... paths) {
        return new Path(paths).create(this, null);
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
            EO eo = new Path(pathStrings).moveTo(this);
            if (eo.isScalar() && !eo.isRoot() && PathElement.isParentSet(getFieldKey())) {
                return ((EoChild) eo).getValueFromParent();
            }
            return eo.get();
        } catch (EoException e) {
            if (pathStrings.length == 1) {
                return this.getModel().get(pathStrings[0], get());
            }
            throw new EoException(String.join("/", pathStrings) + e.getMessage());
        }
    }

    public Object get(final PathElement pathElement) {
        if (!hasEo(pathElement)) {
            throw new EoException("Could not move to eo");
        }
        EO child = getEo(pathElement);
        if (child.isScalar() && !child.isRoot() && pathElement.isParentSet()) {
            return ((EoChild) child).getValueFromParent();
        }
        return child.get();
    }

    protected EO createChild(Path path, Object value) {
        if (path.isEmpty()) {
            mapObject(value);
            return this;
        }
        EO parent = this;
        if (path.isAbsolute()) {
            parent = getRoot();
        }
        int counter = 0;
        for (PathElement element : path.getEntries()) {
            counter++;
            if (element.isRootModel()) {
                throw new EoException("Could not change model with a set");
            }
            if (element.isBack()) {
                parent = parent.getParent();
            } else if (((EoChild) parent).hasEo(element)) {
                parent = parent.getEo(element);
            } else {
                if (counter == path.size()) {
                    parent = parent.createChild(element, value);
                    return parent; // value already mapped by create...
                } else {
                    parent = parent.createChild(element, null);
                }
            }
        }
        parent.mapObject(value);
        return parent;

    }

    @Override
    public EO createChild(final PathElement fieldKey) {
        return this.createChild(fieldKey, null);
    }

    @Override
    public EO createChild(final PathElement pathElement, final Object childValue) {
        if (hasEo(pathElement)) {
            if (childValue != null) {
                getEo(pathElement).mapObject(childValue);
            }
            return getEo(pathElement);
        }

        try {
            if (pathElement.isRootModel()) {
                if (!isEmpty()) {
                    throw new EoException("Model will be changed only for empty parents '" + childValue + "'.");
                }
                this.fieldModels = new Models(this.getConfigMaps(), ((String) childValue).split(","));
                this.fieldValue = fieldModels.create();
                new EoChild(this, PathElement.ROOT_MODEL, fieldModels.toString(), new Models(getConfigMaps(), String.class));
                return this;
            }
            return getModels().createChild(this, pathElement, childValue);
        } catch (Exception e) {
            throw new EoException("Exception for '" + pathElement.getKey() + "': " + e.getMessage());
        }
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

    public EO overWrite(final Object value, final String... path) {
        remove(path);
        return set(value, path);
    }

    @Override
    public EO getEo(String... pathString) {
        return new Path(pathString).moveTo(this);
    }

    @Override
    public EO getEo(PathElement path) {
        if (!hasEo(path)) {
            throw new EoException("Could not find entry for '" + path + "'.");
        }
        return eoMap.get(path.getKey());
    }

    @Override
    public Object get() {
        if (!isScalar()) return fieldValue;
        if (!isParentSet()) return fieldValue;
        return getValueFromParent();
    }

    void set(Object value) {
        this.fieldValue = value;
    }

    protected EO addChildEo(EO childEo) {
        eoMap.put(childEo.getFieldKey(), childEo);
        return childEo;
    }


    @Override
    public EO mapObject(Object value) {
        if (value == null) {
            return this;
        }
        if (isScalar()) {
            setParentValue(ScalarConverter.transform(fieldModels.getModelClass(), value));
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
        Object object = fieldModels.create();
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
    public int sizeEo() {
        if (eoMap == null) {
            return 0;
        }
        return this.keysEo().size();
    }

    @Override
    public int size() {
        if (eoMap == null) {
            return 0;
        }
        return this.keys().size();
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
            EO childAdapter = getEo(key);
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
    public Set<String> getCallKeys() {
        return getCallsEo().keys();
    }

    public EO addCall(Call call) {
        if (call == null) {
            throw new EoException("Null call?!");
        }
        if (!call.hasTargetPath() && !isRoot()) {
            call.setTargetPath(this.getPathAsString());
        }
        return createChild(new PathElement(""), call);
    }

    public EO getCallsEo() {
        return initCalls();
    }

    @Override
    public EO getCallEo(final String key) {
        return getCallsEo().getEo(key);
    }

    private EO initCalls() {
        if (hasCalls()) {
            return getRoot().getEo(PathElement.CALLS);
        }
        EO rootEo = getRoot();
        return rootEo.createChild(new PathElement(PathElement.CALLS, List.class));
    }

    protected boolean hasCalls() {
        return getRoot().hasEo(PathElement.CALLS);
    }

    @Override
    public boolean execute() {
        return getRoot().execute();
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
        if (isRoot()) {
            return Path.DELIMITER;
        }
        final StringBuilder builder = new StringBuilder();
        getPathAsString(builder);
        return builder.toString();
    }

    protected void getPathAsString(final StringBuilder builder) {
        if (isRoot()) {
            return;
        }
        if (!hasParent()) {
            throw new EoException("Non root with no parent!");
        }
        if (getParentEo() == this) {
            throw new EoException("Self referencing child " + getParentEo().toString());
        }
        builder.insert(0, getFieldKey());
        builder.insert(0, Path.DELIMITER);
        getParentEo().getPathAsString(builder);
    }

    private EO getLogEo() {
        EO rootEo = getRoot();
        if (!rootEo.hasEo(PathElement.LOGS)) {
            return rootEo.createChild(new PathElement(PathElement.LOGS));
        }
        return rootEo.getEo(PathElement.LOGS);
    }

    @Override
    public List<String> getLogList() {
        if (!hasEo(PathElement.LOGS)) {
            return new ArrayList<>();
        }
        return (List<String>) getLogEo().get();
    }

    public EO log(String message, LogLevel logLevel) {
        if (message == null) {
            return this;
        }
        setErrorLevel(logLevel);
        EO logEo = getLogEo();
        PathElement logElement = new PathElement(Integer.toString(logEo.size()));
        logEo.createChild(logElement, logLevel.name() + " - " + LocalDateTime.now().toString() + " - " + message);
        return this;
    }

    public EO log(String message, LogLevel logLevel, Exception e) {
        log(message + ": " + e.getMessage(), logLevel);
        return this;
    }

    // LOG_LEVEL
    @Override
    public LogLevel getLogLevel() {
        if (hasLogLevel()) {
            return (LogLevel) get(new PathElement(PathElement.LOG_LEVEL));
        }
        if (isRoot()) {
            return LogLevel.WARN;
        }
        return getParent().getLogLevel();
    }

    @Override
    public EO setLogLevel(LogLevel logLevel) {
        if (!hasEo(PathElement.LOG_LEVEL)) {
            createChild(PathElement.OF_LOG_LEVEL, logLevel);
        }
        return this;
    }

    @Override
    public LogLevel getErrorLevel() {
        return (LogLevel) getErrorLevelEo().get();
    }

    private EO getErrorLevelEo() {
        EO rootEo = getRoot();
        if (!rootEo.hasEo(PathElement.ERROR_LEVEL)) {
            return rootEo.createChild(PathElement.OF_ERROR_LEVEL, LogLevel.DEBUG);
        }
        return rootEo.getEo(PathElement.ERROR_LEVEL);
    }

    private void setErrorLevel(LogLevel messageLogLevel) {
        if (getErrorLevel().ordinal() <= messageLogLevel.ordinal()) {
            ((EoChild) getErrorLevelEo()).setFieldValue(messageLogLevel);
        }
    }

    @Override
    public void setRoles(final String... roles) {
        this.setRoles(Arrays.asList(roles));
    }

    @Override
    public List<String> getRoles() {
        return getRoot().getRoles();
    }

    @Override
    public void setRoles(final List<String> roles) {
        getRoot().setRoles(roles);
    }

    public boolean isCheckObjectReplication() {
        return getRoot().isCheckObjectReplication();
    }

    public void setCheckObjectReplication(boolean checkObjectReplication) {
        getRoot().setCheckObjectReplication(checkObjectReplication);
    }

    public Models getModels() {
        return this.fieldModels;
    }

    void setModels(Models models) {
        this.fieldModels = models;
    }

    /**
     * true if is a list. Will be overwritten in the list type adapters.
     *
     * @return always true
     */

    public boolean isParentSet() {
        return parentEo != null && PathElement.isParentSet(fieldKey);
    }

    @Override
    public boolean isChanged() {
        return changed;
    }
    // SERIALIZATION TYPE

    @Override
    public JSONSerializationType getSerializationType() {
        if (!hasSerializationType()) {
            return JSONSerializationType.EO;
        }
        return (JSONSerializationType) getSerializationTypeEo().get();
    }

    private EoChild getSerializationTypeEo() {
        return (EoChild) getRoot().getEo(PathElement.SERIALIZATION_TYPE);
    }

    private boolean initSerializationTypeEo(JSONSerializationType serializationType) {
        if (hasSerializationType()) {
            return false;
        }
        getRoot().createChild(new PathElement(PathElement.SERIALIZATION_TYPE), serializationType);
        return true;
    }

    @Override
    public EO setSerializationType(JSONSerializationType serializationType) {
        if (!initSerializationTypeEo(serializationType)) {
            getSerializationTypeEo().setParentValue(serializationType);
        }
        return this;
    }

    @Override
    public String toString() {
        return "(" + getModels().toString() + ") " + getPathAsString() + " -> " + get().toString() + "";
    }

    @Override
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
    public String compare(final EO other) {
        StringBuilder diff = new StringBuilder();
        compare(diff, other);
        return diff.toString();
    }

    protected void compare(final StringBuilder builder, final EO other) {
        if (this.isNull()) {
            return;
        }
        if (this.isScalar()) {
            if (!ScalarComparator.compare(this.get(), other.get())) {
                builder.append(getPathAsString() + ": " + this.get() + " <> " + other.get());
            }
            return;
        }
        List<String> list = new ArrayList<>(this.keys());
        List<String> otherList = new ArrayList<>(other.keys());
        for (String key : otherList) {
            if (list.contains(key)) {
                continue;
            }
            builder.append(getPathAsString() + Path.DELIMITER + key + ": null <> " + other.getEo(key).getModelClass().getSimpleName() + "\n");
        }
        for (String key : list) {
            EO childEo = this.getEo(key);
            EO otherChildEo = null;
            if (other.hasEo(key)) {
                otherChildEo = other.getEo(key);
            } else {
                builder.append(getPathAsString() + key + ": " + getEo(key).getModelClass().getSimpleName() + "<> null \n");

                continue;
            }
            if (childEo == null && otherChildEo == null) {
                builder.append(Path.DELIMITER + getPath() + " - " + key);
                builder.append("\nboth null!\n");
                continue;
            } else if (childEo == null && otherChildEo != null) {
                try {
                    if (otherChildEo.isContainer()) {
                        builder.append("null != " + getPath() + "/" + key + " with size= " + otherChildEo.sizeEo() + "\n");
                        continue;
                    } else {
                        builder.append("null != " + getPath() + "/" + key + " = " + otherChildEo.get() + "\n");
                        continue;
                    }
                } catch (Exception e) {
                    builder.append(getPath() + "/" + key + ":");
                    builder.append("\n" + e.getMessage() + "\n");
                    e.printStackTrace();
                    continue;
                }
            } else if (childEo != null && otherChildEo == null) {
                try {
                    if (childEo.isContainer()) {
                        builder.append(getPath() + "/" + key + " with size= " + childEo.sizeEo() + " != null\n");
                        continue;

                    } else {
                        builder.append(getPath() + "/" + key + " = " + childEo.get() + " != null\n");
                        continue;
                    }
                } catch (Exception e) {
                    builder.append(getPath() + "/" + key + ":");
                    builder.append("\n" + e.getMessage() + "\n");
                    e.printStackTrace();
                    continue;
                }
            }
            ((EoChild) childEo).compare(builder, otherChildEo);
        }
    }
}
