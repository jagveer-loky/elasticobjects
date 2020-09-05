package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.calls.ListParams;
import org.fluentcodes.projects.elasticobjects.calls.condition.Or;
import org.fluentcodes.projects.elasticobjects.elasticobjects.EO;

import org.fluentcodes.projects.elasticobjects.elasticobjects.EOExtension;
import org.fluentcodes.projects.elasticobjects.elasticobjects.EOScalar;
import org.fluentcodes.projects.elasticobjects.utils.JSONReader;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javax.naming.InitialContext;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.*;

import static org.fluentcodes.projects.elasticobjects.DB_EO_STATIC.F_DB_KEY;
import static org.fluentcodes.projects.elasticobjects.H_EO_STATIC.*;


/**
 * Created by Werner on 09.10.2016.
 * Refactored 17.5.2018 with Builder
 */
public class HConfig extends ConfigIO implements EOExtension {
    private static final Logger LOG = LogManager.getLogger(HConfig.class);
    private final String hibernateKey;
    private final String dbKey;
    //<call keep="JAVA" templateKey="CacheInstanceVars.tpl">
    private final String models;
    private final String configFile;
    private final String auto;
    private final Map<String, String> properties;
    private final Boolean showSql;
    private final Boolean formatSql;
    private final Boolean useCacheSecondLevel;
    private final Boolean useCacheQuery;
    private final Boolean registerAutoListener;
    private final Boolean useReflectionOptimizer;
    private final List<ModelInterface> modelList;
    private boolean changed = false;
    private Configuration cfg;
    //</call>
    private DbConfig dbConfig;
    private SessionFactory sessionFactory;

    public HConfig(final EOConfigsCache configsCache, final Builder builder)  {
        super(configsCache, builder);

        //<call keep="JAVA" templateKey="CacheSetter.tpl">
        this.hibernateKey = builder.hibernateKey;
        this.dbKey = builder.dbKey;
        this.models = builder.models;
        this.configFile = builder.configFile;
        this.auto = builder.auto;
        this.properties = builder.properties;
        this.showSql = builder.showSql;
        this.formatSql = builder.formatSql;
        this.useCacheSecondLevel = builder.useCacheSecondLevel;
        this.useCacheQuery = builder.useCacheQuery;
        this.registerAutoListener = builder.registerAutoListener;
        this.useReflectionOptimizer = builder.useReflectionOptimizer;//</call>
        this.modelList = builder.modelList;
    }

    public String getKey() {
        return hibernateKey;
    }
    //<call keep="JAVA" templateKey="CacheGetter.tpl">

    /**
     * Mainly the schema
     */
    public String getHibernateKey() {
        return this.hibernateKey;
    }

    /**
     * Mainly the schema
     */
    public String getDbKey() {
        return this.dbKey;
    }

    /**
     * The config embeded int {@link HConfig}.
     */
    public DbConfig getDbConfig()  {
        if (this.dbConfig == null) {
            if (this.getConfigsCache() == null) {
                throw new Exception("Config could not be initialized with a null provider for 'dbConfig' - 'dbKey''!");
            }
            this.dbConfig = (DbConfig) getConfigsCache().find(DbConfig.class, dbKey);
        }
        return this.dbConfig;
    }

    /**
     * A string representation for a list of model keys.
     */
    public String getModels() {
        return this.models;
    }

    /**
     * A hibernate property which defines the location of a xml mapping configuration file.
     */
    public String getConfigFile() {
        return this.configFile;
    }

    /**
     * A hibernate property which defines the creation type of the database, e.g. update or create.
     */
    public String getAuto() {
        return this.auto;
    }

    /**
     * Hibernate properties which are not specified by instance var.
     */
    public Map<String, String> getProperties() {
        return this.properties;
    }

    /**
     * showSql
     */
    public Boolean isShowSql() {
        return this.showSql;
    }

    /**
     * formatSql
     */
    public Boolean isFormatSql() {
        return this.formatSql;
    }

    /**
     * useSecondLevelCache
     */
    public Boolean isUseCacheSecondLevel() {
        return this.useCacheSecondLevel;
    }

