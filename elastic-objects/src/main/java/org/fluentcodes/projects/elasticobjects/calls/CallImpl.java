package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.calls.condition.Or;
import org.fluentcodes.projects.elasticobjects.calls.templates.Parser;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserSqareBracket;
import org.fluentcodes.projects.elasticobjects.domain.BaseBean;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/
import org.fluentcodes.projects.elasticobjects.LogLevel;
import org.fluentcodes.projects.elasticobjects.calls.templates.KeepCalls;

/**
 * Basic implementation for calls. 
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Tue Dec 08 16:36:56 CET 2020
 */
public abstract class CallImpl extends BaseBean implements Call {
/*=>{}.*/

    /*==>{ALLStaticNames.tpl, fieldBeans/*, super eq false, JAVA|>}|*/
   public static final String CONDITION = "condition";
   public static final String DURATION = "duration";
   public static final String KEEP_CALL = "keepCall";
   public static final String LOG_LEVEL = "logLevel";
   public static final String MODELS = "models";
   public static final String OVERWRITE = "overwrite";
   public static final String POSTPEND = "postpend";
   public static final String PREPEND = "prepend";
   public static final String SOURCE_PATH = "sourcePath";
   public static final String START_CONDITION = "startCondition";
   public static final String TARGET_PATH = "targetPath";
/*=>{}.*/

    /*==>{ALLInstanceVars.tpl, fieldBeans/*, super eq false, JAVA|>}|*/
   private  String condition;
   private  Long duration;
   private  KeepCalls keepCall;
   private  LogLevel logLevel;
   private  String models;
   private  Boolean overwrite;
   private  String postpend;
   private  String prepend;
   private  String sourcePath;
   private  String startCondition;
   private  String targetPath;
/*=>{}.*/

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

    protected boolean init(final EO eo)  {
        return evalCondition(eo);
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
        return new Or(new ParserSqareBracket(getStartCondition()).parse(eo)).filter(eo);
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
        return new Or(Parser.replacePathValues(getCondition(), eo)).filter(eo);
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

    public Boolean isOverwrite() {
        return overwrite;
    }

    /*==>{ALLSetter.tpl, fieldBeans/*, super eq false, JAVA|>}|*/
    /**
    A condition for calls. 
    */
    @Override
    public CallImpl setCondition(String condition) {
        this.condition = condition;
        return this;
    }
    @Override
    public String getCondition () {
       return this.condition;
    }


    /**
    The duration of a call.
    */
    @Override
    public CallImpl setDuration(Long duration) {
        this.duration = duration;
        return this;
    }
    @Override
    public Long getDuration () {
       return this.duration;
    }


    /**
    Will use an existing  result file beforehand as template. 
    */
    @Override
    public CallImpl setKeepCall(KeepCalls keepCall) {
        this.keepCall = keepCall;
        return this;
    }
    @Override
    public KeepCalls getKeepCall () {
       return this.keepCall;
    }


    /**
    logLevel
    */
    @Override
    public CallImpl setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
        return this;
    }
    @Override
    public LogLevel getLogLevel () {
       return this.logLevel;
    }


    /**
    A string representation for a list of model keys.
    */
    @Override
    public CallImpl setModels(String models) {
        this.models = models;
        return this;
    }
    @Override
    public String getModels () {
       return this.models;
    }


    /**
    A overwrite field flag for {@link Call}.
    */
    @Override
    public CallImpl setOverwrite(Boolean overwrite) {
        this.overwrite = overwrite;
        return this;
    }
    @Override
    public Boolean getOverwrite () {
       return this.overwrite;
    }


    /**
    postpend String when executed by ExecutorCallImpl
    */
    @Override
    public CallImpl setPostpend(String postpend) {
        this.postpend = postpend;
        return this;
    }
    @Override
    public String getPostpend () {
       return this.postpend;
    }


    /**
    prepend String when executed by ExecutorCallImpl
    */
    @Override
    public CallImpl setPrepend(String prepend) {
        this.prepend = prepend;
        return this;
    }
    @Override
    public String getPrepend () {
       return this.prepend;
    }


    /**
    A sourcePath where EO offers it's input value when the execution starts.
    */
    @Override
    public CallImpl setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
        return this;
    }
    @Override
    public String getSourcePath () {
       return this.sourcePath;
    }


    /**
    A condition for calls. 
    */
    @Override
    public CallImpl setStartCondition(String startCondition) {
        this.startCondition = startCondition;
        return this;
    }
    @Override
    public String getStartCondition () {
       return this.startCondition;
    }


    /**
    A targetPath where the result of the execution will be mapped. If value is "_asString" no mapping occured but a seralized version is returned as value to embed it in the resulting file. Path parameters could be set dynamically with =&gt;[path]. in any combination.
    */
    @Override
    public CallImpl setTargetPath(String targetPath) {
        this.targetPath = targetPath;
        return this;
    }
    @Override
    public String getTargetPath () {
       return this.targetPath;
    }


/*=>{}.*/
}
