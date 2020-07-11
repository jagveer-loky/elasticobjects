package org.fluentcodes.projects.elasticobjects.executor;

import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.ResourceCall;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.LogLevel;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.utils.ReplaceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for call executions.
 *
 * @author Werner Diwischek
 * @version 0.01
 * @date 2.5.2018
 */
public class CallExecutorImpl implements CallExecutor {
    private String sourcePath;
    private String targetPath;
    private boolean overwrite;
    private LogLevel logLevel;
    private Call call;

    public CallExecutorImpl() {
    }

    public CallExecutorImpl(final Call call) {
        this.call = call;
    }

    @Override
    public Object transform(Object object) {
        return object;
    }

    public Object execute(EO eo) {
        if (eo == null) {
            String error = "adapter is null!";
            return error;
        }
        EO source = eo;
        if (this.sourcePath == null) {
            this.sourcePath = ".";
        }
        List<String> sourcePaths = getPathList(sourcePath, eo);
        EO target = eo;
        boolean createTarget = sourcePaths.size() > 1 && targetPath != null;
        if (createTarget) {
            target = eo.setPathValue(targetPath, eo.getModel().create());
        }
        Object returnValue = null;
        Long startTime = System.currentTimeMillis();
        for (String sourcePath : sourcePaths) {
            if (sourcePath != null) {
                source = eo.getEo(sourcePath);
            }
            Object value = transform(getCall().execute(source));
            if (createTarget) {
                target.set(value, sourcePath);
            }
            else if (targetPath != null) {
                eo.set(value, targetPath);
            }
            else {
                source.mapObject(value);
            }
            returnValue = value;
        }
        Long duration = System.currentTimeMillis() - startTime;
        eo.info("Successfully executed within " + duration.toString() + " ms.");
        return returnValue;
    }

    public static final List<String> getPathList(String path, EO eo) {
        List<String> pathList = new ArrayList<>();
        if (path == null || path.isEmpty() || path.equals(".")) {
            pathList.add(".");
            return pathList;
        }
        String myPath = ReplaceUtil.replace(path, eo);
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
                        eo = eo.getEo(firstEntry);
                        pathAsPath = pathAsPath.removeFirst();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return pathList;
                    }
                }
            }
            myPath = pathAsPath.directory(false);
            String tagPath = ReplaceUtil.replace(myPath, eo);
            try {
                pathList = eo.filterPaths(tagPath);
            } catch (Exception e) {
                eo.error("Error for " + tagPath + ":" + e.getMessage());
                return pathList;
            }
        } else {
            String tagPath = ReplaceUtil.replace(myPath, eo);
            try {
                pathList = eo.filterPaths(tagPath);
            } catch (Exception e) {
                eo.error("Error for " + tagPath + ":" + e.getMessage());
                return pathList;
            }
        }

        if (pathList.size() == 0) {
            pathList.add(".");
        }
        return pathList;
    }

    @Override
    public String getSourcePath() {
        return sourcePath;
    }

    @Override
    public CallExecutor setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
        return this;
    }

    @Override
    public String getTargetPath() {
        return targetPath;
    }

    @Override
    public CallExecutor setTargetPath(String targetPath) {
        this.targetPath = targetPath;
        return this;
    }

    protected Call getCall() {
        return call;
    }

    public void setCall(ResourceCall call) {
        this.call = call;
    }
}