    /**
     * useQueryCache
     */
    public Boolean isUseCacheQuery() {
        return this.useCacheQuery;
    }

    /**
     * registerAutoListener
     */
    public Boolean isRegisterAutoListener() {
        return this.registerAutoListener;
    }

    /**
     * useReflectionOptimizer
     */
    public Boolean isUseReflectionOptimizer() {
        return this.useReflectionOptimizer;
    }
//</call>

    public Session getSession()  {
        initSessionFactory();
        if (sessionFactory == null) {
            throw new Exception("No session factory could be created for '" + dbKey + "'.");
        }
        try {
            return sessionFactory.getCurrentSession();
        } catch (Exception e) {
            return sessionFactory.openSession();
        }
    }


    public void closeConnection() {
    }

    public SessionFactory getSessionFactory()  {
        if (sessionFactory != null) {
            return this.sessionFactory;
        }
        initSessionFactory();
        return this.sessionFactory;
    }

    public void initSessionFactory()  {
        if (sessionFactory != null) {
            return;
        }
        if (cfg != null) { // reinit!
            StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
            sessionFactory = cfg.buildSessionFactory(ssrb.build());
            initContent();
            return;
        }
        if (getDbConfig().getJndi() != null && !"".equals(getDbConfig().getJndi())) {
            try {
                sessionFactory = (SessionFactory) new InitialContext().lookup(dbConfig.getJndi());
                if (sessionFactory == null) {
                    throw new Exception("Sessionfactory for initial context lookup for " + getDbConfig().getJndi() + " is null!");
                } else {
                    LOG.info(("Successfully initialized session factory for " + getDbConfig().getDbKey() + " by jndi=" + dbConfig.getJndi()));
                }
            } catch (Exception e) {
                LOG.info(("Could not locate sessionFactory by jndi=" + getDbConfig().getJndi() + ":  " + e.getMessage()));
            }
        }
        if (sessionFactory != null) {
            return;
        }
        if (modelList == null) {
            throw new Exception("No modelList addList for hibernate configuration!");
        }
        this.cfg = new Configuration();

        try {
            cfg.setProperty("hibernate.dialect", getDbConfig().getDbType().getDialect());
            cfg.setProperty("hibernate.connection.driver_class", getDbConfig().getDbType().getDriver());

            cfg.setProperty("hibernate.connection.username", getDbConfig().getHostConfig().getUser());
            if (getDbConfig().getHostConfig().getPassword() != null) {
                cfg.setProperty("hibernate.connection.password", getDbConfig().getHostConfig().getPassword());
            }
            String url = getDbConfig().getUrlPath();
            cfg.setProperty("hibernate.connection.url", url);

            cfg.setProperty("hibernate.bytecode.use_reflection_optimizer", useReflectionOptimizer.toString());
            cfg.setProperty("hibernate.search.autoregister_listeners", "false");
            cfg.setProperty("current_session_context_class", "thread");
            cfg.setProperty("hibernate.show_sql", showSql.toString());
            cfg.setProperty("hibernate.format_sql", formatSql.toString());
            cfg.setProperty("hibernate.hbm2ddl.auto", this.auto);

            cfg.setProperty("hibernate.config.use_second_level_cache", this.useCacheSecondLevel.toString());
            cfg.setProperty("hibernate.config.use_query_cache", this.useCacheQuery.toString());
            cfg.setProperty("hibernate.config.region.factory_class", "net.sf.ehcache.hibernate.EhCacheRegionFactory");
            InputStream inputStream = null;
            if (this.configFile != null && !this.configFile.isEmpty()) {
                Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(configFile);
                List<URL> urlList = Collections.list(urls);
                for (URL urlEntry : urlList) {
                    cfg.addURL(urlEntry);
                }
            } else if (this.modelList != null && !this.modelList.isEmpty()) {
                for (ModelInterface model : this.modelList) {
                    cfg.addAnnotatedClass(model.getModelClass());
                }
            } else {
                inputStream = createXMLConfig();
                cfg.addInputStream(inputStream);
            }
            StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
            sessionFactory = cfg.buildSessionFactory(ssrb.build());
            LOG.info("Successfully initialized session factory for '" + getDbConfig().getDbKey() + "'");
            Map metaData = sessionFactory.getAllClassMetadata();
            if (modelList.isEmpty()) {
                for (Object key : metaData.keySet()) {
                    LOG.info("MetaData " + key);
                    Class modelClass = Class.forName((String) key);
                    modelList.add(getConfigsCache().findModel(modelClass));
                }
            }
            //LOG.info ("Set dbServer to " + conf.getProperty("hibernate.connections.url"));
        } catch (Exception e1) {
            throw new Exception("Exception thrown during initialization: " + getDbConfig().getDbKey() + " neither by jndi=" + getDbConfig().getJndi() + " nor configuration file=" + configFile + " " + e1.getMessage(), e1);
        }
        if (sessionFactory == null) {
            throw new Exception("Could not initialize session factory for " + getDbConfig().getDbKey() + " neither by jndi=" + getDbConfig().getJndi() + " nor configuration file=" + configFile);
        }
        if (getDbConfig().getJndi() != null) {
            //ic.bind(dbDefinitions.getJndi(),sf);
        }
        initContent();
    }

