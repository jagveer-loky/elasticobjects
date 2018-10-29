package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.config.ModelInterface;
import org.fluentcodes.projects.elasticobjects.config.ShapeTypes;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

/**
 * Created by Werner on 22.4.2017
 */
public class ListParamsTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(ListParamsTest.class);

    @Test
    public void callCheckRowStart() {
        final ListParams bean = new ListParams();
        bean.setRowHead(3);
        Assert.assertEquals(new Integer(4), bean.checkRowStart().getRowStart());
    }

    @Test
    public void checkObjectSetRowStart() throws Exception {
        ListParams params = new ListParams();
        params.setRowStart(5);
        Assert.assertEquals(new Integer(5), params.getRowStart());
        params.setRowStart(6);
        Assert.assertEquals(new Integer(5), params.getRowStart());
        params.prepare();
        Assert.assertEquals(new Integer(5), params.getRowStart());
        Assert.assertEquals(new Integer(-1), params.getRowHead());
        Assert.assertEquals(new Integer(-1), params.getLength());
        Assert.assertEquals(new Integer(-1), params.getRowEnd());
        params.prepareStartEnd(10);
        Assert.assertEquals(new Integer(5), params.getLength());
        Assert.assertEquals(new Integer(10), params.getRowEnd());
    }

    @Test
    public void checkObjectSetLength() throws Exception {
        ListParams params = new ListParams();
        params.setLength(5);
        Assert.assertEquals(new Integer(5), params.getLength());
        params.setLength(6);
        Assert.assertEquals(new Integer(5), params.getLength());
        params.prepare();
        Assert.assertEquals(new Integer(0), params.getRowStart());
        Assert.assertEquals(new Integer(-1), params.getRowHead());
        Assert.assertEquals(new Integer(5), params.getLength());
        Assert.assertEquals(new Integer(5), params.getRowEnd());
        params.prepareStartEnd(10);
        Assert.assertEquals(new Integer(5), params.getLength());
        Assert.assertEquals(new Integer(5), params.getRowEnd());
        params.prepareStartEnd(3);
        Assert.assertEquals(new Integer(3), params.getLength());
        Assert.assertEquals(new Integer(3), params.getRowEnd());
    }

    @Test
    public void checkObjectSetHeadAndLength() throws Exception {
        ListParams params = new ListParams();
        params.setRowHead(2);
        params.setLength(5);
        Assert.assertEquals(new Integer(5), params.getLength());
        params.setLength(6);
        Assert.assertEquals(new Integer(5), params.getLength());
        params.prepare();
        Assert.assertEquals(new Integer(3), params.getRowStart());
        Assert.assertEquals(new Integer(2), params.getRowHead());
        Assert.assertEquals(new Integer(5), params.getLength());
        Assert.assertEquals(new Integer(8), params.getRowEnd());
        params.prepareStartEnd(10);
        Assert.assertEquals(new Integer(5), params.getLength());
        Assert.assertEquals(new Integer(8), params.getRowEnd());
        params.prepareStartEnd(5);
        Assert.assertEquals(new Integer(2), params.getLength());
        Assert.assertEquals(new Integer(5), params.getRowEnd());
    }

    @Test
    public void callMapAttributes() {
        final ListParams params = new ListParams();

        Map<String, Object> attributes = new HashMap<>();
        attributes.put(F_ROW_HEAD, 0);
        attributes.put(F_ROW_START, 1);
        attributes.put(F_LENGTH, 10);
        attributes.put(F_ROW_END, 21);
        params.mapAttributes(attributes);
        Assert.assertEquals(new Integer(0), params.getRowHead());
        Assert.assertEquals(new Integer(1), params.getRowStart());

        Assert.assertEquals(new Integer(10), params.getLength());
        Assert.assertEquals(new Integer(21), params.getRowEnd());
        params.prepare();
        Assert.assertEquals(new Integer(0), params.getRowHead());
        Assert.assertEquals(new Integer(1), params.getRowStart());

        Assert.assertEquals(new Integer(10), params.getLength());
        Assert.assertEquals(new Integer(11), params.getRowEnd());


    }

    @Test
    public void assertModel() throws Exception {
        TestHelper.printStartMethod();
        ModelInterface paramsModel = TestObjectProvider.EO_CONFIGS_CACHE.findModel(ListParams.class.getSimpleName());
        Assert.assertEquals(ShapeTypes.INSTANCE, paramsModel.getShapeType());
        Assert.assertTrue(paramsModel.hasModel());
        Assert.assertTrue(paramsModel.isObject());
        Assert.assertFalse(paramsModel.isScalar());

        ListParams params = (ListParams) paramsModel.create();

        paramsModel.set(F_ROW_START, params, new Integer(5));
        Assert.assertEquals(new Integer(5), paramsModel.get(F_ROW_START, params));
    }
}

