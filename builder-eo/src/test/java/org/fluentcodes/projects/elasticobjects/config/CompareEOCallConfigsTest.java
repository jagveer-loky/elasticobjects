package org.fluentcodes.projects.elasticobjects.config;

import org.junit.Ignore;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.BEO_STATIC.BUILDER_EO;
import static org.fluentcodes.projects.elasticobjects.BEO_STATIC.X_CALLS;
import static org.fluentcodes.projects.elasticobjects.B_STATIC.BUILDER;
import static org.fluentcodes.projects.elasticobjects.CEO_STATIC.ACTIONS_CSV;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.XEO_STATIC.ACTIONS_XLSX;


public class CompareEOCallConfigsTest extends CompareConfigs {
    /**
     * Values
     */
    @Test
    public void valueEOTest()  {
        compare(X_CALLS, VALUE, ELASTIC_OBJECTS, TEST);
    }

    // Not used in main.
    @Ignore
    @Test
    public void valueEOMain()  {
        compare(X_CALLS, VALUE, ELASTIC_OBJECTS, MAIN);
    }

    /**
     * Host
     */
    @Test
    public void hostEOTest()  {
        compare(X_CALLS, HOST, ELASTIC_OBJECTS, TEST);
    }

    @Test
    public void hostEOMain()  {
        compare(X_CALLS, HOST, ELASTIC_OBJECTS, MAIN);
    }

    /**
     * Files
     */
    @Test
    public void fileEOTest()  {
        compare(X_CALLS, FILE, ELASTIC_OBJECTS, TEST);
    }

    // No config file in main!
    @Ignore
    @Test
    public void fileEOMain()  {
        compare(X_CALLS, FILE, ELASTIC_OBJECTS, MAIN);
    }

    @Test
    public void fileXEOTest()  {
        compare(X_CALLS, FILE, ACTIONS_XLSX, TEST);
    }

    @Test
    public void fileCEOTest()  {
        compare(X_CALLS, FILE, ACTIONS_CSV, TEST);
    }

    @Test
    public void fileBEOTest()  {
        compare(X_CALLS, FILE, BUILDER_EO, MAIN);
    }

    /**
     * Templates
     */
    @Test
    public void templateEOTest()  {
        compare(X_CALLS, TEMPLATE, ELASTIC_OBJECTS, TEST);
    }

    @Test
    public void templateEOMain()  {
        compare(X_CALLS, TEMPLATE, ELASTIC_OBJECTS, MAIN);
    }

    @Test
    public void templateBMain()  {
        compare(X_CALLS, TEMPLATE, BUILDER, MAIN);
    }

    @Test
    public void templateBTest()  {
        compare(X_CALLS, TEMPLATE, BUILDER, TEST);
    }


    /**
     * Jsons
     */
    @Test
    public void jsonEOTest()  {
        compare(X_CALLS, JSON, ELASTIC_OBJECTS, TEST);
    }

    @Test
    public void jsonEOMain()  {
        compare(X_CALLS, JSON, ELASTIC_OBJECTS, MAIN);
    }

    @Test
    public void jsonBEOMain()  {
        compare(X_CALLS, JSON, BUILDER_EO, MAIN);
    }

}
