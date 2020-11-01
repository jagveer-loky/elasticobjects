package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.db.statements.FindStatement;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListInterface;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParams;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigInterface;

import java.util.List;

/**
 * Reads and writes database tables.
 * Created by werner.diwischek on 28.10.20.
 */
public class DbModelReadCall extends DbModelCall implements ListInterface {
    private ListParams listParams;

    public DbModelReadCall()  {
        super(PermissionType.READ);
        listParams = new ListParams();
    }
    public DbModelReadCall(final String hostConfigKey)  {
        super(PermissionType.READ, hostConfigKey);
        listParams = new ListParams();
    }

    @Override
    public boolean init (EO eo) {
        super.init(eo);
        if (!hasListParams()) {
            this.listParams = new ListParams();
        }
        listParams.initDb();
        return true;
    }

    @Override
    public Object execute(EO eo) {
        init(eo);
        return mapEo(eo, readRaw(eo));
    }
    
    public List readRaw(final EO eo) {
        ModelConfigInterface model = eo.getModel();
        if (!model.isObject()) {
            throw new EoException("No model is provided in path '" + eo.getPathAsString() + "");
        }
        return FindStatement.of(eo)
                .read(
                getDbConfig().getConnection(),
                eo.getConfigsCache(),
                getListParams());
    }

    @Override
    public ListParams getListParams() {
        return listParams;
    }
}
