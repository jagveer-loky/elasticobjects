package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.condition.Or;
import org.fluentcodes.projects.elasticobjects.config.Config;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.config.ModelInterface;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.eo.EOContainer;
import org.fluentcodes.projects.elasticobjects.eo.EOToJSON;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.utils.ReplaceUtil;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.*;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

/**
 * Created by Werner on 10.10.2016.
 * Elementary call with mapping configuration keys to configuration via constructor.
 */
public class Call {
    private static final Logger LOG = LogManager.getLogger(Call.class);
    private final Config config;

    private Map attributes;
    private Map globalAttributes;
    private Set removeAttributes;

    private String path;
    private boolean pathDynamic;
    private String mapPath;

    private boolean absolutePath = false;
    private boolean dynamicMapPath = false;
    private boolean dynamicKey = false;

    private String pathIf;
    private String loopIf;

    /**
     * Just an empty constructor since basic
     */
    public Call(EOConfigsCache configsCache, String cacheKey) throws Exception {
        ModelInterface actionConfig = configsCache.findModel(this.getClass());
        if (actionConfig == null) {
            throw new Exception("Could not find model for '" + this.getClass().getSimpleName() + "'!");
        }
        String modelConfigKey = actionConfig.getModelConfigKey();
        if (modelConfigKey == null) {
            throw new Exception("Could not find model config key  '" + actionConfig.getModelKey() + "'!");
        }
        ModelInterface config = configsCache.findModel(modelConfigKey);
        try {
            this.config = (Config) configsCache.find(config.getPackagePath(), config.getModelKey(), cacheKey);
        } catch (Exception e) {
            throw new Exception("Could not find config entry " + cacheKey + " in actions for config " + config.getModelKey() + ": " + e.getMessage());
        }
        this.dynamicKey = cacheKey.contains("$[");
        this.attributes = new HashMap();
        this.globalAttributes = new HashMap();
        this.removeAttributes = new HashSet();
    }

    public Call(EOConfigsCache configsCache) throws Exception {
        this.config = null;
        this.attributes = new HashMap();
        this.globalAttributes = new HashMap();
        this.removeAttributes = new HashSet();
    }

    public Call() {
        config = null;
    }

    public void mapAttributes(final Map attributes) {
        setPath(attributes.get(F_PATH));
        setMapPath(attributes.get(F_MAP_PATH));
        setPathIf(attributes.get(A_IF));
        setLoopIf(attributes.get(A_IF_LOOP));
        for (Object key : attributes.keySet()) {
            String keyString = (String) key;
            if (keyString.endsWith(":")) {
                keyString = keyString.replaceAll(":$", "");
                if (((String) attributes.get(key)).equals("NULL")) {
                    removeAttributes.add(keyString);
                } else {
                    globalAttributes.put(keyString, attributes.get(key));

                }
            } else {
                this.attributes.put(keyString, attributes.get(key));
            }
        }
    }

    protected void mergeConfig() {
        if (config == null) {
            return;
        }
        setPath(config.getPath());
        setMapPath(config.getMapPath());
    }

    public boolean isDynamicKey() {
        return dynamicKey;
    }

    public EOConfigsCache getProvider() {
        return getConfig().getConfigsCache();
    }

    public Config getConfig() {
        return config;
    }

    public boolean hasConfig() {
        return !(config == null);
    }

    public Map getAttributes() {
        return attributes;
    }


    public Map getGlobalAttributes() {
        return globalAttributes;
    }

    protected Map mergeAttributes(Map externalAttributes) {
        Map attributes = new HashMap();
        attributes.putAll(globalAttributes);
        attributes.putAll(externalAttributes);
        for (Object key : removeAttributes) {
            attributes.remove(key);
        }
        return attributes;
    }

    protected Map mergeAllAttributes(Map externalAttributes) {
        Map attributes = new HashMap();
        attributes.putAll(this.attributes);
        attributes.putAll(globalAttributes);
        attributes.putAll(externalAttributes);
        for (Object key : removeAttributes) {
            attributes.remove(key);
        }
        attributes.remove(F_CONTENT);
        attributes.remove(F_PATH);
        attributes.remove(F_MAP_PATH);
        attributes.remove(F_LOOP_PATH);
        return attributes;
    }

    public boolean hasPathIf() {
        return pathIf != null && !pathIf.isEmpty();
    }

    private String getPathIf() {
        return pathIf;
    }

    public void setPathIf(Object entry) {
        if (entry == null) {
            return;
        }
        if (hasPathIf()) {
            return;
        }
        if (entry instanceof String) {
            this.pathIf = (String) entry;
            return;
        }
        LOG.error("Only String will be supported for creating pathIf " + entry);
    }

    protected boolean executePathIf(EO adapter, Map externalAttributes) {
        if (!hasPathIf()) {
            return true;
        }
        return executeIf(pathIf, adapter, externalAttributes);
    }

    private boolean executeIf(String _if, EO adapter, Map externalAttributes) {
        _if = ReplaceUtil.replace(_if, adapter, externalAttributes);
        Or or = new Or(_if);
        return or.filter(adapter);
    }

