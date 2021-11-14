package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.ConfigFactory;
import org.fluentcodes.projects.elasticobjects.models.Scope;

/**
 * Created by Werner on 1.11.2021.
 */

public class HostFactory extends ConfigFactory< HostBean, HostConfig> {
    public HostFactory() {
        this(Scope.DEV);
    }
    public HostFactory(final Scope scope) {
        super(scope, HostBean.class, HostConfig.class);
    }


}
