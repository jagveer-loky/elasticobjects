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

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.ExecutorCall;
import org.fluentcodes.projects.elasticobjects.calls.values.ValueCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall.CONTENT;


public abstract class Parser {
    private static final String TEMPLATE_CALL_MATCH = "^&";
    private static final String VALUE_CALL_MATCH = "^#";
    private static final String TEMPLATE_CALL_CHAR = "&";
    private static final String VALUE_CALL_CHAR = "#";
    private static final String SYSTEM_CHAR = "@";
    private static final String ENV_CHAR = "%";
    private static final String PARENT = "_parent";
    private static final String VALUE = "_value";
    private static final Map<String, String> SYSTEM = populateSystem();
    private final String template;
    private Matcher match;
    private int end;

    public Parser(final String template) {
        this.template = template;
    }

    protected static final Pattern CREATE_VAR_PATTERN(final String start, final String stop) {
        final String pattern = "\n*[\t ]*([=]{0,2})=>" + start + "(.*?)(" + stop + "[\\.\\|])";
        return Pattern.compile(pattern, Pattern.DOTALL);
    }
    protected static final String CREATE_END_SEQUENCE(final String stop) {
        return stop + ".";
    }

    protected static final String CREATE_CONTINUE_SEQUENCE (final String stop) {
        return stop + "|";
    }

    protected static final String CREATE_START_SEQUENCE (final String start) {
        return "=>" + start;
    }

    protected static final String CREATE_CLOSE_SEQUENCE(final String start, final String stop) {
        return "=>" + start + stop + ".";
    }

    public static String replacePathValues(final String value, EO eo) {
        if (value == null) {
            return value;
        }
        if (ParserCurlyBracket.containsStartSequence(value)) {
            return new ParserCurlyBracket(value).parse(eo);
        }
        if (ParserSqareBracket.containsStartSequence(value)) {
            return new ParserSqareBracket(value).parse(eo);
        }
        return value;
    }

