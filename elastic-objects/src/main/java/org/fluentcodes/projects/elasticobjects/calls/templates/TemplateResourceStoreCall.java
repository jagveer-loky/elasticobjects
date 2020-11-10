package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadWriteCall;

/*==>{ALLHeader.tpl, ., JAVA|>}|*/
/**
 * Executes a TemplateRecourceCall and then a FileWriteCall wth the targetConfigKey.
 * Created by Werner Diwischek at date Fri Nov 06 08:23:14 CET 2020.
 */
public class TemplateResourceStoreCall extends FileReadWriteCall {
/*=>{}.*/

    /*==>{ALLStaticNames.tpl, fieldMap/*, JAVA, override eq false|>}|*/
/*=>{}.*/

    /*==>{ALLInstanceVars.tpl, fieldMap/*, JAVA|>}|*/
/*=>{}.*/

    public TemplateResourceStoreCall() {
        super();
        setCompare(true);
    }

    public TemplateResourceStoreCall(final String sourceFileConfigKey, final String targetFileConfigKey) {
        super(sourceFileConfigKey, targetFileConfigKey);
        setCompare(true);
    }


        public String execute(EO eo)  {
        String template = read(eo);
        String content = new TemplateCall(template).execute(eo);
        return write(eo, content);
    }

    /*==>{ALLSetter.tpl, fieldMap/*, JAVA|>}|*/
 /*=>{}.*/
}
