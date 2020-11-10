package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.HostCall;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserSqareBracket;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/**
 * Created by werner.diwischek on 8.11.2020.
 */
public abstract class FileCall extends HostCall {
    private String fileConfigKey;
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

    public String getFileConfigKey() {
        return fileConfigKey;
    }

    public void setFileConfigKey(String fileConfigKey) {
        this.fileConfigKey = fileConfigKey;
    }

    public boolean hasFileConfigKey() {
        return fileConfigKey!=null && !fileConfigKey.isEmpty();
    }

    public void setConfigKey(String fileConfigKey) {
        this.fileConfigKey = fileConfigKey;
    }
}
