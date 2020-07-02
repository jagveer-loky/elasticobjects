package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.test.MapProviderEO;
import org.fluentcodes.projects.elasticobjects.test.TestCallsProvider;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_CONTENT;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.FILE_RESULT_STRING;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.FILE_RESULT_WITH_MAP_PATH_STRING;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 11.10.2016.
 */
public class FileCallTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(FileCallTest.class);

    @Test
    public void readSourceCachedTxt() throws Exception {
        final FileCall call = TestCallsProvider.createFileCall(FILE_SOURCE_CACHED_TXT);
        final String content = call.read();
        Assert.assertEquals(S_STRING, content);
    }

    @Test
    /**
     * Gets the local:tmp:source.txt entry from the AssetProviders
     */
    public void readSourceTxt() throws Exception {
        final FileCall call = TestCallsProvider.createFileCall(FILE_SOURCE_TXT);
        final String content = call.read();
        Assert.assertEquals(S_STRING, content);
    }

    @Test
    public void readSourceCsvGuest_ok() throws Exception {
        final FileCall call = TestCallsProvider.createFileCall(FILE_SOURCE_TXT);
        EO eo = TestEOProvider.create();
        eo.setRoles(R_GUEST);
        call.read(eo);
        TestObjectProvider.checkLogEmpty(eo);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eo.get(F_CONTENT));
    }

    @Test
    public void readSourceCsvAnonym_hasLog() throws Exception {
        final FileCall call = TestCallsProvider.createFileCall(FILE_SOURCE_TXT);
        EO eo = TestEOProvider.create();
        eo.setRoles(R_ANONYM);
        call.read(eo);
        TestObjectProvider.checkLogNotEmpty(eo);
        Assert.assertNull(INFO_NULL_FAILS, eo.get(F_CONTENT));
    }

    @Test
    public void writeStringTargetTxt() throws Exception {
        final FileCall call = TestCallsProvider.createFileCall(FILE_TARGET_TXT);

        call.write(S_STRING_OTHER);

        Assert.assertEquals(S_STRING_OTHER, call.read());

        final File file = call.getFile();
        Assert.assertTrue(file.exists());
    }


    @Test
    public void writeResultWithMapPathString() throws Exception {
        final FileCall call = TestCallsProvider.createFileCall(FILE_RESULT_WITH_MAP_PATH_STRING);

        final EO eoWithContent = MapProviderEO.createContent(S_STRING);
        call.write(eoWithContent);

        final EO eoEmpty = MapProviderEO.create();
        call.read(eoEmpty);

        Assert.assertEquals(S_STRING, eoEmpty.get(F_CONTENT));
    }


    @Test
    public void writeResultWithMapPathStringWithSetMapPath() throws Exception {
        FileCall call = TestCallsProvider.createFileCall(FILE_RESULT_STRING);
        call.setMapPath(F_TEST_STRING);

        final EO eoString = MapProviderEO.createString();
        call.write(eoString);

        final EO eoEmpty = MapProviderEO.create();
        call.read(eoEmpty);

        Assert.assertEquals(S_STRING, eoString.get(F_TEST_STRING));
    }

    @Test
    public void writeResultWithMapPathStringWithMapPath() throws Exception {
        final FileCall call = TestCallsProvider.createFileCall(FILE_RESULT_WITH_MAP_PATH_STRING);

        final EO eoString = MapProviderEO.createString();
        Map attributes = TestCallsProvider.setMapPath(F_TEST_STRING);
        call.write(eoString, attributes);

        final EO eoEmpty = MapProviderEO.create();
        call.read(eoEmpty, attributes);

        Assert.assertEquals(S_STRING, eoEmpty.get(F_TEST_STRING));
    }
}
