package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.models.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Werner on 10.10.2016.
 */
public interface Config extends Model {
    void resolve() throws Exception;

    String getKey();

    String getModule();

    String getSubModule();

    List<Scope> getScope();//</call>

    EOConfigsCache getConfigsCache();

    String getPath();

    String getMapPath();

    boolean hasScope(final Scope scope);


}
