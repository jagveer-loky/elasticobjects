package org.fluentcodes.projects.elasticobjects.calls;
//  ===>{"(TemplateResourceCall).":{"sourcePath":"javaGenImport/*", "configKey":"ALLImport.tpl", "keepCall":"JAVA"}}|
import org.fluentcodes.projects.elasticobjects.LogLevel;
//=>{}.
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.Path;

/**
 * Basic interface for calls 
 * Created by Werner Diwischek on 29.9.2020.
 */
public interface Call   {
    String TARGET_AS_STRING = "_asString";
    //  ===>{"(TemplateResourceCall).":{"sourcePath":"fieldKeys/*", "configKey":"INTERFACEStaticNames.tpl", "keepCall":"JAVA"}}|
    static final String SOURCE_PATH = "sourcePath";
    static final String TARGET_PATH = "targetPath";
    static final String MODELS = "models";
    static final String CONDITION = "condition";
    static final String START_CONDITION = "startCondition";
    static final String OVERWRITE = "overwrite";
    static final String LOG_LEVEL = "logLevel";
    static final String DURATION = "duration";
    static final String PREPEND = "prepend";
    static final String POSTPEND = "postpend";


//=>{}.

    default void initTargetPath(final Path targetPathFromCallPath) {
        if (!hasTargetPath()) {
            setTargetPath(targetPathFromCallPath.directory());
        }
    }

    Object execute(final EO eo);
    void setByParameter(final String values);
    default boolean isTargetAsString() {
        return TARGET_AS_STRING.equals(getTargetPath());
    }

//  ===>{"(TemplateResourceCall).":{"sourcePath":"fieldKeys/*", "configKey":"INTERFACESetter.tpl", "keepCall":"JAVA"}}|
    /**
     A sourcePath where EO offers it's input value when the execution starts.
     */
    String getSourcePath();
    Call setSourcePath (String sourcePath);
    boolean hasSourcePath();

    /**
     A targetPath where the result of the execution will be mapped. Any combination of $(placeholder. Could be set.
     */
    String getTargetPath();
    Call setTargetPath (String targetPath);
    boolean hasTargetPath();

    /**
     A string representation for a list of model keys.
     */
    String getModels();
    Call setModels (String models);
    boolean hasModels();

    /**
     A condition for calls.
     */
    String getCondition();
    Call setCondition (String condition);
    boolean hasCondition();

    /**
     A condition for calls.
     */
    String getStartCondition();
    Call setStartCondition (String startCondition);
    boolean hasStartCondition();

    /**
     overwrite
     */
    Boolean getOverwrite();
    Call setOverwrite (Boolean overwrite);
    boolean hasOverwrite();

    /**
     logLevel
     */
    LogLevel getLogLevel();
    Call setLogLevel (LogLevel logLevel);
    boolean hasLogLevel();

    /**
     The duration of a call.
     */
    Long getDuration();
    Call setDuration (Long duration);
    boolean hasDuration();

    /**
     prepend String when executed by ExecutorCallImpl
     */
    String getPrepend();
    Call setPrepend (String prepend);
    boolean hasPrepend();

    /**
     postpend String when executed by ExecutorCallImpl
     */
    String getPostpend();
    Call setPostpend (String postpend);
    boolean hasPostpend();

//=>{}.
}
