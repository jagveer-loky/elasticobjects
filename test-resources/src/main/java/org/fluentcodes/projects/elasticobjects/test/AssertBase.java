package org.fluentcodes.projects.elasticobjects.test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
    public class AssertBase {
        private static final Logger LOG = LogManager.getLogger(AssertBase.class);
        private static final String PERSISTENCE_DIRECTORY = "src/test/resources/output";

        protected static final String getEnding(Object object) throws Exception {
            if (object == null) {
                throw new Exception("Compare object is null!");
            }
            String classString = object.getClass().getSimpleName();
            switch (classString) {
                case "ArrayList":
                    return ".list";
                case "HashMap":
                    return ".map";
            }
            return "." + classString.toLowerCase();
        }

        private static final void createPersistenceDirectory() {
            final File directory = new File(PERSISTENCE_DIRECTORY);
            if (! directory.exists()) {
                directory.mkdir();
            }
        }

        protected static final String getFileName() throws Exception {
            final StringBuilder fileNameBuilder = new StringBuilder();
            for (StackTraceElement element :Thread.currentThread().getStackTrace()) {
                if (element.getClassName().matches(".*Test$")) {
                    fileNameBuilder.append(element.getClassName().replaceAll(".*\\.",""));
                    fileNameBuilder.append( "/");
                    fileNameBuilder.append(element.getMethodName());
                    break;
                }
            }
            if (fileNameBuilder.length()==0) {
                throw new Exception("Not found pattern '.*Test$' for className in Stacktrace");
            }
            createPersistenceDirectory();
            final String fileName = PERSISTENCE_DIRECTORY + "/" + fileNameBuilder.toString();
            final File parent = new File(fileName).getParentFile();
            if (!parent.exists()) {
                parent.mkdir();
            }
            return fileName;
        }

        protected static final String readOrWritePersisted(String fileName, String serialized)  throws Exception {
            final File file = new File(fileName);
            try {
                return readPersisted(file);
            }
            catch (Exception e) {
                return writePersisted(file, serialized);
            }
        }

        protected static final String writePersisted(File file, String serialized)  throws Exception {
            LOG.info("Write new persistence file: " + file.getAbsolutePath());
            FileWriter writer = null;
            try {
                writer = new FileWriter(file);
                writer.write(serialized);
                return serialized;
            } catch (Exception e) {
                throw new Exception("Problem writing " + file.getAbsolutePath(), e);
            } finally {
                if (writer != null) {
                    writer.close();
                }
            }
        }


        protected static final String readPersisted(File file)  throws Exception {
            FileInputStream fis = null;
            if (!file.exists()) {
                throw new Exception("Problem reading " + file.getAbsolutePath());
            }
            try {
                    fis = new FileInputStream(file);
                    byte[] data = new byte[(int) file.length()];
                    fis.read(data);
                    return new String(data);
            }
            catch (Exception e) {
                throw new Exception("Problem reading " + file.getAbsolutePath());
            }
            finally {
                if (fis != null) {
                    fis.close();
                }
            }
        }
    }

