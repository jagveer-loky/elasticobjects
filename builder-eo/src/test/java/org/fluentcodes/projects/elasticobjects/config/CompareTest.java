package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.calls.XlsxCall;
import org.fluentcodes.projects.elasticobjects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.JSONReader;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class CompareTest {
    @Test
    public void compareElasticObjectsFieldsMain()  {
        compare("models.xlsx", "Field", "elastic-objects", "main");
    }

    @Test
    public void compareElasticObjectsFieldsTest()  {
        compare("models.xlsx", "Field", "elastic-objects", "test");
    }

    @Test
    public void compareActionsXlsxFieldsMain()  {
        compare("models.xlsx", "Field", "actions-xlsx", "main");
    }

    /**
     * Models
     */

    @Test
    public void compareElasticObjectsModelMain()  {
        compare("models.xlsx", "Model", "elastic-objects", "main");
    }

    @Test
    public void compareElasticObjectsModelTest()  {
        compare("models.xlsx", "Model", "elastic-objects", "test");
    }

    @Test
    public void compareActionsXlsModelMain()  {
        compare("models.xlsx", "Model", "actions-xlsx", "main");
    }

    /**
     * Users
     */

    @Test
    public void compareElasticObjectsUserMain()  {
        compare("models.xlsx", "User", "elastic-objects", "main");
    }

    @Test
    public void compareElasticObjectsUserTest()  {
        compare("models.xlsx", "User", "elastic-objects", "test");
    }


    /**
     * Roles
     */

    @Test
    public void compareElasticObjectsRoleMain()  {
        compare("models.xlsx", "Role", "elastic-objects", "main");
    }

    @Test
    public void compareElasticObjectsRoleTest()  {
        compare("models.xlsx", "Role", "elastic-objects", "test");
    }

    /**
     * Values
     */
    @Test
    public void compareElasticObjectsValueTest()  {
        compare("actions.xlsx", "Value", "elastic-objects", "test");
    }

    // Not used in main.
    @Ignore
    @Test
    public void compareElasticObjectsValueMain()  {
        compare("actions.xlsx", "Value", "elastic-objects", "main");
    }

    /**
     * Host
     */
    @Test
    public void compareElasticObjectsHostTest()  {
        compare("actions.xlsx", "Host", "elastic-objects", "test");
    }

    @Test
    public void compareElasticObjectsHostMain()  {
        compare("actions.xlsx", "Host", "elastic-objects", "main");
    }

    /**
     * Files
     */
    @Test
    public void compareElasticObjectsFileTest()  {
        compare("actions.xlsx", "File", "elastic-objects", "test");
    }

    // No config file in main!
    @Ignore
    @Test
    public void compareElasticObjectsFileMain()  {
        compare("actions.xlsx", "File", "elastic-objects", "main");
    }

    /**
     * Templates
     */
    @Test
    public void compareElasticObjectsTemplateTest()  {
        compare("actions.xlsx", "Template", "elastic-objects", "test");
    }

    @Test
    public void compareElasticObjectsTemplateMain()  {
        compare("actions.xlsx", "Template", "elastic-objects", "main");
    }

    /**
     * Jsons
     */
    @Test
    public void compareElasticObjectsJsonTest()  {
        compare("actions.xlsx", "Json", "elastic-objects", "test");
    }

    @Test
    public void compareElasticObjectsJsonMain()  {
        compare("actions.xlsx", "Json", "elastic-objects", "main");
    }


    public void compare(final String xlsxFile, final String config, final String module, final String subModule)  {
        EO adapter = TestObjectProvider.createEOFromJson();

        Map map = JSONReader.readMapBean(TestObjectProvider.EO_CONFIGS_CACHE, "../" + module + "/src/" + subModule + "/resources/" + config + "Config.json", null);
        adapter.add("json").map(map);

        XlsxCall xlsxAction = new XlsxCall(TestObjectProvider.EO_CONFIGS_CACHE, xlsxFile + ":" + config);
        xlsxAction.setMapPath("$[naturalId]");
        xlsxAction.setPath("xlsx");
        Map filter = new HashMap<>();
        filter.put("module", module);
        filter.put("subModule", subModule);
        xlsxAction.setFilter("0 eq " + module + " && 1 eq " + subModule);
        xlsxAction.read(adapter, filter);
        Assert.assertNotNull(adapter.getChild("json"));
        Assert.assertNotNull(adapter.getChild("xlsx"));

        EO json = adapter.getChild("json");
        EO xlsx = adapter.getChild("xlsx");
        StringBuilder builder = new StringBuilder();
        json.compare(builder, xlsx);
        //System.out.print(builder.toString());
        builder.append("\n-------------------------------\n");
        xlsx.compare(builder, json);
        if (builder.toString().contains("!=")) {
            System.out.print(builder.toString());
        } else {
            System.out.print("Everything synchronized!");
        }

    }
}
