package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.config.ModelInterface;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.paths.PathPattern;
import org.fluentcodes.projects.elasticobjects.utils.FileUtil;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by werner on 20.05.16.
 */
public class EOBuilder {
    //http://stackoverflow.com/questions/3651725/match-multiline-text-using-regular-expression
    protected static final Pattern jsonPattern = Pattern.compile("^[\\s]*[\\{\\[]", Pattern.MULTILINE);
    protected static final Pattern modelPattern = Pattern.compile("^\\(([^\\)]*)\\)(.*)");
    private static final Logger LOG = LogManager.getLogger(EOBuilder.class);
    private Models targetModels;
    private Object value;

    private PathPattern pathPattern;

    private Path path;
    private boolean map = true;

    private EOContainer eoParent;
    private String parentKey;

    private EOConfigsCache configCache;

    private LogLevel logLevel;
    private EOExtension eoExtension = new EOExtensionEmpty();

    private JSONSerializationType serializationType = JSONSerializationType.STANDARD;

    /**
     * Creates a root adapter with an ItemsCache
     */
    public EOBuilder(EOConfigsCache provider) throws Exception {
        this.eoParent = null;
        this.configCache = provider;
        path = new Path(Path.DELIMITER);
    }

    public EOBuilder(EOBuilder builder) throws Exception {
        this.configCache = builder.getConfigCache();
        this.logLevel = builder.getLogLevel();
        this.map = builder.isMap();
        this.serializationType = builder.getSerializationType();
        this.eoExtension = builder.getEoExtension();
        this.eoParent = builder.getEoParent();
        this.pathPattern = builder.getPathPattern();
    }

    public EOBuilder(EOContainer eoParent) throws Exception {
        if (eoParent == null) {
            throw new Exception("Null eoParent");
        }
        this.configCache = eoParent.getConfigsCache();
        this.eoParent = eoParent;
    }

    public EOBuilder(EOBuilder parentBuilder, JSONToEO json) throws Exception {
        if (parentBuilder == null) {
            throw new Exception("Null eoParent");
        }
        this.configCache = parentBuilder.getConfigsCache();
        this.targetModels = parentBuilder.getTargetModels();
        this.map = parentBuilder.isMap();
        this.value = json;
    }

    protected EOConfigsCache getConfigsCache() {
        return configCache;
    }

    protected LogLevel getLogLevel() {
        return logLevel;
    }

