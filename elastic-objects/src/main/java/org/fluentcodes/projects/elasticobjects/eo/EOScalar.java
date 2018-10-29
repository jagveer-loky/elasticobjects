package org.fluentcodes.projects.elasticobjects.eo;

import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.config.ModelInterface;
import org.fluentcodes.projects.elasticobjects.executor.ExecutorList;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.utils.ScalarComparator;

import java.util.Arrays;
import java.util.List;

/**
 * Offers serialized setter and getter for java models
 */

public abstract class EOScalar implements EO {
    public static final String PARAMS = ".params";
    private final EORoot rootAdapter;
    private final EOContainer parentAdapter;
    private final String parentFieldName;
    private final Models models;
    private final LogLevel logLevel;

    private Object object;

    private boolean changed = false;
    private boolean insert = false;
    private Boolean empty = true;

    protected EOScalar(final EOBuilder params) throws Exception {
        if (params.getTargetModels() == null || params.getTargetModels().size() == 0) {
            throw new Exception("No model defined for " + params.toString());
        }
        this.models = params.getTargetModels();
        this.models.setEO(this);
        this.parentAdapter = params.getEoParent();
        this.parentFieldName = params.getParentKey();
        this.logLevel = params.getLogLevel();
        if (this instanceof EORoot) {
            this.rootAdapter = (EORoot) this;
            ((EORoot) this).initRoot(params);
        } else {
            this.rootAdapter = params.getEoParent().getRoot();
        }
    }


    @Override
    public Object get() {
        return this.object;
    }

    protected void setModelClasses(Class... classes) throws Exception {
        if (classes == null || classes.length == 0) {
            info("Empty classes!" + getPathAsString());
            return;
        }
        setModels(new Models(getConfigsCache(), classes));
    }

    public void set(final Object source) throws Exception {
        if (this.object != null && this.object == source) {
            return;  // the same object
        }
        if (object != null && source != null) {
            //throw new Exception("Not allowed to set a null source");
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

    @Override
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
    }

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

    protected void setModels(Models newModels) throws Exception {
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
    public boolean isEmpty() {
        if (this.empty != null) {
            return this.empty;
        }
        if (this.object != null) {
            this.empty = false;
            return true;
        }
        this.empty = true;
        return false;
    }

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
