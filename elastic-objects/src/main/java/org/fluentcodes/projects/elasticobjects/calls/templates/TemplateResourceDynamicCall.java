package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;

/**
 * Created by werner.diwischek on 26.08.20.
 */
public class TemplateCallResourceDynamic extends TemplateCall {
    private static final transient Logger LOG = LogManager.getLogger(TemplateCallResourceDynamic.class);
    private String configKey;

    public TemplateCallResourceDynamic() {
        super();
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String execute(EO eo)  {
        String replacedConfig = new TemplateParser(configKey).parse(eo);
        TemplateConfig config = eo.getConfigsCache().findTemplate(replacedConfig);
        super.setContent(config.read());
        return super.execute(eo);
    }

}
