package org.fluentcodes.projects.elasticobjects.calls;

import java.util.List;
import java.util.Map;

/**
 * Created by Werner on 11.12.2020.
 */
public class DbBean extends HostBean implements DbBeanInterface {
    private List<String> sqlList;
    public DbBean() {
        super();
    }

    public DbBean(final Map<String, Object> map) {
        super();
    }

    public void merge(final Map configMap) {
        super.merge(configMap);
    }

    public List<String> getSqlList() {
        return sqlList;
    }

    public void setSqlList(List<String> sqlList) {
        this.sqlList = sqlList;
    }

}
