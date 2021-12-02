package org.fluentcodes.projects.elasticobjects.calls.values;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

/*.{javaHeader}|*/

/**
 * For setting sinus value to EO.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Tue Dec 08 15:52:04 CET 2020
 */
public class SinusValueCall extends CallImpl implements SimpleCommand {
/*.{}.*/

/*.{javaStaticNames}|*/
/*.{}.*/

/*.{javaInstanceVars}|*/
/*.{}.*/
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
/*.{javaAccessors}|*/
/*.{}.*/
}
