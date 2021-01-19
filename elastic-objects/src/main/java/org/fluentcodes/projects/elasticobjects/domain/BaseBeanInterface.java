package org.fluentcodes.projects.elasticobjects.domain;

import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.Date;
import java.util.Map;

/*=>{javaHeader}|*/
import java.util.Date;
/**
 * 
 * Basic bean as super interface for other cached items.  
 * @author Werner Diwischek
 * @creationDate Mon Dec 21 00:00:00 CET 2020
 * @modificationDate Sun Jan 10 07:36:30 CET 2021
 */
public interface BaseBeanInterface extends BaseConfigInterface  {
/*=>{}.*/
    void merge(Map mapOfFields);

/*=>{javaAccessors}|*/
   public BaseBeanInterface setAuthor(final String author);
   default void mergeAuthor(final Object value) {
            if (value == null) return;
            if (hasAuthor()) return;
            setAuthor(ScalarConverter.toString(value));
  }

   public BaseBeanInterface setCreationDate(final Date creationDate);
   default void mergeCreationDate(final Object value) {
            if (value == null) return;
            if (hasCreationDate()) return;
            setCreationDate(ScalarConverter.toDate(value));
  }

   public BaseBeanInterface setDescription(final String description);
   default void mergeDescription(final Object value) {
            if (value == null) return;
            if (hasDescription()) return;
            setDescription(ScalarConverter.toString(value));
  }

   public BaseBeanInterface setId(final Long id);
   default void mergeId(final Object value) {
            if (value == null) return;
            if (hasId()) return;
            setId(ScalarConverter.toLong(value));
  }

   public BaseBeanInterface setNaturalId(final String naturalId);
   default void mergeNaturalId(final Object value) {
            if (value == null) return;
            if (hasNaturalId()) return;
            setNaturalId(ScalarConverter.toString(value));
  }

/*=>{}.*/

}
