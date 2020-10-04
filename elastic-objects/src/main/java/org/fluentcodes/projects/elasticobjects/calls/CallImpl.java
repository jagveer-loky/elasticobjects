package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.*;
import org.fluentcodes.projects.elasticobjects.calls.condition.Or;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserEoReplace;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelImpl;

/**
 * Created by Werner on 13.07.2020.
 * Elementary call with mapping configuration keys to configuration via constructor.
 */
public abstract class CallImpl extends ModelImpl implements Call {
    public static final String TARGET_AS_STRING = "_asString";
    
// $[(TemplateResourceCall)fieldKeys/* templateKey="BEANStaticNames.java" keepCall="JAVA"]
    public static final String SOURCE_PATH = "sourcePath";
    public static final String TARGET_PATH = "targetPath";
    public static final String MODELS = "models";
    public static final String CONDITION = "condition";
    public static final String LOCAL_CONDITION = "localCondition";
    public static final String OVERWRITE = "overwrite";
    public static final String LOG_LEVEL = "logLevel";
    public static final String DURATION = "duration";
//$[/]

// $[(TemplateResourceCall)fieldKeys/* templateKey="BEANInstanceVars.tpl" keepCall="JAVA"]
    private String sourcePath;
    private String targetPath;
    private String models;
    private String condition;
    private String localCondition;
    private Boolean overwrite;
    private LogLevel logLevel;
    private Long duration;
//$[/]
    
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

    protected Object createReturnScalar(EO eo, Object result) {
        if (!hasTargetPath()) {
            return result;
        }
        if (isInTemplate()) {
            return result.toString();
        }
        eo.set(result, targetPath);
        return "";
    }

    protected Object createReturnType(EO eo, Object result) {
        if (!hasTargetPath()) {
            return result;
        }
        if (isInTemplate()) {
            return new EOToJSON()
                    .setSerializationType(JSONSerializationType.STANDARD)
                    .toJSON(eo.getConfigsCache(), result);
        }
        eo.set(result, targetPath);
        return "";
    }

    protected String createReturnString(EO eo, String result) {
        if (!hasTargetPath() || isInTemplate()) {
            return result;
        }
        eo.set(result, targetPath);
        return "";
    }

    @Override
    public Boolean getInTemplate() {
        return TARGET_AS_STRING.equals(targetPath);
    }

    @Override
    public Boolean isInTemplate() {
        return getInTemplate();
    }

    public void setInTemplate(Boolean inTemplate) {
        if (inTemplate) {
            this.targetPath = TARGET_AS_STRING;
        }
        else {
            this.targetPath = null;
        }
    }

    @Override
    public boolean filter(EO eo) {
        if (!hasCondition()) {
            return true;
        }
        return new Or(new ParserEoReplace(condition).parse(eo)).filter(eo);
    }

    @Override
    public boolean localFilter(EO eo) {
        if (!hasLocalCondition()) {
            return true;
        }
        return new Or(new ParserEoReplace(localCondition).parse(eo)).filter(eo);
    }

    public String prepend() {
        return "";
    }
    public String postPend() {
        return "";
    }

    @Override
    public void setPathByTemplate(final Path templatePath) {
        this.targetPath = templatePath.directory(false);
    }

    public Boolean isOverwrite() {
        return overwrite;
    }

    // $[(TemplateResourceCall)fieldKeys/* templateKey="BEANSetter.tpl" keepCall="JAVA"]
    /**
     A sourcePath where EO offers it's input value when the execution starts.
     */
    @Override
    public final CallImpl setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
        return this;
    }
    @Override
    public final String getSourcePath () {
        return this.sourcePath;
    }
    @Override
    public boolean hasSourcePath () {
        return sourcePath != null  && !sourcePath.isEmpty();
    }

    /**
     A targetPath where the result of the execution will be mapped. Any combination of eo->placeholder. Could be set.
     */
    @Override
    public final CallImpl setTargetPath(String targetPath) {
        this.targetPath = targetPath;
        return this;
    }
    @Override
    public final String getTargetPath () {
        return this.targetPath;
    }
    @Override
    public boolean hasTargetPath () {
        return targetPath != null  && !targetPath.isEmpty();
    }

    /**
     A string representation for a list of model keys.
     */
    @Override
    public final CallImpl setModels(String models) {
        this.models = models;
        return this;
    }
    @Override
    public final String getModels () {
        return this.models;
    }
    @Override
    public boolean hasModels () {
        return models != null  && !models.isEmpty();
    }

    /**
     A condition for calls. 
     */
    @Override
    public final CallImpl setCondition(String condition) {
        this.condition = condition;
        return this;
    }
    @Override
    public final String getCondition () {
        return this.condition;
    }
    @Override
    public boolean hasCondition () {
        return condition != null  && !condition.isEmpty();
    }

    /**
     A condition for calls. 
     */
    @Override
    public final CallImpl setLocalCondition(String localCondition) {
        this.localCondition = localCondition;
        return this;
    }
    @Override
    public final String getLocalCondition () {
        return this.localCondition;
    }
    @Override
    public boolean hasLocalCondition () {
        return localCondition != null  && !localCondition.isEmpty();
    }

    /**
     overwrite
     */
    @Override
    public final CallImpl setOverwrite(Boolean overwrite) {
        this.overwrite = overwrite;
        return this;
    }
    @Override
    public final Boolean getOverwrite () {
        return this.overwrite;
    }
    @Override
    public boolean hasOverwrite () {
        return overwrite != null ;
    }

    /**
     logLevel
     */
    @Override
    public final CallImpl setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
        return this;
    }
    @Override
    public final LogLevel getLogLevel () {
        return this.logLevel;
    }
    @Override
    public boolean hasLogLevel () {
        return logLevel != null ;
    }

    /**
     The duration of a call.
     */
    @Override
    public final CallImpl setDuration(Long duration) {
        this.duration = duration;
        return this;
    }
    @Override
    public final Long getDuration () {
        return this.duration;
    }
    @Override
    public boolean hasDuration () {
        return duration != null ;
    }

//$[/]
}
