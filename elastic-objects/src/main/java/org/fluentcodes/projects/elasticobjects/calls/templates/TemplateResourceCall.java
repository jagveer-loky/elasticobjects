package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.calls.CallKeep;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/*==>{TemplateResourceCall->ALLHeader.tpl, ., JAVA|>}|*/
import org.fluentcodes.projects.elasticobjects.calls.CallKeep;
/**
 * Parses the content of a FileConfig configuration. 
 * Created by Werner Diwischek at date Fri Nov 06 08:22:14 CET 2020.
 */
public class TemplateResourceCall extends TemplateCall implements CallKeep {
/*=>{}.*/

    /*==>{TemplateResourceCall->ALLStaticNames.tpl, fieldMap/*, JAVA, override eq false|>}|*/
   public static final String KEEP_CALL = "keepCall";
   public static final String TEMPLATE_FILE_CONFIG_KEY = "templateFileConfigKey";
/*=>{}.*/

    /*==>{TemplateResourceCall->ALLInstanceVars.tpl, fieldMap/*, JAVA|>}|*/
   private  KeepCalls keepCall;
   private  String templateFileConfigKey;
/*=>{}.*/

    public TemplateResourceCall() {
        super();
    }

    public TemplateResourceCall(String templateFileConfigKey) {
        this();
        this.templateFileConfigKey = templateFileConfigKey;
    }

    @Override
    public void setByParameter(final String values) {
        if (values == null || values.isEmpty()) {
            throw new EoException("Set by empty input values");
        }
        String[] array = values.split(", ");
        if (array.length > 5) {
            throw new EoException("Short form should have form '<configKey>[, <sourcePath>][,<targetPath>][,<condition>]' with max length 3 but has size " + array.length + ": '" + values + "'.");
        }
        if (array.length > 0) {
            templateFileConfigKey = array[0];
        }
        if (array.length > 1) {
            setSourcePath(array[1]);
        }
        if (array.length > 2) {
            setKeepCall(KeepCalls.valueOf(array[2]));
        }
        if (array.length > 3) {
            setCondition(array[3]);
        }
        if (!hasSourcePath()) {
            setSourcePath(PathElement.SAME);
        }
        if (!hasTargetPath()) {
            setTargetPath(PathElement.TEMPLATE);
        }
    }

    public String execute(EO eo) {
        if (!init(eo)) {
            return "";
        }
        final String templateKey = ParserSqareBracket.replacePathValues(getTemplateFileConfigKey(), eo);
        super.setContent((String) new FileReadCall(templateKey).execute(eo));
        return super.execute(eo);
    }

    /*==>{TemplateResourceCall->ALLSetter.tpl, fieldMap/*, JAVA|>}|*/
    /**
    Will use an existing  result file beforehand as template. 
    */
    public TemplateResourceCall setKeepCall(KeepCalls keepCall) {
        this.keepCall = keepCall;
        return this;
    }
    
    public KeepCalls getKeepCall () {
       return this.keepCall;
    }
    
    public boolean hasKeepCall () {
        return keepCall!= null;
    }
    /**
    A target for persisting template results in TemplateResourceStoreCall. 
    */
    public TemplateResourceCall setTemplateFileConfigKey(String templateFileConfigKey) {
        this.templateFileConfigKey = templateFileConfigKey;
        return this;
    }
    
    public String getTemplateFileConfigKey () {
       return this.templateFileConfigKey;
    }
    
    public boolean hasTemplateFileConfigKey () {
        return templateFileConfigKey!= null && !templateFileConfigKey.isEmpty();
    }
/*=>{}.*/
}
