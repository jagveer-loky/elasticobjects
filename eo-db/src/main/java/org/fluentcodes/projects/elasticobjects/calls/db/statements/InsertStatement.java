package org.fluentcodes.projects.elasticobjects.calls.db.statements;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigInterface;

import java.util.Map;

public class InsertStatement extends PreparedStatementValues {

    public InsertStatement(String model, Map<String,Object> values) {
        super(SqlType.INSERT);
        StringBuilder builderFields = new StringBuilder("(");
        StringBuilder builderValues = new StringBuilder("(");
        for (String key: values.keySet()) {
            add(values.get(key));
            builderFields.append(key + ", ");
            builderValues.append("?, ");
        }
        append("INSERT INTO ");
        append(model);
        append(" ");
        append(builderFields.toString().replaceAll(", $", ") "));
        append(" VALUES ");
        append(builderValues.toString().replaceAll(", $", ") "));
    }

    public static InsertStatement of(EO source) {
        if (source == null) {
            throw new EoException("Null eo for delete");
        }
        ModelConfigInterface model = source.getModel();
        if (!model.isObject()) {
            throw new EoException("Model '" + model.getModelKey() + "' is not a object");
        }
        return new InsertStatement(model.getModelKey(), source.getKeyValues());
    }
}
