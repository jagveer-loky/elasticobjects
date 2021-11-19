package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.ConfigConfig;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;

/*=>{javaHeader}|*/

/**
 * Abstract implementation for resources with permissions methods
 *
 * @author null
 * @creationDate Wed Dec 16 00:00:00 CET 2020
 * @modificationDate Thu Jan 14 12:50:34 CET 2021
 */
public abstract class PermissionConfig extends ConfigConfig implements PermissionInterface {
  /*=>{}.*/

  private final PermissionRole rolePermissions;

  protected PermissionConfig(PermissionBean bean, final ConfigMaps configMaps) {
    super(bean, configMaps);
    this.rolePermissions = new PermissionRole(bean.getRolePermissions());
  }

  /*=>{javaAccessors}|*/
  @Override
  public PermissionRole getRolePermissions() {
    return this.rolePermissions;
  }
  /*=>{}.*/

  /**
   * Set the values from config to {@link PermissionBean}
   *
   * @param bean hostbean
   */
  public void populateBean(PermissionBean bean) {
    super.populateBean(bean);
    bean.setRolePermissions(getRolePermissions());
  }
}
