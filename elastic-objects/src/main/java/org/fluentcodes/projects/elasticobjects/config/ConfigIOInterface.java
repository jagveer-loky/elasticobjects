package org.fluentcodes.projects.elasticobjects.config;

import java.util.List;

/**
 * Created by Werner on 10.10.2016.
 */
public interface ConfigIOInterface {

    IOInterface createIO() ;

    RolePermissions getRolePermissions();

    Permissions getPermissions(String... roleKeys) ;

    Permissions getPermissions(List<String> roleKeys) ;

    boolean hasPermissions(Permissions permission, List<String> roleKeys) ;

    boolean hasMapPath();

    String getMapPath();

}