    /**
     * Creates content from [classpath]/data/[model].json when [auto] equals 'create' and [initial] equals true or [changed] equals true.
     */
    public void init()  {
        if (!auto.equals("create")) {
            return;
        }
        if (!getSessionFactory().isClosed()) {
            sessionFactory.close();
        }
        sessionFactory = null;
        initSessionFactory();
    }

    /**
     * Creates content from [classpath]/data/[model].json when [auto] equals 'create' and [initial] equals true or [changed] equals true.
     */
    private void initContent() {
        if (!auto.equals("create")) {
            return;
        }
        for (ModelInterface model : modelList) {
            List list = new ArrayList();
            try {
                JSONReader.readListFromDataClassPath(super.getConfigsCache(), model.getModelClass(), list);
                write(model, list);
            } catch (Exception e) {
                LOG.error("Problem inserting " + model.getModelKey() + ". " + e.getMessage());
                //e.printStackTrace();
            }
        }
    }

    private InputStream createXMLConfig() {
        StringBuffer buffer = new StringBuffer("<?xml version=\"1.0\"?>\n" +
                "<!DOCTYPE hibernate-mapping PUBLIC\n" +
                "\"-//Hibernate/Hibernate Mapping DTD 3.0//EN\"\n" +
                "\"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd\">  \n" +
                "<hibernate-mapping package=\"org.clearcodes.projects.elasticobjects.assets\">\n");

        for (ModelInterface model : modelList) {
            if (model == null) {
                continue;
            }
            if (model.getClass() == null) {
                continue;
            }
            buffer.append(" <class name=\"" + model.getModelKey() + "\" table=\"" + model.getModelKey() + "\">\n");

            try {
                String idKey = model.getIdKey();
                FieldConfig idFieldCache = getConfigsCache().findField(idKey);
                if (idFieldCache != null) {
                    buffer.append("   <id name=\"" + idKey + "\" column=\"" + idKey + "\" type=\"long\" >\n" +
                            "     <generator class=\"native\"/>\n" +
                            "   </id>\n");
                }
                for (String fieldKey : model.getFieldKeys()) {
                    if (fieldKey.equals(idKey)) {
                        continue;
                    }
                    FieldConfig fieldCache = model.getField(fieldKey);
                    ModelInterface type = fieldCache.getModelConfig();
                    ModelInterface childType;
                    if (fieldCache.getChildModel() != null) {
                        childType = fieldCache.getChildModel();
                    }
                    if (type.isScalar()) {
                        String hibernateType = fieldCache.getModelClass().getSimpleName().toLowerCase();
                        buffer.append("<property name=\"" + fieldCache.getFieldKey() + "\" ");
                        buffer.append("type=\"" + hibernateType + "\" ");
                        if (fieldCache.isUnique() != null && fieldCache.isUnique() == true) {
                            buffer.append(" unique=\"true\" ");
                        }
                        if (fieldCache.isNotNull() != null && fieldCache.isNotNull() == true) {
                            buffer.append(" not-null=\"true\" ");
                        }
                        if (fieldCache.getLength() != null && fieldCache.getLength() > 0) {
                            buffer.append(" length=\"" + fieldCache.getLength() + "\" ");
                        }
                        buffer.append("/>\n");
                    } else if (type.isObject()) {
                        buffer.append("<many-to-one name=\"" + fieldCache.getFieldKey() + "\" class=\"" + fieldCache.getModelClass().getName() + "\" ");
                        //(1) column=\"column_name\"  ")
                        buffer.append("/>\n");
                    }
                }
            } catch (Exception e) {
                LOG.warn("Could not merge fields");
            }
            buffer.append("</class>\n");

        }
        buffer.append("</hibernate-mapping>\n");
        return new ByteArrayInputStream(buffer.toString().getBytes());
    }

