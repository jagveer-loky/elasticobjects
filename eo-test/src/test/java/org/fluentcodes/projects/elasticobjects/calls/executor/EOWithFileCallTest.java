package org.fluentcodes.projects.elasticobjects.calls.executor;

import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.files.FileCallRead;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 18.04.2017.
 */
public class EOWithFileCallTest {
    private static final String METHOD_SOURCE_TXT = ".read(source.txt)";

    @Test
    public void addFileReadAction()  {
        final EO root = TestProviderRootTest.createEo();
        root.set(S_STRING, S_LEVEL0, S_LEVEL1);
        final EO child = root.getEo(S_LEVEL0);

        final Call call = new FileCallRead(FILE_SOURCE_TXT).setTargetPath(S_LEVEL0);
        child.addCall(call);

        Assert.assertEquals(Path.DELIMITER + S_LEVEL0, call.getTargetPath());

        root.execute();
        Assert.assertEquals(S_STRING, root.get(Path.ofs(S_LEVEL0, SAMPLE_CONTENT)));
    }

    @Test
    public void givenJsonWithSource_ok() {
        final String json = "{" +
                "\"(List)content\":" +
                "{" +
                "\"(FileCallRead)call\":" +
                "{" +
                "\"configKey\":\"" + FILE_SOURCE_TXT + "\"" +
                "}" +
                "}" +
                "}";
        EO eo = TestProviderRootTest.createEo(json);
        Assert.assertEquals(FileCallRead.class, eo.getEo("content/call").getModelClass());
        Assert.assertEquals(FILE_SOURCE_TXT, eo.get("content/call/configKey"));
        eo.execute();
    }
}
