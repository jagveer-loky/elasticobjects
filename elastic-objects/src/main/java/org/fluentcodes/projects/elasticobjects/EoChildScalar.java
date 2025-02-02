package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.Models;

import java.io.StringWriter;

public class EoChildScalar implements IEOScalar {
    private final IEOObject parentEo;
    private final String fieldKey;
    private Models fieldModels;
    private boolean changed = false;

    EoChildScalar(final Object value, final Models models) {
        this.parentEo = null;
        this.fieldKey = null;
        this.fieldModels = models;
    }

    public EoChildScalar(final IEOObject parentEo, final String fieldKey, final Object value, final Models fieldModels) {
        if (parentEo == null) {
            throw new EoInternalException("Null parent for " + fieldKey);
        }
        if (fieldKey == null || fieldKey.isEmpty()) {
            throw new EoException("Could not create parent EO without a fieldKey for '" + fieldModels.toString() + "'");
        }
        this.parentEo = parentEo;
        this.fieldKey = fieldKey;
        this.fieldModels = fieldModels;
        ((EoChild) this.parentEo).addEo(this.fieldKey, this);
        set(value);
    }

    public boolean hasEo() {
        return false;
    }

    void setModels(Models models) {
        this.fieldModels = models;
    }

    public void set(final Object value) {
        if (value == null) {
            throw new EoException("Will not set null");
        }
        if (!hasParent()) {
            throw new EoException("Root could not be a scalar type but starting value is '" + getModels().toString() + "'!");
        }
        if (getParentEo().get(fieldKey) != null) {
            if (!new Models(getConfigMaps(), value.getClass()).isScalar()) {
                throw new EoException("Could not create '" + getModels().toString() + "' value from '" + value.toString() + "'");
            }
            this.changed = true;
        }
        getParentEo().setValueByModel(getFieldKey(), value);
    }

    @Override
    public String getFieldKey() {
        return fieldKey;
    }

    public EoChild getParentEo() {
        return (EoChild) parentEo;
    }

    @Override
    public IEOObject getParent() {
        return parentEo;
    }

    boolean isParentSet() {
        return hasParent() && PathElement.isParentSet(fieldKey);
    }

    @Override
    public Object get() {
        return getParentEo().getValue(getFieldKey());
    }

    @Override
    public EoRoot getRoot() {
        return getParentEo().getRoot();
    }

    private EoChild getEoObject(final Path path) {
        if (path.isAbsolute()) {
            return getRoot();
        }
        if (path.first().equals(PathElement.BACK)) {
            return (EoChild) getParent();
        }
        throw new EoException("Problem with scalar starting point accessing '" + path.toString() + "'");
    }

    @Override
    public IEOScalar getEo(String... pathString) {
        Path path = new Path(pathString);
        return getEoObject(path).getEo(path);
    }

    @Override
    public Object get(final String... pathStrings) {
        return getEo(pathStrings).get();
    }

    @Override
    public IEOScalar set(final Object value, final String... pathStrings) {
        Path path = new Path(pathStrings);
        return getEoObject(path).createChild(path, value);
    }

    @Override
    public Path getPath() {
        return new Path(getPathAsString());
    }

    @Override
    public String getPathAsString() {
        final StringBuilder builder = new StringBuilder();
        getPathAsString(builder);
        return builder.toString();
    }

    void getPathAsString(final StringBuilder builder) {
        builder.insert(0, getFieldKey());
        builder.insert(0, Path.DELIMITER);
        getParentEo().getPathAsString(builder);
    }

    public Models getModels() {
        return this.fieldModels;
    }

    @Override
    public boolean isChanged() {
        return changed;
    }

    @Override
    public void setChanged() {
        this.changed = true;
    }

    @Override
    public String toString() {
        return "(" + getModels().toString() + ") " + getPathAsString() + " -> " + get().toString() + "";
    }

    @Override
    public IEOScalar map(Object value) {
        if (value == null) {
            return this;
        }
        ((EoChild) getParent()).setValueByModel(getFieldKey(), getModels().asObject(value));
        return this;
    }

    @Override
    public String compare(final IEOScalar other) {
        StringBuilder diff = new StringBuilder();
        compare(diff, other);
        return diff.toString();
    }

    void compare(final StringBuilder builder, final IEOScalar other) {
        if (other.isScalar()) {
            if (!getModels().compare(this.get(), other.get())) {
                builder.append(getPathAsString() + ": " + this.get() + " <> " + other.get());
            }
        } else {
            builder.append(getPathAsString() + ": other is not scalar but '" + other.getModelClass().getSimpleName());
        }
    }

    void writeAsString(StringWriter writer, JSONSerializationType jsonSerializationType) {
        writeName(writer, jsonSerializationType);
        writer.append(getModels().asJson(get()));
    }

    void writeName(StringWriter stringWriter, JSONSerializationType serializationType) {
        if (serializationType != JSONSerializationType.EO && getParent().isList()) {
            return;
        }
        stringWriter.append("\"");
        if (serializationType == JSONSerializationType.EO) {
            stringWriter.append(getModels().createDirective());
        }
        stringWriter.append(getFieldKey());
        stringWriter.append("\": ");
    }

}
