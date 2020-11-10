package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadWriteCall;

/*==>{ALLHeader.tpl, ., JAVA|>}|*/
/**
 * First try to read the target file with targetFileConfigKey and use it as template if exists. If not try to use templateFileConfigKey as template with FileReadCall. After processing a FileWriteCall wth the targetConfigKey is executed.
 * Created by Werner Diwischek at date
 * @creationDate Fri Nov 06 07:40:26 CET 2020.
 * @modificationDate test
 * @author Werner Diwischek
 */
public class TemplateResourceStoreKeepCall extends FileReadWriteCall {
/*=>{}.*/

    /*==>{ALLStaticNames.tpl, fieldMap/*, JAVA, override eq false|>}|*/
/*=>{}.*/

    /*==>{ALLInstanceVars.tpl, fieldMap/*, JAVA|>}|*/
/*=>{}.*/

    public TemplateResourceStoreKeepCall(String fileConfigKeyTemplate, String targetConfig) {
        super(fileConfigKeyTemplate, targetConfig);
    }

    public String execute(EO eo)  {
        try {
            final String template = (String)new FileReadCall(getTargetFileConfigKey()).execute(eo);
            if (!ParserCurlyBracket.containsStartSequence(template)) {
                final String message = "Skip parsing: '" + getTargetFileConfigKey() + "' has no curly start sequences, so nothing will be done!";
                eo.debug(message);
                return message;
            }
            return write(eo, new TemplateCall(template).execute(eo));
        }
        catch (Exception e) {
            return write(eo, new TemplateCall(read(eo)).execute(eo));
        }
    }

    /*==>{ALLSetter.tpl, fieldMap/*, JAVA|>}|*/
/*=>{}.*/
}
