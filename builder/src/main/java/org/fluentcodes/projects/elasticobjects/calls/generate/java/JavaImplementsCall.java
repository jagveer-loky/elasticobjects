package org.fluentcodes.projects.elasticobjects.calls.generate.java;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.Arrays;
import java.util.List;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/
/**
 * Returns the "implements " statement from the interfaces field value.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Wed Nov 11 07:51:41 CET 2020
 */
public class JavaImplementsCall extends CallImpl implements SimpleCommand {
/*=>{}.*/

    /*==>{ALLStaticNames.tpl, fieldMap/*, override eq false, JAVA|>}|*/
/*=>{}.*/

    /*==>{ALLInstanceVars.tpl, fieldMap/*, , JAVA|>}|*/
/*=>{}.*/
    @Override
    public String execute(final EO eo) {
        if (eo.get()==null) {
            return "";
        }
        try {
            List<String> interfaces = (eo.get() instanceof String) ? Arrays.asList(((String)eo.get()).split(",")) : (List<String>) eo.get();
            StringBuilder interfacePart = new StringBuilder("implements");
            for (String interfaceKey : interfaces) {
                interfacePart.append(" " + interfaceKey + ", ");
            }
            return interfacePart.toString().replaceAll(", $", "");
        }
        catch (Exception e) {
            throw new EoException(e.getMessage());
        }
    }
    /*==>{ALLSetter.tpl, fieldMap/*, , JAVA|>}|*/
/*=>{}.*/
}
