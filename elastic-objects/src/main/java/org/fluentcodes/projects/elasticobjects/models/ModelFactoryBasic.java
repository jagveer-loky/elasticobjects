package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.LogLevel;
import org.fluentcodes.projects.elasticobjects.utils.UnmodifiableCollection;
import org.fluentcodes.projects.elasticobjects.utils.UnmodifiableList;
import org.fluentcodes.projects.elasticobjects.utils.UnmodifiableMap;

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

    public ModelFactoryBasic(final ConfigMaps configMaps) {
        super(configMaps);
    }

    @Override
    public Map<String, ModelBean> createBeanMap() {
        Map<String, ModelBean> modelMap = new TreeMap<>();
        return addModelBeans(modelMap);
    }

    public Map<String, ModelBean> addModelBeans(Map<String, ModelBean> modelMap) {
        modelMap.put(LinkedHashMap.class.getSimpleName(), new ModelBean(LinkedHashMap.class, ShapeTypes.MAP)
                .setCreate(true)
        );
        modelMap.put(Map.class.getSimpleName(), new ModelBean(Map.class, ShapeTypes.MAP)
                .setCreate(true)
        );
        modelMap.put(UnmodifiableMap.class.getSimpleName(), new ModelBean(UnmodifiableMap.class, ShapeTypes.MAP));

        modelMap.put(UnmodifiableCollection.class.getSimpleName(), new ModelBean(UnmodifiableCollection.class, ShapeTypes.LIST));
        modelMap.put(UnmodifiableList.class.getSimpleName(), new ModelBean(UnmodifiableList.class, ShapeTypes.LIST));
        modelMap.put(List.class.getSimpleName(), new ModelBean(List.class, ShapeTypes.LIST)
                .setCreate(true)
        );
        modelMap.put(ArrayList.class.getSimpleName(), new ModelBean(ArrayList.class, ShapeTypes.LIST)
                .setCreate(true)
        );

        modelMap.put(Integer.class.getSimpleName(), new ModelBean(Integer.class, ShapeTypes.NUMBER));
        modelMap.put(Long.class.getSimpleName(), new ModelBean(Long.class, ShapeTypes.NUMBER));
        modelMap.put(String.class.getSimpleName(), new ModelBean(String.class, ShapeTypes.STRING));
        modelMap.put(Float.class.getSimpleName(), new ModelBean(Float.class, ShapeTypes.NUMBER));
        modelMap.put(Double.class.getSimpleName(), new ModelBean(Double.class, ShapeTypes.NUMBER));
        modelMap.put(Boolean.class.getSimpleName(), new ModelBean(Boolean.class, ShapeTypes.BOOLEAN));
        modelMap.put(Date.class.getSimpleName(), new ModelBean(Date.class, ShapeTypes.DATE));
        modelMap.put(LogLevel.class.getSimpleName(), new ModelBean(LogLevel.class, ShapeTypes.ENUM));
        modelMap.put(JSONSerializationType.class.getSimpleName(), new ModelBean(JSONSerializationType.class, ShapeTypes.ENUM));
        return modelMap;
    }
}
