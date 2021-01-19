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

import java.util.regex.Pattern;


public class ParserCurlyBracket extends Parser{
    private static final String START = "{";
    private static final String STOP = "}";
    private static final Pattern VAR_PATTERN = Parser.createVarPattern("\\" + START, "\\" + STOP);
    private static final String END_SEQUENCE = Parser.createEndSequence(STOP);
    private static final String CONTINUE_SEQUENCE = Parser.createContinueSequence(STOP);
    private static final String START_SEQUENCE = Parser.createStartSequence(START);
    private static final String CLOSE_SEQUENCE = Parser.createCloseSequence(START, STOP);  public ParserCurlyBracket(final String template) {
        super(template);
    }

    @Override
    protected String getCloseSequence() {
        return CLOSE_SEQUENCE;
    }

    @Override
    protected Pattern getVarPattern() {
        return VAR_PATTERN;
    }

    public static boolean containsStartSequence(final String toParse) {
        return toParse != null && !toParse.isEmpty() && toParse.contains(START_SEQUENCE);
    }

    @Override
    protected String getStartSequence() {
        return START_SEQUENCE;
    }
    @Override
    protected String getEndSequence() {
        return END_SEQUENCE;
    }
    @Override
    protected String getContinueSequence() {
        return CONTINUE_SEQUENCE;
    }

}
