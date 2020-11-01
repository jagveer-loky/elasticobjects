package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.db.statements.FindStatement;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListInterface;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParams;
import org.fluentcodes.projects.elasticobjects.calls.templates.KeepCalls;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.List;

/**
 * Map results of a sql select to eo.
 * Created by werner.diwischek on 18.12.17.
 */
public class DbSqlReadCall extends DbSqlCall implements ListInterface {
    private ListParams listParams;

    public DbSqlReadCall()  {
        super(PermissionType.READ);
        listParams = new ListParams();
    }
    public DbSqlReadCall(final String hostConfigKey)  {
        super(PermissionType.READ, hostConfigKey);
        listParams = new ListParams();
    }
    public DbSqlReadCall(final String hostConfigKey, final String sqlConfigKey)  {
        super(PermissionType.READ, hostConfigKey, sqlConfigKey);
        listParams = new ListParams();
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

    @Override
    public void setByParameter(final String values) {
        if (values == null||values.isEmpty()) {
            throw new EoException("Set by empty input values");
        }
        String[] array = values.split(", ");
        if (array.length>5) {
            throw new EoException("Short form should have form '<configKey>[,<targetPath>][,<condition>][,<keepCall>]' with max length 3 but has size " + array.length + ": '" + values + "'." );
        }
        if (array.length>0) {
            setConfigKey(array[0]);
        }
        if (array.length>1) {
            setSqlKey(array[1]);
        }
        if (array.length>2) {
            setTargetPath( array[2]);
        }
        if (array.length>3) {
            setCondition( array[3]);
        }
        if (array.length>4) {
            setKeepCall(KeepCalls.valueOf(array[4]));
        }
    }

    public List readRaw(final EO eo, final String sql) {
        return new FindStatement(sql, eo)
                .read(
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
