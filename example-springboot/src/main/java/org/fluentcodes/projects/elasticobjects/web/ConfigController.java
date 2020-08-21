package org.fluentcodes.projects.elasticobjects.web;

import org.fluentcodes.projects.elasticobjects.*;
import org.fluentcodes.projects.elasticobjects.calls.ConfigResources;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.RolePermissions;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by werner.diwischek on 11.12.17.
 */

@RestController
public class ConfigController {

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
        String[] roles = EOController.getRoles();
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
        return createConfigEntries(configClassId);
    }

    private String createConfigEntries(final String configClassId) {
        StringBuilder builder = new StringBuilder("<html><head></head><body>");
        builder.append("<a href=\"/config\">Cached Configurations</a>");
        builder.append("<h1>Cached key for configuration '" + configClassId + "'  for scope ='" + scope + "'</h1><ul>");
        try {
            Class<? extends Config> cacheClass = (Class<? extends Config>)Class.forName(EO_STATIC.CP_CONFIG + "." + configClassId);
            List<String> keys = new ArrayList<>(cache.getKeys(cacheClass));
            Collections.sort(keys);
            for (String key : keys) {
                if (cacheClass == ModelInterface.class) {
                    String packagePath = cache.findModel(key).getPackagePath();
                    if (packagePath == null || packagePath.isEmpty()) {
                        continue;
                    }
                    if (!packagePath.contains("elasticobjects")) {
                        continue;
                    }
                }
                //final String CHAR_SET = StandardCharsets.UTF_8.name();
                final String CHAR_SET = "UTF-8";
                builder.append("<li><a href=\"/config/" + configClassId + "/" + URLEncoder.encode(key, CHAR_SET) + "\">"
                        + key + "</a></li>");
            }

            builder.append("</ul></body></html>");
            return builder.toString();
        } catch (Exception e) {
            builder.append("Error: " + e.getMessage() + "</body></html>");
            return builder.toString();
        }
    }

    //https://stackoverflow.com/questions/16332092/spring-mvc-pathvariable-with-dot-is-getting-truncated
    @RequestMapping(value = "/config/{cacheId}/{key:.+}", method = RequestMethod.GET)
    @ResponseBody
    public String get(@PathVariable String cacheId, @PathVariable String key) {
        String[] roles = EOController.getRoles();

        StringBuilder builder = new StringBuilder("<html><head></head><body>");
        builder.append("<a href=\"/config\">Cached Configurations</a> - <a href=\"/config/" + cacheId + "\">" + cacheId + " </a>");
        builder.append("<h1>Cached configuration for '" + cacheId + "'  and key '" + key + "' for scope ='" + scope + "'</h1>");

        try {
            Class<?> cacheClass = Class.forName(EO_STATIC.CP_CONFIG + "." + cacheId);

            Object itemConfig = cache.find(cacheClass, key);
            PermissionType permission = PermissionType.NOTHING;
            if (itemConfig instanceof ConfigResources) {
                RolePermissions permissions = ((ConfigResources)itemConfig).getRolePermissions();
                permission = permissions.getPermissions(Arrays.asList(roles));
            }
            builder.append("<p>You're logged in with the roles ");
            for (String role : roles) {
                builder.append(role);
            }
            builder.append(" and permission " + permission + ".");
            builder.append("</p>");

            builder.append("<xmp style=\"background-color:lightgray;border:1px solid darkgray;\">");
            EO adapter = new EoRoot(cache).set(itemConfig);
            builder.append(new EOToJSON()
                    .setStartIndent(1)
                    .toJSON(adapter));
            builder.append("</xmp>");

            if (itemConfig instanceof Config) {
                ModelConfig modelConfig = cache.findModel(itemConfig.getClass().getSimpleName());
                final String callKey = modelConfig.getEoParams().getModelConfigKey();
                if (callKey != null) {
                    builder.append("Function " + callKey);
                    ModelConfig callConfig = cache.findModel(callKey);
                    if (callConfig != null) {
                        List<String> actions = callConfig.getEoParams().getMethods();
                        for (final String action : actions) {
                            final PermissionType actionPermission = PermissionType.valueOf(action.toUpperCase());

                            builder.append("\n<form action=\"/eo\" method=\"POST\">");
                            builder.append("<textarea name=\"eo\" cols=\"60\" rows=\"6\"");
                            if (actionPermission.ordinal() < permission.ordinal()) {
                                builder.append("style=\"background-color:red\"");
                            } else {
                                builder.append("style=\"background-color:green\"");
                            }
                            builder.append(">{");
                            builder.append("\n  \"");
                            //+ JSONToEO.CALLS +
                            builder.append("\": [\n    {\n         \"execute\": \"");
                            builder.append(callConfig.getModelKey() + ".");
                            builder.append(action);
                            builder.append("(" + key + ")\"");
                            builder.append("\n    }\n  ]\n}</textarea>");

                            builder.append("<input type=\"submit\" value=\"post\"/>");
                            builder.append("</form>");
                        }
                    }
                }
            }
            builder.append("</body></html>");
            return builder.toString();
        } catch (Exception e) {
            builder.append(e.getMessage() + "</body></html>");
            return builder.toString();
        }

    }

}
