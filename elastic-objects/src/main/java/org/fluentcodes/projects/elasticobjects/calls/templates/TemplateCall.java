package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

/**
 * Refactored by werner.diwischek on 27.7.2020.
 */
public class TemplateCall extends CallImpl<String> {
    private static final transient Logger LOG = LogManager.getLogger(TemplateCall.class);
    private String content;

    public TemplateCall() {
        super.setInTemplate(true);
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

    public String getContent() {
        return content;
    }

    @Override
    public void setPathByTemplate(final Path path) {
        setFilterPath(path.getParentKey());
        if (hasSourcePath()) {
            if (path.hasChild()) {
                if (hasFilterPath()) {
                    setFilterPath(path.getParentPath().directory(false));
                }
                else {
                    setFilterPath(getFilterPath() + path.getParentPath().directory(true));
                }
                setFilterPath(path.getParentKey());
            }
        }
        else {
            setSourcePath(path.getParentPath().directory(false));
        }
    }
    /**
     */
    public String execute(EO eo)  {
        if (!hasContent()) {
            return "";
        }
        return new ParserTemplate(content).parse(eo);
    }
}
