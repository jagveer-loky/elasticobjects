package org.fluentcodes.projects.elasticobjects.calls.generate.java;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
/**
 * If type of the field is map, string or array a check for is empty is returned. 
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Wed Nov 11 07:45:39 CET 2020
 */
public class JavaFieldNotEmptyCall extends CallImpl implements SimpleCommand {
/*=>{}.*/

/*==>{ALLStaticNames.tpl, fieldMap/*, override eq false, JAVA|>}|*/
/*=>{}.*/

/*==>{ALLInstanceVars.tpl, fieldMap/*, , JAVA|>}|*/
/*=>{}.*/
    @Override
    public String execute(final EO eo) {
        try {
            String[] models = ((String)eo.get()).split(",");
            if (models[0].matches(".*(Map|List|String)$")) {
                return " && !" + eo.get("../fieldKey") + ".isEmpty()" ;
            }
            else {
                return "";
            }
        }
        catch (Exception e) {
            throw new EoException(e.getMessage());
        }
    }
/*==>{ALLSetter.tpl, fieldMap/*, , JAVA|>}|*/
/*=>{}.*/
}
