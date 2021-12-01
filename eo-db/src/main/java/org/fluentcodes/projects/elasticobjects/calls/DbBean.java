package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.calls.db.DbTypes;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.List;
import java.util.Map;

/**
 * Created by Werner on 11.12.2020.
 */
public class DbBean extends HostBean implements DbInterface {
    private List<String> sqlList;
    public DbBean() {
        super();
    }

    public DbBean(final Map<String, Object> map) {
        super();
    }

    public List<String> getSqlList() {
        return sqlList;
    }

    public void setSqlList(List<String> sqlList) {
        this.sqlList = sqlList;
    }

    public void setSchema(final String value) {
        getProperties().put(SCHEMA, value);
    }
    public void setDriver(final String value) {
        getProperties().put(DRIVER, value);
    }
    public void setJndi(final String value) {
        getProperties().put(JNDI, value);
    }
    public void setDbType(final DbTypes value) {
        getProperties().put(DB_TYPE, value);
    }
    public void setExtension(final String value) {
        getProperties().put(EXTENSION, value);
    }
    private void mergeDbType(final Object value) {
        if (hasDbType()) {
            return;
        }
        if (value == null) {
            return;
        }
        if (value instanceof DbTypes) {
            setDbType((DbTypes) value);
        }
        if (value instanceof String) {
            setDbType(DbTypes.valueOf((String) value));
        }
        throw new EoException("Instance of dbType is '" + value.getClass() + "'");
    }

}
