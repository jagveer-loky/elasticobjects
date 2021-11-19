package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.calls.ExecutorCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.Models;

import java.util.List;
import java.util.Map;

/**
 * Offers serialized setter and getter for java models
 */

public class EoRoot extends EoChild {
    private final ConfigMaps eoConfigCache;
    private List<String> roles;

    private boolean checkObjectReplication = false;

    protected EoRoot(ConfigMaps cache, Object rootValue, Models rootModels) {
        super(null, null, rootValue, rootModels);
        if (rootModels.isScalar()) {
            throw new EoException("Root could not be a scalar type but starting value is '" + rootModels.toString() + "'!");
        }
        this.eoConfigCache = cache;
        if (rootModels.getModelClass() != Map.class) {
            new EoChild(this, PathElement.ROOT_MODEL, rootModels.toString(), new Models(cache, String.class));
        }
        if (rootValue != null) {
            mapObject(rootValue);
        }
    }

    public static EoRoot of(final ConfigMaps cache)  {
        return ofClass(cache, Map.class);
    }

    public static EoRoot ofValue(final ConfigMaps cache, Object rootValue)  {
        if (rootValue == null) return ofClass(cache, Map.class);
        if (rootValue instanceof Class)  return ofClass(cache, (Class) rootValue);
        if (rootValue instanceof String) {
            if (JSONToEO.jsonMapPattern.matcher((String)rootValue).find()) {
                return new EoRoot(cache, rootValue, Models.ofValue(cache, Map.class));
            }
            if (JSONToEO.jsonListPattern.matcher((String)rootValue).find()) {
                return new EoRoot(cache, rootValue, Models.ofValue(cache, List.class));
            }
        }
        return new EoRoot(cache, rootValue, Models.ofValue(cache, rootValue));
    }

    public static EoRoot ofClass(final ConfigMaps cache, Class... rootClasses)  {
        return new EoRoot(cache, null, new Models(cache, rootClasses));
    }

    public static EoRoot ofClass(final ConfigMaps cache, final Object rootValue, Class... rootClasses)  {
        Models rootModels  = new Models(cache, rootClasses);
        if (rootModels.isScalar()) {
            throw new EoException("Could not create root with an scalar entry: '" + rootClasses[0].getSimpleName() + "'");
        }
        return new EoRoot(cache, rootValue, rootModels);
    }

    public static Class getClass(Object value) {
        if (value == null) {
            return Map.class;
        }
        return value.getClass();
    }

    @Override
    public ConfigMaps getConfigsCache() {
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
    public boolean execute() {
        String result = ExecutorCall.executeEo(this) ;
        return true;
    }
}
