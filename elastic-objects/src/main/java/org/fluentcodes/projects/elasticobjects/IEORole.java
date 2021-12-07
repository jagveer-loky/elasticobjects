package org.fluentcodes.projects.elasticobjects;

import java.util.List;

/**
 * Offers an adapter for objects to access elements via path.
 */

public interface IEORole {
    void setRoles(String... roles);

    List<String> getRoles();

    void setRoles(List<String> roles);

}
