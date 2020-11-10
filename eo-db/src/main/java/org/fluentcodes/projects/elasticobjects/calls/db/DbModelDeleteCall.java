package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.commands.ConfigReadCommand;
import org.fluentcodes.projects.elasticobjects.calls.commands.ConfigWriteCommand;
import org.fluentcodes.projects.elasticobjects.calls.db.statements.DeleteStatement;
import org.fluentcodes.projects.elasticobjects.calls.db.statements.FindStatement;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListInterface;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParams;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigDbObject;

import java.util.List;

/**
 * Reads and writes database tables.
 * Created by werner.diwischek on 29.10.20.
 */
public class DbModelDeleteCall extends DbModelCall implements ConfigWriteCommand {
    private ListParams listParams;

    public DbModelDeleteCall()  {
        super();
    }
    public DbModelDeleteCall(final String hostConfigKey)  {
        super( hostConfigKey);
    }

    @Override
    public Object execute(EO eo) {
        return remove(eo);
    }
    
    public Object remove(final EO eo) {
        ModelConfigDbObject modelConfig = init(PermissionType.WRITE, eo);
        if (!modelConfig.isObject()) {
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
