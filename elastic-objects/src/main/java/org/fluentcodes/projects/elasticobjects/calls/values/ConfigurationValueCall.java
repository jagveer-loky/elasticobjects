package org.fluentcodes.projects.elasticobjects.calls.values;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/**
 * Created by Werner on 14.07.2020.
 */
public class ConfigurationValueCall extends CallImpl  implements SimpleCommand {
    private String configType;
    private String configKey;
    private String configFilter;

    @Override
    public Object execute(final EO eo) {
        super.check(eo);
        if (configType == null) {
            throw new EoException("No config type defined");
        }
        return eo.getConfigsCache().getConfigNames(configType);
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

}