    public boolean hasLoopIf() {
        return loopIf != null && !loopIf.isEmpty();
    }

    private String getLoopIf() {
        return loopIf;
    }

    public void setLoopIf(Object entry) {
        if (entry == null) {
            return;
        }
        if (hasLoopIf()) {
            return;
        }
        if (entry instanceof String) {
            this.loopIf = (String) entry;
            return;
        }
        LOG.error("Only String will be supported for creating loopIf " + entry);
    }

    protected boolean executeLoopIf(EO adapter, Map externalAttributes) {
        if (!hasLoopIf()) {
            return true;
        }
        return executeIf(loopIf, adapter, externalAttributes);
    }

    public String getPath() {
        return path;
    }

    public void setPath(Object entry) {
        if (this.path != null) {
            return;
        }
        if (entry == null) {
            return;
        }
        this.path = ScalarConverter.toString(entry);
        pathDynamic = this.path.contains(EO_STATIC.DYNAMIC_PATTERN);
        absolutePath = this.path.startsWith(Path.DELIMITER);
    }

    public String getMergePath() {
        Path path = new Path(this.path);
        if (!hasMapPath()) {
            return path.directory(absolutePath);
        }
        path.addPaths(this.mapPath);
        if (path.isEmpty()) {
            return null;
        }
        return path.directory(absolutePath);
    }

    public void resolvePath(EO adapter, Map attributes) {
        if (this.path == null) {
            return;
        }
        this.path = ReplaceUtil.replace(this.path, adapter, attributes);
    }


    protected boolean hasPath() {
        return !(path == null) && !path.isEmpty();
    }

    protected EO setToPath(final EO adapter, Object object) throws Exception {
        if (adapter == null) {
            throw new Exception("Null adapter");
        }
        if (!hasPath()) {
            return adapter;
        }
        adapter.add(Path.DELIMITER + path).set(object);
        return moveToPath(adapter);
    }

    protected EO mapToPath(final EO adapter, Object object) throws Exception {
        if (adapter == null) {
            throw new Exception("Null adapter");
        }
        if (!hasPath()) {
            return adapter;
        }
        adapter.add(Path.DELIMITER + path).map(object);
        return moveToPath(adapter);
    }

    protected EO moveToPath(final EO adapter) throws Exception {
        if (adapter == null) {
            throw new Exception("Null adapter");
        }
        if (!hasPath()) {
            return adapter;
        }
        return adapter.getChild(Path.DELIMITER + path);
    }

    public String getMapPath() {
        return mapPath;
    }

    public Call setMapPath(Object entry) {
        if (entry == null) {
            return this;
        }
        if (this.mapPath != null) {
            return this;
        }
        String mapPath = ScalarConverter.toString(entry);
        this.mapPath = mapPath.replaceAll("^/+", "");
        this.dynamicMapPath = this.mapPath.contains("$[");
        return this;
    }

    protected boolean isDynamicMapPath() {
        return dynamicMapPath;
    }

    protected boolean isAbsolutePath() {
        return absolutePath;
    }

    protected boolean hasMapPath() {
        return !(mapPath == null) && !mapPath.isEmpty();
    }

    protected List<String> filterPath(EO adapter) {
        try {
            return ((EOContainer) adapter).filterPaths(path);
        } catch (Exception e) {
            adapter.error("Could not create paths " + path + ": " + e.getMessage());
            return null;
        }
    }

    protected EO createAdapter(EO adapter) throws Exception {
        return createAdapter(adapter, new HashMap());
    }

    protected EO createAdapter(EO adapter, Map attributes) throws Exception {
        resolvePath(adapter, attributes);
        return adapter.add(this.path).build();
    }

    @Override
    public String toString() {
        if (this == null) {
            return "null";
        }
        if (getConfig()== null) {
            return "direct";
        }
        StringBuilder serialized = new StringBuilder();

        try {
            serialized.append("{\n\"attributes\":");
            serialized.append(attributes.toString());
             serialized.append("\n,\"config\":");
             serialized.append(new EOToJSON()
                    .setStartIndent(1)
                    .toJSON(getConfig().getConfigsCache(), this.getConfig())
             );
             serialized.append("\n}");
             return serialized.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public String toString2() {
        StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
        for (Object key : getAttributes().keySet()) {
            if (EO_STATIC.F_INSERT.equals(key)) {
                continue;
            }
            builder.append(" ");
            builder.append(key);
            builder.append("=\"");
            builder.append(attributes.get(key));
            builder.append("\"");
        }
        for (Object key : getGlobalAttributes().keySet()) {
            builder.append(" ");
            builder.append(key);
            builder.append(":=\"");
            builder.append(globalAttributes.get(key));
            builder.append("\"");
        }
        if (attributes.get(EO_STATIC.F_INSERT) != null) {
            builder.append("\nFrom ");
            builder.append(EO_STATIC.F_INSERT);
            builder.append(": ");
            builder.append(attributes.get(EO_STATIC.F_INSERT));
        }
        return builder.toString();
    }

}
