package org.fluentcodes.projects.elasticobjects.calls.db;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.calls.files.FileBean;
import org.fluentcodes.projects.elasticobjects.models.Scope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

public class DbSqlBeanMapTest {
    DbSqlBeanMap DB_SQL_BEAN_MAP = new DbSqlBeanMap(Scope.TEST);

    @Test
    public void TEST_dbSqlBeanMap__find_H2MemBasicCreate__notNull() {
        DbSqlBean bean = DB_SQL_BEAN_MAP.find("h2:mem:basic:Create");
        Assertions.assertThat(bean).isNotNull();
    }

}
