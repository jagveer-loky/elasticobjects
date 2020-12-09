package org.fluentcodes.projects.elasticobjects;

import com.lexicalscope.jewel.cli.CliFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.Scope;

/**
 * Created by werner.diwischek on 09.01.18.
 * https://stackoverflow.com/questions/1200054/java-library-for-parsing-command-line-parameters
 */
public class Builder {
    private static final Logger LOG = LogManager.getLogger(Builder.class);
    public static final EOConfigsCache EO_CONFIGS_CACHE = new EOConfigsCache(Scope.TEST);

    public static void main(String[] args)  {
        long start = System.currentTimeMillis();
        BuilderExecutor executor = new BuilderExecutor(
                CliFactory.parseArguments(BuilderParams.class, args)
        );
        LOG.info(executor.execute(EO_CONFIGS_CACHE));
        long duration = System.currentTimeMillis() - start;
        LOG.info("Finished creating within " + duration + " ms. ");
    }



}
