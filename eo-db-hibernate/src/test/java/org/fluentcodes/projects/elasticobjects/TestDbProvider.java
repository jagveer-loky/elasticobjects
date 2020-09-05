package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.config.DbConfig;
import org.fluentcodes.projects.elasticobjects.config.DbSqlConfig;
import org.fluentcodes.projects.elasticobjects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.elasticobjects.LogLevel;
import org.fluentcodes.projects.elasticobjects.calls.executor.CallExecutor;
import org.fluentcodes.projects.elasticobjects.testitemprovider.TestObjectProvider;

/**
 * Created by werner.diwischek on 18.01.18.
 */
public class TestDbProvider {

    public static final DbConfig findDbCache(String key) {
        try {
            return (DbConfig) TestObjectProvider.EO_CONFIGS_CACHE
                    .find(DbConfig.class, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final DbSqlConfig findDbSqlCache(String key) {
        try {
            return (DbSqlConfig) TestObjectProvider.EO_CONFIGS_CACHE
                    .find(DbSqlConfig.class, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static EO createAdapterWithActionBuilder(CallExecutor callExecutor)  {
        EO adapter = TestObjectProvider.createEOBuilder()
                .setLogLevel(LogLevel.WARN)
                .build();
        adapter.addCall(callExecutor);
        return adapter;
    }

    public static EO executeAdapterWithActionBuilderFromJSON(String fileName)  {
        EO adapter = TestObjectProvider.createEOBuilder()
                .mapFile(fileName);
        adapter.executeCalls();
        return adapter;
    }
}
