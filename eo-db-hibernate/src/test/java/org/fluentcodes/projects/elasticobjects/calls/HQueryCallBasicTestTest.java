package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.TEO_STATIC;
import org.fluentcodes.projects.elasticobjects.TestHProvider;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.elasticobjects.EO;

import org.fluentcodes.projects.elasticobjects.testitemprovider.TestObjectProvider;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Werner on 30.6.2017.
 */
public class HQueryCallBasicTestTest {
    private static final Logger LOG = LogManager.getLogger(HQueryCallBasicTestTest.class);

    private static final HQueryCall getAction() {
        return TestHProvider.createHQueryAction(TEO_STATIC.DB_H2_MEM_BASIC_TEST);
    }

    public static final HQueryCall createHibernateModelAction(Map<String, Object> where) {
        HQueryCall bean = getAction();
        if (where != null && !where.isEmpty()) {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("where", where);
            bean.mapAttributes(attributes);
        }
        return bean;
    }

    @Test
    public void readAll()  {
        
        EO adapter = getAction().read(TestObjectProvider.create());
        Assert.assertTrue(adapter.size() > 0);
        //AssertEO.compare(adapter);
    }

    @Test
    public void readWithMapPath()  {
        
        HQueryCall action = getAction();
        action.setMapPath("$[testString]");
        EO adapter = action.read();
        Assert.assertEquals("testString1", adapter.get("testString1/testString"));
        //AssertEO.compare(adapter);
    }

    @Test
    public void readWhereId()  {
        
        HQueryCall action = getAction();
        action.addAnd("id", 1);
        EO adapter = action.read();
        Assert.assertEquals(1, adapter.size());
        Assert.assertEquals("testString1", adapter.get("0/testString"));
        //AssertEO.compare(adapter);
    }

    @Test
    public void readWithPathPattern()  {
        
        HQueryCall action = getAction();

        action.setPathPatternAsString("testString");
        action.addAnd("testString", "testString1");

        EO adapter = action.read();

        Assert.assertEquals("testString1", adapter.get("0/testString"));
        Assert.assertNull(adapter.get("0/testInt"));
        //AssertEO.compare(adapter);
    }

    @Test
    public void readWhereTestString()  {
        

        HQueryCall action = getAction();
        action.addAnd("testString", "testString2");
        action.setMapPath("$[testString]");

        EO adapter = action.read();
        Assert.assertEquals(1, adapter.size());
        Assert.assertEquals("testString2", adapter.get("testString2/testString"));
        //AssertEO.compare(adapter);
    }

    @Test
    public void readByEntry()  {
        
        BasicTest basicTest = new BasicTest();
        basicTest.setTestString("testString3");

        HQueryCall action = getAction();
        action.setUseAdapterValuesByObject(true);

        EO adapter = action
                .read(
                        TestObjectProvider.createEOBuilder()
                                .set(basicTest)
                );
        Assert.assertEquals("testString3", adapter.get("0/testString"));
        Assert.assertEquals(3L, adapter.get("0/id"));
        //AssertEO.compare(adapter);
    }

    @Test
    public void readByListEntries()  {
        
        BasicTest basicTest1 = new BasicTest();
        basicTest1.setTestString("testString1");
        BasicTest basicTest2 = new BasicTest();
        basicTest2.setTestString("testString2");
        List<BasicTest> list = new ArrayList<>();
        list.add(basicTest1);
        list.add(basicTest2);
        HQueryCall action = getAction();
        action.setUseAdapterValuesByObject(true);
        EO adapter = TestObjectProvider.create()
                .add()
                .set(list);
        adapter = action.read(adapter);
        Assert.assertEquals("testString1", adapter.get("0/testString"));
        Assert.assertEquals(1L, adapter.get("0/id"));
        Assert.assertEquals("testString2", adapter.get("1/testString"));
        Assert.assertEquals(2L, adapter.get("1/id"));
        //AssertEO.compare(adapter);
    }

    @Test
    public void readByNonExistingListEntries()  {
        
        BasicTest basicTest1 = new BasicTest();
        basicTest1.setTestString("testString9");
        HQueryCall action = getAction();
        action.setUseAdapterValuesByObject(true);

        EO adapter = TestObjectProvider.create()
                .add()
                .set(basicTest1);

        action.read(adapter);

        Assert.assertEquals("testString9", adapter.get("testString"));
        Assert.assertNull(adapter.get("id"));
        AssertEO.assertLogNotEmpty(adapter);
    }

    @Test
    public void deleteByNaturalKeyTestString2()  {
        

        HQueryCall action = getAction();
        action.setUseAdapterValues(true);

        BasicTest basicTest = new BasicTest();
        basicTest.setTestString("testString2");

        EO adapter = TestObjectProvider.create()
                .add()
                .set(basicTest);
        action.delete(adapter);

        action.setUseAdapterValues(false);
        EO adapterRead = action.read();
        Assert.assertEquals(6, adapterRead.size());
        action.getHConfig().init();
    }

    @Test
    public void deleteWhereTestString4()  {
        

        HQueryCall action = getAction();
        action.addAnd("testString", "testString4");
        action.delete(TestObjectProvider.createEOBuilder().build());

        HQueryCall actionCheck = getAction();
        EO adapterCheck = actionCheck
                .read();
        Assert.assertEquals(6, adapterCheck.size());
        //AssertEO.compare(adapterCheck);
        action.getHConfig().init();
    }

    @Test
    public void deleteWhereNonExisting()  {
        

        HQueryCall action = getAction();
        action.addAnd("testString", "toDelete");
        action.delete(TestObjectProvider.createEOBuilder().build());

        HQueryCall actionCheck = getAction();
        EO adapterCheck = actionCheck
                .read();
        Assert.assertEquals(7, adapterCheck.size());
        //AssertEO.compare(adapterCheck);
    }
}
