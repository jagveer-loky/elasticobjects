package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.calls.files.DirectoryReadCall;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/**
 * Created by werner.diwischek on 26.08.20.
 */
public class TemplateResourceCall extends TemplateCall {
    public static final String KEEP_CALL = "keepCall";
    private String configKey;
    private KeepCalls keepCall;
    public TemplateResourceCall() {
        super();
    }

    public TemplateResourceCall(String configKey) {
        this();
        this.configKey = configKey;
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
            configKey = array[0];
        }
        if (array.length>1) {
            setSourcePath( array[1]);
        }
        if (array.length>2) {
            setKeepCall(KeepCalls.valueOf(array[2]));
        }
        if (array.length>3) {
            setCondition( array[3]);
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
        final String configKey = Parser.replacePathValues(getConfigKey(),eo);
        super.setContent((String)new FileReadCall(configKey).execute(eo));
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
