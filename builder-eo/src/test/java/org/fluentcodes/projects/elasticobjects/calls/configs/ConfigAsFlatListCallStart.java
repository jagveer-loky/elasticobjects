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
                        NATURAL_ID,
                        CONFIG_MODEL_KEY,
                        MODULE,
                        SUB_MODULE,
                        "localFieldKeys",
                        "interfaces",
                        SUPER_KEY,
                        SCOPE,
                        EXPOSE,
                        DESCRIPTION,
                        PACKAGE_PATEH,
                        "modelKey",
                        "eoParams",
                        "dbParams",
                        "viewParams",
                        AUTHOR);
        String result = call.execute(eo);
           XpectString.assertJunit(result);
    }

    @Test
    public void call_configType_FieldConfig__execute__xpected()  {
        final EO eo = ProviderConfigMaps.createEo();
        final ConfigAsFlatListCall call = new ConfigAsFlatListCall()
                .setConfigType(FieldConfig.class.getSimpleName())
                .setFieldKeys(NATURAL_ID,
                        MODULE,
                        SUB_MODULE,
                        "modelKeys",
                        DESCRIPTION,
                        EXPOSE,
                        SCOPE,
                        "fieldKey",
                        "dbFieldParams",
                        "eoFieldParams",
                        "viewFieldParams",
                        AUTHOR);
        String result = call.execute(eo);
       XpectString.assertJunit(result);
    }

    @Test
    public void call_configType_FileConfig__execute__xpected()  {
        final EO eo = ProviderConfigMaps.createEo();
        final ConfigAsFlatListCall call = new ConfigAsFlatListCall()
                .setConfigType(FileConfig.class.getSimpleName())
                .setFieldKeys(NATURAL_ID,
                        CONFIG_MODEL_KEY,
                        MODULE,
                        SUB_MODULE,
                        "hostKey",
                        "filePath",
                        "fileName",
                        DESCRIPTION,
                        SCOPE,
                        EXPOSE,
                        "cached",
                        "listParams",
                        "listMapper",
                        ROLE_PERMISSIONS,
                        AUTHOR);
        String result = call.execute(eo);
       XpectString.assertJunit(result);
    }

    @Test
    public void call_configType_HostConfig__execute__xpected()  {
        final EO eo = ProviderConfigMaps.createEo();
        final ConfigAsFlatListCall call = new ConfigAsFlatListCall()
                .setConfigType(HostConfig.class.getSimpleName())
                .setFieldKeys(
                        NATURAL_ID,
                        CONFIG_MODEL_KEY,
                        MODULE,
                        SUB_MODULE,
                        "protocol",
                        "hostName",
                        DESCRIPTION,
                        "user",
                        "password",
                        SCOPE,
                        EXPOSE,
                        ROLE_PERMISSIONS,
                        "schema",
                        "driver",
                        "dbType",
                        "extension",
                        AUTHOR
                );
        String result = call.execute(eo);
       XpectString.assertJunit(result);
    }

    @Test
    public void call_configType_DbSqlConfig__execute__xpected()  {
        final EO eo = ProviderConfigMaps.createEo();
        final ConfigAsFlatListCall call = new ConfigAsFlatListCall()
                .setConfigType(DbSqlConfig.class.getSimpleName())
                .setFieldKeys(
                        NATURAL_ID,
                        CONFIG_MODEL_KEY,
                        MODULE,
                        SUB_MODULE,
                        DESCRIPTION,
                        SCOPE,
                        EXPOSE,
                        "dbKey",
                        "sql",
                        "sqlList",
                        "modelKey",
                        "listParams",
                        "listMapper",
                        ROLE_PERMISSIONS
                );

        String result = call.execute(eo);
       XpectString.assertJunit(result);
    }
}
