package org.fluentcodes.projects.elasticobjects.calls.values;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.EO;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Werner on 14.07.2020.
 */
public class ConfigKeyListCall extends CallImpl<List> implements Call<List> {
    private static final Logger LOG = LogManager.getLogger(ConfigKeyListCall.class);
    private String configType;
    private String configFilter = ".*";

    @Override
    public List<String> execute(final EO eo) {
        super.check(eo);
        if (configType == null) {
            throw new EoException("Problem no config type defined.");
        }
        Set<String> keys = eo.getConfigsCache().getConfigNames(configType);

        try {
            return keys
                    .stream()
                    .filter(x->x.matches(configFilter))
                    .collect(Collectors.toList());
        }
        catch (Exception e) {
            throw new EoException(e);
        }
    }

    public String getConfigName() {
        return configType;
    }

    public void setConfigName(String configName) {
        this.configType = configName;
    }

    public String getConfigFilter() {
        return configFilter;
    }

    public void setConfigFilter(String configFilter) {
        this.configFilter = configFilter;
    }
}
