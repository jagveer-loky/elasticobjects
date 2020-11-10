package org.fluentcodes.projects.elasticobjects.calls.generate.java;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/**
 * Created by Werner on 3.11.2020.
 */
public class JavaFieldFinalCall extends CallImpl  implements SimpleCommand {

    @Override
    public String execute(final EO eo) {
        try {
            return (Boolean)eo.get()?"final ":"";
        }
        catch (Exception e) {
            throw new EoException(e.getMessage());
        }
    }
}
