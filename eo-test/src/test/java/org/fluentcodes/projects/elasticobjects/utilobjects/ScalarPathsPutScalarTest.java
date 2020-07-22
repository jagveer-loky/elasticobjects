package org.fluentcodes.projects.elasticobjects.utilobjects;

/**
 * TransformerTest
 * transformer=TransformerJTree
 * clazz=MetaModels.class
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.TEO_STATIC;
import org.junit.Assert;
import org.junit.Test;

public class ScalarPathsPutScalarTest {
    private static final Logger log = LogManager.getLogger(ScalarPathsPutScalarTest.class);

    /*@Test
    public void dirScalarTest()  {

        ScalarPaths scalarPath = new ScalarPaths();
        storeScalarTest(scalarPath, "/test/string", "Test", "/test/string");
        storeScalarTest(scalarPath, "/test/int", 1, "/test/int");
        storeScalarTest(scalarPath, "test/string", "Test2", "/test/string");
        storeScalarTest(scalarPath, "/test/int", 2, "test/int");
        storeScalarTest(scalarPath, "/string", "Test", "/string");
        storeScalarTest(scalarPath, "/int", 1, "/int");
        storeScalarTest(scalarPath, "/float", 1.1, "/float");
        storeScalarTest(scalarPath, "/date", TEO_STATIC.SAMPLE_DATE, "/date");
        storeScalarTest(scalarPath, "/boolean", true, "/boolean");
        // TODO for later since scalar path is actually not used storeScalarTest(scalarPath, S_STRING, "Test2", "/string");
        storeScalarTest(scalarPath, TEO_STATIC.S_KEY_INTEGER, 2, "/int");
        storeScalarTest(scalarPath, TEO_STATIC.S_KEY_FLOAT, 1.2, "/float");
        storeScalarTest(scalarPath, TEO_STATIC.S_KEY_DATE, TEO_STATIC.SAMPLE_DATE, TEO_STATIC.S_KEY_DATE);
        storeScalarTest(scalarPath, TEO_STATIC.S_KEY_BOOLEAN, false, "/boolean");
    }


    public void storeScalarTest(ScalarPaths scalarPath, String path, Object value, String absolutePath)  {
        if (scalarPath == null) {
            scalarPath = new ScalarPaths();
        }
        log.debug("Writing " + value.getClass().getSimpleName() + " value: " + value);
        scalarPath.put(path, value);
        testScalarTest(scalarPath, path, value, absolutePath);
    }

    public void testScalarTest(ScalarPaths scalarPath, String path, Object value, String absolutePath) {
        if (scalarPath == null) {
            scalarPath = new ScalarPaths();
        }
        Assert.assertEquals(value, scalarPath.get(path));
        Assert.assertEquals(value, scalarPath.get(absolutePath));
    }*/

}
