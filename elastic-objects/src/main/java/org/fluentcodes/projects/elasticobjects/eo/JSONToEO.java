package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.config.ModelInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * A JSONToEO takes a source getSerialized and extracts characters and tokens from
 * it. It is used by the JSONObject and JSONArray constructors to set
 * JSON source strings.
 *
 * @author JSON.org
 * @version 2012-02-16
 */
public class JSONToEO {
    public static final String ACTION = "_actions";
    public static final String MODELS = "_models";
    public static final String ACTIONS = "_actions";
    public static final String DATA = "_data";
    public static final String LOGS = "_logs";
    public static final String CONFIG = "_config";
    private static final Logger LOG = LogManager.getLogger(JSONToEO.class);
    private EOConfigsCache provider;
    private long character;
    private long index;
    private boolean eof;
    private long line;
    private char previous;
    private Reader reader;
    private boolean usePrevious;
    /**
     * Construct a JSONToEO from a getSerialized.
     *
     * @param s A source getSerialized.
     */
    private String debug;


    /**
     * Construct a JSONToEO from a Reader.
     *
     * @param reader A reader.
     */
    private JSONToEO(Reader reader) {
        this.reader = reader.markSupported()
                ? reader
                : new BufferedReader(reader);
        this.eof = false;
        this.usePrevious = false;
        this.previous = 0;
        this.index = 0;
        this.character = 1;
        this.line = 1;
    }

    public JSONToEO(String s, EOConfigsCache provider) {
        this(new StringReader(s));
        this.debug = s;
        this.provider = provider;
    }

    private static Models stringToValue(final EOConfigsCache configsCache, final String string) throws Exception {
        if (string.equals("")) {
            return new Models(configsCache, String.class);
        }
        if (string.equalsIgnoreCase("true")) {
            return new Models(configsCache, Boolean.class);
        }
        if (string.equalsIgnoreCase("false")) {
            return new Models(configsCache, Boolean.class);
        }
        if (string.equalsIgnoreCase("null")) {
            return new Models(configsCache, Map.class);
        }

        /*
         * If it might be a number, try converting it. If a number cannot be
         * produced, then the fileName will just be a getSerialized.
         */

        char b = string.charAt(0);
        if ((b >= '0' && b <= '9') || b == '-') {
            if (string.indexOf('.') > -1 || string.indexOf('e') > -1
                    || string.indexOf('E') > -1) {
                return new Models(configsCache, Double.class);
            } else {
                return new Models(configsCache, Long.class);
            }
        }
        return new Models(configsCache, String.class);
    }

    /**
     * Back up one character. This provides a sort of lookahead capability,
     * so that you can test for a digit or letter before attempting to set
     * the next number or identifier.
     */
    private void back() throws Exception {
        if (this.usePrevious || this.index <= 0) {
            throw new Exception("Stepping back two steps is not supported");
        }
        this.index -= 1;
        this.character -= 1;
        this.usePrevious = true;
        this.eof = false;
    }

    private boolean end() {
        return this.eof && !this.usePrevious;
    }

    /**
     * Get the next character in the source getSerialized.
     *
     * @return The next character, or 0 if past the end of the source getSerialized.
     */
    private char next() throws Exception {
        int c;
        if (this.usePrevious) {
            this.usePrevious = false;
            c = this.previous;
        } else {
            try {
                c = this.reader.read();
            } catch (IOException exception) {
                throw new Exception(exception);
            }

            if (c <= 0) { // End of stream
                this.eof = true;
                c = 0;
            }
        }
        this.index += 1;
        if (this.previous == '\r') {
            this.line += 1;
            this.character = c == '\n' ? 0 : 1;
        } else if (c == '\n') {
            this.line += 1;
            this.character = 0;
        } else {
            this.character += 1;
        }
        this.previous = (char) c;
        return this.previous;
    }

    /**
     * Get the next n characters.
     *
     * @param n The number of characters to take.
     * @return A getSerialized of n characters.
     * @throws Exception Substring bounds error if there are not
     *                   n characters remaining in the source getSerialized.
     */
    private String next(int n) throws Exception {
        if (n == 0) {
            return "";
        }

        char[] chars = new char[n];
        int pos = 0;

        while (pos < n) {
            chars[pos] = this.next();
            if (this.end()) {
                throw this.syntaxError("Substring bounds error");
            }
            pos += 1;
        }
        return new String(chars);
    }

    /**
     * Get the next char in the getSerialized, skipping whitespace.
     *
     * @return A character, or 0 if there are no more characters.
     * @throws Exception
     */
    private char nextClean() throws Exception {
        for (; ; ) {
            char c = this.next();
            if (c == 0 || c > ' ') {
                return c;
            }
        }
    }

