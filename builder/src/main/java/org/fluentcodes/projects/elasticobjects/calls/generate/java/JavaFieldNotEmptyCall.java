package org.fluentcodes.projects.elasticobjects.calls.generate.java;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.values.SimpleValueFromEoCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/**
 * Created by Werner on 3.11.2020.
 */
public class JavaFieldNotEmptyCall extends SimpleValueFromEoCall {

    @Override
    public String execute(final EO eo) {
        try {
            String[] models = ((String)eo.get()).split(",");
            if (models[0].matches(".*(Map|List|String)$")) {
                return " && !" + eo.get("../fieldKey") + ".isEmpty()" ;
            }
            else {
                return "";
            }
        }
        catch (Exception e) {
            throw new EoException(e.getMessage());
        }
    }
}
