package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
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

    @Override
    public void setByString(final String values) {
        if (values == null||values.isEmpty()) {
            throw new EoException("Set by empty input values");
        }
        String[] array = values.split(", ");
        if (array.length>3) {
            throw new EoException("Short form should have form '<sourcePath>[,<targetPath>][,<condition>]' with max length 3 but has size " + array.length + ": '" + values + "'." );
        }
        setByString(array);
    }

    protected void setByString(final String[] array) {
        if (array == null||array.length == 0) {
            throw new EoException("Set by empty input values");
        }
        if (array.length>0) {
            if (array[0].replaceAll("\\s","").isEmpty()) {
                setSourcePath(PathElement.SAME);
            }
            else {
                setSourcePath(array[0]);
            }
        }
        if (array.length>1) {
            setCondition(array[1]);
        }
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
