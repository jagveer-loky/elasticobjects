package org.fluentcodes.projects.elasticobjects.documentation;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.Scope;
import org.junit.Test;

import java.util.Map;

public class EOHtmlTest {
    protected static final ConfigMaps EO_CONFIGS_DEV = new ConfigMaps(Scope.DEV);
    protected static final ConfigMaps EO_CONFIGS_TEST = new ConfigMaps(Scope.TEST);

    protected static final EO createEoDev() {
        return EoRoot.of(EO_CONFIGS_DEV);
    }

    protected static final EO createEoTest() {
        return EoRoot.of(EO_CONFIGS_TEST);
    }

    @Test
    public void DEV__ConstructorEmpty__hasClassLinkedHashMap() {
        EO eoRoot = EoRoot.of(EO_CONFIGS_DEV);
        Assertions.assertThat(eoRoot.getModelClass())
                .isEqualTo(Map.class);
    }

    @Test
    public void DEV__ConstructorJson__hasClassLinkedHashMap() {
        EO eoRoot = EoRoot.ofValue(EO_CONFIGS_DEV, "{\"key\":1}");
        Assertions.assertThat(eoRoot.get("key"))
                .isEqualTo(1);
    }



    @Test
    public void TEST__ConstructorJsonAnObjectTyped__hasClassAnObject() {
        EO eoRoot = EoRoot.ofValue(EO_CONFIGS_TEST, "{\"(AnObject)key\":{\"myString\":\"test\"}}");
        Assertions.assertThat(eoRoot.get("key/myString"))
                .isEqualTo("test");
        Assertions.assertThat(eoRoot.getEo("key").getModelClass())
                .isEqualTo(AnObject.class);
    }

    @Test
    public void DEV__set_abc_test__hasMapTree() {
        EO eoRoot = createEoDev();
        EO eoChild = eoRoot.set("test", "a/b/c");
        Assertions.assertThat(eoChild.get())
                .isEqualTo("test");
        Assertions.assertThat(((Map)((Map)((Map)eoRoot.get()).get("a")).get("b")).get("c"))
                .isEqualTo("test");
    }
    @Test
    public void DEV__set_a_b_c_test__hasValue() {
        EO eoRoot = createEoDev();
        EO eoChild = eoRoot.set("test", "a","b","c");
        Assertions.assertThat(eoRoot.get("a/b/c"))
                .isEqualTo("test");
        Assertions.assertThat(eoChild.get("/a/b/c"))
                .isEqualTo("test");
    }

    @Test
    public void TEST__set_xyz_AnObject_myString_test__hasMyString() {
        AnObject anObject = new AnObject().setMyString("test");
        EO eoRoot = createEoTest();
        EO eoChild = eoRoot.set(anObject, "x/y/z");
        Assertions.assertThat(eoRoot.get("x/y/z/myString"))
                .isEqualTo("test");
        Assertions.assertThat(eoChild.getModelClass())
                .isEqualTo(AnObject.class);
    }

    @Test
    public void TEST__set_typed_abmyString_test__isTest() {
        EO eoRoot = createEoTest();
        eoRoot.set("test", "a/(AnObject)b/myString");
        Assertions.assertThat(eoRoot.get("a/b/myString"))
                .isEqualTo("test");
        Assertions.assertThat(eoRoot.getEo("a/b").getModelClass())
                .isEqualTo(AnObject.class);
    }

    @Test
    public void scopeDev_set_abc_Test__getEO_get__isTest() {
        EO eoRoot = createEoDev();
        EO eoChild = eoRoot.set("test", "a/b/c");
        Assertions.assertThat(eoChild
                .getEo()
                .get())
                .isEqualTo("test");
        Assertions.assertThat(eoChild
                .getEo("/a/b/c")
                .get())
                .isEqualTo("test");
        Assertions.assertThat(eoRoot
                .getEo("a","b","c")
                .get())
                .isEqualTo("test");
    }

    @Test
    public void DEV__getBack__isTest2() {
        EO eoRoot = createEoDev();
        EO eoChild = eoRoot.set("test1", "a/b/c");
        eoRoot.set("test2", "a/b/x");
        Assertions.assertThat(eoChild
                .getEo("..","x")
                .get())
                .isEqualTo("test2");
    }

    @Test
    public void scopeDev_set_abc_Test__get__isTest() {
        EO eoRoot = createEoDev();
        EO eoChild = eoRoot.set("test", "a/b/c");
        Assertions.assertThat(eoChild.get())
                .isEqualTo("test");
        Assertions.assertThat(eoChild.get("/a/b/c"))
                .isEqualTo("test");
        Assertions.assertThat(eoRoot.get("a","b","c"))
                .isEqualTo("test");
    }

}
