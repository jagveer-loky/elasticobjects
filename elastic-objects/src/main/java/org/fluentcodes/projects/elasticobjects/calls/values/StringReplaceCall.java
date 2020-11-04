package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/**
 * Created by Werner on 26.08.2020.
 */
public class StringReplaceCall extends SimpleValueFromEoCall{
    public static final String TO_REPLACE = "toReplace";
    public static final String REPLACE_BY = "replaceBy";

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
    public void setByParameter(final String values) {
        if (values == null||values.isEmpty()) {
            throw new EoException("Set by empty input values");
        }
        String[] array = values.split(", ");
        if (array.length>3) {
            throw new EoException("Short form should have form '<sourcePath>[,<targetPath>][,<condition>]' with max length 3 but has size " + array.length + ": '" + values + "'." );
        }
        if (array.length>0) {
            setSourcePath(array[0]);
        }
        if (array.length>1) {
            setToReplace(array[1]);
        }
        if (array.length>1) {
            setReplaceBy(array[2]);
        }
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
