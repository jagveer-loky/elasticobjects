package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EoException;
import org.fluentcodes.projects.elasticobjects.utils.JSONReader;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * Created by werner.diwischek on 17.01.18.
 * 31.5.2018: Replace methods within {@link EOConfigs}
 */
public class EOConfigReader {
    public static final Logger LOG = LogManager.getLogger(EOConfigReader.class);
    private final Class<? extends Config> cacheClass;
    private final EOConfigsCache configsCache;
    private final Class builderClass;
    private final Method builderMethod;
    private final Scope scope;

    public EOConfigReader(final EOConfigsCache configsCache, final Class<? extends Config> configClass)  {
        this.configsCache = configsCache;
        this.cacheClass = configClass;
        try {
            this.builderClass = Class.forName(configClass.getName() + "$Builder");
            this.builderMethod = builderClass.getMethod("build", EOConfigsCache.class, Map.class);
        }
        catch (Exception e) {
            throw new EoException(e);
        }
        Scope scope = configsCache.getScope();
        if (scope == null) {
            this.scope = Scope.ALL;
        } else {
            this.scope = scope;
        }
    }

    public Map<String, Config> read(final String url)  {
        Map map = JSONReader.readMapBean(configsCache, url, null);

        Map<String, Config> configMap = new HashMap<String, Config>();
        try {
            add(map, configMap);
        } catch (Exception e) {
            throw new EoException(e.getMessage() + " url=" + url);
        }
        return configMap;
    }

    public void read(final URL url, final Map<String, Config> configMap)  {
        read(url, configMap, Scope.ALL);
    }

    public void read(final URL url, final Map<String, Config> configMap, final Scope scope)  {
        Map map = JSONReader.readMapBean(configsCache, url, Map.class);
        try {
            add(map, configMap, scope);
        } catch (Exception e) {
            throw new EoException(e.getMessage() + " url=" + url);
        }
    }

    public void add(final Map map, final Map<String, Config> configMap)  {
        add(map, configMap, Scope.ALL);
    }

    public void add(final Map map, final Map<String, Config> configMap, final Scope scope)  {
        for (Object key : map.keySet()) {
            Object object = null;
            try {
                object = builderClass.getDeclaredConstructor(null).newInstance();
            } catch (Exception e) {
                throw new EoException(e);
            }
            try {
                Config config = (Config) builderMethod.invoke(object, configsCache, map.get(key));
                if (!config.hasScope(scope)) {
                    continue;
                }
                configMap.put((String) key, config);
            } catch (Exception e) {
                e.printStackTrace();
                throw new EoException("Could not create config for " + key + ".");
            }
        }
    }

    public Map<String, Config> add(final Map map)  {
        Map<String, Config> configMap = new HashMap<>();
        add(map, configMap);
        return configMap;
    }


    public Map<String, Config> read()  {
        return read(Scope.ALL);
    }

    public Map<String, Config> read(final Scope scope)  {
        Map<String, Config> configMap = new HashMap<>();
        return read(configMap, scope);
    }

    public Map<String, Config> read(final Map<String, Config> configMap)  {
        return read(configMap, Scope.ALL);
    }


    public Map<String, Config> read(final Map<String, Config> configMap, final Scope scope)  {
        String providerSource = cacheClass.getSimpleName() + ".json";
        try {
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(providerSource);
            List<URL> urlList = Collections.list(urls);
            for (URL url : urlList) {
                LOG.info("Initialize provider with " + url.getFile());
                read(url, configMap, scope);
            }
            return configMap;
        } catch (IOException e) {
            throw new EoException(e);
        }

    }

}
