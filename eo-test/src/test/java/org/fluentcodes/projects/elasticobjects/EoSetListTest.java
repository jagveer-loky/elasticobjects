package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.test.TestProviderListJson;
import org.fluentcodes.projects.elasticobjects.test.TestProviderMapJson;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootDev;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class EoSetListTest {
    private static final Logger LOG = LogManager.getLogger(EoSetListTest.class);

    @Test
    public void givenListEmpty_withJsonListSmall_ok()  {
        final EO eoEmpty = TestProviderRootDev.createEo();
        eoEmpty.set(TestProviderListJson.JSON_SMALL.content(), S_LEVEL0);
        Assertions.assertThat(eoEmpty.getEo(S_LEVEL0).getModelClass()).isEqualTo(List.class);
        Assertions.assertThat(eoEmpty.get(S_LEVEL0, S1)).isEqualTo(S_INTEGER);
        Assertions.assertThat(eoEmpty.get(S_LEVEL0, S0)).isEqualTo(S_STRING);
    }
}

