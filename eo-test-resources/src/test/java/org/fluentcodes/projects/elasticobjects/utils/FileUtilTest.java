package org.fluentcodes.projects.elasticobjects.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.TEO_STATIC;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;

/**
 * Created by Werner on 14.05.2014.
 * A static test for FileUtil class
 */
public class FileUtilTest {
    private static final Logger LOG = LogManager.getLogger(FileUtilTest.class);
    private static final String TARGET = "/tmp/target.txt";

    @Test
    public void readFileTest() throws Exception {
        TestHelper.printStartMethod();
        if (!new File(TARGET).exists()) {
            FileUtil.writeFile(TARGET, TEO_STATIC.S_STRING_OTHER);
        }
        //String test = FileUtil.readFile(TARGET);
        //Assert.assertEquals(S_STRING_OTHER, test);
    }

    /**
     * Creates a file with sub direoctoris using {@File#mkdir} in temporary system directory.
     *
     * @throws Exception
     */
    @Test
    public void callFileMkdir() throws Exception {
        TestHelper.printStartMethod();
        String tmp = ReplaceUtil.getSystemValue("TMP");
        Assert.assertNotNull(tmp);
        final File targetFile = new File(tmp + "/level1/level2/level3/test.txt");
        File parent = targetFile.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        FileWriter fileWriter = new FileWriter(targetFile);
        fileWriter.write(TEO_STATIC.S_STRING_OTHER);
        Assert.assertTrue(targetFile.exists());

    }

}