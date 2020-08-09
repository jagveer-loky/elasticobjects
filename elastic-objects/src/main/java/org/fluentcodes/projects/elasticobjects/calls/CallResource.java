package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserTemplate;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.calls.condition.Or;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.*;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

/**
 * Created by Werner on 10.10.2016.
 * Elementary call with mapping configuration keys to configuration via constructor.
 */
public abstract class CallResource<RESULT> extends CallImpl<RESULT> {
    private static final Logger LOG = LogManager.getLogger(CallResource.class);
    private Config config;
    private String configKey;
    private PermissionType permissions;

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

    public CallResource() {
    }
    public CallResource(PermissionType permissionType) {
        this.permissions = permissionType;
    }

    public boolean hasPermissions(final List<String> roles) {
        return ((ConfigResourcesImpl)getConfig()).getRolePermissions().hasPermissions(permissions, roles);
    }

    protected void init(final EO eo)  {
        resolve(eo.getConfigsCache());
        hasPermissions(eo.getRoles());
    }

    public CallResource resolve(EOConfigsCache cache) {
        if (config!=null) {
            return this;
        }
        this.config = (Config) cache.find(getConfigClass(), getConfigKey());
        return this;
    }

    public static final String createKeyValue(final String key, final String value) {
        return "\"" + key + "\":\"" + value + "\"";
    }

    public String getConfigKey() {
        return configKey;
    }

    public CallResource setConfigKey(String configKey) {
        this.configKey = configKey;
        return this;
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

    public void setConfig(EOConfigsCache cache) {
        this.config = (Config)cache.find(getConfigClass(), getConfigKey());
    }



    public Class<? extends Config> getConfigClass()  {
        throw new EoException("Problem with configClass getMethod should be overwritten");
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

    public boolean hasPathIf() {
        return pathIf != null && !pathIf.isEmpty();
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

    private boolean executeIf(String _if, EO adapter, Map externalAttributes) {
        _if = new ParserTemplate(_if).parse(adapter);
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
        /*if (!hasMapPath()) {
            return path.directory(absolutePath);
        }
        path.addPaths(this.mapPath);
        if (path.isEmpty()) {
            return null;
        }*/
        return path.directory(absolutePath);
    }

    public void resolvePath(EO adapter, Map attributes) {
        if (this.path == null) {
            return;
        }
        this.path = new ParserTemplate(this.path).parse(adapter);
    }


    protected boolean hasPath() {
        return !(path == null) && !path.isEmpty();
    }

    protected EO moveToPath(final EO adapter)  {
        if (adapter == null) {
            throw new EoException("Null adapter");
        }
        if (!hasPath()) {
            return adapter;
        }
        return adapter.getEo(Path.DELIMITER + path);
    }

    public String getMapPath() {
        return mapPath;
    }

    public CallResource setMapPath(Object entry) {
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


}
