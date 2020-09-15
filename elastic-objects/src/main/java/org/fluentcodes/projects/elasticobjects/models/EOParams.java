package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.util.Map;

/**
 * Created by werner.diwischek on 19.02.18.
 */
public class EOParams {
    public static final String ATTRIBUTE_LIST = "attributeList";
    public static final String DEFAULT_IMPLEMENTATION = "defaultImplementation";
    public static final String MODEL_CONFIG_KEY = "modelConfigKey";
    public static final String SHAPE_TYPE = "shapeType";
    public static final String CREATE = "create";
    private final Boolean create;
    private final ShapeTypes shapeType;
    private final String defaultImplementation;

    public EOParams(Map map) {
        if (map == null || map.isEmpty()) {
            shapeType = ShapeTypes.NONE;
            create = false;
            defaultImplementation = null;
            return;
        }
        this.defaultImplementation = (String) map.get(DEFAULT_IMPLEMENTATION);
        this.create = map.containsKey(CREATE) && map.get(CREATE)!=null ? (Boolean) map.get(CREATE): true;
        try {
            String shapeTypeAsString = (String) map.get(SHAPE_TYPE);
            if (shapeTypeAsString == null || shapeTypeAsString.isEmpty()) {
                this.shapeType = ShapeTypes.BEAN;
            } else {
                this.shapeType = ShapeTypes.valueOf(shapeTypeAsString);
            }
        }
        catch (Exception e) {
            throw new EoInternalException("Problem setting shapeType " + map.get(SHAPE_TYPE));
        }
    }

    public Boolean getCreate() {
        return create;
    }

    public Boolean isCreate() {
        if (create == null) {
            return false;
        }
        return create;
    }

    public ShapeTypes getShapeType() {
        return shapeType;
    }

    public String getDefaultImplementation() {
        return defaultImplementation;
    }

}
