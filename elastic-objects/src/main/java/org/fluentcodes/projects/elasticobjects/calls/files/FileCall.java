package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.HostCall;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.Parser;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/*.{javaHeader}|*/

/**
 * Super class for file calls with one configuration key. Extends {@link HostCall}. Provide an init method to resolve {@link FileConfig} and @HostConfig
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Tue Dec 08 09:45:31 CET 2020
 */
public abstract class FileCall extends HostCall  {
/*.{}.*/

    /*.{javaStaticNames}|*/
   public static final String FILE_CONFIG_KEY = "fileConfigKey";
/*.{}.*/

    /*.{javaInstanceVars}|*/
   private  String fileConfigKey;
/*.{}.*/
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
            throw new EoException("Empty file config key.");
        }
        fileConfig = eo.getConfigsCache().findFile(Parser.replacePathValues(this.fileConfigKey, eo));
        fileConfig.hasPermissions(permissionType, eo.getRoles());
        if (!hasHostConfigKey()) {
            if (fileConfig.hasHostConfigKey()) {
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
        return getHostConfig().getUrl() + fileConfig.getUrl(getHostConfig());
    }

    public void setConfigKey(String fileConfigKey) {
        this.fileConfigKey = fileConfigKey;
    }
    /*.{javaAccessors}|*/
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
/*.{}.*/
}
