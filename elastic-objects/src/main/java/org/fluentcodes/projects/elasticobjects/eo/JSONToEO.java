package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.config.ModelInterface;
import org.fluentcodes.projects.elasticobjects.EoException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * A JSONToEO  extracts characters and tokens from a JSON serialized String.
 * Core source from JSON.org.
 *
 * @author Werner Diwischek
 * @since 10.6.2016
 */
public class JSONToEO {
    public static final String CALLS = "_calls";
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
    private boolean parseCalls = false;
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

    private static Models stringToValue(final EOConfigsCache configsCache, final String string)  {
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
    private void back()  {
        if (this.usePrevious || this.index <= 0) {
            throw new EoException("Stepping back two steps is not supported");
        }
        this.index -= 1;
        this.character -= 1;
        this.usePrevious = true;
        this.eof = false;
    }

    private boolean end() {
        return this.eof && !this.usePrevious;
    }

    private boolean isEof()  {
        try {
            return reader.read() < 0;
        } catch (IOException e) {
            throw new EoException(e);
        }
    }

    /**
     * Get the next character in the source getSerialized.
     *
     * @return The next character, or 0 if past the end of the source getSerialized.
     */
    private char next()  {
        int c;
        if (this.usePrevious) {
            this.usePrevious = false;
            c = this.previous;
        } else {
            try {
                c = this.reader.read();
            } catch (IOException exception) {
                throw new EoException(exception);
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
     * @ Substring bounds error if there are not
     *                   n characters remaining in the source getSerialized.
     */
    private String next(int n)  {
        if (n == 0) {
            return "";
        }

        char[] chars = new char[n];
        int pos = 0;

        while (pos < n) {
            chars[pos] = this.next();
            if (this.end()) {
                throw new EoException("Substring bounds error");
            }
            pos += 1;
        }
        return new String(chars);
    }

    /**
     * Get the next char in the getSerialized, skipping whitespace.
     *
     * @return A character, or 0 if there are no more characters.
     * @
     */
    private char nextClean()  {
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
     * @ Unterminated getSerialized.
     */
    private final String nextString(final char quote, final String specifier)  {
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
                    throw new EoException("Unterminated string cause of an escaped carriage return in a character: '" + specifier + "'.");
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
                            throw new EoException("Illegal escape.'" + c + "':" + sb.toString() + "(" + specifier + ")");
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

    private EO mapObject(EO currentAdapter)  {
        if (currentAdapter == null) {
            LOG.error("Null MODULE_NAME!!!! " + debug());
            return null;
        }
        boolean startFlag = true;
        for (; ; ) {
            //Check for closing tag or a key
            char c = this.nextClean();
            if (c == '}') {
                return currentAdapter;
            }
            if (c == ',') {
                final String key = this.nextKey();
                if (this.nextClean() != ':') {
                    new EoException("Expected ':' in the map after the key '" + key + "' but see '" + c + "': ");
                }
                createChild(currentAdapter, key);
            } else if (startFlag) {
                back();
                final String key = this.nextKey();
                if (this.nextClean() != ':') {
                    new EoException("Expected ':' in the map after the key '" + key + "' but see '" + c + "': ");
                }
                createChild(currentAdapter, key);
                startFlag = false;
            } else {
                throw new EoException("Expected colon not found but '" + c + "'!");
            }
        }
    }


    private EO mapList(final EO currentEO)  {
        if (currentEO == null) {
            LOG.error("Null MODULE_NAME!!!! " + debug());
            return null;
        }
        int counter = 0;
        for (; ; ) {
            final char next = this.nextClean();
            if (next == ']') {
                return currentEO;
            }
            // closing current builder
            if (next == ',') {
                this.createChild(currentEO, new Integer(counter).toString());
                counter++;
            } else if (counter == 0) {
                back();
                this.createChild(currentEO, new Integer(counter).toString());
                counter++;
            } else {
                throw new EoException("Expected colon is not set but '" + next + "'!");
            }
        }
    }

    public EO createChild(EO parentAdapter)  {
        EO eo = createChild(parentAdapter, null);
        if (isEof()) {
            return eo;
        }
        if (parseCalls && eo instanceof EORoot) {
            return eo;
        }
        final char c = nextClean();
        if (c == 0) {
            return eo;
        }
        throw new EoException("Not at the end of the json file!");
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
     * @
     */
    private EO createChild(EO parentAdapter, final String rawFieldName)  {
        if (parentAdapter == null) {
            throw new EoException("parentAdapter is null ...!");
        }

        // see if fieldName has a model information '(ModelKey)fieldName'
        String name = rawFieldName;
        Models models = null;
        if (rawFieldName != null && !rawFieldName.isEmpty()) {
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
                    throw new EoException(this.getClass().getSimpleName() + " createChildForMap: Scalar value with no name");
                }
                String value = this.nextString(c, rawFieldName);
                parentAdapter
                        .add(name)
                        .setModels(models)
                        .map(value);
                return parentAdapter;

            case '{':
                if (name == null) {
                    this.mapObject(parentAdapter);
                    return parentAdapter;
                }
                switch (name) {
                    case (DATA):  // Map directly to the parentAdapter
                        if (parentAdapter.isEmpty() && models != null) {
                            //parentAdapter.setModels(models);
                        }
                        this.mapObject(parentAdapter);
                        return parentAdapter;

                    case (LOGS):  // Create a log
                        EO logAdapter = new EOBuilder(parentAdapter.getConfigsCache())
                                .setModels(LoggingObjectsImpl.class.getSimpleName())
                                .set(this);
                        ((EORoot) parentAdapter).setLog((LoggingObjectsImpl) logAdapter.get());
                        return parentAdapter;

                    case (CALLS):  // Create a list of actions
                        back();
                        parseCalls = true;
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
                                    .add(name)
                                    .setModels(models)
                                    .build();
                            this.mapObject(adapter);
                        } else {
                            this.mapObject(parentAdapter);
                        }
                        return parentAdapter;

                }

            case '[':
                if (CALLS.equals(rawFieldName)) {  // Create a list of actions
                    back();
                    EO actionListAdapter = createChild(new EOBuilder(this.provider).build(), null);
                    ((EORoot) parentAdapter).setCallsByMap((List<Map>) actionListAdapter.get());
                } else if (DATA.equals(rawFieldName)) {  // Create a list of actions
                    EO rootAdapter = (parentAdapter).getRoot();
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
                        } else {
                            ModelInterface model = parentAdapter.getModel();
                            if (model.isMap()) {
                                if (model.getModelClass() == Map.class) {
                                    //parentAdapter.setModelClasses(List.class);
                                }
                            } else if (model.isObject()) {
                                throw new EoException("Could not map an array to an object!");
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
     * @ If syntax error.
     */
    private String nextKey()  {
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
        return new EoException(message + this.toString());
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
