package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.calls.ExecutorCallList;
import org.fluentcodes.projects.elasticobjects.models.Models;
import org.fluentcodes.projects.elasticobjects.paths.PathElement;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Offers serialized setter and getter for java models
 */

public class EoRoot extends EoChild {
    private final EOConfigsCache eoConfigCache;
    private List<String> roles;

    private boolean eoCallEmpty = true;
    private boolean checkObjectReplication = false;
    private EO callsEo;
    private List<Call> calls;
    private EoRoot(final EOConfigsCache cache)  {
        super();
        this.eoConfigCache = cache;
    }

    public static EoRoot ofMap(final EOConfigsCache cache) {
        return new EoRoot(cache, Map.class);
    }

    public static EoRoot ofValue(final EOConfigsCache cache, Object value) {
        EoRoot root =  new EoRoot(cache, Models.getValueClass(value));
        root.mapObject(value);
        return root;
    }

    public EoRoot(final EOConfigsCache cache, Class... classes)  {
        this(cache, LogLevel.DEBUG, classes);
    }

    public EoRoot(final EOConfigsCache cache, final LogLevel logLevel, Class<?>... classes)  {
        this(cache);
        setRootModel(classes);
    }

    private void setRootModel(Class... classes) {
        Models models = new Models(this.eoConfigCache, classes);
        super.setModels(models);
        if (models.isDefaultMap()) {
            return;
        }
        super.set(models.toString(), PathElement.ROOT_MODEL);
    }

    @Override
    public boolean isRoot() {
        return true;
    }
    @Override
    public EOConfigsCache getConfigsCache() {
        return eoConfigCache;
    }
    @Override
    public boolean isCheckObjectReplication() {
        return this.checkObjectReplication;
    }
    @Override
    public void setCheckObjectReplication(boolean checkObjectReplication) {
        this.checkObjectReplication = checkObjectReplication;
    }

    @Override
    public EoRoot getRoot() {
        return this;
    }

    @Override
    public List<String> getRoles() {
        return roles;
    }

    @Override
    public void setRoles(final List<String> roles) {
        this.roles = roles;
    }


    @Override
    public boolean hasRoles() {
        return roles != null && !roles.isEmpty();
    }

    @Override
    public JSONSerializationType getSerializationType() {
        if (!hasEo(PathElement.OF_SERIALIZATION_TYPE())) {
            return JSONSerializationType.EO;
        }

        if (get(PathElement.SERIALIZATION_TYPE) instanceof JSONSerializationType) {
            return (JSONSerializationType) get(PathElement.SERIALIZATION_TYPE);
        }
        else if (get(PathElement.SERIALIZATION_TYPE) instanceof String) {
            try {
                return JSONSerializationType.valueOf((String) get(PathElement.SERIALIZATION_TYPE));
            }
            catch (Exception e) {
                throw new EoException(e);
            }
        }
        else {
            throw new EoException("Mismatch in JSONSerializationType " + getPathAsString() + ": " + get(PathElement.SERIALIZATION_TYPE));
        }

    }

    @Override
    public EO setSerializationType(JSONSerializationType serializationType) {
        set(serializationType, PathElement.SERIALIZATION_TYPE);
        return this;
    }

    @Override
    public LogLevel getErrorLevel() {
        if (!hasEo(PathElement.OF_ERROR_LEVEL())) {
            set(LogLevel.DEBUG, PathElement.ERROR_LEVEL);
        }
        return (LogLevel) get(PathElement.ERROR_LEVEL);
    }

    private void setErrorLevel(LogLevel messageLogLevel) {
        if (getErrorLevel().ordinal() <= messageLogLevel.ordinal()) {
            set(messageLogLevel, PathElement.ERROR_LEVEL);
        }
    }

    protected void log(String message, LogLevel messageLogLevel) {
        if (message == null) {
            return;
        }
        setErrorLevel(messageLogLevel);
        getLogEo().set(messageLogLevel.name() + " - " + LocalDateTime.now().toString() + " - " + message, Integer.valueOf(getLogSize()).toString());
    }

    private int getLogSize() {
        return getLogEo().sizeEo();
    }

    private EO getLogEo() {
        if (!hasEo(PathElement.OF_LOGS())) {
            setEmpty("(List,String)" + PathElement.LOGS);
        }
        return getEo(PathElement.LOGS);
    }

    @Override
    public String getLog() {
        if (!hasEo(PathElement.OF_LOGS())) {
            return "";
        }
        List<String> logs = (List<String>)getLogEo().get();
        if (logs == null|| logs.isEmpty()) {
            return "";
        }
        return logs.stream().collect(Collectors.joining());
    }

    @Override
    public boolean hasErrors() {
        return getErrorLevel() == LogLevel.ERROR;
    }

    @Override
    public boolean addCall(Call call) {
        if (call == null) {
            return false;
        }
        initCalls();
        callsEo.set(call, Integer.valueOf(callsEo.sizeEo()).toString());
        return true;
    }

    protected boolean addCall(EO callEo) {
        if (callEo == null) {
            return false;
        }
        initCalls();
        String counterKey = Integer.valueOf(callsEo.sizeEo()).toString();
        //callsEo.set(call, Integer.valueOf(callsEo.sizeEo()).toString());
        ((EoChild)callsEo).setEo(new PathElement(counterKey), callEo);
        return true;
    }

    private void initCalls() {
        if (callsEo != null) {
            return;
        }
        if (!this.hasEo(PathElement.OF_CALLS())) {
            this.setEmpty("(List)" + PathElement.CALLS);
        }
        callsEo = getEo(PathElement.CALLS);
        calls = (List<Call>) callsEo.get();
    }

    @Override
    public List<Call> getCalls() {
        initCalls();
        return calls;
    }

    @Override
    public EO getCallsEo() {
        initCalls();
        return callsEo;
    }

    @Override
    public Call getLastCall() {
        initCalls();
        if (!hasCalls()) {
            return null;
        }
        return calls.get(calls.size()-1);
    }

    @Override
    public boolean hasCalls() {
        return calls != null && !calls.isEmpty();
    }

    @Override
    public boolean execute() {
        String result = new ExecutorCallList().execute(this) ;
        return true;
    }
}
