package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.calls.files.DirectoryReadCall;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/**
 * Created by werner.diwischek on 26.08.20.
 */
public class TemplateDirResourceCall extends TemplateResourceCall {
    private String fileName;
    public TemplateDirResourceCall() {
        super();
    }

    public TemplateDirResourceCall(String configKey) {
        super(configKey);
    }

    @Override
    public void setByString(final String values) {
        if (values == null||values.isEmpty()) {
            throw new EoException("Set by empty input values");
        }
        String[] array = values.split(", ");
        if (array.length>5) {
            throw new EoException("Short form should have form '<configKey>[, <sourcePath>][,<targetPath>][,<condition>]' with max length 3 but has size " + array.length + ": '" + values + "'." );
        }
        if (array.length>0) {
            setConfigKey(array[0]);
        }
        if (array.length>1) {
            this.fileName = array[1];
        }
        if (array.length>2) {
            setSourcePath( array[2]);
        }
        if (array.length>3) {
            setTargetPath( array[2]);
        }
        if (array.length>4) {
            setCondition( array[4]);
        }
        if (array.length>5) {
            setKeepCall(KeepCalls.valueOf(array[5]));
        }
        if (!hasSourcePath()) {
            setSourcePath(PathElement.SAME);
        }
        if (!hasTargetPath()) {
            setTargetPath(PathElement.TEMPLATE);
        }
    }

    public String execute(EO eo)  {
        if (!init(eo)) {
            return "";
        }

        if (!(hasConfigKey())) {
            throw new EoException("Problem that TemplateResourceCall with fileName '" + getFileName() + "' expects a configKey value.");
        }
        final String configKey = Parser.replacePathValues(getConfigKey(),eo);
        if (!hasFileName()) {
            throw new EoException("No fileName defined for '" + configKey + "'");
        }// directory config

        final String fileName = Parser.replacePathValues(getFileName(),eo);
        super.setContent(new DirectoryReadCall(configKey)
                .setFileName(fileName)
                .execute(eo));
        return super.execute(eo);
    }

    public boolean hasFileName() {
        return fileName!=null  && !fileName.isEmpty();
    }

    public String getFileName() {
        return this.fileName;
    }

    public TemplateDirResourceCall setFileName(final String value) {
        this.fileName = value;
        return this;
    }
}
