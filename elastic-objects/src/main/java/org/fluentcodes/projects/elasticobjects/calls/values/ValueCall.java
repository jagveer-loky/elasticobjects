package org.fluentcodes.projects.elasticobjects.calls.values;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

/**
 * Created by Werner on 13.07.2020.
 */
public class SinusValueCall extends CallImpl<Double> implements Call<Double> {
    private static final Logger LOG = LogManager.getLogger(SinusValueCall.class);
    @Override
    public Double execute(final EO eo) {
        super.check(eo);
        Double value = ScalarConverter.toDouble(eo.get());
        try {
            return Math.sin(value);
        }
        catch (Exception e) {
            throw new EoException(e.getMessage());
        }
    }
}
