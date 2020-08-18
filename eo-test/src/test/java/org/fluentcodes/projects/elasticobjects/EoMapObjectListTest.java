package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_BOOLEAN;

/**
 * @author Werner Diwischek
 * @since 11.8.2020
 */

public class EoMapObjectListTest {
    @Test
    public void givenDev_whenMapBoolean_thenLogNotEmpty()  {
        final EO root = ProviderRootDevScope.createEo(List.class);
        root
                .mapObject(S_BOOLEAN);
        Assertions.assertThat(root.getLog()).isNotEmpty();
        Assert.assertEquals(List.class, root.getModelClass());
    }

    @Test
    public void givenDev_whenMapListBooleanValue_thenLogEmptyAndModelBoolean()  {
        final EO root = ProviderRootDevScope.createEo(List.class);
        root
                .mapObject("[true]");
        Assertions.assertThat(root.getLog()).isEmpty();
        Assert.assertEquals(Boolean.class, root.getEo("0").getModelClass());
    }


    @Test
    public void givenDev_whenMapRootModelListAndIntegerValue_thenLogEmptyAndModelInteger()  {
        final EO root = ProviderRootDevScope.createEo();
        root
                .mapObject("{\"_rootmodel\": \"List\",\"0\": 1}");
        Assertions.assertThat(root.getLog())
                .isEmpty();
        Assertions.assertThat(root.getModelClass())
                .isEqualTo(List.class);
        Assertions.assertThat(root.getEo("0").getModelClass())
                .isEqualTo(Integer.class);
        Assertions.assertThat(root.get("0"))
                .isEqualTo(1);
    }



}