    public EOBuilder setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
        return this;
    }


    public EOConfigsCache getConfigCache() {
        return configCache;
    }

    protected boolean hasParent() {
        return eoParent != null;
    }

    protected boolean isRootSetting() {
        return !hasParent() || ((eoParent instanceof EORoot) && !hasPath());
    }

    protected EOContainer getEoParent() {
        return eoParent;
    }

    protected EOBuilder setEoParent(final EOContainer eoParent) {
        this.eoParent = eoParent;
        return this;
    }

    protected String getParentKey() {
        return this.parentKey;
    }

    protected void setParentKey(final String parentKey) {
        this.parentKey = parentKey;
    }

    protected void setChild(Path path) {
        this.parentKey = path.first();
        this.path = path.getChildPath();
    }

    protected final boolean hasPath() {
        return path != null && !path.isEmpty();
    }

    public Path getPath() {
        return path;
    }

    public EOBuilder setPath(String path) {
        return this.setPath(new Path(path));
    }

    public EOBuilder setPath(final Path path) {
        this.path = path;
        return this;
    }

    public boolean isMap() {
        return map;
    }

    protected EOBuilder setMap(boolean mapped) {
        this.map = mapped;
        return this;
    }

    public String getModels() {
        return this.targetModels.toString();
    }

    public EOBuilder setModels(Models targetModels) {
        this.targetModels = targetModels;
        return this;
    }

    public boolean hasModels() {
        return this.targetModels != null && !this.targetModels.isEmpty();
    }

    public Models getTargetModels() {
        return this.targetModels;
    }

    protected void setModels() {
        if (parentKey == null) {
            return;
        }
        final Matcher matcher = modelPattern.matcher(parentKey);
        if (matcher.find()) {
            String modelKey = matcher.group(1);
            try {
                setModels(modelKey);
            } catch (Exception e) {
            }
            this.parentKey = matcher.group(2);
        }
    }

    public EOBuilder setModels(Class... classes) throws Exception {

        if (classes == null || classes.length == 0) {
            return this;
        }
        this.targetModels = new Models(configCache, classes);
        return this;
    }

    public EOBuilder setModels(ModelInterface... targetModels) {
        this.targetModels = new Models(targetModels);
        return this;
    }

    protected EOBuilder setModels(final String models) throws Exception {
        this.targetModels = new Models(this.configCache, models);
        return this;
    }

    public JSONSerializationType getSerializationType() {
        return this.serializationType;
    }

    public EOBuilder setSerializationType(JSONSerializationType serializationType) {
        this.serializationType = serializationType;
        return this;
    }


    public PathPattern getPathPattern() {
        return pathPattern;
    }

    public EOBuilder setPathPattern(PathPattern pathPattern) {
        this.pathPattern = pathPattern;
        return this;
    }

    public Object getValue() {
        return value;
    }

    protected void setValue(Object value) {
        this.value = value;
    }

    private void prepareValue() throws Exception {
        if (logLevel == null) {
            if (eoParent != null) {
                this.logLevel = eoParent.getLogLevel();
            } else {
                this.logLevel = LogLevel.WARN;
            }
        }
        if (!hasModels() && value == null) {
            this.value = new LinkedHashMap<>();
            return;
        }
        if (this.targetModels.isScalar()) {
            this.map = false;
            if (value != null) {
                try {
                    this.value = ScalarConverter.transform(targetModels.getModelClass(), this.value);
                } catch (Exception e) {
                    LOG.error("Conversion error " + eoParent.getPath());
                    eoParent.error("Conversion error " + eoParent.getPath());
                    throw e;
                }
            }
        } else {
            if (value == null) {
                this.value = this.targetModels.create();
            }
        }
    }


    public EOExtension getEoExtension() {
        return eoExtension;
    }

    public EOBuilder setEoExtension(final EOExtension eoExtension) {
        this.eoExtension = eoExtension;
        return this;
    }


    /**
     * Reads the file from classpath and set the adapter from content as a map path.
     *
     * @param fileName The name of the file
     * @return the created adapter
     * @throws Exception on every exception occurs
     */
    public EO mapFile(final String fileName) throws Exception {
        String value = FileUtil.readFile(fileName);
        return map(value);
    }

    public EO mapFile(final URL url) throws Exception {
        String value = FileUtil.readFile(url);
        return map(value);
    }

    /**
     * Sets the object to the adapter without mapping.
     *
     * @param object The object to be set without expanding structure to adapter.
     * @return the created adapter
     * @throws Exception on every exception occurs
     */
    public EO set(Object object) throws Exception {
        this.value = object;
        this.map = false;
        return build();
    }

    /**
     * Maps the sourceobject to the adapter without mapping.
     * If source is a String and starts either with "{" or "[" it will set the instance value with {@link JSONToEO}.
     * If source is a String and starts with the pattern (&ltModel&gt;) the targetModels will be set to the value in the brackets.
     *
     * @param source The source object will expanding structure to adapters.
     * @return the created adapter
     * @throws Exception on every exception occurs
     */

    public EO map(Object source) throws Exception {
        this.map = true;
        if (source == null) {
            return build();
        }
        if (source instanceof String) {
            String stringValue = (String) source;
            if (jsonPattern.matcher(stringValue).find()) {
                /*JSONToEO eoBuilder = new JSONToEO(stringValue, configCache);
                EO adapter = new EOBuilder(this, eoBuilder).build();
                if (path.isEmpty()) {
                    return adapter;
                }
                this.value = adapter;*/
                this.value = new JSONToEO(stringValue, configCache);
            } else {
                final Matcher matcher = modelPattern.matcher(stringValue);
                if (matcher.find()) {
                    String modelKey = matcher.group(1);
                    try {
                        setModels(modelKey);
                    } catch (Exception e) {
                        if (eoParent == null) {
                            throw e;
                        }
                        this.value = source;
                        //eoParent.debug(e.getMessage());
                    }
                    this.value = matcher.group(2);
                } else {
                    this.value = source;
                }
            }
        } else {
            this.value = source;
        }

        return build();
    }

    /**
     * Creates an adapter depending on the values stored here.
     *
     * @return
     * @throws Exception on model error creating a root adapter.
     */
    public EO build() throws Exception {
        boolean valueCreated = false;
        if (!hasPath()) {
            // Creates a rootAdapter
            if (!hasParent()) {
                if (targetModels == null) {
                    targetModels = new Models(configCache, value, map);
                } else {
                    targetModels.checkRootValue(value, map);
                }
                prepareValue();
                return new EORoot(this);
            }

            // if path is empty one try to add the values directly
            if (value == null) {
                eoParent.info("Wont set/map existing adapter with a null value '");
                return eoParent;
            }
            try {
                eoParent.getModels().checkRootValue(value, map);
            } catch (Exception e) {
                eoParent.warn(e.getMessage());
            }
            return eoParent;
        }

        if (!hasParent()) {
            EOBuilder rootBuilder = new EOBuilder(this);
            rootBuilder.setModels(new Models(configCache));
            rootBuilder.prepareValue();
            eoParent = new EORoot(rootBuilder);
        }

        try {
            EO eoChild = createChild();
            if (hasPath() && eoChild != null) { // further childs should be created ...
                return createChild((EOContainer) eoChild);
            } else {
                return eoChild;
            }
        } catch (Exception e) {
            eoParent.error(e.getMessage());
            return null;
        }
    }

    /**
     * Create a child adapter from the current values
     *
     * @param parent the parent adapter
     * @return
     * @throws Exception
     */
    protected EO createChild(EOContainer parent) throws Exception {
        EOBuilder childBuilder = new EOBuilder(parent);
        childBuilder.setPath(this.path);
        childBuilder.setValue(this.value);
        childBuilder.setModels(this.targetModels);
        childBuilder.setMap(this.isMap());
        childBuilder.setLogLevel(this.logLevel);
        EO adapter = childBuilder.build();
        return adapter;
    }

    /**
     * Create a child adapter depending on the current values
     *
     * @return
     * @throws Exception
     */
    protected EO createChild() throws Exception {
        parentKey = path.first();
        setModels();
        path = path.getChildPath();
        EO eoChild = eoParent.getChildAdapter(this.parentKey);
        if (eoParent.getModels().isList()) {
            if (!parentKey.matches("\\d+")) {
                this.parentKey = new Integer(eoParent.size()).toString();
            }
        }
        if (eoChild == null) {
            if (!hasPath()) {
                this.targetModels = eoParent
                        .getModels()
                        .createChild(parentKey, value, targetModels, map);
                this.prepareValue();
                return new EOContainer(this);
            } else {
                return createGlueContainer();
            }
        } else {
            if (value == null) {
                eoChild.warn("Null value with existing child ");
                return eoChild;
            }
            /*if (!map && eoChild.isScalar()) { // Special an existing childObject on the path will be replaced if when not mapped
                return createGlueContainer();
            }*/
            if (!hasPath()) {  // the child already exist from now on
                try {
                    eoChild.getModels().checkRootValue(value, map);
                } catch (Exception e) {
                    eoChild.warn(e.getMessage());
                    return eoChild;
                }
            }
        }
        return eoChild;
    }

    /**
     * Creates a container containing no value ...
     *
     * @return
     * @throws Exception
     */
    private EO createGlueContainer() throws Exception {
        Models targetModels = eoParent.getModels()
                .createChildForSet(parentKey, null, null);
        if (targetModels.isScalar()) {
            throw new Exception("A glue container could not be a scalar type!");
        }
        EOBuilder pathBuilder = new EOBuilder(eoParent);
        pathBuilder.setEoParent(eoParent);
        pathBuilder.setModels(targetModels);
        pathBuilder.setParentKey(parentKey);
        pathBuilder.setMap(this.map);
        pathBuilder.setLogLevel(this.logLevel);
        pathBuilder.prepareValue();
        return new EOContainer(pathBuilder);
    }

    @Override
    public String toString() {
        if (this == null) {
            return "";
        }
        StringBuilder buffer = new StringBuilder();
        if (targetModels != null && !targetModels.isEmpty()) {
            buffer.append("\"models\":\"");
            buffer.append(targetModels.toString());
            buffer.append("\",");
        }
        if (pathPattern != null) {
            buffer.append(pathPattern.getSerialized());
        }
        buffer.append("\"parentKey:\":\"");
        buffer.append(parentKey);
        buffer.append("\",");
        if (path != null && !path.isEmpty()) {
            buffer.append("\"path:\":\"");
            buffer.append(path.toString());
            buffer.append("\",");
        }
        if (value != null) {
            buffer.append("\"value:\":\"");
            buffer.append(value.toString());
            buffer.append("\",");
        }
        if (buffer.length() == 0) {
            return ("");
        }
        buffer.append("}");
        return "{" + buffer.toString().replaceAll(",}$", "}");
    }

}
