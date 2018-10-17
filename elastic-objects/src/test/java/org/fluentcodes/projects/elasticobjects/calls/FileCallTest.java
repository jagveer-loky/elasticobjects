package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.test.TestCallsProvider;
import org.fluentcodes.projects.elasticobjects.test.MapProviderEO;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_CONTENT;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 11.10.2016.
 */
public class FileCallTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(FileCallTest.class);

    @Test
    public void readSourceCachedTxt() throws Exception {
        final FileCall call = TestCallsProvider.createFileCall( FILE_SOURCE_CACHED_TXT );
        final String content = call.read();
        Assert.assertEquals(S_STRING, content);
    }

    @Test
    /**
     * Gets the local:tmp:source.txt entry from the AssetProviders
     */
    public void readSourceTxt() throws Exception {
        final FileCall call = TestCallsProvider.createFileCall( FILE_SOURCE_TXT);
        final String content = call.read();
        Assert.assertEquals(S_STRING, content);
    }

    @Test
    public void writeStringTargetTxt() throws Exception {
        final FileCall call = TestCallsProvider.createFileCall( FILE_TARGET_TXT);

        call.write(S_STRING_OTHER);

        Assert.assertEquals(S_STRING_OTHER, call.read());

        final File file = call.getFile();
        Assert.assertTrue(file.exists());
    }


    @Test
    public void writeResultWithMapPathString() throws Exception {
        final FileCall call = TestCallsProvider.createFileCall( FILE_RESULT_WITH_MAP_PATH_STRING);

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

        Assert.assertEquals(S_STRING,eoString.get(F_TEST_STRING));
    }

    @Test
    public void writeResultWithMapPathStringWithMapPath() throws Exception {
        final FileCall call =  TestCallsProvider.createFileCall( FILE_RESULT_WITH_MAP_PATH_STRING);

        final EO eoString = MapProviderEO.createString();
        Map attributes = TestCallsProvider.setMapPath(F_TEST_STRING);
        call.write(eoString, attributes);

        final EO eoEmpty = MapProviderEO.create();
        call.read(eoEmpty, attributes);

        Assert.assertEquals(S_STRING, eoEmpty.get(F_TEST_STRING));
    }
}
