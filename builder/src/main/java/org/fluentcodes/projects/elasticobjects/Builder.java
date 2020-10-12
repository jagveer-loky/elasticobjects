package org.fluentcodes.projects.elasticobjects;

import com.lexicalscope.jewel.cli.CliFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.calls.generate.Moduls;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceCall;
import org.fluentcodes.projects.elasticobjects.models.ConfigImpl;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigInterface;
import org.fluentcodes.projects.elasticobjects.models.Scope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.calls.generate.GenerateJsonConfigCall.CONFIG_TYPE;
import static org.fluentcodes.projects.elasticobjects.domain.Base.NATURAL_ID;
import static org.fluentcodes.projects.elasticobjects.models.Config.MODULE;
import static org.fluentcodes.projects.elasticobjects.models.Config.MODULE_SCOPE;

/**
 * Created by werner.diwischek on 09.01.18.
 * https://stackoverflow.com/questions/1200054/java-library-for-parsing-command-line-parameters
 */
public class Builder {
    private static final Logger LOG = LogManager.getLogger(Builder.class);
    public static final EOConfigsCache EO_CONFIGS_CACHE = new EOConfigsCache(Scope.TEST);

    public static void main(String[] args)  {
        long start = System.currentTimeMillis();
        BuilderParams params = CliFactory.parseArguments(BuilderParams.class, args);
        String configKey = params.getBuilder()==null ? "JavaBuilder.tpl" : params.getBuilder();

        EO eo = ProviderRootTestScope.createEo();
        eo.set(params.getConfigType()==null ? FileConfig.class.getSimpleName() : params.getConfigType(), CONFIG_TYPE);
        eo.set(params.getModule()==null ? Moduls.EO_TEST.getName() : params.getModuleScope(), MODULE);
        eo.set(params.getModuleScope(), MODULE_SCOPE);
        eo.set(params.getNaturalId()==null ? "AnObject" : params.getNaturalId(), NATURAL_ID);
        eo.set(params.getBuildPath() == null ? "." : params.getBuildPath());
        TemplateResourceCall call = new TemplateResourceCall(configKey);
        System.out.println(call.execute(eo));
        long duration = System.currentTimeMillis() - start;
        LOG.info("Finished creating within " + duration + " ms. ");
    }

    private static final List<String> getModelKeysFromModule(String module)  {
        List<String> modelKeys = new ArrayList<>();

        for (String modelKey: EO_CONFIGS_CACHE.getConfigKeys(ModelConfigInterface.class)) {
            ModelConfigInterface model = EO_CONFIGS_CACHE.findModel(modelKey);
            String modelModule = model.getModule();
            if (module.equals(modelModule)) {
                modelKeys.add(model.getModelKey());
            }
        }
        return modelKeys;
    }
}
