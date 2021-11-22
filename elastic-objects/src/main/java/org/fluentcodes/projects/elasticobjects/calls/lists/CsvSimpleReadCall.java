package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.files.CsvConfig;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*.{javaHeader}|*/

/**
 * Defines a primitive csv file read operation. 
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Tue Dec 08 11:16:47 CET 2020
 */
public class CsvSimpleReadCall extends FileReadCall implements ListInterface {
/*.{}.*/

/*.{javaStaticNames}|*/
   public static final String LIST_PARAMS = "listParams";
/*.{}.*/

/*.{javaInstanceVars}|*/
   private  ListParams listParams;
/*.{}.*/
    public CsvSimpleReadCall()  {
        super();
        listParams = new ListParams();
    }
    public CsvSimpleReadCall(final String configKey)  {
        super(configKey);
        listParams = new ListParams();
    }

    @Override
    public Object execute(EO eo) {
        return mapEo(eo, readRaw(eo));
    }

    public List readRaw(final EO eo) {
        CsvConfig config = (CsvConfig) init(PermissionType.READ, eo);
        getListParams().merge(config.getProperties());
        String content = super.read(eo);
        if (content == null|| content.isEmpty()) {
            return new ArrayList<>();
        }
        String[] rows = content.split(config.getRowDelimiter());
        List result = new ArrayList<>();

        if (getListParams().hasRowHead(rows.length)) {
            String header = rows[getListParams().getRowHead()];
            if (header!=null && !header.isEmpty()) {
                String[] fields = header.split(config.getFieldDelimiter());
                if (!getListParams().hasColKeys()) {
                    getListParams().setColKeys(Arrays.asList(fields));
                }
            }
        }
        for (int i=0; i<rows.length;i++) {
            String row = rows[i];
            if (row == null || row.isEmpty()) {
                continue;
            }
            if (!getListParams().isRowStart(i)) {
                continue;
            }
            if (!getListParams().isRowEnd(i)) {
                return result;
            }
            if (row == null|| row.isEmpty()) {
                continue;
            }
            String[] fields = row.split(config.getFieldDelimiter());
            List rowEntry = Arrays.asList(fields);
            getListParams().addRowEntry(eo.getConfigsCache(), result, rowEntry);
        }
        return result;
    }
/*.{javaAccessors}|*/
    /**
    Parameters of type {@link ListParams} for list type read call operations like {@link CsvSimpleReadCall}.
    */
    @Override
    public CsvSimpleReadCall setListParams(ListParams listParams) {
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
