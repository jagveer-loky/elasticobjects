package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/**
 * Created by Werner on 30.9.2020.
 */
public class ConfigLinkCall extends GithubLinkCall {
    public ConfigLinkCall() {
        super();
    }
    public ConfigLinkCall(final String fileConfigKey) {
        this(fileConfigKey, "ModelConfig");
    }
    public ConfigLinkCall(final String fileConfigKey, final String configType) {
        super(fileConfigKey,configType);
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
        builder.append("\">&equiv;");
        builder.append(getConfigKey());
        builder.append("</a>\n");
        return builder.toString();
    }
}
