package org.fluentcodes.projects.elasticobjects;

import com.lexicalscope.jewel.cli.CliFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.calls.TemplateCall;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.config.Scope;
import org.fluentcodes.projects.elasticobjects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.utils.test.TestObjectProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by werner.diwischek on 09.01.18.
 * https://stackoverflow.com/questions/1200054/java-library-for-parsing-command-line-parameters
 */
public class Creator {
    public static final EOConfigsCache FINDER = new EOConfigsCache(Scope.TEST);
    private static final Logger LOG = LogManager.getLogger(Creator.class);

    public static void main(String[] args)  {
        long start = System.currentTimeMillis();
        CreatorParams params = CliFactory.parseArguments(CreatorParams.class, args);

        TemplateCall templateAction = new TemplateCall(FINDER, "md.control");
        Map<String, String> attributes = new HashMap<>();
        attributes.put("set", params.getFilter());

        EO adapter = TestObjectProvider.createAdapter();
        String value = templateAction.execute(adapter, attributes);

        long duration = System.currentTimeMillis() - start;
        LOG.info("Finished creating within " + duration + " ms.");
    }
}
