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
 * @modificationDate Tue Nov 10 14:34:36 CET 2020
 */
public class StringReplaceWhiteSpaceCall extends CallImpl implements SimpleCommand {
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
            return ((String) eo.get()).replaceAll("[^a-zA-Z0-9]","_");
        }
        catch (Exception e) {
            throw new EoException(e.getMessage());
        }
    }

    /*==>{ALLSetter.tpl, fieldMap/*, , JAVA|>}|*/
/*=>{}.*/
}
