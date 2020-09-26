package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by werner.diwischek on 26.08.20.
 */
public class TemplateResourceCall extends TemplateCall implements PropertiesTemplateResourceAccessor{
    private static final transient Logger LOG = LogManager.getLogger(TemplateResourceCall.class);
    private Map<String, Object> properties;
    public TemplateResourceCall() {
        super();
        properties = new LinkedHashMap<>();
    }

    public TemplateResourceCall(String configKey) {
        this();
        setTemplateKey(configKey);
    }

    @Override
    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public String prepend() {
        return getDirective();
    }
    public String postPend() {
        return getEndDirective();
    }

    public String execute(EO eo)  {
        String fileConfigKey = new ParserEoReplace(getTemplateKey()).parse(eo);
        FileConfig config = eo.getConfigsCache().findFile(fileConfigKey);
        if (config.hasPermissions(PermissionType.EXECUTE, eo.getRoles())) {
            super.setContent((String) config.read());
            return super.execute(eo);
        }
        else {
            return "No permission to execute '" + config.getNaturalId() + "' with roles '" + eo.getRoles() + "'.";
        }
    }

}
