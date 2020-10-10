package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.files.DirectoryReadCall;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/**
 * Created by werner.diwischek on 26.08.20.
 */
public class TemplateResourceCall extends TemplateCall {
    public static final String KEEP_CALL = "keepCall";
    private String configKey;
    private String fileName;
    private KeepCalls keepCall;
    public TemplateResourceCall() {
        super();
    }

    public TemplateResourceCall(String configKey) {
        this();
        this.configKey = configKey;
    }

    public String execute(EO eo)  {
        if (!init(eo)) {
            return "";
        }
        final String configKey = Parser.replace(getConfigKey(),eo);
        if (hasFileName()) { // directory config
            if (!(hasConfigKey())) {
                throw new EoException("Problem that TemplateResourceCall with fileName '" + getFileName() + "' expects a configKey value.");
            }
            final String fileName = Parser.replace(getFileName(),eo);
            super.setContent(new DirectoryReadCall(configKey)
                    .setFileName(fileName)
                    .execute(eo));
        }
        else { // file config
            super.setContent((String)new FileReadCall(configKey).execute(eo));
        }
        return super.execute(eo);
    }
    public boolean hasConfigKey() {
        return configKey !=null && !configKey.isEmpty();
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public boolean hasFileName() {
        return fileName!=null  && !fileName.isEmpty();
    }

    public String getFileName() {
        return this.fileName;
    }

    public TemplateResourceCall setFileName(final String value) {
        this.fileName = value;
        return this;
    }

    public boolean hasKeepCall() {
        return keepCall !=null  && keepCall != KeepCalls.NONE;
    }

    public KeepCalls getKeepCall() {
        return keepCall;
    }

    public void setKeepCall(KeepCalls keepCalls) {
        this.keepCall = keepCalls;
    }
}
