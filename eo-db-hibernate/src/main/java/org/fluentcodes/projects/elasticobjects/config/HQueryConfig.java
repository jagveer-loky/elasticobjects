package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.calls.condition.And;
import org.fluentcodes.projects.elasticobjects.calls.condition.Condition;
import org.fluentcodes.projects.elasticobjects.paths.PathPattern;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.H_EO_STATIC.*;


/**
 * Created by Werner on 09.10.2016.
 */
public class HQueryConfig extends ListConfig {
    private static final Logger LOG = LogManager.getLogger(HQueryConfig.class);
    //<call keep="JAVA" templateKey="CacheInstanceVars.tpl">

    private final String hibernateModelKey;
    private final String hql;
    private final String modelKey;
    private final String hibernateKey;
    private final PathPattern pathPattern;
    private final And and;
    private ModelInterface modelConfig;
    private HConfig hibernateCache;
//</call>


    public HQueryConfig(final EOConfigsCache configsCache, final Builder builder)  {
        super(configsCache, builder);

        //<call keep="JAVA" templateKey="CacheSetter.tpl">

        this.hibernateModelKey = builder.hibernateModelKey;
        this.hql = builder.hql;
        this.modelKey = builder.modelKey;
        this.hibernateKey = builder.hibernateKey;
        this.pathPattern = builder.pathPattern;
        this.and = builder.and;
        //</call>
    }

    //TODO
    @Override
    public IOInterface createIO() {
        return null;
    }

    public String getKey() {
        return hibernateModelKey;
    }

    public HQueryIO createListIO()  {
        return new HQueryIO(this);
    }

    //<call keep="JAVA" templateKey="CacheGetter.tpl">

    /**
     * Hibernate object with a certain model
     */
    public String getHibernateModelKey() {
        return this.hibernateModelKey;
    }

    public String getHql()  {
        if (hql != null && !hql.isEmpty()) {
            return hql;
        }
        if (this.modelKey == null) {
            throw new Exception("Neither model nor hql defined!");
        }
        return "select from " + modelKey;
    }

    public String getModelKey() {
        return this.modelKey;
    }

    /**
     * A modelConfig value
     */
    public ModelInterface getModelConfig()  {
        if (this.modelConfig == null) {
            if (this.getConfigsCache() == null) {
                throw new Exception("Config could not be initialized with a null provider for 'modelConfig' - 'modelKey''!");
            }
            this.modelConfig = getConfigsCache().findModel(modelKey);
        }
        return this.modelConfig;
    }

    /**
     * Hibernate object with a certain model
     */
    public String getHibernateKey() {
        return this.hibernateKey;
    }

    /**
     * The config embeded int {@link HQueryConfig}.
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

    /**
     * A path pattern for {@link FieldConfig}.
     */
    public PathPattern getPathPattern() {
        return this.pathPattern;
    }


    /**
     * A list of {@link Condition} conditions
     */
    public And getAnd() {
        return this.and;
    }
//</call>

    public static class Builder extends ListConfig.Builder {
        //<call keep="JAVA" templateKey="BeanInstanceVars.tpl">
        private String hql;
        private String hibernateModelKey;
        private String modelKey;
        private String hibernateKey;
        private PathPattern pathPattern;
        private And and;
//</call>

        protected void prepare(EOConfigsCache configsCache, Map<String, Object> values)  {
            hibernateModelKey = (String) configsCache.transform(F_HIBERNATE_MODEL_KEY, values);
            if (hibernateModelKey == null) {
                throw new Exception("No hibernate model key defined - No build process possible");
            }
            hql = (String) configsCache.transform(F_HQL, values);
            hibernateKey = (String) configsCache.transform(F_HIBERNATE_KEY, values);
            modelKey = (String) configsCache.transform(EO_STATIC.F_MODEL_KEY, values);
            String pathPatternAsString = (String) configsCache.transform(EO_STATIC.F_PATH_PATTERN, values);
            pathPattern = new PathPattern(pathPatternAsString);
            String andAsString = (String) configsCache.transform(EO_STATIC.F_AND, values);
            and = new And(andAsString);
            if (hibernateKey == null || hibernateKey.isEmpty()) {
                hibernateKey = hibernateModelKey.replaceAll(":[^:]+$", "");
            }
            super.prepare(configsCache, values);
        }

        public Config build(EOConfigsCache configsCache, Map<String, Object> values)  {
            prepare(configsCache, values);
            return new HQueryConfig(configsCache, this);
        }
    }
}
