package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.Models;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.io.StringWriter;

import static org.fluentcodes.projects.elasticobjects.EOToJSON.stringify;

public class EoChildScalar implements IEOScalar {
    private final EO parentEo;
    private final String fieldKey;
    private Models fieldModels;
    private boolean changed;

    EoChildScalar(final Object value, final Models models) {
        this.parentEo = null;
        this.fieldKey = null;
        this.fieldModels = models;
        set(value);
    }

    public EoChildScalar(final EO parentEo, final String fieldKey, final Object value, final Models fieldModels) {
        if (parentEo != null && (fieldKey == null || fieldKey.isEmpty())) {
            throw new EoException("Could not create parent EO without a fieldKey for '" + fieldModels.toString() + "'");
        }
        this.parentEo = parentEo;
        this.fieldKey = fieldKey;
        this.fieldModels = fieldModels;
        set(value);
    }

    public void set(final Object value) {
        if (isRoot()) {
            throw new EoInternalException("Root has no parent!");
        }
        if (getParentEo().hasEo(new PathElement(fieldKey))) {
            this.changed = true;
        }
        getParentEo().setKeyValue(getFieldKey(), value);
    }

    @Override
    public String getFieldKey() {
        return fieldKey;
    }

    public EoChild getParentEo() {
        return (EoChild) parentEo;
    }

    @Override
    public EO getParent() {
        return parentEo;
    }

    public boolean isParentSet() {
        return parentEo != null && PathElement.isParentSet(fieldKey);
    }

    @Override
    public Object get() {
        return getParentEo().getValue(getFieldKey());
    }

    @Override
    public EoRoot getRoot() {
        return getParentEo().getRoot();
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
}
