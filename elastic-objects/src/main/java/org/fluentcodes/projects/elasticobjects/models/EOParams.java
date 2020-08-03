package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.paths.PathPattern;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

/**
 * Created by werner.diwischek on 19.02.18.
 */
public class EOParams {
    private Boolean create;
    private ShapeTypes shapeType;
    private List<Scope> scope;
    private List<String> methods;
    private List<String> attributeList;
    private String modelConfigKey;
    private String defaultImplementation;
    private PathPattern pathPattern;

    public EOParams(Object object) {
        if (object == null) {
            shapeType = ShapeTypes.NONE;
            return;
        }
        if (!(object instanceof Map)) {
            shapeType = ShapeTypes.NONE;
            return;
        }
        Map map = (Map) object;

        this.modelConfigKey = ScalarConverter.toString(map.get(F_MODEL_CONFIG_KEY));
        this.defaultImplementation = ScalarConverter.toString(map.get(F_DEFAULT_IMPLEMENTATION));
        this.create = ScalarConverter.toBoolean(map.get(F_CREATE));

        String shapeTypeAsString = ScalarConverter.toString(map.get(F_SHAPE_TYPE));
        if (shapeTypeAsString == null || shapeTypeAsString.isEmpty()) {
            this.shapeType = ShapeTypes.OBJECT;
        } else {
            this.shapeType = ShapeTypes.valueOf(shapeTypeAsString);

        }
        String pathPatternAsString = ScalarConverter.toString(map.get(F_PATH_PATTERN));
        if (pathPatternAsString == null || pathPatternAsString.isEmpty()) {
            this.pathPattern = new PathPattern();
        } else {
            this.pathPattern = new PathPattern(pathPatternAsString);
        }

        setScope(map.get(F_SCOPE));

        String methods = ScalarConverter.toString(map.get(F_METHODS));
        if (methods == null || methods.isEmpty()) {
            this.methods = new ArrayList<>();
        } else {
            this.methods = Arrays.asList(methods.split(","));
        }

        String attributeList = ScalarConverter.toString(map.get(F_ATTRIBUTE_LIST));
        if (attributeList == null || attributeList.isEmpty()) {
            this.attributeList = new ArrayList<>();
        } else {
            this.attributeList = Arrays.asList(attributeList.split(","));
        }
    }

    public Boolean getCreate() {
        return create;
    }

    public Boolean isCreate() {
        return create;
    }

    public ShapeTypes getShapeType() {
        return shapeType;
    }

    public List<Scope> getScope() {
        return scope;
    }

    private void setScope(Object scopeObject) {
        this.scope = new ArrayList<>();
        if (scopeObject == null) {
            scope.add(Scope.ALL);
        } else if (scopeObject instanceof String) {
            String scopeAsString = (String) scopeObject;
            if (scopeAsString.isEmpty()) {
                scope.add(Scope.ALL);
            } else {
                this.scope = new ArrayList<>();
                String[] scopeArray = scopeAsString.split(",");
                for (String scope : scopeArray) {
                    try {
                        this.scope.add(Scope.valueOf(scope));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (scopeObject instanceof List) {
            List scopeList = (List) scopeObject;
            for (Object scope : scopeList) {
                try {
                    this.scope.add(Scope.valueOf((String) scope));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<String> getMethods() {
        return methods;
    }

    public List<String> getAttributeList() {
        return attributeList;
    }

    public String getModelConfigKey() {
        return modelConfigKey;
    }

    public String getDefaultImplementation() {
        return defaultImplementation;
    }

    public PathPattern getPathPattern() {
        return pathPattern;
    }

    public String getPathPatternAsString() {
        return pathPattern.getSerialized();
    }


}
