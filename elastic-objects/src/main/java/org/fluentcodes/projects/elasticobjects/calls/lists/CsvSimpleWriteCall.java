package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.files.CsvConfig;
import org.fluentcodes.projects.elasticobjects.calls.files.FileWriteCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.List;

/*.{javaHeader}|*/

/**
 * Defines a primitive csv file write operation. 
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Tue Dec 08 11:26:51 CET 2020
 */
public class CsvSimpleWriteCall extends FileWriteCall implements ListInterface {
/*.{}.*/

/*.{javaStaticNames}|*/
   public static final String LIST_PARAMS = "listParams";
/*.{}.*/

/*.{javaInstanceVars}|*/
   private  ListParams listParams;
/*.{}.*/

    public CsvSimpleWriteCall()  {
        super();
        listParams = new ListParams();
    }

    @Override
    public String execute(EO eo) {
        return write(eo);
    }

    @Override
    public String write(EO eo)  {
        CsvConfig config = (CsvConfig) init(PermissionType.READ, eo);
        getListParams().merge(config.getProperties());
        List rows = (List)eo.get();
        if (rows == null || rows.isEmpty()) {
            throw new EoException("Strange - no list values - nothing to write! Will return without doing anything.");
        }
        StringBuilder buffer = new StringBuilder();
        for (Object row : rows) {
            if (row == null) {
                buffer.append(config.getRowDelimiter());
                continue;
            }
            if (!(row instanceof List)) {
                buffer.append(config.getRowDelimiter());
                continue;
            }
            List rowList = (List) row;
            if (rowList.isEmpty()) {
                buffer.append(config.getRowDelimiter());
                continue;
            }
            for (int i = 0; i < rowList.size(); i++) {
                Object entry = rowList.get(i);
                if (entry == null) {
                    buffer.append(config.getFieldDelimiter());
                }
                buffer.append(entry.toString());
                if (i + 1 < rowList.size()) {
                    buffer.append(config.getFieldDelimiter());
                }
            }
            buffer.append(config.getRowDelimiter());
        }
        setContent(buffer.toString());
        return super.execute(eo);
    }
/*.{javaAccessors}|*/
    /**
    Parameters of type {@link ListParams} for list type read call operations like {@link CsvSimpleReadCall}.
    */
@Override
    public CsvSimpleWriteCall setListParams(ListParams listParams) {
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
