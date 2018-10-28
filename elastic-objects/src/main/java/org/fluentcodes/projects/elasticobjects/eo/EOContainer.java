package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.config.ModelInterface;
import org.fluentcodes.projects.elasticobjects.executor.CallExecutor;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.paths.PathPattern;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.*;

/**
 * Offers serialized setter and getter for java objects
 * @author Werner Diwischek
 * @since 10.10.2015
 */

public class EOContainer extends EOScalar implements EO {
    private static final Logger LOG = LogManager.getLogger(EOContainer.class);
    private Map<String, EO> childMap;

    protected EOContainer(final EOBuilder params) throws Exception {
        super(params);
        if (isScalar()) {
            childMap = null;
            set(params.getValue());
            return;
        } else {
            childMap = new LinkedHashMap<>();
        }
        try {
            if (params.isMap()) {
                map(params);
            } else {
                set(params.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.warn("Exception: " + e.getMessage());
        }
    }

    protected void map(final EOBuilder params) throws Exception {
        Object source = params.getValue();
        if (this.isEmpty()) {
            set(getAdapterExtension().doBeforeMap(this, source));
        } else {
            debug("Check program flow!");
        }
        map(source);
        //if (this.isChanged()) {
        //  getEoExtension().doAfterMap(this);
        //}
    }

    protected void map(final Object source) throws Exception {
        if (source == null) {
            return;
        }
        if (source instanceof JSONToEO) {
            if (get()==null) {
                set(getModel().create());
            }
            ((JSONToEO) source).createChild(this);
            //((JSONToEO) source).createChild(this, null);
            return;
        }

        ModelInterface sourceModel = getConfigsCache().findModel(source);
        if (sourceModel.isScalar()) {
            set(source);
            return;
        }
        //PathPattern pathPattern = params.getPathPattern();
        PathPattern pathPattern = new PathPattern(Path.MATCHER_ALL);
        Map keyValues = null;
        try {
            keyValues = sourceModel.getKeyValues(source, pathPattern);
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

            try {
                EOBuilder builder = this.add()
                        .setPath(fieldName);
                if (isObject()) {
                    builder.setModels(getModel().getFieldModel(fieldName));
                } else {
                    builder.setModels(getModels().getChildModelsList());
                }
                EO adapter = builder.map(childValue);

                setChanged(((EOScalar) adapter).isChanged());
            } catch (Exception e) {
                LOG.warn("Problem mapping " + fieldName + ": " + e.getMessage());
            }
        }
        if (isObject() && isChanged()) {
            getAdapterExtension().doAfterMap(this);
        }
    }


    protected void setCalls(List<CallExecutor> actions) throws Exception {

        for (CallExecutor action : actions) {
            action.setPath(this.getPath().directory());
            getRoot().addCall(action);
        }
        this
                .add()
                .setPath(JSONToEO.CALLS)
                .set(actions);
    }

    protected void setCallsByMap(List<Map> callList) throws Exception {
        for (Map attributes : callList) {
            if (attributes.get(EO_STATIC.F_PATH)==null) {
                attributes.put(EO_STATIC.F_PATH, this.getPath().directory());
            }
            getRoot().addCallExecutor(attributes);
        }
    }

    public void addCall(CallExecutor callExecutor) throws Exception {
        Path path = this.getPath();
        callExecutor.setPath(path.directory());
        getRoot().addCall(callExecutor);
    }

    @Override
    public EOBuilder add() {
        try {
            return new EOBuilder(this);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public EOBuilder add(final String path) {
        try {
            if (path==null || path.isEmpty()) {
                return new EOBuilder(this);
            }
            if (path.startsWith(Path.DELIMITER)) {
                return new EOBuilder(getRoot())
                        .setPath(path);
            }
            return new EOBuilder(this)
                    .setPath(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
    public int size() throws Exception {
        if (!isContainer()) {
            return 0;
        }
        return this.keys().size();
    }

    @Override
    public int size(final String path) throws Exception {
        return (this.childKeys(path).size());
    }

    public List<String> keys(String pathString) throws Exception {
        if (pathString == null || pathString.isEmpty() || ".".equals(pathString)) {
            List<String> keys = new ArrayList<>();
            keys.add(".");
            return keys;
        }
        return keys(new PathPattern(pathString));
    }

    public List<String> keys(PathPattern pathPattern) throws Exception {
        return pathPattern.filter(keys());
    }

    public final Map getKeyValues() throws Exception {
        if (getModel() == null) {
            throw new Exception("Null model!");
        }
        if (!isContainer()) {
            throw new Exception("Not a container model " + getModel().getModelKey() + "'!");
        }
        return getModel().getKeyValues(get(), new PathPattern("*"));
    }

    @Override
    public List<String> filterPaths(String pathString) throws Exception {
        if (pathString == null || pathString.isEmpty() || ".".equals(pathString)) {
            List<String> keys = new ArrayList<>();
            keys.add(".");
            return keys;
        }
        return filterPaths(new PathPattern(pathString), "");
    }

    public List<String> filterPaths(PathPattern pathPattern, String path) throws Exception {
        List<String> result = new ArrayList<>();
        List<String> filter = pathPattern.filter(keys());
        for (String key : filter) {
            if (key.equals(".config")) {
                continue;
            }
            String nextPath = path + Path.DELIMITER + key;
            nextPath = nextPath.replaceAll("^" + Path.DELIMITER, "");
            EO childAdapter = getChild(key);
            if (childAdapter == null){// || childAdapter.isEmpty()) {
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
    public Set<String> keys() throws Exception {
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

    public Set<String> childKeys(final String path) throws Exception {
        return getChild(path).keys();
    }

    /**
     * Return the keys of an Object as String. Not defined in the base implementations.
     * Mainly used for List or Maps
     *
     * @return
     * @throws Exception
     */
    public Set<String> keysValue() throws Exception {
        return this.getModel().keys(this.get());

    }

    /**
     * Sets the object of the embedded object. Implemtented in the appropriate child implementations.
     *
     * @param fieldName
     * @param source
     * @throws Exception
     */
    protected void setValue(final String fieldName, Object source) {
        if (fieldName == null) {
            warn("Null fieldname for object " + source.getClass().getSimpleName());
            return;
        }
        try {
            getModel().set(fieldName, get(), source);
        } catch (Exception e) {
            warn("Could not add field for fieldName " + fieldName + ": " + e.getMessage());
        }
    }

    protected Object getValue(final String fieldName) {
        try {
            return getModel().get(fieldName, get());
        } catch (Exception e) {
            // NullPointerException thrown when null value is add in the Object. Will be ignored.
            //warn("Strange Exception " + fieldName + ": " + e.getMessage());
            //e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean remove(final String fieldName) {
        try {
            getModel().remove(fieldName, get());
            this.childMap.remove(fieldName);
            //this.childMap.put(fieldName, null);

        } catch (Exception e) {
            warn("Could not remove: " + e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * Directly removes the object of the operator
     *
     * @throws Exception On null source
     */
    @Override
    public EO remove() throws Exception {
        if (getParentAdapter() == null) {
            throw new Exception("Could not remove when no paren is add! Problem removing rootAdapter adapter");
        }
        EOContainer returnAdapter = getParentAdapter();
        getParentAdapter().remove(getParentKey());
        return returnAdapter;
    }

    @Override
    public Object get(final String pathString) {
        //throw new Exception("Basic find method should be overwritten");
        if (pathString == null || pathString.isEmpty()) {
            return get();
        }
        String special = null;
        String path = pathString;
        if (pathString.endsWith(EO_STATIC._PARENT_KEY)) {
            path = pathString.replaceAll(EO_STATIC._PARENT_KEY + "$", "");
            special = EO_STATIC._PARENT_KEY;
        }
        else if (pathString.endsWith(EO_STATIC._VALUE)) {
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


    protected void removeChildEO(Object value) throws Exception {
        if (this.isScalar()) {
            this.childMap = null;
        }
        else {
            this.childMap = new LinkedHashMap<>();
        }
        set(value);
    }

    @Override
    public EO getChild(String pathString) throws Exception {
        if (pathString == null || pathString.equals("") || pathString.equals("./") || pathString.equals(".")) {
            return this;
        }
        if (pathString.equals(Path.DELIMITER)) {
            return getRoot();
        }
        if (pathString.startsWith(Path.DELIMITER) && this != getRoot()) {
            return getRoot().getChild(pathString);
        }
        return getChild(new Path(pathString), false);
    }


    protected EO getChild(Path path, boolean create) throws Exception {
        if (path == null || path.isEmpty()) {
            return this;
        }
        String firstEntry = path.getFirstEntry();
        if (firstEntry.equals("..")) {
            return getParentAdapter().getChild(path.getChildPath(), create);
        }
        EO childAdapter = getChildAdapter(firstEntry);

        if (childAdapter == null) {
            if (create) {
                childAdapter = this
                        .add()
                        .setPath(path.getFirstEntry())
                        .build();
            } else {
                try {
                    Object value = getModel()
                            .get(path.getFirstEntry(), this.get());

                    if (value != null) {
                        childAdapter = this
                                .add(path.getFirstEntry())
                                .set(value);
                    } else {
                        debug("Problem getting a non existing object value for " + getPathAsString() + " " + path.getFirstEntry());
                        return null;
                    }
                } catch (Exception e) {
                    info("Problem getting value for " + getPathAsString() + " " + path.getFirstEntry() + ": " + e.getMessage());
                    return null;
                }
            }
        }
        if (path.getChildPath().isEmpty()) {
            return childAdapter;
        }
        // recursive call if child path is not empty
        return ((EOContainer) childAdapter).getChild(path.getChildPath(), create);
    }

    /**
     * Creates just a builder with  with a path and this adapter.
     *
     * @param pathString the path to look for
     * @return a bilder with  with a path and this adapter
     * @throws Exception on builder expception
     */
    protected EOBuilder createBuilder(String pathString) throws Exception {
        return new EOBuilder(this)
                .setPath(pathString);
    }

    public EO getChildAdapter(String fieldName) {
        if (childMap == null || childMap.isEmpty()) {
            return null;
        }
        return childMap.get(fieldName);
    }

    /**
     * Add the adapter with fieldName to childMap.
     * @param fieldName The fieldName
     * @param child the child eo
     */
    protected void setChild(final String fieldName, final EO child) {
        if (fieldName == null) {
            warn("FieldName ist null setting child ObjectsBuilder! ");
            return;
        }
        if (this.childMap.get(fieldName) == child) {
            return;
        }
        this.childMap.put(fieldName, child);
    }

    @Override
    public void compare(final StringBuilder builder, final EO other) {
        if (this.isNull()) {
            return;
        }
        if (this.isScalar()) {
            super.compare(builder, other);
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


    protected void initObjectRegistry() {
        getRoot().initObjectRegistry();
    }

    protected boolean checkObjectRegistry(Object object) {
        return getRoot().checkObjectRegistry(object);
    }
}
