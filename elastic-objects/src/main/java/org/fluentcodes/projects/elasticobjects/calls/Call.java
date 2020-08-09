package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.paths.Path;

public interface Call<RESULT> {
    RESULT execute(final EO eo);

    void setPathByTemplate(final Path templatePath);
    void setInTemplate(Boolean inTemplate);
    Boolean getInTemplate();

    String getFilterPath();
    boolean hasFilterPath();
    Call setFilterPath(String filter);

    String getCondition();
    boolean hasCondition();
    Call setCondition(String condition);
    boolean filter(EO eo);

    String prepend();
    String postPend();

    String getLocationPath();
    boolean hasLocationPath();
    Call setLocationPath(String path);

    String getSourcePath();
    boolean hasSourcePath();
    Call setSourcePath(String path);

    String getTargetPath();
    boolean hasTargetPath();
    Call setTargetPath(String path);

    String[] getModelsAsArray();
    String getModels();
    boolean hasModels();
    Call setModels(String models);

    Long getDuration();
    void setDuration(Long duration);
}
