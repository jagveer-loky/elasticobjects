package org.fluentcodes.projects.elasticobjects.calls.db;

/**
 * Created by werner.diwischek on 17.01.18.
 */
public class CacheDbTests {
    /*
    protected final void checkDbCache(final String path)  {
        Assert.assertNotNull(path);
        Assert.assertFalse(path.isEmpty());

        

        final Map<String, Config> dbCacheMap =
                new EOConfigReader(TestObjectProvider.EO_CONFIGS_CACHE, DbConfig.class)
                        .read("");
        Assert.assertNotNull(dbCacheMap);
        Assert.assertTrue(dbCacheMap.size() > 0);

        for (String key : dbCacheMap.keySet()) {
            Assert.assertNotNull(TestObjectProvider.EO_CONFIGS_CACHE.find(DbConfig.class, key));
            final DbConfig cache = (DbConfig) dbCacheMap.get(key);
            checkDb(cache);
        }
    }

    private final void checkDb(final DbConfig db) {

    }


     */

}
