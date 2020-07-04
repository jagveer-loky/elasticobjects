package org.fluentcodes.projects.elasticobjects.test;

import org.junit.Assert;

import static org.fluentcodes.projects.elasticobjects.test.AssertBase.getFileName;
import static org.fluentcodes.projects.elasticobjects.test.AssertBase.readOrWritePersisted;

public class AssertString {
    public static final String compare(final String toCompare)  {
        Assert.assertNotNull(toCompare);
        String fileName = getFileName() + ".string";
        compare(fileName, toCompare);
        return fileName;
    }

    protected static final void compare(final String fileName, final String toCompare)  {
        Assert.assertNotNull("Compare not started since fileName is null", fileName);
        Assert.assertFalse("Compare not started since fileName is empty. FileName is " + fileName, fileName.isEmpty());
        Assert.assertNotNull("Compare not started since input is null. FileName is " + fileName, toCompare);
        String serialized = readOrWritePersisted(fileName, toCompare);
        Assert.assertNotNull("Compare not started since serialized is null. FileName is " + fileName, serialized);
        Assert.assertFalse("Compare not started since serialized is empty. FileName is " + fileName, serialized.isEmpty());
        // CORE COMPARE!!!
        Assert.assertEquals("Compare fails for " + fileName, serialized, toCompare);
    }

}
