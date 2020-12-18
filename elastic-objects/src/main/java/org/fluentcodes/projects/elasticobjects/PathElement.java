package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.Models;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * PathElement encapsulate eo local values of eo and its parent.
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
    public static final String CALLS = "_calls";
    public static final String TEMPLATE = "_template";
    public static final String IN_TEMPLATE = "_";
    public static final String CONFIG = "_config";

    private String key;
    private String[] modelsArray;

    public PathElement(final String compositionKey) {
        if (PathElement.ROOT_MODEL.equals(compositionKey)) {
            this.key = compositionKey;
            return;
        }
        final Matcher matcher = PathElement.modelPattern.matcher(compositionKey);
        String modelKey = null;
        if (matcher.find()) {
            modelKey = matcher.group(1);
            this.key = matcher.group(2);
        }
        else {
            this.key = compositionKey;
            if (LOG_LEVEL.equals(key) ) {
                modelKey = LogLevel.class.getSimpleName();
            }
            else if (ERROR_LEVEL.equals(key) ) {
                modelKey = LogLevel.class.getSimpleName();
            }
            else if (CALLS.equals(key) ) {
                modelKey = List.class.getSimpleName();
            }
            else if (SERIALIZATION_TYPE.equals(key) ) {
                modelKey = JSONSerializationType.class.getSimpleName();
            }
            if (modelKey == null || modelKey.isEmpty()) {
                modelsArray = new String[]{};
                return;
            }
        }
        modelsArray = modelKey.split(",");
    }


    public PathElement(final String compositionKey, Class... modelClasses) {
        this(compositionKey);
        if (modelsArray!=null && modelsArray.length>0) {
            return;
        }
        if (modelClasses == null|| modelClasses.length==0) {
            return;
        }
        if (modelClasses.length==1 && modelClasses[0].equals(Map.class)) {
            return;
        }
        this.modelsArray = new String[modelClasses.length];
        int counter = 0;
        for (Class modelClass: modelClasses) {
            this.modelsArray[counter] = modelClasses[counter].getSimpleName();
            counter++;
        }
    }

    /**
     * fieldnames starting with underscores will not mapped to a parent object.
     * @param key the fieldName
     * @return true if empty or starting with "_"
     */
    public static boolean isParentNotSet(final String key) {
        return key == null || key.isEmpty() || key.startsWith("_");
    }

    public static boolean isParentSet(final String key) {
        return !isParentNotSet(key);
    }

    public boolean isParentNotSet() {
        return isParentNotSet(this.key);
    }
    public boolean isParentSet() {
        return !isParentNotSet();
    }

    protected boolean isCallDirectory() {
        return CALLS.equals(this.key);
    }

    public boolean isBack() {
        return BACK.equals(key);
    }

    public boolean isSame() {
        return SAME.equals(key);
    }

    public boolean isFilter() {
        return key.contains(MATCHER) || key.contains(MATCHER_ALL);
    }

    public String[] getModelsArray() {
        return modelsArray;
    }

    public Models getModels(EOConfigsCache cache) {
        if (hasModelArray()) {
            return new Models(cache, modelsArray);
        }
        return null;
    }
    public String getKey() {
        return key;
    }

    public boolean hasModelArray() {
        return modelsArray != null && modelsArray.length>0;
    }

    public boolean isRootModel() {
        return ROOT_MODEL.equals(getKey());
    }

    @Override
    public String toString() {
        if (!hasModelArray()) {
            return key;
        }
        return "(" + modelsArray.toString() + ")" + key;
    }
}
