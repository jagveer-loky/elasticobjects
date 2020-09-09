package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;

/**
 * Created by werner.diwischek on 26.08.20.
 */
public class TemplateResourceCall extends TemplateCall {
    private static final transient Logger LOG = LogManager.getLogger(TemplateResourceCall.class);
    private String configKey;
    private KeepCalls keepCall;
    private String directive;
    private String endDirective;

    public TemplateResourceCall() {
        super();
    }

    public TemplateResourceCall(String configKey) {
        super();
        this.configKey = configKey;
    }

    public String getConfigKey() {
        return configKey;
    }

    public TemplateResourceCall setConfigKey(String configKey) {
        this.configKey = configKey;

        return this;
    }

    public KeepCalls getKeepCall() {
        return keepCall;
    }

    public void setKeepCall(KeepCalls keepCall) {
        if (this.keepCall != null) {
            return;
        }
        this.keepCall = keepCall;
    }

    public String getDirective() {
        return directive;
    }

    public void setDirective(String directive) {
        this.directive = directive;
    }

    public String getEndDirective() {
        return endDirective;
    }

    public void setEndDirective(String endDirective) {
        this.endDirective = endDirective;
    }

    public String prepend() {
        if (directive==null) {
            return "";
        }
        return directive;
    }
    public String postPend() {
        if (endDirective==null) {
            return "";
        }
        return endDirective;
    }

    public String execute(EO eo)  {
        String replacedConfig = new ParserEoReplace(configKey).parse(eo);
        FileConfig config = eo.getConfigsCache().findFile(replacedConfig);
        if (config.hasPermissions(PermissionType.EXECUTE, eo.getRoles())) {
            super.setContent(config.read());
            return super.execute(eo);
        }
        else {
            return "No permission to execute '" + config.getNaturalId() + "' with roles '" + eo.getRoles() + "'.";
        }
    }

}
