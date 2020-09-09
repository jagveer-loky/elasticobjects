package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.tools.xpect.XpectString;
import org.junit.Test;

/**
 * Created 8.9.2020
 */
public class ConfigAsFlatListCallTest {

    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(ConfigAsFlatListCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(ConfigAsFlatListCall.class);
    }


    @Test
    public void call_configType_ModelConfig__execute_xpected()  {
        final EO eo = ProviderRootTestScope.createEo();
        final ConfigAsFlatListCall call = new ConfigAsFlatListCall()
                .setConfigType(ModelConfig.class.getSimpleName())
                .setKeys("naturalId","module","subModule","fieldKeys","interfaces","superKey","scope","expose","description","packagePath","modelKey","eoParams","dbParams","viewParams","author");
        String result = call.execute(eo);
        new XpectString().compareAsString(result);
    }

    @Test
    public void call_configType_FieldConfig__execute_xpected()  {
        final EO eo = ProviderRootTestScope.createEo();
        final ConfigAsFlatListCall call = new ConfigAsFlatListCall()
                .setConfigType(FieldConfig.class.getSimpleName())
                .setKeys("naturalId","modelKeys","description","fieldKey","module","subModule","scope","dbFieldParams","eoFieldParams","viewFieldParams");
        String result = call.execute(eo);
        new XpectString().compareAsString(result);
    }

    @Test
    public void call_configType_FileConfig__execute_xpected()  {
        final EO eo = ProviderRootTestScope.createEo();
        final ConfigAsFlatListCall call = new ConfigAsFlatListCall()
                .setConfigType(FileConfig.class.getSimpleName())
                .setKeys("naturalId","module","subModule","hostKey","filePath","fileName","description","scope","expose","cached","rolePermissions");
        String result = call.execute(eo);
        new XpectString().compareAsString(result);
    }
}
