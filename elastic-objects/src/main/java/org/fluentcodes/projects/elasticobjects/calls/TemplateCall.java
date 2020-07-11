package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.config.Config;
import org.fluentcodes.projects.elasticobjects.config.Permissions;
import org.fluentcodes.projects.elasticobjects.config.TemplateConfig;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.executor.CallExecutorResource;
import org.fluentcodes.projects.elasticobjects.executor.ExecutorList;
import org.fluentcodes.projects.elasticobjects.executor.ExecutorListTemplate;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.*;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

/**
 * Created by werner.diwischek on 20.03.17.
 */
public class TemplateCall extends ResourceCall<String>{
    public static final String EXECUTE_TEMPLATE = "TemplateCall.execute(content)";
    private static final transient Logger LOG = LogManager.getLogger(TemplateCall.class);
    private static final Set controlKeys = createControlKeys();
    private String content;
    private boolean contentWithCall;
    private ExecutorList bufferedExecutor;
    private String loopPath;
    private TemplateConfig.KeepKeys keepKey;
    private String keepStart;
    private String keepEnd;

    public TemplateCall() {
        super(Permissions.READ);
    }

    public TemplateCall setConfigKey(final String configKey) {
        return (TemplateCall) super.setConfigKey(configKey);
    }

    public static final String createCallNoContent(final String... keyValues)  {
        return createCall(null, keyValues);
    }

    public static final String createCall(final String content, final String... keyValues)  {
        if (keyValues == null) {
            throw new EoException("Null values for createCall");
        }
        if (keyValues.length == 0) {
            throw new EoException("Empty values for createCall");
        }
        if (keyValues.length % 2 != 0) {
            throw new EoException("Values have odd lenght: " + keyValues.length);
        }
        StringBuffer call = new StringBuffer("<call ");
        for (int i=0; i<keyValues.length; i = i + 2) {
            if (keyValues[i+1] == null) {
                continue;
            }
            call.append(createKeyValue(keyValues[i],keyValues[i+1]));
        }

        if (content == null) {
            call.append("/>");
        }
        else {
            call.append(">");
            call.append(content);
            call.append("</call>");
        }
        return call.toString();

    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return TemplateConfig.class;
    }

    /**
     * Remove list for attributes with a control character in the template context.
     *
     * @return
     */
    private static final Set createControlKeys() {
        Set controlKeys = new HashSet();
        controlKeys.add(F_PATH);
        controlKeys.add(F_MAP_PATH);
        controlKeys.add(A_IF);
        controlKeys.add(F_LOOP_PATH);
        controlKeys.add(A_EXECUTE);
        controlKeys.add(F_CONTENT);
        controlKeys.add(F_INSERT);
        return controlKeys;
    }

    @Override
    public void mapAttributes(final Map attributes) {
        if (attributes == null || attributes.isEmpty()) {
            return;
        }
        setContent(attributes.get(F_CONTENT));
        setLoopPath(attributes.get(F_LOOP_PATH));
        String keep = ScalarConverter.toString(attributes.get(A_KEEP));
        if (keep != null && !keep.isEmpty() && attributes.get(F_INSERT) != null) {
            attributes.remove(F_CONTENT);
            keepKey = TemplateConfig.KeepKeys.valueOf(keep);
            keepStart = ScalarConverter.toString(attributes.get(F_INSERT)).replaceAll("/>", ">");// + keepKey.getEndComment();
            keepEnd = keepKey.getStartComment() + "</call>" + keepKey.getEndComment();
        }
        super.mapAttributes(attributes);
    }

    private Map filterAttributes() {
        Map valueAttributes = new HashMap();
        for (Object key : getAttributes().keySet()) {
            if (controlKeys.contains(key)) {
                continue;
            }
            valueAttributes.put(key, getAttributes().get(key));
        }
        return valueAttributes;
    }

    public void mergeConfig() {
        super.mergeConfig();
    }

    public boolean hasContent() {
        return content != null && !content.isEmpty();
    }

    public String getContent() {
        return content;
    }

