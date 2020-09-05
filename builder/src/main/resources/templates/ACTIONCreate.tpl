<call templateKey="AllTop.tpl" />
    {
    private static transient Logger LOG = LogManager.getLogger($[modelKey].class);
    public enum KEYS {
//<call keep="JAVA" templateKey="AllKeys.tpl" />
    }
//<call keep="JAVA" templateKey="BeanInstanceVars.tpl" />
    public $[modelKey](final EOConfigsCache configsCache, String configKey)  {
      super(configsCache, configCache);
    }
    @Override
    public void mapAttributes(final Map attributes)  {
        if (attributes == null || attributes.isEmpty()) {
          return;
        }
        try {
          super.mapAttributes(attributes);
        } catch (Exception e) {
          e.printStackTrace();
        }
    }
}
