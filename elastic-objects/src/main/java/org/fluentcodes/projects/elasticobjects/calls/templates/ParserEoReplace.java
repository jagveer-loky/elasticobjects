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

import java.util.regex.Pattern;


public class ParserEoReplace extends Parser {
    private static final Logger LOG = LogManager.getLogger(ParserEoReplace.class);
    public static final String START_SEQUENCE = "eo->";
    private static final String END_SEQUENCE = "/.";
    private static final String CLOSE_TAG = START_SEQUENCE + END_SEQUENCE;
    private static final Pattern varPattern = Pattern.compile("eo->(.*?)(/*\\.)");
    public ParserEoReplace(final String template) {
        super(template);
    }

    public String parse() {
        return this.parse((EO)null);
    }

    public String parse(final EO eo) {
        if (!hasTemplate()) {
            return "";
        }
        if (!getTemplate().contains(START_SEQUENCE)) {
            LOG.debug("no placeholder " + START_SEQUENCE + " found  : " + getTemplate());
            return getTemplate();
        }
        return super.parse(eo, varPattern);
    }
}
