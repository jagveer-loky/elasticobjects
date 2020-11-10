package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;

/**
 * Created by werner.diwischek on 10.07.2020
 */
public class JsonWriteCall extends FileWriteCall {
    private JSONSerializationType serializationType;
    private Integer indent;

    public JsonWriteCall()  {
        super();
    }

    public JsonWriteCall(final String configKey) {
        super(configKey);
    }

    @Override
    public String execute(final EO eo)  {
        if (!hasSerializationType()) {
            serializationType = JSONSerializationType.STANDARD;
        }
        if (!hasIndent()) {
            indent = 1;
        }
        setContent(new EOToJSON()
                .setSerializationType(serializationType)
                .setStartIndent(indent)
                .toJSON(eo));
        return super.execute(eo);
    }

    public boolean hasSerializationType() {
        return serializationType!=null;
    }

    public JSONSerializationType getSerializationType() {
        return serializationType;
    }

    public void setSerializationType(JSONSerializationType serializationType) {
        this.serializationType = serializationType;
    }

    public boolean hasIndent() {
        return indent!=null;
    }


    public Integer getIndent() {
        return indent;
    }

    public void setIndent(Integer indent) {
        this.indent = indent;
    }
}
