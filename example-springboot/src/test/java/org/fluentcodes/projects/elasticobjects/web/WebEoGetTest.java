package org.fluentcodes.projects.elasticobjects.web;

import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Test;

public class WebEoGetTest {

    @Test
    public void createConfigStartPage__ModelConfig__xpected() {
        WebEoGet webEoGet = new WebEoGet(ProviderConfigMaps.CONFIG_MAPS);
        String value = webEoGet.createConfigStartPage("ModelConfig");
    }
}
