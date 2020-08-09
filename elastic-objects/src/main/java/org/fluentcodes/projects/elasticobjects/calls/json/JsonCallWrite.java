package org.fluentcodes.projects.elasticobjects.calls.json;

import org.fluentcodes.projects.elasticobjects.calls.files.FileCallWrite;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;

/**
 * Created by werner.diwischek on 10.07.2020
 */
public class JsonCallWrite extends FileCallWrite {

    private JSONSerializationType serializationType;
    private Integer indent;
    private String content;

    public JsonCallWrite()  {
        super();
    }

    public JsonCallWrite(final String configKey) {
        super();
        setConfigKey(configKey);
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return JsonConfig.class;
    }

    public JSONSerializationType getSerializationType() {
        return serializationType;
    }

    public void setSerializationType(JSONSerializationType serializationType) {
        this.serializationType = serializationType;
    }



    public Integer getIndent() {
        return indent;
    }

    public void setIndent(Integer indent) {
        this.indent = indent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
