package org.fluentcodes.projects.elasticobjects.paths;
import com.google.gson.JsonSerializationContext;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.LogLevel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Path creates from a string a special of elements splitted by te delimiter
 * Created by Werner on 4.07.2020.
 */
public class PathElement {
    public static final Pattern modelPattern = Pattern.compile("^\\(([^\\)]*)\\)(.*)");
    public static final String BACK = "..";
    public static final String SAME = ".";
    public static final String MATCHER = "*";
    public static final String MATCHER_ALL = "+";
    public static final String ROOT_MODEL = "_rootmodel";
    public static final String LOGS = "_logs";
    public static final String ERROR_LEVEL = "_errorLevel";
    public static final String SERIALIZATION_TYPE = "_serializationType";
    public static final String LOG_LEVEL = "_logLevel";
    public static final String ROOT_CALLS = "_calls";
    public static final String CALLS = "_calls";
    public static final String CONFIG = "_config";
    private final String[] models;
    private final String key;

    public PathElement(final String path) {
        final Matcher matcher = PathElement.modelPattern.matcher(path);
        if (matcher.find()) {
            String modelKey = matcher.group(1);
            this.key = matcher.group(2);
            if (LOG_LEVEL.equals(key) ) {
                modelKey = LogLevel.class.getSimpleName();
            }
            else if (ERROR_LEVEL.equals(key) ) {
                modelKey = LogLevel.class.getSimpleName();
            }
            else if (SERIALIZATION_TYPE.equals(key) ) {
                modelKey = JSONSerializationType.class.getSimpleName();
            }
            if (modelKey == null || modelKey.isEmpty()) {
                models = new String[]{};
                return;
            }
            models = modelKey.split(",");
        }
        else {
            key = path;
            models = new String[]{};
        }
    }

    public static final PathElement OF_SERIALIZATION_TYPE() {
        return new PathElement(SERIALIZATION_TYPE);
    }
    public static final PathElement OF_LOG_LEVEL() {
        return new PathElement(LOG_LEVEL);
    }
    public static final PathElement OF_ERROR_LEVEL() {
        return new PathElement(ERROR_LEVEL);
    }
    public static final PathElement OF_LOGS() {
        return new PathElement(LOGS);
    }
    public static final PathElement OF_CALLS() {
        return new PathElement(CALLS);
    }


    /**
     * fieldnames starting with underscores will not mapped to a parent object.
     * @param fieldName the fieldName
     * @return true if empty or starting with "_"
     */
    public static boolean isParentNotSet(final String fieldName) {
        return fieldName == null || fieldName.isEmpty() || fieldName.startsWith("_");
    }

    public boolean isParentNotSet() {
        return key == null || key.isEmpty() || key.startsWith("_");
    }

    public static boolean isCallPath(final EO parentAdapter) {
        if (parentAdapter == null) {
            return false;
        }
        final String fieldName = parentAdapter.getParentKey();
        return ROOT_CALLS.equals(fieldName) || CALLS.equals(fieldName);
    }

    public boolean isBack() {
        return BACK.equals(key);
    }
    public boolean isSame() {
        return SAME.equals(key);
    }

    public String[] getModels() {
        return models;
    }

    public String getKey() {
        return key;
    }
    public boolean hasModels() {
        return models != null && models.length>0;
    }

    public boolean isRootModel() {
        return ROOT_MODEL.equals(getKey());
    }

    @Override
    public String toString(){
        if (!hasModels()) {
            return key;
        }
        return "(" + String.join(",", models) + ")" + key;
    }
}
