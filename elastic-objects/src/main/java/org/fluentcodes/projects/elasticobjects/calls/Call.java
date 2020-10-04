package org.fluentcodes.projects.elasticobjects.calls;
// $[(TemplateResourceCall)javaGenImport/* templateKey="ALLImport.java" keepCall="JAVA" ]
import org.fluentcodes.projects.elasticobjects.LogLevel;
//$[/]
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.Path;

/**
 * Basic interface for calls 
 * Created by Werner Diwischek on 29.9.2020.
 */
public interface Call   {

// $[(TemplateResourceCall)fieldKeys/* templateKey="INTERFACEStaticNames.java" keepCall="JAVA"]
   static final String SOURCE_PATH = "sourcePath"; 
   static final String TARGET_PATH = "targetPath"; 
   static final String MODELS = "models"; 
   static final String CONDITION = "condition"; 
   static final String LOCAL_CONDITION = "localCondition"; 
   static final String OVERWRITE = "overwrite"; 
   static final String LOG_LEVEL = "logLevel"; 
   static final String DURATION = "duration"; 
//$[/]

    Object execute(final EO eo);

    void setPathByTemplate(final Path templatePath);
    void setInTemplate(Boolean inTemplate);
    Boolean getInTemplate();
    Boolean isInTemplate();
    boolean filter(EO eo);
    boolean localFilter(EO eo);

    String prepend();
    String postPend();

// $[(TemplateResourceCall)fieldKeys/* templateKey="INTERFACESetter.tpl" keepCall="JAVA"]
  /**
  A sourcePath where EO offers it's input value when the execution starts.
  */
  String getSourcePath();
  Call setSourcePath (String sourcePath);
  boolean hasSourcePath();

  /**
  A targetPath where the result of the execution will be mapped. Any combination of eo->placeholder. Could be set.
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
  String getLocalCondition();
  Call setLocalCondition (String localCondition);
  boolean hasLocalCondition();

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

//$[/]
}
