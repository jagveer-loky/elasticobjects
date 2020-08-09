package org.fluentcodes.projects.elasticobjects.calls.values;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.EO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Werner on 14.07.2020.
 */
public class ConfigTypeKeyListCall extends CallImpl<List> {
    private static final Logger LOG = LogManager.getLogger(ConfigTypeKeyListCall.class);
    private String configFilter;

    @Override
    public List execute(final EO eo) {
        super.check(eo);
        List<String> keys = eo.getConfigsCache().getConfigClassesAsStringList();
        if (configFilter == null) {
            return keys;
        }
        try {
            return keys
                    .stream()
                    .filter(x->x.matches(configFilter))
                    .collect(Collectors.toList());
        }
        catch (Exception e) {
            return keys;
        }
    }

    public String getConfigFilter() {
        return configFilter;
    }

    public void setConfigFilter(String configFilter) {
        this.configFilter = configFilter;
    }
}
