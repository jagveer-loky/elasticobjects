package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.Map;
/*=>{javaHeader}|*/
/**
 * 
 * A bean container class for Field values 
 * @author Werner Diwischek
 * @creationDate Wed Dec 16 00:00:00 CET 2020
 * @modificationDate Thu Jan 14 12:24:13 CET 2021
 */
public class HostBean extends PermissionBean implements HostInterface, PermissionInterface {
/*=>{}.*/

    public HostBean() {
        super();
        defaultConfigModelKey();
    }

    public HostBean(final String naturalId, final Map<String, Object> map) {
        super(naturalId, map);
        defaultConfigModelKey();
    }

    public HostBean(final Map<String, Object> map) {
        super();
        defaultConfigModelKey();
    }

    @Override
    public String getUrlCache() {
        return "";
    }

    public void merge(final Map configMap) {
        super.merge(configMap);
        defaultConfigModelKey();
    }
    
    /*=>{javaAccessors}|*/
    public HostBean setHostName(String value) {
        getProperties().put(HOST_NAME, value);
        return this;
    }
    private void mergeHostName(final Object value) {
        if (value == null) return;
        if (hasHostName()) return;
        setHostName(ScalarConverter.toString(value));
    }

    public HostBean setPassword(String value) {
        getProperties().put(PASSWORD, value);
        return this;
    }
    private void mergePassword(final Object value) {
        if (value == null) return;
        if (hasPassword()) return;
        setPassword(ScalarConverter.toString(value));
    }

    public HostBean setPort(Integer value) {
        getProperties().put(PORT, value);
        return this;
    }
    private void mergePort(final Object value) {
        if (value == null) return;
        if (hasPort()) return;
        setPort(ScalarConverter.toInteger(value));
    }

    public HostBean setProtocol(String value) {
        getProperties().put(PROTOCOL, value);
        return this;
    }
    private void mergeProtocol(final Object value) {
        if (value == null) return;
        if (hasProtocol()) return;
        setProtocol(ScalarConverter.toString(value));
    }

    public HostBean setUrl(String value) {
        getProperties().put(URL, value);
        return this;
    }
    private void mergeUrl(final Object value) {
        if (value == null) return;
        if (hasUrl()) return;
        setUrl(ScalarConverter.toString(value));
    }

    public HostBean setUser(String value) {
        getProperties().put(USER, value);
        return this;
    }
    private void mergeUser(final Object value) {
        if (value == null) return;
        if (hasUser()) return;
        setUser(ScalarConverter.toString(value));
    }


    private void defaultConfigModelKey() {
        if (hasConfigModelKey()) {
            return;
        }
        setConfigModelKey(HostConfig.class.getSimpleName());
    }

    @Override
    public String toString() {
        return getNaturalId() + " -> " + getUrl();
    }
}
