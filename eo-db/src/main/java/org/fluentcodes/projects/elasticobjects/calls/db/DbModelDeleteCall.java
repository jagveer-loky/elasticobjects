package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.db.statements.DeleteStatement;
import org.fluentcodes.projects.elasticobjects.calls.db.statements.FindStatement;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParams;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigInterface;

import java.util.List;

/**
 * Reads and writes database tables.
 * Created by werner.diwischek on 29.10.20.
 */
public class DbModelDeleteCall extends DbModelCall {
    private ListParams listParams;

    public DbModelDeleteCall()  {
        super(PermissionType.DELETE);
    }
    public DbModelDeleteCall(final String hostConfigKey)  {
        super(PermissionType.DELETE, hostConfigKey);
    }

    @Override
    public boolean init (EO eo) {
        super.init(eo);
        return true;
    }

    @Override
    public Object execute(EO eo) {
        init(eo);
        return remove(eo);
    }
    
    public Object remove(final EO eo) {
        ModelConfigInterface model = eo.getModel();
        if (!model.isObject()) {
            throw new EoException("No model is provided in path '" + eo.getPathAsString() + "");
        }
        if (hasTargetPath()) {
            List result = FindStatement.of(eo)
                    .readFirst(
                            getDbConfig().getConnection(),
                            eo.getConfigsCache());
            eo.set(result, getTargetPath());
        }

        return DeleteStatement.of(eo)
                .execute(getDbConfig().getConnection());
    }
}
