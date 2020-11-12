package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.HostCall;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigDbObject;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/
/**
 * Abstract call class for model based {@link ModelConfigDbObject} database operations.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Wed Nov 11 06:19:26 CET 2020
 */
public abstract class DbModelCall extends HostCall  {
/*=>{}.*/

/*==>{ALLStaticNames.tpl, fieldMap/*, override eq false, JAVA|>}|*/
   public static final String MODEL_CONFIG_KEY = "modelConfigKey";
/*=>{}.*/

/*==>{ALLInstanceVars.tpl, fieldMap/*, , JAVA|>}|*/
   private  String modelConfigKey;
/*=>{}.*/

    private ModelConfigDbObject modelConfigDbObject;
    public DbModelCall()  {
        super();
    }

    public DbModelCall(final String hostConfigKey)  {
        super(hostConfigKey);
    }

    public void setConfigKey(final String configKey) {
        this.modelConfigKey = configKey;
    }

    protected ModelConfigDbObject init(final PermissionType permissionType, final EO eo) {
        modelConfigKey = eo.getModelClass().getSimpleName();
        modelConfigDbObject = (ModelConfigDbObject) eo.getConfigsCache().findModel(modelConfigKey);
        modelConfigDbObject.hasPermissions(permissionType, eo.getRoles());
        if (!hasHostConfigKey()) {
            if (modelConfigDbObject.hasHostConfigKey()) {
                setHostConfigKey(modelConfigDbObject.getHostConfigKey());
            }
            else {
                setHostConfigKey(DbConfig.H2_BASIC);
            }
        }
        super.initHostConfig(permissionType, eo);
        return modelConfigDbObject;
    }

    protected DbConfig getDbConfig() {
        return (DbConfig)getHostConfig();
    }

/*==>{ALLSetter.tpl, fieldMap/*, , JAVA|>}|*/
    /**
    The model name for the cache object {{@link link} Config}.
    */

    public DbModelCall setModelConfigKey(String modelConfigKey) {
        this.modelConfigKey = modelConfigKey;
        return this;
    }
    
    public String getModelConfigKey () {
       return this.modelConfigKey;
    }
    
    public boolean hasModelConfigKey () {
        return modelConfigKey!= null && !modelConfigKey.isEmpty();
    }
/*=>{}.*/
}
