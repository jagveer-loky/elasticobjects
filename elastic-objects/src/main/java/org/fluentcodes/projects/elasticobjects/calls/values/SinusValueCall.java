package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;


/*==>{ALLHeader.tpl, ., , JAVA|>}|*/
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
/**
 * For setting sinus value to EO.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Tue Nov 10 14:26:08 CET 2020
 */
public class SinusValueCall extends CallImpl implements SimpleCommand {
/*=>{}.*/

        /*==>{ALLStaticNames.tpl, fieldMap/*, override eq false, JAVA|>}|*/
/*=>{}.*/

        /*==>{ALLInstanceVars.tpl, fieldMap/*, , JAVA|>}|*/
/*=>{}.*/
    @Override
    public Object execute(final EO eo) {
        super.check(eo);
        Double value = ScalarConverter.toDouble(eo.get());
        try {
            return super.createReturnScalar(eo, Math.sin(value));
        }
        catch (Exception e) {
            throw new EoException(e.getMessage());
        }
    }
    /*==>{ALLSetter.tpl, fieldMap/*, , JAVA|>}|*/
/*=>{}.*/
}
