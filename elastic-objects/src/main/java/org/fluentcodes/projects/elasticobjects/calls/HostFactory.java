package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.ConfigConfigInterface;
import org.fluentcodes.projects.elasticobjects.models.ConfigFactory;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.Scope;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

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
