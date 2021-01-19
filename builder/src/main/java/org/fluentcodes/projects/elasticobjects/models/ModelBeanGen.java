package org.fluentcodes.projects.elasticobjects.models;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModelBeanGen extends ModelBean implements ModelBeanInterface4Java {
    private static final Pattern linkPattern = Pattern.compile("@([^\\s\\.,]*)([\\s\\.,])");
    public ModelBeanGen() {
        super();
    }
    public ModelBeanGen(String key) {
        this();
        setNaturalId(key);
    }

    public ModelBeanGen(final ModelConfig config) {
        super(config);
    }

    public static String replaceLinks(final String comment) {
        String result = comment.replaceAll("\n", "\n * ");
        if (!result.contains("@")) {
            return result;
        }
        StringBuilder replaced = new StringBuilder();
        Matcher matcher = linkPattern.matcher(comment);
        int end = 0;
        while (matcher.find()) {
            replaced.append(comment.substring(end, matcher.start()));
            end = matcher.end();
            replaced.append("{@link " + matcher.group(1) + "}");
            replaced.append(matcher.group(2));
        }
        replaced.append(comment.substring(end, comment.length()));
        return replaced.toString();
    }

    protected void addFieldMap(final ModelConfig config) {
        for (FieldConfig fieldConfig: config.getFieldMap().values()) {
            getFieldBeans().put(fieldConfig.getNaturalId(), new FieldBeanGen(fieldConfig));
            getFieldBeans().get(fieldConfig.getNaturalId()).setModelBean(this);
        }
    }

    public ModelBeanGen(final Map values) {
        super(values);
    }

    @Override
    protected void addField(final String fieldKey) {
        FieldBeanGen fieldBean = new FieldBeanGen(fieldKey);
        if (hasFinal()) fieldBean.setFinal(getFinal());
        if (hasOverride()) fieldBean.setOverride(getOverride());
        getFieldBeans().put(fieldKey, fieldBean);
    }

    @Override
    protected void addFieldSuper(final FieldBean fieldBean) {
        FieldBean fieldBeanNew = new FieldBeanGen(fieldBean);
        fieldBeanNew.setSuper(true);
        getFieldBeans().put(fieldBean.getNaturalId(), fieldBean);
    }

    @Override
    protected void addField(final Map fieldConfigMap) {
        for (Object key : fieldConfigMap.keySet()) {
            Map<String, Object> fieldMap = (Map<String,Object>) fieldConfigMap.get(key);
            FieldBeanGen fieldBean = new FieldBeanGen(fieldMap);
            fieldBean.setNaturalId((String)key);
            if (hasFinal()) fieldBean.setFinal(getFinal());
            if (hasOverride()) fieldBean.setOverride(getOverride());
            getFieldBeans().put((String)key, fieldBean);
        }
    }
}