    /**
     * Return the characters up to the next close quote character.
     * Backslash processing is done. The formal JSON format does not
     * allow strings in standard quotes, but an implementation is allowed to
     * accept them.
     *
     * @param quote The quoting character, either
     *              <code>"</code>&nbsp;<small>(double quote)</small> or
     *              <code>'</code>&nbsp;<small>(standard quote)</small>.
     * @return A String.
     * @throws Exception Unterminated getSerialized.
     */
    private final String nextString(final char quote, final String specifier) throws Exception {
        char c;
        final StringBuffer sb = new StringBuffer();
        for (; ; ) {
            c = this.next();
            switch (c) {
                case 0:
                case '\n':
                    //sb.append('\n');
                    //break;
                case '\r':
                    //sb.append('\r');
                    //break;
                    throw this.syntaxError("Unterminated string cause of an escaped carriage return in a character: '" + specifier + "'.");
                case '\\':
                    c = this.next();
                    switch (c) {
                        case 'b':
                            sb.append('\b');
                            break;
                        case 't':
                            sb.append('\t');
                            break;
                        case 'n':
                            sb.append('\n');
                            break;
                        case 'f':
                            sb.append('\f');
                            break;
                        case 'r':
                            sb.append('\r');
                            break;
                        case 'u':
                            sb.append((char) Integer.parseInt(this.next(4), 16));
                            break;
                        case '"':
                        case '\'':
                        case '\\':
                        case '/':
                            sb.append(c);
                            break;
                        default:
                            throw this.syntaxError("Illegal escape.'" + c + "':" + sb.toString() + "(" + specifier + ")");
                    }
                    break;
                default:
                    if (c == quote) {
                        return sb.toString();
                    }
                    sb.append(c);
            }
        }
    }

    private String debug() {
        return (Thread.currentThread().getStackTrace()[2].getMethodName() + ": " + index + ": " + debug.substring(0, new Long(index).intValue()) + "==" + debug.substring(new Long(index).intValue()));
    }

    private EO mapObject(EO currentAdapter) throws Exception {
        if (currentAdapter == null) {
            LOG.error("Null MODULE_NAME!!!! " + debug());
            return null;
        }
        char c;
        String key;
        for (; ; ) {
            //Check for closing tag or a key
            c = this.nextClean();
            switch (c) {
                case 0:
                    throw new Exception("A JSONObject text must end with '}' but is '" + c + "'at " + index + ":" + debug);
                    // closing tag
                case '}':   // empty object
                    return currentAdapter;
                // key
                default:
                    this.back();
                    key = this.nextKey();
            }

            // The key is followed by ':'.
            c = this.nextClean();
            if (c != ':') {
                throw syntaxError("Expected ':' in the map after the key '" + key + "' but see '" + c + "': ");
            }
            if (currentAdapter.isList() && !key.endsWith(DATA) && !key.startsWith("(")) {
                key = new Integer(currentAdapter.size()).toString();
            }
            currentAdapter = createChild(currentAdapter, key);

            c = this.nextClean();
            switch (c) {
                case ';':

                case ',':
                    // adding next value to current ObjectsBuilder
                    if (this.nextClean() != '}') {
                        this.back();
                        break;
                    } else {
                        return ((EOContainer) currentAdapter).getParentAdapter(); //special for ',}'
                    }
                case '}':
                    // closing current ObjectsBuilder
                    return currentAdapter;
                default:
                    throw this.syntaxError("After key '" + key + "' in the map. Expected  ',' '}' but not '" + c + "': ");
            }
        }
    }

    private EO mapList(final EO currentBuilder) throws Exception {
        if (currentBuilder == null) {
            LOG.error("Null MODULE_NAME!!!! " + debug());
            return null;
        }

        if (this.nextClean() != ']') {
            this.back();
            int counter = 0;
            for (; ; ) {
                // closing current builder
                if (this.nextClean() == ',') {
                    this.back();
                    continue;
                } else {
                    this.back();
                    this.createChild(currentBuilder, new Integer(counter).toString());
                    counter++;
                }
                switch (this.nextClean()) {
                    case ',':
                        // adding next value to current builder
                        if (this.nextClean() == ']') {
                            return currentBuilder;
                        }
                        this.back();
                        break;
                    case ']':
                        // closing current builder
                        return currentBuilder;
                    default:
                        throw this.syntaxError("Expected  ',' or ']' within list value: ");
                }
            }
        }
        return currentBuilder;
    }

    public EO createChild(EO parentAdapter) throws Exception {
        return createChild(parentAdapter, null);
    }

