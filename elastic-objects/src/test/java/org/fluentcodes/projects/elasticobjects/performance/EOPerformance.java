package org.fluentcodes.projects.elasticobjects.performance;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.TEO_STATIC;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.eo.EOBuilder;
import org.fluentcodes.projects.elasticobjects.test.*;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Ignore
public class EOPerformance extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(EOPerformance.class);
    private static final int maxRoot = 300000;

    private EOBuilder builder() throws Exception {
        return TestEOProvider.createEOBuilder();
    }

    @Test
    public void root() throws Exception {
        builder().build();
        BasicTest basicTest = new BasicTest();
        basicTest.setTestString(TEO_STATIC.S_STRING);
        basicTest.setTestBoolean(false);
        rootPerformance(null);
        System.out.println("null         : " + rootPerformance(null));
        System.out.println(testMap(5));
        System.out.println(testList(5));
        System.out.println("BT empty     : " + rootPerformance(new BasicTest()));
        System.out.println("BT with 3    : " + rootPerformance(basicTest));
        System.out.println("BT small     : " + rootPerformance(BTProvider.createSmall()));
        System.out.println("BT small JSN : " + rootPerformance(BTProviderJSN.readSmall()));
        System.out.println("BT JSN       : " + rootPerformance(BTProviderJSN.read()));
        System.out.println("Map JSN      : " + rootPerformance(MapProviderJSN.read()));
        System.out.println("List JSN     : " + rootPerformance(ListProviderJSN.readAll()));
    }

    private String rootPerformance(Object object) throws Exception {
        return setRootPerformance(object) + " - " + mapRootPerformance(object);
    }

    private String testMap(int counter) throws Exception {
        Map<String, String> map = new HashMap<>();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < counter; i++) {
            builder.append("Map " + i + "       : " + rootPerformance(map) + "\n");
            map.put(new Integer(i).toString(), new Integer(i).toString());
        }
        return builder.toString();
    }

    private String testList(int counter) throws Exception {
        List<String> map = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < counter; i++) {
            builder.append("List " + i + "      : " + rootPerformance(map) + "\n");
            map.add(new Integer(i).toString());
        }
        return builder.toString();
    }

    private long mapRootPerformance(Object object) throws Exception {
        long start = System.currentTimeMillis();
        for (long i = 0; i < maxRoot; i++) {
            builder()
                    .map(object);
        }
        return System.currentTimeMillis() - start;
    }

    private long setRootPerformance(Object object) throws Exception {
        long start = System.currentTimeMillis();
        for (long i = 0; i < maxRoot; i++) {
            builder()
                    .set(object);
        }
        return System.currentTimeMillis() - start;
    }
}