    public TemplateCall setContent(Object entry) {
        if (entry == null) {
            return this;
        }
        if (hasContent()) {
            return this;
        }
        this.content = ScalarConverter.toString(entry);
        contentWithCall = this.content.contains("<call");
        return this;
    }

    public boolean hasLoopPath() {
        return loopPath != null && !loopPath.isEmpty();
    }

    public String getLoopPath() {
        return loopPath;
    }

    public TemplateCall setLoopPath(Object entry) {
        if (entry == null) {
            return this;
        }
        if (hasLoopPath()) {
            return this;
        }
        this.loopPath = ScalarConverter.toString(entry);
        return this;
    }

    public TemplateConfig getTemplateConfig() {
        return (TemplateConfig) getConfig();
    }

    /**
     * This method without external attributes will be called from {@link CallExecutorResource}
     *
     * @param eo the adapter data object to be rendered
     * @return
     * @
     */
    public String execute(EO eo)  {
        mergeConfig();
        //resolvePath(eo, externalAttributes);
        EO adapter = eo;
        if (hasPath()) {
            adapter = eo.getEo(getPath());
        }
        /*if (!executePathIf(adapter, externalAttributes)) {
            return "";
        }*/
        // gets the template as content from the mapPath value
        if (hasMapPath()) {
            if (adapter.get(getMapPath()) != null) {
                this.setContent(adapter.get(getMapPath()));
            }
        }
        StringBuilder result = new StringBuilder();
        List<String> pathList = null;
        boolean isDynamic = false;
        if (getTemplateConfig() != null) {
            isDynamic = getTemplateConfig().getTemplateKey().contains("$[");
        }
        if (keepKey != null && !hasContent()) {
            result.append(keepStart);
        }
        if (contentWithCall) {
            Map valueAttributes = filterAttributes();
            bufferedExecutor = new ExecutorListTemplate(content, valueAttributes);
            if (bufferedExecutor.isEmpty()) {
                LOG.error("Buffered executor is empty!" + this.toString());
                return "";
            }
        }
        for (String path : pathList) {
            EO nextAdapter = null;
            try {
                nextAdapter = adapter.getEo(path);
            } catch (Exception e) {
                adapter.error(e.getMessage());
                e.printStackTrace();
                continue;
            }
            if (nextAdapter == null) {
                continue;
            }
            /*if (!executeLoopIf(nextAdapter, externalAttributes)) {
                continue;
            }*/
            if (hasContent()) {

                /*if (!contentWithCall) {// no subsequent calls
                    result.append(ReplaceUtil.replace(content, nextAdapter, mergeAllAttributes(externalAttributes)));
                } else {
                    if (bufferedExecutor.getExecutorList().size() > 1) {
                        result.append(bufferedExecutor.execute(nextAdapter, mergeAttributes(externalAttributes)));
                    } else {
                        result.append(bufferedExecutor.execute(nextAdapter, externalAttributes));
                    }
                }*/
            } else {
                /*try {
                    if (isDynamic) {
                        String dynamicKey = ReplaceUtil.replace(getTemplateConfig().getKey(), nextAdapter, mergeAllAttributes(externalAttributes));
                        EOConfigsCache configsCache = nextAdapter.getConfigsCache();
                        TemplateConfig templateConfig = configsCache.findTemplate(dynamicKey);
                        result.append(templateConfig.execute(nextAdapter, mergeAttributes(externalAttributes)));
                    } else {
                        result.append(getTemplateConfig().execute(nextAdapter, mergeAttributes(externalAttributes)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    adapter.error("Problem executing template " + getTemplateConfig().getTemplateKey() + "! " + e.getMessage());
                    return "Problem executing template " + getTemplateConfig().getTemplateKey() + "! " + e.getMessage();
                }*/
            }
            if (hasMapPath()) {
                nextAdapter.setPathValue(getMapPath(), result.toString());
            }
        }
        if (keepKey != null && !hasContent()) {
            result.append(keepEnd);
        }
        return result.toString();
    }

}
