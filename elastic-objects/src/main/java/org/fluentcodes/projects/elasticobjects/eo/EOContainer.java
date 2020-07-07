package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.EoException;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.config.ModelInterface;
import org.fluentcodes.projects.elasticobjects.executor.CallExecutor;
import org.fluentcodes.projects.elasticobjects.executor.ExecutorList;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.paths.PathElement;
import org.fluentcodes.projects.elasticobjects.paths.PathPattern;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.io.File;
import java.util.*;

/**
 * Offers serialized setter and getter for java objects
 *
 * @author Werner Diwischek
 * @since 10.10.2015
 */

public class EOContainer implements EO {
    private static final Logger LOG = LogManager.getLogger(EOContainer.class);
    private final EORoot rootAdapter;
    private final EOContainer parentAdapter;
    private final String parentFieldName;
    private final Models models;
    private LogLevel logLevel;

    private Object object;

    private boolean changed = false;
    private boolean insert = false;
    private Boolean empty = true;

    private Map<String, EO> childMap;

    protected EOContainer(Models models, LogLevel logLevel)  {
        rootAdapter = (EORoot) this;
        parentAdapter = null;
        parentFieldName = null;
        this.logLevel = logLevel;
        this.models = models;
        if (!isScalar()) {
            this.object = this.models.create();
            childMap = new LinkedHashMap<>();
        }
        else {
            childMap = null;
        }
    }

    protected EOContainer(EOContainer parent, Path path, Object value, Class... classes) {
        this.rootAdapter = parent.getRoot();
        this.parentAdapter = parent;
        this.parentFieldName = path.getFirstEntry();
        this.logLevel = parent.getLogLevel();
        if (path.hasChild()) {
            this.models = parent.getModels().getChildModels(path);
            childMap = new LinkedHashMap<>();
            this.object = this.models.create();
            EOContainer eo = new EOContainer(this, path.getChildPath(), value);
        }
        else {
            this.models = parent.getModels().getChildModels(path, value);
            if (!isScalar()) {
                childMap = new LinkedHashMap<>();
                this.object = this.models.create();
                mapObject(value);
            }
            else {
                this.object = value;
            }
        }
        parentAdapter.setChild(path.getFirstEntry(), this);
    }

    public void mapObject(final Object value)  {
        if (value == null) {
            return;
        }

        ModelInterface sourceModel = getConfigsCache().findModel(value);
        if (sourceModel.isScalar()) {
            if ((value instanceof File)) {
                return;
            }
            if ((value instanceof String) && JSONToEO.jsonPattern.matcher((String)value).find()) {
                new JSONToEO((String)value).createChild(this);
                return;
            }

            set(value);
            return;
        }
        //PathPattern pathPattern = params.getPathPattern();
        PathPattern pathPattern = new PathPattern(PathElement.MATCHER_ALL);
        Map keyValues = null;
        try {
            keyValues = sourceModel.getKeyValues(value, pathPattern);
        } catch (Exception e) {
            throw (e);
        }
        //TODO: List<String> paths= this.pathPattern.set(myKeys);
        if (keyValues.isEmpty()) {
            return;
        }

        for (Object key : keyValues.keySet()) {
            String fieldName = ScalarConverter.toString(key);
            // when mapping model is a list but key is not a parseable integer use size
            if (isList()) {
                try {
                    Integer.parseInt(fieldName);
                } catch (Exception e) {
                    fieldName = new Integer(this.size()).toString();
                }
            }
            Object childValue = keyValues.get(key);
            if (childValue == null) {
                continue;
            }
            setPathValue(fieldName, childValue);
        }
    }

    public void addCall(CallExecutor callExecutor)  {
        Path path = this.getPath();
        callExecutor.setPath(path.directory());
        getRoot().addCall(callExecutor);
    }

