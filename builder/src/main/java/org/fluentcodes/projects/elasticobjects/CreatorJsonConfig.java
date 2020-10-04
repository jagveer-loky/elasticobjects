package org.fluentcodes.projects.elasticobjects;

import com.lexicalscope.jewel.cli.CliFactory;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;


import java.util.HashMap;
import java.util.Map;

/**
 * Created 7.6.2018
 */
public class CreatorJsonConfig {
    private final EOConfigsCache configsCache;

    public CreatorJsonConfig(final EOConfigsCache configsCache) {
        this.configsCache = configsCache;
    }

    public String createJson(final String sheet, final String module, final String subModule)  {
        CreatorParams params = CliFactory.parseArguments(CreatorParams.class,
                new String[]{"-m", module, "-s", subModule, "-a", sheet, "-e", "TPL_JSON_CONTROL"});
        return createJson(params);
    }


    public String createJson(final CreatorParams params)  {
        StringBuilder builder = new StringBuilder();
        for (final String sheet : params.getActionKeys()) {
            TemplateCall action = new TemplateCall();
            EO eo = new EoRoot(configsCache);
            eo.set(sheet,"/tmp/sheet");
            action.execute(eo);
        }
        return builder.toString();
    }

    private Map getAsAttributes(CreatorParams params) {
        Map attributes = new HashMap();
        attributes.put("module", params.getModule());
        attributes.put("subModule", params.getSubModule());
        attributes.put("filterModels", params.getFilter());
        attributes.put("actionKeys", params.getActionKeys());
        return attributes;
    }
}
