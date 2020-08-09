package org.fluentcodes.projects.elasticobjects.calls.values;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.paths.Path;

/**
 * Created by Werner on 3.08.2020.
 */
public abstract class SimpleValueFromEoCall<RESULT> extends CallImpl<RESULT> {

    @Override
    public void setPathByTemplate(final Path templatePath) {
        setSourcePath(templatePath.directory(false));
    }

}
