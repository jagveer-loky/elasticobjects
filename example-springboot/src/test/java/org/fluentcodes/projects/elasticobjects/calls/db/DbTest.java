package org.fluentcodes.projects.elasticobjects.calls.db;

public class DbTest {
/*
    @Before
    public void init() {
        final Call call = new DbSqlCall()
                .setConfigKey("h2:mem:basic:Create");
        EO eo = ProviderRootTestScope.createEo();
        Object value = call.execute(eo);
        Assertions.assertThat(eo.getLog())
                .isEmpty();
    }
    @Test
    public void givenH2MemBT_whenExecuteCall_thenResultSizeIs3()  {
        EO eo = ProviderRootTestScope.createEo();
        final Call callQuery = new DbQueryCall("h2:mem:basic:AnObject");
        List result = (List)callQuery.execute(eo);
        Assertions.assertThat(result.size()).isEqualTo(3);
    }

    @Test
    public void givenH2MemBT_whenExecuteEo_thenResultSizeIs3()  {
        EO eo = ProviderRootTestScope.createEo();
        final Call callQuery = new DbQueryCall("h2:mem:basic:AnObject").setTargetPath("abc");
        eo.addCall(callQuery);
        eo.execute();
        List result = (List) eo.get("abc");
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result.size()).isEqualTo(3);
    }

    @Test
    public void givenH2MemBT_whenExecuteEoOnBT_thenResultSizeIs3()  {
        EO eo = ProviderRootTestScope.createEo();
        final Call callQuery = new DbQueryCall("h2:mem:basic:AnObject").setTargetPath("abc");
        eo.addCall(callQuery);
        eo.setEmpty("(List,AnObject)abc");
        eo.execute();
        List result = (List) eo.get("abc");
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result.size()).isEqualTo(3);
        Assertions.assertThat(eo.getEo("abc/0").getModelClass()).isEqualTo(AnObject.class);
    }

 */
}
