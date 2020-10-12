package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.tools.xpect.IOString;

/**
 * Created by werner.diwischek on 21.9.20.
 */
public class TemplateResourceStoreCall extends TemplateResourceCall {
    public static final String TARGET_FILE = "targetFile";
    private String targetFile;
    public TemplateResourceStoreCall() {
        super();
    }

    public TemplateResourceStoreCall(String configKey) {
        super(configKey);
    }

    public String execute(EO eo)  {
        String content = super.execute(eo);
        String targetFile = new ParserEoReplace(getTargetFile()).parse(eo);
        new IOString().setFileName(targetFile).write(content);
        return "Created " + targetFile;
    }

    public String getTargetFile() {
        return targetFile;
    }

    public void setTargetFile(String targetFile) {
        this.targetFile = targetFile;
    }
}
