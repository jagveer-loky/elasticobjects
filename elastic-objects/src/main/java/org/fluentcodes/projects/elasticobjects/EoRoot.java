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
    private List<String> roles;

    private boolean checkObjectReplication = false;

    protected EoRoot(Object rootValue, Models rootModels) {
        super();
        setModels(rootModels);
        if (rootModels.isScalar()) {
            throw new EoException("Root could not be a scalar type but starting value is '" + rootModels.toString() + "'!");
        }
        if (rootModels.getModelClass() != Map.class) {
            new EoChild(this, PathElement.ROOT_MODEL, rootModels.toString(), new Models(rootModels.getConfigMaps(), String.class));
        }
        set(rootModels.create());
        mapObject(rootValue);
    }

    public static EoRoot of(final ConfigMaps cache)  {
        return ofClass(cache, Map.class);
    }

    public static EoRoot ofValue(final ConfigMaps cache, Object rootValue)  {
        if (rootValue == null) return ofClass(cache, Map.class);
        if (rootValue instanceof Class)  return ofClass(cache, (Class) rootValue);
        return new EoRoot(rootValue, Models.ofValue(cache, rootValue));
    }

    public static EoRoot ofClass(final ConfigMaps cache, Class... rootClasses)  {
        Models models = new Models(cache, rootClasses);
        if (!models.isCreate()) {
            throw new EoException("Could not create value from " + models.toString());
        }
        Object value = models.create();
        return new EoRoot(value, new Models(cache, rootClasses));
    }

    public static EoRoot ofClass(final ConfigMaps cache, final Object rootValue, Class... rootClasses)  {
        Models rootModels  = new Models(cache, rootClasses);
        if (rootModels.isScalar()) {
            throw new EoException("Could not create root with an scalar entry: '" + rootClasses[0].getSimpleName() + "'");
        }
        return new EoRoot(rootValue, rootModels);
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
