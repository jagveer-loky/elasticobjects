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


public class ParserTemplate extends Parser{
    private static final Logger LOG = LogManager.getLogger(ParserTemplate.class);
    private static final Pattern varPattern = Pattern.compile("\\$\\[(.*?)(/*\\])");
    
    public ParserTemplate(final String template) {
        super(template);
    }

    public String parse() {
        return this.parse((EO)null);
    }

    public String parse(final EO eo) {
        if (!getTemplate().contains("$[")) {
            LOG.debug("no placeholder $[ found  : " + getTemplate());
            return getTemplate();
        }
        return super.parse(eo, varPattern);
    }
}
