package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.Models;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    public static final Map<String,String[]> keyClassMap = initKeyClassMap();

    public static final PathElement OF_ERROR_LEVEL = new PathElement(ERROR_LEVEL);
    public static final PathElement OF_LOG_LEVEL = new PathElement(LOG_LEVEL);
    public static final PathElement OF_ROOT_MODEL = new PathElement(ROOT_MODEL);

    private final String key;
    private final String[] modelsArray;
    private final Boolean call;
    private String callTargetPath;

    public PathElement(final String compositionKey) {
        this(compositionKey, null);
    }

    public PathElement(final PathElement pathElement, final Models fieldModels) {
        this.key = pathElement.getKey();
        this.call = false;
        if (fieldModels == null || fieldModels.isEmpty()) {
            this.modelsArray = pathElement.getModelsArray();
        }
        else {
            this.modelsArray = fieldModels.getModelsStringArray();
        }
    }

    public PathElement(final String compositionKey, Class... modelClasses) {
        if (PathElement.ROOT_MODEL.equals(compositionKey)) {
            this.call = false;
            this.key = compositionKey;
            this.modelsArray = keyClassMap.get(key);
            return;
        }
        final Matcher matcher = PathElement.modelPattern.matcher(compositionKey);
        if (matcher.find()) {
            String modelString = matcher.group(1);
            this.call = modelString.endsWith("Call") ? true : false;
            this.key = matcher.group(2);
            if (modelString != null && !modelString.isEmpty()) {
                if (keyClassMap.containsKey(key)) {
                    this.modelsArray = keyClassMap.get(key);
                    if (!modelString.equals(Arrays.stream(modelsArray).collect(Collectors.joining(",")))) {
                        throw new EoException("Mismatch between setted model '" + modelString + "' and fixed model '" + key + "'");
                    }
                    return;
                }
                this.modelsArray = modelString.split(",");
                return;
            }
            this.modelsArray = keyClassMap.get(key);
            return;
        }
        else {
            this.key = compositionKey;
            this.call = false;
            if (modelClasses == null|| modelClasses.length==0) {
                this.modelsArray = keyClassMap.get(key);
                return;
            }
            this.modelsArray = new String[modelClasses.length];
            int counter = 0;
            for (Class modelClass: modelClasses) {
                this.modelsArray[counter] = modelClasses[counter].getSimpleName();
                counter++;
            }
        }
        //if (key.isEmpty())  throw new EoException("An empty key derived for input '" + compositionKey + "'");
    }

    private static final Map<String, String[]> initKeyClassMap() {
        final Map<String, String[]> keyEnumMap = new HashMap<>();
        keyEnumMap.put(LOG_LEVEL, new String[] {LogLevel.class.getSimpleName()});
        keyEnumMap.put(LOGS, new String[] {List.class.getSimpleName()});
        keyEnumMap.put(CALLS, new String[] {List.class.getSimpleName()});
        keyEnumMap.put(SERIALIZATION_TYPE, new String[] {JSONSerializationType.class.getSimpleName()});
        keyEnumMap.put(ERROR_LEVEL, new String[] {LogLevel.class.getSimpleName()});
        keyEnumMap.put(ROOT_MODEL, new String[] {String.class.getSimpleName()});
        return keyEnumMap;
    }

    /**
     * fieldnames starting with underscores will not mapped to a parent object.
     * @param key the fieldName
     * @return true if empty or starting with "_"
     */
    public static boolean isParentNotSet(final String key) {
        return key == null || key.startsWith("_");
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

    protected boolean isCall() {
        return call;
    }

    protected void  createCallTargetPath(final EO parentEo) {
        if (parentEo==null) {
            throw new EoException("Could not create target path.");
        }
        if (callTargetPath!=null) {
            throw new EoInternalException("target path should be created one time.");
        }
        this.callTargetPath = parentEo.getPathAsString() + Path.DELIMITER + key;
    }

    protected String getCallTargetPath() {
        return getCallTargetPath();
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
        return "(" + Arrays.stream(modelsArray).collect(Collectors.joining(",")) + ")" + key;
    }
}
