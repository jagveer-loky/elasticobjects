package org.fluentcodes.projects.elasticobjects.test;

import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.eo.EOBuilder;

import static org.fluentcodes.projects.elasticobjects.test.JSONInputReader.*;

/**
 * Provider for different SubTest objects with persistence directory input/json/ and ST* prefix
 *
 * @since 07.09.2018
 */
public class STProviderJSN {
    public static EOBuilder builder() {
        return TestEOProvider.createEOBuilder();
    }

    public static String readEmpty() throws Exception {
        return readInputJSN(TYPE.ST, EMPTY);
    }

    public static EO createEmpty() throws Exception {
        final EO eo = builder().map(readEmpty());
        STProviderEO.assertEmpty(eo);
        return eo;
    }

    public static EO compareEmpty() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.ST, EMPTY, STProviderEO.createEmpty());
        STProviderEO.assertEmpty(eo);
        return eo;
    }

    public static String readString() throws Exception {
        return readInputJSN(TYPE.ST, JSONInputReader.STRING);
    }

    public static EO createString() throws Exception {
        final EO eo = builder().map(readString());
        STProviderEO.assertString(eo);
        return eo;
    }

    public static EO compareString() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.ST, JSONInputReader.STRING, STProviderEO.createString());
        STProviderEO.assertString(eo);
        return eo;
    }

    public static String readName() throws Exception {
        return readInputJSN(TYPE.ST, JSONInputReader.NAME);
    }

    public static EO createName() throws Exception {
        final EO eo = builder().map(readName());
        STProviderEO.assertName(eo);
        return eo;
    }

    public static EO compareName() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.ST, JSONInputReader.NAME, STProviderEO.createName());
        STProviderEO.assertName(eo);
        return eo;
    }

    public static String readST() throws Exception {
        return readInputJSN(TYPE.ST, SUB_TEST);
    }

    public static EO createST() throws Exception {
        final EO eo = builder().map(readST());
        STProviderEO.assertST(eo);
        return eo;
    }

    public static EO compareST() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.ST, SUB_TEST, STProviderEO.createST());
        STProviderEO.assertST(eo);
        return eo;
    }

    public static String readSimple() throws Exception {
        return readInputJSN(TYPE.ST, SIMPLE);
    }

    public static EO createSimple() throws Exception {
        final EO eo = builder().map(readSimple());
        STProviderEO.assertSimple(eo);
        return eo;
    }

    public static EO compareSimple() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.ST, SIMPLE, STProviderEO.createSimple());
        STProviderEO.assertSimple(eo);
        return eo;
    }

    public static String readAll() throws Exception {
        return readInputJSN(TYPE.ST, ALL);
    }

    public static EO create() throws Exception {
        final EO eo = builder().map(readAll());
        STProviderEO.asserts(eo);
        return eo;
    }

    public static EO compare() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.ST, ALL, STProviderEO.create());
        STProviderEO.asserts(eo);
        return eo;
    }


}