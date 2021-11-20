package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.commands.ConfigReadCommand;
import org.fluentcodes.projects.elasticobjects.calls.db.statements.FindStatement;
import org.fluentcodes.projects.elasticobjects.calls.lists.CsvSimpleReadCall;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListInterface;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParams;
import org.fluentcodes.projects.elasticobjects.calls.templates.KeepCalls;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.List;

/*.{javaHeader}|*/
/**
 * Map results of a sql select to the targetPath.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Wed Nov 11 07:20:13 CET 2020
 */
public class DbSqlReadCall extends DbSqlCall implements ListInterface,  ConfigReadCommand {
/*.{}.*/

/*.{javaStaticNames}|*/
   public static String LIST_PARAMS = "listParams";
/*.{}.*/

/*.{javaInstanceVars}|*/
   private ListParams listParams;
/*.{}.*/

    public DbSqlReadCall()  {
        super();
        listParams = new ListParams();
    }
    public DbSqlReadCall(final String hostConfigKey)  {
        super(hostConfigKey);
        listParams = new ListParams();
    }
    public DbSqlReadCall(final String hostConfigKey, final String sqlConfigKey)  {
        super(hostConfigKey, sqlConfigKey);
        listParams = new ListParams();
    }

    @Override
    public Object execute(EO eo) {
        return mapEo(eo, readRaw(eo));
    }

    @Override
    public void setByParameter(final String values) {
        if (values == null||values.isEmpty()) {
            throw new EoException("Set by empty input values");
        }
        String[] array = values.split(", ");
        if (array.length>5) {
            throw new EoException("Short form should have form '<configKey>[,<targetPath>][,<condition>][,<keepCall>]' with max length 3 but has size " + array.length + ": '" + values + "'." );
        }
        if (array.length>0) {
            setHostConfigKey(array[0]);
        }
        if (array.length>1) {
            setSqlKey(array[1]);
        }
        if (array.length>2) {
            setTargetPath( array[2]);
        }
        if (array.length>3) {
            setCondition( array[3]);
        }
        if (array.length>4) {
            setKeepCall(KeepCalls.valueOf(array[4]));
        }
    }

    public List readRaw(final EO eo) {
        DbSqlConfig config = init(PermissionType.READ, eo);
        listParams.initDb();
        return new FindStatement(config.getSql(), eo)
                .read(
                getConnection(),
                eo.getConfigsCache(),
                listParams);
    }

/*.{javaAccessors}|*/
    /**
    Parameters of type {@link ListParams} for list type read call operations like {@link CsvSimpleReadCall}.
    */
    @Override
    public DbSqlReadCall setListParams(ListParams listParams) {
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
