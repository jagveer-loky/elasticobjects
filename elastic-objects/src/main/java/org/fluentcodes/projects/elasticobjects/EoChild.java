package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.ModelInterface;
import org.fluentcodes.projects.elasticobjects.models.Models;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.paths.PathElement;
import org.fluentcodes.projects.elasticobjects.paths.PathPattern;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.*;

/**
 * Offers serialized setter and getter for java objects
 *
 * @author Werner Diwischek
 * @since 10.10.2015
 */

public class EoChild implements EO {
    private static final Logger LOG = LogManager.getLogger(EoChild.class);
    private EoChild eoParent;
    private String parentKey;
    private Models models;
    private Object object;

    private boolean changed = false;

    private Map<String, EO> eoMap;

    protected EoChild()  {
        eoParent = null;
        parentKey = null;
        eoMap = new LinkedHashMap<>();
    }

    protected EoChild(EoChild eoParent, PathElement pathElement, Object value) {
        if (eoParent.getModels() == null) {
            throw new EoInternalException("Null model!!!");
        }
        eoMap = new LinkedHashMap<>();
        if (pathElement.isParentNotSet() && value!=null) {
            this.models = new Models(eoParent.getConfigsCache(), value.getClass());
        }
        else if (eoParent.getSerializationType() == JSONSerializationType.EO) {
            this.models = eoParent.getModels().createChildModels(eoParent, pathElement, value);
        }
        else {
            this.models = eoParent.getModels().createChildModelsSimple(eoParent, pathElement, value);
        }
        if (isScalar() ) {
            this.eoParent = eoParent;
            this.parentKey = pathElement.getKey();
            this.object = models.transform(value);
        }
        else {
            this.object = this.models.create();
            if ((object instanceof Call) && !PathElement.isCallPath(eoParent)) {
                String targetPath = getPathAsString() + Path.DELIMITER + pathElement.getKey() ;
                this.eoParent = (EoChild)eoParent.getCallsEo();
                this.parentKey = Integer.valueOf(this.eoParent.size()).toString();
                if (!targetPath.isEmpty()) {
                    this.set(targetPath, "targetPath");
                }
            }
            else {
                this.eoParent = eoParent;
                this.parentKey = pathElement.getKey();
            }
            mapObject(value);
        }
        this.eoParent.setEo(pathElement, this);
    }

    /**
     * Gets the fieldName to access the adapter in the  parent adapter object.
     *
     * @return The fieldName of the parent adapters object.
     */
    public String getParentKey() {
        return parentKey;
    }

    public EoChild getEoParent() {
        return eoParent;
    }

    public EO getParent() {
        return eoParent;
    }

    public boolean hasParent() {
        return eoParent != null;
    }

    protected boolean hasEo(Path path) {
        return hasEo(path.getFirstPathElement());
    }

    protected boolean hasEo(PathElement pathElement) {
        return eoMap !=null && eoMap.containsKey(pathElement.getKey());
    }

    @Override
    public EO set(Object value, final String... paths) {
        return set(value, new Path(paths));
    }

    @Override
    public EO setEmpty(final String... paths) {
        return set(null, new Path(paths));
    }

    protected EO set(Object value, final Path path) {
        if (path.isEmpty()) {
            mapObject(value);
            return this;
        }
        if (path.isRootModel()) {
            if (value instanceof String) {
                this.models = new Models(getConfigsCache(), ((String)value).split(","));
            }
            else if (value instanceof Class) {
                this.models = new Models(getConfigsCache(), (Class)value);
            }
            this.object = getModel().create();
            return this;
        }

        if (hasEo(path)) {
            return ((EoChild) eoMap.get(path.getFirstEntry())).set(value, path.createChildPath());
        }
        else {
            try {
                if (path.hasChild()) {
                    new EoChild(this, path.getFirstPathElement(), null);
                    return ((EoChild) eoMap.get(path.getFirstEntry())).set(value, path.createChildPath());
                } else {
                    EoChild eoChild = new EoChild(this, path.getFirstPathElement(), value);
                    return eoChild;
                }
            }
            catch (EoException e) {
                error("Problem create new child: " + e.getMessage());
                return this;
            }
            catch (NullPointerException e) {
                throw new EoException(e);
            }
        }
    }
    @Override
    public void add(EO child, PathElement pathElement) {

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
        return this.object;
    }

    private final void set(final Object value)  {
        if (this.object != null && this.object == value) {
            return;  // the same object
        }
        if (object != null && value != null) {
            //throw new eoException("Not allowed to set a null source");
            if (this.object.hashCode() == value.hashCode()) {
                return;
            }
            if (!(object instanceof LogLevel)) {
                info("Existing Object is overwritten! " + getPath() + ".");
            }
        }
        this.object = value;
        //empty = this.models.isEmpty(source);
        changed = true;
        setParent();
    }

