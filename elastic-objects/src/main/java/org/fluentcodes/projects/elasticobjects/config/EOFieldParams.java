package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.paths.PathPattern;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_PATH_PATTERN;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_PROVIDE_IN_ACTION;

/**
 * Created by werner.diwischek on 19.02.18.
 */
public class EOFieldParams {
    private EOConfigsCache configsCache;
    private Map attributes;
    private PathPattern pathPattern;
    private boolean provideInAction;

    public EOFieldParams(EOConfigsCache configsCache, Object object)  {

        if (object == null || !(object instanceof Map) || ((Map) object).isEmpty()) {
            //this.pathPattern = new PathPattern("/*");
            this.provideInAction = false;
            this.attributes = null;
            return;
        }
        this.configsCache = configsCache;
        this.attributes = (Map) object;
    }

    public PathPattern getPathPattern()  {
        resolve();
        return pathPattern;
    }

    public boolean hasPathPattern()  {
        resolve();
        return pathPattern != null && pathPattern.size() > 0;
    }

    public boolean isFilterNothing()  {
        resolve();
        return !hasPathPattern() || pathPattern.isFilterNothing();
    }

    public String getPathPatternAsString()  {
        resolve();
        if (pathPattern == null) {
            return Path.DELIMITER + Path.MATCHER;
        }
        return pathPattern.getSerialized();
    }

    public Boolean isDeliverAction()  {
        resolve();
        return provideInAction;
    }

    private void resolve()  {
        if (attributes == null) {
            return;
        }
        this.pathPattern = (PathPattern) configsCache.transform(
                F_PATH_PATTERN,
                attributes.get(F_PATH_PATTERN));

        this.provideInAction = (Boolean) configsCache.transform(
                F_PROVIDE_IN_ACTION,
                attributes.get(F_PROVIDE_IN_ACTION),
                false);
        this.attributes = null;
    }
}
