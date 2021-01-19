package org.fluentcodes.projects.elasticobjects.calls;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

/*=>{javaHeader}|*/
/**
 * 
 * Methods to get the properties schema from properties map. 
 * @author Werner Diwischek
 * @creationDate Wed Dec 16 00:00:00 CET 2020
 * @modificationDate Thu Jan 14 12:20:17 CET 2021
 */
public interface HostBeanInterface extends HostConfigInterface,PermissionBeanInterface  {
/*=>{}.*/

/*=>{javaAccessors}|*/
   default HostBeanInterface setHostName(String value) {
      getProperties().put(HOST_NAME, value);
      return this;
   }
   default void mergeHostName(final Object value) {
      if (value == null) return;
      if (hasHostName()) return;
      setHostName(ScalarConverter.toString(value));
   }

   default HostBeanInterface setPassword(String value) {
      getProperties().put(PASSWORD, value);
      return this;
   }
   default void mergePassword(final Object value) {
      if (value == null) return;
      if (hasPassword()) return;
      setPassword(ScalarConverter.toString(value));
   }

   default HostBeanInterface setPort(Integer value) {
      getProperties().put(PORT, value);
      return this;
   }
   default void mergePort(final Object value) {
      if (value == null) return;
      if (hasPort()) return;
      setPort(ScalarConverter.toInteger(value));
   }

   default HostBeanInterface setProtocol(String value) {
      getProperties().put(PROTOCOL, value);
      return this;
   }
   default void mergeProtocol(final Object value) {
      if (value == null) return;
      if (hasProtocol()) return;
      setProtocol(ScalarConverter.toString(value));
   }

   default HostBeanInterface setUrl(String value) {
      getProperties().put(URL, value);
      return this;
   }
   default void mergeUrl(final Object value) {
      if (value == null) return;
      if (hasUrl()) return;
      setUrl(ScalarConverter.toString(value));
   }

   default HostBeanInterface setUser(String value) {
      getProperties().put(USER, value);
      return this;
   }
   default void mergeUser(final Object value) {
      if (value == null) return;
      if (hasUser()) return;
      setUser(ScalarConverter.toString(value));
   }

/*=>{}.*/

}
