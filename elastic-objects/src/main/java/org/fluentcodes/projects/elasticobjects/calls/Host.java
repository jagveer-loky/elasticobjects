package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.domain.Base;

public interface Host extends HostConfigInterface, Base {

    default void setHostName(final String value) {
        if (hasProperties()) {
            getProperties().put(value, HOST_NAME);
        }
    }

    default void setProtocol(final String value) {
        getProperties().put(value, PROTOCOL);
    }
    default void setPort(final Integer value) {
        getProperties().put(value, PORT);
    }

    default void setUser(final String value) {
        getProperties().put(value, USER);
    }

    default void setPassword(final String value) {
        getProperties().put(value, PASSWORD);
    }
}
