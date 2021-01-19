package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.calls.HostBean;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;

import java.util.List;
import java.util.Map;

/**
 * Created by Werner on 11.12.2020.
 */
public class DbBean extends HostBean implements DbBeanInterface {
    private List<String> sqlList;
    public DbBean() {
        super();
        defaultConfigModelKey();
    }

    public DbBean(final Map<String, Object> map) {
        super();
        defaultConfigModelKey();
    }

    public void merge(final Map configMap) {
        super.merge(configMap);
    }

    @Override
    public void defaultConfigModelKey() {
        if (hasConfigModelKey()) {
            return;
        }
        setConfigModelKey(HostConfig.class.getSimpleName());
    }

    public List<String> getSqlList() {
        return sqlList;
    }

    public void setSqlList(List<String> sqlList) {
        this.sqlList = sqlList;
    }

}
