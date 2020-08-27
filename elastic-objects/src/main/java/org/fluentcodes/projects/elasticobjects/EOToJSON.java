package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelInterface;
import org.fluentcodes.projects.elasticobjects.models.Models;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by werner.diwischek on 13.01.18.
 */
public class EOToJSON {
    public static final String REPEATED = ".repeated";
    private int startIndent = 1;
    private PathPattern pathPattern;
    private JSONSerializationType serializationType;
    private boolean checkObjectReplication = false;
    private List<EO> objectRegistry;
    private String spacer = "  ";

    public EOToJSON() {

    }

    public boolean isCheckObjectReplication() {
        return checkObjectReplication;
    }

    public EOToJSON setCheckObjectReplication(boolean checkObjectReplication) {
        if (this.checkObjectReplication == true) {
            return this;
        }
        this.checkObjectReplication = checkObjectReplication;
        return this;
    }

    public int getStartIndent() {
        return startIndent;
    }

    public EOToJSON setStartIndent(int indent) {
        this.startIndent = indent;
        return this;
    }

    public PathPattern getPathPattern() {
        return pathPattern;
    }

    public EOToJSON setPathPattern(PathPattern pathPattern) {
        this.pathPattern = pathPattern;
        return this;
    }

    public JSONSerializationType getSerializationType() {
        return serializationType;
    }

    public EOToJSON setSerializationType(JSONSerializationType serializationType) {
        this.serializationType = serializationType;
        return this;
    }

    public String toJSON(final EOConfigsCache cache, final Object object)  {
        return toJSON(new EoRoot(cache, object));
    }

    public String toJSON(final EO eo)  {
        if (serializationType ==null) {
            this.setSerializationType(eo.getSerializationType());
        }
        this.setCheckObjectReplication(eo.isCheckObjectReplication());
        if (this.pathPattern == null) {
            this.pathPattern = new PathPattern("+");
        }
        StringBuilder json = new StringBuilder();
        toJSON(json, eo, startIndent, this.pathPattern);
        return json.toString();
    }

    private String toJSON(final StringBuilder json, final EO eoParent, final int indentLevel, PathPattern pathPattern)  {
        if (eoParent.get() == null) {
            return "";
        }
        final String indent = getIndent(indentLevel);
        final String lineBreak = getLineBreak(indentLevel);
        /*EO repeated = checkObjectReplication(eoParent);
        if (repeated != null) {
            this.addRepeated(json, repeated, lineBreak, indent);
            return json.toString();
        }*/

        ModelInterface model = eoParent.getModel();
        if (model.isObject()) {
            if (!model.getPathPattern().isEmpty()) {
                if (pathPattern.isFilterNothing()) {
                    pathPattern = model.getPathPattern();
                }
            }
        }

        this.addStart(json, eoParent, indent);

        List<String> fieldNames;
        try {
            fieldNames = eoParent.keys(pathPattern);
        } catch (Exception e) {
            eoParent.debug("Empty keys for " + e.getMessage());
            return "";
        }

        if (fieldNames.isEmpty()) {
            addEnd(json, eoParent, lineBreak, indent);
            return json.toString();
        }
        // main loop
        final String nextIndent = getNextIndent(indentLevel);
        final int nextIndentLevel = getNextIndentLevel(indentLevel);
        final boolean isTyped = eoParent.isChildTyped();
        boolean first = true;
        if (eoParent.isRoot() && serializationType == JSONSerializationType.EO) {
            json.append(lineBreak);
            json.append(nextIndent);
            json.append("\"");
            json.append(PathElement.ROOT_MODEL);
            json.append("\": \"");
            json.append(eoParent.getModels().toString());
            json.append("\"");
            first = false;
        }
        for (int i = 0; i < fieldNames.size(); i++) {
            String fieldName = fieldNames.get(i);
            if (fieldName.equals(PathElement.ROOT_MODEL)) {
                continue;
            }
            if (PathElement.isParentNotSet(fieldName) && serializationType == JSONSerializationType.STANDARD) {
                continue;
            }
            EO eoChild  = eoParent.getEo(fieldName);
            if (eoChild.get() == null) {
                continue;
            }
            if (eoChild.isEoEmpty()) {
                if (eoChild.getModel().isToSerialize()) {
                    continue;
                }
                else if (eoChild.getModel().isList()) {
                    continue;
                }
                else if (eoChild.getModel().isMap()) {
                    continue;
                }
                else if (eoChild.getModel().isObject()) {
                    continue;
                }
            }

            if (!first) {
                json.append(",");
            }
            first = false;
            json.append(lineBreak);
            // named serialization ....
            if (eoParent.getModel().isMapType() || serializationType == JSONSerializationType.EO) {
                json.append(nextIndent);
                json.append("\"");
                if (serializationType == JSONSerializationType.EO) {
                    addEOModel(json, eoChild, isTyped);
                }
                json.append(fieldName);
                json.append("\": ");
            } else {
                json.append(nextIndent);
            }
            if (eoChild.isScalar() || eoChild.getModel().isToSerialize()) {
                addScalar(json, eoChild, isTyped);
                continue;
            }
            //json.append(lineBreak);
            PathPattern fieldPathPattern = getChildPathPattern(fieldName, model, pathPattern);
            toJSON(json, eoChild, nextIndentLevel, fieldPathPattern);
        }
        addEnd(json, eoParent, lineBreak, indent);
        return json.toString();
    }

