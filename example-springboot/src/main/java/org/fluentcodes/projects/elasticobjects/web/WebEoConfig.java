package org.fluentcodes.projects.elasticobjects.web;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.ConfigResources;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.RolePermissions;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceCall;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by werner.diwischek on 11.12.17.
 */

@RestController
public class WebEoConfig {

    //http://www.baeldung.com/spring-value-defaults
    @Value("${elasticobjects.scope:QS}")
    String scope;
    @Autowired
    private EOConfigsCache cache;

    @RequestMapping(value = "/eoConfigurations", method = RequestMethod.GET)
    @ResponseBody
    public String all() {
        return cache.serialize();
    }

    @RequestMapping(value = "/config", method = RequestMethod.GET)
    @ResponseBody
    public String getCacheClasses() {
        String[] roles = WebEo.getRoles();
        StringBuilder builder = new StringBuilder("<html><head></head><body>");
        builder.append("<a href=\"/config\">cached classes</a>");
        builder.append("<h1>Available Cache Classes  for scope =" + scope + "</h1><ul>");
        try {
            for (Class cacheClass : cache.getKeys()) {
                builder.append("<li><a href=\"/config/" + cacheClass.getSimpleName() + "\">"
                        + cacheClass.getSimpleName() + "</a></li>");
            }

            builder.append("</ul></body></html>");
            return builder.toString();
        } catch (Exception e) {
            builder.append("Error: " + e.getMessage() + "</body></html>");
            return builder.toString();
        }
    }


    @RequestMapping(value = "/config/{configClassId}", method = RequestMethod.GET)
    @ResponseBody
    public String get(
            @PathVariable final String configClassId) {
        EO eo = new EoRoot(cache);
        eo.set(configClassId, "configType");
        eo.addCall(new TemplateResourceCall("ConfigKeysCall_LinkListHtml"));
        eo.execute();
        return (String) eo.get("_template");
    }


    //https://stackoverflow.com/questions/16332092/spring-mvc-pathvariable-with-dot-is-getting-truncated
    @RequestMapping(value = "/config/{configType}/{configFilter:.+}", method = RequestMethod.GET)
    @ResponseBody
    public String get(@PathVariable String configType, @PathVariable String configFilter) {
        EO eo = new EoRoot(cache);
        eo.set(configType, "configType");
        eo.set(configFilter, "configFilter");
        eo.addCall(new TemplateResourceCall("ConfigCall_Form"));
        eo.execute();
        return (String) eo.get("_template");
    }

}
