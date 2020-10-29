package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.ResourceCall;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListInterface;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParams;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


/**
 * Created by Werner on 09.10.2016.
 */
public class DbSqlExecuteCall extends DbSqlCall {
    public DbSqlExecuteCall()  {
        super(PermissionType.EXECUTE);
    }
    public DbSqlExecuteCall(final String hostConfigKey)  {
        super(PermissionType.EXECUTE, hostConfigKey);
    }
    public DbSqlExecuteCall(final String hostConfigKey, final String sqlConfigKey)  {
        super(PermissionType.EXECUTE, hostConfigKey, sqlConfigKey);
    }

    public Boolean execute(EO eo)  {
        if (eo == null) {
            throw new EoException("Null or empty EO. But checkConfig needs values to be readed from the db!");
        }
        init(eo);
        return execute(getSqlConfig().getSqlList());
    }

    public boolean execute(List<String> sqlList) {
        boolean executed = true;
        for (String sql : sqlList) {
            Statement statement = null;
            try {
                statement = getDbConfig().getConnection().createStatement();
                executed = statement.execute(sql) && executed;
            }
            catch (SQLException e) {
                PreparedStatementValues.closeStatement(statement);
                throw new EoException("Problem execute " + e.getMessage());
            }
        }
        return executed;
    }

}
