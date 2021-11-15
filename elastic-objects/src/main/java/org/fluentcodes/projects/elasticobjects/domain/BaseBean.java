package org.fluentcodes.projects.elasticobjects.domain;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.Map;

/*=>{javaHeader}|*/
import java.util.Date;

import static org.fluentcodes.projects.elasticobjects.domain.BaseInterface.ID;
import static org.fluentcodes.projects.elasticobjects.domain.BaseInterface.NATURAL_ID;
import static org.fluentcodes.projects.elasticobjects.domain.BaseInterface.AUTHOR;
import static org.fluentcodes.projects.elasticobjects.domain.BaseInterface.CREATION_DATE;
import static org.fluentcodes.projects.elasticobjects.domain.BaseInterface.DESCRIPTION;
/**
 * 
 * Base bean as super object for model beans with id, naturalId and description but no annotations.  
 * @author Werner Diwischek
 * @creationDate Wed Dec 21 00:00:00 CET 2016
 * @modificationDate Sat Jan 09 13:58:40 CET 2021
 */
public class BaseBean {
/*=>{}.*/
    /*=>{javaInstanceVars}|*/
   /* The author of the class. */
   private String author;
   /* Used to define the creation of an item. */
   private Date creationDate;
   /* A description of the model used by every model extending BaseClassImpl.  */
   private String description;
   /* The numeric id of an instance of a class. */
   private Long id;
   /* The natural key in @Base */
   private String naturalId;
/*=>{}.*/

    /**
     * Just an empty constructor since basic
     */
    public BaseBean() {
    }

    public BaseBean(final String naturalId) {
        this.naturalId = naturalId;
    }

    public BaseBean(final BaseConfig  config) {
        this.naturalId = config.getNaturalId();
        this.author = config.getAuthor();
        this.creationDate = config.getCreationDate();
        this.description = config.getDescription();
        this.id = config.getId();
    }

    public void merge(final BaseBean bean) {
        mergeId(bean.getId());
        mergeNaturalId(bean.getNaturalId());
        mergeDescription(bean.getDescription());
        mergeCreationDate(bean.getCreationDate());
        mergeAuthor(bean.getAuthor());
    }

    public void merge(final Map configMap) {
        if (configMap == null || configMap.isEmpty()) {
            return;
        }
        try {
            mergeId(configMap.get(ID));
            mergeNaturalId(configMap.get(NATURAL_ID));
            mergeDescription(configMap.get(DESCRIPTION));
            mergeCreationDate(configMap.get(CREATION_DATE));
            mergeAuthor(configMap.get(AUTHOR));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    /*=>{javaAccessors}|*/
   public String getAuthor() {
      return this.author;
   }

   public BaseBean setAuthor(final String author) {
      this.author = author;
      return this;
    }

   public boolean hasAuthor() {
      return getAuthor() != null && !getAuthor().isEmpty();
   }

   public Date getCreationDate() {
      return this.creationDate;
   }

   public BaseBean setCreationDate(final Date creationDate) {
      this.creationDate = creationDate;
      return this;
    }

   public boolean hasCreationDate() {
      return getCreationDate() != null;
   }

   public String getDescription() {
      return this.description;
   }

   public BaseBean setDescription(final String description) {
      this.description = description;
      return this;
    }

   public boolean hasDescription() {
      return getDescription() != null && !getDescription().isEmpty();
   }

   public Long getId() {
      return this.id;
   }

   public BaseBean setId(final Long id) {
      this.id = id;
      return this;
    }

   public boolean hasId() {
      return getId() != null;
   }

   public String getNaturalId() {
      return this.naturalId;
   }

   public BaseBean setNaturalId(final String naturalId) {
      this.naturalId = naturalId;
      return this;
    }

   public boolean hasNaturalId() {
      return getNaturalId() != null && !getNaturalId().isEmpty();
   }
/*=>{}.*/

    private void mergeNaturalId(final Object value) {
        if (value == null) return;
        if (hasNaturalId()) return;
        setNaturalId(ScalarConverter.toString(value));
    }

    private void mergeId(final Object value) {
        if (value == null) return;
        if (hasId()) return;
        setId(ScalarConverter.toLong(value));
    }

    private void mergeDescription(final Object value) {
        if (value == null) return;
        if (hasDescription()) return;
        setDescription(ScalarConverter.toString(value));
    }

    private void mergeCreationDate(final Object value) {
        if (value == null) return;
        if (hasCreationDate()) return;
        setCreationDate(ScalarConverter.toDate(value));
    }

    private void mergeAuthor(final Object value) {
        if (value == null) return;
        if (hasAuthor()) return;
        setAuthor(ScalarConverter.toString(value));
    }
}
