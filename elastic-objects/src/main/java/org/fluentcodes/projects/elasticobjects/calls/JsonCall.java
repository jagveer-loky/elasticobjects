package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.config.JsonConfig;
import org.fluentcodes.projects.elasticobjects.config.Permissions;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.eo.EOToJSON;
import org.fluentcodes.projects.elasticobjects.eo.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by werner.diwischek on 20.03.2018.
 * Refactored with write 18.5.2018
 */
public class JsonCall extends FileCall {

    private JSONSerializationType serializationType;
    private Integer indent;
    private String content;

    public JsonCall(EOConfigsCache provider, String key) throws Exception {
        super(provider, key);
    }

    @Override
    public void mapAttributes(final Map attributes) {
        if (attributes == null) {
            return;
        }
        super.mapAttributes(attributes);
        setContent(attributes.get(EO_STATIC.F_CONTENT));
        setSerializationType(attributes.get(EO_STATIC.F_SERIALIZATION_TYPE));
        setIndent(attributes.get(EO_STATIC.F_INDENT));
    }


    public void mergeConfig() {
        super.mergeConfig();
        setSerializationType(getJsonConfig().getSerializationType());
        setIndent(getJsonConfig().getIndent());
    }

    public boolean hasContent() {
        return content != null && !content.isEmpty();
    }

    public String getContent() {
        return content;
    }

    public JsonCall setContent(Object entry) {
        if (entry == null) {
            return this;
        }
        if (this.content != null && !this.content.isEmpty()) {
            return this;
        }
        this.content = ScalarConverter.toString(entry);
        return this;
    }

    public JSONSerializationType getSerializationType() {
        return serializationType;
    }

    public JsonCall setSerializationType(final Object entry) {
        if (entry == null) {
            return this;
        }
        if (this.serializationType != null) {
            return this;
        }
        if (entry instanceof JSONSerializationType) {
            this.serializationType = (JSONSerializationType) entry;
        }
        if (entry instanceof String) {
            this.serializationType = JSONSerializationType.valueOf((String) entry);
        }
        return this;
    }

    public JsonCall setIndent(Object entry) {
        if (entry == null) {
            return this;
        }
        if (this.indent != null && this.indent > 0) {
            return this;
        }
        try {
            this.indent = ScalarConverter.toInt(entry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public JsonConfig getJsonConfig() {
        return (JsonConfig) getConfig();
    }


    public String read(EO adapter) throws Exception {
        return read(adapter, new HashMap());
    }

    public String read(EO eo, Map attributes) throws Exception {
        if (!hasContent()) {
            this.content = super.read(eo, null);
        }
        if (eo.hasRoles()) {
            try {
                if (!this.getRolePermissions().hasPermissions(Permissions.READ, eo.getRoles())) {
                    eo.warn("No permission!");
                    return "No permissions!";
                }
            } catch (Exception e) {
                eo.warn(e.getMessage());
                return e.getMessage();
            }
        }
        mapAttributes(attributes);
        mergeConfig();
        if (hasMapPath()) {
            if (isDynamicMapPath()) {
                //TODO... has to be implemented where a value of the content defines the mapPath
            }
            eo.add(getMergePath()).map(content);
        } else {
            eo.add(getPath()).map(content);
        }
        return "";
    }

    public String write(EO adapter) throws Exception {
        return write(adapter, new HashMap<>());
    }

    @Override
    public String write(EO eo, Map attributes) throws Exception {
        if (eo == null) {
            throw new Exception("Null adapter for write!");
        }
        setSerializationType(getJsonConfig().getSerializationType());
        setIndent(getJsonConfig().getIndent());
        EOToJSON jsonBuilder = new EOToJSON();
        jsonBuilder.setStartIndent(this.indent);
        jsonBuilder.setSerializationType(this.serializationType);
        String result = jsonBuilder.toJSON(eo.getChild(super.getMapPath()));
        super.write(eo, attributes, result);
        return "";
    }
}
