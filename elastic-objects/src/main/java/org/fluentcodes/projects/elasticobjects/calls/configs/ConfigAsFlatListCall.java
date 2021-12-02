package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.commands.SimpleCommand;
import org.fluentcodes.projects.elasticobjects.calls.lists.CsvSimpleWriteCall;

import java.util.Arrays;
import java.util.List;

/*.{javaHeader}|*/

/**
 * Creates a flat list from all loaded configurations for a specific configType.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Tue Dec 08 09:31:09 CET 2020
 */
public class ConfigAsFlatListCall extends CallImpl implements SimpleCommand {
/*.{}.*/

    /*.{javaStaticNames}|*/
   public static final String CONFIG_TYPE = "configType";
   public static final String FIELD_KEYS = "fieldKeys";
/*.{}.*/

    /*.{javaInstanceVars}|*/
   private  String configType;
   private  List<String> fieldKeys;
/*.{}.*/

    public ConfigAsFlatListCall() {
        super();
    }

    public ConfigAsFlatListCall setFieldKeys(String... fieldKeys) {
        this.fieldKeys = Arrays.asList(fieldKeys);
        return this;
    }

    @Override
    public String execute(EO eo)  {
        List<Object> resultAsListMap = (List) new ConfigCall()
                .setConfigType(configType)
                .execute(eo);
        return new CsvSimpleWriteCall()
                .asString(eo, resultAsListMap, fieldKeys);
    }

    /*.{javaAccessors}|*/
    /**
    Key for configuration type like ModelConfig, FileConfig, FieldConfig, HostConfig, DbSqlConfig.
    */

    public ConfigAsFlatListCall setConfigType(String configType) {
        this.configType = configType;
        return this;
    }
    
    public String getConfigType () {
       return this.configType;
    }
    
    public boolean hasConfigType () {
        return configType!= null && !configType.isEmpty();
    }
    /**
    A list of field keys for the model configuration. 
    */

    public ConfigAsFlatListCall setFieldKeys(List<String> fieldKeys) {
        this.fieldKeys = fieldKeys;
        return this;
    }
    
    public List<String> getFieldKeys () {
       return this.fieldKeys;
    }
    
    public boolean hasFieldKeys () {
        return fieldKeys!= null && !fieldKeys.isEmpty();
    }
/*.{}.*/

}
