package org.fluentcodes.projects.elasticobjects.calls.db.statements;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.db.DbConfig;
import org.fluentcodes.projects.elasticobjects.calls.db.DbSqlExecuteCall;
import org.fluentcodes.projects.elasticobjects.calls.db.DbSqlReadCall;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParams;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class FindStatementTest {
    @Before
    public void init() {
        EO eo = ProviderRootTestScope.createEo();
        Call call = new DbSqlExecuteCall(DbConfig.H2_BASIC,"h2:mem:basic:Create");
        call.execute(eo);
    }

    @Test
    public void anObject__readFirst__1() {
        EO eo = ProviderRootTestScope.createEo();
        FindStatement findStatement = new FindStatement("Select * from AnObject", eo);
        DbConfig dbConfig = (DbConfig) eo.getConfigsCache().findHost(DbConfig.H2_BASIC);
        List values = findStatement.readFirst(dbConfig.getConnection(), eo.getConfigsCache());//, new ListParams().setRowHead(0).setRowStart(0));
        Assertions.assertThat(values).isNotEmpty();
        Assertions.assertThat(values.size()).isEqualTo(1);
    }

    @Test
    public void anObject__read__3() {
        EO eo = ProviderRootTestScope.createEo();
        FindStatement findStatement = new FindStatement("Select * from AnObject", eo);
        DbConfig dbConfig = (DbConfig) eo.getConfigsCache().findHost(DbConfig.H2_BASIC);
        List values = findStatement.read(dbConfig.getConnection(), eo.getConfigsCache(), new ListParams().setRowHead(0).setRowStart(0).setRowEnd(10));
        Assertions.assertThat(values).isNotEmpty();
        Assertions.assertThat(values.size()).isEqualTo(3);
    }

    @Test
    public void anObject__execute__1() {
        EO eo = ProviderRootTestScope.createEo();
        FindStatement findStatement = new FindStatement("Select * from AnObject", eo);
        DbConfig dbConfig = (DbConfig) eo.getConfigsCache().findHost(DbConfig.H2_BASIC);
        int value = findStatement.execute(dbConfig.getConnection());
        Assertions.assertThat(value).isEqualTo(1);
    }

    @Test
    public void anObject_myString_notExist__execute__0() {
        EO eo = ProviderRootTestScope.createEo();
        FindStatement findStatement = new FindStatement("Select * from AnObject where myString='notExist'", eo);
        DbConfig dbConfig = (DbConfig) eo.getConfigsCache().findHost(DbConfig.H2_BASIC);
        int value = findStatement.execute(dbConfig.getConnection());
        Assertions.assertThat(value).isEqualTo(0);
    }

    @Test
    public void anObject_myString_value1__execute__1() {
        EO eo = ProviderRootTestScope.createEo();
        FindStatement findStatement = new FindStatement("Select * from AnObject where myString='value1'", eo);
        DbConfig dbConfig = (DbConfig) eo.getConfigsCache().findHost(DbConfig.H2_BASIC);
        int value = findStatement.execute(dbConfig.getConnection());
        Assertions.assertThat(value).isEqualTo(1);
    }
}
