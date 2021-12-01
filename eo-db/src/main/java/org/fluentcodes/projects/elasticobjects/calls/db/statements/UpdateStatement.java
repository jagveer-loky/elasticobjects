package org.fluentcodes.projects.elasticobjects.calls.db.statements;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.ModelInterface;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.domain.BaseInterface.F_ID;
import static org.fluentcodes.projects.elasticobjects.domain.BaseInterface.F_NATURAL_ID;

public class UpdateStatement extends PreparedStatementValues {

    public UpdateStatement(String model, Map<String,Object> values) {
        super(SqlType.UPDATE);
        StringBuilder builderValues = new StringBuilder("");
        for (String key: values.keySet()) {
            add(values.get(key));
            builderValues.append(key + " = ?, ");
        }
        append("UPDATE ");
        append(model);
        append(" SET ");
        append(builderValues.toString().replaceAll(", $", " "));
        if (values.get(F_ID)!=null) {
            add(values.get(F_ID));
            append(" WHERE id = ? ");
        }
        else if (values.get(F_NATURAL_ID)!=null) {
            add(values.get(F_NATURAL_ID));
            append(" WHERE naturalId = ? ");
        }
        else {
            throw new EoInternalException("no id nor naturalid provided.");
        }
    }

    public static UpdateStatement of(EO source) {
        if (source == null) {
            throw new EoException("Null eo for delete");
        }
        ModelInterface model = source.getModel();
        if (!model.isObject()) {
            throw new EoException("Model '" + model.getModelKey() + "' is not a object");
        }
        return new UpdateStatement(model.getModelKey(), source.getKeyValues());
    }
}
