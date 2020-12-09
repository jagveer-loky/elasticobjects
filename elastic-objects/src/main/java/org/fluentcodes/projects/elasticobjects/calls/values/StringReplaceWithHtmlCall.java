package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/

/**
 * Replaces white space with underscore.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Tue Dec 08 12:01:44 CET 2020
 */
public class StringReplaceWithHtmlCall extends CallImpl implements SimpleCommand {
/*=>{}.*/

    /*==>{ALLStaticNames.tpl, fieldBeans/*, super eq false, JAVA|>}|*/
/*=>{}.*/

    /*==>{ALLInstanceVars.tpl, fieldBeans/*, super eq false, JAVA|>}|*/
/*=>{}.*/

    @Override
    public String execute(final EO eo) {
        super.check(eo);
        try {
            // https://alvinalexander.com/blog/post/java/remove-non-alphanumeric-characters-java-string/
            return ((String) eo.get()).replaceAll("\n","<br/>");
        }
        catch (Exception e) {
            throw new EoException(e.getMessage());
        }
    }
    /*==>{ALLSetter.tpl, fieldBeans/*, super eq false, JAVA|>}|*/
/*=>{}.*/
}
