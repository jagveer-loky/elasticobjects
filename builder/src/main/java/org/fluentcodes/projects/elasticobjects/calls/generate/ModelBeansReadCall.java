package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.xlsx.XlsxReadCall;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelBeanGen;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.calls.generate.GenerateModelAbstract.MODEL_BEANS_JSON;

/*=>{javaHeader}|*/

/**
 * Call for generation json configurations from the sheet.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Wed Nov 11 07:39:50 CET 2020
 */
public class ModelBeansReadCall extends GenerateAbstract implements GenerateProperties{
    /*=>{javaStaticNames}|*/
/*=>{}.*/

/*=>{javaInstanceVars}|*/
/*=>{}.*/
    public ModelBeansReadCall() {
        super();
        setTargetFileConfigKey(MODEL_BEANS_JSON);
    }

    public ModelBeansReadCall(final String sourceFileConfigKey) {
        super();
        setSourceFileConfigKey(sourceFileConfigKey);
        setTargetFileConfigKey(MODEL_BEANS_JSON);
    }

    @Override
    public String execute(final EO eo) {
        long start = System.currentTimeMillis();
        StringBuilder feedback = new StringBuilder();
        feedback.append(read(getSourceFileConfigKey(), eo));
        return feedback.toString();
    }

    public static String read(final String sourceFileConfigKey, final EO eo) {
        StringBuilder feedback = new StringBuilder();
        final EO readEo = EoRoot.of(eo.getConfigsCache());
        XlsxReadCall modelCall = new XlsxReadCall(sourceFileConfigKey + ":" + ModelConfig.class.getSimpleName());
        modelCall.setTargetPath(ModelConfig.class.getSimpleName() + "/=>[naturalId].");
        feedback.append(modelCall.execute(readEo));

        XlsxReadCall fieldCall = new XlsxReadCall(sourceFileConfigKey + ":" + FieldConfig.class.getSimpleName());
        fieldCall.setTargetPath(FieldConfig.class.getSimpleName() + "/=>[naturalId].");
        feedback.append(fieldCall.execute(readEo));

        Map<String, ModelBean> modelBeans = new LinkedHashMap<>();
        Map<String, Map> models = (Map<String, Map>)readEo.get(ModelConfig.class.getSimpleName());
        for (String key: models.keySet()) {
            ModelBeanGen modelBean = new ModelBeanGen(models.get(key));
            modelBean.mergeFieldDefinition((Map<String, Map>)readEo.get(FieldConfig.class.getSimpleName()));
            modelBeans.put(key, modelBean);
        }
        for (ModelBean modelBean: modelBeans.values()) {
            modelBean.resolveSuper(modelBeans, false);
        }
        eo.mapObject(modelBeans);
        return feedback.toString();
    }

/*=>{javaAccessors}|*/
/*=>{}.*/

}
