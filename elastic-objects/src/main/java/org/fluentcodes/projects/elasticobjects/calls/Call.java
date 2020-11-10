package org.fluentcodes.projects.elasticobjects.calls;
/*==>{ALLImport.tpl, javaGenImport/*, JAVA}|*/
import org.fluentcodes.projects.elasticobjects.LogLevel;
/*=>{}.*/
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.calls.templates.KeepCalls;

/**
 * Basic interface for calls 
 * Created by Werner Diwischek on 29.9.2020.
 */
public interface Call  {
    String TARGET_AS_STRING = "_asString";

    /*==>{INTERFACEStaticNames.tpl, fieldMap/*, JAVA}|*/
    String CONDITION = "condition";
    String DURATION = "duration";
    String LOG_LEVEL = "logLevel";
    String MODELS = "models";
    String OVERWRITE = "overwrite";
    String POSTPEND = "postpend";
    String PREPEND = "prepend";
    String SOURCE_PATH = "sourcePath";
    String START_CONDITION = "startCondition";
    String TARGET_PATH = "targetPath";
    String KEEP_CALL = "keepCall";
    /*=>{}.*/

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

    boolean hasKeepCall();
    KeepCalls getKeepCall();
    Call setKeepCall(KeepCalls keepCalls);
    default String getKeepEndSequence() {
        if (!hasKeepCall()) {
            return "";
        }
        return getKeepCall().getEndComment();
    }
    default String getKeepStartSequence() {
        if (!hasKeepCall()) {
            return "";
        }
        return getKeepCall().getStartComment();
    }

    /*==>{INTERFACESetter.tpl, fieldMap/*, JAVA}|*/

    /**
     * A condition for calls.
     */
    String getCondition();
    Call setCondition (String condition);
    boolean hasCondition ();

    /**
     * The duration of a call.
     */
    Long getDuration();
    Call setDuration (Long duration);
    boolean hasDuration ();

    /**
     * logLevel
     */
    LogLevel getLogLevel();
    Call setLogLevel (LogLevel logLevel);
    boolean hasLogLevel ();

    /**
     * A string representation for a list of model keys.
     */
    String getModels();
    Call setModels (String models);
    boolean hasModels ();

    /**
     * overwrite
     */
    Boolean getOverwrite();
    Call setOverwrite (Boolean overwrite);
    boolean hasOverwrite ();

    /**
     * postpend String when executed by ExecutorCallImpl
     */
    String getPostpend();
    Call setPostpend (String postpend);
    boolean hasPostpend ();

    /**
     * prepend String when executed by ExecutorCallImpl
     */
    String getPrepend();
    Call setPrepend (String prepend);
    boolean hasPrepend ();

    /**
     * A sourcePath where EO offers it's input value when the execution starts.
     */
    String getSourcePath();
    Call setSourcePath (String sourcePath);
    boolean hasSourcePath ();

    /**
     * A condition for calls.
     */
    String getStartCondition();
    Call setStartCondition (String startCondition);
    boolean hasStartCondition ();

    /**
     * A targetPath where the result of the execution will be mapped. If value is "_asString" no mapping occured but a seralized version is returned as value to embed it in the resulting file. Path parameters could be set dynamically with =&gt;[path]. in any combination.
     */
    String getTargetPath();
    Call setTargetPath (String targetPath);
    boolean hasTargetPath ();
    /*=>{}.*/
}
