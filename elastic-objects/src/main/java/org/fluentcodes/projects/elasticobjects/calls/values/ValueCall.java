package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallContent;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;

/**
 * Add value to eo.
 * Created by Werner on 02.08.2020.
 */

public class ValueCall extends CallImpl implements CallContent, SimpleCommand {
    public static final String CONTENT = "content";
    private String content;
    public ValueCall() {
    }

    public ValueCall(final String content) {
        this();
        this.content = content;
    }
    @Override
    public String execute(final EO eo) {
        super.check(eo);
        //Object value = eo.get();
        if (hasTargetPath()) {
            eo.set(this.content, getTargetPath());
        }
        return super.createReturnString(eo, content.toString());
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public ValueCall setContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    public boolean hasContent() {
        return this.content!=null && !this.content.isEmpty();
    }



}
