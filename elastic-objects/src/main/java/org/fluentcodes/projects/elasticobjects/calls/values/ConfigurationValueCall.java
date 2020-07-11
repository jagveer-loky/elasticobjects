package org.fluentcodes.projects.elasticobjects.calls.values;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.EO;

import java.util.Set;

/**
 * Created by Werner on 14.07.2020.
 */
public class ConfigurationValueCall extends CallImpl<Set> implements Call<Set> {
    private static final Logger LOG = LogManager.getLogger(ConfigurationValueCall.class);
    private String configType;
    private String configKey;
    private String configFilter;

    @Override
    public Set execute(final EO eo) {
        super.check(eo);
        if (configType == null) {
            throw new EoException("No config type defined");
        }
        return eo.getConfigsCache().getConfigMap(configType).getKeys();
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

}
