package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_KEY0;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_KEY1;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL0;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL1;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL2;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL3;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_STRING;

/**
 * Created by Werner on 22.03.2017.
 */
public class EOKeysTest {

    @Test
    public void keyPath()  {
        EO eo = ProviderConfigMaps.createEo();
        eo.set(S_STRING, S_LEVEL3);
        eo.set(S_STRING, S_LEVEL0, S_LEVEL1, S_KEY0);
        eo.set(S_STRING, S_LEVEL0, S_LEVEL2, S_KEY0);
        eo.set(S_STRING, S_LEVEL0, S_LEVEL2, S_KEY1);

        List<String> keys = eo.filterPaths(S_LEVEL3);
        Assert.assertEquals(1, keys.size());
        Assert.assertEquals(S_LEVEL3, keys.get(0));

        /*keys = eo.filterPaths(path1);
        Assert.assertEquals(1, keys.size());
        Assert.assertEquals(path1, keys.get(0));

        keys = eo.filterPaths(PathElement.MATCHER);
        Assert.assertEquals(2, keys.size());
        Assert.assertEquals(S_LEVEL3, keys.get(0));
        Assert.assertEquals(S_LEVEL0, keys.get(1));

        keys = eo.filterPaths(PathElement.MATCHER_ALL);
        Assert.assertEquals(4, keys.size());
        Assert.assertEquals(S_LEVEL3, keys.get(0));
        Assert.assertEquals(path1, keys.get(1));

        final String pathMatcher = Path.ofs(S_LEVEL0, PathElement.MATCHER, S_KEY0);
        keys = eo.filterPaths(pathMatcher);
        Assert.assertEquals(2, keys.size());
        Assert.assertEquals(path1, keys.get(0));

        keys = eo.filterPaths(Path.ofs(S_LEVEL0, PathElement.MATCHER));
        Assert.assertEquals(2, keys.size());
        Assert.assertEquals(Path.ofs(S_LEVEL0, S_LEVEL1), keys.get(0));

        keys = eo.filterPaths(Path.ofs(S_LEVEL0, PathElement.MATCHER, S_LEVEL4));
        Assert.assertEquals(0, keys.size());

        keys = eo.filterPaths(".*0");
        Assert.assertEquals(1, keys.size());
        Assert.assertEquals(S_LEVEL0, keys.get(0));

        keys = eo.filterPaths("^" + S_LEVEL3);
        Assert.assertEquals(1, keys.size());
        Assert.assertEquals(S_LEVEL3, keys.get(0));*/

    }

}
