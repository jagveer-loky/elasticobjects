package org.fluentcodes.projects.elasticobjects.test;
import org.fluentcodes.projects.elasticobjects.eo.EO;

import org.fluentcodes.projects.elasticobjects.eo.LogLevel;
import org.junit.Assert;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_CONTENT;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class MapProviderEO {
    public static EO createEmpty()  {
        final EO eo = TestEOProvider.create(Map.class)
                ;eo.mapObject(MapProvider.createEmpty());
        BTProviderEO.assertEmpty(eo);
        return eo;
    }

    public static EO createString()  {
        final EO eo = TestEOProvider.create(Map.class);eo.mapObject(MapProvider.createString());
        BTProviderEO.assertString(eo);
        return eo;
    }

    public static EO createInteger()  {
        final EO eo = TestEOProvider.create(Map.class);eo.mapObject(MapProvider.createInteger());
        BTProviderEO.assertInteger(eo);
        return eo;
    }

    public static EO createLong()  {
        final EO eo = TestEOProvider.create(Map.class);eo.mapObject(MapProvider.createLong());
        BTProviderEO.assertLong(eo);
        return eo;
    }

    public static EO createFloat()  {
        final EO eo = TestEOProvider.create(Map.class);eo.mapObject(MapProvider.createFloat());
        BTProviderEO.assertFloat(eo);
        return eo;
    }

    public static EO createDouble()  {
        final EO eo = TestEOProvider.create(Map.class);eo.mapObject(MapProvider.createDouble());
        BTProviderEO.assertDouble(eo);
        return eo;
    }

    public static EO createDate()  {
        final EO eo = TestEOProvider.create(Map.class);eo.mapObject(MapProvider.createDate());
        BTProviderEO.assertDate(eo);
        return eo;
    }

    public static EO createBoolean()  {
        final EO eo = TestEOProvider.create(Map.class);eo.mapObject(MapProvider.createBoolean());
        BTProviderEO.assertBoolean(eo);
        return eo;
    }

    public static EO createMap()  {
        final EO eo = TestEOProvider.create(Map.class);eo.mapObject(MapProvider.createMap());
        BTProviderEO.assertMap(eo);
        return eo;
    }

    public static EO createList()  {
        final EO eo = TestEOProvider.create(Map.class);eo.mapObject(MapProvider.createList());
        BTProviderEO.assertList(eo);
        return eo;
    }

    public static EO createBT()  {
        final EO eo = TestEOProvider.create(Map.class);eo.mapObject(MapProvider.createBT());
        BTProviderEO.assertBT(eo);
        return eo;
    }

    public static EO createST()  {
        final EO eo = TestEOProvider.create(Map.class);eo.mapObject(MapProvider.createST());
        BTProviderEO.assertST(eo);
        return eo;
    }

    public static EO createMapST()  {
        final EO eo = TestEOProvider.create(Map.class);eo.mapObject(MapProvider.createMapST());
        BTProviderEO.assertMapST(eo);
        return eo;
    }

    public static EO createListST()  {
        final EO eo = TestEOProvider.create(Map.class);eo.mapObject(MapProvider.createListST());
        BTProviderEO.assertListST(eo);
        return eo;
    }

    public static EO createSimple()  {
        final EO eo = TestEOProvider.create(Map.class);eo.mapObject(MapProvider.createSimple());
        BTProviderEO.assertSimple(eo);
        return eo;
    }

    public static EO createSmall()  {
        final EO eo = TestEOProvider.create(Map.class);eo.mapObject(MapProvider.createSmall());
        BTProviderEO.assertSmall(eo);
        return eo;
    }

    public static EO create()  {
        final EO eo = TestEOProvider.create(Map.class);eo.mapObject(MapProvider.create());
        BTProviderEO.asserts(eo);
        return eo;
    }


    public static EO createContent(String content)  {
        final EO eo = createEmpty();
        eo.setPathValue(F_CONTENT, content);
        Assert.assertEquals(INFO_COMPARE_FAILS, content, eo.get(F_CONTENT));
        return eo;
    }


    public static EO createBigEO(int length)  {
        final EO adapter = TestEOProvider.create();
        for (int i = 0; i < length; i++) {
            adapter.setPathValue("key" + i, i);
        }
        return adapter;
    }

    public static EO createBigAEOUnexpanded(int length)  {
        return TestEOProvider.create(MapProvider.createBig(length));
    }


    public static EO createWithLongPathAndValueString()  {
        return TestEOProvider.create().setPathValue(toPath(S_LEVEL0, S_LEVEL1, S_LEVEL2, S_KEY0),S_STRING);
    }


    public static final EO createSimpleInsertWithPath() {
        try {
            EO adapter = TestEOProvider.create();
            adapter
                    .setPathValue(S_KEY0, S_STRING);
            adapter
                    .setPathValue(toPath(S_LEVEL0, S_KEY0),S_STRING);
            return adapter;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final EO createDeepPathValueAdapter() {
        try {
            EO child = TestEOProvider.create().setPathValue(toPath(S_LEVEL0, S_LEVEL1, S_LEVEL2, S_LEVEL3, S_LEVEL4, S_TEST_STRING),S_STRING);
            return child.getRoot();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}