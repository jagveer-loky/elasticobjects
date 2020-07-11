package org.fluentcodes.tools.xpect;

import org.fluentcodes.ihe.gematik.fdv.model.DocumentWithMetadata;
import org.fluentcodes.tools.testobjects.ForTestClass;
import org.javers.core.diff.Diff;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Some examples are from generated code delivered by a maven artifact from a project last year with gematik
 */
public class XpectEoTest {
    @Test
    public void testHashMap() {
        Map map = new LinkedHashMap<>();
        map.put("1", "test1");
        new XpectEo().compareAsString(map);
    }

    @Test
    public void testHashMapAsObject() {
        Map map = new LinkedHashMap<>();
        map.put("1", "test1");
        new XpectEo().compareAsObject(map);
    }

    @Test
    public void testForTestClass() {
        ForTestClass forTest  = ForTestClass.of1();
        new XpectEo().compareAsString(forTest);
    }
}
