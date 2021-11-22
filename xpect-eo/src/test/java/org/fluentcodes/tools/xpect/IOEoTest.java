package org.fluentcodes.tools.xpect;

import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.Scope;
import org.fluentcodes.tools.testobjects.ForTestClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;
public class IOEoTest {

    static ConfigMaps configMaps = new ConfigMaps(Scope.TEST);
    @Ignore
    @Test
    public void testHashMap() {
        Map map = new LinkedHashMap<>();
        map.put("1", "test1");
        XpectEo.assertJunit(map);
    }
    @Ignore
    @Test
    public void testForTestClass() {
        ForTestClass forTest  = ForTestClass.of1();
        XpectEo.assertJunit(forTest);
    }

}
