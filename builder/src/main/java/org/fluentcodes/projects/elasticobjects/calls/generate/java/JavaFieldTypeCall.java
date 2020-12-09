package org.fluentcodes.projects.elasticobjects.calls.generate.java;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/
/**
 * Returns a type String depending on the modelKeys field value, e.g. "<Map, AnObject>" if modeKey is "Map, AnObject".
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Wed Nov 11 07:50:06 CET 2020
 */
public class JavaFieldTypeCall extends CallImpl implements SimpleCommand {
/*=>{}.*/

/*==>{ALLStaticNames.tpl, fieldBeans/*, override eq false, JAVA|>}|*/
/*=>{}.*/

/*==>{ALLInstanceVars.tpl, fieldBeans/*, , JAVA|>}|*/
/*=>{}.*/
    @Override
    public String execute(final EO eo) {

        return TYPE((String)eo.get());
    }
    public static final String TYPE(final String modelKeys)  {
        try {
            String[] models = modelKeys.split(",");
            if (models.length == 2) {
                if (models[0].endsWith("Map")) {
                    return "Map<String, " + models[1] + ">";
                }
                else if (models[0].endsWith("List")) {
                    return "List<" + models[1] + ">";
                }
                else {
                    return models[0];
                }
            }
            else {
                return models[0];
            }
        }
        catch (Exception e) {
            throw new EoException(e.getMessage());
        }
    }
/*==>{ALLSetter.tpl, fieldBeans/*, , JAVA|>}|*/
/*=>{}.*/
}
