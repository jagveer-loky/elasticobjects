package org.fluentcodes.projects.elasticobjects.test;

import org.fluentcodes.projects.elasticobjects.eo.EO;

import org.junit.Assert;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;


/**
 * @author Werner Diwischek
 * @since 1.10.2018
 */

public class EOTestHelper {

    public static final EO setValueWins_ok(final Object value, final Class... targetClasses)  {
        final EO root = TestEOProvider.createWithClasses(targetClasses);
        root.mapObject(value);
        Class modelClass = value.getClass();

        Assert.assertEquals(value, root.get());
        Assert.assertEquals(INFO_COMPARE_FAILS, modelClass, root.getModelClass());

        Assert.assertTrue(INFO_EMPTY_FAILS, root.getLog().isEmpty());
        return root;
    }

    public static EO setValue_ok(final Object value, final Class... targetClasses)  {
        return set_ok(null, value, targetClasses);
    }

    public static EO set_ok(final String path, final Class... targetClasses)  {
        return set_ok(path, null, targetClasses);
    }

    public static final EO set_ok(final String path, Object value, Class... targetClasses)  {
        if (targetClasses.length == 0) {
            if (value != null) {
                targetClasses = new Class[]{value.getClass()};
            } else {
                targetClasses = new Class[]{Map.class};
            }
        }
        final EO root = TestEOProvider.createWithClasses(targetClasses);
        EO child = root.setPathValue(path, value);

        /*Assert.assertEquals(value, child.get());
        Assert.assertEquals(INFO_COMPARE_FAILS, modelClass, child.getModelClass());

        Assert.assertEquals(value, root.get(path));
        Assert.assertEquals(INFO_COMPARE_FAILS, modelClass, root.getChild(path).getModelClass());
*/
        Assert.assertTrue(INFO_EMPTY_FAILS + root.getLog(), root.getLog().isEmpty());
        return child;
    }


    public static final EO map_fails(Object value, Class... classes)  {
        return map_fails(null, value, classes);
    }

    public static final EO map_fails(final String path, Object value, Class... targetClasses)  {
        final EO child = TestEOProvider.createWithClasses(targetClasses);
        child.setPathValue(path,value);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS + child.getLog(), child.getLog().isEmpty());
        return child;
    }

    public static final EO map_ok(Class... classes)  {
        return map_ok(null, null, classes);
    }

    public static final EO mapValue_ok(Object value, Class... classes)  {
        return map_ok(null, value, classes);
    }

    public static final EO map_ok(String path, Object value, Class... classes)  {
        if (classes != null) {
            if (classes.length == 0 && value != null) {
                classes = new Class[]{value.getClass()};
            }
        } else {

        }

        final EO child = TestEOProvider.createWithClasses(classes);

        child.mapObject(value);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, child);
        final EO root = child.getRoot();
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, root);

        /*if (value == null) {
        } else if (models != null && models.isScalar()) {

        } else {
            Assert.assertNotNull(INFO_NOT_NULL_FAILS, root.get());
            Assert.assertNotNull(INFO_NOT_NULL_FAILS, root.get(path));
        }


        if (models != null) {
            Assert.assertEquals(INFO_COMPARE_FAILS, models.getModelClass(), child.getModelClass());
            Assert.assertEquals(INFO_COMPARE_FAILS, models.getModelClass(), root.getChild(path).getModelClass());
        }
        */
        Assert.assertTrue(INFO_EMPTY_FAILS, root.getLog().isEmpty());
        return child;
    }

    public static EO setEO_fails(final EO root, final Class... classes)  {
        return setEO_fails(root, null, null, classes);
    }

    public static EO setEO_fails(final EO root, final Object value, final Class... classes)  {
        return setEO_fails(root, null, value, classes);
    }

    public static EO setEO_fails(final EO root, final String path, final Class... classes)  {
        return setEO_fails(root, path, null, classes);
    }


    public static EO setEO_fails(final EO root, final String path, final Object value, final Class... classes)  {
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, root);
        final EO child = root
                .setPathValue(path, value, classes);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, root.getLog().isEmpty());
        return child;
    }

    public static EO setEO_ok(final EO root, final Class... classes)  {
        return setEO_ok(root, null, null, classes);
    }

    public static EO setEOValue_ok(final EO root, final Object value, final Class... classes)  {
        return setEO_ok(root, null, value, classes);
    }

    public static EO setEO_ok(final EO root, final String path, final Class... classes)  {
        return setEO_ok(root, path, null, classes);
    }

    public static EO setEO_ok(final EO root, final String path, final Object value, final Class... classes)  {
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, root);
        Class valueClass = null;
        if (classes.length > 0) {
        } else if (value != null) {
            valueClass = value.getClass();
        } else if (classes.length > 0) {
            valueClass = classes[0];
        }
        final EO child = root
                .setPathValue(path, value, classes);

        if (valueClass != null) {
            Assert.assertEquals(value, child.get());
            Assert.assertEquals(INFO_COMPARE_FAILS, valueClass, child.getModelClass());
        }
        Assert.assertTrue(INFO_EMPTY_FAILS + root.getLog(), root.getLog().isEmpty());
        return child;
    }


    public static final EO mapEO_fails(final EO root, final String path, Class... classes)  {
        return mapEO_fails(root, path, null, classes);
    }

    public static final EO mapEOValue_fails(final EO root, final Object value, Class... classes)  {
        return mapEO_fails(root, null, value, classes);
    }

    public static final EO mapEO_fails(final EO root, Class... classes)  {
        return mapEO_fails(root, null, null, classes);
    }

    public static final EO mapEO_fails(final EO root, final String path, Object value, Class... classes)  {
        final EO child = root.setPathValue(path, value, classes);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS + root.getLog(), root.getLog().isEmpty());
        return child;
    }


    public static final void mapEO_noValueCompare(final EO root, final Object value)  {
        final Class valueClass = root.getModelClass();
        root.mapObject(value);
        Assert.assertEquals(INFO_COMPARE_FAILS, valueClass, root.getModelClass());
        Assert.assertTrue(INFO_EMPTY_FAILS, root.getLog().isEmpty());
    }

    public static final EO mapEO_ok(final EO root, Class... classes)  {
        return mapEO_ok(root, null, null, classes);
    }

    public static final EO mapEO_ok(final EO root, final String path, Class... classes)  {
        return mapEO_ok(root, path, null, classes);
    }

    public static final EO mapEOValue_ok(final EO root, final Object value, Class... classes)  {
        return mapEO_ok(root, null, value, classes);
    }

    public static final EO mapEO_ok(final EO root, final String path, final Object value, Class... classes)  {
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, root);
        Class valueClass = null;
        if (classes.length > 0) {
        } else if (root.getChild(path) != null) {
            valueClass = root.getChild(path).getModelClass();
        } else if (value != null) {
            valueClass = value.getClass();
        }
        final EO child = root
                .setPathValue(path, value, classes);
        if (valueClass != null) {
            Assert.assertEquals(INFO_COMPARE_FAILS, valueClass, child.getModelClass());
            Assert.assertEquals(INFO_COMPARE_FAILS, valueClass, root.getChild(path).getModelClass());
        }
        Assert.assertTrue(INFO_EMPTY_FAILS + root.getLog(), root.getLog().isEmpty());
        return child;
    }

}
