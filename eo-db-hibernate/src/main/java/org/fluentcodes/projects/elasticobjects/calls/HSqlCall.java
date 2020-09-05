package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.config.*;
import org.fluentcodes.projects.elasticobjects.elasticobjects.EO;


import java.util.List;
import java.util.Map;


/**
 * Created by Werner on 09.10.2016.
 */
public class HSqlCall extends CallIO {
    private static final Logger LOG = LogManager.getLogger(HSqlCall.class);

    public HSqlCall(EOConfigsCache provider, String cacheKey)  {
        super(provider, cacheKey);
    }

    public HSqlConfig getHibernateSqlCache() {
        return (HSqlConfig) getConfig();
    }

    public HConfig getHibernateCache()  {
        return getHibernateSqlCache().getHConfig();
    }

    public DbConfig getDbConfig()  {
        return getHibernateCache().getDbConfig();
    }

    public HostConfig getHostCache()  {
        return getDbConfig().getHostConfig();
    }

    public void mapAttributes(Map attributes) {
        if (attributes == null) {
            return;
        }
        super.mapAttributes(attributes);
    }

    public List<String> getSqlList() {
        return getHibernateSqlCache().getSqlList();
    }


    public EO execute()  {
        EO adapter = new EOBuilder(getProvider()).build();
        return execute(adapter);
    }

    public EO execute(EO adapter)  {
        if (adapter == null) {
            throw new Exception("Null or empty adapter. But checkConfig needs values to be readed from the db!");
        }
        getHibernateSqlCache().execute();
        return adapter;
    }
}
