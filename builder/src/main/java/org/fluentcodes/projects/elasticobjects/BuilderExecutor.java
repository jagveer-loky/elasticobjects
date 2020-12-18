package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.calls.generate.GenerateAbstract;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.Scope;

/**
 * Created by werner.diwischek on 07.12.2020.
 */

public class BuilderExecutor {
    private final BuilderParams params;
    protected BuilderExecutor(BuilderParams params) {
        this.params = params;
    }

    protected String execute(EOConfigsCache cache) {
        EO callEo = EoRoot.OFcache);
        new FileReadCall()
                .setFileConfigKey(params.getCallJson())
                .setTargetPath(".")
                .execute(callEo);
        GenerateAbstract generator = (GenerateAbstract)callEo.get();
        generator.mergeParams(params);
        EO eo = EoRoot.OFcache);
        return (String) generator.execute(eo);
    }
}
