package org.fluentcodes.projects.elasticobjects.executor.statics;

import org.fluentcodes.projects.elasticobjects.executor.ValueParamsHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by werner.diwischek on 06.02.18.
 */
public class ValuesContent extends ValueParamsHelper {
    protected static final Pattern linkPattern = Pattern.compile("(.*)://(.*)");
    protected static final Map<String, String> PROTOCOLS = initProtocols();

    private static final Map<String, String> initProtocols() {
        Map<String, String> result = new HashMap<>();
        result.put("http", "remote");
        result.put("https", "remote");
        result.put("wiki", "wiki");
        result.put("git", "git");
        return result;
    }

}
