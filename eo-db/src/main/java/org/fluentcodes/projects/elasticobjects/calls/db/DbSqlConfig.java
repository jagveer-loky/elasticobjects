package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.calls.PermissionConfig;
import org.fluentcodes.projects.elasticobjects.models.ConfigBean;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;

import java.util.List;


/**
 * Created by Werner on 09.10.2016.
 */
public class DbSqlConfig extends PermissionConfig implements DbSqlInterface {
    private final List<String> sqlList;

    public DbSqlConfig(final ConfigBean configBean, final ConfigMaps configMaps) {
        this((DbSqlBean)configBean, configMaps);
    }

    public DbSqlConfig(final DbSqlBean bean, final ConfigMaps configMaps) {
        super(bean, configMaps);
        sqlList = bean.getSqlList();
    }

    public List<String> getSqlList() {
        return sqlList;
    }
}
