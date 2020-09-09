package org.fluentcodes.projects.elasticobjects.calls.executor;

import org.fluentcodes.projects.elasticobjects.TestBuilderEOProvider;
import org.junit.Assert;
import org.junit.Test;


public class ValueFieldsJavaTest {

/*
    @Test
    public void createTypeLong()  {
        String type = ValueFieldsJava.createType(TestBuilderEOProvider.STATIC, "id");
        Assert.assertEquals("Long", type);
    }

    @Test
    public void getModelKeysTestString()  {
        String type = ValueFieldsJava.getModelKeys(new Object[]{TestBuilderEOProvider.STATIC, "testString"});
        Assert.assertEquals("String", type);
    }

    @Test
    public void getModelKeys()  {
        String type = ValueFieldsJava.getModelKeys(new Object[]{TestBuilderEOProvider.STATIC, "untypedMap"});
        Assert.assertEquals("HashMap", type);
    }

    @Test
    public void getDefaultValueTestString()  {
        String defaultValue = ValueFieldsJava.getDefaultValue(new Object[]{TestBuilderEOProvider.STATIC, "testString"});
        Assert.assertEquals("", defaultValue);
    }

    @Test
    public void createTypeString()  {
        String type = ValueFieldsJava.createType(TestBuilderEOProvider.STATIC, "testString");
        Assert.assertEquals("String", type);
    }

    @Test
    public void createAnnotation_With_TestString()  {
        String result = ValueFieldsJava.createAnnotation(TestBuilderEOProvider.STATIC, "testString");
        Assert.assertEquals(S_ROW_DELIMITER +
                "  @Column(name=\"testString\", column=\"testString\", type=\"string\", length=\"20\")", result);
    }

    @Test
    public void getOneToManyAnnotation()  {

        EO dbParams = ValueFieldsJava.getFieldDbParams(
                TestBuilderEOProvider.STATIC,
                "localizedEntryMap"
        );
        String result = ValueFieldsJava.getOneToManyAnnotation(dbParams);
        Assert.assertEquals("\n  @OneToMany ()\n", result);
    }

    @Test
    public void getManyToOneAnnotation()  {
        EO dbParams = ValueFieldsJava.getFieldDbParams(
                TestBuilderEOProvider.STATIC,
                "localizedString"
        );
        String result = ValueFieldsJava.getManyToOneAnnotation(dbParams);
        Assert.assertEquals(S_ROW_DELIMITER +
                "  @ManyToOne ()\n", result);
    }

    @Test
    public void getManyToManyAnnotation()  {

        EO dbParams = ValueFieldsJava.getFieldDbParams(
                TestBuilderEOProvider.STATIC,
                "rightList"
        );
        String result = ValueFieldsJava.getManyToManyAnnotation(dbParams);
        Assert.assertEquals("@ManyToMany ()\n" +
                "  @JoinTable(name = \"LeftRight\",\n" +
                "    joinColumns = @JoinColumn(name = \"left_id\", referencedColumnName = \"id\"),\n" +
                "    inverseJoinColumns = @JoinColumn(name = \"right_id\", referencedColumnName = \"id\")\n" +
                "  )", result);
    }

 */
}
