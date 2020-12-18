package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.domain.Base;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.Models;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EoChildParams {
    private final EO parentEo;
    private final Models pathElementModels;
    private Models fieldModels;
    private Object value;
    private final String fieldKey;
    private ModelConfig valueModel;

    protected EoChildParams(final Models models) {
        this(models, null);
    }

    protected EoChildParams(final Models models, final Object value) {
        this.fieldModels = models;
        this.fieldKey = null;
        this.parentEo = null;
        pathElementModels = null;
        this.value = value;
    }

    protected EoChildParams(final EO parentEo, final PathElement pathElement, final Object value) {
        this.parentEo = parentEo;
        this.value = value;
        if (parentEo.isList() && (pathElement.getKey() instanceof String)) {
            if (!pathElement.getKey().matches("\\d+")) {
                this.fieldKey = new Integer(parentEo.size()).toString();
            }
            else {
                this.fieldKey = pathElement.getKey();
            }
        }
        else {
            this.fieldKey = pathElement.getKey();
        }
        this.fieldModels = parentEo.getModels().getChildModels(fieldKey);
        this.pathElementModels = pathElement.getModels(parentEo.getConfigsCache());
        if (!hasFieldModels()) {
            fieldModels = pathElementModels;
        }
        resolve();
        transformValue();
    }



    protected void resolveChildAndFieldModels() {
        if (pathElementModels.getModelClass() == fieldModels.getModelClass()) {
            return;
        }
        /*throw new EoException("Problem with having differne field model ("
                + fieldModels + ") for field name '"
                + fieldKey + "'  and child model "
                + childModels.getModel().getNaturalId() + "!");*/
    }

    protected void resolve() {
        // empty ... Map
        if (resolveNoValueNoChild()) return;
        // try to resolve childModel...
        if (resolveNoValue()) return;
        // try to resolve childModel...
        createValueModel(parentEo);
        if (resolveNoChild()) return;
        if (resolveObjectLongValue()) return;
        if (resolveContainerStringValue()) return;
        // Exception when mapping is not possible...
        if (PathElement.isParentNotSet(fieldKey)) {
            return;
        }
        if (fieldModels.isScalar() && !valueModel.isScalar()) {
            throw new EoException("Problem setting non scalar value ("
                    + valueModel.getNaturalId() + ") for field name '"
                    + fieldKey + "'. Expected is "
                    + fieldModels.getModel().getNaturalId() + "!");
        }
        else if (!fieldModels.isScalar() && valueModel.isScalar()) {
            throw new EoException("Problem setting scalar value ("
                    + valueModel.getNaturalId() + ") for field name '"
                    + fieldKey + "'. Expected is "
                    + fieldModels.getModel().getNaturalId() + "!");
        }
    }

    protected void transformValue() {
        if (!fieldModels.isScalar()) return;
        if (fieldModels.getModelClass() == value.getClass()) return;
        value = ScalarConverter.transform(fieldModels.getModelClass(), value);
    }

    protected boolean resolveNoValueNoChild() {
        if (!hasValue() && !hasPathElementModels()) {
            fieldModels = new Models(parentEo.getConfigsCache(), Map.class);
            value = new LinkedHashMap<>();
            return true;
        }
        return false;
    }

    protected boolean resolveNoValue() {
        if (!hasValue() && hasPathElementModels()) {
            if (fieldModels.isCreate()) {
                value = fieldModels.create();
            }
            return true;
        }
        return false;
    }

    protected boolean resolveObjectLongValue() {
        if (!hasFieldModels()) {
            return false;
        }
        if (!fieldModels.isObject()) {
            return false;
        }
        if (!(value instanceof Long)) {
            return false;
        }
        // db?!
        Object object = fieldModels.create();
        if (object instanceof Base) {
            ((Base)object).setId((Long)object);
            this.value = object;
            return true;
        }
        throw new EoException("Problem setting long value ("
                + valueModel.getNaturalId() + ") for field name '"
                + fieldKey + "'. Expected is "
                + fieldModels.getModel().getNaturalId() + "!");

    }

    protected boolean resolveContainerStringValue() {
        if (!hasFieldModels()) {
            return false;
        }
        if (!fieldModels.isContainer()) {
            return false;
        }
        if (!(value instanceof String)) {
            return false;
        }
        if (JSONToEO.jsonMapPattern.matcher((String) value).find()) {
            return true;
        }
        if (JSONToEO.jsonListPattern.matcher((String) value).find()) {
            return true;
        }
        Object object = fieldModels.create();
        if (object instanceof Base) {
            ((Base)object).setNaturalId((String)object);
            this.value = object;
            return true;
        }
        throw new EoException("Problem setting long value ("
                + valueModel.getNaturalId() + ") for field name '"
                + fieldKey + "'. Expected is "
                + fieldModels.getModel().getNaturalId() + "!");
    }


    protected boolean resolveNoChild() {
        if (!hasValue()) {
            return false;
        }
        if (hasFieldModels()) {
            return false;
        }
        if (value instanceof String) {
            if (JSONToEO.jsonMapPattern.matcher((String) value).find()) {
                fieldModels = new Models(parentEo.getConfigsCache(), Map.class);
                return true;
            } else if (JSONToEO.jsonListPattern.matcher((String) value).find()) {
                fieldModels = new Models(parentEo.getConfigsCache(), List.class);
                return true;
            }
        }
        fieldModels = new Models(valueModel);
        return true;
    }

    protected boolean hasPathElementModels() {
        return pathElementModels != null && !pathElementModels.isEmpty();
    }

    public Models getPathElementModels() {
        return pathElementModels;
    }

    protected boolean hasFieldModels() {
        return fieldModels != null && !fieldModels.isEmpty();
    }

    public Models getFieldModels() {
        return fieldModels;
    }



    protected Object getValue() {
        return value;
    }

    protected boolean hasValue() {
        return value != null;
    }

    protected void createValueModel(EO eo) {
        if (!hasValue()) {
            return;
        }
        valueModel = eo.getConfigsCache().findModel(value);
        if (eo.getSerializationType() == JSONSerializationType.STANDARD) {
            if (valueModel.isObject() || valueModel.isMap()) {
                valueModel = eo.getConfigsCache().findModel(Map.class);
            }
        }
    }

    protected String getFieldKey() {
        return fieldKey;
    }

    protected boolean hasFieldKey() {
        return fieldKey != null && !fieldKey.isEmpty();
    }

    public EO getParentEo() {
        return parentEo;
    }
}
