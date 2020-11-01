package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.db.statements.FindStatement;
import org.fluentcodes.projects.elasticobjects.calls.db.statements.InsertStatement;
import org.fluentcodes.projects.elasticobjects.calls.db.statements.UpdateStatement;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigInterface;

import java.util.List;

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
        int updateCount = 0;
        if (FindStatement.ofId(eo).execute(getDbConfig().getConnection()) == 1) {
            updateCount =  UpdateStatement
                    .of(eo)
                    .execute(getDbConfig().getConnection());
        }
        else {
            updateCount =  InsertStatement
                    .of(eo)
                    .execute(getDbConfig().getConnection());
        }
        if (hasTargetPath()) {
            List result = FindStatement.of(eo)
                    .readFirst(
                    getDbConfig().getConnection(),
                    eo.getConfigsCache());
            eo.set(result, getTargetPath());
        }
        return updateCount;
    }





}
