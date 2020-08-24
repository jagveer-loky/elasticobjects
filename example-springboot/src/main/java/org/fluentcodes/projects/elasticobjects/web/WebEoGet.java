package org.fluentcodes.projects.elasticobjects.web;

import org.fluentcodes.projects.elasticobjects.*;
import org.fluentcodes.projects.elasticobjects.calls.configs.ConfigKeysCall;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceCall;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.tools.xpect.IOString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by werner.diwischek on 11.12.17.
 */

@RestController
public class WebEoGet {

    //http://www.baeldung.com/spring-value-defaults
    @Value("${elasticobjects.scope:QS}")
    String scope;
    @Autowired
    private EOConfigsCache cache;

    //https://stackoverflow.com/questions/16332092/spring-mvc-pathvariable-with-dot-is-getting-truncated
    @RequestMapping(value = "/config/{configType}/{configSelected:.+}", method = RequestMethod.GET)
    @ResponseBody
    public String get(@PathVariable String configType, @PathVariable String configSelected, @RequestParam(required = false, defaultValue = ".*") String configFilter) {
        EO eo = new EoRoot(cache);
        eo.set(configFilter, "configFilter");
        eo.set(configType, "configType");
        eo.set(configSelected, "configSelected");
        eo.addCall(new TemplateResourceCall("ConfigsPage.html"));
        eo.execute();
        return (String) eo.get(PathElement.TEMPLATE);
    }

    @RequestMapping(value = "/{selectedItem:.+}.html", method = RequestMethod.GET)
    @ResponseBody
    public String createRootPage(@PathVariable String selectedItem) {
        EO eo = new EoRoot(cache);
        eo.set(selectedItem + ".html", "selectedItem");
        eo.set("[]", "navigationItem");
        eo.addCall(new TemplateResourceCall("ContentPage.html"));
        try {
            eo.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return (String) eo.get(PathElement.TEMPLATE);
    }

    @RequestMapping(value = "/examples/{selectedItem:.+}", method = RequestMethod.GET)
    @ResponseBody
    public String createExamplesPage(@PathVariable String selectedItem) {
        EO eo = new EoRoot(cache);
        eo.set(selectedItem, "selectedItem");
        EO child = eo.setEmpty("navigationItem");
        //child.set("/docs/Eo","EO");
        eo.addCall(new TemplateResourceCall("ExamplesPage.html"));
        eo.execute();
        if (eo.hasErrors()) {
            return new EOToJSON().toJSON(eo);
        }
        return (String) eo.get(PathElement.TEMPLATE);
    }

    @RequestMapping(value = "/config/{selectedItem:.+}", method = RequestMethod.GET)
    @ResponseBody
    public String createConfigStartPage(@PathVariable String selectedItem) {
        EO eo = new EoRoot(cache);
        eo.set(selectedItem, "selectedItem");

        ConfigKeysCall configKeysCall = new ConfigKeysCall(selectedItem.replaceAll(".html$", ""));
        List entries = configKeysCall.execute(eo);
        EO child = eo.setEmpty("navigationItem");
        for (Object entry: entries) {
            child.set("/config/" + selectedItem.replaceAll(".html$", "") + "/" + entry, (String) entry);
        }
        eo.addCall(new TemplateResourceCall("ContentPage.html"));
        eo.execute();
        return (String) eo.get(PathElement.TEMPLATE);
    }

}
