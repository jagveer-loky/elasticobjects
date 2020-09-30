package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.assets.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 23.05.2016.
 */
public class EoRemoveTest {
    private static final Logger LOG = LogManager.getLogger(EoRemoveTest.class);

    /**
     * Test for the remove method, which is depending on the object
     *
     * @
     */
    @Test
    public void givenAnObject_thenRemoved()  {
        EO child = ProviderRootTestScope.createEo(new AnObject())
                .set(S_STRING, AnObject.MY_STRING);
        EO root = child.getRoot();
        Assert.assertEquals(1, (root).size());

        root.remove(AnObject.MY_STRING);
        Assert.assertEquals(0, (root).size());
    }

    @Test
    public void givenBtEmpty_WhenRemove_thenExceptionThrown()  {
        EO root = ProviderRootTestScope.createEo(AnObject.class);
        Assertions
                .assertThatThrownBy(()->{root.remove(AnObject.MY_STRING);})
                .hasMessage("Object value for " + AnObject.MY_STRING + " is already null.");
    }

    /**
     * Test for the remove method, which is depending on the object
     *
     * @
     */
    @Test
    public void givenMap_thenRemoved()  {
        EO child = ProviderRootTestScope.createEo().set(S_STRING, S_TEST_STRING);
        EO root = child.getRoot();
        Assert.assertEquals(1, root.size());
        Assert.assertEquals(S_STRING, root.get(S_TEST_STRING));
        root.remove(S_TEST_STRING);
        Assert.assertEquals(0, root.size());

    }

    @Test
    public void givenMapEmpty_WhenRemove_thenExceptionThrown()  {
        EO root = ProviderRootDevScope.createEo(Map.class);
        Assertions
                .assertThatThrownBy(()->{root.remove("test");})
                .hasMessage("No value add for fieldName=test");
    }

    @Test
    public void givenList_thenRemoved()  {
        EO child = ProviderRootTestScope.createEo(new ArrayList<>())
                .set( S_STRING,S0);
        // remove value entry first
        EO root = child.getRoot();
        Assert.assertEquals(1, root.size());
        root.remove(S0);
        Assert.assertEquals(0, root.size());
    }

    @Test
    public void givenListEmpty_WhenRemove_thenExceptionThrown()  {
        EO root = ProviderRootDevScope.createEo(new ArrayList<>());
        Assertions
                .assertThatThrownBy(()->{root.remove(S0);})
                .hasMessage("List size 0 greater than 0");
    }
}
