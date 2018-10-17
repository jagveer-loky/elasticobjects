package org.fluentcodes.projects.elasticobjects.test;

import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.eo.EOBuilder;
import org.fluentcodes.projects.elasticobjects.eo.Models;
import org.junit.Assert;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;


/**
 * @author Werner Diwischek
 * @since 1.10.2018
 */

public class EOTest {


    public static final  EO setValue_fails(final Object value, Class... targetClasses) throws Exception {
        return set_fails(null, value);
    }

    public static final  EO set_fails(final String path, Class... targetClasses) throws Exception {
        return set_fails(path, null, targetClasses);
    }

    public static final  EO set_fails(final String path, Object value, Class... targetClasses) throws Exception {
        final EOBuilder builder = TestObjectProvider.createEOBuilder()
                .setPath(path)
                .setModels(targetClasses);
        final EO child = builder
                .set(value);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS + child.getLog(), child.getLog().isEmpty());
        return child;
    }

    public static final  EO setValueWins_ok(final Object value, final Class... targetClasses) throws Exception {
        final EO root = TestObjectProvider.createEOBuilder()
                .setModels(targetClasses)
                .set(value);
        Class modelClass = value.getClass();

        Assert.assertEquals(value, root.get());
        Assert.assertEquals(INFO_COMPARE_FAILS, modelClass , root.getModelClass());

        Assert.assertTrue(INFO_EMPTY_FAILS, root.getLog().isEmpty());
        return root;
    }

    public static EO setValue_ok(final Object value, final Class... targetClasses) throws Exception {
        return set_ok(null, value, targetClasses);
    }

    public static EO set_ok(final String path, final Class... targetClasses) throws Exception {
        return set_ok(path, null, targetClasses);
    }

    public static final EO set_ok(final String path, Object value, Class... targetClasses) throws Exception {
        if (targetClasses.length == 0) {
            if (value!=null) {
                targetClasses = new Class[]{value.getClass()};
            }
            else {
                targetClasses = new Class[]{Map.class};
            }
        }
        final EOBuilder builder = TestObjectProvider.createEOBuilder()
                .setPath(path)
                .setModels(targetClasses);

        final Models models = builder.getTargetModels();
        final Class modelClass = models.getModelClass();
        if (value == null) {
            if (models!=null&&!models.isScalar()) {
                value=models.create();
            }
        }

        final EO child = builder
                .set(value);
        final EO root = child.getRoot();

        Assert.assertEquals(value, child.get());
        Assert.assertEquals(INFO_COMPARE_FAILS, modelClass , child.getModelClass());

        Assert.assertEquals(value, root.get(path));
        Assert.assertEquals(INFO_COMPARE_FAILS, modelClass, root.getChild(path).getModelClass());

        Assert.assertTrue(INFO_EMPTY_FAILS + root.getLog(), root.getLog().isEmpty());
        return child;
    }




    public static final EO map_fails(Object value, Class... classes) throws Exception {
        return map_fails(null, value, classes);
    }

    public static final  EO map_fails(final String path, Object value, Class... targetClasses) throws Exception {
        final EO child = TestObjectProvider.createEOBuilder()
                .setPath(path)
                .setModels(targetClasses)
                .map(value);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS + child.getLog(), child.getLog().isEmpty());
        return child;
    }

    public static final  EO map_ok(Class... classes) throws Exception {
        return map_ok(null, null, classes);
    }

    public static final  EO mapValue_ok(Object value, Class... classes) throws Exception {
        return map_ok(null, value, classes);
    }

    public static final EO map_ok( String path, Object value, Class... classes) throws Exception {
        if (classes != null) {
            if (classes.length == 0 && value != null) {
                classes = new Class[]{value.getClass()};
            }
        }
        else {

        }

        final EOBuilder builder = TestObjectProvider.createEOBuilder()
                .setPath(path)
                .setModels(classes);

        Models models = builder.getTargetModels();
        final EO child = builder
                .map(value);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, child);
        final EO root = child.getRoot();
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, root);

        if (value == null) {
        }
        else if (models!=null && models.isScalar()) {

        }
        else {
            Assert.assertNotNull(INFO_NOT_NULL_FAILS, root.get());
            Assert.assertNotNull(INFO_NOT_NULL_FAILS, root.get(path));
        }


        if (models != null) {
            Assert.assertEquals(INFO_COMPARE_FAILS, models.getModelClass(), child.getModelClass());
            Assert.assertEquals(INFO_COMPARE_FAILS, models.getModelClass(), root.getChild(path).getModelClass());
        }
        Assert.assertTrue(INFO_EMPTY_FAILS, root.getLog().isEmpty());
        return child;
    }

    public static EO setEO_fails(final EO root, final Class... classes) throws Exception {
        return setEO_fails(root, null, null, classes);
    }

    public static EO setEO_fails(final EO root,  final Object value, final Class... classes) throws Exception {
        return setEO_fails(root, null, value, classes);
    }
    public static EO setEO_fails(final EO root,  final String path, final Class... classes) throws Exception {
        return setEO_fails(root, path, null, classes);
    }


    public static EO setEO_fails(final EO root, final String path, final Object value, final Class... classes) throws Exception {
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, root);
        final EO child = root
                .add(path)
                .setModels(classes)
                .set(value);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, root.getLog().isEmpty());
        return child;
    }

    public static EO setEO_ok(final EO root, final Class... classes) throws Exception {
        return setEO_ok(root, null, null, classes);
    }

    public static EO setEOValue_ok(final EO root, final Object value, final Class... classes) throws Exception {
         return setEO_ok(root, null, value, classes);
    }

    public static EO setEO_ok(final EO root, final String path, final Class... classes) throws Exception {
        return setEO_ok(root, path, null, classes);
    }

    public static EO setEO_ok(final EO root, final String path, final Object value, final Class... classes) throws Exception {
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, root);
        Class valueClass = null;
        if (classes.length>0) {
        }
        else if (value!=null) {
            valueClass = value.getClass();
        }
        else if (classes.length>0) {
            valueClass = classes[0];
        }
        final EO child = root
                .add(path)
                .setModels(classes)
                .set(value);

        if (valueClass!=null) {
            Assert.assertEquals(value, child.get());
            Assert.assertEquals(INFO_COMPARE_FAILS, valueClass, child.getModelClass());
        }
        Assert.assertTrue(INFO_EMPTY_FAILS + root.getLog(), root.getLog().isEmpty());
        return child;
    }


    public static final EO mapEO_fails(final EO root, final String path,  Class... classes) throws Exception {
        return mapEO_fails(root, path, null, classes);
    }

    public static final EO mapEOValue_fails(final EO root, final Object value,  Class... classes) throws Exception {
        return mapEO_fails(root, null, value, classes);
    }

    public static final EO mapEO_fails(final EO root,   Class... classes) throws Exception {
        return mapEO_fails(root, null, null, classes);
    }

    public static final EO mapEO_fails(final EO root, final String path, Object value, Class... classes) throws Exception {
        final EO child = root
                .add(path)
                .setModels(classes)
                .map(value);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS + root.getLog(), root.getLog().isEmpty());
        return child;
    }



    public static final  void mapEO_noValueCompare(final EO root, final Object value) throws Exception {
        final Class valueClass = root.getModelClass();
        root.add()
                .map(value);
        Assert.assertEquals(INFO_COMPARE_FAILS, valueClass, root.getModelClass());
        Assert.assertTrue(INFO_EMPTY_FAILS, root.getLog().isEmpty());
    }

    public static final EO mapEO_ok(final EO root, Class... classes) throws Exception {
        return mapEO_ok(root, null, null, classes);
    }

    public static final EO mapEO_ok(final EO root, final String path, Class... classes) throws Exception {
        return mapEO_ok(root, path, null, classes);
    }

    public static final EO mapEOValue_ok(final EO root, final Object value, Class... classes) throws Exception {
        return mapEO_ok(root, null, value, classes);
    }

    public static final EO mapEO_ok(final EO root, final String path, final Object value, Class... classes) throws Exception {
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, root);
        Class valueClass = null;
        if (classes.length>0 ) {
        }
        else if (root.getChild(path) != null) {
            valueClass = root.getChild(path).getModelClass();
        }
        else if (value!=null) {
            valueClass = value.getClass();
        }
        final EO child = root
                .add(path)
                .setModels(classes)
                .map(value);

        if (valueClass != null) {
            Assert.assertEquals(INFO_COMPARE_FAILS, valueClass, child.getModelClass());
            Assert.assertEquals(INFO_COMPARE_FAILS, valueClass, root.getChild(path).getModelClass());
        }
        Assert.assertTrue(INFO_EMPTY_FAILS +  root.getLog(), root.getLog().isEmpty());
        return child;
    }

}
