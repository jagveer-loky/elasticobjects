package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.domain.Base;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.Models;
import org.fluentcodes.projects.elasticobjects.utils.ScalarComparator;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class EoChild implements EO {
    private final EO parentEo;
    private final String fieldKey;
    private Models fieldModels;
    private Object value;
    private boolean changed;

    private final Map<String, EO> eoMap;

    protected EoChild(EoChildParams params) {
        parentEo = params.getParentEo();
        fieldModels = params.getFieldModels();
        fieldKey = params.getFieldKey();

        if (!fieldModels.isScalar()) {
            eoMap = new LinkedHashMap<>();
            this.value = fieldModels.create();
            if (params.getValue() != null) {
                this.value = fieldModels.create();
                mapObject(params.getValue());
            }
            if (hasParent()) ((EoChild)parentEo).setValue(value);
        }
        else {
            eoMap = null;
            if (PathElement.isParentSet(fieldKey) && hasParent()) {  // scalar context
                setValue(params.getValue());
            }
            else  {  // scalar context
                this.value = params.getValue();
            }
        }
        if (hasParent()) ((EoChild)parentEo).addChildEo(this);
    }

    protected void setValue(final Object value) {
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
        return parentEo;
    }

    protected Object getValueFromParent() {
        return getParentEo().getValue(getFieldKey());
    }

    @Override
    public boolean hasParent() {
        return !isRoot();
    }

    @Override
    public boolean hasEo(final String key) {
        return eoMap!=null && eoMap.containsKey(key);
    }

    protected boolean hasEo(PathElement pathElement) {
        return hasEo(pathElement.getKey());
    }

    @Override
    public EO set(Object value, final String... paths) {
        return new Path(paths).create(this, value);
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
            EO eo =  new Path(pathStrings).moveTo(this);
            if (eo.isScalar() && !eo.isRoot()) {
                return ((EoChild)eo).getValueFromParent();
            }
            return eo.get();
        }
        catch (EoException e) {
            if (pathStrings.length == 1) {
                return this.getModel().get(pathStrings[0], get());
            }
            throw new EoException(String.join("/", pathStrings) + e.getMessage());
        }
    }

    @Override
    public EO createChild(final PathElement fieldKey) {
        return this.createChild(fieldKey, null);
    }

    @Override
    public EO createChild(final PathElement pathElement, final Object value) {
        if (hasEo(pathElement)) {
            if (value != null) {
                mapObject(value);
            }
            return getEo(pathElement);
            //throw new EoException("Element already exists in " + pathElement.getKey());
        }

        try {
            if (pathElement.isRootModel()) {
                if (!isEmpty()){
                    throw new EoException("Can change model only for empty parents");
                }
                if (isRoot()) {
                    this.fieldModels = pathElement.getModels(this.getConfigsCache());
                    this.value = fieldModels.create();
                }
                else {
                    parentEo.set(fieldModels.create(), pathElement.getKey());
                }
                return this;
            }
            return new EoChild(new EoChildParams(this, pathElement, value));
        }
        catch (Exception e) {
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
        EoChild parentEo = (EoChild) getEo(removePath.parent());
        String parentFieldName = removePath.getParentKey();
        if (!parentEo.hasEo(parentFieldName)) {
            throw new EoException("Could not remove entry '" + parentFieldName + "' because it is not set in '" + getModel().getModelKey() + "'");
        }
        parentEo.removeChild(parentFieldName);
        return parentEo;
    }

    public EO overWrite( final Object value, final String... path) {
        remove(path);
        return set(value, path);
    }

    @Override
    public boolean isTransient(final String fieldName) {
        return getModel().hasFieldConfig(fieldName)  ? getModel().getFieldConfig(fieldName).isTransient(): false;
    }

    @Override
    public EO getEo(String... pathString)  {
        return new Path(pathString).moveTo(this);
    }

    @Override
    public EO getEo(PathElement path)  {
        if (!hasEo(path)) {
            throw new EoException("Could not find entry for '" + path + "'.");
        }
        return eoMap.get(path.getKey());
    }

    @Override
    public Object get() {
        if (!isScalar()) return value;
        if (!isParentSet()) return value;
        return getValueFromParent();
    }

    protected EO addChildEo(EO childEo) {
        eoMap.put(childEo.getFieldKey(), childEo);
        return childEo;
    }


    @Override
    public EO mapObject(Object value)  {
        if (value == null) {
            return this;
        }
        ModelConfig valueModel = getConfigsCache().findModel(value);
        if (valueModel.isScalar()) {
            if (isScalar()) {
                setValue(value);
                return this;
            }
            else {
                if (value instanceof Long && isObject()) {
                    Long id = Long.valueOf((Long)value);
                    if (id == 0) {
                        return this;
                    }
                    value = getModel().create();
                    getModel().set(Base.ID, value, id);
                    valueModel = getModel();
                }
                else if (!((value instanceof String) && JSONToEO.jsonPattern.matcher((String)value).find())) {
                    error("Could not map scalar to container '" + this.getPath().toString() + "'");
                    return this;
                }
            }
        }
        else {
            if (isScalar()) {
                error("Could not map container to scalar '" + this.getPath().toString() + "'");
                return this;
            }
        }
        if (valueModel.isEmpty(value)) {
            return this;
        }

        if  ((value instanceof String) && JSONToEO.jsonPattern.matcher((String)value).find()) {
            new JSONToEO((String)value).createChild(this);
            return this;
        }
        //PathPattern pathPattern = params.getPathPattern();
        PathPattern pathPattern = new PathPattern(PathElement.MATCHER_ALL);
        Map keyValues = null;
        try {
            keyValues = valueModel.getKeyValues(value, pathPattern);
        } catch (Exception e) {
            error("Problem getting key values of value " + e.getMessage());
            return this;
        }
        //TODO: List<String> paths= this.pathPattern.set(myKeys);
        if (keyValues.isEmpty()) {
            //error("Empty key values of value " + sourceModel.getModelKey());
            return this;
        }

        for (Object key : keyValues.keySet()) {
            String fieldName = ScalarConverter.toString(key);
            PathElement pathElement = new PathElement(fieldName);
            Object childValue = keyValues.get(key);
            if (childValue == null && hasEo(pathElement)) {
                continue;
            }
            createChild(pathElement, childValue);
        }
        return this;
    }

    @Override
    public boolean isEoEmpty() {
        return eoMap == null || eoMap.isEmpty();
    }

    @Override
    public boolean isEmpty() {
        return getModels().isEmpty(get());
    }

    @Override
    public int sizeEo()  {
        if (eoMap==null) {
            return 0;
        }
        return this.keysEo().size();
    }

    @Override
    public int size()  {
        if (eoMap==null) {
            return 0;
        }
        return this.keys().size();
    }

    @Override
    public Set<String> keysEo()  {
        if (this.eoMap == null || get() == null) {
            return new HashSet<>();
        }
        return eoMap.keySet();
    }

    @Override
    public Set<String> keys()  {
        Set filteredSet = new LinkedHashSet();
        if (this.eoMap == null || get() == null) {
            return filteredSet;
        }
        for (String key: keysEo()) {
            if (PathElement.isParentNotSet(key)) {
                continue;
            }
            filteredSet.add(key);
        }
        return filteredSet;
    }

    public List<String> keys(String pathString)  {
        if (pathString == null || pathString.isEmpty() || ".".equals(pathString)) {
            List<String> keys = new ArrayList<>();
            keys.add(".");
            return keys;
        }
        return keys(new PathPattern(pathString));
    }

    public List<String> keys(PathPattern pathPattern)  {
        return pathPattern.filter(keysEo());
    }

    public final Map getKeyValues()  {
        if (getModel() == null) {
            throw new EoException("Null model!");
        }
        if (!isContainer()) {
            throw new EoException("Not a container model " + getModel().getModelKey() + "'!");
        }
        return getModel().getKeyValues(get(), new PathPattern("*"));
    }

    @Override
    public List<String> filterPaths(String pathString)  {
        if (pathString == null || pathString.isEmpty() || ".".equals(pathString)) {
            List<String> keys = new ArrayList<>();
            keys.add(".");
            return keys;
        }
        return filterPaths(new PathPattern(pathString), "");
    }

    public List<String> filterPaths(PathPattern pathPattern, String path)  {
        List<String> result = new ArrayList<>();
        List<String> filter = pathPattern.filter(keysEo());
        for (String key : filter) {
            if (key.equals(".config")) {
                continue;
            }
            String nextPath = path + Path.DELIMITER + key;
            nextPath = nextPath.replaceAll("^" + Path.DELIMITER, "");
            EO childAdapter = getEo(key);
            if (childAdapter == null) {// || childAdapter.isEmpty()) {
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

    /**
     * Return the keys of an Object as String. Not defined in the base implementations.
     * Mainly used for List or Maps
     *
     * @return
     * @
     */
    public Set<String> keysValue()  {
        return this.getModel().keys(this.get());

    }

    @Override
    public Set<String> getCallKeys() {
        return getCallsEo().keys();
    }

    public EO addCall(Call call) {
        if (call == null) {
            throw new EoException("Null call?!");
        }
        EO callsEo = getCallsEo();
        return callsEo.createChild(new PathElement(Integer.valueOf(callsEo.size()).toString()), call);
    }

    protected EO getCallsEo() {
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
    public boolean isRoot() {
        return false;
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


    @Override
    public EO debug(String message) {
        if (checkLevel(LogLevel.DEBUG)) {
            return log(message, getLogLevel());
        }
        return this;
    }

    @Override
    public EO info(String message) {
        if (checkLevel(LogLevel.INFO)) {
            return log(message, LogLevel.INFO);
        }
        return this;
    }

    @Override
    public EO warn(String message) {
        if (checkLevel(LogLevel.WARN)) {
            return log(message, LogLevel.WARN);
        }
        return this;
    }

    @Override
    public EO warn(String message, Exception e) {
        if (checkLevel(LogLevel.WARN)) {
            return log( message, LogLevel.WARN, e);
        }
        return this;
    }

    @Override
    public EO error(String message) {
        if (checkLevel(LogLevel.ERROR)) {
            return log( message, LogLevel.ERROR);
        }
        return this;
    }

    @Override
    public EO error(String message, Exception e) {
        if (checkLevel(LogLevel.ERROR)) {
            return log( message, LogLevel.ERROR, e);
        }
        return this;
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
        return (List<String>)getLogEo().get();
     }

    @Override
    public String getLog() {
        if (!hasEo(PathElement.LOGS)) {
            return "";
        }
        return getLogList().stream().collect(Collectors.joining("\n"));
    }


    private EO log(String message, LogLevel logLevel) {
        if (message == null) {
            return this;
        }
        setErrorLevel(logLevel);
        EO logEo = getLogEo();
        PathElement logElement = new PathElement(Integer.valueOf(logEo.size()).toString());
        logEo.createChild(logElement, logLevel.name() + " - " + LocalDateTime.now().toString() + " - " + message);
        return this;
    }

    private EO log(String message, LogLevel logLevel, Exception e) {
        log( message + ": " + e.getMessage(), logLevel);
        return this;
    }

    // LOG_LEVEL

    private boolean checkLevel(LogLevel messageLevel) {
        return getLogLevel().ordinal() <= messageLevel.ordinal();
    }

    @Override
    public LogLevel getLogLevel() {
        if (hasLogLevel()) {
            return (LogLevel) get(PathElement.LOG_LEVEL);
        }
        if (isRoot()) {
            return LogLevel.WARN;
        }
        return getParent().getLogLevel();
    }


    @Override
    public EO setLogLevel(LogLevel logLevel) {
        if (!hasEo(PathElement.LOG_LEVEL)) {
            createChild(new PathElement(PathElement.LOG_LEVEL), logLevel);
        }
        return this;
    }

    public boolean hasLogLevel() {
        return hasEo(new PathElement(PathElement.LOG_LEVEL));
    }


    @Override
    public boolean hasErrors() {
        return getErrorLevel() == LogLevel.ERROR;
    }

    @Override
    public LogLevel getErrorLevel() {
        return (LogLevel)getErrorLevelEo().get();
    }

    public EO getErrorLevelEo() {
        EO rootEo = getRoot();
        if (!rootEo.hasEo(PathElement.ERROR_LEVEL)) {
            return rootEo.createChild(new PathElement(PathElement.ERROR_LEVEL), LogLevel.DEBUG);
        }
        return rootEo.getEo(PathElement.ERROR_LEVEL);
    }

    private void setErrorLevel(LogLevel messageLogLevel) {
        if (getErrorLevel().ordinal() <= messageLogLevel.ordinal()) {
            ((EoChild)getErrorLevelEo()).setValue(messageLogLevel);
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

    public boolean hasRoles() {
        return getRoot().hasRoles();
    }

    @Override
    public EOConfigsCache getConfigsCache() {
        return getRoot().getConfigsCache();
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

    public ModelConfig getModel() {
        return getModels().getModel();
    }

    public Models getChildModels(PathElement pathElement) {
        return getModels().getChildModels(this, pathElement);
    }

    public Class getModelClass() {
        return getModels().getModelClass();
    }

    protected boolean hasChildModel() {
        return getModels().hasChildModel();
    }


    /**
     * true if is a list. Will be overwritten in the list type adapters.
     *
     * @return always true
     */

    public boolean isParentSet() {
        return parentEo!=null && PathElement.isParentSet(fieldKey);
    }

    @Override
    public boolean isChanged() {
        return changed;
    }

    @Override
    public boolean isList() {
        return getModel().isList();
    }

    @Override
    public boolean isObject() {
        return getModel().isObject();
    }

    @Override
    public boolean isScalar() {
        return getModel().isScalar() || getModels().isEnum();
    }

    @Override
    public boolean isMap() {
        return getModel().isMap();
    }

    @Override
    public boolean isNull() {
        return getModel().isNull();
    }

    @Override
    public boolean isContainer() {
        return !isScalar();
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

    private boolean hasSerializationType() {
        return getRoot().hasEo(PathElement.SERIALIZATION_TYPE);
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
            getSerializationTypeEo().setValue(serializationType);
        }
        return this;
    }

    @Override
    public String toString() {
        return "(" + getModels().toString() + ") " + getPathAsString() + " -> " + get().toString() + "";
        //return toString(JSONSerializationType.EO);
    }

    @Override
    public String toString(JSONSerializationType serializationType) {
        if (this == null) {
            return "Not instanciated";
        }
        if (isScalar()) {
            return get().toString();
        }
        try {
            return new EOToJSON()
                    .setSerializationType(serializationType)
                    .toJSON(this);
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
        for (String key: otherList) {
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
            }
            else {
                builder.append(getPathAsString() + key + ": " + getEo(key).getModelClass().getSimpleName() + "<> null \n");

                continue;
            }
            if (childEo == null && otherChildEo == null) {
                builder.append(Path.DELIMITER + getPath() + " - " + key);
                builder.append("\nboth null!\n");
                continue;
            }
            else if (childEo == null && otherChildEo != null) {
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
            ((EoChild)childEo).compare(builder, otherChildEo);
        }
    }
}
