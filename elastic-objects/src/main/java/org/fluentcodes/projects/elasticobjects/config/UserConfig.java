package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.EO_STATIC;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Werner on 21.10.2016.
 */
public class UserConfig extends ConfigImpl {
    public static final String GUEST = "guest";
    public static final String ANONYM = "anonym";
    public static final String SUPERADMIN = "superadmin";
    //<call keep="JAVA" templateKey="CacheInstanceVars.tpl">

    private final String userKey;
    private final String userName;
    private final ArrayList<String> roles;
//</call>


    public UserConfig(final EOConfigsCache provider, Builder bean) {
        super(provider, bean);
        //<call keep="JAVA" templateKey="CacheSetter.tpl">

        this.userKey = bean.userKey;
        this.userName = bean.userName;
        this.roles = bean.roles;//</call>
    }

    @Override
    public String getKey() {
        return userKey;
    }

    //<call keep="JAVA" templateKey="CacheGetter.tpl">


    /**
     * Config definitions with final instance vars
     */
    public String getUserKey() {
        return this.userKey;
    }

    /**
     * Config definitions with final instance vars
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * A list of role keys.
     */
    public ArrayList<String> getRoles() {
        return this.roles;
    }//</call>

    public static class Builder extends ConfigImpl.Builder {
        //<call keep="JAVA" templateKey="BeanInstanceVars.tpl">

        private String userKey;
        private String userName;
        private ArrayList<String> roles;
//</call>

        protected void prepare(final EOConfigsCache configsCache, final Map<String, Object> values) throws Exception {
            userKey = (String) configsCache.transform(EO_STATIC.F_USER_KEY, values);
            userName = (String) configsCache.transform(EO_STATIC.F_USER_NAME, values);
            super.prepare(configsCache, values);
        }

        public UserConfig build(final EOConfigsCache configsCache, final Map<String, Object> values) throws Exception {
            prepare(configsCache, values);
            return new UserConfig(configsCache, this);
        }

    }
}
