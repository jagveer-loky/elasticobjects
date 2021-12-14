package org.fluentcodes.projects.elasticobjects;

import java.io.StringWriter;

import static org.fluentcodes.projects.elasticobjects.EOToJSON.stringify;

/**
 * Offers an adapter for scalar wrapper to access elements via path.
 */

public interface IEOScalar extends IEOBase, IEOModel, IEOCall, IEOLog, IEORole, IEOSerialize {
    String compare(final IEOScalar other);

    default String toString(JSONSerializationType jsonSerializationType) {
        StringWriter writer = new StringWriter();
        writer.append(getName(jsonSerializationType));
        if (getModelClass() == String.class || getModels().isEnum()) {
            writer.append("\"");
            writer.append(stringify(get()));
            writer.append("\"");
            return writer.toString();
        } else {
            return writer.toString() + stringify(get());
        }
    }

    default String getName(JSONSerializationType serializationType) {
        if (serializationType != JSONSerializationType.EO && getParent().isList()) {
            return "";
        }
        StringWriter stringWriter = new StringWriter();
        stringWriter.append("\"");
        if (serializationType == JSONSerializationType.EO) {
            stringWriter.append(getModels().createDirective());
        }

        stringWriter.append(getFieldKey());
        stringWriter.append("\": ");
        return stringWriter.toString();
    }
}
