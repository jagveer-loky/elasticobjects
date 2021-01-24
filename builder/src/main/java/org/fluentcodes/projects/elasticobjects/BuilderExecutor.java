package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.calls.generate.GenerateAbstract;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;

/**
 * Created by werner.diwischek on 07.12.2020.
 */

public class BuilderExecutor {
    private final BuilderParams params;
    protected BuilderExecutor(BuilderParams params) {
        this.params = params;
    }

    protected String execute(ConfigMaps cache) {
        EO callEo = EoRoot.of(cache);
        new FileReadCall()
                .setFileConfigKey(params.getCallJson())
                .setTargetPath(".")
                .execute(callEo);
        GenerateAbstract generator = (GenerateAbstract)callEo.get();
        generator.mergeParams(params);
        EO eo = EoRoot.of(cache);
        return (String) generator.execute(eo);
    }
}
