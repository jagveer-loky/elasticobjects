package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class EoCompare2Test {
    @Ignore
    @Test
    public void test()  {
        final Map map = new HashMap();
        map.put(AnObject.MY_STRING, "value");
        final EO eo = ProviderRootTestScope.createEo();
        eo.mapObject(map);

        AnObject anObject = new AnObject();
        anObject.setMyString("value");
        final EO eo2 = ProviderRootTestScope.createEo();
        eo2.mapObject(anObject);

        StringBuilder diff = new StringBuilder();
        eo.compare(diff, eo2);
        Assert.assertEquals("", diff.toString());
    }
}
