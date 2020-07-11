package org.fluentcodes.projects.elasticobjects.calls.values;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.EO;

/**
 * Created by Werner on 13.07.2020.
 */
public class SinusValueCall extends CallImpl<Double> implements Call<Double> {
    private static final Logger LOG = LogManager.getLogger(SinusValueCall.class);
    @Override
    public Double execute(final EO eo) {
        super.check(eo);
        if (!(eo.get() instanceof Double)) {
            throw new EoException("Value is not instance of Double but " + eo.getModelClass().getSimpleName());
        }
        try {
            return Math.sin((Double) eo.get());
        }
        catch (Exception e) {
            throw new EoException(e);
        }
    }
}
