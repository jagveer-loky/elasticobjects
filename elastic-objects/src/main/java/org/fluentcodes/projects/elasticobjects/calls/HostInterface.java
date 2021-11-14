package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.ConfigInterface;
/*=>{javaHeader}|*/
/**
 * 
 * Methods to get the properties schema from properties map. 
 * @author Werner Diwischek
 * @creationDate Sat Sep 19 00:00:00 CEST 2020
 * @modificationDate Thu Jan 14 12:15:25 CET 2021
 */
public interface HostInterface extends ConfigInterface {
/*=>{}.*/

/*=>{javaStaticNames}|*/
   String HOST_NAME = "hostName";
   String PASSWORD = "password";
   String PORT = "port";
   String PROTOCOL = "protocol";
   String URL = "url";
   String USER = "user";
/*=>{}.*/
   String getUrlCache();
/*=>{javaAccessors}|*/
   default String getHostName(){
      return (String) getProperties().get(HOST_NAME);
   }
   default boolean hasHostName() {
      return getProperties().containsKey(HOST_NAME) && getProperties().get(HOST_NAME) != null;
   }
   default String getPassword(){
      return (String) getProperties().get(PASSWORD);
   }
   default boolean hasPassword() {
      return getProperties().containsKey(PASSWORD) && getProperties().get(PASSWORD) != null;
   }
   default Integer getPort(){
      return (Integer) getProperties().get(PORT);
   }
   default boolean hasPort() {
      return getProperties().containsKey(PORT) && getProperties().get(PORT) != null;
   }
   default String getProtocol(){
      return (String) getProperties().get(PROTOCOL);
   }
   default boolean hasProtocol() {
      return getProperties().containsKey(PROTOCOL) && getProperties().get(PROTOCOL) != null;
   }
   default String getUrl(){
      return (String) getProperties().get(URL);
   }
   default boolean hasUrl() {
      return getProperties().containsKey(URL) && getProperties().get(URL) != null;
   }
   default String getUser(){
      return (String) getProperties().get(USER);
   }
   default boolean hasUser() {
      return getProperties().containsKey(USER) && getProperties().get(USER) != null;
   }
/*=>{}.*/

    default String getPasswordReal() {
        return hasProperties()?(String)getProperties().get(PASSWORD):null;
    }
}
