package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;

/**
 * Created by Werner on 3.08.2020.
 */
public abstract class SimpleValueFromEoCall extends CallImpl{

    @Override
    public void setPathByTemplate(final Path templatePath) {
        setSourcePath(templatePath.directory(false));
    }

}
