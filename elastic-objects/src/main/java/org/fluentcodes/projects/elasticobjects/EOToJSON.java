package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.Models;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import static org.fluentcodes.projects.elasticobjects.PathElement.SERIALIZATION_TYPE;

/**
 * Created by werner.diwischek on 13.01.18.
 */
public class EOToJSON {
    public static final String REPEATED = ".repeated";
    public static final Pattern REMOVE_PATTERN = Pattern.compile("\r");
    public static final Pattern NEWLINE_PATTERN = Pattern.compile("\n");
    public static final Pattern ESCAPE_PATTERN = Pattern.compile("\"");
    public static final Pattern NUMBER_PATTERN = Pattern.compile("[\\.,]+0+$");
    private int indent = 1;
    private PathPattern pathPattern;
    private JSONSerializationType serializationType;
    private boolean checkObjectReplication = false;
    private List<EO> objectRegistry;
    private String spacer = "  ";

    public EOToJSON() {

    }
    public EOToJSON(JSONSerializationType serializationType) {
        this.serializationType = serializationType;
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

    public EOToJSON setIndent(int indent) {
        this.indent = indent;
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

    private boolean isStandard() {
        return this.serializationType == JSONSerializationType.STANDARD;
    }

    public String toJson(final ConfigMaps cache, final Object object)  {
        if (isStandard()) {
            EO mapEo = EoRoot.ofClass(cache, Map.class);
            mapEo.setSerializationType(JSONSerializationType.STANDARD);
            return toJson(mapEo.mapObject(object));
        }
        return toJson(EoRoot.ofValue(cache, object));
    }

    public String toJson(final EO eo)  {
        if (eo.isScalar()) {
            return eo.get().toString();
        }
        if (serializationType == null) {
            this.setSerializationType(eo.getSerializationType());
        }
        StringWriter stringWriter = new StringWriter();
        this.addContainerStart(stringWriter, eo);
        toJson(stringWriter, eo, indent);
        addLineBreak(stringWriter,indent);
        addContainerEnd(stringWriter, eo, 0);
        return stringWriter.toString();
    }

    private void toJson(final StringWriter stringWriter, final EO eoParent, final int indentLevel)  {
        if (eoParent.get() == null) {
            return ;
        }
        if (eoParent.isEmpty() && serializationType!=JSONSerializationType.EO) {
            return;
        }
        Set<String> fieldNames = eoParent.keysEo();
        if (fieldNames.isEmpty()) {
            return;
        }
        boolean first = true;

        for (String fieldName: fieldNames) {
            if (PathElement.isParentNotSet(fieldName) && serializationType == JSONSerializationType.STANDARD) {
                continue;
            }
            if (eoParent.isTransient(fieldName)) {
                continue;
            }
            EO eoChild  = eoParent.getEo(fieldName);
            if (eoChild.isEmpty()) {
                continue;
            }
            if (!first) {
                stringWriter.append(",");
            }
            first = false;
            addLineBreak(stringWriter, indentLevel);
            addIndent(stringWriter, indentLevel);
            addName(stringWriter, eoChild);
            if (eoChild.isScalar()) {
                if (eoChild.getModelClass() == String.class || eoChild.getModels().isEnum()) {
                    stringWriter.append("\"");
                    stringWriter.append(stringify(eoChild.get()));
                    stringWriter.append("\"");
                } else {
                    stringWriter.append(stringify(eoChild.get()));
                }
                continue;
            }
            addContainerStart(stringWriter, eoChild);
            toJson(stringWriter, eoChild, indentLevel + 1);
            addContainerEnd(stringWriter, eoChild, indentLevel);
        }
    }

    private final void addContainerStart(final StringWriter stringWriter, final EO eo) {
        if (serializationType == JSONSerializationType.EO) {
            stringWriter.append("{");
            return;
        }
        if (eo.isList()) {
            stringWriter.append("[");
        }
        else {
            stringWriter.append("{");
        }
    }

    private void addContainerEnd(final StringWriter stringWriter, final EO eo, final int indentLevel) {
        addLineBreak(stringWriter, indentLevel);
        addIndent(stringWriter, indentLevel);
        if (serializationType == JSONSerializationType.EO) {
            stringWriter.append("}");
            return;
        }
        if (eo.isList()) {
            stringWriter.append("]");
        } else {
            stringWriter.append("}");
        }
    }

    private void addRepeated(final StringWriter stringWriter, final EO repeated, final int indentLevel) {
        addIndent(stringWriter, indentLevel);
        stringWriter.append("{\"");
        stringWriter.append(REPEATED);
        stringWriter.append("\": \"");
        stringWriter.append(repeated.getPathAsString());
        stringWriter.append("\"}");
        addLineBreak(stringWriter, indentLevel);
    }

    static String stringify(Object object) {
        if (object == null) {
            return "";
        }
        String value = NEWLINE_PATTERN
                .matcher(ScalarConverter.toString(object))
                .replaceAll("\\\\n");
        value = ESCAPE_PATTERN
                .matcher(value)
                .replaceAll("\\\\\"");
         value = REMOVE_PATTERN
                .matcher(value)
                .replaceAll("");

        if (object instanceof Number) {
            value = NUMBER_PATTERN
                    .matcher(ScalarConverter.toString(object))
                    .replaceAll("");
        }
        return value;
    }

    private void addName(final StringWriter stringWriter, final EO eoChild) {
        if (serializationType != JSONSerializationType.EO && eoChild.getParent().isList()) {
            return;
        }
        stringWriter.append("\"");
        if (serializationType == JSONSerializationType.EO) {
            stringWriter.append(eoChild.getModels().createDirective());
        }

        stringWriter.append(eoChild.getFieldKey());
        stringWriter.append("\": ");
    }

    public String getParentKeyWithModels(EO eo) {
        if (eo.isRoot()) {
            return "";
        }
        Models models = eo.getModels();
        return models.createDirective() + eo.getFieldKey();
    }

    private void addLineBreak(StringWriter stringWriter, int indent) {
        if (indent == 0) {
            return;
        }
        stringWriter.append("\n");
    }

    private void addIndent(final StringWriter stringWriter, int indent) {
        for (int i = 0; i < indent; i++) {
            stringWriter.append(this.spacer);
        }
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
