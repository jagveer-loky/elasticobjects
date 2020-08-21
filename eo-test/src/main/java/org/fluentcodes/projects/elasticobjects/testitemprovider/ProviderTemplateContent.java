package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.tools.xpect.IOString;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.PATH_INPUT;

public enum ProviderTemplateContent {
    EMPTY(PATH_INPUT + "calls/configs/ConfigKeysCallModelConfig.tpl");

    private final String content;
    private String fileName;

    ProviderTemplateContent(final String content) {
        if (content.startsWith(PATH_INPUT)) {
            this.fileName = content;
            this.content = new IOString().setFileName(content).read();
        } else {
            this.content = content;
        }
    }

    public String content() {
        return content;
    }

    public String getFileName() {
        return fileName;
    }

    public String getConfigKey() {
        if (fileName == null) {
            throw new EoException("No fileName for " + name() + " defined");
        }
        return fileName.replaceAll(".*/", "");
    }
}
