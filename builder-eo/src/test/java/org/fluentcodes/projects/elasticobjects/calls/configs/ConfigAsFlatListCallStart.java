package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.calls.db.DbSqlConfig;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.tools.xpect.XpectString;
import org.junit.Test;

/**
 * Created 8.9.2020
 */
public class ConfigAsFlatListCallStart  implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return AnObject.class;
    }

    @Override
    @Test
    public void create_noEoException()  {
        assertCreateNoException();
    }

    @Override
    @Test
    public void compareModelConfig()  {
        assertModelConfigEqualsPersisted();
    }

    @Override
    @Test
    public void compareBeanFromModelConfig()  {
        assertBeanFromModelConfigEqualsPersisted();
    }

    @Test
    public void call_configType_ModelConfig__execute__xpected()  {
        final EO eo = ProviderConfigMaps.createEo();
        final ConfigAsFlatListCall call = new ConfigAsFlatListCall()
                .setConfigType(ModelConfig.class.getSimpleName())
                .setFieldKeys(
                        "naturalId",
                        "configModelKey",
                        "module",
                        "subModule",
                        "localFieldKeys",
                        "interfaces",
                        "superKey",
                        "scope",
                        "expose",
                        "description",
                        "packagePath",
                        "modelKey",
                        "eoParams",
                        "dbParams",
                        "viewParams",
                        "author");
        String result = call.execute(eo);
            new XpectString().compareAsString(result);
    }

    @Test
    public void call_configType_FieldConfig__execute__xpected()  {
        final EO eo = ProviderConfigMaps.createEo();
        final ConfigAsFlatListCall call = new ConfigAsFlatListCall()
                .setConfigType(FieldConfig.class.getSimpleName())
                .setFieldKeys("naturalId",
                        "module",
                        "subModule",
                        "modelKeys",
                        "description",
                        "expose",
                        "scope",
                        "fieldKey",
                        "dbFieldParams",
                        "eoFieldParams",
                        "viewFieldParams",
                        "author");
        String result = call.execute(eo);
        new XpectString().compareAsString(result);
    }

    @Test
    public void call_configType_FileConfig__execute__xpected()  {
        final EO eo = ProviderConfigMaps.createEo();
        final ConfigAsFlatListCall call = new ConfigAsFlatListCall()
                .setConfigType(FileConfig.class.getSimpleName())
                .setFieldKeys("naturalId",
                        "configModelKey",
                        "module",
                        "subModule",
                        "hostKey",
                        "filePath",
                        "fileName",
                        "description",
                        "scope",
                        "expose",
                        "cached",
                        "listParams",
                        "listMapper",
                        "rolePermissions",
                        "author");
        String result = call.execute(eo);
        new XpectString().compareAsString(result);
    }

    @Test
    public void call_configType_HostConfig__execute__xpected()  {
        final EO eo = ProviderConfigMaps.createEo();
        final ConfigAsFlatListCall call = new ConfigAsFlatListCall()
                .setConfigType(HostConfig.class.getSimpleName())
                .setFieldKeys(
                        "naturalId",
                        "configModelKey",
                        "module",
                        "subModule",
                        "protocol",
                        "hostName",
                        "description",
                        "user",
                        "password",
                        "scope",
                        "expose",
                        "rolePermissions",
                        "schema",
                        "driver",
                        "dbType",
                        "extension",
                        "author"
                );
        String result = call.execute(eo);
        new XpectString().compareAsString(result);
    }

    @Test
    public void call_configType_DbSqlConfig__execute__xpected()  {
        final EO eo = ProviderConfigMaps.createEo();
        final ConfigAsFlatListCall call = new ConfigAsFlatListCall()
                .setConfigType(DbSqlConfig.class.getSimpleName())
                .setFieldKeys(
                        "naturalId",
                        "configModelKey",
                        "module",
                        "subModule",
                        "description",
                        "scope",
                        "expose",
                        "dbKey",
                        "sql",
                        "sqlList",
                        "modelKey",
                        "listParams",
                        "listMapper",
                        "rolePermissions"
                );

        String result = call.execute(eo);
        new XpectString().compareAsString(result);
    }
}