    private String createQuery(ModelInterface model, Or or) {
        StringBuffer sql = new StringBuffer(" from " + model.getModelKey() + " where ");
        if (or == null || or.isEmpty()) {
            sql.append(" 1=1");
            return sql.toString();
        }
        sql.append(or.createQuery());
        return sql.toString();
    }

    public List read(ModelInterface model, ListParams listParams)  {
        if (listParams == null) {
            throw new Exception("Null value for listParams not allowed");
        }
        Session session = getSession();
        Query query = session
                .createQuery(createQuery(model, listParams.getFilter()));

        if (listParams.getFilter() == null || listParams.getFilter().isEmpty()) {
            return query.list();
        }
        Map<String, Object> keyValueMap = listParams.getFilter().getKeyValueMap();
        for (String idKey : keyValueMap.keySet()) {
            Object value = keyValueMap.get(idKey);
            String key = idKey.replaceAll("_\\d+$", "");

            Object transfer = ScalarConverter.transform(model.getField(key).getModelClass(), value);
            query.setParameter(idKey, transfer);
        }

        return query.list();
    }

    public List readAll(ModelInterface model)  {
        return read(model, new ListParams());
    }

    public int delete(ModelInterface model, ListParams listParams)  {
        List list = read(model, listParams);
        int counter = 0;
        for (Object dbObject : list) {
            delete(dbObject);
            counter++;
        }
        return counter;
    }

    public Object find(ModelInterface model, Object object)  {
        if (object == null) {
            return null;
        }
        try {
            Object id = model.get(model.getIdKey(), object);
            if (id != null) {
                return getSession().byId(model.getModelClass()).load((Serializable) id);
            }
        } catch (Exception e) {

        }
        if (model.getNaturalKeys() != null && !model.getNaturalKeys().isEmpty()) {
            ListParams listParams = new ListParams();
            for (String key : model.getNaturalKeys()) {
                listParams.addAnd(key, model.get(key, object));
            }

            List result = read(model, listParams);
            if (result.size() == 0) {
                return null;
                //throw new Exception("Could not find value neither by id nor natural keys!");
            }
            return result.get(0);
        }
        throw new Exception("No id defined for object " + model.getModelKey());
    }


    public void delete(ModelInterface model, Object object)  {
        delete(find(model, object));
    }

    public void delete(Object object)  {
        if (object == null) {
            throw new Exception("Tried to delete with null object");
        }
        Session session = this.getSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        session.delete(object);
        tx.commit();
        session.flush();
        changed = true;
    }


    public Object write(ModelInterface model, Object object)  {
        EOBuilder adapterBuilder = new EOBuilder(getConfigsCache())
                .setEoExtension(this);
        if (object instanceof List) {
            adapterBuilder.setModels(List.class, model.getModelClass());
        } else if (object instanceof Map) {
            adapterBuilder.setModels(Map.class, model.getModelClass());
        } else {
            adapterBuilder.setModels(model);
        }
        EO adapter = adapterBuilder.map(object);
        return adapter.get();
    }

    public void insert(ModelInterface model, Object object)  {
        Session session = this.getSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        session.persist(object);
        tx.commit();
        session.flush();
        changed = true;
    }


