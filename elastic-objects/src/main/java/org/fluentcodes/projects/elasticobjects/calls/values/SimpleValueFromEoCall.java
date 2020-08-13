package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.Path;

/**
 * Created by Werner on 3.08.2020.
 */
public abstract class SimpleValueFromEoCall<RESULT> extends CallImpl<RESULT> {

    @Override
    public void setPathByTemplate(final Path templatePath) {
        setSourcePath(templatePath.directory(false));
    }

}
