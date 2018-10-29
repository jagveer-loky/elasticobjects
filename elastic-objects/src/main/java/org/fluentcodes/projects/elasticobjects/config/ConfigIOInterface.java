package org.fluentcodes.projects.elasticobjects.config;

import java.util.List;

/**
 * Created by Werner on 10.10.2016.
 */
public interface ConfigIOInterface {

    IOInterface createIO() throws Exception;

    RolePermissions getRolePermissions();

    Permissions getPermissions(String... roleKeys) throws Exception;

    Permissions getPermissions(List<String> roleKeys) throws Exception;

    boolean hasPermissions(Permissions permission, List<String> roleKeys) throws Exception;

    boolean hasMapPath();

    String getMapPath();

}
