package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.calls.PermissionBean;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.calls.files.FileConfig.CACHED;
import static org.fluentcodes.projects.elasticobjects.calls.files.FileConfig.FILE_NAME;
import static org.fluentcodes.projects.elasticobjects.calls.files.FileConfig.FILE_PATH;

/**
 * Created by Werner on 11.12.2020.
 */
public class FileBean extends PermissionBean implements FileBeanInterface {
    private String fileName;
    private String filePath;
    private String hostConfigKey;
    private boolean cached;

    public FileBean() {
        super();
        defaultConfigModelKey();
    }

    public FileBean(final Map map) {
        super();
        merge(map);
    }

    protected void merge (final Map map) {
        super.merge(map);
        mergeFileName(map.get(FILE_NAME));
        mergeFileName(map.get(FILE_PATH));
        mergeCached(map.get(CACHED));
        mergeTemplate(map.get(TEMPLATE));
        mergeHostConfigKey(map.get(FILE_NAME));
        defaultConfigModelKey();
    }

    @Override
    public void defaultConfigModelKey() {
        if (hasConfigModelKey()) {
            return;
        }
        setConfigModelKey(FileConfig.class.getSimpleName());
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public FileBean setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    @Override
    public String getFilePath() {
        return filePath;
    }

    @Override
    public FileBean setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    @Override
    public String getHostConfigKey() {
        return hostConfigKey;
    }

    @Override
    public FileBean setHostConfigKey(String hostConfigKey) {
        this.hostConfigKey = hostConfigKey;
        return this;
    }

    @Override
    public Boolean getCached() {
        return cached;
    }

    @Override
    public FileBean setCached(Boolean cached) {
        this.cached = cached;
        return this;
    }
}
