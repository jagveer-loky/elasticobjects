package org.fluentcodes.projects.elasticobjects.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.eo.*;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.junit.Assert;

import java.io.File;


public class AssertEO {
    private static final Logger LOG = LogManager.getLogger(AssertEO.class);

    public static void assertLogEmpty(EO adapter) {
        Assert.assertTrue("Log should be empty! " + adapter.getLog(), adapter.getLog().isEmpty());
    }

    public static void assertLogNotEmpty(EO adapter) {
        Assert.assertFalse("Log should not be empty! ", adapter.getLog().isEmpty());
    }

    public static final void compare(EOConfigsCache configsCache, final Object object) throws Exception {
        AssertObject.compare(configsCache, object);
    }

    public static final String compare(final EO adapter) throws Exception {
        String serialized = new EOToJSON()
                        .setStartIndent(1)
                        .setSerializationType(JSONSerializationType.EO)
                        .toJSON(adapter);
        String fileName = AssertBase.getFileName() + ".jsn";
        AssertString.compare(fileName, serialized);
        return fileName;
    }

    public static final void compare(final String fileName, final EO adapter) throws Exception {
        String serialized = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.EO)
                .toJSON(adapter);
        AssertString.compare(fileName, serialized);
    }

    public static final String compareJSON(final EO adapter) throws Exception {
        String serialized = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.STANDARD)
                .toJSON(adapter);
        String fileName = AssertBase.getFileName() + ".json";
        AssertString.compare(fileName, serialized);
        return fileName;
    }

    public static final void compareJSON(final String fileName, final EO adapter) throws Exception {
        String serialized = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.STANDARD)
                .toJSON(adapter);
        AssertString.compare(fileName, serialized);
    }

    public static final void compareJSN(final String fileName, final EO adapter) throws Exception {
        String serialized = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.EO)
                .toJSON(adapter);
        AssertString.compare(fileName, serialized);
    }


    public static final String compare(final String serializedObject) throws Exception {
        return AssertString.compare(serializedObject);
    }
}