package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.domain.BaseBean;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by werner.diwischek on 06.01.18.
 */
public class BaseBeanTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return BaseBean.class;
    }

    @Override
    @Test
    public void create_noEoException() {
        assertCreateNoException();
    }

    @Override
    @Test
    public void compareModelConfig() {
        assertModelConfigEqualsPersisted();
    }

    @Override
    @Test
    public void compareBeanFromModelConfig() {
        assertBeanFromModelConfigEqualsPersisted();
    }

    @Test
    public void merge_mapWithAuthor() {
        Map<String, Object> map = new HashMap<>();
        map.put("author", "author");
        BaseBean baseBean = new BaseBean();
        baseBean.merge(map);
        Assert.assertEquals("author", baseBean.getAuthor());
    }

}
