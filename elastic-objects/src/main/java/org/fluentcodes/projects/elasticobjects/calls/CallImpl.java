package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.EO;

/**
 * Created by Werner on 13.07.2020.
 * Elementary call with mapping configuration keys to configuration via constructor.
 */
public abstract class CallImpl<RESULT> implements Call<RESULT> {
    private static final Logger LOG = LogManager.getLogger(CallImpl.class);
    public CallImpl() {
    }


    public void check(final EO eo) {
        if (eo == null) {
            throw new EoException("Null eo value");
        }
        if (eo.get()== null) {
            throw new EoException("empty eo value");
        }
    }

}
