package org.fluentcodes.projects.elasticobjects.paths;
import java.util.Arrays;
import java.util.List;
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
    private final List<String> models;
    private final String pathElement;

    public PathElement(final String path) {
        final Matcher matcher = PathElement.modelPattern.matcher(path);
        if (matcher.find()) {
            final String modelKey = matcher.group(1);
            this.pathElement = matcher.group(2);
            if (modelKey == null || modelKey.isEmpty()) {
                models = null;
                return;
            }
            models = Arrays.asList(modelKey.split(","));
        }
        else {
            pathElement = path;
            models = null;
        }
    }

    public List<String> getModels() {
        return models;
    }

    public String getPathElement() {
        return pathElement;
    }
    public boolean hasModels() {
        return models != null && !models.isEmpty();
    }
}
