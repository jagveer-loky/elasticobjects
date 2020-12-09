package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserSqareBracket;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;

/*==>{ALLHeader.tpl, ., , JAVA}|*/
/**
 * Call for generation of java code from ModelConfig data. 
 *  * FieldConfig will be loaded from ConfigsCache. 
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Wed Nov 11 07:35:57 CET 2020
 */
public class GenerateModelTemplateCall extends GenerateModelAbstract {
/*=>{}.*/
    private final static String JAVA_GEN_MODEL = "/modelKey";
    public final static String FIELD_MAP = "fieldMap";

/*==>{ALLStaticNames.tpl, fieldBeans/*, override eq false, JAVA|>}|*/
/*=>{}.*/

/*==>{ALLInstanceVars.tpl, fieldBeans/*, , JAVA|>}|*/
/*=>{}.*/

    public GenerateModelTemplateCall() {
        super();
    }

    public boolean filter (final ModelBean modelBean) {
        if (modelBean.getNaturalId()==null) {
            throw new EoException("Natural id is null!");
        }
        if (hasNaturalId() && !modelBean.getNaturalId().equals(getNaturalId())) {
            return false;
        }
        return super.filter(modelBean);
    }

    @Override
    public Object execute(EO eo) {
        super.init(eo);
        StringBuilder feedback = new StringBuilder();
        this.setNaturalId(ParserSqareBracket.replacePathValues(getNaturalId(), eo));
        int counter = 1;
        for (final String naturalId: eo.keys()) {
            EO eoModel = eo.getEo(naturalId);
            if (!isModelBean(eoModel)) {
                continue;
            }
            ModelBean modelBean = getModelBean(eoModel);
            if (!filter(modelBean)) {
                continue;
            }
            final String modelKey = modelBean.getModelKey();
            eo.set(modelKey, JAVA_GEN_MODEL);
            create(eoModel, getSourceFileConfigKey(), getTargetFileConfigKey());
            counter++;
        }
        if (counter==1) {
            throw new EoException("No java entry in ModelConfig found matching '" + getModule() + "', '" + getModuleScope() + "' and '" + getNaturalId() + "'.");
        }
        return feedback.toString();
    }


/*==>{ALLSetter.tpl, fieldBeans/*, , JAVA|>}|*/
/*=>{}.*/


}
