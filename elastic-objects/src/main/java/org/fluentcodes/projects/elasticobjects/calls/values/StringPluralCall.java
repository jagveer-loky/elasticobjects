package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/

/**
 * For setting lower value to EO.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Tue Nov 10 14:30:42 CET 2020
 */
public class StringPluralCall extends CallImpl implements SimpleCommand {
/*=>{}.*/

    /*==>{ALLStaticNames.tpl, fieldBeans/*, super eq false, JAVA|>}|*/
/*=>{}.*/

    /*==>{ALLInstanceVars.tpl, fieldBeans/*, super eq false, JAVA|>}|*/
/*=>{}.*/
    @Override
    public String execute(final EO eo) {
        super.check(eo);
        try {
            return plural((String) eo.get());
        }
        catch (Exception e) {
            throw new EoException(e.getMessage());
        }
    }

    public static String plural(String item) {
        if (item == null) {
            throw new EoException("String is null");
        }
        if (item.isEmpty()) {
            return "";
        }
        if (item.endsWith("y")) {
            return item.replaceAll("y$", "ies");
        }
        else if (item.endsWith("s")) {
            return item.replaceAll("s$", "ses");
        }
        return item + "s";
    }

    /*==>{ALLSetter.tpl, fieldBeans/*, super eq false, JAVA|>}|*/
/*=>{}.*/
}
