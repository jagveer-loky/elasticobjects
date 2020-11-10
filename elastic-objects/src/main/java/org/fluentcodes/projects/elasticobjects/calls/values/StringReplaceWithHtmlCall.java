package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
/**
 * Replaces white space with underscore.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Tue Nov 10 14:35:43 CET 2020
 */
public class StringReplaceWithHtmlCall extends CallImpl implements SimpleCommand {
/*=>{}.*/

    /*==>{ALLStaticNames.tpl, fieldMap/*, override eq false, JAVA|>}|*/
/*=>{}.*/

    /*==>{ALLInstanceVars.tpl, fieldMap/*, , JAVA|>}|*/
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
    /*==>{ALLSetter.tpl, fieldMap/*, , JAVA|>}|*/
/*=>{}.*/
}
