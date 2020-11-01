package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.Config;

public interface HostProperties extends Config {

    String PORT = "port";
    String PROTOCOL = "protocol";
    String PASSWORD = "password";
    String HOST_NAME = "hostName";
    String USER = "user";
    String URL = "url";

    String getUrlPath();

    default boolean hasUrl() {
        return getUrl()!=null && !getUrl().isEmpty();
    }

    default String getUrl() {
        return hasProperties()?(String)getProperties().get(URL):null;
    }

    default void setUrl(final String value) {
        if (hasProperties()) {
            getProperties().put(value, URL);
        }
    }

    default boolean hasHostName() {
        return getHostName()!=null && !getHostName().isEmpty();
    }

    default String getHostName() {
        return hasProperties()?(String)getProperties().get(HOST_NAME):null;
    }

    default void setHostName(final String value) {
        if (hasProperties()) {
            getProperties().put(value, HOST_NAME);
        }
    }

    default boolean hasProtocol() {
        return getProtocol()!=null && !getProtocol().isEmpty();
    }

    default String getProtocol() {
        return hasProperties()?(String)getProperties().get(PROTOCOL):null;
    }

    default void setProtocol(final String value) {
        if (hasProperties()) {
            getProperties().put(value, PROTOCOL);
        }
    }

    default boolean hasPort() {
        return getPort()!=null;
    }

    default Integer getPort() {
        return hasProperties()?(Integer)getProperties().get(PORT):null;
    }

    default void setPort(final Integer value) {
        if (hasProperties()) {
            getProperties().put(value, PORT);
        }
    }

    default boolean hasUser() {
        return getUser()!=null && !getUser().isEmpty();
    }

    default String getUser() {
        return hasProperties()?(String)getProperties().get(USER):null;
    }

    default void setUser(final String value) {
        if (hasProperties()) {
            getProperties().put(value, USER);
        }
    }

    default boolean hasPassword() {
        return getPassword()!=null && !getPassword().isEmpty();
    }

    default String getPassword() {
        return hasProperties() && getProperties().containsKey(PASSWORD) ? "########" : null;
    }

    default String getPasswordReal() {
        return hasProperties()?(String)getProperties().get(PASSWORD):null;
    }

    default void setPassword(final String value) {
        if (hasProperties()) {
            getProperties().put(value, PASSWORD);
        }
    }
}
