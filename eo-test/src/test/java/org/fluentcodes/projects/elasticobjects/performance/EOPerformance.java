package org.fluentcodes.projects.elasticobjects.performance;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.TEO_STATIC;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;

import org.fluentcodes.projects.elasticobjects.test.*;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Ignore
public class EOPerformance {
    private static final Logger LOG = LogManager.getLogger(EOPerformance.class);
    private static final int maxRoot = 300000;

    @Test
    public void root()  {
        BasicTest basicTest = new BasicTest();
        basicTest.setTestString(TEO_STATIC.S_STRING);
        basicTest.setTestBoolean(false);
        rootPerformance(null);
        System.out.println("null         : " + rootPerformance(null));
        System.out.println(testMap(5));
        System.out.println(testList(5));
        System.out.println("BT empty     : " + rootPerformance(new BasicTest()));
        System.out.println("BT with 3    : " + rootPerformance(basicTest));
        //System.out.println("BT small     : " + rootPerformance(BTProvider.createSmall()));
        //System.out.println("BT small JSN : " + rootPerformance(BTProviderJSN.readSmall()));
        //System.out.println("BT JSN       : " + rootPerformance(BTProviderJSN.read()));
        //System.out.println("Map JSN      : " + rootPerformance(MapProviderJSN.read()));
        //System.out.println("List JSN     : " + rootPerformance(ListProviderJSN.readAll()));
    }

    private String rootPerformance(Object object)  {
        return setRootPerformance(object) + "";
    }

    private String testMap(int counter)  {
        Map<String, String> map = new HashMap<>();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < counter; i++) {
            builder.append("Map " + i + "       : " + rootPerformance(map) + "\n");
            map.put(new Integer(i).toString(), new Integer(i).toString());
        }
        return builder.toString();
    }

    private String testList(int counter)  {
        List<String> map = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < counter; i++) {
            builder.append("List " + i + "      : " + rootPerformance(map) + "\n");
            map.add(new Integer(i).toString());
        }
        return builder.toString();
    }
    private long setRootPerformance(Object object)  {
        long start = System.currentTimeMillis();
        for (long i = 0; i < maxRoot; i++) {
            TestProviderRootTest.createEo(object);
        }
        return System.currentTimeMillis() - start;
    }
}