package org.fluentcodes.projects.elasticobjects.eo;

import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.config.FieldConfig;
import org.fluentcodes.projects.elasticobjects.config.ModelInterface;
import org.fluentcodes.projects.elasticobjects.executor.ExecutorList;
import org.fluentcodes.projects.elasticobjects.paths.PathPattern;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by werner.diwischek on 13.01.18.
 */
public class EOToJSON {
    public static final String REPEATED = ".repeated";
    private int startIndent = 0;
    private PathPattern pathPattern;
    private JSONSerializationType serializationType;
    private boolean checkObjectReplication = false;
    private List<EO> objectRegistry;
    private String spacer = "\t";


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

    public String toJSON(final EOConfigsCache finder, final Object object) throws Exception {
        return this.toJSON(
                new EOBuilder(finder)
                        .setLogLevel(LogLevel.WARN)
                        .set(object)
        );
    }

    public String toJSON(final EO adapter) throws Exception {
        this.setCheckObjectReplication(adapter.isCheckObjectReplication());
        if (this.pathPattern == null) {
            this.pathPattern = new PathPattern("+");
        }
        if (this.serializationType == null) {
            serializationType = JSONSerializationType.STANDARD;
        }
        StringBuilder json = new StringBuilder();
        if (serializationType != JSONSerializationType.EO) {
            toJSON(json, adapter, startIndent, this.pathPattern);
            return json.toString();
        } else {
            return toJSONRoot(adapter.getRoot());
        }
    }

    private String toJSONRoot(final EO adapter) throws Exception {
        this.setCheckObjectReplication(adapter.isCheckObjectReplication());
        StringBuilder jsn = new StringBuilder();
        final String indent = getIndent(startIndent);
        final String lineBreak = getLineBreak(startIndent);
        jsn.append("{");
        jsn.append(lineBreak);
        jsn.append(indent);
        jsn.append("\"");
        addEOModel(jsn, adapter, false);
        jsn.append(JSONToEO.DATA);
        jsn.append("\":");
        jsn.append(lineBreak);
        toJSON(jsn, adapter, startIndent + 1, this.pathPattern);

        ExecutorList executorList = adapter.getCalls();
        if (executorList.size() > 0) {
            EO actionsAdapter = new EOBuilder(adapter.getConfigsCache()).set(executorList.getListMap());
            jsn.append(",");
            jsn.append(lineBreak);
            jsn.append(indent);
            jsn.append("\"(List)");
            jsn.append(JSONToEO.CALLS);
            jsn.append("\":");
            jsn.append(lineBreak);
            toJSON(jsn, actionsAdapter, startIndent, this.pathPattern);
        }

        if (!adapter.getLog().isEmpty()) {
            jsn.append(",");
            jsn.append(lineBreak);
            jsn.append(indent);
            jsn.append("\"");
            jsn.append(JSONToEO.LOGS);
            jsn.append("\":");
            this.addScalarSimple(jsn, String.class, adapter.getLog());
        }
        jsn.append(lineBreak);
        jsn.append("}");
        return jsn.toString();
    }


    private String toJSON(final StringBuilder json, final EO adapter, final int indentLevel, PathPattern pathPattern) throws Exception {
        if (adapter.get() == null) {
            return "";
        }
        final String indent = getIndent(indentLevel);
        final String lineBreak = getLineBreak(indentLevel);
        EO repeated = checkObjectReplication(adapter);
        if (repeated != null) {
            this.addRepeated(json, repeated, lineBreak, indent);
            return json.toString();
        }

        ModelInterface model = adapter.getModel();
        if (model.isObject()) {
            if (!model.getPathPattern().isEmpty()) {
                if (pathPattern.isFilterNothing()) {
                    pathPattern = model.getPathPattern();
                }
            }
        }

        this.addStart(json, adapter, indent);

        List<String> fieldNames;
        try {
            fieldNames = adapter.keys(pathPattern);
        } catch (Exception e) {
            adapter.debug("Empty keys for " + e.getMessage());
            return "";
        }

        if (fieldNames.isEmpty()) {
            addEnd(json, adapter, lineBreak, indent);
            return json.toString();
        }
        // main loop
        boolean firstEntry = true;
        final String nextIndent = getNextIndent(indentLevel);
        final int nextIndentLevel = getNextIndentLevel(indentLevel);
        final boolean isTyped = adapter.isChildTyped();
        for (int i = 0; i < fieldNames.size(); i++) {
            String fieldName = fieldNames.get(i);
            EO childAdapter = adapter.getChild(fieldName);
            if (childAdapter == null) {
                continue;
            }
            if (childAdapter.get() == null) {
                continue;
            }
            if (childAdapter.getModel().isToSerialize() && childAdapter.isEmpty()) {
                continue;
            }
            if (!firstEntry) {
                json.append(",");
            } else {
                firstEntry = false;
            }
            json.append(lineBreak);
            // named serialization ....
            if (adapter.getModel().isMapType() || serializationType == JSONSerializationType.EO) {
                json.append(nextIndent);
                json.append("\"");
                if (serializationType == JSONSerializationType.EO) {
                    addEOModel(json, childAdapter, isTyped);
                }
                json.append(fieldName);
                json.append("\":");
            } else {
                json.append(nextIndent);
            }
            if (childAdapter.isScalar() || childAdapter.getModel().isToSerialize()) {
                addScalar(json, childAdapter, isTyped);
                continue;
            }
            json.append(lineBreak);
            PathPattern fieldPathPattern = getChildPathPattern(fieldName, model, pathPattern);
            toJSON(json, childAdapter, nextIndentLevel, fieldPathPattern);
        }
        addEnd(json, adapter, lineBreak, indent);
        return json.toString();
    }

    private final void addEOModel(final StringBuilder jsn, final EO adapter, boolean isParentTyped) {
        if (isParentTyped && adapter.getModelClass() != Object.class) {
            return;
        }
        if (adapter.hasDefaultMap()) {
            return;
        }
        if (adapter.getModelClass() == String.class) {
            return;
        }
        if (adapter.getModelClass() == Long.class) {
            return;
        }
        jsn.append("(");
        jsn.append(adapter.getModels().toString());
        jsn.append(")");
    }

    private final void addStart(final StringBuilder buffer, final EO adapter, final String indent) {
        buffer.append(indent);
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
        buffer.append("\":\"");
        buffer.append(repeated.getPath());
        buffer.append("\"}");
        buffer.append(lineBreak);
    }

    private PathPattern getChildPathPattern(String key, ModelInterface model, PathPattern pathPattern) throws Exception {
        PathPattern fieldPathPattern;
        if (pathPattern != null && pathPattern.filterSomething()) {
            fieldPathPattern = pathPattern.getPathList(key);
        } else {
            FieldConfig fieldConfig = model.getField(key);
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

    private void addScalar(final StringBuilder buffer, final EO adapter, final boolean isTyped) throws Exception {
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
