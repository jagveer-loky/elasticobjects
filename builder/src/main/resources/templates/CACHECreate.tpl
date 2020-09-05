<call templateKey="AllTop.tpl" />
    {
    private static transient Logger LOG = LogManager.getLogger($[modelKey].class);
    public enum KEYS {
//<call keep="JAVA" templateKey="AllKeys.tpl" />
    }
//<call keep="JAVA" templateKey="CacheInstanceVars.tpl" />
    public $[modelKey](final EOConfigsCache configsCache, Builder builder)  {
      super(configsCache, builder);
//<call keep="JAVA" templateKey="CacheSetter.tpl" />
    }

   <call templateKey="CacheGetter.tpl" keep="JAVA" />

   public static class Builder extends $[superKey].Builder {
//<call keep="JAVA" templateKey="BeanInstanceVars.tpl" />
     protected void prepare(Map<String, Object> values)  {
//<call keep="JAVA" templateKey="ConfigBuilderSetter.tpl" />
       super.prepare(values);
     }

     @Override
     public Config build(EOConfigsCache configsCache, Map<String, Object> values)  {
       prepare(values);
       return new $[modelKey](configsCache, this);
     }
   }
}
