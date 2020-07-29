package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.calls.CallResource;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.config.Config;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.executor.CallExecutorResource;
import org.fluentcodes.projects.elasticobjects.calls.executor.ExecutorList;
import org.fluentcodes.projects.elasticobjects.calls.executor.ExecutorListTemplate;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.*;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

/**
 * Created by werner.diwischek on 20.03.17.
 */
public class TemplateCall extends CallResource<String> {
    private static final transient Logger LOG = LogManager.getLogger(TemplateCall.class);
    private String content;
    private ExecutorList bufferedExecutor;
    private String loopPath;
    private TemplateConfig.KeepKeys keepKey;
    private String keepStart;
    private String keepEnd;

    public TemplateCall() {
        super(PermissionType.READ);
    }

    public TemplateCall setConfigKey(final String configKey) {
        return (TemplateCall) super.setConfigKey(configKey);
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

    public TemplateCall setContent(String entry) {
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
        super.resolve(eo);
        StringBuilder result = new StringBuilder();
        return result.toString();
    }

}
