package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserSqareBracket;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/*=>{javaHeader}|*/

/**
 * Call for generation of java code from ModelConfig data. 
 *  * FieldConfig will be loaded from ConfigsCache. 
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Wed Nov 11 07:35:57 CET 2020
 */
public class GenerateModelTemplateMapCall extends GenerateModelAbstract {
/*=>{}.*/
    private final static String JAVA_GEN_MODEL = "/modelKey";
    public final static String FIELD_MAP = "fieldMap";
/*=>{javaStaticNames}|*/
/*=>{}.*/

/*=>{javaInstanceVars}|*/
/*=>{}.*/

    public GenerateModelTemplateMapCall() {
        super();
    }

    public boolean filter (final ModelBean modelBean) {
        if (modelBean.getNaturalId()==null) {
            throw new EoException("Natural id for '" + modelBean.getNaturalId() + "' is null!");
        }
        if (hasNaturalId() && !modelBean.getNaturalId().equals(getNaturalId())) {
            return false;
        }
        if (!modelBean.hasPackagePath()) {
            throw new EoException("PackagePath for '" + modelBean.getNaturalId() + "' is null!");
        }
        /*if (!modelBean.getPackagePath().startsWith(getPackagePath())) {
            return false;
        }*/
        return super.filter(modelBean);
    }

    private Map<String, String> initFileConfigKeyMap(final EO eo) {
        EOConfigsCache cache = eo.getConfigsCache();
        Set<String> fileConfigKeys = cache.getConfigKeys(FileConfig.class);
        Map<String, String> fileConfigKeyMap = new LinkedHashMap<>();
        for (String key: fileConfigKeys) {
            if (!key.contains(".tpl.")) {
                continue;
            }
            fileConfigKeyMap.put(key.replaceAll("\\.tpl\\.", "."), key);
        }
        return fileConfigKeyMap;
    }

    @Override
    public String execute(EO eo) {
        long start = System.currentTimeMillis();
        init(eo);
        Map<String, String> fileConfigKeyMap = initFileConfigKeyMap(eo);
        defaultValues();
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
            eo.set(modelBean.getModelKey(), "/modelKey");
            for (String fileConfigKey: fileConfigKeyMap.keySet()) {
                if (hasTargetFileConfigKey() && !fileConfigKey.matches(getTargetFileConfigKey())) {
                    continue;
                }
                feedback.append(
                        super.create(eoModel, fileConfigKeyMap.get(fileConfigKey), fileConfigKey));
            }
            counter++;
        }
        if (counter==1) {
            throw new EoException("No entry in ModelConfig found matching '" + getModule() + "', '" + getModuleScope() + "' and '" + getNaturalId() + "'.");
        }
        setDuration(System.currentTimeMillis() - start);
        return feedback.toString();
    }

/*=>{javaAccessors}|*/
    /*=>{}.*/


}
