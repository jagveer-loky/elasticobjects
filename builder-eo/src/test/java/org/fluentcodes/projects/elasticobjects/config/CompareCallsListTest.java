package org.fluentcodes.projects.elasticobjects.config;

import org.junit.Ignore;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.BEO_STATIC.BUILDER_EO;
import static org.fluentcodes.projects.elasticobjects.BEO_STATIC.X_CALLS_LIST;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.MAIN;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.TEST;
import static org.fluentcodes.projects.elasticobjects.XEO_STATIC.ACTIONS_XLSX;
import static org.fluentcodes.projects.elasticobjects.XEO_STATIC.XLSX;


public class CompareCallsListTest extends CompareConfigs {
    /**
     * Xlsx
     */
    @Test
    public void xlsxXEOTest()  {
        compare(X_CALLS_LIST, XLSX, ACTIONS_XLSX, TEST);
    }

    // Not used in main.
    @Ignore
    @Test
    public void xlsxXEOMain()  {
        compare(X_CALLS_LIST, XLSX, BUILDER_EO, MAIN);
    }

    @Test
    public void xlsxBEOMain()  {
        compare(X_CALLS_LIST, XLSX, BUILDER_EO, MAIN);
    }

    /**
     * Csv
     */
    @Test
    public void csvCEOTest()  {
        compare(X_CALLS_LIST, "Csv", "actions-csv", TEST);
    }
}