    private final void addEOModel(final StringBuilder jsn, final EO eoChild, boolean isParentTyped) {
        //if (isParentTyped && eoChild.getModelClass() != Object.class) {
            //return;
        //}
        if (eoChild.hasDefaultMap()) {
            return;
        }
        if (eoChild.getModelClass() == String.class) {
            return;
        }
        if (eoChild.getModelClass() == Integer.class) {
            return;
        }
        if (eoChild.getModelClass() == Boolean.class) {
            return;
        }
        jsn.append("(");
        jsn.append(eoChild.getModels().toString());
        jsn.append(")");
    }

    private final void addStart(final StringBuilder buffer, final EO adapter, final String indent) {
        if (adapter.getModel().isMapType() || serializationType == JSONSerializationType.EO) {
            buffer.append("{");
        } else if (adapter.getModel().isListType()) {
            buffer.append("[");
        }
    }

    private void addEnd(final StringBuilder buffer, final EO adapter, final String lineBreak, final String indent) {
        buffer.append(lineBreak);
        buffer.append(indent);
        if (adapter.getModel().isMapType() || serializationType == JSONSerializationType.EO) {
            buffer.append("}");
        } else if (adapter.getModel().isListType()) {
            buffer.append("]");
        }
    }

    private void addRepeated(final StringBuilder buffer, final EO repeated, final String lineBreak, final String indent) {
        buffer.append(indent);
        buffer.append("{\"");
        buffer.append(REPEATED);
        buffer.append("\": \"");
        buffer.append(repeated.getPath());
        buffer.append("\"}");
        buffer.append(lineBreak);
    }

    private PathPattern getChildPathPattern(String key, ModelInterface model, PathPattern pathPattern)  {
        PathPattern fieldPathPattern;
        if (pathPattern != null && pathPattern.filterSomething()) {
            fieldPathPattern = pathPattern.getPathList(key);
        } else {
            FieldConfig fieldConfig = model.getFieldConfig(key);
            if (fieldConfig != null && fieldConfig.hasPathPattern()) {
                fieldPathPattern = fieldConfig.getPathPattern();
            } else {
                fieldPathPattern = new PathPattern("+");
            }
        }
        return fieldPathPattern;
    }

    private String stringify(Object object) {
        if (object == null) {
            return "";
        }
        String value = ScalarConverter.toString(object);
        value = value.replaceAll("\n", "\\\\n");
        value = value.replaceAll("\"", "\\\\\"");
        if (object instanceof Number) {
            return value.replaceAll("[\\.,]+0+$", "");
        }
        return value;
    }

    private void addScalarSimple(final StringBuilder buffer, final Class modelClass, final String value) {
        if (modelClass == String.class || modelClass.isEnum()) {
            buffer.append("\"");
            buffer.append(value.replaceAll("\"", "\\\""));
            buffer.append("\"");
        } else {
            buffer.append(value);
        }
    }

    private void addScalarTyped(final StringBuilder buffer, final Models models, final String value) {
        buffer.append("\"");
        if (models.getModelClass() != String.class) {
            buffer.append("(");
            buffer.append(models.toString());
            buffer.append(")");
        }
        buffer.append(value);
        buffer.append("\"");
    }

    private void addScalar(final StringBuilder buffer, final EO adapter, final boolean isTyped)  {
        String value = stringify(adapter.get());
        if (serializationType != JSONSerializationType.SCALAR) {
            addScalarSimple(buffer, adapter.getModelClass(), value);
        } else {
            addScalarTyped(buffer, adapter.getModels(), value);
        }
    }

    private String getLineBreak(int indent) {
        if (indent == 0) {
            return "";
        }
        return "\n";
    }

    private int getNextIndentLevel(int indent) {
        if (indent == 0) {
            return 0;
        }
        return indent + 1;
    }

    private String getIndent(int indent) {
        if (indent == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < indent; i++) {
            builder.append(this.spacer);
        }
        return builder.toString();
    }

    private String getNextIndent(int indent) {
        if (indent == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            builder.append(this.spacer);
        }
        return builder.toString();
    }

    private final EO checkObjectReplication(final EO adapter) {
        if (!this.checkObjectReplication) {
            return null;
        }
        if (objectRegistry == null) {
            objectRegistry = new ArrayList();
        }
        if (adapter.get() == null) {
            return null;
        }
        Object object = adapter.get();
        for (EO registered : objectRegistry) {
            if (registered.get() == object) {
                return registered;
            }
        }
        objectRegistry.add(adapter);
        return null;
    }

}
