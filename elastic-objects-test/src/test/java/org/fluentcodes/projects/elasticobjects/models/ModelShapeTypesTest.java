package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class ModelShapeTypesTest {

    @Test
    public void testTypeClasses()  {

        //testTypeClass(Object.class, ShapeTypes.NONE);
        testTypeClass(Boolean.class, ShapeTypes.SCALAR);
        testTypeClass(Date.class, ShapeTypes.SCALAR);
        testTypeClass(Long.class, ShapeTypes.SCALAR);
        testTypeClass(Integer.class, ShapeTypes.SCALAR);
        testTypeClass(Double.class, ShapeTypes.SCALAR);
        testTypeClass(Float.class, ShapeTypes.SCALAR);
        testTypeClass(String.class, ShapeTypes.SCALAR);
        testTypeClass(Date.class, ShapeTypes.SCALAR);
        testTypeClass(List.class, ShapeTypes.LIST);
        testTypeClass(Map.class, ShapeTypes.MAP);

    }

    @Test
    public void testTypeObjects()  {

        //testTypeObject(new Object(), ShapeTypes.NONE);
        testTypeObject(S_BOOLEAN, ShapeTypes.SCALAR);
        testTypeObject(SAMPLE_DATE, ShapeTypes.SCALAR);
        testTypeObject(SAMPLE_LONG, ShapeTypes.SCALAR);
        testTypeObject(S_INTEGER, ShapeTypes.SCALAR);
        testTypeObject(SAMPLE_DOUBLE, ShapeTypes.SCALAR);
        testTypeObject(SAMPLE_FLOAT, ShapeTypes.SCALAR);
        testTypeObject(S_STRING, ShapeTypes.SCALAR);
        testTypeObject(SAMPLE_LIST_EMPTY, ShapeTypes.LIST);
        testTypeObject(SAMPLE_MAP_EMPTY, ShapeTypes.MAP);
    }

    private void testTypeClass(Class clazz, ShapeTypes shapeType)  {
        ShapeTypes shapeTypeStored = ProviderConfigMaps.CONFIG_MAPS.findModel(clazz).getShapeType();
        Assert.assertEquals(shapeType, shapeTypeStored);
    }

    private void testTypeObject(Object object, ShapeTypes shapeType) {
        try {
            ShapeTypes shapeTypeStored = ProviderConfigMaps.CONFIG_MAPS.findModel(object).getShapeType();
            Assert.assertEquals(shapeType, shapeTypeStored);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}