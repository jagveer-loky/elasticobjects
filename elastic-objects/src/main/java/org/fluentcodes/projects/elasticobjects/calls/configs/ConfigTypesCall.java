package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Werner on 22.08.2020.
 */
public class ConfigTypesCall extends CallImpl {
    private SortOrder sortOrder = SortOrder.ASC;
    private Class<? extends Config> configClass;

    public ConfigTypesCall() {
        super();
    }

    @Override
    public Object execute(final EO eo) {
        super.check(eo);
        Set<Class> keys = eo.getConfigsCache().getKeys();
        try {
            return keys
                    .stream()
                    .map(x->x.getSimpleName())
                    .sorted(sortOrder.getComparator())
                    .collect(Collectors.toList());
        }
        catch (Exception e) {
            throw new EoException(e);
        }
    }
}
