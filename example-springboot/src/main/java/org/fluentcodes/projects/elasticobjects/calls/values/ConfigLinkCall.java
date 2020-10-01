package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;

/**
 * Created by Werner on 30.9.2020.
 */
public class ConfigLinkCall extends GithubLinkCall {
    public ConfigLinkCall() {
        super();
    }
    public ConfigLinkCall(final String configKey) {
        this(configKey, "ModelConfig");
    }
    public ConfigLinkCall(final String configKey, final String configType) {
        super(configKey,configType);
    }

    @Override
    public String execute(final EO eo) {
        super.check(eo);
        if (!hasConfigKey()) {
            throw new EoException("No key is set. Could not find value");
        }
        super.setNoLabel();
        StringBuilder builder = new StringBuilder(super.execute(eo));
        builder.append("\n<a href=\"/config/");
        builder.append(getConfigType());
        builder.append("/");
        builder.append(getConfigKey());
        builder.append("\">&ocir;");
        builder.append(getConfigKey());
        builder.append("</a>\n");
        return builder.toString();
    }
}
