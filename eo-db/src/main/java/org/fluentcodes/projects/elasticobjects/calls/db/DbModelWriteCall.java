package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParams;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigInterface;

import java.util.List;
import java.util.Map;

/**
 * Write model data from current eo to data base.
 * Created by werner.diwischek on 28.10.20.
 */

public class DbModelWriteCall extends DbModelCall {

    public DbModelWriteCall()  {
        super(PermissionType.WRITE);
    }
    public DbModelWriteCall(final String hostConfigKey)  {
        super(PermissionType.WRITE, hostConfigKey);
    }
    public DbModelWriteCall(final String hostConfigKey, final String modelKey)  {
        super(PermissionType.WRITE, hostConfigKey, modelKey);
    }

    @Override
    public Object execute(EO eo) {
        init(eo);
        return save (eo);
    }
    
    public int save(final EO eo) {
        ModelConfigInterface model = eo.getModel();
        if (!model.isObject()) {
            throw new EoException("No model is provided in path '" + eo.getPathAsString() + "");
        }
        PreparedStatementValues preparedStatementValues = new PreparedStatementValues();
        Map<String, Object> keyValues = eo.getKeyValues();
        int updateCount = 0;
        if (preparedStatementValues.find(getDbConfig().getConnection(), model.getModelKey(), keyValues) == 1) {
            updateCount =  new PreparedStatementValues()
                    .update(getDbConfig().getConnection(), model.getModelKey(), keyValues);
        }
        else {
            updateCount =  new PreparedStatementValues()
                    .insert(getDbConfig().getConnection(), model.getModelKey(), keyValues);
        }
        if (hasTargetPath()) {
            List result = new PreparedStatementValues()
                    .createSelect(model.getModelKey(), eo.getKeyValues())
                    .read(
                    getDbConfig().getConnection(),
                    eo.getConfigsCache(),
                    new ListParams()
                    .setRowHead(0).setRowStart(0).setRowEnd(1));
            eo.set(result, getTargetPath());
        }
        return updateCount;
    }





}
