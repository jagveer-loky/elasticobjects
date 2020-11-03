package org.fluentcodes.projects.elasticobjects.calls.generate.java;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.values.SimpleValueFromEoCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/**
 * Created by Werner on 3.11.2020.
 */
public class JavaExtendsCall extends SimpleValueFromEoCall {

    @Override
    public String execute(final EO eo) {
        try {
            return eo.get()!=null? "extends " + eo.get() : "";
        }
        catch (Exception e) {
            throw new EoException(e.getMessage());
        }
    }
}
