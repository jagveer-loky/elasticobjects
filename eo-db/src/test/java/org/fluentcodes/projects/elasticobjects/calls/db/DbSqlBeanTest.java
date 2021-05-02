package org.fluentcodes.projects.elasticobjects.calls.db;

import com.sun.tools.javac.util.List;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DbSqlBeanTest {
    @Test
    public void constructor_emptyMap() {
        Map<String, Object> testMap = new HashMap<>();
        DbSqlBean bean = new DbSqlBean("test", testMap);
        assertThat(bean.getSqlList()).isNull();
    }

    @Test
    public void constructor_mergePropertiesSqlList() {
        Map<String, Object> testMap = new HashMap<>();
        testMap.put(DbSqlBean.SQL_LIST, List.of("Test"));
        DbSqlBean bean = new DbSqlBean("test", testMap);
        assertThat(bean.getSqlList().get(0)).isEqualTo("Test");
    }


}
