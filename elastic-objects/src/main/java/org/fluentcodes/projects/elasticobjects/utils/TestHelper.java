package org.fluentcodes.projects.elasticobjects.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * Created by werner.diwischek on 16.12.17.
 */
public class TestHelper {
    private static final Logger LOG = LogManager.getLogger(TestHelper.class);


    public static final String createPersistenceFileName(String ending) {
        final String fileName = TestHelper.getTestTarget("persistedobjects")
                + "/" + TestHelper.getFilenameFromClassAndMethod(".*Test$")
                + "." + ending;
        final File parent = new File(fileName).getParentFile();
        if (!parent.exists()) {
            parent.mkdir();
        }
        return fileName;
    }

    public static final String getFilenameFromClassAndMethod(final String classFilter) {
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element.getClassName().matches(classFilter)) {
                return element.getClassName().replaceAll(".*\\.", "") + "/" + element.getMethodName();
            }
        }
        return "Not found pattern " + classFilter + " for className in Stacktrace";
    }


    public static String getTestTarget(String target) throws RuntimeException {
        String fileName = "src/test/resources/" + target;
        File file = new File(fileName);
        if (!file.exists()) {
            file.mkdir();
        }
        return fileName;
    }

    public static void printStartMethod() {
        LOG.info(">>> " + TestHelper.getFilenameFromClassAndMethod(".*Test$"));
    }
}
