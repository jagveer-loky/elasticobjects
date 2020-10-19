package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

/**
 * Refactored by werner.diwischek on 27.7.2020.
 */
public class TemplateCall extends CallImpl {
    public final static String CONTENT = "content";
    private static final transient Logger LOG = LogManager.getLogger(TemplateCall.class);
    private String content;

    public TemplateCall() {
        super.setTargetPath(TARGET_AS_STRING);
    }

    public TemplateCall(final String content) {
        this();
        this.content = content;
    }


    public TemplateCall setContent(String entry) {
        if (entry == null) {
            return this;
        }
        this.content = ScalarConverter.toString(entry);

        return this;
    }

    public boolean hasContent() {
        return content != null && !content.isEmpty();
    }

    public boolean isContentActive() {
        return hasContent() && ParserCurlyBracket.containsStartSequence(content);
    }

    public String getContent() {
        return content;
    }

    /**
     */
    public String execute(EO eo)  {
        if (!hasContent()) {
            return "";
        }
        if (!init(eo)) {
            return "";
        }
        return new ParserCurlyBracket(content).parse(eo);
    }
    public TemplateCall setTargetPath(String x) {
        super.setTargetPath(x);
        return this;
    }
}
