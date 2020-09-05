package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.TestHProvider;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.calls.ListParams;

import org.fluentcodes.projects.elasticobjects.testitemprovider.TestObjectProvider;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

/**
 * Created by Werner on 17.1.2018.
 * http://dbunit.sourceforge.net/intro.html
 */
public class HConfigH2MemTest {
    protected static final HConfig HIBERNATE = TestHProvider.findHibernateCache("h2:mem:basic");
    protected static final ModelInterface BASIC_TEST = TestObjectProvider.findModel("BasicTest");
    protected static final ModelInterface SUB_TEST = TestObjectProvider.findModel("SubTest");
    private static final Logger LOG = LogManager.getLogger(HConfigH2MemTest.class);

    @Test
    public void readBasicTestAll()  {

        List result = HIBERNATE.read(BASIC_TEST, new ListParams());
        Assert.assertEquals(7, result.size());
    }

    @Test
    public void deleteByBasicTest2L()  {

        BasicTest basicTest = new BasicTest();
        basicTest.setId(2L);

        HIBERNATE.delete(BASIC_TEST, basicTest);
        List result = HIBERNATE.read(BASIC_TEST, new ListParams());
        Assert.assertEquals(6, result.size());
        HIBERNATE.init();
    }

    @Test
    public void deleteTestStringWithTest()  {


        int counter = HIBERNATE.delete(BASIC_TEST, new ListParams().addAnd("testString", "testString2"));
        Assert.assertEquals(1, counter);

        List result = HIBERNATE.read(BASIC_TEST, new ListParams());
        Assert.assertEquals(6, result.size());
        HIBERNATE.init();
    }

    @Test()
    public void deleteNonExistingObject()  {

        BasicTest basicTest = new BasicTest();
        basicTest.setId(13L);
        try {
            HIBERNATE.delete(BASIC_TEST, basicTest);
        } catch (Exception e) {

        }
        List result = HIBERNATE.readAll(BASIC_TEST);
        Assert.assertEquals(7, result.size());
    }

    @Ignore
    @Test
    public void deleteAll()  {

        HIBERNATE.delete(BASIC_TEST, new ListParams());
        List result = HIBERNATE.read(BASIC_TEST, new ListParams());
        Assert.assertEquals(0, result.size());
        HIBERNATE.init();
    }

    @Test
    public void readBasicTest2L()  {

        BasicTest basicTest = new BasicTest();
        basicTest.setId(2L);
        BasicTest dbBasicTest = (BasicTest) HIBERNATE.find(BASIC_TEST, basicTest);
        Assert.assertEquals(new Long(2L), dbBasicTest.getId());
    }

    @Test
    public void readWhereId2L()  {


        List result = HIBERNATE.read(BASIC_TEST, new ListParams().addAnd("id", 2L));
        Assert.assertEquals(1, result.size());
        //AssertEO.compare(TestObjectProvider.EO_CONFIGS_CACHE, result);
    }

    @Test
    public void readWhereTestString()  {


        List result = HIBERNATE.read(BASIC_TEST, new ListParams().addAnd("testString", "testString3"));
        Assert.assertEquals(1, result.size());
        //AssertEO.compare(TestObjectProvider.EO_CONFIGS_CACHE, result);
    }

    @Test
    public void writeBasicTestNew()  {

        BasicTest basicTest = new BasicTest();
        basicTest.setTestString("testString8");
        basicTest.setTestInt(8);
        basicTest.setTestFloat(8.1F);
        basicTest.setTestDouble(8.1);
        basicTest.setTestBoolean(true);

        HIBERNATE.write(BASIC_TEST, basicTest);

        BasicTest dbBasicTest = (BasicTest) HIBERNATE.find(BASIC_TEST, basicTest);
        Assert.assertEquals(new Integer(8), dbBasicTest.getTestInt());
        Assert.assertEquals(new Long(8L), dbBasicTest.getId());

        HIBERNATE.init();
    }

