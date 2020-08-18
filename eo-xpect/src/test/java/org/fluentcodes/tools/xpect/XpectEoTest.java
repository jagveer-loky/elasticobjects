package org.fluentcodes.tools.xpect;

import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.Scope;
import org.fluentcodes.tools.testobjects.ForTestClass;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Some examples are from generated code delivered by a maven artifact from a project last year with gematik
 */
public class XpectEoTest {
    private static final EOConfigsCache cache = new EOConfigsCache(Scope.DEV);
    @Test
    public void testHashMap() {
        Map map = new LinkedHashMap<>();
        map.put("1", "test1");
        new XpectEo(cache).compareAsString(map);
    }

    @Test
    public void testHashMapAsObject() {
        Map map = new LinkedHashMap<>();
        map.put("1", "test1");
        new XpectEo(cache).compareAsObject(map);
    }

    @Test
    public void testForTestClass() {
        ForTestClass forTest  = ForTestClass.of1();
        new XpectEo(cache).compareAsString(forTest);
    }
}
