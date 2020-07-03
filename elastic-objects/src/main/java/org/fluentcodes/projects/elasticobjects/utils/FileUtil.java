/**
 * Copyright 2015 Werner Diwischek
 * Some static util methods to checkConfig files
 * http://www.j-tree.org<br/>
 * date 10.3.2012<br/>
 *
 * @author Werner Diwischek
 * @version 0.1
 */

package org.fluentcodes.projects.elasticobjects.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EoException;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class FileUtil {
    private static final Logger LOG = LogManager.getLogger(FileUtil.class);
    private static SimpleDateFormat dayTimeFormat = new SimpleDateFormat("yyyyMMdd-hhmmss");
    private static SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");
    //private static File baseUri = new File("file://C:/myProjects/jdbee-utils");
    private static File baseUri = new File("");

    public FileUtil() {
    }

    //
    // http://commons.apache.org/proper/commons-vfs/api.html


    /**
     * Buffered Reader
     *
     * @param bReader
     * @return
     * @throws IOException
     */
    private static String readFileBuffered(Reader bReader) {
        String result = "";
        Writer stringWriter = null;
        try {
            stringWriter = new StringWriter();
            //http://stackoverflow.com/questions/1684040/java-why-charset-colKeys-are-not-constants
            int n;
            char[] buff = new char[1024];
            try {
                while ((n = bReader.read(buff)) != -1) {
                    try {
                        stringWriter.write(buff, 0, n);
                    } catch (IOException e) {
                        throw new EoException(e);
                    }
                }
            }
            catch (Exception e) {
                throw new EoException(e);
            }
            result = stringWriter.toString();
        } finally {
            if (stringWriter != null) {
                try {
                    stringWriter.close();
                } catch (IOException e) {
                    throw new EoException(e);
                }
            }
        }
        return result;
    }


    //http://stackoverflow.com/questions/9742557/append-to-a-file-using-apache-commons-vfs

    //http://wiki.apache.org/commons/SimpleSftpFileDownload

    /**
     * Reads a file from the class path
     *
     * @param fileName - The filename according to the classpath src/main/resources/
     * @return
     * @throws IOException
     */

    public static String readFileFromClassPath(String fileName) throws IOException {
        LOG.debug("Start reading " + fileName + " from ClassPath");
        URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
        if (url != null) {
            return readFile(url);
        } else {
            throw new IOException("Could not checkConfig " + fileName + " from ClassPath.");
        }
    }

    /**
     * checkConfig file with an url
     *
     * @param url - The url of the file
     * @return
     * @throws IOException
     */

    public static String readFile(URL url) {
        LOG.debug("Start reading " + url.getPath());
        try {
            BufferedReader bReader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            //http://stackoverflow.com/questions/1684040/java-why-charset-colKeys-are-not-constants
            String result = readFileBuffered(bReader);
            try {
                bReader.close();
            } catch (IOException e) {
                throw new EoException(e);
            }
            return result;
        }
        catch (Exception e) {
            throw new EoException(e);
        }
    }

    /**
     * Reads a file with exception
     *
     * @param fileName
     * @return
     * @throws IOException
     */

    public static String readFile(final String fileName) {
        LOG.debug("Start reading " + fileName);
        File file = new File(fileName);
        if (file.exists()) {
            return readFile(file);
        }
        try {
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(fileName);
            LOG.debug("Try to find url for " + file.getName());
            List<URL> urlList = Collections.list(urls);
            if (urlList == null || urlList.isEmpty()) {
                throw new EoException("Could not load url from " + file.getName() + " - " + file.getAbsolutePath());
            }
            return readFile(urlList.get(0));
        }
        catch (Exception e) {
            throw new EoException(e);
        }
    }

    public static String readFile(final File file) {
        try {
            return readFileBuffered(new FileReader(file));
        }
        catch (Exception e) {
            throw new EoException(e);
        }
    }

    public static void writeFile(URL url, String content)  {

        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(url.getFile()));
            out.write(content);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stores a file in utf-8 format without Exception throwing
     *
     * @param fileName
     * @param content
     * @return
     */
    public static String writeFile(String fileName, String content)  {
        return writeFile(new File(fileName), content);
    }

    public static String writeFile(File file, String content)  {
        if (!file.exists()) {
            LOG.info("New file for writing " + file.getAbsolutePath());
        } else {
            LOG.info("Overwrite file " + file.getAbsolutePath());
        }
        try {
            Writer writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        } catch (Exception e) {
            throw new EoException("Could not write to " + file.getAbsolutePath(), e);
        }
        return "";
    }

    /**
     * Stores a file in utf-8 format without Exception throwing
     *
     * @param fileName
     * @param content
     * @return
     */
    public static String writeFileWithoutException(String fileName, String content) {
        try {
            return writeFile(fileName, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
