package org.fluentcodes.projects.elasticobjects.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceCall;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * Created by werner.diwischek on 11.12.17.
 */

@RestController
public class WebEoGet {
    private final static Logger LOG = LogManager.getLogger(WebEoGet.class);
    //http://www.baeldung.com/spring-value-defaults
    @Value("${elasticobjects.scope:QS}")
    String scope;

    ConfigMaps cache;
    @Autowired
    public WebEoGet(ConfigMaps cache) {
        this.cache = cache;
    }

    @RequestMapping(value = "/{selectedItem:.+}.html", method = RequestMethod.GET)
    @ResponseBody
    public String createRootPage(@PathVariable String selectedItem) {
        return createPage("docs", selectedItem + ".html");
    }

    @RequestMapping(value = "/eo/{selectedItem:.+}", method = RequestMethod.GET)
    @ResponseBody
    public String createEoPage(@PathVariable String selectedItem) {
        return createPage("eo", selectedItem);
    }


    @RequestMapping(value = "/examples/{directory}/{selectedItem:.+}", method = RequestMethod.GET)
    @ResponseBody
    public String createExamplesPageWithDirectory(@PathVariable String directory, @PathVariable String selectedItem) {
        return createPage("examples", directory + "/" + selectedItem);
    }

    @RequestMapping(value = "/examples/{selectedItem:.+}", method = RequestMethod.GET)
    @ResponseBody
    public String createExamplesPage(@PathVariable String selectedItem) {
        return createPage("examples", selectedItem);
    }



    @RequestMapping(value = "/blog/{selectedItem:.+}", method = RequestMethod.GET)
    @ResponseBody
    public String createBlogPage(@PathVariable String selectedItem) {
        return createPage("blog", selectedItem);
    }

    @RequestMapping(value = "/faq/{selectedItem:.+}", method = RequestMethod.GET)
    @ResponseBody
    public String createFaqPage(@PathVariable String selectedItem) {
        return createPage("faq", selectedItem);
    }

    private String createPage(final String contentDirectory, final String selectedItem) {
        EoRoot eo = EoRoot.of(cache);
        eo.setRoles(Arrays.asList(WebEo.getRoles()));
        eo.set(selectedItem, "selectedItem");
        eo.set(contentDirectory, "contentDirectory");
        eo.addCall(new TemplateResourceCall("ContentPage.html"));
        System.out.println("Request for " + contentDirectory + "/" + selectedItem);
        LOG.info("Request for " + contentDirectory + "/" + selectedItem);
        try {
            eo.execute();
        }
        catch (Exception e) {
            LOG.error(e);
            return e.getMessage();
        }
        if (eo.hasErrors()) {
            LOG.error(eo.getLog());
            return eo.getLog();
        }
        return (String) eo.get(PathElement.TEMPLATE);
    }


    //https://stackoverflow.com/questions/16332092/spring-mvc-pathvariable-with-dot-is-getting-truncated
    @RequestMapping(value = "/config/{configType}/{configSelected:.+}", method = RequestMethod.GET)
    @ResponseBody
    public String createConfigPage(@PathVariable String configType, @PathVariable String configSelected, @RequestParam(required = false, defaultValue = ".*") String configFilter) {
        EoRoot eo = EoRoot.of(cache);
        eo.set(configType + " - " + configSelected, "selectedItem");
        eo.set(configFilter, "configFilter");
        eo.set(configType, "configType");
        eo.set(configSelected, "configSelected");
        System.out.println("Request for " + configType + "/" + configSelected);
        LOG.info("Request for " + configType + "/" + configSelected);
        eo.addCall(new TemplateResourceCall("ConfigsPage.html"));
        eo.setRoles(Arrays.asList(WebEo.getRoles()));

        try {
            eo.execute();
            if (eo.hasErrors()) {
                LOG.error(eo.getLog());
                return "Errors occured: " + eo.getLog();
            }
        }
        catch (Exception e) {
            LOG.error(e);
            return "Exception thrown: " + e.getMessage();
        }
        return (String) eo.get(PathElement.TEMPLATE);
    }

    @RequestMapping(value = "/configs/{selectedItem:.+}.html", method = RequestMethod.GET)
    @ResponseBody
    public String createConfigStartPage(@PathVariable String selectedItem) {
        EoRoot eo = EoRoot.of(cache);
        eo.setRoles(Arrays.asList(WebEo.getRoles()));
        eo.set(selectedItem + ".html", "selectedItem");
        eo.set(".*", "configFilter");
        eo.set(selectedItem, "configType");
        System.out.println("Request for config " + selectedItem);
        LOG.info("Request for config " + selectedItem);
        eo.addCall(new TemplateResourceCall("ConfigsStartPage.html"));
        try {
            eo.execute();
            if (eo.hasErrors()) {
                LOG.error(eo.getLog());
                return "Errors occured: " + eo.getLog();
            }
        }
        catch (Exception e) {
            LOG.error(e);
            return "Exception thrown: " + e.getMessage();
        }
        return (String) eo.get(PathElement.TEMPLATE);
    }
}
