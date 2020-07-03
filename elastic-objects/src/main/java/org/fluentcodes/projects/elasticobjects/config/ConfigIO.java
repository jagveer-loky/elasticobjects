package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Werner on 10.10.2016.
 */
public abstract class ConfigIO extends ConfigImpl {
    private final RolePermissions rolePermissions;
    private final String mapPath;

    public ConfigIO(EOConfigsCache configsCache, Builder builder) {
        super(configsCache, builder);
        this.rolePermissions = builder.rolePermissions;
        this.mapPath = builder.mapPath;
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

    public boolean hasMapPath() {
        return mapPath != null && !mapPath.isEmpty();
    }

    public String getMapPath() {
        return mapPath;
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
