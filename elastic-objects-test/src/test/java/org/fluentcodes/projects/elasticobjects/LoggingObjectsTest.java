package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.eo.LogLevel;
import org.fluentcodes.projects.elasticobjects.eo.LoggingObject;
import org.fluentcodes.projects.elasticobjects.eo.LoggingObjectsImpl;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

public class LoggingObjectsTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(LoggingObjectsTest.class);

    @Test
    public void testConstructor() {
        TestHelper.printStartMethod();
        LoggingObject loggingObject = new LoggingObjectsImpl();
        loggingObject.setLogLevel(LogLevel.DEBUG);
        loggingObject.debug(TEO_STATIC.S_MESSAGE);
        LoggingObject loggingObjectDerived = new LoggingObjectsImpl(loggingObject);
        Assert.assertEquals(LogLevel.DEBUG, loggingObjectDerived.getLogLevel());
        Assert.assertTrue(loggingObjectDerived.getLog().contains(TEO_STATIC.S_MESSAGE));
    }

    @Test
    public void testErrorLevel() {
        TestHelper.printStartMethod();
        LoggingObject loggingObject = new LoggingObjectsImpl();
        loggingObject.setLogLevel(LogLevel.DEBUG);
        Assert.assertEquals(LogLevel.DEBUG, loggingObject.getErrorLevel());
        loggingObject.debug(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals(LogLevel.DEBUG, loggingObject.getErrorLevel());
        loggingObject.infoTest(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals(LogLevel.INFO_TEST, loggingObject.getErrorLevel());
        loggingObject.infoQsu(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals(LogLevel.INFO_QSU, loggingObject.getErrorLevel());
        loggingObject.infoProd(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals(LogLevel.INFO_PROD, loggingObject.getErrorLevel());
        loggingObject.info(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals(LogLevel.INFO, loggingObject.getErrorLevel());
        loggingObject.warn(TEO_STATIC.S_MESSAGE);

        Assert.assertFalse(loggingObject.hasErrors());

        Assert.assertEquals(LogLevel.WARN, loggingObject.getErrorLevel());
        loggingObject.error(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals(LogLevel.ERROR, loggingObject.getErrorLevel());

        loggingObject.debug(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals(LogLevel.ERROR, loggingObject.getErrorLevel());

        loggingObject.info(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals(LogLevel.ERROR, loggingObject.getErrorLevel());

        Assert.assertTrue(loggingObject.hasErrors());

        loggingObject = new LoggingObjectsImpl();
        loggingObject.info(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals(LogLevel.INFO, loggingObject.getErrorLevel());
        Assert.assertEquals("", loggingObject.getLog());

    }

    @Test
    public void assertSetLogLevel() {
        TestHelper.printStartMethod();

        LoggingObject loggingObject = new LoggingObjectsImpl();
        Assert.assertEquals(LogLevel.ERROR, loggingObject.getLogLevel());

        loggingObject.setLogLevel("DEBUG");
        Assert.assertEquals(LogLevel.DEBUG, loggingObject.getLogLevel());

        loggingObject.setLogLevel("INFO_TEST");
        Assert.assertEquals(LogLevel.INFO_TEST, loggingObject.getLogLevel());

        loggingObject.setLogLevel("INFO_QSU");
        Assert.assertEquals(LogLevel.INFO_QSU, loggingObject.getLogLevel());

        loggingObject.setLogLevel("INFO_PROD");
        Assert.assertEquals(LogLevel.INFO_PROD, loggingObject.getLogLevel());

        loggingObject.setLogLevel("INFO");
        Assert.assertEquals(LogLevel.INFO, loggingObject.getLogLevel());

        loggingObject.setLogLevel("WARN");
        Assert.assertEquals(LogLevel.WARN, loggingObject.getLogLevel());

        loggingObject.setLogLevel("ERROR");
        Assert.assertEquals(LogLevel.ERROR, loggingObject.getLogLevel());

        loggingObject.setLogLevel("DEBUG");
        Assert.assertEquals(LogLevel.DEBUG, loggingObject.getLogLevel());

        loggingObject.setLogLevel("");
        Assert.assertEquals(LogLevel.DEBUG, loggingObject.getLogLevel());

        loggingObject.setLogLevel("FALSE LOG_LEVEL");
        Assert.assertEquals(LogLevel.DEBUG, loggingObject.getLogLevel());

        loggingObject.setLogLevel((Object) "DEBUG");
        Assert.assertEquals(LogLevel.DEBUG, loggingObject.getLogLevel());

        loggingObject.setLogLevel((Object) "INFO_TEST");
        Assert.assertEquals(LogLevel.INFO_TEST, loggingObject.getLogLevel());

        loggingObject.setLogLevel((Object) "INFO_QSU");
        Assert.assertEquals(LogLevel.INFO_QSU, loggingObject.getLogLevel());

        loggingObject.setLogLevel((Object) "INFO_PROD");
        Assert.assertEquals(LogLevel.INFO_PROD, loggingObject.getLogLevel());

        loggingObject.setLogLevel((Object) "INFO");
        Assert.assertEquals(LogLevel.INFO, loggingObject.getLogLevel());

        loggingObject.setLogLevel((Object) "WARN");
        Assert.assertEquals(LogLevel.WARN, loggingObject.getLogLevel());

        loggingObject.setLogLevel((Object) "ERROR");
        Assert.assertEquals(LogLevel.ERROR, loggingObject.getLogLevel());

        loggingObject.setLogLevel((Object) "DEBUG");
        Assert.assertEquals(LogLevel.DEBUG, loggingObject.getLogLevel());

        loggingObject.setLogLevel((Object) "");
        Assert.assertEquals(LogLevel.DEBUG, loggingObject.getLogLevel());
    }

    @Test
    public void testAddLog() {
        TestHelper.printStartMethod();
        LoggingObject loggingObject = new LoggingObjectsImpl();
        loggingObject.addLog(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));
    }

    @Test
    public void testLogNull() {
        TestHelper.printStartMethod();
        LoggingObject loggingObject = new LoggingObjectsImpl();
        loggingObject.error(null);
        Assert.assertTrue(loggingObject.getLog().contains("ERROR"));

        loggingObject = new LoggingObjectsImpl();
        loggingObject.error("");
        Assert.assertTrue(loggingObject.getLog().contains("ERROR"));

        loggingObject.error(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        loggingObject.error(null, new Exception(TEO_STATIC.S_MESSAGE));
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        loggingObject.error("", new Exception(TEO_STATIC.S_MESSAGE));
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));
    }

    @Test
    public void testStart() {
        TestHelper.printStartMethod();
        LoggingObject loggingObject = new LoggingObjectsImpl();
        long start = loggingObject.start();
        Assert.assertTrue(System.currentTimeMillis() - start < 1000);
    }

    @Test
    public void testStartDiff() {
        TestHelper.printStartMethod();
        LoggingObject loggingObject = new LoggingObjectsImpl();
        Assert.assertTrue(loggingObject.startDiff() < 1000);
    }


    public void assertSetLogLevel(LoggingObject loggingObject, LogLevel logLevel) {
        TestHelper.printStartMethod();
        loggingObject.setLogLevel(logLevel);
        Assert.assertEquals(logLevel, loggingObject.getLogLevel());
    }

    /**
     * Tests debug() with different logLevels
     *
     * @
     */
    @Test
    public void testDebug() {
        TestHelper.printStartMethod();
        LoggingObject loggingObject = new LoggingObjectsImpl();
        loggingObject.debug(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals("", loggingObject.getLog());

        assertSetLogLevel(loggingObject, LogLevel.INFO);
        loggingObject.debug(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals("", loggingObject.getLog());

        assertSetLogLevel(loggingObject, LogLevel.INFO_TEST);
        loggingObject.debug(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals("", loggingObject.getLog());

        assertSetLogLevel(loggingObject, LogLevel.INFO_QSU);
        loggingObject.debug(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals("", loggingObject.getLog());

        assertSetLogLevel(loggingObject, LogLevel.INFO_PROD);
        loggingObject.debug(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals("", loggingObject.getLog());

        assertSetLogLevel(loggingObject, LogLevel.ERROR);
        loggingObject.debug(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals("", loggingObject.getLog());

        assertSetLogLevel(loggingObject, LogLevel.WARN);
        loggingObject.debug(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals("", loggingObject.getLog());

        assertSetLogLevel(loggingObject, LogLevel.DEBUG);
        loggingObject.debug(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        assertSetLogLevel(loggingObject, LogLevel.DEBUG);
        loggingObject.debug(TEO_STATIC.S_MESSAGE, new Exception("TEST"));
        Assert.assertTrue(loggingObject.getLog().contains("TEST"));
    }

    /**
     * Tests info() with different logLevels
     */
    @Test
    public void testInfoTest() {
        TestHelper.printStartMethod();
        LoggingObject loggingObject = new LoggingObjectsImpl();
        loggingObject.infoTest(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals("", loggingObject.getLog());

        assertSetLogLevel(loggingObject, LogLevel.ERROR);
        loggingObject.infoTest(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals("", loggingObject.getLog());

        assertSetLogLevel(loggingObject, LogLevel.WARN);
        loggingObject.infoTest(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals("", loggingObject.getLog());

        assertSetLogLevel(loggingObject, LogLevel.INFO);
        loggingObject.infoTest(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals("", loggingObject.getLog());

        assertSetLogLevel(loggingObject, LogLevel.INFO_PROD);
        loggingObject.infoTest(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals("", loggingObject.getLog());

        assertSetLogLevel(loggingObject, LogLevel.INFO_QSU);
        loggingObject.infoTest(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals("", loggingObject.getLog());

        assertSetLogLevel(loggingObject, LogLevel.INFO_TEST);
        loggingObject.infoTest(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        assertSetLogLevel(loggingObject, LogLevel.DEBUG);
        loggingObject.infoTest(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        assertSetLogLevel(loggingObject, LogLevel.INFO_TEST);
        loggingObject.infoTest(TEO_STATIC.S_MESSAGE, new Exception("TEST"));
        Assert.assertTrue(loggingObject.getLog().contains("TEST"));
    }

    /**
     * Tests infoQsu() with different logLevels
     */
    @Test
    public void testInfoQsu() {
        TestHelper.printStartMethod();
        LoggingObject loggingObject = new LoggingObjectsImpl();
        loggingObject.infoQsu(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals("", loggingObject.getLog());

        assertSetLogLevel(loggingObject, LogLevel.ERROR);
        loggingObject.infoQsu(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals("", loggingObject.getLog());

        assertSetLogLevel(loggingObject, LogLevel.WARN);
        loggingObject.infoQsu(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals("", loggingObject.getLog());

        assertSetLogLevel(loggingObject, LogLevel.INFO);
        loggingObject.infoQsu(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals("", loggingObject.getLog());

        assertSetLogLevel(loggingObject, LogLevel.INFO_PROD);
        loggingObject.infoQsu(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals("", loggingObject.getLog());

        assertSetLogLevel(loggingObject, LogLevel.INFO_QSU);
        loggingObject.infoQsu(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        assertSetLogLevel(loggingObject, LogLevel.INFO_TEST);
        loggingObject.infoQsu(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        assertSetLogLevel(loggingObject, LogLevel.DEBUG);
        loggingObject.infoQsu(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        assertSetLogLevel(loggingObject, LogLevel.INFO_TEST);
        loggingObject.infoQsu(TEO_STATIC.S_MESSAGE, new Exception("TEST"));
        Assert.assertTrue(loggingObject.getLog().contains("TEST"));
    }

    /**
     * Tests infoProd() with different logLevels
     */
    @Test
    public void testInfoProd() {
        TestHelper.printStartMethod();
        LoggingObject loggingObject = new LoggingObjectsImpl();
        loggingObject.infoProd(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals("", loggingObject.getLog());

        assertSetLogLevel(loggingObject, LogLevel.ERROR);
        loggingObject.infoProd(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals("", loggingObject.getLog());

        assertSetLogLevel(loggingObject, LogLevel.WARN);
        loggingObject.infoProd(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals("", loggingObject.getLog());

        assertSetLogLevel(loggingObject, LogLevel.INFO);
        loggingObject.infoProd(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        assertSetLogLevel(loggingObject, LogLevel.INFO_PROD);
        loggingObject.infoProd(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        assertSetLogLevel(loggingObject, LogLevel.INFO_QSU);
        loggingObject.infoProd(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        assertSetLogLevel(loggingObject, LogLevel.INFO_TEST);
        loggingObject.infoProd(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        assertSetLogLevel(loggingObject, LogLevel.DEBUG);
        loggingObject.infoProd(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        assertSetLogLevel(loggingObject, LogLevel.INFO_TEST);
        loggingObject.infoProd(TEO_STATIC.S_MESSAGE, new Exception("TEST"));
        Assert.assertTrue(loggingObject.getLog().contains("TEST"));
    }

    /**
     * Tests info() with different logLevels
     */
    @Test
    public void testInfo() {
        TestHelper.printStartMethod();
        LoggingObject loggingObject = new LoggingObjectsImpl();
        loggingObject.info(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals("", loggingObject.getLog());

        assertSetLogLevel(loggingObject, LogLevel.ERROR);
        loggingObject.info(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals("", loggingObject.getLog());

        assertSetLogLevel(loggingObject, LogLevel.WARN);
        loggingObject.info(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals("", loggingObject.getLog());

        assertSetLogLevel(loggingObject, LogLevel.INFO);
        loggingObject.info(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        assertSetLogLevel(loggingObject, LogLevel.INFO_PROD);
        loggingObject.info(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        assertSetLogLevel(loggingObject, LogLevel.INFO_QSU);
        loggingObject.info(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        assertSetLogLevel(loggingObject, LogLevel.INFO_TEST);
        loggingObject.info(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        assertSetLogLevel(loggingObject, LogLevel.DEBUG);
        loggingObject.info(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        assertSetLogLevel(loggingObject, LogLevel.INFO_TEST);
        loggingObject.info(TEO_STATIC.S_MESSAGE, new Exception("TEST"));
        Assert.assertTrue(loggingObject.getLog().contains("TEST"));
    }

    /**
     * Tests warn() with different logLevels
     */
    @Test
    public void testWarn() {
        TestHelper.printStartMethod();
        LoggingObject loggingObject = new LoggingObjectsImpl();
        loggingObject.warn(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals("", loggingObject.getLog());

        assertSetLogLevel(loggingObject, LogLevel.ERROR);
        loggingObject.warn(TEO_STATIC.S_MESSAGE);
        Assert.assertEquals("", loggingObject.getLog());

        assertSetLogLevel(loggingObject, LogLevel.WARN);
        loggingObject.warn(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        assertSetLogLevel(loggingObject, LogLevel.INFO);
        loggingObject.warn(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        assertSetLogLevel(loggingObject, LogLevel.INFO_PROD);
        loggingObject.warn(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        assertSetLogLevel(loggingObject, LogLevel.INFO_QSU);
        loggingObject.warn(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        assertSetLogLevel(loggingObject, LogLevel.INFO_TEST);
        loggingObject.warn(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        assertSetLogLevel(loggingObject, LogLevel.DEBUG);
        loggingObject.warn(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        assertSetLogLevel(loggingObject, LogLevel.INFO_TEST);
        loggingObject.warn(TEO_STATIC.S_MESSAGE, new Exception("TEST"));
        Assert.assertTrue(loggingObject.getLog().contains("TEST"));
    }

    /**
     * Tests error() with different logLevels
     */
    @Test
    public void testError() {
        TestHelper.printStartMethod();
        LoggingObject loggingObject = new LoggingObjectsImpl();
        loggingObject.error(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        assertSetLogLevel(loggingObject, LogLevel.ERROR);
        loggingObject.error(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        assertSetLogLevel(loggingObject, LogLevel.WARN);
        loggingObject.error(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        assertSetLogLevel(loggingObject, LogLevel.INFO);
        loggingObject.error(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        assertSetLogLevel(loggingObject, LogLevel.INFO_PROD);
        loggingObject.error(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        assertSetLogLevel(loggingObject, LogLevel.INFO_QSU);
        loggingObject.error(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        assertSetLogLevel(loggingObject, LogLevel.INFO_TEST);
        loggingObject.error(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        assertSetLogLevel(loggingObject, LogLevel.DEBUG);
        loggingObject.error(TEO_STATIC.S_MESSAGE);
        Assert.assertTrue(loggingObject.getLog().contains(TEO_STATIC.S_MESSAGE));

        loggingObject = new LoggingObjectsImpl();
        assertSetLogLevel(loggingObject, LogLevel.INFO_TEST);
        loggingObject.error(TEO_STATIC.S_MESSAGE, new Exception("TEST"));
        Assert.assertTrue(loggingObject.getLog().contains("TEST"));
    }


}