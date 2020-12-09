package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadWriteCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/

/**
 * Executes a TemplateRecourceCall and then a FileWriteCall wth the targetConfigKey.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Tue Dec 08 11:39:40 CET 2020
 */
public class TemplateResourceStoreCall extends FileReadWriteCall  {
/*=>{}.*/

    /*==>{ALLStaticNames.tpl, fieldBeans/*, super eq false, JAVA|>}|*/
/*=>{}.*/

    /*==>{ALLInstanceVars.tpl, fieldBeans/*, super eq false, JAVA|>}|*/
/*=>{}.*/

    public TemplateResourceStoreCall() {
        super();
        setCompare(true);
    }

    public TemplateResourceStoreCall(final String sourceFileConfigKey, final String targetFileConfigKey) {
        super(sourceFileConfigKey, targetFileConfigKey);
    }


    public String execute(EO eo)  {
        String template = readSourceOrTarget(eo);
        if (!ParserCurlyBracket.containsStartSequence(template)) {
            return "Nothing to do no template parts.";
        }
        String content = new TemplateCall(template).execute(eo);
        return writeTarget(eo, content);
    }

    private String readSourceOrTarget(final EO eo) {
        FileConfig targetFileConfig = eo.getConfigsCache().findFile(getTargetFileConfigKey());
        if (!targetFileConfig.isTemplate()) {
            return readSource(eo);
        }
        else {
            try {
                return readTarget(eo);
            }
            catch (Exception e) {
                return readSource(eo);
            }
        }
    }

    /*==>{ALLSetter.tpl, fieldBeans/*, super eq false, JAVA|>}|*/
/*=>{}.*/
}
