package org.fluentcodes.projects.elasticobjects.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.calls.TemplateCall;
import org.fluentcodes.projects.elasticobjects.calls.ValueCall;
import org.fluentcodes.projects.elasticobjects.paths.Path;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

/**
 * Created by Werner on 02.07.2014.
 * Refactored 1.5.2018: Refactored parsing as executor
 */

public class ExecutorListTemplate extends ExecutorList {
    public static final String VALUES = "values";
    public static final String MAP = "map";
    public static final String SET = "set";

    private static final Logger LOG = LogManager.getLogger(ExecutorListTemplate.class);
    private static final Pattern actionPattern = Pattern.compile("(?s)<([/]*)(call)([^>]*)([/]*>)");
    private static final Pattern attributePattern = Pattern.compile("(?s)\\s*([^ ]*?)(=)\"([^\"]*?)\"");
    private final Map parentAttributes;

    public ExecutorListTemplate(String template) throws Exception {
        this(template, new HashMap());
    }

    public ExecutorListTemplate(String template, Map parentAttributes) throws Exception {
        super();
        this.parentAttributes = parentAttributes;
        createTemplateList(template);
    }

    /**
     * This will create the executor list from a template string.
     */
    private void createTemplateList(final String template) throws Exception {
        Matcher actionMatcher = actionPattern.matcher(template);
        int start = 0;
        while (actionMatcher.find()) {
            String before = template.substring(start, actionMatcher.start());
            start = actionMatcher.end();
            if (!before.isEmpty()) {
                super.addContentTemplateAction(before, parentAttributes);
            }
            Map<String, String> tagAttributes = getAttributes(actionMatcher.group(3));
            tagAttributes.put(F_INSERT, actionMatcher.group(0));
            if (!actionMatcher.group(3).endsWith(Path.DELIMITER)) {  // have some contents <call>content</call>
                String content = findEndTag(template, actionMatcher);
                start = actionMatcher.end();
                //contentToParse = parts[1];
                if (tagAttributes.get(A_KEEP) == null) {
                    //actionMatcher = actionPattern.matcher(contentToParse);
                    if (tagAttributes.get(VALUES) != null && tagAttributes.get(VALUES).equals(MAP)) {
                        tagAttributes.put(F_VALUE, content);
                        tagAttributes.put(Executor.EXECUTE, ValueCall.MAP("empty"));
                        super.add(tagAttributes);
                    } else if (tagAttributes.get(VALUES) != null && tagAttributes.get(VALUES).equals(SET)) {
                        tagAttributes.put(F_VALUE, content);
                        tagAttributes.put(Executor.EXECUTE, ValueCall.SET("empty"));
                        super.add(tagAttributes);
                    } else {
                        tagAttributes.put(F_CONTENT, content);
                        tagAttributes.put(Executor.EXECUTE, TemplateCall.EXECUTE_TEMPLATE);
                        //super.add(merge(parentAttributes, tagAttributes));
                        super.add(tagAttributes);
                    }
                    continue;
                }
            }
            //actionMatcher = actionPattern.matcher(contentToParse);
            if (tagAttributes.get(Executor.EXECUTE) != null) {
                super.add(tagAttributes);
            } else if (tagAttributes.get(A_TEMPLATE_KEY) != null) {
                String templateKey = tagAttributes.get(A_TEMPLATE_KEY);
                if (!templateKey.isEmpty()) {
                    tagAttributes.put(Executor.EXECUTE, "TemplateCall.execute(" + templateKey + ")");
                    super.add(tagAttributes);
                }
            } else {
                throw new Exception("Insert without execute! " + tagAttributes.toString());
            }
        }
        super.addContentTemplateAction(template.substring(start, template.length()), parentAttributes);
    }

    /**
     * Creates a key fileName hashmap from attribute getSerialized
     *
     * @param attributeContent
     * @return
     */
    private final Map<String, String> getAttributes(final String attributeContent) {
        Map<String, String> result = new HashMap<>();
        Matcher attributeMatcher = attributePattern.matcher(attributeContent);
        while (attributeMatcher.find()) {
            String key = attributeMatcher.group(1);
            String operator = attributeMatcher.group(2);
            String value = attributeMatcher.group(3);
            if (operator.equals(":=")) {
                //value=replaceVars(value);
                //this.setValue(key, value);
            }

            result.put(key, value);
        }
        return result;
    }

    private Map merge(Map parentAttributes, Map tagAttributes) {
        if (parentAttributes == null || parentAttributes.isEmpty()) {
            return tagAttributes;
        }
        for (Object key: parentAttributes.keySet()) {
            if (tagAttributes.containsKey(key)) {
                continue;
            }
            tagAttributes.put(key, parentAttributes.get(key));
        }
        return tagAttributes;
    }

    private String findEndTag(String template, Matcher matcherFind) throws Exception {
        StringBuilder content = new StringBuilder();
        int hierarchy = 1;
        int start = matcherFind.end();
        while (matcherFind.find()) {
            content.append(template.substring(start, matcherFind.start()));
            start = matcherFind.end();
            if (matcherFind.group(1).equals("/")) {
                hierarchy--;
            } else {
                if (!matcherFind.group(3).endsWith("/")) {
                    hierarchy++;
                }
            }
            if (hierarchy == 0) {
                return content.toString();
            } else {
                content.append(matcherFind.group());
            }
        }
        throw new Exception("Could not find closing tag for parseString");

    }

}

