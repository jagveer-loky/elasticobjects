package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.config.ConfigImpl;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.calls.Permissions;
import org.fluentcodes.projects.elasticobjects.calls.RolePermissions;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Werner on 10.10.2016.
 */
public abstract class ConfigResourcesImpl extends ConfigImpl {
    private final RolePermissions rolePermissions;

    public ConfigResourcesImpl(EOConfigsCache configsCache, Builder builder) {
        super(configsCache, builder);
        this.rolePermissions = builder.rolePermissions;
    }

    public RolePermissions getRolePermissions() {
        return rolePermissions;
    }

    public Permissions getPermissions(String... roleKeys)  {
        return rolePermissions.getPermissions(Arrays.asList(roleKeys));
    }

    public Permissions getPermissions(List<String> roleKeys)  {
        return rolePermissions.getPermissions(roleKeys);
    }

    public boolean hasPermissions(Permissions permission, List<String> roleKeys)  {
        return permission.value() <= rolePermissions.getPermissions(roleKeys).value();
    }

    public static class Builder extends ConfigImpl.Builder {
        //<call keep="JAVA" templateKey="BeanInstanceVars.tpl">

        private RolePermissions rolePermissions;
        private String mapPath;


        protected void prepare(final EOConfigsCache configsCache, final Map<String, Object> values)  {
            this.mapPath = ScalarConverter.toString(values.get(EO_STATIC.F_MAP_PATH));
            this.rolePermissions = new RolePermissions((Map) values.get(EO_STATIC.F_ROLE_PERMISSIONS));
            super.prepare(configsCache, values);
        }
    }
}