    /**
     * Sets the object of the embedded object. Implemtented in the appropriate child implementations.
     *
     * @param fieldName
     * @param source
     * @
     */
    protected void setValue(final String fieldName, Object source) {
        if (fieldName == null|| fieldName.isEmpty()) {
            throw new EoException("Empty fieldname for object " + source.getClass().getSimpleName());
        }
        if (PathElement.isParentNotSet(fieldName)) {
            return;
        }
        try {
            getModel().set(fieldName, get(), source);
        } catch (EoException e) {
            warn("Could not setValue for fieldName '" + fieldName + "': " + e.getMessage());
        }
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
                fieldKey= new Integer(((List)object).size()).toString();
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

    protected void setModels(Models models) {
        this.models = models;
        this.object = models.getModel().create();
    }

    @Override
    public EO mapObject(final Object value)  {
        if (value == null) {
            return this;
        }

        ModelInterface sourceModel = getConfigsCache().findModel(value);
        if (sourceModel.isScalar()) {
            if (isContainer() && (value instanceof String) && JSONToEO.jsonPattern.matcher((String)value).find()) {
                new JSONToEO((String)value).createChild(this);
                return this;
            }
            if (getModel().isScalar()  || getModel().isScalar()) {
                set(getModels().transform(value));
            }
            else {
                error("Problem setting value with Model " + sourceModel.toString() + " and eo with Model " + getModels().toString() );
                //set(value);
            }
            return this;
        }
        if (models.isScalar()) {
            error("Tried to map scalar child(" + getModelClass().getSimpleName() + ") with a non scalar value " + sourceModel + ".");
            return this;
        }
        //PathPattern pathPattern = params.getPathPattern();
        PathPattern pathPattern = new PathPattern(PathElement.MATCHER_ALL);
        Map keyValues = null;
        try {
            keyValues = sourceModel.getKeyValues(value, pathPattern);
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
            // when mapping model is a list but key is not a parseable integer use size
            if (isList()) {
                try {
                    Integer.parseInt(fieldName);
                } catch (Exception e) {
                    fieldName = new Integer(this.sizeEo()).toString();
                }
            }
            Object childValue = keyValues.get(key);
            if (childValue == null) {
                continue;
            }
            set(childValue, fieldName);
        }
        return this;
    }

    /**
     * If the object has a non null object.
     *
     * @return true if the object is not null.
     */
    @Override
    public boolean isEmpty() {
        return keys().isEmpty();
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
    public boolean addCall(Call call)  {
        if (call.hasTargetPath()) {
            if (!call.getTargetPath().startsWith(Path.DELIMITER)) {
                call.setTargetPath(getPathAsString() + Path.DELIMITER + call.getTargetPath());
            }
        }
        else {
            call.setTargetPath(getPathAsString());
        }
        return getRoot().addCall(call);
    }

    @Override
    public List<Call> getCalls() {
        return getRoot().getCalls();
    }
    @Override
    public EO getCallsEo() {
        return getRoot().getCallsEo();
    }

    @Override
    public Call getLastCall() {
        return getRoot().getLastCall();
    }


    @Override
    public boolean hasCalls() {
        return getRoot().hasCalls();
    }

    @Override
    public boolean execute() {
        return getRoot().execute();
    }

    protected void setParent() {
        if (eoParent == null) {
            return;
        }
        Object value = eoParent.getValue(parentKey);
        if (this.object != null && value == this.object) {
            return;
        }
        if (value != null && isScalar()) {
            if (value.equals(this.object)) {
                return;
            }
        }
        eoParent.setChanged(true);
        eoParent.setValue(getParentKey(), this.object);
        eoParent.setEo(new PathElement(parentKey), this);

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

    protected final void setChanged(final boolean changed) {
        this.changed = this.changed || changed;
    }

    /**
     * Returns the stored rootAdapter of the current adapter.
     *
     * @return The rootAdapter adapter instance var
     */
    public EoRoot getRoot() {
        if (eoParent !=null) {
            return eoParent.getRoot();
        }
        return (EoRoot) this;
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
        StringBuilder builder = new StringBuilder();
        getPathAsString(builder);
        return builder.toString();
    }

    protected void getPathAsString(StringBuilder builder) {
        if (parentKey != null && !parentKey.isEmpty()) {
            builder.insert(0, this.parentKey);
            builder.insert(0, Path.DELIMITER);
            eoParent.getPathAsString(builder);
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
        if (!hasLogLevel()) {
            if (eoParent != null) {
                return eoParent.getLogLevel();
            } else {
                setLogLevel(LogLevel.WARN);
            }
        }
        if (get(PathElement.LOG_LEVEL) instanceof LogLevel) {
            return (LogLevel) get(PathElement.LOG_LEVEL);
        }
        else if (get(PathElement.LOG_LEVEL) instanceof String) {
            try {
                return LogLevel.valueOf((String)get(PathElement.LOG_LEVEL));
            }
            catch (Exception e) {
                throw new EoException(e);
            }
        }
        else {
            throw new EoException("Mismatch in LogLevel " + getPathAsString() + ": " + get(PathElement.LOG_LEVEL));
        }

    }

    public boolean hasLogLevel() {
        return hasEo(new PathElement(PathElement.LOG_LEVEL));
    }

    @Override
    public LogLevel getErrorLevel() {
        return getRoot().getErrorLevel();
    }

    @Override
    public boolean hasErrors() {
        return getRoot().hasErrors();
    }

    @Override
    public String getLog() {
        return getRoot().getLog();
    }

    @Override
    public EO setLogLevel(LogLevel logLevel) {
        set(logLevel, "(LogLevel)" + PathElement.LOG_LEVEL);
        return this;
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

    private EO log(String message, LogLevel logLevel) {
        if (message == null) {
            return this;
        }
        getRoot().log(getPathAsString() + ": " + message, logLevel);
        return this;
    }

    private EO log(String message, LogLevel logLevel, Exception e) {
        getRoot().log(getPathAsString() + " - " + message + ": " + e.getMessage(), logLevel);
        return this;
    }

    protected boolean checkLevel(LogLevel messageLevel) {
        return getLogLevel().ordinal() <= messageLevel.ordinal();
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

    /**
     * Returns the object class stored in the adapter.
     *
     * @return The object class instance object of the adapter.
     */
    public Models getModels() {
        return models;
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
        return getModel().isScalar() || getModels().isEnum();
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


    @Override
    public JSONSerializationType getSerializationType() {
        return getRoot().getSerializationType();
    }

    @Override
    public EO setSerializationType(final JSONSerializationType serializationType) {
        getRoot().setSerializationType(serializationType);
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
