package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallExecutorResource;
import org.fluentcodes.projects.elasticobjects.calls.files.FileCallRead;
import org.fluentcodes.projects.elasticobjects.config.Config;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

/**
 * Created by werner.diwischek on 26.08.20.
 */
public class TemplateCallResource extends FileCallRead {
    private static final transient Logger LOG = LogManager.getLogger(TemplateCallResource.class);
    private String content;

    public TemplateCallResource() {
        super();
        setInTemplate(true);
    }

    @Override
    public TemplateCallResource setConfigKey(final String configKey) {
        return (TemplateCallResource) super.setConfigKey(configKey);
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return TemplateConfig.class;
    }

    public boolean hasContent() {
        return content != null && !content.isEmpty();
    }

    public String getContent() {
        return content;
    }

    public TemplateCallResource setContent(String entry) {
        if (entry == null) {
            return this;
        }
        if (hasContent()) {
            return this;
        }
        this.content = ScalarConverter.toString(entry);
        return this;
    }

    public TemplateConfig getTemplateConfig() {
        return (TemplateConfig) getConfig();
    }

    /**
     * This method without external attributes will be called from {@link CallExecutorResource}
     *
     * @param eo the adapter data object to be rendered
     * @return
     * @
     */
    public String execute(EO eo)  {
        super.resolve(eo.getConfigsCache());
        TemplateCall call = new TemplateCall();
        call.setContent(super.execute(eo));
        return call.execute(eo);
    }

}
