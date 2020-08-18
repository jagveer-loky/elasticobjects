package org.fluentcodes.tools.xpect;

import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.tools.testobjects.ForTestClass;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class IOJsonEoTest {
    public static final EOConfigsCache CACHE = new EOConfigsCache();
    @Test
    public void testHashMap() {
        Map map = new LinkedHashMap<>();
        map.put("1", "test1");
        new XpectEo(CACHE).write(map);
    }

    @Test
    public void testForTestClass() {
        ForTestClass forTest  = ForTestClass.of1();
        new XpectEo(CACHE).write(forTest);
    }

}
