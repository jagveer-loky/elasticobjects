package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.calls.HQueryCall;
import org.fluentcodes.projects.elasticobjects.config.HConfig;
import org.fluentcodes.projects.elasticobjects.config.HQueryConfig;
import org.fluentcodes.projects.elasticobjects.config.HSqlConfig;
import org.fluentcodes.projects.elasticobjects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.executor.CallExecutor;
import org.fluentcodes.projects.elasticobjects.calls.executor.ExecutorListTemplate;
import org.fluentcodes.projects.elasticobjects.testitemprovider.TestObjectProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by werner.diwischek on 18.01.18.
 */
public class TestHProvider {


    public static final HConfig findHibernateCache(String key) {
        try {
            return (HConfig) TestObjectProvider.EO_CONFIGS_CACHE
                    .find(HConfig.class, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static final HSqlConfig findHibernateSqlCache(String key) {
        try {
            return (HSqlConfig) TestObjectProvider.EO_CONFIGS_CACHE
                    .find(HSqlConfig.class, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final HQueryConfig findHibernateModelCache(String key) {
        try {
            return (HQueryConfig) TestObjectProvider.EO_CONFIGS_CACHE
                    .find(HQueryConfig.class, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final HQueryCall createHQueryAction(String key) {
        try {
            return new HQueryCall(TestObjectProvider.EO_CONFIGS_CACHE, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final CallExecutor createHibernateModelActionRead(String key)  {
        return createHibernateModelActionRead(new HashMap(), key);
    }

    public static final CallExecutor createHibernateModelActionRead(Map attributes, String key)  {
        attributes.put(ExecutorListTemplate.EXECUTE, HQueryCall.class.getSimpleName() + ".set(" + key + ")");
        return new CallExecutor(attributes);
    }

    public static final CallExecutor createHibernateModelActionWrite(String key)  {
        return createHibernateModelActionWrite(new HashMap(), key);
    }

    public static final CallExecutor createHibernateModelActionWrite(Map attributes, String key)  {
        attributes.put(ExecutorListTemplate.EXECUTE, HQueryCall.class.getSimpleName() + ".write(" + key + ")");
        return new CallExecutor(attributes);
    }

    public static final CallExecutor createHibernateModelActionDelete(String key)  {
        return createHibernateModelActionDelete(new HashMap(), key);
    }

    public static final CallExecutor createHibernateModelActionDelete(Map attributes, String key)  {
        attributes.put(ExecutorListTemplate.EXECUTE, HQueryCall.class.getSimpleName() + ".write(" + key + ")");
        return new CallExecutor(attributes);
    }

    public static EO createAdapterWithActionBuilder(CallExecutor callExecutor)  {
        EO adapter = TestObjectProvider.createEOFromJson();
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
