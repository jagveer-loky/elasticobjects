package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.EO;

public interface Call<RESULT> {
    RESULT execute(final EO eo);
}
