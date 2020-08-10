package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

/**
 * Created by Werner on 22.4.2017
 */
public class ListParamsTest {
    @Test
    public void givenTestProvider_whenGetModelConfigParameters_thenXpected() {
        ConfigModelChecks.compare(ListParams.class);
    }

    @Test
    public void givenModelClass_whenCreate_thenNoException() {
        ConfigModelChecks.create(ListParams.class);
    }

    @Test
    public void callCheckRowStart() {
        final ListParams bean = new ListParams();
        bean.setRowHead(3);
        Assert.assertEquals(new Integer(4), bean.checkRowStart().getRowStart());
    }

    @Test
    public void checkObjectSetRowStart() {
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
    public void checkObjectSetLength() {
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
    public void checkObjectSetHeadAndLength() {
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
}

