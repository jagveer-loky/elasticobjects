package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.commands.ConfigReadCommand;
import org.fluentcodes.projects.elasticobjects.calls.db.statements.FindStatement;
import org.fluentcodes.projects.elasticobjects.calls.lists.CsvSimpleReadCall;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListInterface;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParams;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigDbObject;

import java.util.List;

/*.{javaHeader}|*/
/**
 * Read an entry in database by creating a select sql from entry in sourcePath. The object must be an instance of {@link ModelConfigDbObject}.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Wed Nov 11 06:39:50 CET 2020
 */
public class DbModelReadCall extends DbModelCall implements ListInterface,  ConfigReadCommand {
/*.{}.*/

/*.{javaStaticNames}|*/
   public static final String LIST_PARAMS = "listParams";
/*.{}.*/

/*.{javaInstanceVars}|*/
   private ListParams listParams;
/*.{}.*/

    public DbModelReadCall()  {
        super();
        listParams = new ListParams();
    }
    public DbModelReadCall(final String hostConfigKey)  {
        super(hostConfigKey);
        listParams = new ListParams();
    }




    @Override
    public Object execute(EO eo) {
        return mapEo(eo, readRaw(eo));
    }
    
    public List readRaw(final EO eo) {
        ModelConfigDbObject modelConfig = init(PermissionType.READ, eo);
        listParams.initDb();
        return FindStatement.of(eo)
                .read(
                getDbConfig().getConnection(),
                eo.getConfigsCache(),
                getListParams());
    }

/*.{javaAccessors}|*/
    /**
    Parameters of type {@link ListParams} for list type read call operations like {@link CsvSimpleReadCall}.
    */
    @Override
    public DbModelReadCall setListParams(ListParams listParams) {
        this.listParams = listParams;
        return this;
    }

    @Override
    public ListParams getListParams () {
       return this.listParams;
    }
    @Override
    public boolean hasListParams () {
        return listParams!= null;
    }
/*.{}.*/
}
