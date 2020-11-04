package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.calls.files.FileWriteCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.tools.xpect.IOString;

/**
 * Created by werner.diwischek on 21.9.20.
 */
public class TemplateResourceStoreKeepCall extends TemplateCall {
    public static final String TARGET_FILE = "targetFile";
    private String targetConfigKey;
    private String configKey;
    public TemplateResourceStoreKeepCall(String configKey, String targetConfig) {
        super();
        this.configKey = configKey;
        this.targetConfigKey = targetConfig;
    }

    public String execute(EO eo)  {
        FileConfig targetConfig = eo.getConfigsCache().findFile(this.targetConfigKey);
        String targetFileName = ParserSqareBracket.replacePathValues(targetConfig.getUrlPath(), eo);
        targetConfig.hasPermissions(PermissionType.WRITE, eo.getRoles());
        try {
            setContent((String) new FileReadCall(targetConfigKey).execute(eo));
        }
        catch (Exception e) {
            setContent((String) new FileReadCall(configKey).execute(eo));
        }
        if (!ParserCurlyBracket.containsStartSequence(getContent())) {
            throw new EoException("Existing target file '" + targetFileName + "' has no curly start sequences!");
        }
        String result = super.execute(eo);
        if (result.equals(getContent())) {
            return "Same content, nothing will be written to " + targetFileName;
        }
        new FileWriteCall(targetConfigKey, result)
                .execute(eo);
        return "Created " + targetFileName;
    }

    public String getTargetConfigKey() {
        return targetConfigKey;
    }

    public void setTargetConfigKey(String targetConfigKey) {
        this.targetConfigKey = targetConfigKey;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }
}
