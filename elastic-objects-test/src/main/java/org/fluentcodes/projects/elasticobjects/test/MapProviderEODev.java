package org.fluentcodes.projects.elasticobjects.test;

import org.fluentcodes.projects.elasticobjects.eo.EO;


import java.util.Map;

public class MapProviderEODev {
    public static EO createEmpty()  {
        final EO eo = DevObjectProvider.createEO(MapProvider.createEmpty());
        BTProviderEO.assertEmpty(eo);
        return eo;
    }

    public static EO createString()  {
        final EO eo = DevObjectProvider.createEO(MapProvider.createString());
        BTProviderEO.assertString(eo);
        return eo;
    }

    public static EO createInteger()  {
        final EO eo = DevObjectProvider.createEO(MapProvider.createInteger());
        BTProviderEO.assertInteger(eo);
        return eo;
    }

    public static EO createLong()  {
        final EO eo = DevObjectProvider.createEO(MapProvider.createLong());
        BTProviderEO.assertLong(eo);
        return eo;
    }

    public static EO createDouble()  {
        final EO eo = DevObjectProvider.createEO(MapProvider.createDouble());
        BTProviderEO.assertDouble(eo);
        return eo;
    }

    public static EO createBoolean()  {
        final EO eo = DevObjectProvider.createEO(MapProvider.createBoolean());
        BTProviderEO.assertBoolean(eo);
        return eo;
    }

    public static EO createMap()  {
        final EO eo = DevObjectProvider.createEO(MapProvider.createMap());
        BTProviderEO.assertMap(eo);
        return eo;
    }

    public static EO createSmall()  {
        final EO eo = DevObjectProvider.createEO(MapProvider.createSmall());
        BTProviderEO.assertSmall(eo);
        return eo;
    }
}