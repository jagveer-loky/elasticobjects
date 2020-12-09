package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/

/**
 * For setting upper value to EO.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Tue Dec 08 12:03:32 CET 2020
 */
public class StringUpperFirstCharCall extends CallImpl implements SimpleCommand {
/*=>{}.*/

    /*==>{ALLStaticNames.tpl, fieldBeans/*, super eq false, JAVA|>}|*/
/*=>{}.*/

    /*==>{ALLInstanceVars.tpl, fieldBeans/*, super eq false, JAVA|>}|*/
/*=>{}.*/

    @Override
    public String execute(final EO eo) {
        super.check(eo);
        try {
            return upper((String) eo.get());
        }
        catch (Exception e) {
            throw new EoException(e.getMessage());
        }
    }

    /**
     * Returns the getSerialized with an upper first character
     *
     * @param item A getSerialized item
     * @return Capitalized item
     */
    public static String upper(String item) {
        if (item == null) {
            throw new EoException("String is null");
        }
        if (item.isEmpty()) {
            return "";
        }
        return item.substring(0, 1).toUpperCase() + item.substring(1);
    }

    /*==>{ALLSetter.tpl, fieldBeans/*, super eq false, JAVA|>}|*/
/*=>{}.*/
}
