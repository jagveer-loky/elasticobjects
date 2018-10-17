package org.fluentcodes.projects.elasticobjects.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.eo.EOBuilder;

import java.io.File;
import java.net.URL;
import java.util.*;

public class JSONReader {
    private static final Logger LOG = LogManager.getLogger(JSONReader.class);

    public static Object readBean(final EOConfigsCache configs, final String urlString, final Class beanClass) throws Exception {
        return new EOBuilder(configs)
                .setModels(beanClass)
                .mapFile(urlString);
    }

    public static List<Object> readListFromDataClassPath(final EOConfigsCache configs, final Class beanClass) throws Exception {
        return readListFromUrl(configs, "data/" + beanClass.getSimpleName() + ".json", beanClass);
    }

    public static void readListFromDataClassPath(final EOConfigsCache configs, final Class beanClass, final List result) throws Exception {
        readListFromUrl(configs, "data/" + beanClass.getSimpleName() + ".json", beanClass, result);
    }

    private static List<Object> readListFromUrl(final EOConfigsCache configs, final String urlString, final Class beanClass) throws Exception {
        List result = new ArrayList();
        readListFromUrl(configs, urlString, beanClass, result);
        return result;
    }

    public static void readListFromUrl(final EOConfigsCache configs, final String urlString, final Class beanClass, final List result) throws Exception {
        Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(urlString);
        List<URL> urlList = Collections.list(urls);
        if (urlList.size() == 0) {
            throw new Exception("Could not found any url matching '" + urlString + "'.");
        }
        for (URL url : urlList) {
            LOG.info("Add " + beanClass.getSimpleName() + " from " + url.getFile());
            readList(configs, url, beanClass, result);
        }
    }


    public static List<Object> readList(final EOConfigsCache configs, final String urlString, final Class beanClass) throws Exception {
        List result = new ArrayList();
        readList(configs, getUrl(urlString), beanClass, result);
        return result;
    }

    private static void readList(final EOConfigsCache configs, final URL url, final Class beanClass, final List result) throws Exception {

        List list = (List) new EOBuilder(configs)
                .setModels(List.class, beanClass)
                .mapFile(url).get();
        result.addAll(list);
    }

    public static Map readMapFromUrl(final EOConfigsCache configs, final String urlString, final Class configClass) throws Exception {
        Map result = new LinkedHashMap();
        Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(urlString);
        List<URL> urlList = Collections.list(urls);
        for (URL url : urlList) {
            LOG.info("Add " + configClass.getSimpleName() + " from " + url.getFile());
            readMapBean(configs, url, configClass, result);
        }
        return result;
    }


    public static Map readMapBean(final EOConfigsCache configs, final String urlString, final Class beanClass) throws Exception {
        return readMapBean(configs, getUrl(urlString), beanClass, new LinkedHashMap<>());
    }

    public static Map readMapBean(final EOConfigsCache configs, final URL url, final Class beanClass) throws Exception {
        return readMapBean(configs, url, beanClass, new LinkedHashMap<>());
    }

    public static EO readAdapterBean(final EOConfigsCache configs, final String urlString, final Class beanClass) throws Exception {
        return new EOBuilder(configs)
                .setModels(Map.class, beanClass)
                .mapFile(urlString);
    }

    public static EO readAdapterBean(final EOConfigsCache configs, final URL url, final Class beanClass) throws Exception {
        try {
            return new EOBuilder(configs)
                    .setModels(Map.class, beanClass)
                    .mapFile(url);
        }
        catch (Exception e) {
            throw e;
        }
    }

    public static Map readMapBean(final EOConfigsCache configs, final URL url, final Class<?> beanClass, final Map result) throws Exception {
        Map beanMap = (Map) readAdapterBean(configs, url, beanClass).get();
        result.putAll(beanMap);
        return result;
    }


    public final static URL getUrl(final String urlString) throws Exception {
        File file = new File(urlString);
        if (!file.exists()) {
            throw new Exception("Could not load file " + file.getAbsolutePath());
        }
        return file.toURL();
    }
}