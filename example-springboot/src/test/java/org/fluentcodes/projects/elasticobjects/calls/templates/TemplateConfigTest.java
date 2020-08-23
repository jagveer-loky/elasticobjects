package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_COMPARE_FAILS;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_NOT_NULL_FAILS;


/**
 * @author Werner Diwischek
 * @since 30.10.18.
 */
public class TemplateConfigTest {
    private static final String TEST_TPL_HTML = "test";
    @Test
    public void findCache() throws Exception {
        final TemplateConfig config = ProviderRootTestScope.EO_CONFIGS
                .findTemplate(TEST_TPL_HTML);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config);
        Assert.assertEquals(INFO_COMPARE_FAILS, TEST_TPL_HTML, config.getKey());
    }
    /*
    @Test
    public void createTemplateCacheMain() throws Exception {
        Map<String, Config> configMap = TestConfig.readConfigMapFromFile(CONFIG_TEMPLATE_MAIN, TemplateConfig.html.class);
        TemplateConfig.html config = (TemplateConfig.html) configMap.get(TEST_TPL_HTML);
        Assert.assertNotNull(config);
        Assert.assertEquals(TEST_TPL_HTML, config.getTemplateKey());
    }*/

    /*
    @Test
    public void readMapMain() throws Exception {
        Map configMap = TestConfig.readMapFromFile(CONFIG_TEMPLATE_MAIN);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, configMap);
        Assert.assertTrue(INFO_CONDITION_TRUE_FAILS, configMap.size()>0);
    }*/
}
