package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.DB_EO_STATIC.F_DB_SQL_LIST;
import static org.fluentcodes.projects.elasticobjects.H_EO_STATIC.F_HIBERNATE_KEY;
import static org.fluentcodes.projects.elasticobjects.H_EO_STATIC.F_HIBERNATE_SQL_KEY;


/**
 * Created by Werner on 09.10.2016.
 */
public class HSqlConfig extends ConfigIO {
    private static final Logger LOG = LogManager.getLogger(HSqlConfig.class);

    //<call keep="JAVA" templateKey="CacheInstanceVars.tpl">
    private final String hibernateKey;
    private final String hibernateSqlKey;
    private final List<String> sqlList;
    private HConfig hibernateCache;
//</call>


    public HSqlConfig(final EOConfigsCache provider, final Builder bean)  {
        super(provider, bean);
        //<call keep="JAVA" templateKey="CacheSetter.tpl">
        this.hibernateSqlKey = bean.hibernateSqlKey;
        this.hibernateKey = bean.hibernateKey;
        this.sqlList = bean.sqlList;
    }

    public String getKey() {
        return hibernateSqlKey;
    }

    //<call keep="JAVA" templateKey="CacheGetter.tpl">

    /**
     * Hibernate object with a certain model
     */
    public String getHibernateSqlKey() {
        return this.hibernateSqlKey;
    }

    public List<String> getSqlList() {
        return this.sqlList;
    }

    /**
     * Hibernate object with a certain model
     */
    public String getHibernateKey() {
        return this.hibernateKey;
    }

    /**
     * The config embeded int {@link HSqlConfig}.
     */
    public HConfig getHConfig()  {
        if (this.hibernateCache == null) {
            if (this.getConfigsCache() == null) {
                throw new Exception("Config could not be initialized with a null provider for 'hibernateCache' - 'hibernateKey''!");
            }
            this.hibernateCache = (HConfig) getConfigsCache().find(HConfig.class, hibernateKey);
        }
        return this.hibernateCache;
    }

//</call>

    public void execute()  {
        try {
            this.getHConfig().executeUpdate(sqlList);
        } catch (Exception e) {
            throw new Exception("Problem excucuting " + getHibernateSqlKey() + " with '" + getSqlList() + "'", e);
        }
    }

    public static class Builder extends ListConfig.Builder {
        //<call keep="JAVA" templateKey="BeanInstanceVars.tpl">

        private String hibernateSqlKey;
        private String hibernateKey;
        private List<String> sqlList;
//</call>

        protected void prepare(EOConfigsCache configsCache, Map<String, Object> values)  {
            hibernateSqlKey = (String) configsCache.transform(F_HIBERNATE_SQL_KEY, values);
            if (hibernateSqlKey == null || hibernateSqlKey.isEmpty()) {
                throw new Exception("No hibernate sql key defined - No build process possible");
            }
            hibernateKey = (String) configsCache.transform(F_HIBERNATE_KEY, values);
            sqlList = (List) configsCache.transform(F_DB_SQL_LIST, values);

            if (hibernateKey == null || hibernateKey.isEmpty()) {
                hibernateKey = hibernateSqlKey.replaceAll(":[^:]+$", "");
            }
            super.prepare(configsCache, values);
        }

        public Config build(EOConfigsCache configsCache, Map<String, Object> values)  {
            prepare(configsCache, values);
            return new HSqlConfig(configsCache, this);
        }
    }
}
