package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.HostCall;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserSqareBracket;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/
/**
 * Super class for file calls with one configuration key. Extends {@link HostCall}. Provide an init method to resolve {@link FileConfig} and @HostConfig
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Tue Nov 10 15:53:00 CET 2020
 */
public abstract class FileCall extends HostCall {
/*=>{}.*/

    /*==>{ALLStaticNames.tpl, fieldMap/*, override eq false, JAVA|>}|*/
   public static final String FILE_CONFIG_KEY = "fileConfigKey";
/*=>{}.*/

    /*==>{ALLInstanceVars.tpl, fieldMap/*, , JAVA|>}|*/
   private  String fileConfigKey;
/*=>{}.*/
    private FileConfig fileConfig;

    public FileCall() {
        super();
    }

    public FileCall(final String configKey) {
        super();
        setFileConfigKey(configKey);
    }

    public FileCall(final String hostConfigKey, final String configKey) {
        super(hostConfigKey);
        setFileConfigKey(configKey);
    }

    protected FileConfig init(final PermissionType permissionType, final EO eo) {
        if (!hasFileConfigKey()) {
            throw new EoException("Empty key");
        }
        fileConfig = eo.getConfigsCache().findFile(ParserSqareBracket.replacePathValues(this.fileConfigKey, eo));
        fileConfig.hasPermissions(permissionType, eo.getRoles());
        if (!hasHostConfigKey()) {
            if (fileConfig.hasHostKey()) {
                setHostConfigKey(fileConfig.getHostConfigKey());
            }
            else {
               setHostConfigKey(HostConfig.LOCALHOST);
            }
        }
        super.initHostConfig(permissionType, eo);
        return fileConfig;
    }

    protected String getUrl() {
        return getHostConfig().getUrl() + fileConfig.getUrl();
    }

    public void setConfigKey(String fileConfigKey) {
        this.fileConfigKey = fileConfigKey;
    }
    /*==>{ALLSetter.tpl, fieldMap/*, , JAVA|>}|*/
    /**
    Defines the key for a file configuration {@link FileConfig} where to read or write a file.
    */

    public FileCall setFileConfigKey(String fileConfigKey) {
        this.fileConfigKey = fileConfigKey;
        return this;
    }
    
    public String getFileConfigKey () {
       return this.fileConfigKey;
    }
    
    public boolean hasFileConfigKey () {
        return fileConfigKey!= null && !fileConfigKey.isEmpty();
    }
/*=>{}.*/
}
