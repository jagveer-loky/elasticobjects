package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.Map;

/**
 * Created by Werner on 21.10.2016.
 */
public class RoleConfig extends ConfigImpl {
    public static final String GUEST = "guest";
    public static final String ANONYM = "anonym";
    public static final String SUPERADMIN = "superadmin";
    private final String name;

    public RoleConfig(final EOConfigsCache provider, final Builder bean) {
        super(provider, bean);
        this.name = bean.name;
    }

    public String getKey() {
        return name;
    }

    public String getName() {
        return name;
    }

    public static class Builder extends ConfigImpl.Builder {
        private String name;
        protected void prepare(final EOConfigsCache configsCache, final Map<String, Object> values) throws Exception {
            name = (String)configsCache.transform(EO_STATIC.F_NAME, values);
            super.prepare(configsCache, values);
        }

        public RoleConfig build(final EOConfigsCache configsCache, final Map<String, Object> values) throws Exception {
            prepare(configsCache, values);
            return new RoleConfig(configsCache, this);
        }

    }
}
