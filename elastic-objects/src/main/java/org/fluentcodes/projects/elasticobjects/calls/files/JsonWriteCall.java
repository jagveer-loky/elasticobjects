package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.IEOScalar;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;

/*.{javaHeader}|*/

/**
 * Serialize the value of the sourcePath as JSON and write it to a file
 *
 * @author Werner Diwischek
 * @creationDate
 * @modificationDate Tue Dec 08 09:49:43 CET 2020
 */
public class JsonWriteCall extends FileWriteCall {
    /*.{}.*/

    /*.{javaStaticNames}|*/
    public static final String INDENT = "indent";
    public static final String SERIALIZATION_TYPE = "serializationType";
    /*.{}.*/

    /*.{javaInstanceVars}|*/
    private Integer indent;
    private JSONSerializationType serializationType;
    /*.{}.*/

    public JsonWriteCall() {
        super();
    }

    public JsonWriteCall(final String configKey) {
        super(configKey);
    }

    @Override
    public String execute(final IEOScalar eo) {
        if (!hasSerializationType()) {
            serializationType = JSONSerializationType.STANDARD;
        }
        if (!hasIndent()) {
            indent = 1;
        }
        setContent(new EOToJSON()
                .setSerializationType(serializationType)
                .setIndent(indent)
                .toJson(eo));
        return super.execute(eo);
    }

    /*.{javaAccessors}|*/

    /**
     * The number of indent for serialization level.
     */

    public JsonWriteCall setIndent(Integer indent) {
        this.indent = indent;
        return this;
    }

    public Integer getIndent() {
        return this.indent;
    }

    public boolean hasIndent() {
        return indent != null;
    }

    /**
     * The types of Serialization.
     */

    public JsonWriteCall setSerializationType(JSONSerializationType serializationType) {
        this.serializationType = serializationType;
        return this;
    }

    public JSONSerializationType getSerializationType() {
        return this.serializationType;
    }

    public boolean hasSerializationType() {
        return serializationType != null;
    }
    /*.{}.*/
}