    /**
     * Creates the childbuilder for the fieldName depending on the value.
     * It will createHost
     * <ul>
     * <li>maps Map or Object starting with '{'</li>
     * <li>maps List starting with '['</li>
     * <li>Scalar else. </li>
     * </ul>
     *
     * @param parentAdapter the parent builder where the child will be added
     * @param rawFieldName  The fieldname of the child.
     * @return
     * @throws Exception
     */
    public EO createChild(EO parentAdapter, final String rawFieldName) throws Exception {
        if (parentAdapter == null) {
            throw new Exception("parentAdapter is null ...!");
        }
        String name = rawFieldName;
        Models models = null;
        if (rawFieldName!=null && !rawFieldName.isEmpty()) {
            Matcher modelInName = EOBuilder.modelPattern.matcher(rawFieldName);
            if (modelInName.find()) {
                String model = modelInName.group(1);
                name = modelInName.group(2);
                models = new Models(parentAdapter.getConfigsCache(), model);
            }
        }
        char c = this.nextClean();
        switch (c) {
            case '"':
            case '\'':

                if (rawFieldName == null) {
                    throw this.syntaxError(this.getClass().getSimpleName() + " createChildForMap: Scalar value with no name");
                }
                String value = this.nextString(c, rawFieldName);
                    parentAdapter
                            .add(rawFieldName)
                            .map(value);

                return parentAdapter;

            case '{':
                if (name==null) {
                    this.mapObject(parentAdapter);
                    return parentAdapter;
                }
                switch (name) {

                    case(DATA):  // Create a list of actions
                        if (parentAdapter.isEmpty() && models !=null) {
                            ((EOScalar) parentAdapter).setModels(models);
                        }
                        this.mapObject(parentAdapter);
                        return parentAdapter;

                    case(LOGS):  // Create a list of actions
                        EO logAdapter = new EOBuilder(((EOScalar) parentAdapter).getConfigsCache())
                                .setModels(LoggingObjectsImpl.class.getSimpleName())
                                .set(this);
                        ((EORoot) parentAdapter).setLog((LoggingObjectsImpl) logAdapter.get());
                        return parentAdapter;

                    case (ACTIONS):  // Create a list of actions
                        back();
                        EO actionAdapter = new EOBuilder(this.provider)
                                .setModels(List.class)
                                .map(this);
                        Object actionList = actionAdapter.get();
                        ((EOContainer) parentAdapter).setCallsByMap((List<Map>) actionList);
                        return parentAdapter;

                    default:
                        if (parentAdapter == null) {
                            parentAdapter = new EOBuilder(provider).build();
                        }
                        if (name != null) {
                            EO adapter = parentAdapter
                                    .add(rawFieldName)
                                    .setModels(models)
                                    .build();
                            this.mapObject(adapter);
                        } else {
                            this.mapObject(parentAdapter);
                        }
                        return parentAdapter;

                }

            case '[':
                if (ACTIONS.equals(rawFieldName)) {  // Create a list of actions
                    back();
                    EO actionListAdapter = new EOBuilder(provider)
                            .setModels("List,CallExecutor")
                            .map(this);
                    ((EORoot) parentAdapter).setCallsByMap((List<Map>) actionListAdapter.get());
                } else if (DATA.equals(rawFieldName)) {  // Create a list of actions
                    EO rootAdapter = ((EOScalar) parentAdapter).getRoot();
                    rootAdapter
                            .add()
                            .map(this);
                    return rootAdapter;
                } else {
                    EO childAdapter = null;
                    if (rawFieldName == null) {
                        if (parentAdapter == null) {
                            parentAdapter = new EOBuilder(this.provider)
                                    .setModels(List.class.getSimpleName())
                                    .build();
                        }
                        else {
                            ModelInterface model = parentAdapter.getModel();
                            if (model.isMap()) {
                                if (model.getModelClass() == Map.class) {
                                    ((EOScalar) parentAdapter).setModelClasses(List.class);
                                }
                            }
                            else if (model.isObject()) {
                                throw new Exception("Could not map an array to an object!");
                            }
                        }
                        childAdapter = parentAdapter;
                    } else {
                        childAdapter = parentAdapter
                                .add()
                                .setPath(rawFieldName)
                                .setModels(List.class)
                                .build();
                    }
                    this.mapList(childAdapter);

                }
                return parentAdapter;
        }
        /*
         * Handle unquoted text. This could be the rows true, false, or
         * null, or it can be a number. An implementation (such as this one)
         * is allowed to also accept non-standard forms.
         *
         * Accumulate characters until we reach the end of the text or a
         * formatting character.
         */

        StringBuffer sb = new StringBuffer();
        while (c >= ' ' && ",:]}/\\\"[{;=#".indexOf(c) < 0) {
            sb.append(c);
            c = this.next();
        }
        this.back();

        String value = sb.toString().trim();
        if (models == null) {
            models = stringToValue(parentAdapter.getConfigsCache(), value);
        }
        if ("null".equals(value)) {
            value = null;
            models = new Models(parentAdapter.getConfigsCache());
        }

        /*
        Now everything else beside String, List or Map: Integer, Boolean...
         */
            parentAdapter
                    .add(name)
                    .setModels(models)
                    .map(value);
        return parentAdapter;
    }

    /**
     * Get the next fileName. The fileName can be a Boolean, Double, Integer,
     * JSONArray, JSONObject, Long, or String, or the JSONObject.NULL standard.
     *
     * @return An standard.
     * @throws Exception If syntax error.
     */
    private String nextKey() throws Exception {
        char c = this.nextClean();
        switch (c) {
            case '"':
            case '\'':
                return this.nextString(c, " finding key");
        }
        return "";
    }

    /**
     * Make a Exception to signal a syntax error.
     *
     * @param message The error message.
     * @return A Exception standard, suitable for throwing
     */
    private Exception syntaxError(String message) {
        return new Exception(message + this.toString());
    }

    /**
     * Make a printable getSerialized of this JSONToEO.
     *
     * @return " at {index} [character {character} line {line}]"
     */
    public String toString() {
        return " [character " + this.index + " line " +
                this.line + "]";
    }
}
