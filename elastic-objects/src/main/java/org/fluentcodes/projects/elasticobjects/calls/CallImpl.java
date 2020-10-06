package org.fluentcodes.projects.elasticobjects.calls;
// $[(TemplateResourceCall)javaGenImport/* configKey="ALLImport.tpl" keepCall="JAVA" ]
import org.fluentcodes.projects.elasticobjects.LogLevel;
//$[/]
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.calls.condition.Or;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserEoReplace;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.domain.BaseImpl;

/**
 * Created by Werner on 13.07.2020.
 * Elementary call with mapping configuration keys to configuration via constructor.
 */
public abstract class CallImpl extends BaseImpl implements Call {

    // $[(TemplateResourceCall)fieldKeys/* configKey="ALLStaticNames.java" keepCall="JAVA"]
//$[/]

// $[(TemplateResourceCall)fieldKeys/* configKey="ALLInstanceVars.tpl" keepCall="JAVA"]
    private String sourcePath;
    private String targetPath;
    private String models;
    private String startCondition;
    private String condition;
    private Boolean overwrite;
    private LogLevel logLevel;
    private Long duration;
    private String prepend;
    private String postpend;
//$[/]

    public CallImpl() {
        prepend = "";
        postpend = "";
    }

    public void check(final EO eo) {
        if (eo == null) {
            throw new EoException("Null eo value");
        }
        if (eo.get() == null) {
            throw new EoException("empty eo value");
        }
    }



    /**
     * Evaluates the startCondition field  at the beginning of ExecutorCall.
     * @param eo the current wrapper in the loop.
     * @return
     */
    protected boolean evalStartCondition(EO eo) {
        if (!hasStartCondition()) {
            return true;
        }
        return new Or(new ParserEoReplace(getStartCondition()).parse(eo)).filter(eo);
    }

    /**
     * Evaluates the condition field within ExecutorCall.
     * @param eo the current wrapper in the loop.
     * @return
     */
    protected boolean evalCondition(EO eo) {
        if (!hasCondition()) {
            return true;
        }
        return new Or(new ParserEoReplace(getCondition()).parse(eo)).filter(eo);
    }

    protected Object createReturnScalar(EO eo, Object result) {
        if (!hasTargetPath()) {
            return result;
        }
        if (isTargetAsString()) {
            return result.toString();
        }
        eo.set(result, targetPath);
        return "";
    }

    protected Object createReturnType(EO eo, Object result) {
        if (!hasTargetPath()) {
            return result;
        }
        if (isTargetAsString()) {
            return new EOToJSON()
                    .setSerializationType(JSONSerializationType.STANDARD)
                    .toJSON(eo.getConfigsCache(), result);
        }
        eo.set(result, targetPath);
        return "";
    }

    protected String createReturnString(EO eo, String result) {
        if (!hasTargetPath() || isTargetAsString()) {
            return result;
        }
        eo.set(result, targetPath);
        return "";
    }

    @Override
    public void setPathByTemplate(final Path templatePath) {
        this.targetPath = templatePath.directory(false);
    }

    public Boolean isOverwrite() {
        return overwrite;
    }

    // $[(TemplateResourceCall)fieldKeys/* configKey="AllSetter.tpl" keepCall="JAVA"]
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
    public final CallImpl setStartCondition(String startCondition) {
        this.startCondition = startCondition;
        return this;
    }
    @Override
    public final String getStartCondition () {
        return this.startCondition;
    }
    @Override
    public boolean hasStartCondition () {
        return startCondition != null  && !startCondition.isEmpty();
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

    /**
     prepend String when executed by ExecutorCallImpl
     */

    @Override
    public final CallImpl setPrepend(String prepend) {
        this.prepend = prepend;
        return this;
    }
    @Override
    public final String getPrepend () {
        return this.prepend;
    }
    @Override
    public boolean hasPrepend () {
        return prepend != null  && !prepend.isEmpty();
    }

    /**
     postpend String when executed by ExecutorCallImpl
     */

    @Override
    public final CallImpl setPostpend(String postpend) {
        this.postpend = postpend;
        return this;
    }
    @Override
    public final String getPostpend () {
        return this.postpend;
    }
    @Override
    public boolean hasPostpend () {
        return postpend != null  && !postpend.isEmpty();
    }

//$[/]
}
