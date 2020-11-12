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
 * @modificationDate Tue Nov 10 14:37:19 CET 2020
 */
public class StringUpperCall extends CallImpl implements SimpleCommand {
/*=>{}.*/

    /*==>{ALLStaticNames.tpl, fieldMap/*, override eq false, JAVA|>}|*/
/*=>{}.*/

    /*==>{ALLInstanceVars.tpl, fieldMap/*, , JAVA|>}|*/
/*=>{}.*/
    @Override
    public String execute(final EO eo) {
        super.check(eo);
        try {
            return upper((String)eo.get());
        }
        catch (Exception e) {
            throw new EoException(e.getMessage());
        }
    }

    public static String upper(String item) {
        if (item == null || item.isEmpty()) {
            throw new EoException("Null or empty value");
        }

        char[] chars = item.toCharArray();
        StringBuilder builder = new StringBuilder();
        char previous = 0;
        for (char entry : chars) {
            if ((entry > 96 && entry < 123) || entry == 228 || entry == 246 || entry == 252) {
                int upper = (int) entry - 32;
                builder.append((char) upper);
            } else if ((entry > 47 && entry < 58) || (entry > 64 && entry < 91) || entry == 196 || entry == 214 || entry == 220) {
                if ((previous > 47 && previous < 58) || (previous > 96 && previous < 123) || previous == 228 || previous == 246 || previous == 252) {
                    builder.append("_");
                }
                builder.append(entry);
            } else if (entry < 65 && !(entry > 47 && entry < 58)) {
                builder.append("_");
            } else if (entry > 90 && entry < 97) {
                builder.append("_");
            }
            previous = entry;
        }
        return builder.toString();
    }
    /*==>{ALLSetter.tpl, fieldMap/*, , JAVA|>}|*/
/*=>{}.*/

}