    public void update(ModelInterface model, Object dbObject)  {
        Session session = this.getSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        session.update(dbObject);
        tx.commit();
        session.flush();
        changed = true;
    }

    public void executeUpdate(List<String> sqlList)  {
        for (String sql : sqlList) {
            executeUpdate(sql);
        }
    }

    //https://stackoverflow.com/questions/3492453/hibernate-and-delete-all
    public void executeUpdate(String sql)  {
        Session session = this.getSession();
        Transaction tx = session.getTransaction();
        tx.begin();
        Query query = session.createSQLQuery(sql);
        query.executeUpdate();
        tx.commit();
        session.flush();
        changed = true;
    }

    public Object doBeforeMap(EO adapter, Object objectToMap)  {
        if (adapter.isList()) {
            return adapter.getModel().create();
        } else if (adapter.isMap()) {
            return adapter.getModel().create();
        } else if (adapter.isObject()) {
            Object dbObject = find(adapter.getModel(), objectToMap);
            if (dbObject == null) {
                ((EOScalar) adapter).setInsert(true);
                return adapter.getModel().create();
            }
            return dbObject;
        }
        adapter.warn("Problem no value defined");
        return null;

    }

    public void doAfterMap(EO adapter)  {
        if (adapter.isObject()) {
            if (((EOScalar) adapter).isInsert()) {
                insert(adapter.getModel(), adapter.get());
                Object dbObject = find(adapter.getModel(), adapter.get());
                EO parentAdapter = ((EOScalar) adapter).getParentAdapter();
                String parentKey = ((EOScalar) adapter).getParentKey();
                if (parentAdapter != null) {
                    parentAdapter.remove(parentKey);
                    parentAdapter.add(parentKey).set(dbObject);
                } else {
                    //adapter.addList().set(dbObject);
                }
            } else {
                update(adapter.getModel(), adapter.get());
            }

        }
    }

    public static class Builder extends ListConfig.Builder {
        //<call keep="JAVA" templateKey="BeanInstanceVars.tpl">
        private String hibernateKey;
        private String dbKey;
        //<call keep="JAVA" templateKey="CacheInstanceVars.tpl">
        private String models;
        private String configFile;
        private String auto;
        private Map<String, String> properties;
        private Boolean showSql;
        private Boolean formatSql;
        private Boolean useCacheSecondLevel;
        private Boolean useCacheQuery;
        private Boolean registerAutoListener;
        private Boolean useReflectionOptimizer;
        private List<ModelInterface> modelList;
//</call>

        protected void prepare(EOConfigsCache configsCache, Map<String, Object> values)  {
            hibernateKey = (String) configsCache.transform(F_HIBERNATE_KEY, values);
            if (hibernateKey == null) {
                throw new Exception("No hibernate key defined - No build process possible");
            }
            dbKey = (String) configsCache.transform(F_DB_KEY, values);
            models = (String) configsCache.transform(F_MODELS, values);
            configFile = (String) configsCache.transform(F_CONFIG_FILE, values);
            properties = (Map<String, String>) values.get(F_PROPERTIES);
            auto = (String) configsCache.transform(F_AUTO, values);
            showSql = (Boolean) configsCache.transform(F_SHOW_SQL, values);
            formatSql = (Boolean) configsCache.transform(F_FORMAT_SQL, values);
            useCacheSecondLevel = (Boolean) configsCache.transform(F_USE_CACHE_SECOND_LEVEL, values);
            useCacheQuery = (Boolean) configsCache.transform(F_USE_CACHE_QUERY, values);
            registerAutoListener = (Boolean) configsCache.transform(F_REGISTER_AUTO_LISTENER, values);
            useReflectionOptimizer = (Boolean) configsCache.transform(F_USER_REFLECTION_OPTIMIZER, values);
            modelList = new ArrayList<>();
            if (dbKey == null || dbKey.isEmpty()) {
                dbKey = hibernateKey;
                values.put(F_DB_KEY, dbKey);
            }
            super.prepare(configsCache, values);
        }

        public Config build(EOConfigsCache configsCache, Map<String, Object> values)  {
            prepare(configsCache, values);
            return new HConfig(configsCache, this);
        }
    }


}
