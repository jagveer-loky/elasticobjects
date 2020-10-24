package org.fluentcodes.projects.elasticobjects.calls.files;


import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.calls.ResourceWriteCall;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserSqareBracket;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.tools.xpect.IOString;

/**
 * Created by werner.diwischek on 9.7.2020.
 */
public class FileWriteCall extends ResourceWriteCall {
    private String classPath;
    private String content;
    public FileWriteCall() {
        super();
    }

    public FileWriteCall(final String configKey) {
        super(configKey);
    }
    public FileWriteCall(final String configKey, final String content) {
        super(configKey);
        setContent(content);
    }

    public boolean hasContent() {
        return content != null && !content.isEmpty();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public FileConfig getFileConfig()  {
        return ((FileConfig) getConfig());
    }

    @Override
    public String execute(final EO eo)  {
        if (!init(eo)) {
            return null;
        }
        this.write(eo);
        return content;
    }

    public void write(EO eo)  {
        if (!hasContent()) {
            if (eo.isScalar()) {
                content = eo.get().toString();
            } else {
                content = eo.toString();
            }
        }
        String url = getFileConfig().createUrl().getFile();
        if (ParserSqareBracket.containsStartSequence(url)) {
            url = new ParserSqareBracket(url).parse(eo);
        }
        if (hasClassPath()) {
            url = getClassPath() + Path.DELIMITER + url;
        }
        write(url, content);
    }

    public static void write(String targetFile, Object content)  {
        new IOString().setFileName(targetFile).write((String)content);
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return FileConfig.class;
    }

    public boolean hasClassPath() {
        return classPath!=null && !classPath.isEmpty();
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }
}
