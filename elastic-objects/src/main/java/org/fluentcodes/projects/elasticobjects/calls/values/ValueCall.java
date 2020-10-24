package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;

/**
 * Add value to eo.
 * Created by Werner on 02.08.2020.
 */

public class ValueCall extends CallImpl {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