    /**
     * If the object has a non null object.
     *
     * @return true if the object is not null.
     * TODO implement correct isEmpty in object.
     */
    @Override
    public boolean isEmpty() {
        if (this.getEmpty() != null) {
            return this.getEmpty();
        }
        if (this.get() == null) {
            this.setEmpty(true);
            return true;
        }
        try {
            this.setEmpty(getModel().isEmpty(this.get()));
            return getEmpty();
        } catch (Exception e) {
            warn("Exception in isEmpty: " + e.getMessage());
            return false;
        }

    }


    public int adaptersize() {
        return this.childMap.keySet().size();
    }

    public int valuesize() {
        try {
            return getModel().size(get());
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int size()  {
        if (!isContainer()) {
            return 0;
        }
        return this.keys().size();
    }

    @Override
    public int size(final String path)  {
        return (this.childKeys(path).size());
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
        return pathPattern.filter(keys());
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
        List<String> filter = pathPattern.filter(keys());
        for (String key : filter) {
            if (key.equals(".config")) {
                continue;
            }
            String nextPath = path + Path.DELIMITER + key;
            nextPath = nextPath.replaceAll("^" + Path.DELIMITER, "");
            EO childAdapter = getChild(key);
            if (childAdapter == null) {// || childAdapter.isEmpty()) {
                continue;
            }
            PathPattern childPathPattern = pathPattern.getPathList(key);
            if ((!childPathPattern.isEmpty() || childPathPattern.isAll()) && !childAdapter.isScalar()) {
                List<String> keys = ((EOContainer) childAdapter).filterPaths(childPathPattern, nextPath);
                result.addAll(keys);
            } else {
                result.add(nextPath);
            }
        }
        return result;
    }

    @Override
    public Set<String> keys()  {
        if (!isContainer()) {
            return new HashSet<>();
        }
        if (this.childMap == null) {
            return new HashSet<>();
        }
        if (get() == null) {
            return new HashSet<>();
        }
        return this.getModel().keys(get());
    }

    public Set<String> childKeys(final String path)  {
        return getChild(path).keys();
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

    /**
     * Sets the object of the embedded object. Implemtented in the appropriate child implementations.
     *
     * @param fieldName
     * @param source
     * @
     */
    protected void setValue(final String fieldName, Object source) {
        if (fieldName == null) {
            warn("Null fieldname for object " + source.getClass().getSimpleName());
            return;
        }
        try {
            getModel().set(fieldName, get(), source);
        } catch (EoException e) {
            warn("Could not setValue for fieldName '" + fieldName + "': " + e.getMessage());
        }
    }

    public boolean hasChild(Path path) {
        return childMap!=null && childMap.containsKey(path.getFirstEntry());
    }

    public EO setPathValue(final String pathString) {
        return setPathValue(pathString, null);
    }

    public EO setPathValue(final String pathString, Object value) {
        return setPathValue(pathString, value, null);
    }

    public EO setPathValue(final String pathString, Object source, Class... classes) {
        if (pathString == null) {
            warn("Null fieldName for object " + source.getClass().getSimpleName());
            return this;
        }
        Path path = new Path(pathString);
        return setPathValue(path, source);
    }

    protected EO setPathValue(final Path path, Object source, Class... classes) {
        if (path.isEmpty()) {
            warn("Empty fieldName for object " + source.getClass().getSimpleName());
            return this;
        }
        if (!getModel().hasKey(path)) {
            warn("No field found for  " + path.getFirstEntry());
            return this;
        }
        if (hasChild(path)) {
            return ((EOContainer)childMap.get(path.getFirstEntry())).setPathValue(path.getChildPath(), source);
        }
        else {
            EOContainer eo = new EOContainer(this, path, source, classes);
            return eo;
        }
    }

    protected Object getValue(final String fieldName) {
        try {
            return getModel().get(fieldName, get());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean remove(final String fieldName) {
        try {
            getModel().remove(fieldName, get());
            this.childMap.remove(fieldName);
        }
        catch (Exception e) {
            warn("Could not remove: " + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Directly removes the object of the operator
     *
     * @ On null source
     */
    @Override
    public EO remove()  {
        if (getParentAdapter() == null) {
            throw new EoException("Could not remove when no paren is add! Problem removing rootAdapter adapter");
        }
        EOContainer returnAdapter = getParentAdapter();
        getParentAdapter().remove(getParentKey());
        return returnAdapter;
    }

    @Override
    public Object get(final String pathString) {
        //throw new eoException("Basic find method should be overwritten");
        if (pathString == null || pathString.isEmpty()) {
            return get();
        }
        String special = null;
        String path = pathString;
        if (pathString.endsWith(EO_STATIC._PARENT_KEY)) {
            path = pathString.replaceAll(EO_STATIC._PARENT_KEY + "$", "");
            special = EO_STATIC._PARENT_KEY;
        } else if (pathString.endsWith(EO_STATIC._VALUE)) {
            path = pathString.replaceAll(EO_STATIC._VALUE + "$", "");
            //special = EO_STATIC._VALUE;
        }
        EO child = null;
        try {
            child = getChild(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (child == null) {
            debug("Could not find entry for path '" + path + "'.");
            return null;
        }
        if (special == null) {
            return child.get();
        }
        switch (special) {
            case EO_STATIC._PARENT_KEY:
                return child.getParentKey();
            default:
                return (child.get());
        }
    }


    protected void removeChildEO(Object value)  {
        if (this.isScalar()) {
            this.childMap = null;
        } else {
            this.childMap = new LinkedHashMap<>();
        }
        set(value);
    }

    @Override
    public EO getChild(String pathString)  {
        if (pathString == null || pathString.equals("") || pathString.equals("./") || pathString.equals(".")) {
            return this;
        }
        if (pathString.equals(Path.DELIMITER)) {
            return getRoot();
        }
        if (pathString.startsWith(Path.DELIMITER) && this != getRoot()) {
            return getRoot().getChild(pathString);
        }
        return getChild(new Path(pathString));
    }

    protected EO getChild(Path path)  {
        if (path == null || path.isEmpty()) {
            return this;
        }
        String firstEntry = path.getFirstEntry();
        if (firstEntry.equals("..")) {
            return getParentAdapter().getChild(path.getChildPath());
        }
        else if (path.hasChild()) {
            if (childMap.containsKey(firstEntry)) {
                return ((EOContainer)childMap.get(firstEntry)).getChild(path.getChildPath());
            }
            else {
                throw new EoException("Could not find entry for " + firstEntry);
            }
        }
        return childMap.get(firstEntry);
    }

    /**
     * Add the adapter with fieldName to childMap.
     *
     * @param fieldName The fieldName
     * @param child     the child eo
     */
    protected void setChild(final String fieldName, final EO child) {
        if (fieldName == null) {
            warn("FieldName ist null setting child ObjectsBuilder! ");
            return;
        }
        if (childMap == null) {
            warn("Could not add a child with fieldName '" + fieldName + "'to a scalar parent!");
            return;
        }
        if (this.childMap.get(fieldName) == child) {
            return;
        }
        this.childMap.put(fieldName, child);
        try {
            getModel().set(fieldName, get(), child.get());
        } catch (EoException e) {
            warn("Could not setValue for fieldName '" + fieldName + "': " + e.getMessage());
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
            list = new ArrayList<>(keys());
        } catch (Exception e) {
            e.printStackTrace();
            builder.append(getPath() + ":");
            builder.append("\nProblem getting keys!\n");
            return;
        }
        for (String key : list) {
            EO nextAdapter = null;
            try {
                nextAdapter = this.getChild(key);
            } catch (Exception e) {
                builder.append(getPath());
                builder.append("\nProblem getting child for " + key + "!" + e.getMessage() + "\n");
                continue;
            }
            EO nextOther = null;
            try {
                nextOther = other.getChild(key);
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
                        builder.append("null != " + getPath() + "/" + key + " with size= " + nextOther.size() + "\n");
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
                        builder.append(getPath() + "/" + key + " with size= " + nextAdapter.size() + " != null\n");
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

    @Override
    public Object get() {
        return this.object;
    }

    private void set(final Object source)  {
        if (this.object != null && this.object == source) {
            return;  // the same object
        }
        if (object != null && source != null) {
            //throw new eoException("Not allowed to set a null source");
            if (this.object.hashCode() == source.hashCode()) {
                return;
            }
            info("Existing Object is overwritten! " + getPath() + ".");
        }
        this.object = source;
        //empty = this.models.isEmpty(source);
        changed = true;
        setParent();
    }

    protected void setParent() {
        if (parentAdapter == null) {
            return;
        }
        Object value = parentAdapter.getValue(parentFieldName);
        if (this.object != null && value == this.object) {
            return;
        }
        if (value != null && isScalar()) {
            if (value.equals(this.object)) {
                return;
            }
        }
        parentAdapter.setEmpty(false);
        parentAdapter.setChanged(true);
        parentAdapter.setValue(getParentKey(), this.object);
        parentAdapter.setChild(parentFieldName, this);

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

    public final boolean isChanged() {
        return this.changed;
    }

    protected final void setChanged(final boolean changed) {
        this.changed = this.changed || changed;
    }

    public boolean isInsert() {
        return insert;
    }

    public void setInsert(boolean insert) {
        this.insert = insert;
    }

    protected final void initChanged() {
        this.changed = false;
    }

    /**
     * Returns the stored rootAdapter of the current adapter.
     *
     * @return The rootAdapter adapter instance var
     */
    public EORoot getRoot() {
        return rootAdapter;
    }

    public boolean isRoot() {
        return false;
    }

    public Path getPath() {
        Path path = new Path(Path.DELIMITER);
        getPath(path);
        return path;
    }

    public void getPath(Path path) {
        if (parentAdapter != null) {
            path.prependPath(this.parentFieldName);
            parentAdapter.getPath(path);
        }
    }

    public String getPathAsString() {
        StringBuilder builder = new StringBuilder();
        getPathAsString(builder);
        return builder.toString();
    }

    protected void getPathAsString(StringBuilder builder) {
        if (parentFieldName != null && !parentFieldName.isEmpty()) {
            builder.insert(0, this.parentFieldName);
            builder.insert(0, Path.DELIMITER);
            parentAdapter.getPathAsString(builder);
        }
    }

    public EOExtension getAdapterExtension() {
        return getRoot().getAdapterExtension();
    }

    /**
     * Returns the params of the stored rootAdapter of the current adapter.
     *
     * @return The params of the rootAdapter adapter instance var
     */
    public LogLevel getLogLevel() {
        return logLevel;
    }

    public LogLevel getErrorLevel() {
        return getRoot().getErrorLevel();
    }

    public String getLog() {
        return getRoot().getLog();
    }

    @Override
    public void debug(String message) {
        if (this.logLevel == null) {
            getRoot().debug(getPathAsString() + ": " + message);
        } else if (checkLevel(LogLevel.DEBUG)) {
            getRoot().log(getPathAsString() + ": " + message, LogLevel.DEBUG);
            return;
        }
    }

    @Override
    public void info(String message) {
        if (this.logLevel == null) {
            getRoot().info(getPathAsString() + ": " + message);
        } else if (checkLevel(LogLevel.INFO)) {
            getRoot().log(getPathAsString() + ": " + message, LogLevel.INFO);
        }
    }

    @Override
    public void warn(String message) {
        if (this.logLevel == null) {
            getRoot().warn(getPathAsString() + ": " + message);
        } else if (checkLevel(LogLevel.WARN)) {
            getRoot().log(getPathAsString() + ": " + message, LogLevel.WARN);
        }
    }

    @Override
    public void error(String message) {
        if (this.logLevel == null) {
            getRoot().error(getPathAsString() + ": " + message);
        } else if (checkLevel(LogLevel.ERROR)) {
            getRoot().log(getPathAsString() + ": " + message, LogLevel.ERROR);
        }
    }

    @Override
    public void warn(String message, Exception e) {
        if (this.logLevel == null) {
            getRoot().warn(getPathAsString() + ": " + message, e);
        } else if (checkLevel(LogLevel.WARN)) {
            getRoot().log(getPathAsString() + ": " + message, LogLevel.WARN);
        }
    }

    @Override
    public void error(String message, Exception e) {
        if (this.logLevel == null) {
            getRoot().error(getPathAsString() + ": " + message, e);
        } else if (checkLevel(LogLevel.ERROR)) {
            getRoot().log(getPathAsString() + ": " + message, LogLevel.ERROR);
        }
    }

    @Override
    public boolean hasErrors() {
        return
                getRoot().hasErrors();
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

    @Override
    public boolean hasRoles() {
        return getRoot().hasRoles();
    }

    protected boolean checkLevel(LogLevel messageLevel) {
        if (logLevel == null) {
            return false;
        }
        return logLevel.ordinal() <= messageLevel.ordinal();
    }

    public Boolean getEmpty() {
        return empty;
    }

    public EOConfigsCache getConfigsCache() {
        return getRoot().getConfigsCache();
    }

    public ExecutorList getCalls() {
        return getRoot().getCalls();
    }

    @Override
    public boolean hasCalls() {
        return this.hasCalls();
    }

    public void executeCalls() {
        getRoot().executeCalls();
    }

    public boolean isCheckObjectReplication() {
        return getRoot().isCheckObjectReplication();
    }

    public void setCheckObjectReplication(boolean checkObjectReplication) {
        getRoot().setCheckObjectReplication(checkObjectReplication);
    }

    public JSONSerializationType getSerializationType() {
        return getRoot().getSerializationType();
    }

    /**
     * Returns the object class stored in the adapter.
     *
     * @return The object class instance object of the adapter.
     */
    public Models getModels() {
        return models;
    }

    protected void setModels(Models newModels)  {
        if (newModels.isEmpty()) {
            info("Empty classes!" + getPathAsString());
            return;
        }
        if (!this.isEmpty()) {
            warn("Could not add the models value on a nonempty adapter!" + getPathAsString());
            return;
        }
        try {
            models.setClasses(newModels);
        } catch (Exception e) {
            warn("Could not set " + e.getMessage());
        }
    }

    /**
     * Returns the object class stored in the adapter.
     *
     * @return The object class instance object of the adapter.
     */
    public ModelInterface getModel() {
        return models.getModel();
    }

    public Class getModelClass() {
        return models.getModelClass();
    }

    protected boolean hasChildModel() {
        return models.hasChildModel();
    }


    /**
     * true if is a list. Will be overwritten in the list type adapters.
     *
     * @return always true
     */

    public boolean isList() {
        return getModel().isList();
    }

    public boolean isObject() {
        return getModel().isObject();
    }

    public boolean isScalar() {
        return getModel().isScalar();
    }

    public boolean isMap() {
        return getModel().isMap();
    }

    public boolean hasDefaultMap() {
        return getModels().hasDefaultMap();
    }

    public boolean isChildTyped() {
        return isObject() || hasChildModel();
    }

    public boolean isNull() {
        return getModel().isNull();
    }

    /**
     * The method returns true for all adapters beside scalar type adapters.
     *
     * @return true if adapters object is a container.
     */
    public boolean isContainer() {
        return !isScalar();
    }

    /**
     * If the object has a non null object.
     *
     * @return true if the object is not null.
     */
    /*public boolean isEmpty() {
        if (this.empty != null) {
            return this.empty;
        }
        if (this.object != null) {
            this.empty = false;
            return true;
        }
        this.empty = true;
        return false;
    }*/

    public void setEmpty(Boolean empty) {
        if (this.empty != null && this.empty == false) {
            return;
        }
        this.empty = empty;
    }

    /**
     * Gets the fieldName to access the adapter in the  parent adapter object.
     *
     * @return The fieldName of the parent adapters object.
     */
    public String getParentKey() {
        return parentFieldName;
    }

    public EOContainer getParentAdapter() {
        return parentAdapter;
    }

    public boolean hasParent() {
        return parentAdapter != null;
    }

    @Override
    public String toString() {
        if (this == null) {
            return "Not instanciated";
        }
        try {
            return new EOToJSON().toJSON(this);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error getSerialized " + getPath() + ": " + e.getMessage();
        }
    }
}
