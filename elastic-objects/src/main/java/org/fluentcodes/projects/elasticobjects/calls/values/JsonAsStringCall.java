package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOToJSON;

/**
 * Created by Werner on 3.08.2020.
 */
public class JsonAsStringCall extends SimpleValueFromEoCall{
    public JsonAsStringCall() {
        setTargetPath(TARGET_AS_STRING);
    }
    @Override
    public String execute(final EO eo) {
        super.check(eo);
        return new EOToJSON().toJSON(eo);
    }
}
