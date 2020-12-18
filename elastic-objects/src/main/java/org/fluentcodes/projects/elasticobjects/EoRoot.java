package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.calls.ExecutorCall;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.Models;

import java.util.List;
import java.util.Map;

/**
 * Offers serialized setter and getter for java models
 */

public class EoRoot extends EoChild {
    private final EOConfigsCache eoConfigCache;
    private List<String> roles;

    private boolean checkObjectReplication = false;

    protected EoRoot(final EOConfigsCache cache, final EoChildParams params) {
        super(params);
        this.eoConfigCache = cache;
    }

    public static EoRoot OF(final EOConfigsCache cache)  {
        return OF(cache, (Object)null);
    }

    public static EoRoot OF(final EOConfigsCache cache, Object value)  {
        if (value != null &&(value instanceof Class)) {
            return OF_CLASS(cache, (Class) value);
        }
        Models models = new Models(cache, value);
        EoChildParams params = new EoChildParams(models);
        EoRoot root = new EoRoot(cache, params);
        root.setLogLevel(LogLevel.WARN);
        if (value!=null) {
            root.mapObject(value);
        }
        return root;
    }

    public static EoRoot OF_CLASS(final EOConfigsCache cache, Class... classes)  {
        Models models  = new Models(cache, classes);
        EoChildParams params = new EoChildParams(models);
        EoRoot root = new EoRoot(cache, params);
        root.setLogLevel(LogLevel.WARN);
        return root;
    }

    public static Class getClass(Object value) {
        if (value == null) {
            return Map.class;
        }
        return value.getClass();
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
    public List<String> getRoles() {
        return roles;
    }

    @Override
    public void setRoles(final List<String> roles) {
        this.roles = roles;
    }

    @Override
    public EoRoot getRoot() {
        return this;
    }

    @Override
    public boolean isRoot() {
        return true;
    }

    @Override
    public boolean hasRoles() {
        return roles != null && !roles.isEmpty();
    }

    @Override
    public boolean execute() {
        String result = ExecutorCall.executeEo(this) ;
        return true;
    }
}
