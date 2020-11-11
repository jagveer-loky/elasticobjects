package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
/**
 * Defines a primitive csv file read operation. 
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Wed Nov 11 06:10:13 CET 2020
 */
public class CsvSimpleReadCall extends FileReadCall implements ListInterface {
/*=>{}.*/

/*==>{ALLStaticNames.tpl, fieldMap/*, override eq false, JAVA|>}|*/
   public static final String LIST_PARAMS = "listParams";
/*=>{}.*/

/*==>{ALLInstanceVars.tpl, fieldMap/*, , JAVA|>}|*/
   private  ListParams listParams;
/*=>{}.*/
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
/*==>{ALLSetter.tpl, fieldMap/*, , JAVA|>}|*/
    /**
    Filter for the readings within {{@link link} ListAction}.
    */

    public CsvSimpleReadCall setListParams(ListParams listParams) {
        this.listParams = listParams;
        return this;
    }
    
    public ListParams getListParams () {
       return this.listParams;
    }
    
    public boolean hasListParams () {
        return listParams!= null;
    }
/*=>{}.*/
}
