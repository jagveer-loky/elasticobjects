package org.fluentcodes.projects.elasticobjects.test;

import org.fluentcodes.projects.elasticobjects.eo.EO;


import static org.fluentcodes.projects.elasticobjects.test.JSONInputReader.*;

/**
 * Provider for different SubTest objects with persistence directory input/json/ and ST* prefix
 *
 * @since 07.09.2018
 */
public class STProviderJSN {
    
    public static String readEmpty()  {
        return readInputJSN(TYPE.ST, EMPTY);
    }

    public static EO createEmpty()  {
        final EO eo = TestEOProvider.create(readEmpty());
        STProviderEO.assertEmpty(eo);
        return eo;
    }

    public static EO compareEmpty()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.ST, EMPTY, STProviderEO.createEmpty());
        STProviderEO.assertEmpty(eo);
        return eo;
    }

    public static String readString()  {
        return readInputJSN(TYPE.ST, JSONInputReader.STRING);
    }

    public static EO createString()  {
        final EO eo = TestEOProvider.create(readString());
        STProviderEO.assertString(eo);
        return eo;
    }

    public static EO compareString()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.ST, JSONInputReader.STRING, STProviderEO.createString());
        STProviderEO.assertString(eo);
        return eo;
    }

    public static String readName()  {
        return readInputJSN(TYPE.ST, JSONInputReader.NAME);
    }

    public static EO createName()  {
        final EO eo = TestEOProvider.create(readName());
        STProviderEO.assertName(eo);
        return eo;
    }

    public static EO compareName()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.ST, JSONInputReader.NAME, STProviderEO.createName());
        STProviderEO.assertName(eo);
        return eo;
    }

    public static String readST()  {
        return readInputJSN(TYPE.ST, SUB_TEST);
    }

    public static EO createST()  {
        final EO eo = TestEOProvider.create(readST());
        STProviderEO.assertST(eo);
        return eo;
    }

    public static EO compareST()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.ST, SUB_TEST, STProviderEO.createST());
        STProviderEO.assertST(eo);
        return eo;
    }

    public static String readSimple()  {
        return readInputJSN(TYPE.ST, SIMPLE);
    }

    public static EO createSimple()  {
        final EO eo = TestEOProvider.create(readSimple());
        STProviderEO.assertSimple(eo);
        return eo;
    }

    public static EO compareSimple()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.ST, SIMPLE, STProviderEO.createSimple());
        STProviderEO.assertSimple(eo);
        return eo;
    }

    public static String readAll()  {
        return readInputJSN(TYPE.ST, ALL);
    }

    public static EO create()  {
        final EO eo = TestEOProvider.create(readAll());
        STProviderEO.asserts(eo);
        return eo;
    }

    public static EO compare()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.ST, ALL, STProviderEO.create());
        STProviderEO.asserts(eo);
        return eo;
    }


}