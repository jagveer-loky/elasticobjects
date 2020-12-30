package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.calls.PermissionConfig;
import org.fluentcodes.projects.elasticobjects.models.ConfigBean;

import java.util.List;


/**
 * Created by Werner on 09.10.2016.
 */
public class DbSqlConfig extends PermissionConfig implements DbSqlConfigInterface {
    private final List<String> sqlList;

    public DbSqlConfig(final ConfigBean configBean) {
        this((DbSqlBean)configBean);
    }

    public DbSqlConfig(final DbSqlBean bean) {
        super(bean);
        sqlList = bean.getSqlList();
    }

    public List<String> getSqlList() {
        return sqlList;
    }
}
