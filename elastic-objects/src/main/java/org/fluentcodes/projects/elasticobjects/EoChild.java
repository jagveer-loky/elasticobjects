package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.Model;
import org.fluentcodes.projects.elasticobjects.models.ModelInterface;
import org.fluentcodes.projects.elasticobjects.models.Models;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class EoChild implements EO {
    private static final Logger LOG = LogManager.getLogger(EoChild.class);
    private static final List<Class> DEFAULT_CLASSES = Arrays.asList(new Class[] {Map.class, LinkedHashMap.class, String.class, Boolean.class, Integer.class});
    private PathElement pathElement;
    private Map<String, EO> eoMap;

    protected EoChild()  {
        eoMap = new LinkedHashMap<>();

    }

    protected EoChild(PathElement pathElement) {
        eoMap = new LinkedHashMap<>();
        if (pathElement == null) {
            throw new EoException("Strange null pathElement!");
        }
        if (!pathElement.hasModels()) {
            throw new EoException("No models defined for " + pathElement.toString());
        }
        this.pathElement = pathElement;
        this.pathElement.addToParent(this);
    }

    protected void setPathElement(PathElement pathElement) {
        pathElement.resolve(this, null);
        this.pathElement = pathElement;
    }

    protected void setRootModels(final String models) {
        if (!isRoot()) {
            throw new EoException("No Root element, no models could be changed");
        }
        if (!isEmpty()) {
            throw new EoException("Non empty root element, no models could be changed");
        }
        pathElement.setRootModels(this, models);
    }

    public void setValue(Object value) {
        if (value == null) {
            return;
        }
        ModelInterface valueModel = getConfigsCache().findModel(value);
        if (valueModel.isScalar()) {
            if (isScalar()) {
                setValueChecked(value);
            }
            else {
                error("Could not map scalar to container");
            }
        }
        else {
            if (isScalar()) {
                error("Could not map container to scalar");
            }
            else {
                setValueChecked(value);
            }
        }
    }

    private void setValueChecked(Object value) {
        this.pathElement.setValue(value);
    }

    protected void setValueChecked(String key, Object value) {
        getModel().set(key, this.get(), value);
    }

    public String getParentKey() {
        if (hasPathElement()) {
            return pathElement.getKey();
        }
        else {
            return "";
        }
    }

    public String getParentKeyWithModels() {
        if (isRoot()) {
            return "";
        }
        if (getParentEo().isObject()) {
            return getParentKey();
        }
        if (DEFAULT_CLASSES.contains(getModelClass())) {
            return getParentKey();
        }
        return "(" + getModels().toString() + ")" + getParentKey();
    }

    public boolean hasPathElement() {
        return pathElement != null && pathElement.hasModels();
    }

    public EoChild getParentEo() {
        return (EoChild) pathElement.getParent();
    }

    @Override
    public EO getParent() {
        return pathElement.getParent();
    }

    @Override
    public boolean hasParent() {
        return pathElement.hasParent();
    }

    protected boolean hasEo(final Path path) {
        return hasEo(path.getFirstPathElement());
    }

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
        return new Path(pathStrings).moveTo(this).get();
    }

    protected void removeChild(String parentFieldName) {
        this.eoMap.remove(parentFieldName);
        getModel().remove(parentFieldName, get());
    }

    @Override
    public EO remove(final String... path) {
        Path removePath = new Path(path);
        EoChild parentEo = (EoChild) getEo(removePath.parent());
        if (parentEo == null) {
            return this;
        }
        String parentFieldName = removePath.getParentKey();
        parentEo.removeChild(parentFieldName);
        return parentEo;
    }

    public EO overWrite( final Object value, final String... path) {
        remove(path);
        return set(value, path);
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
        return pathElement.getValue();
    }

    public EO addEo(EO eo) {
        eoMap.put(eo.getParentKey(), eo);
        if (((EoChild)eo).isParentSet()) {
            this.setValueChecked(eo.getParentKey(), eo.get());
        }
        return eo;
    }

    /**
     * Add the adapter with fieldName to childMap.
     *
     * @param fieldName The fieldName
     * @param child     the child eo
     */
    protected void setEo(final PathElement pathElement, final EO child) {
        final String fieldName = pathElement.getKey();
        if (fieldName == null) {
            throw new EoException("FieldName ist null setting child ObjectsBuilder! ");
        }
        if (eoMap == null) {
            throw new EoException("Could not add a child with fieldName '" + fieldName + "'to a scalar parent!");
        }
        String fieldKey = fieldName;
        if (getModels().isList() && !PathElement.isParentNotSet(fieldKey)) {
            try {
                Integer.parseInt(fieldName);
            }
            catch(NumberFormatException e) {
                fieldKey= new Integer(((List)get()).size()).toString();
            }
        }
        if (this.eoMap.containsKey(fieldKey) && this.eoMap.get(fieldKey) == child) {
            EO stored = this.eoMap.get(fieldKey);
            return;
        }
        this.eoMap.put(fieldKey, child);
        if (PathElement.isParentNotSet(fieldKey)) {
            return;
        }
        try {
            getModel().set(fieldKey, get(), child.get());
        } catch (EoException e) {
            warn("Could not setValue for fieldName '" + fieldName + "': " + e.getMessage());
        }
    }

    @Override
    public EO mapObject(Object value)  {
        if (value == null) {
            return this;
        }
        ModelInterface valueModel = getConfigsCache().findModel(value);
        if (valueModel.isScalar()) {
            if (isScalar()) {
                setValueChecked(value);
                return this;
            }
            else {
                if (value instanceof Long && isObject()) {
                    Long id = Long.valueOf((Long)value);
                    if (id == 0) {
                        return this;
                    }
                    value = getModel().create();
                    getModel().set(Model.ID, value, id);
                    valueModel = getModel();
                }
                else if (!((value instanceof String) && JSONToEO.jsonPattern.matcher((String)value).find())) {
                    error("Could not map scalar to container");
                    return this;
                }
            }
        }
        else {
            if (isScalar()) {
                error("Could not map container to scalar");
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
            EO child = null;
            if (hasEo(pathElement)) {
                child = getEo(pathElement);
            }
            Object childValue = keyValues.get(key);
            if (childValue == null && hasEo(pathElement)) {
                continue;
            }
            pathElement.resolve(this, childValue);
            child = new EoChild(pathElement);
            child.mapObject(childValue);
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
        EO callEo =  new PathElement(Integer.valueOf(callsEo.size()).toString(), callsEo, call)
                .buildEo();
        return callEo.mapObject(call);
    }

    protected boolean addCall(EO callEo) {
        if (callEo == null) {
            return false;
        }
        addCall((Call) callEo.get());
        return true;
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
        return PathElement.OF_CALLS(rootEo).buildEo();
    }

    protected boolean hasCalls() {
        return getRoot().hasEo(PathElement.CALLS);
    }

    @Override
    public boolean execute() {
        return getRoot().execute();
    }

    /*@Override
    public void compare(final StringBuilder builder, final EO other) {
        if (!this.isScalar()) {
            builder.append("N");
            return;
        }
        try {
            if (!ScalarComparator.compare(get(), other.get())) {
                builder.append(getPath());
                builder.append(" = ");
                builder.append(get());
                builder.append(": != ");
                builder.append(other.get());
                builder.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public EoRoot getRoot() {
        if (!isRoot()) {
            return getParentEo().getRoot();
        }
        return (EoRoot) this;
    }

    @Override
    public boolean isRoot() {
        return this instanceof EoRoot;
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
        builder.insert(0, getParentKey());
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
        if (rootEo == null) {
            throw new EoException("No root found");
        }
        if (!((EoChild)rootEo).hasEo(PathElement.LOGS)) {
            return addEo(new EoChild(PathElement.OF_LOGS(rootEo)));
        }
        return rootEo.getEo(PathElement.LOGS);
    }

    @Override
    public String getLog() {
        if (!hasEo(PathElement.LOGS)) {
            return "";
        }
        List<String> logs = (List<String>)getLogEo().get();
        if (logs == null|| logs.isEmpty()) {
            return "";
        }
        return logs.stream().collect(Collectors.joining("\n"));
    }


    private EO log(String message, LogLevel logLevel) {
        if (message == null) {
            return this;
        }
        setErrorLevel(logLevel);
        EO logEo = getLogEo();
        PathElement logElement = new PathElement(Integer.valueOf(logEo.size()).toString(), String.class);
        logElement.resolve(logEo, logLevel.name() + " - " + LocalDateTime.now().toString() + " - " + message);
        ((EoChild)logEo).addEo(new EoChild(logElement));
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
            PathElement.OF_LOG_LEVEL(this, logLevel).buildEo();
        }
        ((EoChild)getEo(PathElement.LOG_LEVEL)).setValueChecked(logLevel);
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
        if (!((EoChild)rootEo).hasEo(PathElement.ERROR_LEVEL)) {
            return PathElement.OF_ERROR_LEVEL(rootEo, LogLevel.DEBUG).buildEo();
        }
        return rootEo.getEo(PathElement.ERROR_LEVEL);
    }

    private void setErrorLevel(LogLevel messageLogLevel) {
        if (getErrorLevel().ordinal() <= messageLogLevel.ordinal()) {
            ((EoChild)getErrorLevelEo()).setValueChecked(messageLogLevel);
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
        return pathElement.getModels();
    }
    public ModelInterface getModel() {
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
        return pathElement.isParentSet();
    }

    @Override
    public boolean isChanged() {
        return pathElement.isChanged();
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
    public boolean hasDefaultMap() {
        return getModels().hasDefaultMap();
    }

    @Override
    public boolean isChildTyped() {
        return isObject() || hasChildModel();
    }

    @Override
    public boolean isNull() {
        return getModel().isNull();
    }

    @Override
    public boolean isContainer() {
        return !isScalar();
    }
    @Override
    public boolean isToSerialize(JSONSerializationType serializationType) {
        if (get() == null) {
            return false;
        }
        if (isContainer()) {
            if (serializationType == JSONSerializationType.EO) {
                if (isEoEmpty()) {
                    return false;
                }
            }
            else if (isEmpty()) {
                    return false;
            }
        }
        return true;
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
        new EoChild(PathElement.OF_SERIALIZATION_TYPE(getRoot(), serializationType));
        return true;
    }

    @Override
    public EO setSerializationType(JSONSerializationType serializationType) {
        if (!initSerializationTypeEo(serializationType)) {
            getSerializationTypeEo().setValueChecked(serializationType);
        }
        return this;
    }

    @Override
    public String toString() {
        if (this == null) {
            return "Not instanciated";
        }
        if (isScalar()) {
            return get().toString();
        }
        try {
            return new EOToJSON().toJSON(this);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error getSerialized " + getPath() + ": " + e.getMessage();
        }
    }


    @Override
    public void compare(final StringBuilder builder, final EO other) {
        if (this.isNull()) {
            return;
        }
        if (this.isScalar()) {
            //super.compare(builder, other);
            return;
        }
        List<String> list;
        try {
            list = new ArrayList<>(keysEo());
        } catch (Exception e) {
            e.printStackTrace();
            builder.append(getPath() + ":");
            builder.append("\nProblem getting keys!\n");
            return;
        }
        for (String key : list) {
            EO nextAdapter = null;
            try {
                nextAdapter = this.getEo(key);
            } catch (Exception e) {
                builder.append(getPath());
                builder.append("\nProblem getting child for " + key + "!" + e.getMessage() + "\n");
                continue;
            }
            EO nextOther = null;
            try {
                nextOther = other.getEo(key);
            } catch (Exception e) {
                builder.append(getPath() + ": ");
                builder.append("\nProblem getting child for " + key + "!" + e.getMessage() + "\n");
                continue;
            }
            if (nextAdapter == null && nextOther == null) {
                builder.append(getPath() + " - " + key);
                builder.append("\nboth null!\n");
                continue;
            } else if (nextAdapter == null && nextOther != null) {
                try {
                    if (nextOther.isContainer()) {
                        builder.append("null != " + getPath() + "/" + key + " with size= " + nextOther.sizeEo() + "\n");
                        continue;
                    } else {
                        builder.append("null != " + getPath() + "/" + key + " = " + nextOther.get() + "\n");
                        continue;
                    }
                } catch (Exception e) {
                    builder.append(getPath() + "/" + key + ":");
                    builder.append("\n" + e.getMessage() + "\n");
                    e.printStackTrace();
                    continue;
                }
            } else if (nextAdapter != null && nextOther == null) {
                try {
                    if (nextAdapter.isContainer()) {
                        builder.append(getPath() + "/" + key + " with size= " + nextAdapter.sizeEo() + " != null\n");
                        continue;

                    } else {
                        builder.append(getPath() + "/" + key + " = " + nextAdapter.get() + " != null\n");
                        continue;
                    }
                } catch (Exception e) {
                    builder.append(getPath() + "/" + key + ":");
                    builder.append("\n" + e.getMessage() + "\n");
                    e.printStackTrace();
                    continue;
                }
            }
            nextAdapter.compare(builder, nextOther);
        }
    }
}
