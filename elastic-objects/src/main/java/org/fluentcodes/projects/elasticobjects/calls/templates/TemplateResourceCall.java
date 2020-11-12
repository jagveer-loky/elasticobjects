package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/*==>{ALLHeader.tpl, ., JAVA|>}|*/
/**
 * Parses the content of a FileConfig configuration. 
 * Created by Werner Diwischek at date Fri Nov 06 08:22:14 CET 2020.
 */
public class TemplateResourceCall extends FileReadCall {
/*=>{}.*/

    /*==>{ALLStaticNames.tpl, fieldMap/*, override eq false, JAVA|>}|*/
/*=>{}.*/

    /*==>{ALLInstanceVars.tpl, fieldMap/*, , JAVA|>}|*/
/*=>{}.*/

    public TemplateResourceCall() {
        super();
    }

    public TemplateResourceCall(final String templateFileConfigKey) {
        super(templateFileConfigKey);
    }
    public TemplateResourceCall(final String hostConfigKey, final String templateFileConfigKey) {
        super(hostConfigKey, templateFileConfigKey);
    }
    @Override
    public void setByParameter(final String values) {
        if (values == null||values.isEmpty()) {
            throw new EoException("Set by empty input values");
        }
        String[] array = values.split(", ");
        if (array.length>4) {
            throw new EoException("Short form should have form '<configKey>[,<targetPath>][,<condition>][,<keepCall>]' with max length 4 but has size " + array.length + ": '" + values + "'." );
        }
        if (array.length>0) {
            setConfigKey(array[0]);
        }
        if (array.length>1) {
            setSourcePath( array[1]);
        }
        if (array.length>2) {
            setCondition( array[2]);
        }
        if (array.length>3) {
            setKeepCall(KeepCalls.valueOf(array[3]));
        }
    }

    public String execute(EO eo) {
        String content = super.read(eo);
        return new TemplateCall(content).execute(eo);
    }

    /*==>{ALLSetter.tpl, fieldMap/*, , JAVA|>}|*/
/*=>{}.*/
}
