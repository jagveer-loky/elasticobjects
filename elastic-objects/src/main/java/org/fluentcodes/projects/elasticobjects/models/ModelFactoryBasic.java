package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.LogLevel;
import org.fluentcodes.projects.elasticobjects.UnmodifiableCollection;
import org.fluentcodes.projects.elasticobjects.UnmodifiableList;
import org.fluentcodes.projects.elasticobjects.UnmodifiableMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Werner on 21.1.2021.
 */

public class ModelFactoryBasic extends ModelFactory {
    /**
     * Default init map.
     * @return the expanded final configurations.
     */
    @Override
    public Map<String, ModelBean> createBeanMap(ConfigMaps configMaps) {
        Map<String, ModelBean> modelMap = new TreeMap<>();
        return addModelBeans(modelMap);
    }

    public Map<String, ModelBean> createBeanMap() {
        Map<String, ModelBean> modelMap = new TreeMap<>();
        return addModelBeans(modelMap);
    }

    public Map<String, ModelBean> addModelBeans(Map<String, ModelBean> modelMap) {
        modelMap.put(LinkedHashMap.class.getSimpleName(), new ModelBean(LinkedHashMap.class, ShapeTypes.MAP));
        modelMap.put(Map.class.getSimpleName(), new ModelBean(Map.class, ShapeTypes.MAP));
        modelMap.put(UnmodifiableMap.class.getSimpleName(), new ModelBean(UnmodifiableMap.class, ShapeTypes.MAP));

        modelMap.put(UnmodifiableCollection.class.getSimpleName(), new ModelBean(UnmodifiableCollection.class, ShapeTypes.MAP));
        modelMap.put(UnmodifiableList.class.getSimpleName(), new ModelBean(UnmodifiableList.class, ShapeTypes.MAP));
        modelMap.put(List.class.getSimpleName(), new ModelBean(List.class, ShapeTypes.MAP));
        modelMap.put(ArrayList.class.getSimpleName(), new ModelBean(ArrayList.class, ShapeTypes.MAP));

        modelMap.put(Integer.class.getSimpleName(), new ModelBean(Integer.class, ShapeTypes.MAP));
        modelMap.put(Long.class.getSimpleName(), new ModelBean(Long.class, ShapeTypes.MAP));
        modelMap.put(String.class.getSimpleName(), new ModelBean(String.class, ShapeTypes.MAP));
        modelMap.put(Float.class.getSimpleName(), new ModelBean(Float.class, ShapeTypes.MAP));
        modelMap.put(Double.class.getSimpleName(), new ModelBean(Double.class, ShapeTypes.MAP));
        modelMap.put(Boolean.class.getSimpleName(), new ModelBean(Boolean.class, ShapeTypes.MAP));
        modelMap.put(Date.class.getSimpleName(), new ModelBean(Date.class, ShapeTypes.MAP));
        modelMap.put(LogLevel.class.getSimpleName(), new ModelBean(LogLevel.class, ShapeTypes.MAP));
        modelMap.put(JSONSerializationType.class.getSimpleName(), new ModelBean(JSONSerializationType.class, ShapeTypes.MAP));
        return modelMap;
    }
}
