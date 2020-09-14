package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/**
 * Created by Werner on 26.08.2020.
 */
public class StringReplaceCall extends SimpleValueFromEoCall{
    private String toReplace;
    private String replaceBy;
    public StringReplaceCall() {
        super();
    }
    public StringReplaceCall(final String toReplace, final String replaceBy) {
        super();
        this.toReplace = toReplace;
        this.replaceBy = replaceBy;
    }
    @Override
    public String execute(final EO eo) {
        super.check(eo);
        if (eo.get() == null) {
            throw new EoException("Value " + eo.getPathAsString() + " is null ");
        }
        if (toReplace == null) {
            throw new EoException("toReplace is null! ");
        }
        if (replaceBy == null) {
            throw new EoException("replaceBy is null! ");
        }
        if (!(eo.get() instanceof String)) {
            throw new EoException("Value " + eo.getPathAsString() + " for replace is not a String but " + eo.get().getClass());
        }
        return ((String)eo.get()).replaceAll(toReplace, replaceBy);
    }

    public String getToReplace() {
        return toReplace;
    }

    public void setToReplace(String toReplace) {
        this.toReplace = toReplace;
    }

    public String getReplaceBy() {
        return replaceBy;
    }

    public void setReplaceBy(String replaceBy) {
        this.replaceBy = replaceBy;
    }
}
