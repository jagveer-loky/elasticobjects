package org.fluentcodes.projects.elasticobjects.calls.db.statements;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigInterface;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigProperties;

import java.util.Map;

public class DeleteStatement extends PreparedStatementValues {

    public DeleteStatement(String model, Map<String,Object> values) {
        super(SqlType.DELETE);
        StringBuilder builderValues = new StringBuilder("");
        for (String key: values.keySet()) {
            add(values.get(key));
            builderValues.append(key + " = ? AND ");
        }
        append("DELETE ");
        append(model);
        append(" WHERE ");
        append(builderValues.toString().replaceAll(" AND $", " "));
    }

    public static DeleteStatement of(EO source) {
        if (source == null) {
            throw new EoException("Null eo for delete");
        }
        ModelConfigProperties model = source.getModel();
        if (!model.isObject()) {
            throw new EoException("Model '" + model.getModelKey() + "' is not a object");
        }
        return new DeleteStatement(model.getModelKey(), source.getKeyValues());
    }
}
