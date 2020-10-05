package org.fluentcodes.projects.elasticobjects.web;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceCall;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

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
        eo.set(configType + " - " + configSelected, "selectedItem");
        eo.set(configFilter, "configFilter");
        eo.set(configType, "configType");
        eo.set(configSelected, "configSelected");
        eo.addCall(new TemplateResourceCall("ConfigsPage.html"));
        eo.setRoles(Arrays.asList(WebEo.getRoles()));
        try {
            eo.execute();
        }
        catch (Exception e) {
            return e.getMessage();
        }
        return (String) eo.get(PathElement.TEMPLATE);
    }

    @RequestMapping(value = "/{selectedItem:.+}.html", method = RequestMethod.GET)
    @ResponseBody
    public String createRootPage(@PathVariable String selectedItem) {
        EO eo = new EoRoot(cache);
        eo.setRoles(Arrays.asList(WebEo.getRoles()));
        eo.set(selectedItem + ".html", "selectedItem");
        //eo.set("[]", "navigationItem");
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
        return templatePageCall("ExamplesPage.html", selectedItem);
    }

    private String templatePageCall(final String pageTemplate, final String selectedItem) {
        EO eo = new EoRoot(cache);
        eo.setRoles(Arrays.asList(WebEo.getRoles()));
        eo.set(selectedItem, "selectedItem");
        EO child = eo.setEmpty("navigationItem");
        eo.addCall(new TemplateResourceCall(pageTemplate));
        try {
            eo.execute();
        }
        catch (Exception e) {
            return e.getMessage();
        }
        if (eo.hasErrors()) {
            return eo.getLog();
        }
        return (String) eo.get(PathElement.TEMPLATE);
    }

    @RequestMapping(value = "/blog/{selectedItem:.+}", method = RequestMethod.GET)
    @ResponseBody
    public String createBlogPage(@PathVariable String selectedItem) {
        return templatePageCall("BlogPage.html", selectedItem);
    }

    @RequestMapping(value = "/configs/{selectedItem:.+}.html", method = RequestMethod.GET)
    @ResponseBody
    public String createConfigStartPage(@PathVariable String selectedItem) {
        EO eo = new EoRoot(cache);
        eo.setRoles(Arrays.asList(WebEo.getRoles()));
        eo.set(selectedItem + ".html", "selectedItem");
        eo.set(".*", "configFilter");
        eo.set(selectedItem, "configType");
        eo.addCall(new TemplateResourceCall("ConfigsStartPage.html"));
        try {
        eo.execute();
        }
        catch (Exception e) {
            return e.getMessage();
        }
        return (String) eo.get(PathElement.TEMPLATE);
    }
}
