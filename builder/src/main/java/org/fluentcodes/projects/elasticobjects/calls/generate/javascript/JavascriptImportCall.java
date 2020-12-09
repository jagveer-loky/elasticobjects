package org.fluentcodes.projects.elasticobjects.calls.generate.javascript;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.models.FieldBean;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/

/**
 * Returns a type String depending on the modelKeys field value, e.g. "<Map, AnObject>" if modeKey is "Map, AnObject".
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Wed Nov 11 07:50:06 CET 2020
 */
public class JavascriptImportCall extends CallImpl implements SimpleCommand {
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
        FieldBean fieldBean = (FieldBean) eo.get();
        if ("string".equals(fieldBean.getJavascriptType()) || "number".equals(fieldBean.getJavascriptType())) {
            return "";
        }
        if (!fieldBean.hasModelKeys()) {
            throw new EoException("No modelKeys for '" + fieldBean.getFieldKey() + "'.");
        }
        String[] modelKeys = fieldBean.getModelKeys().split(",");
        ModelBean modelBean = null;
        if (modelKeys.length == 2) {
            modelBean = (ModelBean) eo.get(Path.DELIMITER + modelKeys[1]);
        }
        else if (modelKeys.length == 1) {
            modelBean = (ModelBean) eo.get(Path.DELIMITER + modelKeys[0]);
        }
        return modelBean.getModelKey();
    }
/*==>{ALLSetter.tpl, fieldBeans/*, , JAVA|>}|*/
/*=>{}.*/
}
