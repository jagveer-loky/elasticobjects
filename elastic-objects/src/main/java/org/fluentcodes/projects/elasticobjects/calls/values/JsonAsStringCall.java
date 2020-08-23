package org.fluentcodes.projects.elasticobjects.calls.values;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/**
 * Created by Werner on 3.08.2020.
 */
public class JsonAsStringCall extends SimpleValueFromEoCall<String> {
    private static final Logger LOG = LogManager.getLogger(JsonAsStringCall.class);
    public JsonAsStringCall() {
        setInTemplate(true);
    }
    @Override
    public String execute(final EO eo) {
        super.check(eo);
        return new EOToJSON().toJSON(eo);
    }
}
