package org.fluentcodes.projects.elasticobjects.calls.generate.java;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.models.FieldBeanInterface;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/

/**

 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Wed Nov 11 07:41:22 CET 2020
 */
public class JavaFieldAnnotationSizeCall extends CallImpl implements SimpleCommand {
/*=>{}.*/

/*==>{ALLStaticNames.tpl, fieldBeans/*, override eq false, JAVA|>}|*/
/*=>{}.*/

/*==>{ALLInstanceVars.tpl, fieldBeans/*, , JAVA|>}|*/
/*=>{}.*/

    @Override
    public String execute(final EO eo) {
        if (!(eo.get() instanceof FieldBeanInterface)) {
            throw new EoException("Input must be instance of '" + FieldBeanInterface.class.getSimpleName() + "' but is '" + eo.get().getClass().getSimpleName() + "'");
        }
        try {
            FieldBeanInterface properties = (FieldBeanInterface) eo.get();
            if (!properties.hasSize()) {
                return "";
            }
            StringBuilder builder = new StringBuilder("@Size(");
            if (properties.hasMin()) {
                builder.append("min=");
                builder.append(properties.getMin());
                builder.append(", ");
            }
            if (properties.hasMin()) {
                builder.append("max=");
                builder.append(properties.getMax());
                builder.append(", ");
            }
            return builder.toString().replaceAll(" ,", ")\n");
        }
        catch (Exception e) {
            throw new EoException(e.getMessage());
        }
    }
/*==>{ALLSetter.tpl, fieldBeans/*, , JAVA|>}|*/
/*=>{}.*/
}
