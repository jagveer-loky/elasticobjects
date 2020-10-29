package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListInterface;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParams;

import java.util.List;

/**
 * Map results of a sql select to eo.
 * Created by werner.diwischek on 18.12.17.
 */
public class DbSqlReadCall extends DbSqlCall implements ListInterface {
    private ListParams listParams;

    public DbSqlReadCall()  {
        super(PermissionType.READ);
    }
    public DbSqlReadCall(final String hostConfigKey)  {
        super(PermissionType.READ, hostConfigKey);
    }
    public DbSqlReadCall(final String hostConfigKey, final String sqlConfigKey)  {
        super(PermissionType.READ, hostConfigKey, sqlConfigKey);
    }

    @Override
    public boolean init (EO eo) {
        super.init(eo);
        if (!hasListParams()) {
            this.listParams = new ListParams();
        }
        getListParams().merge(getSqlConfig().getProperties());
        getListParams().initDb();
        return true;
    }

    @Override
    public Object execute(EO eo) {
        init(eo);
        return mapEo(eo, readRaw(eo, getSqlConfig().getSql()));
    }

    public List readRaw(final EO eo, final String sql) {
        PreparedStatementValues preparedStatementValues = new PreparedStatementValues(sql, eo);
        return preparedStatementValues.read(
                getDbConfig().getConnection(),
                eo.getConfigsCache(),
                listParams);
    }

    @Override
    public ListParams getListParams() {
        return listParams;
    }

    public boolean hasListParams() {
        return listParams!=null && !listParams.isEmpty();
    }

}
