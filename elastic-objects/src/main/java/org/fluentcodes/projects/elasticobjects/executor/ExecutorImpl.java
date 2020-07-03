package org.fluentcodes.projects.elasticobjects.executor;

import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.EoException;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.utils.ReplaceUtil;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract class for Executors. Actually only
 * <li>
 * <ul>{@link CallExecutor} for calling Actions with a key defined in the config cache</ul>
 * <ul> and {@link ExecutorValues} for static methods with String return values</ul>
 * </li>
 * are defined.
 *
 * @author Werner Diwischek
 * @version 0.01
 * @date 2.5.2018
 */
public abstract class ExecutorImpl {
    private ExecutorItem executorItem;
    private Map attributes;
    private ExecutorItem.TYPES type;


    public ExecutorImpl(Map attributes, ExecutorItem.TYPES type)  {
        this.type = type;
        if (attributes.get(Executor.EXECUTE) == null) {
            throw new EoException("No executor defined " + this.toString());
        }
        this.attributes = attributes;
        this.executorItem = new ExecutorItem((String) attributes.get(Executor.EXECUTE), this.type);
    }

    public ExecutorImpl(final Class executorClass, final String method, final String... args)  {
        this.type = ExecutorItem.TYPES.value;
        this.executorItem = new ExecutorItem(executorClass, method, args);
        this.attributes = new HashMap();
    }

    public static final List<String> getPathList(String path, EO adapter, Map attributes) {
        List<String> pathList = new ArrayList<>();
        if (path == null || path.isEmpty() || path.equals(".")) {
            pathList.add(".");
            return pathList;
        }
        String myPath = ReplaceUtil.replace(path, adapter, attributes);
        if (!myPath.matches(".*[\\*|+].*")) {
            pathList.add(myPath);
            return pathList;
        }
        if (myPath.startsWith("..")) {
            Path pathAsPath = new Path(myPath);
            boolean isMoveBack = true;
            while (pathAsPath.hasFirstEntry() && isMoveBack) {
                String firstEntry = pathAsPath.getFirstEntry();
                if (!firstEntry.equals("..")) {
                    isMoveBack = false;
                } else {
                    try {
                        adapter = adapter.getChild(firstEntry);
                        pathAsPath = pathAsPath.removeFirst();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return pathList;
                    }
                }
            }
            myPath = pathAsPath.directory(false);
            String tagPath = ReplaceUtil.replace(myPath, adapter, attributes);
            try {
                pathList = adapter.filterPaths(tagPath);
            } catch (Exception e) {
                adapter.error("Error for " + tagPath + ":" + e.getMessage());
                return pathList;
            }
        } else {
            String tagPath = ReplaceUtil.replace(myPath, adapter, attributes);
            try {
                pathList = adapter.filterPaths(tagPath);
            } catch (Exception e) {
                adapter.error("Error for " + tagPath + ":" + e.getMessage());
                return pathList;
            }
        }

        if (pathList.size() == 0) {
            pathList.add(".");
        }
        return pathList;
    }

    protected void mapAttributes(Map attributes)  {
        this.attributes = attributes;
    }

    public Map getAttributes() {
        if (attributes == null) {
            return new HashMap();
        }
        return attributes;
    }

    public Object getAttribute(String key) {
        if (attributes == null) {
            return null;
        }
        return attributes.get(key);
    }

    protected boolean hasExecute(Map attributes) {
        if (attributes.get(Executor.EXECUTE) == null) {
            return false;
        }
        return !((String) attributes.get(Executor.EXECUTE)).isEmpty();
    }

    protected ExecutorItem getExecutorItem() {
        return executorItem;
    }

    protected void setExecutorItem(ExecutorItem executorItem) {
        this.executorItem = executorItem;
    }


    protected boolean hasLoopPath() {
        return getLoopPath() != null && !getLoopPath().isEmpty();
    }

    public String getLoopPath() {
        return (String) attributes.get(EO_STATIC.F_LOOP_PATH);
    }

    public void setLoopPath(final Object entry) {
        if (hasLoopPath()) {
            return;
        }
        if (entry == null || ((String) entry).isEmpty()) {
            return;
        }
        attributes.put(EO_STATIC.F_LOOP_PATH, ScalarConverter.toString(entry));
    }

    protected boolean hasPath() {
        return getPath() != null && !getPath().isEmpty();
    }

    public String getPath() {
        return (String) attributes.get(EO_STATIC.F_PATH);
    }

    public void setPath(final Object entry) {
        if (hasPath()) {
            return;
        }
        if (entry == null || ((String) entry).isEmpty()) {
            return;
        }
        attributes.put(EO_STATIC.F_PATH, ScalarConverter.toString(entry));
    }

    protected boolean hasMapPath() {
        return getMapPath() != null && !getMapPath().isEmpty();
    }

    protected String getMapPath() {
        return (String) attributes.get(EO_STATIC.F_MAP_PATH);
    }

    protected void setMapPath(final Object entry) {
        if (hasMapPath()) {
            return;
        }
        if (entry == null || ((String) entry).isEmpty()) {
            return;
        }
        attributes.put(EO_STATIC.F_MAP_PATH, ScalarConverter.toString(entry));
    }

    protected List<String> getPathList(EO adapter) {
        String loopPath = ReplaceUtil.replace(getLoopPath(), adapter, attributes);
        return getPathList(loopPath, adapter, null);
    }
}
