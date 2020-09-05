package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.calls.XlsxCall;
import org.fluentcodes.projects.elasticobjects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.JSONReader;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

public class CompareConfigs {

    public void compare(final String xlsxFile, final String config, final String module, final String subModule)  {
        EO adapter = TestObjectProvider.createEOFromJson();

        Map map = JSONReader.readMapBean(TestObjectProvider.EO_CONFIGS_CACHE, "../" + module + "/src/" + subModule + "/resources/" + config + "Config.json", null);
        adapter.add("json").map(map);

        XlsxCall xlsxCall = new XlsxCall(TestObjectProvider.EO_CONFIGS_CACHE, xlsxFile + ":" + config);
        xlsxCall.setMapPath("$[naturalId]");
        xlsxCall.setPath("xlsx");
        Map filter = new HashMap<>();
        filter.put("module", module);
        filter.put("subModule", subModule);
        xlsxCall.setFilter("0 eq " + module + " && 1 eq " + subModule);
        xlsxCall.read(adapter, filter);
        Assert.assertNotNull(adapter.getChild("json"));
        Assert.assertNotNull(adapter.getChild("xlsx"));

        EO json = adapter.getChild("json");
        EO xlsx = adapter.getChild("xlsx");
        StringBuilder compare = new StringBuilder();
        json.compare(compare, xlsx);
        //System.out.print(builder.toString());
        compare.append("\n-------------------------------\n");
        xlsx.compare(compare, json);
        if (compare.toString().contains("!=")) {
            System.out.print(compare.toString());
        } else {
            System.out.print("Everything synchronized!");
        }
        Assert.assertFalse(compare.toString().contains("!="));
    }
}
