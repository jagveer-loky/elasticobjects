package org.fluentcodes.projects.elasticobjects.test;

import com.sun.javafx.collections.MappingChange;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.eo.EOBuilder;
import org.fluentcodes.projects.elasticobjects.eo.LogLevel;
import org.junit.Assert;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_CONTENT;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class MapProviderEO {
    public static EOBuilder builder() {
        return TestEOProvider.createEOBuilder();
    }

    public static EO createEmpty()  {
        final EO eo = builder()
                .setModels(MappingChange.Map.class)
                .map(MapProvider.createEmpty());
        BTProviderEO.assertEmpty(eo);
        return eo;
    }

    public static EO createString()  {
        final EO eo = builder().setModels(Map.class).map(MapProvider.createString());
        BTProviderEO.assertString(eo);
        return eo;
    }

    public static EO createInteger()  {
        final EO eo = builder().setModels(Map.class).map(MapProvider.createInteger());
        BTProviderEO.assertInteger(eo);
        return eo;
    }

    public static EO createLong()  {
        final EO eo = builder().setModels(Map.class).map(MapProvider.createLong());
        BTProviderEO.assertLong(eo);
        return eo;
    }

    public static EO createFloat()  {
        final EO eo = builder().setModels(Map.class).map(MapProvider.createFloat());
        BTProviderEO.assertFloat(eo);
        return eo;
    }

    public static EO createDouble()  {
        final EO eo = builder().setModels(Map.class).map(MapProvider.createDouble());
        BTProviderEO.assertDouble(eo);
        return eo;
    }

    public static EO createDate()  {
        final EO eo = builder().setModels(Map.class).map(MapProvider.createDate());
        BTProviderEO.assertDate(eo);
        return eo;
    }

    public static EO createBoolean()  {
        final EO eo = builder().setModels(Map.class).map(MapProvider.createBoolean());
        BTProviderEO.assertBoolean(eo);
        return eo;
    }

    public static EO createMap()  {
        final EO eo = builder().setModels(Map.class).map(MapProvider.createMap());
        BTProviderEO.assertMap(eo);
        return eo;
    }

    public static EO createList()  {
        final EO eo = builder().setModels(Map.class).map(MapProvider.createList());
        BTProviderEO.assertList(eo);
        return eo;
    }

    public static EO createBT()  {
        final EO eo = builder().setModels(Map.class).map(MapProvider.createBT());
        BTProviderEO.assertBT(eo);
        return eo;
    }

    public static EO createST()  {
        final EO eo = builder().setModels(Map.class).map(MapProvider.createST());
        BTProviderEO.assertST(eo);
        return eo;
    }

    public static EO createMapST()  {
        final EO eo = builder().setModels(Map.class).map(MapProvider.createMapST());
        BTProviderEO.assertMapST(eo);
        return eo;
    }

    public static EO createListST()  {
        final EO eo = builder().setModels(Map.class).map(MapProvider.createListST());
        BTProviderEO.assertListST(eo);
        return eo;
    }

    public static EO createSimple()  {
        final EO eo = builder().setModels(Map.class).map(MapProvider.createSimple());
        BTProviderEO.assertSimple(eo);
        return eo;
    }

    public static EO createSmall()  {
        final EO eo = builder().setModels(Map.class).map(MapProvider.createSmall());
        BTProviderEO.assertSmall(eo);
        return eo;
    }

    public static EO create()  {
        final EO eo = builder().setModels(Map.class).map(MapProvider.create());
        BTProviderEO.asserts(eo);
        return eo;
    }


    public static EO createContent(String content)  {
        final EO eo = createEmpty();
        eo.add(F_CONTENT).set(content);
        Assert.assertEquals(INFO_COMPARE_FAILS, content, eo.get(F_CONTENT));
        return eo;
    }


    public static EO createBigEO(int length)  {
        final EO adapter = TestEOProvider.createEmptyMap();
        for (int i = 0; i < length; i++) {
            adapter.add("key" + i).set(i);
        }
        return adapter;
    }

    public static EO createBigAEOUnexpanded(int length)  {
        return TestEOProvider.createEOBuilder()
                .set(MapProvider.createBig(length));
    }


    public static EO createWithLongPathAndValueString()  {
        return TestEOProvider.createEOBuilder()
                .setPath(toPath(S_LEVEL0, S_LEVEL1, S_LEVEL2, S_KEY0))
                .setLogLevel(LogLevel.INFO)
                .set(S_STRING);
    }


    public static final EO createSimpleInsertWithPath() {
        try {
            EO adapter = TestEOProvider.createEmptyMap();
            adapter
                    .add(S_KEY0)
                    .set(S_STRING);
            adapter
                    .add(toPath(S_LEVEL0, S_KEY0))
                    .set(S_STRING);
            return adapter;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final EO createDeepPathValueAdapter() {
        try {
            EO child = TestEOProvider.createEOBuilder()
                    .setPath(toPath(S_LEVEL0, S_LEVEL1, S_LEVEL2, S_LEVEL3, S_LEVEL4, S_TEST_STRING))
                    .set(S_STRING);
            return child.getRoot();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}