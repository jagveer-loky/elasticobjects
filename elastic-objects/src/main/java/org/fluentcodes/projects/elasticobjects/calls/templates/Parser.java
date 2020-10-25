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
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.CallContent;
import org.fluentcodes.projects.elasticobjects.calls.CallKeep;
import org.fluentcodes.projects.elasticobjects.calls.ExecutorCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
    private static final Pattern NEW_LINE_REMOVE = Pattern.compile("\\\\\n$", Pattern.DOTALL);

    public Parser(final String template) {
        this.template = template;
    }

    protected static final Pattern CREATE_VAR_PATTERN(final String start, final String stop) {
        final String pattern = "[\t ]*([=]{0,2})=>" + start + "(.*?)(" + stop + "[\\.\\|])";
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
            int start = match.start();
            String before = chomp(template.substring(end, start));
            result.append(before);
            end = match.end();
            String matchAll = match.group();
            String callIndicator = match.group(1);
            String callSequence = match.group(2);
            String finish = match.group(3);
            String defaultValue = getDefault(callSequence);
            callSequence = callSequence.replaceAll("|>.*", "");
            try {
                if (callIndicator == null || callIndicator.isEmpty()) {
                    result.append(replacePathValues(eo, callSequence));
                } else if (callIndicator.equals("=")) { // setCall
                    result.append(callParameter(eo, callSequence, finish));
                } else if (callIndicator.equals("==")) { // json
                    result.append(callJson(eo, callSequence, finish));
                } else {
                    eo.error("!!Callindication should be =>, ==> or ===> but found '" + matchAll + "'!!");
                    result.append("!!Callindication should be =>, ==> or ===> but found '" + matchAll + "'!!");
                }
            }
            catch (EoException e) {
                if (eo != null) {
                    eo.error(e.getMessage());
                }
                if (defaultValue!=null) {
                    result.append(defaultValue);
                }
                else {
                    result.append("!!" + e.getMessage() + "!!");
                }
            }
        }
        result.append(parseString.substring(end, parseString.length()));
        return result.toString();
    }


    private String getDefault(String pathOrKey) {
            String[] pathAndDefault = pathOrKey.split("\\|>");
            if (pathAndDefault.length == 2) {
                return pathAndDefault[1];
            }
            if (pathAndDefault.length == 1) {
                return null;
            }
            throw new EoException("Problem setting default values with " + pathAndDefault.length + " splitter set: '" + pathAndDefault.length + "'");
    }

    private String replacePathValues(final EO eo, String pathOrKey) {
        if (pathOrKey.startsWith(SYSTEM_CHAR)) {
            return getSystemValue(pathOrKey.replaceAll(SYSTEM_CHAR, ""));
        }
        if (pathOrKey.startsWith(ENV_CHAR)) {
            return System.getenv(pathOrKey.replaceAll(ENV_CHAR, ""));
        }
        if (eo == null) {
            throw new EoException("Null eo wrapper defined to get '" + pathOrKey + "'");
        }
        if (pathOrKey.equals(VALUE)) {
            return eo.get().toString();
        }
        if (pathOrKey.equals(PARENT)) {
            return eo.getParentKey();
        }
        pathOrKey = pathOrKey
                .replaceAll(PARENT, eo.getParentKey())
                .replaceAll(PARENT, eo.getParentKey());

        return eo.getEo(pathOrKey).toString();
    }

    protected Object callParameter(final EO eo, final String callDirective, String finish) {
        String[] methodAndInput = callDirective.split("->");
        ModelConfig callModel = eo.getConfigsCache().findModel(methodAndInput[0]);
        Call call = (Call)callModel.create();
        call.setByParameter(methodAndInput[1]);

        if (!isEndSequence(finish)) {
            String content = findContent();
            if (call instanceof CallContent)  {
                ((CallContent)call).setContent(content);
            }
            else {
                throw new EoException("Problem setting content with implementing CallContent: '" + call.getClass().getSimpleName() + "'.");
            }
        }
        StringBuffer returnResult = new StringBuffer();
        String postPend = "";
        if (call instanceof CallKeep && ((CallKeep)call).hasKeepCall()) {
            returnResult.insert(0, "=" +
                    getStartSequence() +
                    callDirective +
                    getContinueSequence() +
                    ((CallKeep)call).getKeepEndSequence() );
            postPend = ((CallKeep)call).getKeepStartSequence() +
                    getCloseSequence();
        }
        Object value = ExecutorCall.execute(eo, call);
        if (value!=null) {
            returnResult.append(value.toString());
        }
        returnResult.append(postPend);
        return returnResult.toString();
    }

    protected Object callJson(final EO eo, final String callDirective, String finish) {
        EO eoCall = new EoRoot(eo.getConfigsCache(), "{" + callDirective + "}");
        if (!eoCall.isEmpty()) {
            eo.mapObject(eoCall.get());
        }
        if (eoCall.getCallKeys().isEmpty()) {
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
        List<String> callSet = new ArrayList<>(eoCall.getCallKeys());
        StringBuffer returnResult = new StringBuffer();
        String postPend = "";
        for (int i=0; i<callSet.size(); i++) {
            Call loopCall = (Call) eoCall.getCallEo(Integer.valueOf(i).toString()).get();
            if (i == callSet.size()-1) {
                if (loopCall instanceof CallKeep && ((CallKeep)loopCall).hasKeepCall()) {
                    returnResult.insert(0, "==" +
                            getStartSequence() +
                            callDirective +
                            getContinueSequence() +
                            ((CallKeep)loopCall).getKeepEndSequence() );
                    postPend = ((CallKeep)loopCall).getKeepStartSequence() +
                            getCloseSequence();
                }
                else {
                    if (content != null && !content.isEmpty()) {
                        if (loopCall instanceof CallContent) {
                            ((CallContent) loopCall).setContent(content);

                        } else {
                            throw new EoException("Last call with content template directive is not instance of CallContent but '" + loopCall.getClass().getSimpleName() + "'");
                        }
                    }
                }
            }
            returnResult.append(ExecutorCall.execute(eo, loopCall));
        }
        returnResult.append(postPend);
        return returnResult.toString();
    }

    private String chomp(final String prepend) {
        if (NEW_LINE_REMOVE.matcher(prepend).find()) {
            return prepend.substring(0, prepend.length()-2);
        }
        else if (prepend.endsWith("\n")) {
            return prepend.substring(0, prepend.length()-1);
        }
        return prepend;
    }

    private String findContent() {
        StringBuilder content = new StringBuilder();
        int hierarchy = 1;
        while (match.find()) {
            int start = match.start();
            content.append(template.substring(end, start));
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
                return chomp(content.toString());  // replace backslash+newline or newline from content due to closing tag
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
