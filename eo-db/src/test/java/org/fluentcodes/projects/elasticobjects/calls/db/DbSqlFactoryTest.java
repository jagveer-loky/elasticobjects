package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.tools.xpect.XpectString;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DbSqlFactoryTest {

    @Test
    public void configFiles() {
        new XpectString().compareAsString(
                DbSqlFactory.readConfigFiles("DbSqlConfig.json")
        );
    }

    @Test
    public void mapConfigFile() {
        String value = DbSqlFactory.readConfigFiles("DbSqlConfig.json");
        EoRoot eoRoot = EoRoot.ofClass(ProviderRootTestScope.EO_CONFIGS, value, Map.class, DbSqlBean.class);
        eoRoot.mapObject(value);
        assertThat(eoRoot.size()).isEqualTo(4);
        assertThat(eoRoot.get("h2:mem:basic:AnObjectDrop").getClass()).isEqualTo(DbSqlBean.class);
    }

    @Test
    public void createBeanMap() {
        DbSqlFactory beanMap = new DbSqlFactory();
        Map<String, DbSqlBean> map = beanMap.createBeanMap(ProviderRootTestScope.EO_CONFIGS);
        assertThat(map.size()).isEqualTo(4);
    }

    @Test
    public void createConfigMap() {
        DbSqlFactory beanMap = new DbSqlFactory();
        Map<String, DbSqlConfig> map = beanMap.createConfigMap(ProviderRootTestScope.EO_CONFIGS);
        assertThat(map.size()).isEqualTo(4);
    }

    @Test
    public void getDbFromConfigMap() {
        DbSqlConfig config = (DbSqlConfig)ProviderRootTestScope.EO_CONFIGS.find(DbSqlConfig.class, "h2:mem:basic:AnObject");
        assertThat(config).isNotNull();
    }

}
