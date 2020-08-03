/**
 * <p>
 * Some static utility classes....
 * <p>
 * http://www.jdbee.org<br/>
 * date 10.3.2012<br/>
 *
 * @author Werner Diwischek
 * @version 0.1
 */
package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.executor.CallExecutorImpl;
import org.fluentcodes.projects.elasticobjects.config.Models;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ReplaceUtil {
    private static final Logger LOG = LogManager.getLogger(ReplaceUtil.class);
    private static final Pattern attributePattern = Pattern.compile("(?s)\\s*([^ ]*?)(=)\"([^\"]*?)\"");
    private static final Pattern varPattern = Pattern.compile("\\$\\[(.*?)\\]");
    private static final Map<String, String> SYSTEM = populateSystem();
    private static SimpleDateFormat dayTimeFormat = new SimpleDateFormat("yyyyMMdd-hhmmss");
    private static SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");

    private static final Map<String, String> populateSystem() {
        Map<String, String> result = new HashMap<>();
        if (System.getenv("TMPDIR") != null) {
            result.put("TMP", System.getenv("TMPDIR"));
        } else if (System.getenv("TMPDIR") != null) {
            result.put("TMP", System.getenv("TMPDIR"));
        }

        if (System.getenv("HOMEPATH") != null) {
            result.put("HOME", System.getenv("HOMEPATH"));
        }
        return result;
    }

    public static String replace(String toParse) {
        return replace(toParse,null);
    }


    /**
     * Replaces ${param}br/>
     * changing the static final varPattern you can use the var you prefer
     * varPattern = Pattern.compile("${}(.*?)]"); would look for $param
     *
     * @param toParse
     * @return
     */

    public static String replace(final String toParse, final EO eo) {
        if (toParse == null || toParse.isEmpty()) {
            return "";
        }
        if (!toParse.contains("$[")) {
            LOG.debug("no placeholder $[ $# $! found  : " + toParse);
            return toParse;
        }

        StringBuffer result = new StringBuffer();
        String parseString = toParse;
        Matcher match = varPattern.matcher(parseString);
        int start = 0;
        while (match.find()) {
            int findStart = match.start();
            result.append(parseString.substring(start, findStart));
            start = match.end();
            String value = match.group();
            String pathOrKey = match.group(1);
            // default value when no value could be derived
            if (pathOrKey.contains("|")) {
                String[] pathAndDefault = pathOrKey.split("\\|");
                if (pathAndDefault.length == 1) {
                    value = "";
                } else {
                    value = pathAndDefault[1];
                }
                pathOrKey = pathAndDefault[0];
            }
            pathOrKey = pathOrKey.replaceAll("\\\\", "/");
            if (pathOrKey.startsWith("@")) {
                value = getSystemValue(pathOrKey.replaceAll("^@", ""));
            }
            else if (pathOrKey.startsWith("%")) {
                value = System.getenv(pathOrKey.replaceAll("^%", ""));
            }
            else if (eo != null) {
                if (pathOrKey.startsWith("(")) {
                    try {
                        value = ScalarConverter.toString(makeCall(toParse, pathOrKey, match, eo));
                    } catch (Exception e) {
                        eo.debug(e.getMessage());
                        value = "!!" + e.getMessage() + "!!";
                    }
                }
                else if (pathOrKey.startsWith("_")) {
                    return pathOrKey
                            .replaceAll("_value", eo.get().toString())
                            .replaceAll("_parent", eo.getParentKey());

                }
                else {

                    try {
                        value = ScalarConverter.toString(eo.get(pathOrKey));
                        if (value == null) {
                            value = "!! not found '" + pathOrKey + "' in '" + eo.getPathAsString() + "'!!";
                        }
                    } catch (Exception e) {
                        eo.debug(e.getMessage());
                        value = "!!" + e.getMessage() + "!!";
                    }
                }
            }
            else {
                value = "!!Could not find path '" + pathOrKey + "'!!";
            }
            result.append(value);
        }
        result.append(parseString.substring(match., parseString.length()));
        return result.toString();
    }

    private static Object makeCall(final String toParse, String callPath, final Matcher match, final EO eo) {
        String content = "";
        if (!callPath.endsWith(Path.DELIMITER)) {
            content = findContent(toParse, match);
        }
        callPath = callPath.replaceAll(Path.DELIMITER + "$","");

        final String callPathCore = callPath
                .replaceAll(" .*", "")
                .replaceAll(Path.DELIMITER + "/$", "");

        Map<String, String> callVars = extractAttributes(callPath.replaceAll(".* ",""));
        Path path = new Path(callPathCore);
        if (!path.hasModel()) {
            throw new EoException("Could not find call in path " + callPath);
        }

        Models models = new Models(eo.getConfigsCache(), path.getModels());
        Object callObject = models.create();
        if (!(callObject instanceof Call)) {
            throw new EoException("Object is not instance of call but " + callObject.getClass().getSimpleName() + "!");
        }
        if (!content.isEmpty() && (callObject instanceof TemplateCall)) {
            ((TemplateCall)callObject).setContent(content);
        }

        EO callEo = EoRoot.ofValue(eo.getConfigsCache(), callObject);
        callEo.mapObject(callVars);
        Object value = new CallExecutorImpl().execute(eo.getEo(path), (Call) callEo.get());
        return value.toString();
    }

    private static final String findContent(final String template, final Matcher matcherFind) {
        StringBuilder content = new StringBuilder();
        int hierarchy = 1;
        int start = matcherFind.end();
        while (matcherFind.find()) {
            content.append(template.substring(start, matcherFind.start()));
            start = matcherFind.end();
            String found = matcherFind.group(1);
            String foundAll = matcherFind.group();
            if (found.equals("/")) {
                hierarchy--;
            }
            else if (found.startsWith("(") && !found.endsWith(Path.DELIMITER)){
                hierarchy++;
            }
            if (hierarchy == 0) {
                return content.toString();
            } else {
                content.append(foundAll);
            }
        }
        throw new EoException("Could not find closing tag for parseString");
    }

    private static final Map<String, String> extractAttributes(final String callPath) {
        Map<String, String> attributes = new LinkedHashMap<>();
        Matcher attributeMatcher = attributePattern.matcher(callPath);
        while (attributeMatcher.find()) {
            String key = attributeMatcher.group(1);
            String operator = attributeMatcher.group(2);
            String value = attributeMatcher.group(3);
            attributes.put(key, value);
        }
        return attributes;
    }

    public static final String getSystemValue(String key) {
        if (key == null || key.isEmpty()) {
            return null;
        }
        if (SYSTEM.get(key) != null) {
            return SYSTEM.get(key);
        }
        if (key.equals("TMP")) {
            return SYSTEM.get("TEMP");
        }
        if (key.equals("TEMP")) {
            return SYSTEM.get("TMP");
        }
        return null;
    }


}