    public String getTemplate() {
        return template;
    }
    public boolean hasTemplate() {
        return template!=null && !template.isEmpty();
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

    public String parse() {
        return parse(null);
    }


    /**
     * Replaces $=>param}. br/>
     * changing the static final varPattern you can use the var you prefer
     * varPattern = Pattern.compile(" ===>{(.*?)}. "); would look for $param
     *
     * @param eo
     * @return
     */

    public String parse(final EO eo) {
        if (!hasStartSequence(template)) {
            return template == null ? "": template;
        }
        this.match = getVarPattern().matcher(template);
        StringBuffer result = new StringBuffer();
        String parseString = template;
        end = 0;
        while (match.find()) {
            int findStart = match.start();
            result.append(parseString.substring(end, findStart));
            end = match.end();
            String value = match.group();
            String callIndicator = match.group(1);
            String pathOrKey = match.group(2);
            String finish = match.group(3);
            if (callIndicator == null || callIndicator.isEmpty()) {
                result.append(replacePathValues(eo, pathOrKey));
            }
            else if (callIndicator.equals("=")) { // setCall
                result.append(callWithValues(eo, pathOrKey, finish));
            }
            else if (callIndicator.equals("==")){ // json
                result.append(callWithJson(eo, pathOrKey, finish));
            }
            else {

            }
        }
        result.append(parseString.substring(end, parseString.length()));
        return result.toString();
    }

    private String replacePathValues(final EO eo, String pathOrKey) {
        String defaultValue = null;
        if (pathOrKey.contains("|>")) {
            String[] pathAndDefault = pathOrKey.split("\\|>");
            pathOrKey = pathAndDefault[0];
            if (pathAndDefault.length == 2) {
                defaultValue = pathAndDefault[1];
            }
            else {
                defaultValue="";
            }
        }

        if (eo != null && pathOrKey.startsWith("_")) {
            defaultValue = pathOrKey
                    .replaceAll(VALUE, eo.get().toString())
                    .replaceAll(PARENT, eo.getParentKey());
        }

        else if (pathOrKey.startsWith(SYSTEM_CHAR)) {
            defaultValue = getSystemValue(pathOrKey.replaceAll(SYSTEM_CHAR, ""));
        }
        else if (pathOrKey.startsWith(ENV_CHAR)) {
            defaultValue = System.getenv(pathOrKey.replaceAll(ENV_CHAR, ""));
        }

        else if (eo != null){
                try {
                    pathOrKey = pathOrKey
                            .replaceAll(VALUE, eo.get().toString())
                            .replaceAll(PARENT, eo.getParentKey());
                    defaultValue = ScalarConverter.toString(eo.get(pathOrKey));
                } catch (Exception e) {
                    if (defaultValue == null) {
                        eo.error(e.getMessage());
                        defaultValue = "!!" + e.getMessage() + "!!";
                    }
                }
        }
        else {
            defaultValue = "!!Could not find path '" + pathOrKey + "'!!";
        }
        return defaultValue;
    }

    protected Object callWithValues(final EO eo, final String callDirective, String finish) {
        String[] methodAndInput = callDirective.split("->");
        ModelConfig model = eo.getConfigsCache().findModel(methodAndInput[0]);
        Call call = (Call)model.create();
        call.setByString(methodAndInput[1]);
        if (!isEndSequence(finish)) {
            try {
                String content = findContent();
                if (content!=null && !content.isEmpty()) {
                    if ((call instanceof TemplateCall)) {
                        ((TemplateCall) call).setContent(content);
                    }
                    else if ((call instanceof ValueCall)) {
                        ((ValueCall) call).setValue(content);
                    }
                }
            }
            catch(EoException e) {
                throw new EoException(e.getMessage() + ": " + callDirective);
            }
        }

        Object value = ExecutorCall.execute(eo, call);
        if (value == null) {
            return "";
        }
        return value.toString();
    }

    protected Object callWithJson(final EO eo, final String callDirective, String finish) {
        EO eoCall = new EoRoot(eo.getConfigsCache(), "{" + callDirective + "}");
        if (eoCall.getCallKeys().isEmpty()) {
            if (!isEndSequence(finish)) {
                throw new EoException("No call type but no end tag");
            }
            eo.mapObject(eoCall.get());
            return "";
        }
        String content = null;
        if (!isEndSequence(finish)) {
            try {
                content = findContent();
            }
            catch(EoException e) {
                throw new EoException(e.getMessage() + ": " + callDirective);
            }
        }
        Object call = eoCall.get(PathElement.CALLS, "0");
        if (call instanceof TemplateCall) {
            if (content!=null && !content.isEmpty()) {
                 eoCall.set(content, PathElement.CALLS, "0", CONTENT);
            }
            if (call instanceof TemplateResourceCall) {
                TemplateResourceCall resourceCall = (TemplateResourceCall) call;
                if (resourceCall.hasKeepCall()) {
                    KeepCalls keepCall = resourceCall.getKeepCall();
                    resourceCall
                            .setPrepend(keepCall.createStartDirective("==" + getStartSequence() + callDirective + getContinueSequence()));
                    resourceCall
                            .setPostpend(keepCall.getStartComment() + getCloseSequence());
                }
            }
        }
        else if (call instanceof ValueCall) {
            if (content!=null && !content.isEmpty()) {
                eoCall.set(content, PathElement.CALLS, "0", ValueCall.VALUE);
            }
        }
        Object value = ExecutorCall.execute(eo, (Call) call);
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
            String found = match.group(2);
            String foundEnd = match.group(3);
            String foundAll = match.group();
            if (isCloseSequence(foundAll)) {
                hierarchy--;
            }
            else if (!isEndSequence(foundEnd)) {
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

    public boolean isCloseSequence(final String toParse) {
        return toParse != null && !toParse.isEmpty() && toParse.endsWith(getCloseSequence());
    }

    protected String getCloseSequence() {
        throw new EoInternalException("Should be overwritten");
    }

    protected Pattern getVarPattern() {
        throw new EoInternalException("Should be overwritten");
    }

    public boolean hasStartSequence(final String toParse) {
        return toParse != null && !toParse.isEmpty() && toParse.contains(getStartSequence());
    }

    protected String getStartSequence() {
        throw new EoInternalException("Should be overwritten");
    }

    protected String getEndSequence() {
        throw new EoInternalException("Should be overwritten");
    }

    protected String getContinueSequence() {
        throw new EoInternalException("Should be overwritten");
    }

    private boolean isEndSequence(final String toParse) {
        return toParse != null && !toParse.isEmpty() && toParse.equals(getEndSequence());
    }

    @Override
    public String toString() {
        return template;
    }
}
