package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ConfigConfigInterface;
import org.fluentcodes.projects.elasticobjects.models.ConfigProperties;

public interface HostConfigInterface extends ConfigConfigInterface{

    String PORT = "port";
    String PROTOCOL = "protocol";
    String PASSWORD = "password";
    String HOST_NAME = "hostName";
    String USER = "user";
    String URL = "url";

    default boolean hasUrl() {
        return getUrl()!=null && !getUrl().isEmpty();
    }

    default String getUrl() {
        return hasProperties()?(String)getProperties().get(URL):null;
    }

    default boolean hasHostName() {
        return getHostName()!=null && !getHostName().isEmpty();
    }

    default String getHostName() {
        return hasProperties()?(String)getProperties().get(HOST_NAME):null;
    }

    default boolean hasProtocol() {
        return getProtocol()!=null && !getProtocol().isEmpty();
    }

    default String getProtocol() {
        return hasProperties()?(String)getProperties().get(PROTOCOL):null;
    }


    default boolean hasPort() {
        return getPort()!=null;
    }

    default Integer getPort() {
        return hasProperties()?(Integer)getProperties().get(PORT):null;
    }

    default boolean hasUser() {
        return getUser()!=null && !getUser().isEmpty();
    }

    default String getUser() {
        return hasProperties()?(String)getProperties().get(USER):null;
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
}
