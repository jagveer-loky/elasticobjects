package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.config.*;
import org.fluentcodes.projects.elasticobjects.elasticobjects.EO;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by Werner on 09.10.2016.
 */
public class HQueryCall extends ListCall {
    private static final Logger LOG = LogManager.getLogger(HQueryCall.class);

    private Boolean useAdapterValues = false;

    public HQueryCall(EOConfigsCache provider, String cacheKey)  {
        super(provider, cacheKey);
    }

    public HQueryConfig getHQueryConfig() {
        return (HQueryConfig) getConfig();
    }

    public HConfig getHConfig()  {
        return getHQueryConfig().getHConfig();
    }

    public DbConfig getDbConfig()  {
        return getHConfig().getDbConfig();
    }

    public HostConfig getHostConfig()  {
        return getDbConfig().getHostConfig();
    }

    public ModelInterface getModelConfig()  {
        return ((HQueryConfig) getConfig()).getModelConfig();
    }

    public Boolean isUseAdapterValues() {
        return useAdapterValues;
    }

    public void setUseAdapterValues(Boolean useAdapterValues) {
        this.useAdapterValues = useAdapterValues;
    }

    public void setUseAdapterValuesByObject(Object useAdapterValues) {
        if (useAdapterValues == null) {
            this.useAdapterValues = false;
            return;
        }
        if (useAdapterValues instanceof Boolean) {
            this.useAdapterValues = (Boolean) useAdapterValues;
            return;
        }
        this.useAdapterValues = false;
    }

    public void mapAttributes(Map attributes) {
        if (attributes == null) {
            return;
        }
        super.mapAttributes(attributes);
        this.setUseAdapterValuesByObject(attributes.get("useAdapterValues"));

    }


    private void prepareListParams(EO adapter)  {
        if (!useAdapterValues) {
            return;
        }
        if (adapter.isEmpty()) {
            return;
        }
        if (adapter.isObject()) {
            getListParams().addAnd(adapter);
        } else if (adapter.isMap() || adapter.isList()) {
            for (String key : adapter.keys()) {
                EO subAdapter = adapter.getChild(key);
                if (subAdapter.isEmpty()) {
                    continue;
                }
                getListParams().addAnd(subAdapter);
            }
        }
    }

    public EO read()  {
        throw new Exception("Not implemented!");
    }

    /**
     * The hibernate persistence method for data.
     *
     * @param adapter The adapter where the logic is in.
     * @return
     */
    @Override
    public void write(EO adapter) {
        try {
            if (!super.getRolePermissions().hasPermissions(Permissions.WRITE, adapter.getRoles())) {
                adapter.warn("User has no permissions to write!");
                return;
            }

        } catch (Exception e) {
            adapter.warn("Problem getting user! " + e.getMessage());
            return;
        }

        if (adapter == null || adapter.get() == null) {
            adapter.warn("Empty or null adapter");
            return;
        }
        if (adapter.isList() || adapter.isMap()) {
            Set<String> keys;
            try {
                keys = adapter.keys();
            } catch (Exception e) {
                adapter.warn("Problem getting keys " + e.getMessage());
                return;
            }
            for (String key : keys) {
                try {
                    Object persisted = ((HQueryConfig) getConfig())
                            .getHConfig()
                            .write(getModelConfig(), adapter.get(key));
                    adapter
                            .add(key)
                            .setPathPattern(getPathPattern())
                            .map(persisted);
                } catch (Exception e) {
                    adapter.warn("Problem deleting: " + e.getMessage());
                }
            }
        } else if (adapter.isObject()) {
            try {
                Object object = ((HQueryConfig) getConfig())
                        .getHConfig()
                        .write(getModelConfig(), adapter.get());
                adapter
                        .add()
                        .setPathPattern(getPathPattern())
                        .map(object);
            } catch (Exception e) {
                adapter.warn("Could not write adapter value " + e.getMessage());
            }
        } else {
            adapter.warn("MODULE_NAME neither list, map or object!");
            return;
        }
        return;
    }

    public EO delete(EO adapter)  {
        if (adapter == null) {
            throw new Exception("Not allowed with empty adapter");
        }

        try {
            if (!super.getRolePermissions().hasPermissions(Permissions.DELETE, adapter.getRoles())) {
                adapter.warn("User  has no permissions to write!");
                return adapter;
            }
        } catch (Exception e) {
            adapter.warn("Problem getting user! " + e.getMessage());
            return adapter;
        }
        prepareListParams(adapter);
        List toDelete = getHConfig()
                .read(getModelConfig(), getListParams());
        for (Object dbObject : toDelete) {
            try {
                ((HQueryConfig) getConfig())
                        .getHConfig()
                        .delete(getModelConfig(), dbObject);
            } catch (Exception e) {
                adapter.warn("Problem deleting: " + e.getMessage());
            }
        }
        return adapter;
    }

    public EO deleteAll(EO adapter)  {
        try {
            if (!super.getRolePermissions().hasPermissions(Permissions.DELETE, adapter.getRoles())) {
                adapter.warn("User has no permissions to write!");
                return adapter;
            }
        } catch (Exception e) {
            adapter.warn("Problem getting user! " + e.getMessage());
            return adapter;
        }
        /** ((HQueryConfig) getConfig())
         .getHConfig()
         .executeUpdate("delete from " + getHConfig().getModelKey());*/
        return adapter;
    }


}
