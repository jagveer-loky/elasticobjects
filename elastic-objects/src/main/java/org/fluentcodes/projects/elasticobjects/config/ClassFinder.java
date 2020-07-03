package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.EoException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class ClassFinder {

    private String SEARCH = ".*/HashMap.class";

    private StringBuilder scanLog;
    private Map<String, String> mapKeyClass;
    private Map<String, String> mapKeyOrigin;

    private List<String> jarFilter = new ArrayList<>();
    private List<String> jarExcludeFilter = new ArrayList<>();
    private List<String> fileFilter = new ArrayList<>();
    private List<String> fileExcludeFilter = new ArrayList<>();
    private List<String> pathFilter = new ArrayList<>();
    private List<String> pathExcludeFilter = new ArrayList<>();

    // https://jaxenter.de/classpath-scan-im-eigenbau-aus-der-java-trickkiste-12963

    public void find()  {
        scanLog = new StringBuilder();
        mapKeyClass = new TreeMap<>();
        mapKeyOrigin = new TreeMap<>();
        int counter = 0;
        long start = System.currentTimeMillis();
        StringBuilder log = new StringBuilder();
        for (URL url : getContextClassLoaderUrls()) {
            File f = new File(url.getPath());
            if (f.isDirectory()) {
                counter += visitFile(f, "-", log);
            } else {
                counter += visitJar(url, log);
            }
        }
        start = System.currentTimeMillis() - start;
        scanLog.append("Scanned " + counter + " classes within " + start + " ms.\n");
        scanLog.append(log);
        for (String key : mapKeyClass.keySet()) {
            scanLog.append("\n");
            scanLog.append("From " + mapKeyOrigin.get(key) + ":\n");
            scanLog.append(key + " - " + mapKeyClass.get(key) + "\n");
        }
    }

    public String scanLog() {
        return scanLog.toString();
    }

    public ClassFinder addJarFilter(final String filter) {
        jarFilter.add(filter);
        return this;
    }

    public ClassFinder addJarExcludeFilter(final String filter) {
        jarExcludeFilter.add(filter);
        return this;
    }

    public ClassFinder addFileFilter(final String filter) {
        fileFilter.add(filter);
        return this;
    }

    public ClassFinder addFileExcludeFilter(final String filter) {
        fileExcludeFilter.add(filter);
        return this;
    }

    public ClassFinder addPathFilter(final String filter) {
        pathFilter.add(filter);
        return this;
    }

    public ClassFinder addPathExcludeFilter(final String filter) {
        pathExcludeFilter.add(filter);
        return this;
    }

    public Map<String, String> getMapKeyClass() {
        return mapKeyClass;
    }

    public Map<String, String> getMapKeyOrigin() {
        return mapKeyOrigin;
    }

    private List<URL> getContextClassLoaderUrls() {
        final List<URL> result = new ArrayList<>();
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        while (cl != null) {
            if (cl instanceof URLClassLoader) {
                URL[] urls = ((URLClassLoader) cl).getURLs();
                result.addAll(Arrays.asList(urls));
            }
            cl = cl.getParent();
        }
        return result;
    }


    private void addToMap(final String className, final String source) {
        if (!filter(className, this.pathFilter)) {
            return;
        }
        if (!this.pathExcludeFilter.isEmpty() && filter(className, this.pathExcludeFilter)) {
            return;
        }
        String classKey = className.replaceAll("\\.class", "");
        classKey = classKey.replaceAll(".*/", "");
        mapKeyClass.put(classKey, className);
        mapKeyOrigin.put(classKey, source);
    }


    private boolean filter(final String name, List<String> filterEntries) {
        if (filterEntries.isEmpty()) {
            return true;
        }
        for (final String filter : filterEntries) {
            if (name.matches(filter)) {
                return true;
            }
        }
        return false;
    }

    private int visitFile(final File file, final String path, final StringBuilder builder) {
        if (!filter(file.getName(), fileFilter)) {
            return 0;
        }
        if (!fileExcludeFilter.isEmpty() && filter(file.getName(), fileExcludeFilter)) {
            return 0;
        }

        int counter = 0;

        if (file.isDirectory()) {
            final File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    if ("-".equals(path)) {
                        counter += visitFile(child, "", builder);
                    } else if ("".equals(path)) {
                        counter += visitFile(child, file.getName(), builder);
                    } else {
                        counter += visitFile(child, path + "/" + file.getName(), builder);
                    }
                }
            }
        } else if (file.getName().endsWith(".class")) {
            if (file.getName().contains("$")) {
                return counter;
            }
            addToMap(path + "/" + file.getName(), file.getName());
            counter++;
        }
        if (counter > 0) {
            if (file.isDirectory()) {
                builder.append("Found " + counter + " classes in " + file.getAbsolutePath() + ".\n");
            }
        }
        return counter;
    }

    private int visitJar(URL url, StringBuilder builder)  {
        int counter = 0;
        if (!filter(url.getFile(), jarFilter)) {
            builder.append("Skip " + url.getFile() + ".\n");
            return counter;
        }
        if (!jarExcludeFilter.isEmpty() && filter(url.getFile(), jarExcludeFilter)) {
            builder.append("Skip due to exclusion " + url.getFile() + ".\n");
            return counter;
        }
        try (InputStream urlIn = url.openStream();
             JarInputStream jarIn = new JarInputStream(urlIn)) {
            JarEntry entry;
            while ((entry = jarIn.getNextJarEntry()) != null) {
                if (entry.getName().endsWith(".class")) {
                    if (entry.getName().contains("$")) {
                        continue;
                    }
                    addToMap(entry.getName(), url.getFile());
                    counter++;
                }
            }
        }
        catch (Exception e) {
            throw new EoException(e);
        }
        builder.append("Found " + counter + " classes in " + url.getFile() + ".\n");
        return counter;
    }
}
