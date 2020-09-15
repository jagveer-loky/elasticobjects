package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.calls.ExecutorCallList;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;

import java.util.List;
import java.util.Map;

/**
 * Offers serialized setter and getter for java models
 */

public class EoRoot extends EoChild {
    private final EOConfigsCache eoConfigCache;
    private List<String> roles;

    private boolean checkObjectReplication = false;

    private EoRoot(final EOConfigsCache cache)  {
        this(cache, Map.class);
    }

    public EoRoot(final EOConfigsCache cache, Object value)  {
        super();

        this.eoConfigCache = cache;
        if (value == null) {
            PathElement rootPathElement = new PathElement(cache, Map.class);
            setPathElement(rootPathElement);
        }
        else {
            PathElement rootPathElement = new PathElement(cache, value);
            setPathElement(rootPathElement);
        }
        if (value == null || value instanceof Class) {
            return;
        }
        if (!isScalar()) {
            mapObject(value);
        }
        setLogLevel(LogLevel.WARN);
    }

    public EoRoot(final EOConfigsCache cache, Class... classes)  {
        super();
        this.eoConfigCache = cache;
        PathElement rootPathElement = new PathElement(cache, classes);
        rootPathElement.resolveRoot(this, null);
        setPathElement(rootPathElement);
        setLogLevel(LogLevel.WARN);
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
    public boolean hasRoles() {
        return roles != null && !roles.isEmpty();
    }

    @Override
    public boolean execute() {
        String result = new ExecutorCallList().execute(this) ;
        return true;
    }
}
