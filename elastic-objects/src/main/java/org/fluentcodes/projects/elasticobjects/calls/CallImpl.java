package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.LogLevel;
import org.fluentcodes.projects.elasticobjects.calls.condition.Or;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.paths.Path;

/**
 * Created by Werner on 13.07.2020.
 * Elementary call with mapping configuration keys to configuration via constructor.
 */
public abstract class CallImpl<RESULT> implements Call<RESULT> {
    private static final Logger LOG = LogManager.getLogger(CallImpl.class);
    private Boolean inTemplate;
    private String sourcePath;
    private String filterPath;
    private String targetPath;
    private String locationPath;
    private String models;
    private boolean overwrite;
    private LogLevel logLevel;
    private Long duration;
    private String condition;

    public CallImpl() {
    }

    public void check(final EO eo) {
        if (eo == null) {
            throw new EoException("Null eo value");
        }
        if (eo.get() == null) {
            throw new EoException("empty eo value");
        }
    }

    @Override
    public Boolean getInTemplate() {
        if (inTemplate == null) {
            return false;
        }
        return inTemplate;
    }

    public void setInTemplate(Boolean inTemplate) {
        this.inTemplate = inTemplate;
    }

    @Override
    public boolean hasModels() {
        return models!=null && !models.isEmpty();
    }

    @Override
    public String[] getModelsAsArray() {
        if (!hasModels()) {
            return new String[0];
        }
        return models.split(",");
    }

    @Override
    public String getModels() {
        return models;
    }

    @Override
    public Call setModels(String models) {
        this.models = models;
        return this;
    }

    @Override
    public boolean hasFilterPath() {
        return filterPath !=null && !filterPath.isEmpty();
    }

    @Override
    public String getFilterPath() {
        return filterPath;
    }

    @Override
    public Call setFilterPath(String filter) {
        this.filterPath = filter;
        return this;
    }

    @Override
    public String getCondition() {
        return condition;
    }

    @Override
    public Call setCondition(String condition) {
        this.condition = condition;
        return this;
    }

    @Override
    public boolean hasCondition() {
        return condition!=null && !condition.isEmpty();
    }
    @Override
    public boolean filter(EO eo) {
        if (!hasCondition()) {
            return true;
        }
        return new Or(this.condition).filter(eo);
    }

    public String prepend() {
        return "";
    }
    public String postPend() {
        return "";
    }

    @Override
    public boolean hasLocationPath() {
        return locationPath!=null && !locationPath.isEmpty();
    }

    @Override
    public String getLocationPath() {
        return locationPath;
    }

    @Override
    public Call setLocationPath(String locationPath) {
        this.locationPath = locationPath;
        return this;
    }

    @Override
    public boolean hasSourcePath() {
        return sourcePath!=null && !sourcePath.isEmpty();
    }

    @Override
    public String getSourcePath() {
        return sourcePath;
    }

    @Override
    public Call setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
        return this;
    }

    @Override
    public void setPathByTemplate(final Path templatePath) {
        this.targetPath = templatePath.directory(false);
    }

    @Override
    public boolean hasTargetPath() {
        return targetPath!=null && !targetPath.isEmpty();
    }

    @Override
    public String getTargetPath() {
        return targetPath;
    }

    @Override
    public Call setTargetPath(String targetPath) {
        this.targetPath = targetPath;
        return this;
    }

    public Boolean isOverwrite() {
        return overwrite;
    }

    public void setOverwrite(Boolean overwrite) {
        this.overwrite = overwrite;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
