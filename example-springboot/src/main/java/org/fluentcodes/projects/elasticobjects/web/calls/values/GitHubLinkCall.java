package org.fluentcodes.projects.elasticobjects.web.calls.values;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoChild;
import org.fluentcodes.projects.elasticobjects.calls.values.SimpleValueFromEoCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;

/**
 * Created by Werner on 3.08.2020.
 */
public class GitHubLinkCall extends SimpleValueFromEoCall<String> {
    private static final Logger LOG = LogManager.getLogger(GitHubLinkCall.class);

    @Override
    public String execute(final EO eo) {
        super.check(eo);
        StringBuilder builder = new StringBuilder("https://github.com/fluentcodes/elasticobjects/blob/master/");
        if (!((EoChild)eo).hasEo(ModelConfig.PACKAGE_PATH)) {
            throw new EoException("No packagePath defined!");
        }
        if (((EoChild)eo).hasEo(ModelConfig.MODULE)) {
            throw new EoException("No packagePath defined!");
        }
        if (!((EoChild)eo).hasEo(ModelConfig.SUB_MODULE)) {
            throw new EoException("No packagePath defined!");
        }
        return "to be implemented...";
    }
}
