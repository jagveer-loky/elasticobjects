package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.commands.ConfigWriteCommand;
import org.fluentcodes.projects.elasticobjects.calls.db.statements.DeleteStatement;
import org.fluentcodes.projects.elasticobjects.calls.db.statements.FindStatement;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigDbObject;

import java.util.List;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/
/**
 * Remove an entry from database by creating a delete sql from entry in sourcePath.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Wed Nov 11 06:24:57 CET 2020
 */
public class DbModelDeleteCall extends DbModelCall implements ConfigWriteCommand {
/*=>{}.*/

/*==>{ALLStaticNames.tpl, fieldBeans/*, override eq false, JAVA|>}|*/
/*=>{}.*/

/*==>{ALLInstanceVars.tpl, fieldBeans/*, , JAVA|>}|*/
/*=>{}.*/

    public DbModelDeleteCall()  {
        super();
    }
    public DbModelDeleteCall(final String hostConfigKey)  {
        super( hostConfigKey);
    }

    @Override
    public Object execute(EO eo) {
        return remove(eo);
    }
    
    public Object remove(final EO eo) {
        ModelConfigDbObject modelConfig = init(PermissionType.WRITE, eo);
        if (!modelConfig.isObject()) {
            throw new EoException("No model is provided in path '" + eo.getPathAsString() + "");
        }
        if (hasTargetPath()) {
            List result = FindStatement.of(eo)
                    .readFirst(
                            getDbConfig().getConnection(),
                            eo.getConfigsCache());
            eo.set(result, getTargetPath());
        }

        return DeleteStatement.of(eo)
                .execute(getDbConfig().getConnection());
    }
/*==>{ALLSetter.tpl, fieldBeans/*, , JAVA|>}|*/
/*=>{}.*/
}
