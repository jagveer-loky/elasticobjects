package org.fluentcodes.projects.elasticobjects;

import com.lexicalscope.jewel.cli.CliFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.ModelInterface;
import org.fluentcodes.projects.elasticobjects.models.Scope;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by werner.diwischek on 09.01.18.
 * https://stackoverflow.com/questions/1200054/java-library-for-parsing-command-line-parameters
 */
public class Creator {
    public static final EOConfigsCache EO_CONFIGS_CACHE = new EOConfigsCache(Scope.TEST);
    private static final Logger LOG = LogManager.getLogger(Creator.class);

    public static void main(String[] args)  {
        long start = System.currentTimeMillis();
        CreatorParams params = CliFactory.parseArguments(CreatorParams.class, args);
        checkParams(params);
        Map attributes = new HashMap();
        String result = null;
        switch (params.getExecute()) {
            case ("JsonControl.tpl"):
                result = new CreatorJsonConfig(EO_CONFIGS_CACHE).createJson(params);
                break;
            /*case ("JavaControl.tpl"):
                TemplateCall action = new TemplateCall(EO_CONFIGS_CACHE, params.getExecute());
                EO eo = new EoRoot(EO_CONFIGS_CACHE);
                result = action.execute(eo, attributes);
                break;*/
            default:
                LOG.error("Not a valid term for -- readFiltered (-e)" + params.getExecute());
                break;
        }

        long duration = System.currentTimeMillis() - start;
        LOG.info("Finished creating within " + duration + " ms. " + result);
    }


    private static void checkParams(CreatorParams params)  {
        if (params.getActionKeys() != null && !params.getActionKeys().isEmpty()) {
            return;
        }
        if (params.getModule() != null && !params.getModule().isEmpty()) {
            return;
        }
        throw new EoException("Neither --module/-m nor --actionKey/-a set");
    }

    private static final List<String> getModelKeysFromModule(String module)  {
        List<String> modelKeys = new ArrayList<>();

        for (String modelKey: EO_CONFIGS_CACHE.getConfigKeys(ModelInterface.class)) {
            ModelInterface model = EO_CONFIGS_CACHE.findModel(modelKey);
            String modelModule = model.getModule();
            if (module.equals(modelModule)) {
                modelKeys.add(model.getModelKey());
            }
        }
        return modelKeys;
    }
}
