package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.tools.xpect.IOString;

/**
 * Created by werner.diwischek on 21.9.20.
 */
public class TemplateResourceStoreCall extends TemplateResourceCall implements PropertiesTemplateResourceStoreAccessor {

    public TemplateResourceStoreCall() {
        super();
    }

    public TemplateResourceStoreCall(String templateKey) {
        super(templateKey);
    }

    public String execute(EO eo)  {
        String content = super.execute(eo);
        String targetFile = new ParserEoReplace(getTargetFile()).parse(eo);
        new IOString().setFileName(targetFile).write(content);
        System.out.println("Created " + targetFile);
        return "";
    }
}
