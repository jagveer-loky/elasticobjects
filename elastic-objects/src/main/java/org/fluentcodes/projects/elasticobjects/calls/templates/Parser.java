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
import org.fluentcodes.projects.elasticobjects.calls.ExecutorCallImpl;
import org.fluentcodes.projects.elasticobjects.calls.values.ValueCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Models;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class Parser {
    private static final Logger LOG = LogManager.getLogger(Parser.class);
    private static final String TEMPLATE_CALL_CHAR = "^&";
    private static final String VALUE_CALL_CHAR = "^#";
    private static final String SYSTEM_CHAR = "^@";
    private static final String ENV_CHAR = "^%";
    private static final Map<String, String> SYSTEM = populateSystem();
    private final String template;
    private Matcher match;
    private int end;

    public Parser(final String template) {
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }

    public int getEnd() {
        return end;
    }

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

    public String parse(Pattern varPattern) {
        return parse(null, varPattern);
    }


    /**
     * Replaces ${param}br/>
     * changing the static final varPattern you can use the var you prefer
     * varPattern = Pattern.compile("${}(.*?)]"); would look for $param
     *
     * @param eo
     * @return
     */

    public String parse(final EO eo, Pattern varPattern) {
        if (template == null || template.isEmpty()) {
            return "";
        }
        this.match = varPattern.matcher(template);
        StringBuffer result = new StringBuffer();
        String parseString = template;
        end = 0;
        while (match.find()) {
            int findStart = match.start();
            result.append(parseString.substring(end, findStart));
            end = match.end();
            String value = match.group();
            String pathOrKey = match.group(1);
            String finish = match.group(2);
            result.append(replace(eo,pathOrKey,finish));
        }
        result.append(parseString.substring(end, parseString.length()));
        return result.toString();
    }

    private String replace(final EO eo, String pathOrKey, final String finish) {
        String value = null;
        if (pathOrKey.contains("|")) {
            String[] pathAndDefault = pathOrKey.split("\\|");
            if (pathAndDefault.length == 1) {
                value = "";
            } else {
                value = pathAndDefault[1];
            }
            pathOrKey = pathAndDefault[0];
        }
        pathOrKey = pathOrKey
                .replaceAll("\\\\", "/")
                .replaceAll(TEMPLATE_CALL_CHAR,"(TemplateCall)")
                .replaceAll(VALUE_CALL_CHAR ,"(ValueCall)");

        if (eo != null && pathOrKey.startsWith("(")) {
            try {
                value = ScalarConverter.toString(makeCall(pathOrKey, finish, eo));
            } catch (Exception e) {
                eo.debug(e.getMessage());
                value = "!!" + e.getMessage() + "!!";
            }
        }
        else if (eo != null && pathOrKey.startsWith("_")) {
            value = pathOrKey
                    .replaceAll("_value", eo.get().toString())
                    .replaceAll("_parent", eo.getParentKey());
        }

        else if (pathOrKey.startsWith("@")) {
            value = getSystemValue(pathOrKey.replaceAll(SYSTEM_CHAR, ""));
        }

        else if (pathOrKey.startsWith("%")) {
            value = System.getenv(pathOrKey.replaceAll(ENV_CHAR, ""));
        }

        else if (eo != null){
            try {
                pathOrKey = pathOrKey
                        .replaceAll("_value", eo.get().toString())
                        .replaceAll("_parent", eo.getParentKey());
                value = ScalarConverter.toString(eo.get(pathOrKey));
                if (value == null) {
                    value = "!! not found '" + pathOrKey + "' in '" + eo.getPathAsString() + "'!!";
                }
            } catch (Exception e) {
                eo.debug(e.getMessage());
                value = "!!" + e.getMessage() + "!!";
            }
        }
        else {
            value = "!!Could not find path '" + pathOrKey + "'!!";
        }
        return value;
    }

    protected Object makeCall(final String callDirective, String finish,  final EO eo) {
        String content = "";
        if (!finish.startsWith(Path.DELIMITER)) {
            content = findContent();
        }
        String[] callsAndAttributes = callDirective.split("\" +");
        String callPathCore = callsAndAttributes[0].replaceAll(" .*","");
        Map<String, String> attributes = extractAttributes(callsAndAttributes);
        Path path = new Path(callPathCore);
        if (!path.hasModel()) {
            throw new EoException("Could not find call in path " + callPathCore);
        }

        Models models = new Models(eo.getConfigsCache(), path.getModels());
        Object callObject = models.create();
        if (!(callObject instanceof Call)) {
            throw new EoException("Object is not instance of call but " + callObject.getClass().getSimpleName() + "!");
        }

        ((Call)callObject).setPathByTemplate(path);
        if (!content.isEmpty()) {
            if (callObject instanceof TemplateCall) {
                ((TemplateCall)callObject).setContent(content);
            }
            else if (callObject instanceof ValueCall) {
                ((ValueCall)callObject).setValue(content);
            }
            else {
                throw new EoException("Template has a content but meaning is only for TemplateCall or ValueCall. The call is '" + callObject.getClass().getSimpleName() + "'");
            }
        }

        if (!attributes.isEmpty()) {
            EO eoForMapAttributesToCall = new EoRoot(eo.getConfigsCache(), callObject);
            eoForMapAttributesToCall.mapObject(attributes);
            callObject = (Call) eoForMapAttributesToCall.get();
        }
        if (callObject instanceof TemplateResourceCall) {
            if (((TemplateResourceCall)callObject).getKeepCall()!=null) {
                KeepCalls keepCall = ((TemplateResourceCall)callObject).getKeepCall();
                String directiveWithComment = keepCall.createDirective(callDirective);
                ((TemplateResourceCall)callObject).setDirective(directiveWithComment);
                ((TemplateResourceCall)callObject).setEndDirective(keepCall.createDirective("/"));
            }
        }
        Object value = new ExecutorCallImpl().execute(eo, (Call) callObject);
        if (value == null) {
            return "";
        }
        return value.toString();
    }

    private String findContent() {
        StringBuilder content = new StringBuilder();
        int hierarchy = 1;
        while (match.find()) {
            content.append(template.substring(end, match.start()));
            end = match.end();
            String found = match.group(1);
            String foundEnd = match.group(2);
            String foundAll = match.group();
            if (foundAll.equals("$[/]")) {
                hierarchy--;
            }
            else if (!found.endsWith(Path.DELIMITER) && (found.startsWith("&") || found.startsWith("("))){
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

    private static final Map<String, String> extractAttributes(final String[] attributesList) {
        Map<String, String> attributes = new LinkedHashMap<>();
        if (attributesList.length == 1 && !attributesList[0].contains(" ")) {
            return attributes;
        }
        attributesList[0] = attributesList[0].replaceAll("^[^ ]* ","");

        for (String attribute: attributesList) {
            String[] keyValue = attribute.split("=\"");
            if (keyValue.length == 2) {
                attributes.put(keyValue[0], keyValue[1].replaceAll("\"$",""));
            }
            if (keyValue.length == 1) {
                attributes.put(keyValue[0], "true");
            }
            if (keyValue.length > 2) {
                throw new EoException("Wrong attribute! " + attribute);
            }
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
    @Override
    public String toString() {
        return template;
    }
}
