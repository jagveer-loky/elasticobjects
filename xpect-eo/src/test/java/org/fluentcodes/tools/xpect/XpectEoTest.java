package org.fluentcodes.tools.xpect;

import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.Scope;
import org.fluentcodes.tools.testobjects.ForTestClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Some examples are from generated code delivered by a maven artifact from a project last year with gematik
 */
public class XpectEoTest {
    private static final ConfigMaps configMaps = new ConfigMaps(Scope.DEV);
    @Test
    public void testHashMap() {
        Map map = new LinkedHashMap<>();
        map.put("1", "test1");
        XpectEo.assertJunit(map);
    }

    @Test
    public void testHashMapAsObject() {
        Map map = new LinkedHashMap<>();
        map.put("1", "test1");
        XpectEo.assertEoJunit(map);
    }

    @Ignore
    @Test
    public void testForTestClass() {
        ForTestClass forTest  = ForTestClass.of1();
        XpectEo.assertJunit(forTest);
    }
}
