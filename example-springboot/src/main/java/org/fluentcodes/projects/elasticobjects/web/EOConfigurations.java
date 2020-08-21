package org.fluentcodes.projects.elasticobjects.web;

import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.Scope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by werner.diwischek on 11.12.17.
 */

@Configuration
public class EOConfigurations {
    @Value("${elasticobjects.scope:QS}")
    String scope;

    /**
     * Initialize the configs cache object with the json config files in the classpath.
     * @return An initaliazed ConfigsCache object
     * @throws Exception on misconfiguration of the json config files in the classpath.
     */
    @Bean
    public EOConfigsCache createProvider() throws Exception {
        EOConfigsCache provider = new EOConfigsCache(Scope.valueOf(scope), true);
        /*provider.getConfig(HostConfig.class);
        provider.getConfig(FileConfig.class);
        provider.getConfig(JsonConfig.class);
        provider.getConfig(ScsConfig.class);
        provider.getConfig(TemplateConfig.class);*/
        //FileUtil.writeFile("src/main/resources/static/eoConfigurations.json", provider.toString());
        return provider;
    }
}
