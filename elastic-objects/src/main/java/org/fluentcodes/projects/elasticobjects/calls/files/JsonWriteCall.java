package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.models.Config;

/**
 * Created by werner.diwischek on 10.07.2020
 */
public class JsonWriteCall extends FileWriteCall {

    private JSONSerializationType serializationType;
    private Integer indent;
    private String content;

    public JsonWriteCall()  {
        super();
    }

    public JsonWriteCall(final String configKey) {
        super();
        setConfigKey(configKey);
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return FileConfig.class;
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
