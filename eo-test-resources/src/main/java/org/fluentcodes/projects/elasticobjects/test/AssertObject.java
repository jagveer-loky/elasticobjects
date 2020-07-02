package org.fluentcodes.projects.elasticobjects.test;
/**
 * Created 29.8.2018
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.eo.EOToJSON;
import org.fluentcodes.projects.elasticobjects.eo.JSONSerializationType;

public class AssertObject {
    private static final Logger LOG = LogManager.getLogger(AssertObject.class);

    public static final void compare(EOConfigsCache provider, final Object object) throws Exception {
        String serialized = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.EO)
                .toJSON(provider, object);
        String fileName = AssertBase.getFileName() + "." + object.getClass().getSimpleName();
        AssertString.compare(fileName, serialized);
    }
}