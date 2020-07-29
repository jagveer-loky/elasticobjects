package org.fluentcodes.projects.elasticobjects.utils;

import org.fluentcodes.projects.elasticobjects.calls.templates.ReplaceUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by werner.diwischek on 07.01.18.
 */
public class ReplaceUtilTest {
    @Test
    public void replaceTmp() {
        String replace = "$[TMP]";
        String result = ReplaceUtil.replace(replace);
        Assert.assertNotNull(result);
        Assert.assertFalse(result.contains("TMP"));
    }

    @Test
    public void noReplace() {
        String replace = "#[TMP]";
        String result = ReplaceUtil.replace(replace);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains("#"));
    }
}
