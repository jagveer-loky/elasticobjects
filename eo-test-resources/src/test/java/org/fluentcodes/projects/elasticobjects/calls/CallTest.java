package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created 12.6.2018
 */
public class CallTest {
    @Test
    public void emptyAction() throws Exception {
        Call call = new Call();
        Assert.assertNull(call.getPath());
        Assert.assertNull(call.getMapPath());
        Assert.assertFalse(call.isAbsolutePath());
        Assert.assertFalse(call.isDynamicKey());
        Assert.assertFalse(call.isDynamicMapPath());
    }

    @Test
    public void withPath() throws Exception {
        Call call = new Call();
        call.setPath(S_LEVEL0);
        Assert.assertEquals(S_LEVEL0, call.getPath());
        Assert.assertEquals(S_LEVEL0, call.getMergePath());
        Assert.assertFalse(call.isAbsolutePath());
        Assert.assertFalse(call.isDynamicKey());
        Assert.assertFalse(call.isDynamicMapPath());
    }

    @Test
    public void withMapPath() throws Exception {
        Call call = new Call();
        call.setMapPath(S_LEVEL0);
        Assert.assertEquals(S_LEVEL0, call.getMapPath());
        Assert.assertEquals(S_LEVEL0, call.getMergePath());
        Assert.assertFalse(call.isAbsolutePath());
        Assert.assertFalse(call.isDynamicKey());
        Assert.assertFalse(call.isDynamicMapPath());
    }

    @Test
    public void withMapPathAbsolute() throws Exception {
        Call call = new Call();
        call.setMapPath(S_LEVEL0);
        Assert.assertEquals(S_LEVEL0, call.getMapPath());
        Assert.assertEquals(S_LEVEL0, call.getMergePath());
        Assert.assertFalse(call.isAbsolutePath());
        Assert.assertFalse(call.isDynamicKey());
        Assert.assertFalse(call.isDynamicMapPath());
    }

    @Test
    public void withMapPathDynamic() throws Exception {
        Call call = new Call();
        call.setMapPath(S_PATH_DYNAMIC);
        Assert.assertEquals(S_PATH_DYNAMIC, call.getMapPath());
        Assert.assertEquals(S_PATH_DYNAMIC, call.getMergePath());
        Assert.assertFalse(call.isAbsolutePath());
        Assert.assertFalse(call.isDynamicKey());
        Assert.assertTrue(call.isDynamicMapPath());
    }

    @Test
    public void withPathAbsolute() throws Exception {
        Call call = new Call();
        call.setPath(Path.DELIMITER + S_KEY0);
        Assert.assertEquals(Path.DELIMITER + S_KEY0, call.getPath());
        Assert.assertEquals(Path.DELIMITER + S_KEY0, call.getMergePath());
        Assert.assertTrue(call.isAbsolutePath());
        Assert.assertFalse(call.isDynamicKey());
        Assert.assertFalse(call.isDynamicMapPath());
    }

    @Test
    public void withPathAndMapPath() throws Exception {
        Call call = new Call();
        call.setPath(S_LEVEL0);
        call.setMapPath(S_LEVELMAP);
        Assert.assertEquals(S_LEVEL0, call.getPath());
        Assert.assertEquals(S_LEVELMAP, call.getMapPath());
        Assert.assertEquals(S_LEVEL0_LEVELMAP, call.getMergePath());
        Assert.assertFalse(call.isAbsolutePath());
        Assert.assertFalse(call.isDynamicKey());
        Assert.assertFalse(call.isDynamicMapPath());
    }

    @Test
    public void withPathAbsoluteAndMapPath() throws Exception {
        Call call = new Call();
        call.setPath(Path.DELIMITER + S_LEVEL0);
        call.setMapPath(S_KEY_MAP);
        Assert.assertEquals(Path.DELIMITER + S_LEVEL0, call.getPath());
        Assert.assertEquals(S_KEY_MAP, call.getMapPath());
        Assert.assertEquals(Path.DELIMITER + toPath(S_LEVEL0, S_KEY_MAP), call.getMergePath());
        Assert.assertTrue(call.isAbsolutePath());
        Assert.assertFalse(call.isDynamicKey());
        Assert.assertFalse(call.isDynamicMapPath());
    }
}
