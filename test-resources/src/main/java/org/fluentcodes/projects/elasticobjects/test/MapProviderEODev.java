package org.fluentcodes.projects.elasticobjects.test;

import com.sun.javafx.collections.MappingChange;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.eo.EOBuilder;

import java.util.Map;

public class MapProviderEODev {
    public static EOBuilder builder() {
        return DevObjectProvider.createEOBuilder();
    }

    public static EO createEmpty() throws Exception {
        final EO eo = builder()
                .setModels(MappingChange.Map.class)
                .map(MapProvider.createEmpty());
        BTProviderEO.assertEmpty(eo);
        return eo;
    }

    public static EO createString() throws Exception {
        final EO eo = builder().setModels(Map.class).map(MapProvider.createString());
        BTProviderEO.assertString(eo);
        return eo;
    }

    public static EO createInteger() throws Exception {
        final EO eo = builder().setModels(Map.class).map(MapProvider.createInteger());
        BTProviderEO.assertInteger(eo);
        return eo;
    }

    public static EO createLong() throws Exception {
        final EO eo = builder().setModels(Map.class).map(MapProvider.createLong());
        BTProviderEO.assertLong(eo);
        return eo;
    }

    public static EO createDouble() throws Exception {
        final EO eo = builder().setModels(Map.class).map(MapProvider.createDouble());
        BTProviderEO.assertDouble(eo);
        return eo;
    }

    public static EO createBoolean() throws Exception {
        final EO eo = builder().setModels(Map.class).map(MapProvider.createBoolean());
        BTProviderEO.assertBoolean(eo);
        return eo;
    }

    public static EO createMap() throws Exception {
        final EO eo = builder().setModels(Map.class).map(MapProvider.createMap());
        BTProviderEO.assertMap(eo);
        return eo;
    }

    public static EO createSmall() throws Exception {
        final EO eo = builder().setModels(Map.class).map(MapProvider.createSmall());
        BTProviderEO.assertSmall(eo);
        return eo;
    }
}