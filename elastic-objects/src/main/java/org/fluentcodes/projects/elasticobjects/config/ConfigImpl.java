package org.fluentcodes.projects.elasticobjects.config;


import org.fluentcodes.projects.elasticobjects.eo.EOToJSON;
import org.fluentcodes.projects.elasticobjects.models.ModelImpl;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

/**
 * Created by Werner on 10.10.2016.
 */
public abstract class ConfigImpl extends ModelImpl implements Config {
    private final EOConfigsCache provider;
    private final String module;
    //<call keep="JAVA" templateKey="CacheInstanceVars.tpl">
    private final String subModule;
    private final String path;
    private final String mapPath;
    private final List<Scope> scope;
    private boolean resolved = false;

    public ConfigImpl(EOConfigsCache provider, Builder builder) {
        //<call keep="JAVA" templateKey="CacheSetter.tpl">

        this.module = builder.module;
        this.subModule = builder.subModule;
        this.path = builder.path;
        this.mapPath = builder.mapPath;
        this.scope = builder.scope;//</call>
        this.provider = provider;

        super.setId(builder.id);
        super.setNaturalId(builder.naturalId);
        super.setDescription(builder.description);
        super.setCreationDate(builder.creationDate);
        super.setModificationDate(new Date());

    }
//</call>

    protected boolean isResolved() {
        return this.resolved;
    }

    protected void setResolved() {
        this.resolved = true;
    }

    public void resolve() throws Exception {
        this.resolved = true;
    }

    /**
     * Defines a target module where generating occurs.
     */
    public String getModule() {
        return this.module;
    }
    //<call keep="JAVA" templateKey="CacheGetter.tpl">

    /**
     * Defines the target  sub module like 'main' or 'test'.
     */
    public String getSubModule() {
        return this.subModule;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getMapPath() {
        return mapPath;
    }

    /**
     * A scope for the config value.
     */
    public List<Scope> getScope() {
        return this.scope;
    }

    public boolean hasScope(final Scope scope) {
        if (scope == Scope.ALL) {
            return true;
        }
        if (this.scope == null) {
            return true;
        } else if (this.scope.isEmpty()) {
            return true;
        }
        if (this.scope.contains(scope)) {
            return true;
        }
        return false;
    }

    public EOConfigsCache getConfigsCache() {
        return provider;
    }
//</call>

    @Override
    public String toString() {
        if (this == null) {
            return "{}";
        }
        try {
            return new EOToJSON().toJSON(getConfigsCache(), this);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String serialize() {
        if (this == null) {
            return "{}";
        }
        try {
            return new EOToJSON().setStartIndent(1).toJSON(getConfigsCache(), this);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public static class Builder {
        //<call keep="JAVA" templateKey="BeanInstanceVars.tpl">
        private String module;
        private String subModule;
        private String path;
        private String mapPath;
        private List<Scope> scope;
        private String description;
        private String naturalId;
        private Long id;
        private Date creationDate;
        private boolean expanded = false;

        public Builder() {
            super();
        }

//{/insert

        protected void prepare(EOConfigsCache configsCache, Map<String, Object> values) throws Exception {
            this.module = ScalarConverter.toString(values.get(F_MODULE));
            this.subModule = ScalarConverter.toString(values.get(F_SUB_MODULE));
            this.path = ScalarConverter.toString(values.get(F_PATH));
            this.mapPath = ScalarConverter.toString(values.get(F_MAP_PATH));
            this.naturalId = ScalarConverter.toString(values.get(F_NATURAL_ID));
            this.description = ScalarConverter.toString(values.get(F_DESCRIPTION));
            this.creationDate = ScalarConverter.toDate(values.get(F_CREATION_DATE));
            this.id = ScalarConverter.toLong(values.get(F_ID));
            Object scopeAsObject = values.get(F_SCOPE);
            this.scope = new ArrayList<Scope>();
            if (scopeAsObject != null && scopeAsObject instanceof List) {
                List scopeList = (List) scopeAsObject;
                for (Object scopeObject : scopeList) {
                    String scope = ScalarConverter.toString(scopeObject);
                    if (scope == null || scope.isEmpty()) {
                        continue;
                    }
                    this.scope.add(Scope.valueOf(scope));
                }
            }
            this.expanded = ScalarConverter.toBoolean(values.get(F_EXPANDED));
        }


        protected boolean isExpanded() {
            return expanded;
        }
    }

}
