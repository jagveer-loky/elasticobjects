package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.Path;
/*=>{javaHeader}|*/
import org.fluentcodes.projects.elasticobjects.LogLevel;
import org.fluentcodes.projects.elasticobjects.calls.templates.KeepCalls;

/**
 * Basic interface for calls 
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Tue Dec 08 16:27:20 CET 2020
 */
public interface Call  {
/*=>{}.*/
    String TARGET_AS_STRING = "_asString";

    /*=>{javaStaticNames}|*/
   String CONDITION = "condition";
   String DURATION = "duration";
   String KEEP_CALL = "keepCall";
   String LOG_LEVEL = "logLevel";
   String MODELS = "models";
   String OVERWRITE = "overwrite";
   String POSTPEND = "postpend";
   String PREPEND = "prepend";
   String SOURCE_PATH = "sourcePath";
   String START_CONDITION = "startCondition";
   String TARGET_PATH = "targetPath";
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

    /*=>{javaAccessors}|*/

  /**
  * A condition for calls. 
  */
   String getCondition();
   Call setCondition (String condition);
   default Boolean hasCondition () {
      return getCondition()!= null && !getCondition().isEmpty();
    }

  /**
  * The duration of a call.
  */
   Long getDuration();
   Call setDuration (Long duration);
   default Boolean hasDuration () {
      return getDuration()!= null;
    }

  /**
  * Will use an existing  result file beforehand as template. 
  */
   KeepCalls getKeepCall();
   Call setKeepCall (KeepCalls keepCall);
   default Boolean hasKeepCall () {
      return getKeepCall()!= null;
    }

  /**
  * logLevel
  */
   LogLevel getLogLevel();
   Call setLogLevel (LogLevel logLevel);
   default Boolean hasLogLevel () {
      return getLogLevel()!= null;
    }

  /**
  * A string representation for a list of model keys.
  */
   String getModels();
   Call setModels (String models);
   default Boolean hasModels () {
      return getModels()!= null && !getModels().isEmpty();
    }

  /**
  * A overwrite field flag for @Call.
  */
   Boolean getOverwrite();
   Call setOverwrite (Boolean overwrite);
   default Boolean hasOverwrite () {
      return getOverwrite()!= null;
    }

  /**
  * postpend String when executed by ExecutorCallImpl
  */
   String getPostpend();
   Call setPostpend (String postpend);
   default Boolean hasPostpend () {
      return getPostpend()!= null && !getPostpend().isEmpty();
    }

  /**
  * prepend String when executed by ExecutorCallImpl
  */
   String getPrepend();
   Call setPrepend (String prepend);
   default Boolean hasPrepend () {
      return getPrepend()!= null && !getPrepend().isEmpty();
    }

  /**
  * A sourcePath where EO offers it's input value when the execution starts.
  */
   String getSourcePath();
   Call setSourcePath (String sourcePath);
   default Boolean hasSourcePath () {
      return getSourcePath()!= null && !getSourcePath().isEmpty();
    }

  /**
  * A condition for calls. 
  */
   String getStartCondition();
   Call setStartCondition (String startCondition);
   default Boolean hasStartCondition () {
      return getStartCondition()!= null && !getStartCondition().isEmpty();
    }

  /**
  * A targetPath where the result of the execution will be mapped. If value is "_asString" no mapping occured but a seralized version is returned as value to embed it in the resulting file. Path parameters could be set dynamically with =&gt;[path]. in any combination.
  */
   String getTargetPath();
   Call setTargetPath (String targetPath);
   default Boolean hasTargetPath () {
      return getTargetPath()!= null && !getTargetPath().isEmpty();
    }
/*=>{}.*/
}
