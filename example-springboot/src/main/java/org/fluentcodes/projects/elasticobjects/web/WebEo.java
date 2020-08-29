package org.fluentcodes.projects.elasticobjects.web;

import org.fluentcodes.projects.elasticobjects.*;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

/**
 * Created by werner.diwischek on 11.12.17.
 */

@RestController
public class WebEo {

    @Autowired
    private EOConfigsCache configsCache;

    private static final LogLevel getLevel(final String logLevelAsString) {
        if (logLevelAsString == null || logLevelAsString.isEmpty()) {
            return LogLevel.WARN;
        }
        try {
            return LogLevel.valueOf(logLevelAsString);
        } catch (Exception e) {
            return LogLevel.WARN;
        }
    }

    protected static final String[] getRoles() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession(true);
        if (session.getAttribute("roles") != null) {
            return (String[]) session.getAttribute("roles");
        }
        String[] roles = new String[]{"guest"};
        session.setAttribute("roles", roles);
        return roles;
    }
    // https://stackoverflow.com/questions/36520314/accessing-httpsession-in-a-controlleradvice-in-a-springboot-application
    // https://stackoverflow.com/questions/49670209/can-spring-map-post-parameters-by-a-way-other-than-requestbody

    @RequestMapping(value = "/eo", method = RequestMethod.POST)
    public String eoPost(@RequestBody String body) {
        return eoPostForm(body, LogLevel.WARN.name());
    }


    @RequestMapping(value = "/eo-form", method = RequestMethod.POST)
    public String eoPostForm(
            @RequestParam(value = "eo", required = true) final String eoAsString,
            @RequestParam(value = "logLevel", required = false, defaultValue = "WARN") final String logLevelAsString
    ) {
        if (eoAsString == null) {
            return "No 'value' is set!";
        }
        if (eoAsString.isEmpty()) {
            return "'value' is empty!";
        }

        final String[] roles = getRoles();
        final LogLevel logLevel = getLevel(logLevelAsString);

        try {
            final EO eo = new EoRoot(this.configsCache)
                    .setLogLevel(logLevel)
                    .mapObject(eoAsString);

            eo.setRoles(Arrays.asList(roles));
            try {
                eo.execute();
            }
            catch (Exception e) {
                return e.getMessage();
            }
            eo.debug("'/eo-form' is executed ");
            if (((EoChild)eo).hasEo("asTemplate")) {
                return (String)eo.get(PathElement.TEMPLATE);
            }
            final String result = new EOToJSON()
                    .setStartIndent(1)
                    .toJSON(eo);
            return result;
        } catch (Exception e) {
            return "Unexpected Exception occured. Please contact Elastic Objects support! " + e.getMessage();
        }
    }
}
