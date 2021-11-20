package org.fluentcodes.projects.elasticobjects.domain;

/*.{javaHeader}|*/

import java.util.Date;

/**
 * Basic final configuration as super object for all configuration with id, naturalId and description.
 *
 * @author Werner Diwischek
 * @creationDate Mon Dec 21 00:00:00 CET 2020
 * @modificationDate Sat Jan 09 08:03:06 CET 2021
 */
public class BaseConfig implements BaseInterface {
  /*.{}.*/
  /*.{javaInstanceVars}|*/
  /* The author of the class. */
  private final String author;
  /* Used to define the creation of an item. */
  private final Date creationDate;
  /* A description of the model used by every model extending BaseClassImpl.  */
  private final String description;
  /* The numeric id of an instance of a class. */
  private final Long id;
  /* The natural key in @Base */
  private final String naturalId;
  /*.{}.*/

  /*.{javaBeanConstructor}|*/
  public BaseConfig(final BaseBean bean) {
    this.author = bean.getAuthor();
    this.creationDate = bean.getCreationDate();
    this.description = bean.getDescription();
    this.id = bean.getId();
    this.naturalId = bean.getNaturalId();
  }
  /*.{}.*/

  /*.{javaAccessors}|*/
  public String getAuthor() {
    return this.author;
  }

  public boolean hasAuthor() {
    return getAuthor() != null && !getAuthor().isEmpty();
  }

  public Date getCreationDate() {
    return this.creationDate;
  }

  public boolean hasCreationDate() {
    return getCreationDate() != null;
  }

  public String getDescription() {
    return this.description;
  }

  public boolean hasDescription() {
    return getDescription() != null && !getDescription().isEmpty();
  }

  public Long getId() {
    return this.id;
  }

  public boolean hasId() {
    return getId() != null;
  }

  public String getNaturalId() {
    return this.naturalId;
  }

  public boolean hasNaturalId() {
    return getNaturalId() != null && !getNaturalId().isEmpty();
  }


  /*.{}.*/

  /**
   * Set the values from config to {@link BaseBean}
   *
   * @param baseBean basebean
   */
  public void populateBean(BaseBean baseBean) {
    baseBean.setAuthor(getAuthor());
    baseBean.setId(getId());
    baseBean.setNaturalId(getNaturalId());
    baseBean.setCreationDate(getCreationDate());
    baseBean.setDescription(getDescription());
  }
}
