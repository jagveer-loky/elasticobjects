package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.fluentcodes.projects.elasticobjects.calls.IOInterface;
import org.fluentcodes.projects.elasticobjects.calls.Permissions;
import org.fluentcodes.projects.elasticobjects.calls.RolePermissions;

import java.util.List;

/**
 * Created by Werner on 10.10.2016.
 */
public interface ConfigResources {

    IOInterface createIO() ;

    RolePermissions getRolePermissions();

    Permissions getPermissions(String... roleKeys);

    Permissions getPermissions(List<String> roleKeys) ;

    boolean hasPermissions(Permissions permission, List<String> roleKeys) ;
}
