package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.Path;

public interface Call {
    Object execute(final EO eo);

    void setPathByTemplate(final Path templatePath);
    void setInTemplate(Boolean inTemplate);
    Boolean getInTemplate();
    Boolean isInTemplate();

    String getCondition();
    boolean hasCondition();
    Call setCondition(String condition);

    public String getLocalCondition();
    public void setLocalCondition(String localCondition);
    boolean hasLocalCondition();

    boolean filter(EO eo);
    boolean localFilter(EO eo);

    String prepend();
    String postPend();

    String getSourcePath();
    boolean hasSourcePath();
    Call setSourcePath(String path);

    String getTargetPath();
    boolean hasTargetPath();
    boolean isMapToTargetEo();
    Call setTargetPath(String targetPath);

    String[] getModelsAsArray();
    String getModels();
    boolean hasModels();
    Call setModels(String models);

    Long getDuration();
    void setDuration(Long duration);
}