    @Test
    public void WriteBasicTestUpdate()  {

        BasicTest basicTest = new BasicTest();
        basicTest.setTestString("testString3");
        basicTest.setTestInt(10);

        HIBERNATE.write(BASIC_TEST, basicTest);

        List result = HIBERNATE.readAll(BASIC_TEST);
        Assert.assertEquals(7, result.size());
        Assert.assertEquals(new Integer(10), ((BasicTest) result.get(2)).getTestInt());
        HIBERNATE.init();
    }

    @Test
    public void writeByIdUpdate()  {

        BasicTest basicTest = new BasicTest();
        basicTest.setId(2L);
        basicTest.setTestInt(5);

        HIBERNATE.write(BASIC_TEST, basicTest);

        List result = HIBERNATE.readAll(BASIC_TEST);
        Assert.assertEquals(7, result.size());
        Assert.assertEquals(new Integer(5), ((BasicTest) result.get(1)).getTestInt());
        HIBERNATE.init();
    }

    @Test
    public void WriteWithSubBasicTestInserted()  {

        BasicTest basicTestParent = new BasicTest();
        basicTestParent.setTestString("testString3");

        BasicTest basicTestSub = new BasicTest();
        basicTestSub.setTestString("testString8");
        basicTestSub.setTestInt(4);
        basicTestParent.setBasicTest(basicTestSub);

        HIBERNATE.write(BASIC_TEST, basicTestParent);

        List result = HIBERNATE.readAll(BASIC_TEST);
        Assert.assertEquals(8, result.size());
        BasicTest changed = (BasicTest) result.get(2);
        Assert.assertEquals(new Integer(4), changed.getBasicTest().getTestInt());
        HIBERNATE.init();
    }

    @Test
    public void writeBasicTestWith2SubBasicTests()  {

        BasicTest basicTestParent = new BasicTest();
        basicTestParent.setTestString("testString3");

        BasicTest basicTestSub = new BasicTest();
        basicTestSub.setTestString("testString8");
        basicTestSub.setTestInt(8);
        basicTestParent.setBasicTest(basicTestSub);

        BasicTest basicTest2Sub = new BasicTest();
        basicTest2Sub.setTestString("testString9");
        basicTest2Sub.setTestInt(9);
        basicTestSub.setBasicTest(basicTest2Sub);

        HIBERNATE.write(BASIC_TEST, basicTestParent);

        List result = HIBERNATE.readAll(BASIC_TEST);
        Assert.assertEquals(9, result.size());
        BasicTest changed = (BasicTest) result.get(2);
        Assert.assertEquals(new Integer(8), changed.getBasicTest().getTestInt());
        HIBERNATE.init();
    }

    @Test
    public void writeBasicTestWithSubTestInsert()  {

        BasicTest basicTest = new BasicTest();
        basicTest.setTestString("testString8");
        basicTest.setTestInt(4);
        basicTest.setTestFloat(4.1F);
        basicTest.setTestDouble(4.1);
        basicTest.setTestBoolean(true);

        BasicTest basicTestSub = new BasicTest();
        basicTestSub.setTestString("testString5");
        basicTestSub.setTestInt(5);
        basicTest.setBasicTest(basicTestSub);

        HIBERNATE.write(BASIC_TEST, basicTest);

        List result = HIBERNATE.readAll(BASIC_TEST);
        Assert.assertEquals(8, result.size());
        BasicTest inserted = (BasicTest) result.get(7);
        Assert.assertEquals(new Integer(5), inserted.getBasicTest().getTestInt());
        HIBERNATE.init();
    }

    @Test
    public void execute_DropTableBasicTest_BySql()  {

        HIBERNATE.executeUpdate("Drop table BasicTest");
        List subTestList = HIBERNATE.readAll(SUB_TEST);
        Assert.assertTrue(subTestList.size() > 0);
        try {
            HIBERNATE.readAll(BASIC_TEST);
            Assert.fail("Exception should be thrown when null for adapterValue is used");
        } catch (Exception e) {
            Assert.assertEquals("could not prepare statement", e.getMessage());
        }
        HIBERNATE.init();
    }

}

