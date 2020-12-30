package org.fluentcodes.projects.elasticobjects.calls.generate.javascript;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.models.FieldBean;
import org.fluentcodes.projects.elasticobjects.calls.values.StringLowerCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/

/**
 * Returns a type String depending on the modelKeys field value, e.g. "<Map, AnObject>" if modeKey is "Map, AnObject".
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Wed Nov 11 07:50:06 CET 2020
 */
public class JavascriptFieldCall extends CallImpl implements SimpleCommand {
/*=>{}.*/

/*==>{ALLStaticNames.tpl, fieldBeans/*, override eq false, JAVA|>}|*/
/*=>{}.*/

/*==>{ALLInstanceVars.tpl, fieldBeans/*, , JAVA|>}|*/
/*=>{}.*/
    @Override
    public String execute(final EO eo) {
        if (eo.getModelClass() != FieldBean.class) {
            throw new EoException("No FieldBean class in '" + this.getClass().getSimpleName() + "' but '" + eo.getModelClass().getSimpleName() + "'" );
        }
        return createField((FieldBean) eo.get());
    }

    public static String createField(final FieldBean fieldBean) {
        if (!fieldBean.hasFieldKey()) {
            throw new EoException("FieldBean has no fieldKey in 'JavascriptFieldCall'" );
        }
        if (!fieldBean.hasModelKeys()) {
            throw new EoException("FieldBean has no modelKeys for '" + fieldBean.getFieldKey() + "'in 'JavascriptFieldCall'" );
        }
        StringBuilder builder = new StringBuilder("public ");
        builder. append(StringLowerCall.lower(fieldBean.getFieldKey()));
        builder.append("?: ");
        // TODO builder.append(JavascriptFieldTypeCall.createType(fieldBean.getModelKeys()));
        builder.append(", ");
        return builder.toString();
    }
/*==>{ALLSetter.tpl, fieldBeans/*, , JAVA|>}|*/
/*=>{}.*/
}
